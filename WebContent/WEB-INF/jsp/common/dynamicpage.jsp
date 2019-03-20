<%@page import="com.sys4u.company.dto.Pagination"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%
Pagination pagiNation = (Pagination) request.getAttribute("page");
int startPageIndex = pagiNation.getStartPageIndex();
int endPage = pagiNation.getEndPageIndex() >= pagiNation.getTotalPageCount() ? pagiNation.getTotalPageCount() : pagiNation.getEndPageIndex();
int totalPageCount = pagiNation.getTotalPageCount();
%>

<tr align="center">
	<td colspan="4">
		 <input type="hidden" id="totalPageCount" value="<%=totalPageCount%>"> 
		 <span id="first" style="cursor: pointer;">◀◀</span> 
		 <span id="prev" style="cursor: pointer;">◀</span>
		<%
		 	for (int i = startPageIndex; i <= endPage; i++) {
		%> 
		<span class="pages" id="page-<%=i%>" page="<%=i%>" style="cursor: pointer;">[<%=i%>]</span>
		 <%} %>
		<span id="next" style="cursor: pointer;">▶</span>
		 <span id="end" style="cursor: pointer;">▶▶</span>
	</td>
</tr>
