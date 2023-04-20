import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;

//leer la info del otro servlet de las tres caracteristicas mediante el getparameter
//despue sun update

@SuppressWarnings("serial")
public class CategoryUpdate extends HttpServlet {
    Connection connection;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        connection = ConnectionUtils.getConnection(config);

    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
        res.setContentType("text/html");
		PrintWriter toClient = res.getWriter();
		
		//sacas estos nombres de categoryedit
		int idStr = Integer.parseInt(req.getParameter("categoryId"));
		String name = req.getParameter("categoryName");
		String desc = req.getParameter("description");
		
		
        CategoryData category = new CategoryData(
			idStr,
            name,
            desc
        );
        int n = CategoryData.metodoUpdate(connection, category);
		res.sendRedirect("CategoryList");


    }
}
	
