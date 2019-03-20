<%@page import="com.sys4u.company.dto.Dept"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import= "java.util.List"
import="com.sys4u.company.dto.Page"
%>
<%
	List<Dept> depts = (List<Dept>) request.getAttribute("depts");
	//Page empPage = (Page) request.getAttribute("page");
	String searchKey = request.getParameter("searchKey") == null? "DEPTNO" : request.getParameter("searchKey");
	String searchValue = request.getParameter("searchValue") == null? "" : request.getParameter("searchValue") ;
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>EMP Table List</title>
<script type="text/javascript" src="/bbs/static-root/js/common/page.js"></script> 
</head>
<body>

	<input type="hidden" id="transportUrl" value="/bbs/deptlist.do">
	<h2>EMP Table</h2>
	<table border="1">
		<tr>
			<td>DEPTNO</td>
			<td>DNAME</td>
			<td>LOC</td>			
		</tr>
		
		<%
			for(Dept dept : depts) {
		%>
		
		<tr>
			<td><%=dept.getDeptNo()%></td>
			<td><a href="<%=request.getAttribute("viewUrl")%>&deptno=<%=dept.getdName()%>"><%=dept.getdName()%></a></td>
			<td><%=dept.getLoc()%></td>
		</tr>
		
		<%} %>
				
		<script>
			function deptGoPage(pageNum){
				console.log('hello dept');
			}
		</script>
		<jsp:include page="/WEB-INF/jsp/common/dynamicpage.jsp" flush="false">
			<jsp:param value="dept" name="domain"/>
		</jsp:include>
		
		<script>
			function empGoPage(pageNum){
				console.log('hello emp');
			}
		</script>
		<jsp:include page="/WEB-INF/jsp/common/dynamicpage.jsp" flush="false">
			<jsp:param value="emp" name="domain"/>
		</jsp:include>
		
		<tr>
			<td><span> Dept Count:<%=request.getAttribute("deptCount") %></span></td>
			<td colspan="2" align="right"><button id="btn_deptRegister">DEPT 등록</button></td>
		</tr>		
	</table>

	
</body>
</html>