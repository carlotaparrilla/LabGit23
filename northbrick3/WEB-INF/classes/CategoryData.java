import java.util.Vector;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryData {
    int    categoryId;
    String categoryName;
    String categoryDescription;


   
    CategoryData (int categoryId, String categoryName, String categoryDescription) { // constructor (nombre igual a la clase)
        this.categoryId    = categoryId; // referirte a los que estan arriba y lo igualas a lo que le ingresas.
        this.categoryName  = categoryName;
        this.categoryDescription = categoryDescription;

    }

//te devuelve todo lo de category data NO SOLO 1, que es lo que me hace falta para el edit, por eso ahora solo hare referencia al primer valor del vector vec
    public static Vector<CategoryData> getProductList(Connection connection) { // metodo llamado getProductList y devuelve un vector de tipo CategoryData.
        Vector<CategoryData> vec = new Vector<CategoryData>();
//definir el sql
        String sql = "Select CategoryID, CategoryName, Description FROM Categories";
        System.out.println("getProductList: " + sql); // verificar lo que hago
        try { // para poder detectar un error. TRY Y CATCH SIEMPRE ES IGUAL
            Statement statement=connection.createStatement(); // lo que antes se hacia con el odcb
            ResultSet result = statement.executeQuery(sql); // tipo de variables que te devuelve el sql. EXECUTE O UPDATE dependiendo de lo que te pidan
            while(result.next()) {
                CategoryData product = new CategoryData( // siempre que creas un tipo de variable pones new por delante
                    Integer.parseInt(result.getString("CategoryID")),
                    result.getString("CategoryName"),
                    result.getString("Description")
                );
                vec.addElement(product);
            }
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Error in getProductList: " + sql + " Exception: " + e);
        }
        return vec;
    }
    public static Vector<ProductData> getCategoryProductList(Connection connection, String id) {
        Vector<ProductData> vec = new Vector<ProductData>();
        String sql = "Select ProductId, ProductName, Products.SupplierId as SupplierId, CompanyName, UnitPrice FROM Products, Suppliers";
        sql += " WHERE Products.Category = Categories.CategoryName AND CategoryID=?";
        System.out.println("getProductList: " + sql);
        try {
            PreparedStatement pstmt=connection.prepareStatement(sql); // PREPARED ES PORQUE TIENES UN ? ANTES
            pstmt.setInt(1, Integer.parseInt(id));  // ¿QUE PASA SI AQUI PONGO UN 2 EN VEZ DE 1,?
            ResultSet result = pstmt.executeQuery();
            while(result.next()) {
                ProductData product = new ProductData(
                    result.getString("ProductId"),
                    result.getString("ProductName"),
                    Integer.parseInt(result.getString("SupplierId")),
                    result.getString("CompanyName"),
                    Float.parseFloat(result.getString("UnitPrice"))
                );
                vec.addElement(product);
            }
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Error in getProductList: " + sql + " Exception: " + e);
        }
        return vec;
    }
	
	
	public static CategoryData getCategoryEdit(Connection connection, int id) {
		CategoryData category = null;
		String sql = "Select CategoryID, CategoryName, Description FROM Categories";
		sql += " WHERE CategoryID=?";
		System.out.println("getCategoryEdit: " + sql);
		try {
			PreparedStatement pstmt=connection.prepareStatement(sql); // mas seguro para eviatr hackeos
			pstmt.setInt(1, id);
			ResultSet result = pstmt.executeQuery();
			if(result.next()) {
				category = new CategoryData(
				Integer.parseInt(result.getString("CategoryId")),
				result.getString("CategoryName"),
				result.getString("Description")
				);
			}
			result.close();
			pstmt.close();
			
		}
				catch(SQLException e) {
					e.printStackTrace();
					System.out.println("Error in getProductList: " + sql + " Exception: " + e);
				}
					return category;
	}
	
	
	public static int metodoUpdate(Connection connection, CategoryData category) {
		String sql = "UPDATE Categories";
		sql += " SET  CategoryName = ?, Description = ?";
		sql += " WHERE CategoryID=?";
		System.out.println("getCategoryEdit: " + sql);
		
		int n=0;
		try {
			PreparedStatement pstmtUP=connection.prepareStatement(sql); // mas seguro para eviatr hackeos
			pstmtUP.setInt(3, category.categoryId);
			pstmtUP.setString(1, category.categoryName);
			pstmtUP.setString(2, category.categoryDescription);
			
			n= pstmtUP.executeUpdate();
			pstmtUP.close();
			
			
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Error in getProductList: " + sql + " Exception: " + e);
		}
			return n;
	}
}





//n devuleve el nuemro de flias que se han editado
