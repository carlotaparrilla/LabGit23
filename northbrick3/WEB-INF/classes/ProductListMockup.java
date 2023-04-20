import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class ProductListMockup extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
        res.setContentType("text/html");
        PrintWriter toClient = res.getWriter();
        toClient.println(Utils.header("Products"));
        toClient.println("<table border='1'>");
        Vector<ProductDataMockup> productList = ProductDataMockup.getProductList();
        for(int i=0; i< productList.size(); i++){
                ProductDataMockup product = productList.elementAt(i);
                toClient.println("<tr>");
                toClient.println("<td>" + product.productId + " </td>");
                toClient.println("<td>" + product.productName + " </td>");
                toClient.println("<td>" + product.companyName + " </td>");
                toClient.println("<td>" + product.unitPrice + " </td>");
                toClient.println("<td><a href='ProductEditMockup?id=" + product.productId + "'>Edit</a></td>");
                toClient.println("</tr>");
        }

        toClient.println("</table>");
        toClient.println(Utils.footer("Products"));
        toClient.close();
    }
}