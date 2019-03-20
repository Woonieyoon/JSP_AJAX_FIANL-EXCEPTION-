package com.sys4u.company.command.emp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sys4u.company.command.AbstractCommand;
import com.sys4u.company.command.Constants;
import com.sys4u.company.dao.EmpDAO;
import com.sys4u.company.dto.Employee;
import com.sys4u.company.exception.CommandExecutionException;
import com.sys4u.company.exception.IllegalParameterException;

public class EmpInsertCommand extends AbstractCommand{

	
	private static final String REDIRECT_URL ="/bbs/emplist.do";
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		
		EmpDAO empDAO = new EmpDAO(conn);
		Employee employee;
		try {
			employee = new Employee().create(req);
		} catch (NumberFormatException e) {
			throw new IllegalParameterException();
		}
		
		int result = empDAO.insert(employee);
		
		if (result > 0) {
			req.setAttribute("redirectUrl", REDIRECT_URL);
			req.setAttribute("message", "성공적으로 저장되었습니다.");
			return Constants.REDIRECT_VIEW;
		}

		throw new CommandExecutionException("Employee data[" + employee.getEname() + "] not inserted.");
	}

}
