import java.util.Vector;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ShippersData {
    int    shipperId;
    String companyName;
    String phone;


	ShippersData (int shipperId, String companyName, String phone) { // constructor (nombre igual a la clase)
        this.shipperId    = shipperId; // referirte a los que estan arriba y lo igualas a lo que le ingresas.
        this.companyName  = companyName;
        this.phone = phone;
    }
	
	ShippersData (String companyName, String phone) { // constructor (nombre igual a la clase)
        
        this.companyName  = companyName;
        this.phone = phone;
    }

	// error de concepto:
	// String cod=Integer.parseInt("cod"); NOO
	//sería: int cod=Integer.parseInt("cod"); SII
	public static Vector<ShippersData> getShippersList(Connection connection, int cod) { // metodo llamado getProductList y devuelve un vector de tipo CategoryData.
        Vector<ShippersData> vec = new Vector<ShippersData>();
		if(cod==0) { // no hace falta ponerle int pq ya esta inicializado arriba como int
			
			String sql = "Select ShipperID, CompanyName, Phone FROM Shippers";
			System.out.println("getShippersList: " + sql); // verificar lo que hago
			try { 
				Statement statement=connection.createStatement(); // lo que antes se hacia con el odcb
				ResultSet result = statement.executeQuery(sql); // tipo de variables que te devuelve el sql. EXECUTE O UPDATE dependiendo de lo que te pidan
			
				while(result.next()) {
					ShippersData shippers = new ShippersData( // siempre que creas un tipo de variable pones new por delante
						result.getInt("ShipperID"),
						result.getString("CompanyName"),
						result.getString("Phone")
					);
					vec.addElement(shippers);
				}
			} catch(SQLException e) {
				e.printStackTrace();
				System.out.println("Error in getShippersList: " + sql + " Exception: " + e);
			}
			return vec;
		
			
		} else {
			
			String sql = "Select ShipperID, CompanyName, Phone FROM Shippers";
			sql += " WHERE ShipperID=?";
			System.out.println("getShippersList: " + sql);
			
			try { 
				PreparedStatement pstmt=connection.prepareStatement(sql);
				pstmt.setInt(1, cod); //ojooo si pone int ya es un int no poner el integer y es así pq ya es int
				// si fuera un String cod desde arriba sería así: pstmt.setInt(1, Integer.parseInt(cod));
				ResultSet result = pstmt.executeQuery();
			
				while(result.next()) {
					ShippersData shipper = new ShippersData(
						cod,
						result.getString("CompanyName"),
						result.getString("Phone")
					);
					vec.addElement(shipper);
					
				}
			} catch(SQLException e) {
				e.printStackTrace();
				System.out.println("Error in getShippersList: " + sql + " Exception: " + e);
			}
			return vec;
		}
	}
	
	public static int insertShipper(Connection connection, ShippersData shipper) {
		String sql ="INSERT INTO Shippers (CompanyName, Phone) VALUES (?, ?)";
			System.out.println("insertShipper: " + sql);
			int n = 0;
				
		try {
				
			PreparedStatement stmtUpdate= connection.prepareStatement(sql);
			stmtUpdate.setString(1,shipper.companyName);
			stmtUpdate.setString(2,shipper.phone);
			// al se run update se guarda en un int n
			n = stmtUpdate.executeUpdate();
			stmtUpdate.close();
				
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Error in insertShipper: " + sql + " Exception: " + e);
		}
			return n;
	}
		

        
    

	
}
