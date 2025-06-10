package GussingGame;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

@WebServlet("/LogicServlet")
public class LogicServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
    	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    	response.setHeader("Pragma", "no-cache");
    	response.setDateHeader("Expires", 0);

    	response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();

        // Get generated number safely
        Integer generated = (Integer) session.getAttribute("generated");
        if (generated == null) {
            generated = new Random().nextInt(100) + 1;
            session.setAttribute("generated", generated);
            session.setAttribute("attempt", 0);
        }

        
        int guess = Integer.parseInt(request.getParameter("guessnumber"));
        int attempt = (int) session.getAttribute("attempt");
        attempt++;
        session.setAttribute("attempt", attempt);

        // HTML Response
        out.println("<html><body style='text-align:center; font-family:sans-serif;'>");

        if (guess == generated) {
            int score = 10 - attempt + 1;
            out.println("<h2 style='color:green;'>🎉 Congratulations! You guessed it right: " + generated + "</h2>");
            out.println("<h3>Your Score: " + score + "</h3>");
            session.invalidate(); // Reset game
        } else if (attempt >= 10) {
            out.println("<h2 style='color:red;'>❌ Game Over! You've used all 10 attempts.</h2>");
            out.println("<h3>The correct number was: " + generated + "</h3>");
            session.invalidate();
        } else if (guess > generated) {
            out.println("<h2>🔻 Too High! Try again.</h2>");
            out.println("<h3>Attempts left: " + (10 - attempt) + "</h3>");
        } else {
            out.println("<h2>🔺 Too Low! Try again.</h2>");
            out.println("<h3>Attempts left: " + (10 - attempt) + "</h3>");
        }

        out.println("<br><a href='play.jsp'><button>Back to Game</button></a>");
        out.println("</body></html>");
    }
}
