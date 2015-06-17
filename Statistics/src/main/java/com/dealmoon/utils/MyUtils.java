package com.dealmoon.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class MyUtils {
	public static Map<String, String> proReader(String path) {
		Reader reader;
		Map<String, String> map = new HashMap<String, String>();
		try {
			reader = new FileReader(path);
			
			Properties prop = new Properties();
			Set keySet = null;
			prop.load(reader);
			keySet = prop.keySet();
			Iterator it = keySet.iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				// 把配置文件中的键值对存到map中
				map.put(key, (String) prop.get(key));
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
}
