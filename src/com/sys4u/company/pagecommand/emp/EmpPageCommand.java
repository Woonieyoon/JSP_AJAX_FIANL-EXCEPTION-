package com.sys4u.company.pagecommand.emp;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sys4u.company.command.AbstractCommand;
import com.sys4u.company.dao.EmpDAO;
import com.sys4u.company.dto.DeptData;
import com.sys4u.company.dto.Employee;
import com.sys4u.company.exception.IllegalParameterException;

public class EmpPageCommand extends AbstractCommand{

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp ) {
		
		String viewPageName = "/WEB-INF/jsp/emp/registerorupdate.jsp";
		String form = getParameter(req, "form", "register");
		
		EmpDAO empDAO = new EmpDAO(conn);
		Employee employee = new Employee();
		if(req.getParameter("empno")!=null) {
			int empno = Integer.parseInt(req.getParameter("empno"));
			employee = empDAO.findDetail(empno);
		}else{
			employee.setEmpno(empDAO.getMaxNum()+1);
		}
		
		List<String> jobList = empDAO.getJob();
		List<DeptData> deptList = empDAO.getDept();
		
		req.setAttribute("employee", employee);
		req.setAttribute("jobList", jobList);
		req.setAttribute("deptList", deptList);
		req.setAttribute("form", form);
		
		return viewPageName;
	}

}
