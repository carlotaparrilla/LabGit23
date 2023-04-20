import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;

//leer la info del otro servlet de las tres caracteristicas mediante el getparameter
//despue sun update

@SuppressWarnings("serial")
public class InsertSupplier extends HttpServlet {
    Connection connection;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        connection = ConnectionUtils.getConnection(config);

    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
        res.setContentType("text/html");
		PrintWriter toClient = res.getWriter();
		
		//sacas estos nombres de categoryedit
		String name = req.getParameter("supplierName");
		String cit = req.getParameter("city");
		String countr = req.getParameter("country");
		
        SupplierData supplier = new SupplierData(
			0,
            name,
            cit,
			countr
        );
        int n = SupplierData.insertSupplier(connection, supplier);
		res.sendRedirect("Countries.html");


    }
}
	
