package com.dealmoon.service;

import java.util.Set;

import org.apache.ibatis.session.SqlSession;

import redis.clients.jedis.Jedis;

import com.dealmoon.dao.StatisticsMapper;
import com.dealmoon.model.Statistics;
import com.dealmoon.utils.DB;

public class Service {

	private static Jedis jedis = DB.getJedis();

	public static synchronized void insert() {

		Set<String> set = jedis.keys("statistics_*");
		SqlSession session = DB.openSession();
		StatisticsMapper statisticsMapper = session.getMapper(StatisticsMapper.class);

		for (String key : set) {
			String[] strs = key.split("_");

			Statistics statistics = new Statistics();
			statistics.setRes_type(strs[1]);
			statistics.setRes_id(Integer.parseInt(strs[2]));

			String data = jedis.get(key);
			if (data == null) {
				continue;
			}
			statistics.setData(Integer.parseInt(data));
			statisticsMapper.insert(statistics);
			jedis.del(key);
		}
		jedis.close();
		session.commit();
		session.close();
	}

	public static void main(String[] args){
		Service.insert();
	}
}
