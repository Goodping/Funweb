<%@page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@page import="board.FileBoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	

	String uploadPath = "/upload";
	
	int fileSize = 1024 * 1024 * 10;

	ServletContext context = request.getServletContext();

	String realPath = context.getRealPath(uploadPath);

	MultipartRequest multi = new MultipartRequest(
			request, realPath, fileSize, "UTF-8", new DefaultFileRenamePolicy()
			);
	
	int pageNum = Integer.parseInt(multi.getParameter("pageNum"));
	
	int num = Integer.parseInt(multi.getParameter("num"));
	String pass = multi.getParameter("pass");
	String subject = multi.getParameter("subject");
	String content = multi.getParameter("content");

	FileBoardDAO fileBoardDAO = new FileBoardDAO();
	
	String fileElement = multi.getFileNames().nextElement().toString();
	
	String original_file = multi.getOriginalFileName(fileElement);
	String file = multi.getFilesystemName(fileElement);	
	
	int updateCount = fileBoardDAO.fileUpdateBoard(num, pass, subject, content, original_file, file);
	
	if(updateCount > 0) 
	{
		response.sendRedirect("driver_content.jsp?num=" + num + "&page=" + pageNum);
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