package GussingGame;
import java.util.*;
import java.sql.*;
public class LoginDao {
	String driver="com.mysql.cj.jdbc.Driver";
	String url="jdbc:mysql://localhost:3306/gussingGame";
	String username="root";
	String password="root123";
	String query = "SELECT * FROM signup WHERE username=? AND passwords=?";

	public boolean checkUser(String user,String pass)
	{
		try
		{
			Class.forName(driver);
			Connection con=DriverManager.getConnection(url, username, password);
			PreparedStatement pmt=con.prepareStatement(query);
			pmt.setString(1, user);
			pmt.setString(2, pass);
			ResultSet rs=pmt.executeQuery();
			if(rs.next())
			{
				return true;
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return false;
	}

}
