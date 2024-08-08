import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.sql.*;

public class CreateList extends HttpServlet {
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String is_done = request.getParameter("is_done");
        String target_date = request.getParameter("target_date");

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Statement stmt1 = null;
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(true);
        int uid = (int) session.getAttribute("uid");

        try {
            // Establishing the connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/todo", "root", "password");

            // Creating the SQL statement
            String sql = "SELECT * FROM list where title='" + title + "'and description='" + description
                    + "'and is_done='" + is_done + "'and target_date='" + target_date + "'";
            stmt1 = conn.createStatement();
            rs = stmt1.executeQuery(sql);
            if (!rs.next()) {
                // Creating the SQL statement
                String sql2 = "INSERT INTO list (id,title,description,is_done,target_date) VALUES (?,?,?,?,?)";
                stmt = conn.prepareStatement(sql2);
                stmt.setInt(1, uid);
                stmt.setString(2, title);
                stmt.setString(3, description);
                stmt.setString(4, is_done);
                stmt.setString(5, target_date);

                // Executing the statement
                stmt.executeUpdate();

                response.setContentType("text/html");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>To view the task </title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<div style='text-align: right; padding: 10px;'>");
                out.println("<form action='LogOutServlet' method='post'>");
                out.println("<input type='submit' value='LogOut'>");
                out.println("</form>");
                out.println("</div>");
                out.println("<center>Task added successfully<br></center>");
                out.println("<br><br><center><form action='ReadList' method='post'><input type='submit' value='Read data'></form></center>");
                out.println("<br><center><form action='list.html' method='post'><input type='submit' value='Add data'></form></center>");
                out.println("</body>");
                out.println("</html>");
            } else {
                out.println("Task already exists");
            }
        } catch (Exception e) {
            out.print(e);
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
                out.print(e);
            }
        }
    }
}
