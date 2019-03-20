package com.sys4u.company.command.emp;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sys4u.company.command.AbstractCommand;
import com.sys4u.company.command.Constants;
import com.sys4u.company.dao.EmpDAO;
import com.sys4u.company.dto.Employee;
import com.sys4u.company.exception.CommandExecutionException;

public class EmpUpdateCommand extends AbstractCommand{

	private static final String REDIRECT_URL ="/bbs/emplist.do";	
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp ) {
		EmpDAO empDao = new EmpDAO(conn);
		Employee employee = new Employee().create(req);
		
		int result = empDao.update(employee);

		if (result != 1) {
			try {
				conn.rollback();
			} catch (SQLException e) {
				LOGGER.error("rollback failed.", e);
			}
			throw new CommandExecutionException("Updating Employee["+employee.getEmpno()+"] failed.");
		}

		req.setAttribute("redirectUrl", REDIRECT_URL);
		req.setAttribute("message", "성공적으로 저장되었습니다.");
		return Constants.REDIRECT_VIEW;
	}

}
