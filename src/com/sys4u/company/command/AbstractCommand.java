package com.sys4u.company.command;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractCommand implements Command {
	protected static final Logger LOGGER = LoggerFactory.getLogger(Command.class);
	protected Connection conn;
	
	@Override
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	
	protected String getBeforeParameter(String beforeValue,String defaultValue) {
		return beforeValue == null || beforeValue.isEmpty() ? defaultValue : beforeValue ;
	}
	
	protected String getParameter(HttpServletRequest req, String name, String defaultValue) {
		String value = req.getParameter(name);
		System.out.println("value값은"+value);
		return value == null || value.isEmpty()? defaultValue : value;
	}
	
	protected int getIntParameter(HttpServletRequest req, String name, String defaultValue) {
		String value = req.getParameter(name);
		value = (value == null) ? defaultValue : value;
		int intValue;
		try {
			intValue = Integer.parseInt(value);
		} catch(Exception e) {
			intValue =  Integer.parseInt(defaultValue);
		}
		return intValue;
	}
	
	protected int getInt(String data) {
		
		int intValue;
		try {
			intValue = Integer.parseInt(data);
		} catch (NumberFormatException e) {
			return 1;
		}
		return intValue;
	}
	
	
	@Override
	public void destroy() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
