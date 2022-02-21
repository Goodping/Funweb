<%@page import="java.util.ArrayList"%>
<%@page import="board.ReplyDAO"%>
<%@page import="board.ReplyDTO"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="board.BoardDTO"%>
<%@page import="board.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>center/notice_content.jsp</title>
	
<%
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

	BoardDAO boardDAO = new BoardDAO();

	BoardDTO board = boardDAO.selectBoard(num);
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	ReplyDAO replyDAO = new ReplyDAO();
	
	ArrayList<ReplyDTO> replyList = replyDAO.selectReplyList(num);
	
	
%>



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
			<h1>Notice Content</h1>
			<table id="notice">
				<tr>
					<td>글번호</td>
					<td><%=num %></td>
					<td>글쓴이</td>
					<td><%=board.getName()%></td>
				</tr>
				<tr>
					<td>작성일</td>
					<td><%=sdf.format(board.getDate()) %></td>
					<td>조회수</td>
					<td><%=board.getReadcount() %></td>
				</tr>
				<tr>
					<td>제목</td>
					<td colspan="3"><%=board.getSubject() %></td>
				</tr>
				<tr>
					<td>내용</td>
					<td colspan="3">
					<textarea style="resize: none;" rows="10" cols="22" name="content" readonly="readonly">
						<%=board.getContent() %>
					</textarea></td>
				</tr>
			</table>
			
			<div>
				<h3>댓글쓰기</h3>
				<%
				if(session.getAttribute("sessionId") != null)
				{
				%>
				<form action="notice_content_reply.jsp" method="post">
					<input type="hidden" name="contentNum" value="<%=num%>">
					<input type="hidden" name="pageNum" value="<%=request.getParameter("page")%>">
					<textarea rows="4" cols="90" style="resize: none;" name="reply" required="required"></textarea>
					<br>
					<input type="submit" value="댓글작성">
				</form>
				<% 
				}
				%>
				</div>
			<hr>
			<table border="1">
			<%
			if(replyList != null && replyList.size() > 0)
			{
				for(ReplyDTO reply : replyList)
				{
			%>
				<tr>
					<td width="255">댓글 작성자</td>
					<td width="255"> <%=reply.getId()%></td>
					<td width="255">댓글 작성일</td>
					<td width="255"> <%=sdf.format(reply.getDate()) %></td>	
				</tr>
				<tr>
					<td colspan="4"><%= reply.getReply() %></td>
				</tr>
			<%
				}
			}
			else
			{
			%>	
				<tr><td colspan="5">작성된 댓글이 없습니다.</td></tr>
			<%
			}
			%>
			</table>
			
			
			<div id="table_search">
			<%
				if(session.getAttribute("sessionId") != null)
				{
					if(session.getAttribute("sessionId").equals(board.getName()))
					{
					%>
						<input type="button" value="수정" class="btn" onclick="location.href='notice_update.jsp?num=<%=num%>&page=<%=request.getParameter("page")%>'"> 
						<input type="button" value="삭제" class="btn" onclick="location.href='notice_delete.jsp?num=<%=num%>'">
					<%
					}
				}
			%>
				<input type="button" value="목록" class="btn" onclick="location.href='notice.jsp?page=<%=request.getParameter("page")%>'">
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


