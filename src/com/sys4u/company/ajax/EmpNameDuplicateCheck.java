package com.sys4u.company.ajax;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sys4u.company.command.AbstractCommand;
import com.sys4u.company.command.Constants;
import com.sys4u.company.dao.EmpDAO;
import com.sys4u.company.exception.DAOException;

public class EmpNameDuplicateCheck extends AbstractCommand{

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp ) {
		
		EmpDAO empDao = new EmpDAO(conn);
		String ename = req.getParameter("ename");
		boolean exists = false;

		try {
			exists = empDao.existsName(ename);
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		
		req.setAttribute("ajaxResponse", exists ? "exists" : "notexists");
		return Constants.AJAX_VIEW;
	}
}
