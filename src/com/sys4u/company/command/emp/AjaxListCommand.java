package com.sys4u.company.command.emp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sys4u.company.command.AbstractCommand;
import com.sys4u.company.command.Constants;
import com.sys4u.company.dao.EmpDAO;
import com.sys4u.company.dto.Employee;
import com.sys4u.company.dto.Pagination;
import com.sys4u.company.exception.CommandExecutionException;

public class AjaxListCommand extends AbstractCommand{

	private static final int ROWS_PER_PAGE = 3;
	private static final int PAGES_PER_PAGE = 5;
	
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		
		String searchKey = getParameter(req,"searchKey" ,"EMPNO");
		String searchValue = getParameter(req,"searchValue","");
		int pageNum = getIntParameter(req, "pageNum", "1");
		
		System.out.println(searchKey +"," +searchValue +"," + pageNum);
		EmpDAO empDao = new EmpDAO(conn);
		int empCount = empDao.getToTalCount(searchKey, searchValue);
		System.out.println(empCount);
		Pagination page = new Pagination(ROWS_PER_PAGE, pageNum, PAGES_PER_PAGE, empCount);
		
		if (page.getTotalPageCount() < pageNum) {
			page.setPageNum(page.getTotalPageCount());
		}
		
		List<Employee> employees = empDao.find(searchKey, searchValue, page);
		
		Map<String , Object> resultMap = new HashMap<>();
		resultMap.put("employees",employees);
		resultMap.put("paging",page);
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			String json = mapper.writeValueAsString(resultMap);
			System.out.println(json);
			req.setAttribute("json", json);
		} catch (JsonProcessingException e) {
			throw new CommandExecutionException();
		}
		return Constants.AJAX_List_VIEW;
	}

}
