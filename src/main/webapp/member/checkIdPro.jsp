<%@page import="member.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");

	String id = request.getParameter("id");
	
	MemberDAO memberDAO = new MemberDAO();
	
	boolean checkId = memberDAO.checkId(id);
	
	response.sendRedirect("checkId.jsp?id="+id+"&result="+checkId);
	
%> 