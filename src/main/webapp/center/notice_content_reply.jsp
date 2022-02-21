<%@page import="board.ReplyDTO"%>
<%@page import="board.ReplyDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%

	request.setCharacterEncoding("UTF-8");

	int contentNum = Integer.parseInt(request.getParameter("contentNum"));
	String contentReply = request.getParameter("reply");
	String id = (String) session.getAttribute("sessionId");
	int pageNum = Integer.parseInt(request.getParameter("pageNum"));

	ReplyDAO replyDAO = new ReplyDAO();

	ReplyDTO reply = new ReplyDTO();

	reply.setContentnum(contentNum);
	reply.setId(id);
	reply.setReply(contentReply);

	int insertCount = replyDAO.insertReply(reply);

	if (insertCount > 0) {
		response.sendRedirect("notice_content.jsp?num=" + contentNum + "&page=" + pageNum);
	} else {
%>
		<script>
			alert('댓글작성 실패!');
			history.back();
		</script>
<%	}
%>
