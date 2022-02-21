<%@page import="board.FileBoardDAO"%>
<%@page import="board.FileBoardDTO"%>
<%@page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
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
	
	FileBoardDTO fileBoard = new FileBoardDTO();
	FileBoardDAO fileBoardDAO = new FileBoardDAO();
	
	fileBoard.setName(multi.getParameter("name"));
	fileBoard.setPass(multi.getParameter("pass"));
	fileBoard.setSubject(multi.getParameter("subject"));
	fileBoard.setContent(multi.getParameter("content"));
	
	String fileElement = multi.getFileNames().nextElement().toString();
	
	String original_file = multi.getOriginalFileName(fileElement);
	String file = multi.getFilesystemName(fileElement);
	
	fileBoard.setOriginal_file(original_file);
	fileBoard.setfile(file);
	
	int insertCount = fileBoardDAO.insertFileBoard(fileBoard);
	
	if(insertCount > 0)
	{ 
		response.sendRedirect("driver.jsp");
	}
	else
	{
%>
	<script>
		alert('글쓰기 실패!');
		history.back();
	</script>
<%	
}
	
%>