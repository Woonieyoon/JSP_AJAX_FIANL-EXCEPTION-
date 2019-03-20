<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import= "java.util.List"
import="com.sys4u.company.dto.Employee"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>EMP Table Detail</title>
</head>
<body>
	<h1> EMP Table Detail </h1>
		<table  style="padding: 5px 0 5px 0; width=940; ">
			<%
				Employee employee = (Employee)request.getAttribute("employee");
			%>
			
			<tr height="2" bgcolor="#FFC8C3">
				<td colspan="2"></td>
			</tr>
			<tr>
				<th>EMPNO</th>
				<td><input type="text" name="emp_no" value=<%=employee.getEmpno() %> readonly style="background-color:#e2e2e2;"></td>
			</tr>

			<tr>
				<th>ENAME</th>
				<td><input type="text" name="emp_name" value=<%=employee.getEname()%> readonly style="background-color:#e2e2e2;"></td>
			</tr>

			<tr>
				<th>JOB</th>
				<td><input type="text" name="emp_job" value=<%=employee.getJob()%> readonly style="background-color:#e2e2e2;"></td>
			</tr>

			<tr>
				<th>MGR</th>
				<td><input type="text" name="emp_mgr" value=<%=employee.getMgr()%> readonly style="background-color:#e2e2e2;"></td>
			</tr>

			<tr>
				<th>HIREDATE</th>
				<td><input type="text" name="emp_hiredate" value=<%=employee.getHiredate().toString()%> readonly style="background-color:#e2e2e2;">
			</tr>

			<tr>
				<th>SAL</th>
				<td><input type="text" name="emp_sal" value=<%=employee.getSal() %> readonly style="background-color:#e2e2e2;"></td>
			</tr>

			<tr>
				<th>COMM</th>
				<td><input type="text" name="emp_comm" value=<%=employee.getComm() %> readonly style="background-color:#e2e2e2;"></td>
			</tr>

			<tr>
				<th>DEPT</th>
				<td><input type="text" name="emp_deptno" value=<%=employee.getDname()%> readonly style="background-color:#e2e2e2;"></td>
			</tr>
			
           <tr>
             <td colspan="2" align="center">
               <button id="btnUpdate">수정</button>
               <button id="btnCancle" onclick="javascript:history.back();">취소</button>               
            </td>
           </tr>
		</table>
	
	<script>
		document.getElementById('btnUpdate').onclick = function(){
			location.href = "<%=request.getContextPath()%>/emppage.do?form=update&empno=<%=employee.getEmpno() %>";
		};
	</script>
	
</body>
</html>