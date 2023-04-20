import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;

//leer la info del otro servlet de las tres caracteristicas mediante el getparameter
//despue sun update

@SuppressWarnings("serial")
public class InsertShipper extends HttpServlet {
    Connection connection;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        connection = ConnectionUtils.getConnection(config);

    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
        res.setContentType("text/html");
		PrintWriter toClient = res.getWriter();
		
		//sacas estos nombres de categoryedit
		String compname = req.getParameter("companyName");
		String pho = req.getParameter("phone");
		
        ShippersData shipper = new ShippersData(
			0,
            compname,
            pho
			
        );
		int n = ShippersData.insertShipper(connection, shipper);
		res.sendRedirect("Shippers.html");


    }
}