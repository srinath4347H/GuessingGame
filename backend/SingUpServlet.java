package GussingGame;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import jakarta.servlet.RequestDispatcher;

@WebServlet("/registration")
public class SingUpServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        PrintWriter out = response.getWriter();


        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/gussingGame";
        String user = "root";
        String pass = "root123";
        String query = "INSERT INTO signup(username, email, passwords) VALUES (?, ?, ?)";

        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            Class.forName(driver);
            Connection con = DriverManager.getConnection(url, user, pass);
            PreparedStatement pmt = con.prepareStatement(query);

            pmt.setString(1, username);
            pmt.setString(2, email);
            pmt.setString(3, password);

            int count = pmt.executeUpdate();

            if (count > 0) {
                out.println("<h1>Registration Succesful</h1>");
                out.println("<a href='Welcome.jsp'>🏠 Home Page</a><br>");
                out.println("<a href='Login.jsp'>🔐 Login Page</a>");

            } else {
                out.println("<h1>Registration Failed</h1>");
            }

            pmt.close();
            con.close();

        } catch (Exception e) {
            out.println("<h1>User name Already Exist please try to use another user name</h1>");
        } finally {
            out.close();
        }
       
        
    }
}
