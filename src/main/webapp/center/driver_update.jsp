<%@page import="board.FileBoardDTO"%>
<%@page import="board.FileBoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%
	request.setCharacterEncoding("UTF-8");
	int num = Integer.parseInt(request.getParameter("num"));
	FileBoardDAO fileBoardDAO = new FileBoardDAO();
	FileBoardDTO fileBoard = fileBoardDAO.selectFileBoard(num);
	
	int pageNum = Integer.parseInt(request.getParameter("page"));
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>center/notice_update.jsp</title>
<link href="../css/default.css" rel="stylesheet" type="text/css">
<link href="../css/subpage.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="wrap">
		<!-- 헤더 들어가는곳 -->
		<jsp:include page="../inc/top.jsp" />
		<!-- 헤더 들어가는곳 -->

		<!-- 본문들어가는 곳 -->
		<!-- 본문 메인 이미지 -->
		<div id="sub_img_center"></div>
		<!-- 왼쪽 메뉴 -->
		<nav id="sub_menu">
			<ul>
				<li><a href="notice.jsp">Notice</a></li>
				<li><a href="#">Public News</a></li>
				<li><a href="driver.jsp">Driver Download</a></li>
				<li><a href="#">Service Policy</a></li>
			</ul>
		</nav>
		<!-- 본문 내용 -->
		<article>
			<h1>Notice Update</h1>
			<form action="driver_updatePro.jsp" method="post" enctype="multipart/form-data">
				<input type="hidden" name="num" value="<%=num %>">
				<input type="hidden" name="pageNum" value="<%=pageNum %>">
				<table id="notice">
					<tr>
						<td>글쓴이</td>
						<td><input type="text" name="name" readonly="readonly" value="<%=fileBoard.getName() %>"></td>
					</tr>
					<tr>
						<td>비밀번호</td>
						<td><input type="password" name="pass" required="required" ></td>
					</tr>
					<tr>
						<td>제목</td>
						<td><input type="text" name="subject" required="required" value="<%=fileBoard.getSubject()%>"></td>
					</tr>
					<tr>
						<td>내용</td>
						<td><textarea style="resize: none;" rows="10" cols="22" name="content" required="required"><%=fileBoard.getContent() %></textarea></td>
					</tr>
					<tr>
						<td>파일</td>
						<td><input type="file" name="file" required="required"><%=fileBoard.getOriginal_file() %></td>
					</tr>
				</table>

				<div id="table_search">
					<input type="submit" value="글수정" class="btn">
				</div>
			</form>
			<div class="clear"></div>
		</article>


		<div class="clear"></div>
		<!-- 푸터 들어가는곳 -->
		<jsp:include page="../inc/bottom.jsp" />
		<!-- 푸터 들어가는곳 -->
	</div>
</body>
</html>


