import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.sql.*;

public class DeleteServlet extends HttpServlet {
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int tid  = Integer.parseInt(request.getParameter("tid"));

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // Establishing the connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/todo", "root", "password");

            // Creating the SQL statement
            String sql = "DELETE FROM list WHERE tid=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, tid);

            // Executing the statement
            stmt.executeUpdate();

            // Redirect to a success page
            response.sendRedirect("/Todos/ReadList");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Closing resources
            try {
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
