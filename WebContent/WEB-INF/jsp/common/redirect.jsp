<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%
	String redirectUrl = (String)request.getAttribute("redirectUrl");
	String message = (String)request.getAttribute("message");
%><script>
	if('<%=message%>' !== ''){
		alert('<%=message%>');		
	}
	document.location.replace('<%=redirectUrl%>');
</script>