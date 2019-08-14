package com.jt.util;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.vo.EasyUITree;

/**
 * 说明：工具类中主要实现对象与json互转
 * static方法被用户直接调用
 * @author 沙鑫熠
 *
 */
public class ObjectMapperUtil {
	//成员变量：是否有线程安全问题
//	private static final Integer abc =123;  有 要加final
	private static final ObjectMapper objectMapper =new ObjectMapper();
	//1.对象转化为json
	public static String toJSON(Object target) {
		String result =null;
		try {
			result=objectMapper.writeValueAsString(target);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return result;
		
	}
	
	 	//2.JSON转化为对象
	public static <T> T toObject(String json,Class<T>targetClass) {
		T target = null;
		try {
			target = objectMapper.readValue(json, targetClass);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return target;
	}

}

	
	
	

