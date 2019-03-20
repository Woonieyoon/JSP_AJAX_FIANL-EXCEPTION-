<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@ 
    page import= "java.util.List"
import="com.sys4u.company.dto.Employee"
import="com.sys4u.company.dto.Pagination"
%><%
	String pageNum = request.getParameter("pageNum") == null? "1" : request.getParameter("pageNum");
	String searchKey = request.getParameter("searchKey") == null? "EMPNO":request.getParameter("searchKey");
	String searchValue = request.getParameter("searchValue") == null? "":request.getParameter("searchValue");
	int empCount = (Integer)request.getAttribute("empCount");
%><!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>EMP Table List</title>
<script type="text/javascript" src="/bbs/static-root/js/common/ajaxtable.js"></script>
</head>
<body>

<input type="hidden" id="transportUrl" value="<%=request.getContextPath()%>/emplist.do">
<input type="hidden" id="ajaxUrl" value ="<%=request.getContextPath()%>/ajaxlist.do">
<input type="hidden" id="searchKey" value ="<%=searchKey%>">
<input type="hidden" id="searchValue" value ="<%=searchValue%>">
<input type="hidden" id="pageNum" value="<%=pageNum%>">

	<h2>EMP Table</h2>
	<table border="1">
		<thead>
			<tr>
				<td>사원번호</td>
				<td>사원명</td>
				<td>입사일</td>
				<td>부서명</td>		
			</tr>
		</thead>
		
		<tbody id='table_list_template'>	
		</tbody>
		
		<tbody id='table_Page_template'>	
		</tbody>
		<tr>
			<td colspan="4">
				<form action="" method="get">
					<select name="searchKey" id="select_searchKey">
						<option value="EMPNO">사원번호</option>
						<option value="ENAME">사원명</option>
						<option value="DNAME">부서명</option>
					</select>
					<input type="text" id="select_searchValue" name="searchValue" value=<%=searchValue%>>
					<input type="button" id="btn_search" value="검색">
				</form>
			</td>
		</tr>
		<tr>
			<td colspan="4">
				<Button id="btn_register">사원 등록</Button>
			</td>
		</tr>
		
		<tbody id='employee_register_template'>	
		</tbody>
	</table>
	
	
</body>
</html>