import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.sql.*;

public class RegisterUsers extends HttpServlet {
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String Email = request.getParameter("Email");
        String phno = request.getParameter("phno");
        String pswd = request.getParameter("pswd");

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Statement stmt1 = null;
        PrintWriter out = response.getWriter();

        try 
        {
            // Establishing the connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/todo", "root", "password");

                String sql = "SELECT * FROM users where Email='"+Email+"'and phno='"+phno+"'";
                stmt1 = conn.createStatement();
                rs = stmt1.executeQuery(sql);
                if(!rs.next())
                {
                    // Creating the SQL statement
                    String sql2 = "INSERT INTO users (name, Email,phno,pswd) VALUES (?,?,?,?)";
                    stmt = conn.prepareStatement(sql2);
                    stmt.setString(1, name);
                    stmt.setString(2, Email);
                    stmt.setString(3, phno);
                    stmt.setString(4, pswd);

                    // Executing the statement
                    stmt.executeUpdate();

                    out.println("Registered successfully");

                    response.setContentType("text/html");
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>To Login in</title>");
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<br><br><a href='index.html'>Login in to the application</a>");
                    out.println("</body>");
                    out.println("</html>");
                    out.close();
                    
    
                }
                else
                {
                    out.println("User already exists");
                }
        }
        catch (Exception e) {
          out.println(e);
        } 
        finally {
                // Closing resources
            try {
                if (stmt1 != null) stmt1.close();
                if (conn != null) conn.close();
                if (rs!=null) rs.close();
            } catch (SQLException e) {
                     e.printStackTrace();
                }
            }
        }
    }