<%@page import="board.FileBoardDTO"%>
<%@page import="board.FileBoardDAO"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%
	request.setCharacterEncoding("UTF-8");
	int num = 0;

	if(request.getParameter("num") == null)
	{
	%>
	<script type="text/javascript">
		alert("잘못된 접근입니다.");
		history.back();
	</script>
	<%
	}
	else 
	{
		num = Integer.parseInt(request.getParameter("num"));
	}

	FileBoardDAO fileBoardDAO = new FileBoardDAO();

	FileBoardDTO fileBoard = fileBoardDAO.selectFileBoard(num);
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>center/notice_content.jsp</title>
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
			<h1>Driver Content</h1>
			<table id="notice">
				<tr>
					<td>글번호</td>
					<td><%=num %></td>
					<td>글쓴이</td>
					<td><%=fileBoard.getName()%></td>
				</tr>
				<tr>
					<td>작성일</td>
					<td><%=sdf.format(fileBoard.getDate()) %></td>
					<td>조회수</td>
					<td><%=fileBoard.getReadcount() %></td>
				</tr>
				<tr>
					<td>제목</td>
					<td colspan="3"><%=fileBoard.getSubject() %></td>
				</tr>
				<tr>
					<td>파일</td>
					<td colspan="3">
						<%=fileBoard.getOriginal_file() %> &nbsp;&nbsp;
						<a href="../upload/<%=fileBoard.getfile()%>" download="<%=fileBoard.getOriginal_file()%>"><input type="button" value="다운로드"></a>
					</td>
				</tr>
				<tr>
					<td>내용</td>
					<td colspan="3">
					<textarea style="resize: none;" rows="10" cols="22" name="content" readonly="readonly">
						<%=fileBoard.getContent() %>
					</textarea></td>
				</tr>
			</table>

			<div id="table_search">
			<%
				if(session.getAttribute("sessionId") != null)
				{
					if(session.getAttribute("sessionId").equals(fileBoard.getName()))
					{
					%>
						<input type="button" value="글수정" class="btn" onclick="location.href='driver_update.jsp?num=<%=num%>&page=<%=request.getParameter("page")%>'"> 
						<input type="button" value="글삭제" class="btn" onclick="location.href='driver_delete.jsp?num=<%=num%>'"> 
					<%
					}
				}
			%>	
				<input type="button" value="글목록" class="btn" onclick="location.href='driver.jsp?page=<%=request.getParameter("page")%>'">
			</div>

			<div class="clear"></div>
		</article>

		<div class="clear"></div>
		<!-- 푸터 들어가는곳 -->
		<jsp:include page="../inc/bottom.jsp" />
		<!-- 푸터 들어가는곳 -->
	</div>
</body>
</html>


