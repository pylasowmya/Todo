import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.sql.*;

public class UpdateValue extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conn = null;
        Statement stmt = null;
        PreparedStatement stmt1 = null;
        ResultSet rs = null;

        int tid  = Integer.parseInt(request.getParameter("tid"));
       
        String update = request.getParameter("submit");
        
        try {
            // Establishing the connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/todo", "root", "password");

            // Creating the SQL statement
            String sql = "SELECT * FROM list where tid=" + tid;
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
            out.println("    background-image: url('white.jpg');");
            out.println("    background-size: cover;");
            out.println("}");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            
            out.println("<form name='update' action='/Todos/UpdateList'><table align='center' border='1'>");
            out.println("<tr>");
            out.println("<th colspan='4'><h2>User List</h2></th>");
            out.println("</tr><input type='hidden' name='tid' value="+tid+">");
            while (rs.next()) {
                out.println("<tr><td><label for='title'>Title:</label></td><td> <input type='text' id='title' name='title' value='"
                 + rs.getString("title") + "' required></td></tr><tr><td><label for='description'>Description:</label></td><td> <input type='text' id='description' name='description' value='" 
                 + rs.getString("description") + "' required></td></tr><tr><td><label for='is_done'>is_done:</label></td><td> <input type='text' id='is_done' name='is_done' value='" 
                 + rs.getString("is_done") + "' required></td></tr><tr><td><label for='target_date'>target_date:</label></td><td><input type='date' id='target_date' name='target_date' value='" 
                 + rs.getString("target_date") + "'></td></tr><tr><td colspan='2'><input type='submit' name='submit' value='change'> </a></td></tr>");
            }
            out.println("</table></form>");
            out.println("</body>");
            out.println("</html>");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Closing resources
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
