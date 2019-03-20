<%@page import="com.sys4u.company.dto.DeptData"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.Date"%>
<%@page import="com.sys4u.company.dto.Employee"%>
<%@page import="com.sys4u.company.dto.Pagination"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	Employee employee = (Employee) request.getAttribute("employee");
	String form = (String) request.getAttribute("form");
	List<String> jobList = (List<String>)request.getAttribute("jobList");
	List<DeptData> deptList = (List<DeptData>)request.getAttribute("deptList");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Employee From</title>
</head>
<body>

	<h1>
		<%
			if (form.equals("register")) {
		%>
		사원 등록
		<%
			} else {
		%>
		사원 수정
		<%
			}
		%>
	</h1>
	<form name="empForm" method="post" action="" value="register">

		<input type="hidden" name="form" id="form"	value="<%=request.getAttribute("form")%>">
		<input type="hidden" name="empno" value="<%=employee.getEmpno()%>">

		<table  style="padding: 5px 0 5px 0; width=940 ">
			<tr height="2" bgcolor="#FFC8C3">
				<td colspan="2"></td>
			</tr>
			<tr>
				<th>EMPNO</th>
				<td><input type="text" name="emp_no" value="<%=employee.getEmpno()%>" readonly="readonly"  style="background-color: #e2e2e2;"></td>
			</tr>

			<tr>
				<th>ENAME</th>
				<td>
				<input type="text" name="emp_name" id="emp_name" value="<%=employee.getEname() == null ? "" : employee.getEname() %>"> 
				<input type="button" id="duplicate_check" value="중복확인"></td>
			</tr>

			<tr>
				<th>JOB</th>
				<td>
					<select name="emp_job">
						<%for (int i=0;i<jobList.size();i++){
							if(jobList.get(i).equals(employee.getJob())){
						%>
						<option selected="selected" value="<%=jobList.get(i)%>"><%=jobList.get(i)%></option>
						<%} else {%>
						<option value="<%=jobList.get(i)%>"><%=jobList.get(i)%></option>
						<%}
						}
						%>
					</select>
				</td>
			</tr>

			<tr>
				<th>MGR</th>
				<td><input type="text" id="emp_mgr" name="emp_mgr" value="<%=employee.getMgr() == 0 ? "" :employee.getMgr()%>"></td>
			</tr>

			<tr>
				<th>HIREDATE</th>
				<td><input type="date" name="emp_hiredate" value="<%=employee.getHiredate()%>"></td>
			</tr>

			<tr>
				<th>SAL</th>
				<td><input type="text" id="emp_sal" name="emp_sal" value="<%=employee.getSal() == 0? "" : employee.getSal()%>"></td>
			</tr>

			<tr>
				<th>COMM</th>
				<td><input type="text" id="emp_comm" name="emp_comm" value="<%=employee.getComm() == 0? "" : employee.getComm()%>"></td>
			</tr>

			<tr>
				<th>DEPT</th>
				<td>
					<select name="emp_deptno">
						<%for(int i=0;i<deptList.size();i++){
							if(deptList.get(i).getDeptNo()== employee.getDeptno()){
						%>
						<option selected="selected" value="<%=deptList.get(i).getDeptNo()%>"><%=deptList.get(i).getdName()%></option>
						<%} else{ %>
						<option value="<%=deptList.get(i).getDeptNo()%>"><%=deptList.get(i).getdName()%></option>
						<%}} %>	
					</select>
				</td>
			</tr>

			<tr>	
				<td colspan="2" align="center">
					<%if(form.equals("register")) {%>
						<input type="button" id="btnRegister" value="사원등록"> 
						<input type="button" value="취소" onclick="javascript:history.back()">
					<%}else { %>
						<input type="button" id="btnRegister"value="수정완료"> 
						<input type="reset" value="취소" onclick="javascript:history.back()">
					<%} %>
				</td>
			</tr>
		</table>
	</form>

	<script type="text/javascript" src="<%=request.getContextPath()%>/static-root/js/emp/empregistermodified.js"></script>

</body>
</html>
