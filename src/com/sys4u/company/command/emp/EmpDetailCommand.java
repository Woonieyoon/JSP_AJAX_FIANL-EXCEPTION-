package com.sys4u.company.command.emp;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sys4u.company.command.AbstractCommand;
import com.sys4u.company.dao.EmpDAO;
import com.sys4u.company.dto.Employee;

public class EmpDetailCommand extends AbstractCommand{

	private static final String DETAIL_VIEW_PAGE ="/WEB-INF/jsp/emp/empdetail.jsp";
	
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp ) {

		EmpDAO empDAO = new EmpDAO(conn);
		Employee employee = empDAO.findDetail(Integer.parseInt(req.getParameter("empno")));
		
		req.setAttribute("employee", employee);
		return DETAIL_VIEW_PAGE;
	}

}
