import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.sql.*;

public class ReadList extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // Establishing the connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/todo", "root", "password");

            // Creating the SQL statement
            String sql = "SELECT * FROM list";
            stmt = conn.createStatement();

            // Executing the query
            rs = stmt.executeQuery(sql);

            // Processing the result
            PrintWriter out = response.getWriter();

            out.println("<html lang='en'>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
            out.println("<title>User List</title>");
            out.println("<style>");
            out.println("body {");
            out.println("  background-image: url('white.jpg');");
            out.println("  background-size: cover;");
            out.println("}");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");

            out.println("<div style='text-align: right; padding: 10px;'>");
            out.println("<form action='LogOutServlet' method='post'>");
            out.println("<input type='submit' value='LogOut'>");
            out.println("</form>");
            out.println("</div>");

            out.println("<table align='center' border='1'>");
            out.println("<tr>");
            out.println("<th colspan='6'><h2>User List</h2></th>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<th>Title</th>");
            out.println("<th>Description</th>");
            out.println("<th>Status</th>");
            out.println("<th>Target_Date</th>");
            out.println("<th colspan='2'>Action</th>");
            out.println("</tr>");
            while (rs.next()) {
                int stats = rs.getInt("is_done");
                String flag;
                if (stats == 0) {
                    flag = "Pending";
                } else {
                    flag = "Completed";
                }
                out.println("<tr><td>" + rs.getString("title") + "</td><td>" + rs.getString("description") + "</td><td>"
                        + flag + "</td><td>" + rs.getString("target_date")
                        + "</td><td><a href='UpdateValue?tid=" + rs.getInt("tid")
                        + "'><input type='submit' name='submit' value='Update'> </a></td><td><a href='DeleteServlet?tid="
                        + rs.getInt("tid") + "'><input type='submit' name='submit' value='Delete'> </a></td></tr>");
            }
            out.println("</table>");
            out.println("<br><br><center><form action='list.html' method='post'><input type='submit' value='Add data'></form></center>");
            out.println("</body>");
            out.println("</html>");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Closing resources
            try {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
