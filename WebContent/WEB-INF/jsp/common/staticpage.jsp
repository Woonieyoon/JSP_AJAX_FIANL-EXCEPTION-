<%@page import="com.sys4u.company.dto.Pagination"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%
	Pagination empPage = (Pagination) request.getAttribute("page");
%>

<tr align="center">
	<td colspan="4">
		<input type="hidden" id="totalPageCount" value="<%=empPage.getTotalPageCount()%>">
		<span id="first" style="cursor: pointer;">◀◀</span>
		<span id="prev" style="cursor: pointer;">◀</span>
		
		<%
			int endPage = empPage.getEndPageIndex() >= empPage.getTotalPageCount() ? empPage.getTotalPageCount() : empPage.getEndPageIndex();
			for(int i=empPage.getStartPageIndex(); i<= endPage ; i++){			
		%>
		<span class="pages" id="page-<%=i%>" page="<%=i%>" style="cursor: pointer;"><%=i%></span>		
		<%}%>
		<span id="next" style="cursor: pointer;">▶</span>
		<span id="end" style="cursor: pointer;">▶▶</span>
	</td>
</tr>    