package com.sys4u.company.command.emp;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sys4u.company.command.AbstractCommand;
import com.sys4u.company.command.Constants;
import com.sys4u.company.dao.EmpDAO;
import com.sys4u.company.dto.Employee;
import com.sys4u.company.exception.CommandExecutionException;

public class AjaxInsertCommand extends AbstractCommand{

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {

		//이부분은 일부러 ajax에러 처리를 확인하기 위해 만들어놓음 지우면 잘 돌아가~~
		if(req!=null) {
			throw new CommandExecutionException("강제로 발생시킴");
		}
		
		ObjectMapper mapper = new ObjectMapper();
        try {
			Employee obj = mapper.readValue(req.getInputStream(), Employee.class);
			int result = new EmpDAO(conn).insert(obj);
			if(result>0) {
				System.out.println("성공");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
        req.setAttribute("ajaxResponse", "success");
        
        return null;
        //return Constants.AJAX_VIEW;
	}

}
