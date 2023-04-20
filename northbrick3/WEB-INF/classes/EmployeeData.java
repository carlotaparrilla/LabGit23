import java.util.Vector;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeData {
    int    employeeId;
    String lastName;
    String firstName;
	String ciudad;


	EmployeeData (int employeeId, String lastName, String firstName) { // constructor (nombre igual a la clase)
        this.employeeId    = employeeId; // referirte a los que estan arriba y lo igualas a lo que le ingresas.
        this.lastName  = lastName;
        this.firstName = firstName;
		

    }
	
	
	
	
	public static Vector<EmployeeData> getEmployeesList(Connection connection, String ciudad){
        Vector<EmployeeData> vec = new Vector<EmployeeData>();
//definir el sql
        String sql = "Select EmployeeID, LastName, FirstName FROM Employees";
		sql += " WHERE Employees.City=?";
        System.out.println("getSuppliersList: " + sql); // verificar lo que hago
		
        try { // para poder detectar un error. TRY Y CATCH SIEMPRE ES IGUAL
            PreparedStatement pstmt=connection.prepareStatement(sql); // mas seguro para eviatr hackeos
			pstmt.setString(1, ciudad); // lo que antes se hacia con el odcb
            ResultSet result = pstmt.executeQuery(); 
            
			while(result.next()) {
				//Utilizamos el constructor de Supplier que necesita (int supplierId, String contactName, String city)
                EmployeeData emplo = new EmployeeData( // siempre que creas un tipo de variable pones new por delante
                    Integer.parseInt(result.getString("EmployeeID")),
                    result.getString("LastName"),
                    result.getString("FirstName")
                );
                vec.addElement(emplo);
            }
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Error in getSuppliersList: " + sql + " Exception: " + e);
        }
        return vec;
    }
}