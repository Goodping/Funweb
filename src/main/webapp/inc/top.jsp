<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	String sessionId = (String) session.getAttribute("sessionId");
%>    
    
<header>
  <!-- login join -->
  <%if(sessionId == null)
	{
	%>
		<div id="login"><a href="../member/login.jsp">login</a> | <a href="../member/join.jsp">join</a></div>
	<%
	}
	else
	{%>
		<div id="login"><a href="#"><%= sessionId %>님</a> | <a href="../member/logout.jsp">로그아웃</a></div>
	<%
	}
	%>  
  <div class="clear"></div>
  <!-- 로고들어가는 곳 -->
  <div id="logo"><a href="../main/main.jsp"><img src="../images/logo.jpg"></a></div>
  <!-- 메뉴들어가는 곳 -->
  <nav id="top_menu">
  	<ul>
  		<li><a href="../main/main.jsp">HOME</a></li>
  		<li><a href="../company/welcome.jsp">COMPANY</a></li>
  		<li><a href="../company/welcome.jsp">SOLUTIONS</a></li>
  		<li><a href="../center/notice.jsp">CUSTOMER CENTER</a></li>
  		<li><a href="../mail/mailForm.jsp">CONTACT US</a></li>
  	</ul>
  </nav>
</header>