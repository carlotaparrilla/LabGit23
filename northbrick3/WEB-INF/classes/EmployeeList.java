import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;

@SuppressWarnings("serial")
public class EmployeeList extends HttpServlet {
    Connection connection;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        connection = ConnectionUtils.getConnection(config);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
        res.setContentType("text/html");
        PrintWriter toClient = res.getWriter();
		String ciudad= req.getParameter("ciudad");

        toClient.println(Utils.header("Employee List from " + ciudad));
		
        toClient.println("<table border='1'>");
        Vector<EmployeeData> employeeList; 
        employeeList = EmployeeData.getEmployeesList(connection, ciudad);
		toClient.println("<tr><td>Id</td><td>Last Name</td><td>First Name</td></tr>");

        for(int i=0; i< employeeList.size(); i++){
                EmployeeData employee = employeeList.elementAt(i); 
                toClient.println("<tr>");
                toClient.println("<td>" + employee.employeeId + " </td>");
                toClient.println("<td>" + employee.lastName + " </td>");
                toClient.println("<td>" + employee.firstName + " </td>");

                toClient.println("</tr>");
        }

        toClient.println("</table>");
        toClient.println(Utils.footer("Suppliers"));
        toClient.close();
    }
}