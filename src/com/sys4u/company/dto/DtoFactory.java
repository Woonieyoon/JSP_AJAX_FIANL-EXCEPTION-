package com.sys4u.company.dto;

import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.ResultSet;

public class DtoFactory {

	public static<T> T fromResultSet(ResultSet rs,Class<T> clazz) throws Exception{
		T instance = clazz.newInstance();
	
		Field[] fields = Employee.class.getDeclaredFields();
		for(Field field:fields) {
			if(field.getName().equals("serialVersionUID")) {
				continue;
			}
			field.setAccessible(true);
			
			String columnName = field.getName().toUpperCase();
			Class<?> type = field.getType();
			
			if(type==int.class) {
				field.set(instance, rs.getInt(columnName));
			}else if(type == String.class) {
				field.set(instance, rs.getString(columnName));
			}else if(type==Date.class) {
				field.set(instance, rs.getDate(columnName));
			}
			
		}
		return instance;
	}	
	
}