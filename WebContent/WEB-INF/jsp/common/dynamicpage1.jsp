<%@page import="com.sys4u.company.dto.Pagination"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	Pagination pageObject = (Pagination) request.getAttribute("page");
//String domain = (String)request.getAttribute("domain");
String domain = (String)request.getParameter("domain");
//String url = request.getParameter("url");
int startPageIndex = pageObject.getStartPageIndex();
int endPage = pageObject.getEndPageIndex() >= pageObject.getTotalPageCount() ? pageObject.getTotalPageCount() : pageObject.getEndPageIndex();
int totalPageCount = pageObject.getTotalPageCount();
%>
<%-- <script type="text/javascript" src="/bbs/static-root/js/common/dynamicpage.js"></script> --%>

<tr align="center">
	<td colspan="4">
		<button id="btn<%=domain%>">hello</button> <input type="hidden"
		id="<%=domain%>totalPageCount" value="<%=totalPageCount%>"> <span
		id="<%=domain%>first" style="cursor: pointer;">◀◀</span> <span
		id="<%=domain%>prev" style="cursor: pointer;">◀</span> <%
			for (int i = startPageIndex; i <= endPage; i++) {
		%> <span class="pages" id="<%=domain%>page-<%=i%>" page="<%=i%>" style="cursor: pointer;"><%=i%></span> <%} %> <span id="<%=domain%>next"
		style="cursor: pointer;">▶</span> <span id="<%=domain%>end"
		style="cursor: pointer;">▶▶</span>
	</td>
</tr>

<script>
	
	var getParameter = function (name){
		   if(name=(new RegExp('[?&]'+encodeURIComponent(name)+'=([^&]*)')).exec(window.location.search)) {
			   return decodeURIComponent(name[1]);
		   }
		   return "";
	};
		
	var goPage = function(pageNum){
		var searchKey = getParameter('searchKey');
		var searchValue = getParameter('searchValue');
		var transportUrl = document.getElementById('transportUrl').value;
	
		location.href = transportUrl + "?searchKey=" + searchKey + "&searchValue=" + searchValue + "&pageNum=" + pageNum; 
	}

	var pages = document.getElementsByClassName('pages');

	for(var i=0;i<pages.length;i++){
		pages[i].onclick = function(){
			var pageNum = this.getAttribute('page');
			var toPage = isNaN(parseInt(pageNum)) ? 1 : parseInt(pageNum);
			return goPage(toPage); 
		};
	}
	
	document.getElementById('<%=domain%>first').onclick = function(){
			return goPage(1);
	};
	
	document.getElementById('<%=domain%>prev').onclick = function(){
			var pageNum = getParameter('pageNum');
			var toPage = isNaN(parseInt(pageNum)) ? 1:parseInt(pageNum);
			toPage = toPage == 1 ? 1 : toPage - 1;
			return goPage(toPage);
	};
	
	document.getElementById('<%=domain%>next').onclick = function(){
			var pageNum = getParameter('pageNum');
			var toPage = isNaN(parseInt(pageNum)) ? 1 : parseInt(pageNum);
			var totalPageCount = document.getElementById('<%=domain%>totalPageCount').value;
			toPage = toPage >= totalPageCount ? totalPageCount : toPage+1;
			return goPage(toPage);
	};
	
	document.getElementById('<%=domain%>end').onclick = function(){
		var toPage = document.getElementById('<%=domain%>totalPageCount').value;
		return goPage(toPage);
	};

	var Page = function(url,domain,pageGo){
		this.url = url,
		this.domain = domain,
		this.GoPage = pageGo
	}

	
	 //var <%=domain%>Page = new Page(url, domain, <%=domain%>GoPage);
	//var <%=domain%>Page = new Page("<%=domain%>", "<%=domain%>", "<%=domain%>");
	//document.getElementById("btn<%=domain%>").onclick = <%=domain%>GoPage;
		
	
</script>