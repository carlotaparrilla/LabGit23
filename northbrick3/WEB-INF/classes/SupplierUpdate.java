import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;

//leer la info del otro servlet de las tres caracteristicas mediante el getparameter
//despue sun update

@SuppressWarnings("serial")
public class SupplierUpdate extends HttpServlet {
    Connection connection;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        connection = ConnectionUtils.getConnection(config);

    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
        res.setContentType("text/html");
		PrintWriter toClient = res.getWriter();
		
		//sacas estos nombres de categoryedit
		int idStr = Integer.parseInt(req.getParameter("supplierId"));
		String name = req.getParameter("contactName");
		String cit = req.getParameter("city");
		String countr = req.getParameter("country");
		
        SupplierData supplier = new SupplierData(
			idStr,
            name,
            cit,
			countr
        );
        int n = SupplierData.metodoUpdate(connection, supplier);
		res.sendRedirect("Countries.html");


    }
}
	
