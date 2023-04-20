import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;

@SuppressWarnings("serial")
public class ShippersList extends HttpServlet {
    Connection connection;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        connection = ConnectionUtils.getConnection(config);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
        res.setContentType("text/html");
        PrintWriter toClient = res.getWriter();
		int cod= Integer.parseInt(req.getParameter("cod")); //OJOOOOOO lo qeu te lee get parameter es un string si pones int delante tientes que poner el integer
		// String cod= req.getParameter(Integer.ParseInt("cod")); MAL
		// String cod= Integer.ParseInt(req.getParameter("cod")); MAL
        toClient.println(Utils.header("Shippers"));
		
        toClient.println("<table border='1'>");
        Vector<ShippersData> shipperList;
        shipperList = ShippersData.getShippersList(connection, cod); //ya esta como int por lña linea 19 y ahora netra como int al igual que en el metodo
		toClient.println("<tr><td>Id</td><td>CompanyName</td><td>Phone</td></tr>");

        for(int i=0; i< shipperList.size(); i++){
                ShippersData ship= shipperList.elementAt(i);
                toClient.println("<tr>");
                toClient.println("<td>" + ship.shipperId + " </td>");
                toClient.println("<td>" + ship.companyName + " </td>");
                toClient.println("<td>" + ship.phone + " </td>");
                toClient.println("</tr>");
        }

        toClient.println("</table>");
        toClient.println(Utils.footer("Shippers"));
        toClient.close();
    }
}