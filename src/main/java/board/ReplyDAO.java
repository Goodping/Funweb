package board;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReplyDAO {

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
	
	
	public ArrayList<ReplyDTO> selectReplyList(int contentNum)
	{
		con = getConnection();
		
		ArrayList<ReplyDTO> replyList =  null;
		
		try 
		{
			
			String sql = "SELECT * FROM reply WHERE contentnum=? ORDER BY date DESC";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, contentNum);
			
			rs = pstmt.executeQuery();
			
			replyList = new ArrayList<ReplyDTO>();
			
			while(rs.next()) 
			{ 
				ReplyDTO reply = new ReplyDTO();
				
				reply.setContentnum(rs.getInt("contentnum"));
				reply.setReplynum(rs.getInt("replynum"));
				reply.setId(rs.getString("id"));
				reply.setReply(rs.getString("reply"));
				reply.setDate(rs.getDate("date"));
				
				replyList.add(reply);
			}
	
		} catch (SQLException e) {
			System.out.println("SQL 구문 오류 발생!");
			e.printStackTrace();
		}	
		finally
		{
			close(rs);
			close(pstmt);
			close(con);
		}
		
		return replyList;
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
			String sql = "SELECT COUNT(*) FROM reply";
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
	
	public int insertReply(ReplyDTO reply)
	{
		int insertCount = 0;
		
		con = getConnection();
		
		try 
		{
			String sql = "SELECT MAX(replynum) FROM reply";
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next())
			{
				reply.setReplynum(rs.getInt("MAX(replynum)")+1);
			}
			else
			{
				reply.setReplynum(1);
			}
			
			sql = "INSERT INTO reply VALUES(?,?,?,?,now())";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, reply.getContentnum());
			pstmt.setInt(2, reply.getReplynum());
			pstmt.setString(3, reply.getId());
			pstmt.setString(4, reply.getReply());
			
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
	
}
