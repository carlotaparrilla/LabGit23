import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;

@SuppressWarnings("serial")
public class CategoryList extends HttpServlet {
    Connection connection;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        connection = ConnectionUtils.getConnection(config);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
        res.setContentType("text/html");
        PrintWriter toClient = res.getWriter();

        toClient.println(Utils.header("Categories"));
        toClient.println("<table border='1'>");
        Vector<CategoryData> categoryList;
        categoryList = CategoryData.getProductList(connection);

        for(int i=0; i< categoryList.size(); i++){
                CategoryData product = categoryList.elementAt(i);
                toClient.println("<tr>");
                toClient.println("<td>" + product.categoryId + " </td>");
                toClient.println("<td>" + product.categoryName + " </td>");
                toClient.println("<td>" + product.categoryDescription + " </td>");
                toClient.println("<td><a href='ProductList?id=" + product.categoryId + "'>Products</a></td>");
				toClient.println("<td><a href='CategoryEdit?id=" + product.categoryId + "'>Edit</a></td>");
                toClient.println("</tr>");
        }

        toClient.println("</table>");
        toClient.println(Utils.footer("Categories"));
        toClient.close();
    }
}