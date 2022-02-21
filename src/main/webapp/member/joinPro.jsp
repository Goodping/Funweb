<%@page import="member.MemberDTO"%>
<%@page import="member.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");

	String id = request.getParameter("id");
	String pass = request.getParameter("pass");
	String pass2 = request.getParameter("pass2");
 	String name = request.getParameter("name");
	String email = request.getParameter("email");
	String mobile = request.getParameter("mobile");
	
	String address = request.getParameter("address");
	String phone = request.getParameter("phone");

	
	MemberDAO dao = new MemberDAO();
	
	MemberDTO member = new MemberDTO();
	
	member.setName(name);
	member.setPass(pass);
	member.setId(id);
	member.setEmail(email);
	member.setPhone(phone);
	member.setMobile(mobile);
	member.setAddress(address);
	
	int insertCount = dao.insertMember(member);
	
	if(insertCount > 0)
	{
		response.sendRedirect("joinSuccess.jsp");
	}
	else
	{
	%>
		<script type="text/javascript">
			alert("회원 가입 실패");
			history.back();
		</script>
	<%	
	}
%>