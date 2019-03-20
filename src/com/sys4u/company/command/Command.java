package com.sys4u.company.command;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface Command {
	public void setConnection(Connection conn);
	public String execute(HttpServletRequest req, HttpServletResponse resp);
	public void destroy();
}
