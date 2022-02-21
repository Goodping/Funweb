package member;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberDAO {

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
	
	public int insertMember(MemberDTO member)
	{
		int insertCount = 0;
		
		con = getConnection();
		
		try {
			String sql = "INSERT INTO member VALUES (?,?,?,now(),?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member.getId()); 
			pstmt.setString(2, member.getPass()); 
			pstmt.setString(3, member.getName()); 
			pstmt.setString(4, member.getEmail()); 
			pstmt.setString(5, member.getMobile());
			pstmt.setString(6, member.getAddress());
			pstmt.setString(7, member.getPhone());
			insertCount = pstmt.executeUpdate();
		} 
		
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		finally
		{
			close(pstmt);
			close(con);
		}
		return insertCount;
	}
	
	public boolean checkUser(MemberDTO member)
	{
		boolean isLoginSuccess = false;
		
		con = getConnection();
		
		try 
		{
			String sql = "SELECT * FROM member WHERE id=? AND pass=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getPass());
			
			rs = pstmt.executeQuery();
			
			if(rs.next())
			{
				isLoginSuccess = true;
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
		
		return isLoginSuccess; 
	}
	
	public boolean checkId(String id)
	{
		boolean checkId = false;
		con = getConnection();
		
		try 
		{
			String sql = "SELECT * FROM member WHERE id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);

			
			rs = pstmt.executeQuery();
			
			if(rs.next())
			{
				checkId = true;
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
		
		
		return checkId;
	}
}
