<%@page import="board.FileBoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	int num = Integer.parseInt(request.getParameter("num"));
	String pass = request.getParameter("pass");
	
	FileBoardDAO dao = new FileBoardDAO();
	int deleteCount = dao.fileDeleteBoard(pass, num);
	
	if(deleteCount > 0) { 
		response.sendRedirect("driver.jsp");
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