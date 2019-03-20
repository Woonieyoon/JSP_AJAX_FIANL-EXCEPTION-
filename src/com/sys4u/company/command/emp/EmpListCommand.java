package com.sys4u.company.command.emp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sys4u.company.command.AbstractCommand;
import com.sys4u.company.dao.EmpDAO;
import com.sys4u.company.dto.Pagination;


public class EmpListCommand extends AbstractCommand{

	private static final String ALL_LIST_VIEW = "/WEB-INF/jsp/emp/emplist.jsp" ;
	private static final int ROWS_PER_PAGE = 3;
	private static final int PAGES_PER_PAGE = 5;
	
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp ) {
		
		String searchKey = getParameter(req,"searchKey" ,"EMPNO");
		String searchValue = getParameter(req,"searchValue","");
		int pageNum = getIntParameter(req, "pageNum", "1");
		
//		StringBuilder viewUrl = new StringBuilder();
//		viewUrl.append("/bbs/empdetail.do");
//		viewUrl.append("?searchKey=").append(searchKey);
//		viewUrl.append("&searchValue=").append(searchValue);
//		viewUrl.append("&pageNum=").append(pageNum);
		
		EmpDAO empDao = new EmpDAO(conn);
		
		int empCount = empDao.getToTalCount(searchKey, searchValue);
		Pagination page = new Pagination(ROWS_PER_PAGE, pageNum, PAGES_PER_PAGE, empCount);
		
		if (page.getTotalPageCount() < pageNum) {
			page.setPageNum(page.getTotalPageCount());
		}
		
		//page는 선택하게 만들어준다. 5 , 10 ...
		
		req.setAttribute("employees", empDao.find(searchKey, searchValue, page));
		//req.setAttribute("viewUrl", viewUrl.toString());
		req.setAttribute("page", page);
		//req.setAttribute("empCount", empCount);
		
		return ALL_LIST_VIEW;
	}
}
