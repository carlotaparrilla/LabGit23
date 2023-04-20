import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;

//@SuppressWarnings("serial")
public class ProductDataTest {

    public static void main(String[] args) {
        Connection connection = null;;
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            String url="jdbc:odbc:northwind";
            connection=DriverManager.getConnection(url); 
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        if (args.length == 0) {
            Vector<ProductData> productList = ProductData.getProductList(connection);
            for(int i=0; i< productList.size(); i++){
                ProductData product = productList.elementAt(i);
                System.out.println("Product id: "   + product.productId);
                System.out.println("Product name: " + product.productName);
                System.out.println("Supplier id: "  + product.supplierId);
                System.out.println("Company name: " + product.companyName);
                System.out.println("Unit price: "   + product.unitPrice);
                System.out.println("");
            }
        } else if (args.length == 1) {
            ProductData product = ProductData.getProduct(connection, args[0]);
                System.out.println("Product id: "   + product.productId);
                System.out.println("Product name: " + product.productName);
                System.out.println("Supplier id: "  + product.supplierId);
                System.out.println("Unit price: "   + product.unitPrice);
        } else if (args.length == 4) {
            String productId = args[0];
            String productName = args[1];
            int supplierId = Integer.parseInt(args[2]);
            float unitPrice = Float.parseFloat(args[3]);
            ProductData product = new ProductData(productId, productName, supplierId, null, unitPrice);
            int n = ProductData.updateProduct(connection, product);
            System.out.println("Updated: " + n + " products");
        }
        try {
            connection.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
