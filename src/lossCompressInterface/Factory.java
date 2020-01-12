package lossCompressInterface;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;

class Factory {
	public static IEncode GetEncodeInstance(String className) {
		IEncode instance = null;
		try {
			instance = (IEncode) Class.forName(className).getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return instance;
	}
	
	public static IDecode GetDecodeInstance(String className, String path, LinkedHashMap<String, ?> map) {
		IDecode instance = null;
		
		try {
			instance = (IDecode) Class.forName(className).getDeclaredConstructor(String.class, LinkedHashMap.class)
					.newInstance(path, map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return instance;
	}
	
	public static String GetEncodeClassName(String type) {
		StringBuilder builder = new StringBuilder();
		return builder.append(type + "." + type + "Encode").toString();
	}
	
	public static String GetDecodeClassName(String type) {
		StringBuilder builder = new StringBuilder();
		return builder.append(type + "." + type + "Decode").toString();
	}
	
}


