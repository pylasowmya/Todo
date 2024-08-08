import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
public class LogOutServlet extends HttpServlet{
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

            	HttpSession session=request.getSession(false);
            	if(session!=null)
            	{
            		session.invalidate();
            	}
            	response.sendRedirect("/Todos/index.html");
            }
}