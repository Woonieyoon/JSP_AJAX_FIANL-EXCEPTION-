package com.sys4u.company.pagecommand.emp;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sys4u.company.command.AbstractCommand;
import com.sys4u.company.command.Constants;
import com.sys4u.company.dao.EmpDAO;
import com.sys4u.company.dto.DeptData;
import com.sys4u.company.dto.Employee;

public class AjaxPageCommand extends AbstractCommand {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		
		StringBuilder ajaxResponse = new StringBuilder();
		String form = getParameter(req, "form", "register");
		Employee employee = new Employee();
		employee.setEname("");
		
		EmpDAO empDao = new EmpDAO(conn);
		
		if (req.getParameter("empno") != null) {
			int empno = getIntParameter(req, "empno", "");
			employee = empDao.findDetail(empno);
		}
		
		List<String> jobList = empDao.getJob();
		List<DeptData> deptList = empDao.getDept();
		
		
		ajaxResponse.append("<h1>");
		if (form.equals("register")) {
			ajaxResponse.append("사원 등록");
		} else {
			ajaxResponse.append("사원 수정");
		}
		ajaxResponse.append("</h1>");
		//ajaxResponse.append("<form name='empForm' method='post' action='' >");
		ajaxResponse.append("<input type='hidden' name='form' id='form'	value='").append(form+"'>");
		ajaxResponse.append("<input type='hidden' name='empno' value='").append(employee.getEmpno()+"'>");

		ajaxResponse.append("<table  style='padding: 5px 0 5px 0; width=940 '>");
		ajaxResponse.append("	<tr height='2' bgcolor='#FFC8C3'>");
		ajaxResponse.append("		<td colspan='2'></td>");
		ajaxResponse.append("	</tr> ");
		ajaxResponse.append("	<tr> ");
		ajaxResponse.append("		<th>EMPNO</th>");
		ajaxResponse.append("		<td><input type='text' name='emp_no' value='"+(empDao.getMaxNum()+1)+"' readonly='readonly'  style='background-color: #e2e2e2;'></td>");
		ajaxResponse.append("	</tr> ");

		ajaxResponse.append("	<tr> ");
		ajaxResponse.append("	<th>ENAME</th> ");
		ajaxResponse.append("		<td> ");
		ajaxResponse.append("		<input type='text' name='emp_name' id='emp_name' value='"+(employee.getEname() == null ? "" : employee.getEname()) +"'> ");
		ajaxResponse.append("		<input type='button' id='duplicate_check' value='중복확인'></td>");
		ajaxResponse.append("	</tr> ");

		ajaxResponse.append("	<tr> ");
		ajaxResponse.append("		<th>JOB</th> ");
		ajaxResponse.append("		<td> ");
		ajaxResponse.append("	<select name='emp_job' id='emp_job'>");
		for (int i=0;i<jobList.size();i++){
				if(jobList.get(i).equals(employee.getJob())){
					ajaxResponse.append("<option selected='selected' value='"+jobList.get(i)+"'>"+jobList.get(i)+"</option>");
				}else{
					ajaxResponse.append("	<option value='"+jobList.get(i)+"'>"+jobList.get(i)+"</option> ");
				}
		}
						
		ajaxResponse.append("			</select>");
		ajaxResponse.append("		</td>");
		ajaxResponse.append("	</tr>");

		ajaxResponse.append("	<tr>");
		ajaxResponse.append("		<th>MGR</th>");
		ajaxResponse.append("		<td><input type='text' id='emp_mgr' name='emp_mgr' value='"+ (employee.getMgr() == 0 ? "" :employee.getMgr())+ "'></td>");
		ajaxResponse.append("	</tr> ");
		ajaxResponse.append("			<tr>");
		ajaxResponse.append("		<th>HIREDATE</th>");
		ajaxResponse.append("		<td><input type='date' id='emp_date' name='emp_hiredate' value='"+ employee.getHiredate() + "'></td> ");
		ajaxResponse.append("	</tr>");

		ajaxResponse.append("	<tr>");
		ajaxResponse.append("		<th>SAL</th> ");
		ajaxResponse.append("		<td><input type='text' id='emp_sal' name='emp_sal' value='"+ (employee.getSal() == 0? "" : employee.getSal()) + "'></td>");
		ajaxResponse.append("	</tr>");

		ajaxResponse.append("	<tr>");
		ajaxResponse.append("		<th>COMM</th>");
		ajaxResponse.append("		<td><input type='text' id='emp_comm' name='emp_comm' value='" + (employee.getComm() == 0? "" : employee.getComm()) + "'></td>");
		ajaxResponse.append("	</tr>");

		ajaxResponse.append("	<tr>");
		ajaxResponse.append("		<th>DEPT</th>");
		ajaxResponse.append("		<td>");
		ajaxResponse.append("			<select name='emp_deptno' id='emp_deptno'> ");
		for(int i=0;i<deptList.size();i++){
			if(deptList.get(i).getDeptNo()== employee.getDeptno()){
				ajaxResponse.append("<option selected='selected' value='"+ deptList.get(i).getDeptNo()+ "'>"+ deptList.get(i).getdName()+"</option>");
			}else{
				ajaxResponse.append("<option value='"+deptList.get(i).getDeptNo()+"'>"+deptList.get(i).getdName()+"</option>");
			}
		}	
		
		ajaxResponse.append("</select>");
		ajaxResponse.append("		</td>");
		ajaxResponse.append("	</tr>");

		ajaxResponse.append("<tr>");	
		ajaxResponse.append("  <td colspan='2' align='center'>");
		if(form.equals("register")) {
			ajaxResponse.append("<input type='button' id='btnRegister' value='사원등록'>"); 
			ajaxResponse.append("<input type='button' id='btnRegister_cancel' value='취소' onclick='javascript:history.back()'>");
		}else {
			ajaxResponse.append("<input type='button' id='btnRegister' value='수정완료'>"); 
			ajaxResponse.append("<input type='reset' id='btnRegister_cancel'value='취소' onclick='javascript:history.back()'>");
		} 
		
		ajaxResponse.append("		</td>");
		ajaxResponse.append("	</tr>");
		ajaxResponse.append("</table>");
		//ajaxResponse.append("</form>");
		
		req.setAttribute("ajaxResponse", ajaxResponse.toString());
		System.out.println("page:"+ajaxResponse.toString());
		return Constants.AJAX_VIEW;
		
	}
}
