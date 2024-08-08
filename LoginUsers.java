import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.sql.*;

public class LoginUsers extends HttpServlet {
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String Email = request.getParameter("Email");
        String pswd = request.getParameter("pswd");

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Statement stmt1 = null;
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();

        try {
            // Establishing the connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/todo", "root", "password");

            String sql = "SELECT * FROM users where Email='" + Email + "'and pswd='" + pswd + "'";
            stmt1 = conn.createStatement();
            rs = stmt1.executeQuery(sql);
            if (!rs.next()) {

                out.println("User does not exist");

                response.setContentType("text/html");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>To Register </title>");
                out.println("<style>");
                out.println("body {");
                out.println("  background-image: url('blue.jpg');");
                out.println("  background-size: cover;");
                out.println("}");
                out.println("</style>");
                out.println("</head>");
                out.println("<body>");
                out.println("<br><br><a href='registration.html'>Register as a user</a>");
                out.println("</body>");
                out.println("</html>");
                out.close();

            } else {
                int user_id = rs.getInt("id");
                session.setAttribute("uid", user_id);
                response.setContentType("text/html");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>The TO-DO List </title>");
                out.println("<style>");
                out.println("body {");
                out.println("  background-image: url('blue.jpg');");
                out.println("  background-size: cover;");
                out.println("}");
                out.println("</style>");
                out.println("</head>");
                out.println("<body>");

                out.println("<div style='display: flex; justify-content: space-between; align-items: center; padding: 10px;'>");
                out.println("<h2>Welcome to the TO-DO List</h2>");
                out.println("<form action='LogOutServlet' method='post'>");
                out.println("<input type='submit' value='LogOut'>");
                out.println("</form>");
                out.println("</div>");
                out.println("<center><form action='list.html' method='post'><input type='submit' value='Add data'></form></center>");
                out.println("<center><form action='ReadList' method='post'><input type='submit' value='Read data'></form></center>");
                out.println("</body>");
                out.println("</html>");
                out.close();

            }
        } catch (Exception e) {
            out.println(e);
        } finally {
            // Closing resources
            try {
                if (stmt1 != null)
                    stmt1.close();
                if (conn != null)
                    conn.close();
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
