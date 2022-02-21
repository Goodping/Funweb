package board;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FileBoardDAO {

	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	
	public Connection getConnection()
	{	
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/funweb";
		String dbUser = "root";
		String dbPassword = "1234";
		
		try 
		{
			Class.forName(driver);
			
			con = DriverManager.getConnection(url, dbUser, dbPassword);
		} 
		catch (ClassNotFoundException e) 
		{
			System.out.println("드라이버 클래스 로드 실패!");
			e.printStackTrace();
		}
		catch (SQLException e) 
		{
			System.out.println("DB 연결 실패!");
			e.printStackTrace();
		}
		
		return con;
	}
	
	public void close(Connection con)
	{
		if(con != null)
		{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void close(PreparedStatement pstmt)
	{	
		if(pstmt != null)
		{
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void close(ResultSet rs)
	{	
		if(rs != null)
		{
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public int insertFileBoard(FileBoardDTO fileBoard)
	{
		int insertCount = 0;
		
		con = getConnection();
		
		try 
		{
			String sql = "SELECT MAX(num) FROM file_board";
			
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next())
			{
				fileBoard.setNum(rs.getInt("MAX(num)")+1);
			}
			else
			{
				fileBoard.setNum(1);
			}
			
			sql = "INSERT INTO file_board VALUES(?,?,?,?,?,?,?,now(),0)";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, fileBoard.getNum());
			pstmt.setString(2, fileBoard.getName());
			pstmt.setString(3, fileBoard.getPass());
			pstmt.setString(4, fileBoard.getSubject());
			pstmt.setString(5, fileBoard.getContent());
			pstmt.setString(6, fileBoard.getfile());
			pstmt.setString(7, fileBoard.getOriginal_file());
			
			insertCount = pstmt.executeUpdate();
			
		} 
		catch (SQLException e1) 
		{
			e1.printStackTrace();
		}
		finally
		{
			close(rs);
			close(pstmt);
			close(con);
		}		
		return insertCount;
	}
	
	public ArrayList<FileBoardDTO> selectFileBoardList(int pageNum, int listLimit)
	{
		con = getConnection();
		
		ArrayList<FileBoardDTO> fileBoardList =  null;
		
		try 
		{
			int startRow = (pageNum - 1) * listLimit;
			
			String sql = "SELECT * FROM file_board ORDER BY num DESC LIMIT ?,?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, listLimit);
			
			rs = pstmt.executeQuery();
			
			fileBoardList =  new ArrayList<FileBoardDTO>();
			
			while(rs.next()) 
			{ 
				FileBoardDTO fileBoard = new FileBoardDTO();
				
				fileBoard.setNum(rs.getInt("num"));
				fileBoard.setName(rs.getString("name"));
				fileBoard.setSubject(rs.getString("subject"));
				fileBoard.setDate(rs.getDate("date"));
				fileBoard.setReadcount(rs.getInt("readcount"));
				
				fileBoardList.add(fileBoard);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}	
		finally
		{
			close(rs);
			close(pstmt);
			close(con);
		}
		
		return fileBoardList;
	}
	
	
	public ArrayList<FileBoardDTO> selectFileBoardList()
	{
		con = getConnection();
		
		ArrayList<FileBoardDTO> fileBoardList =  new ArrayList<FileBoardDTO>();
		
		try 
		{
			String sql = "SELECT * FROM file_board ORDER BY num DESC";
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) 
			{ 
				FileBoardDTO fileBoard = new FileBoardDTO();
				
				fileBoard.setNum(rs.getInt("num"));
				fileBoard.setName(rs.getString("name"));
				fileBoard.setSubject(rs.getString("subject"));
				fileBoard.setDate(rs.getDate("date"));
				fileBoard.setReadcount(rs.getInt("readcount"));
				
				fileBoardList.add(fileBoard);
			}
			return fileBoardList;
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		finally
		{
			close(rs);
			close(pstmt);
			close(con);
		}
		
		return null;
	}
	
	public int getListCount() 
	{
		int listCount = 0;
		
		con = getConnection();
		
		try
		{
			String sql = "SELECT COUNT(*) FROM file_board";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				listCount = rs.getInt(1);
			}
		} 
		catch (SQLException e) 
		{
			System.out.println("SQL 구문 오류 발생!");
			e.printStackTrace();
		} 
		finally 
		{
			close(rs);
			close(pstmt);
			close(con);
		}
		
		return listCount;
	}

	
	public FileBoardDTO selectFileBoard(int num)
	{
		FileBoardDTO fileBoardDTO = null;
		
		con = getConnection();
		
		try 
		{
			String sql = "UPDATE file_board SET readcount=readcount+1 WHERE num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
		
			pstmt.executeUpdate();
		
			sql = "SELECT * FROM file_board WHERE num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
		
			rs = pstmt.executeQuery();
		
			if(rs.next()) 
			{		
				fileBoardDTO = new FileBoardDTO();
				fileBoardDTO.setName(rs.getString("name"));
				fileBoardDTO.setSubject(rs.getString("subject"));
				fileBoardDTO.setContent(rs.getString("content"));
				fileBoardDTO.setfile(rs.getString("file"));
				fileBoardDTO.setOriginal_file(rs.getString("original_file"));
				fileBoardDTO.setReadcount(rs.getInt("readcount"));
				fileBoardDTO.setDate(rs.getDate("date"));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			close(rs);
			close(pstmt);
			close(con);
		}
		
		
		return fileBoardDTO;
	}
	
	public int fileDeleteBoard(String pass, int num) 
	{
		int deleteCount = 0;

		con = getConnection();
		
		try 
		{
			String sql = "SELECT pass FROM file_board WHERE num=?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) 
			{
				if(pass.equals(rs.getString("pass"))) 
				{
				
					sql = "DELETE FROM file_board WHERE num=?";
					pstmt = con.prepareStatement(sql);
					pstmt.setInt(1, num); 
					
					deleteCount = pstmt.executeUpdate();
				}
			}
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally 
		{
			close(rs);
			close(pstmt);
			close(con);
		}
		
		return deleteCount;
	}
	
	public int fileUpdateBoard(int num, String pass, String subject, String content, String original_file, String file)
	{
		int updateCount = 0;
		
		con = getConnection();
		
		try 
		{
			String sql = "SELECT pass FROM file_board WHERE num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) 
			{ 
				if(pass.equals(rs.getString("pass"))) 
				{
					sql = "UPDATE file_board SET subject=?,content=?, original_file=?, file =? WHERE num=?";
					
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, subject);
					pstmt.setString(2, content);
					pstmt.setString(3, original_file);
					pstmt.setString(4, file);
					pstmt.setInt(5, num);
					
					updateCount = pstmt.executeUpdate();
				}	
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		} 
		finally
		{
			close(rs);
			close(pstmt);
			close(con);
		}	
		return updateCount;
	}
	
}
