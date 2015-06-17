package com.dealmoon.utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import redis.clients.jedis.Jedis;

public class DB {
	private static SqlSessionFactory sqlSessionFactory;
	private static Reader reader;
	private static Jedis jedis;

	//初始化sqlSessionFactory
	static {
		try {
			File f = new File("conf/configuration.xml");
			reader = new FileReader(f);
			//reader = Resources.getResourceAsReader("conf/configuration.xml");
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static SqlSessionFactory getSessionFactory() {
		return sqlSessionFactory;
	}

	public static SqlSession openSession() {
		return sqlSessionFactory.openSession();
	}

	public static Jedis getJedis() {
		Map<String, String> config = MyUtils.proReader("conf/config.properties");
		if (jedis == null) {
			jedis = new Jedis(config.get("redis_read_host"), Integer.parseInt(config.get("redis_read_port")));
		}
		return jedis;
	}
}
