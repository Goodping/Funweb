<%@page import="member.MemberDTO"%>
<%@page import="member.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	request.setCharacterEncoding("UTF-8");

	MemberDTO member = new MemberDTO();
	member.setId(request.getParameter("id"));
	member.setPass(request.getParameter("pass"));
	
	MemberDAO memberDAO = new MemberDAO();
	
	boolean isLoginSuccess = memberDAO.checkUser(member);
	
	
	if(isLoginSuccess)
	{	
		session.setAttribute("sessionId", member.getId());
		response.sendRedirect("../main/main.jsp");
	}	
	else
	{
	%>
		<script type="text/javascript">
			alert("로그인 실패");
			history.back();
		</script>
	<%
	}
%>
