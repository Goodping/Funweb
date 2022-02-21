<%@page import="board.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	
	int num = Integer.parseInt(request.getParameter("num"));
	int pageNum = Integer.parseInt(request.getParameter("pageNum")); 
	String pass = request.getParameter("pass");
	String subject = request.getParameter("subject");
	String content = request.getParameter("content");

	BoardDAO boardDAO = new BoardDAO();
	
	int updateCount = boardDAO.updateBoard(num, pass, subject, content);
	
	if(updateCount > 0) 
	{
		response.sendRedirect("notice_content.jsp?num=" + num + "&page=" + pageNum);
	} 
	else 
	{ 
	%>
	<script type="text/javascript">
		alert("글 수정 실패!");
		history.back();
	</script>
	<%
	}
%>