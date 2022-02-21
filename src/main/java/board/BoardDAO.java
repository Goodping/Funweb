package board;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BoardDAO {

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
	
	public int insertBoard(BoardDTO board)
	{
		int insertCount = 0;
		
		con = getConnection();
		
		try 
		{
			String sql = "SELECT MAX(num) FROM board";
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next())
			{
				board.setNum(rs.getInt("MAX(num)")+1);
			}
			else
			{
				board.setNum(1);
			}
			
			sql = "INSERT INTO board VALUES(?,?,?,?,?,now(),0)";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, board.getNum());
			pstmt.setString(2, board.getName());
			pstmt.setString(3, board.getPass());
			pstmt.setString(4, board.getSubject());
			pstmt.setString(5, board.getContent());
			
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
	
	public ArrayList<BoardDTO> selectBoardList(int pageNum, int listLimit)
	{
		con = getConnection();
		
		ArrayList<BoardDTO> boardList =  null;
		
		try 
		{
			int startRow = (pageNum - 1) * listLimit;
			
			String sql = "SELECT * FROM board ORDER BY num DESC LIMIT ?,?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, listLimit);
			
			rs = pstmt.executeQuery();
			
			boardList = new ArrayList<BoardDTO>();
			
			while(rs.next()) 
			{ 
				BoardDTO board = new BoardDTO();
				
				board.setNum(rs.getInt("num"));
				board.setName(rs.getString("name"));
				board.setSubject(rs.getString("subject"));
				board.setDate(rs.getDate("date"));
				board.setReadcount(rs.getInt("readcount"));
				
				boardList.add(board);
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
		
		return boardList;
	}

	public BoardDTO selectBoard(int num)
	{

		con = getConnection();
	
		BoardDTO board = null;
		try 
		{
			String sql = "UPDATE board SET readcount=readcount+1 WHERE num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
		
			pstmt.executeUpdate();
		
			sql = "SELECT * FROM board WHERE num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
		
			rs = pstmt.executeQuery();
		
			if(rs.next()) 
			{		
				board = new BoardDTO();
				board.setName(rs.getString("name"));
				board.setDate(rs.getDate("date"));
				board.setReadcount(rs.getInt("readcount"));
				board.setSubject(rs.getString("subject"));
				board.setContent(rs.getString("content"));
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
	
		return board;
	}
	
	public int deleteBoard(String pass, int num) 
	{
		int deleteCount = 0;

		con = getConnection();
		
		try 
		{
			String sql = "SELECT pass FROM board WHERE num=?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) 
			{
				if(pass.equals(rs.getString("pass"))) 
				{
				
					sql = "DELETE FROM board WHERE num=?";
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
	
	public int updateBoard(int num, String pass, String subject, String content)
	{
		int updateCount = 0;
		
		con = getConnection();
		
		try 
		{
			String sql = "SELECT pass FROM board WHERE num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) 
			{ 
				if(pass.equals(rs.getString("pass"))) 
				{
					sql = "UPDATE board SET subject=?,content=? WHERE num=?";
					
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, subject);
					pstmt.setString(2, content);
					pstmt.setInt(3, num);
					
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
	
	public int getListCount() 
	{
		int listCount = 0;
		
		con = getConnection();
		
		try
		{
			String sql = "SELECT COUNT(*) FROM board";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) 
			{
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
	
	public ArrayList<BoardDTO> searchBoardList(int pageNum, int listLimit, String search)
	{
		con = getConnection();
		
		ArrayList<BoardDTO> boardList =  null;
		System.out.println(search);
		
		try 
		{
			int startRow = (pageNum - 1) * listLimit;
			
			String sql = "SELECT * FROM board WHERE subject LIKE ? ORDER BY num DESC LIMIT ?,?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%"+search+"%");
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, listLimit);
			
			rs = pstmt.executeQuery();
			
			boardList = new ArrayList<BoardDTO>();
			
			while(rs.next()) 
			{ 
				BoardDTO board = new BoardDTO();
				
				board.setNum(rs.getInt("num"));
				board.setName(rs.getString("name"));
				board.setSubject(rs.getString("subject"));
				board.setDate(rs.getDate("date"));
				board.setReadcount(rs.getInt("readcount"));
				
				boardList.add(board);
			}
	
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}	
		finally
		{
			close(rs);
			close(pstmt);
			close(con);
		}
		
		return boardList;
	}
	
	public int searchGetListCount(String search) 
	{
		int listCount = 0;
		
		con = getConnection();
		
		try
		{
			String sql = "SELECT COUNT(*) FROM board WHERE subject LIKE CONCAT('%',?,'%')";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, search);
			rs = pstmt.executeQuery();
			
			if(rs.next()) 
			{
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
}
