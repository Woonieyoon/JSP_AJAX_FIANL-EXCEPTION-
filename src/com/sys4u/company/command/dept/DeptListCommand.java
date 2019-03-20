package com.sys4u.company.command.dept;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sys4u.company.command.AbstractCommand;
import com.sys4u.company.dao.DeptDAO;
import com.sys4u.company.dto.Pagination;

public class DeptListCommand extends AbstractCommand{

	private final static String DEPT_LIST_VIEW = "/WEB-INF/jsp/dept/deptlist.jsp";
	private static final int ROWS_PER_PAGE = 1;
	private static final int PAGES_PER_PAGE = 1;
	
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp  ) {

		String searchKey = getParameter(req, "searchKey","DEPTNO");
		String searchValue = getParameter(req,"searchValue","");
		int pageNum = getIntParameter(req, "pageNum", "1");
		
		
		StringBuilder viewUrl = new StringBuilder();
		viewUrl.append("/bbb/deptdetail.do");
		viewUrl.append("?searchKey=").append(searchKey);
		viewUrl.append("&searchValue=").append(searchValue);
		viewUrl.append("&pageNum=").append(pageNum);
		
		
		DeptDAO deptDAO = new DeptDAO(conn);
		int deptCount = deptDAO.getDeptCount(searchKey, searchValue);
		
		Pagination page = new Pagination(ROWS_PER_PAGE, pageNum, PAGES_PER_PAGE, deptCount);
		
		if (page.getTotalPageCount() < pageNum) {
			page.setPageNum(page.getTotalPageCount());
		}
		
		req.setAttribute("viewUrl", viewUrl);
		req.setAttribute("deptCount", deptCount);
		req.setAttribute("depts", deptDAO.find(searchKey, searchValue, page));
		req.setAttribute("page", page);
		req.setAttribute("domain", "dept");
		
		return DEPT_LIST_VIEW;
	}

}
