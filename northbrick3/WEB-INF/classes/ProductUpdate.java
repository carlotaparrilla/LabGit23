import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;

@SuppressWarnings("serial")
public class ProductUpdate extends HttpServlet {
    Connection connection;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        connection = ConnectionUtils.getConnection(config);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
        res.setContentType("text/html");
        String idStr = req.getParameter("productId");
        ProductData product = new ProductData(
                    req.getParameter("productId"),
                    req.getParameter("productName"),
                    Integer.parseInt(req.getParameter("supplierId")),
                    null,
                    Float.parseFloat(req.getParameter("unitPrice"))
                );
        int n = ProductData.updateProduct(connection, product);
        res.sendRedirect("ProductEdit?id=" + idStr + "&a=" + Math.random());
    }
}