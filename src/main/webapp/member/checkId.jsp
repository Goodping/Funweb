<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>



<script type="text/javascript">
	
	function idJoin(id, check) {

		if (check) {
			opener.parent.checkDuplicateId();
			opener.document.fr.id.value=id;
			window.close();		
		}
		else {
			alert("아이디 중복 확인을 해주세요");
		}
	}
</script>

<%
	String id = null;
	boolean check = false;
	request.setCharacterEncoding("UTF-8");
	if(request.getParameter("id") != null)
	{
		id = request.getParameter("id");
		if(request.getParameter("result").equals("false")) {
		%>
			<script type="text/javascript">
				alert("사용 가능한 아이디 입니다.");
			</script>
		<%	
			check = true;
		
		} else {
			%>
			<script type="text/javascript">
				alert("이미 사용중인 아이디 입니다.");
			</script>
		<%
		}
	}
%>
</head>
<body>

	<br><br>
 <form action="checkIdPro.jsp" method="post">
	 ID <input type="text" name="id" value="<%if(id != null){%><%=id%><%}%>">
	 <input type="submit" value="중복확인">
 </form>
 	<br>
 	<br>
 	 &nbsp;&nbsp;&nbsp;&nbsp;
	<input type="button" value="확인" onclick="idJoin('<%=id %>', <%=check%>)">
 	<input type="button" value="닫기" onclick="window.close()">
</body>
</html>