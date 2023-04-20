import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;

@SuppressWarnings("serial")
public class CategoryEdit extends HttpServlet {
    Connection connection;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        connection = ConnectionUtils.getConnection(config); // creamos la conexión con el database
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
        res.setContentType("text/html");
        PrintWriter toClient = res.getWriter();
		
		// lo pasamos a int EL GET PARAMETER ES PARA OBTENER EL NUMERO DEL ID PARA LA URL
		// EL GET PARAMETER FUNCIONA CON STRINGS
		// EL "id" HACE REFERENCIA A LO DE LA URL y viene del ctaegoriList de aqui: (linea 32) toClient.println("<td><a href='CategoryEdit?id=" + product.categoryId + "'>Edit</a></td>");
		int idStr = Integer.parseInt(req.getParameter("id")); // reading el value del id=10124 (en la barra de navegación de la web), para enseñar la información de ese producto específico
		
		// en categories tengo todas las categorias en el vector categories
		Vector<CategoryData> Categories = CategoryData.getProductList(connection);
		
		//para seleccionar solo 1
		CategoryData category = CategoryData.getCategoryEdit(connection, idStr);
		
        toClient.println(Utils.header("Category Form"));
		
		// FROM CATEGORY EDIT ME VOY A CATEGORYUPDATE PARA ENVIAR LA INFO DE UN SERVLET A OTR
		// para enviar informacion de un servleta a otro usamos el form
        toClient.println("<form  action = 'CategoryUpdate' method='GET'>"); 
        toClient.println("<table border='1'>"); 
        
		toClient.println("<input name='categoryId' type='hidden' value=" + category.categoryId + ">");
        toClient.println("<tr><td>Id</td><td>"+category.categoryId+"</td></tr>");
        
		toClient.println("<tr><td>Name</td>");
        String name = category.categoryName;
        System.out.println("Name: " + name);
        name = name.replace("'","&#39;");
        System.out.println("Name: " + name);
        toClient.println("<td><input name='categoryName' value='" + name + "'></td></tr>");
        
        toClient.println("<tr><td>Description</td>");
        String description = category.categoryDescription;
        System.out.println("Description: " + description);
        name = description.replace("'","&#39;");
        System.out.println("Description: " + description);
        toClient.println("<td><input name='description' value='" + description + "'></td></tr>");

        toClient.println("</tr>");
        toClient.println("</table>");
        toClient.println("<input type='submit'>");
        toClient.println("</form>");
        toClient.println(Utils.footer("Category Form"));
        toClient.close();
    }
}