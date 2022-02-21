<%@page import="board.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int num = Integer.parseInt(request.getParameter("num"));
	String pass = request.getParameter("pass");
	
	BoardDAO dao = new BoardDAO();
	int deleteCount = dao.deleteBoard(pass, num);
	
	if(deleteCount > 0) { 
		response.sendRedirect("notice.jsp");
	} else 
	{ 
		%>
		<script type="text/javascript">
			alert("글 삭제 실패!");
			history.back();
		</script>
		<%
	}
%>