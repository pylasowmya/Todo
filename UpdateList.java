import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.sql.*;

public class UpdateList extends HttpServlet {
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int tid  = Integer.parseInt(request.getParameter("tid"));
        String title  = request.getParameter("title");
        String description = request.getParameter("description");
        String is_done = request.getParameter("is_done");
        String target_date= request.getParameter("target_date");
        
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // Establishing the connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/todo", "root", "password");

            // Creating the SQL statement
            String sql = "UPDATE list SET title=?, description=?, is_done=?,target_date=? where tid="+tid;
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, title);
            stmt.setString(2, description);
            stmt.setString(3, is_done);
            stmt.setString(4, target_date);


            // Executing the statement
            stmt.executeUpdate();

            // Redirect to a success page
            response.sendRedirect("/Todos/ReadList");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Closing resources
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
