import java.util.Vector;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

public class OrderData {
    int    orderId;
    String    customerId;
    String companyName;
    Date    orderDate;
    
    OrderData (int orderId, String customerId, String companyName, Date orderDate) {
        this.orderId    = orderId;
        this.customerId   = customerId;
        this.companyName = companyName;
        this.orderDate = orderDate;
    }
    public static Vector<OrderData> getOrderList(Connection connection) {
        Vector<OrderData> vec = new Vector<OrderData>();
        String sql = "Select OrderId, CustomerId, OrderDate FROM Orders";
        System.out.println("getOrderList: " + sql);
        try {
            Statement statement=connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while(result.next()) {
                OrderData order = new OrderData(
                    Integer.parseInt(result.getString("OrderID")),
                    result.getString("CustomerId"),
                    null,
                    result.getDate("OrderDate")
                );
                vec.addElement(order);
            }
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Error in getOrderList: " + sql + " Exception: " + e);
        }
        return vec;
    }
    public static OrderData getOrderHeader(Connection connection, int id) {
        String sql = "Select OrderId, Orders.CustomerId, OrderDate FROM Orders, Customers";
        sql += " WHERE Orders.CustomerID = Customers.CustomerID AND OrderID=?";
        System.out.println("getOrder: " + sql);
        OrderData order = null;;
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet result = pstmt.executeQuery();
            if(result.next()) {
                order = new OrderData(
                    Integer.parseInt(result.getString("OrderID")),
                    result.getString("CustomerId"),
                    null,
                    result.getDate("OrderDate")
                );
            }
            result.close();
            pstmt.close();
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Error in getOrderHeader: " + sql + " Exception: " + e);
        }
        return order;
    }
    public static int updateOrderHeader(Connection connection, OrderData order) {
        String sql ="UPDATE Orders "
            + "SET CustomerId = ?,"
            + " OrderDate = ?"
            + " WHERE OrderId = ?";
        System.out.println("updateOrder: " + sql);
        int n = 0;
        try {
            PreparedStatement stmtUpdate= connection.prepareStatement(sql);
            stmtUpdate.setString(1,order.customerId);
            stmtUpdate.setDate(2,order.orderDate);
            stmtUpdate.setInt(3,order.orderId);
            n = stmtUpdate.executeUpdate();
            stmtUpdate.close();
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Error in updateOrder: " + sql + " Exception: " + e);
        }
        return n;
    }
    public static Vector<OrderDetailData> getOrderDetail(Connection connection, int id) {
        Vector<OrderDetailData> vec = new Vector<OrderDetailData>();
        String sql = "Select OrderID, [Order Details].ProductID, ProductName, [Order Details].UnitPrice as UnitPrice, Quantity, Discount FROM [Order Details], Products";
        sql += " WHERE [Order Details].ProductID = Products.ProductID AND OrderID = ?";
        System.out.println("getOrderDetail: " + sql);
        try {
            PreparedStatement statement= connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            while(result.next()) {
                OrderDetailData orderDetail = new OrderDetailData(
                    Integer.parseInt(result.getString("OrderID")),
                    result.getString("ProductID"),
                    result.getString("ProductName"),
                    Float.parseFloat(result.getString("UnitPrice")),
                    Float.parseFloat(result.getString("Quantity")),
                    Float.parseFloat(result.getString("Discount"))
                );
                vec.addElement(orderDetail);
            }
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Error in getOrderDetail: " + sql + " Exception: " + e);
        }
        return vec;
    }

    public static Vector<OrderDetailData> getSimpleOrderDetail(Connection connection, int id) {
        Vector<OrderDetailData> vec = new Vector<OrderDetailData>();
        String sql = "Select OrderID, ProductID, UnitPrice, Quantity, Discount FROM OrderDetailsOld";
        sql += " WHERE OrderID = ?";
        //System.out.println("getSimpleOrderDetail: " + sql);
        try {
            PreparedStatement statement= connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            while(result.next()) {
                OrderDetailData orderDetail = new OrderDetailData(
                    Integer.parseInt(result.getString("OrderID")),
                    result.getString("ProductID"),
                    "",
                    Float.parseFloat(result.getString("UnitPrice")),
                    Float.parseFloat(result.getString("Quantity")),
                    Float.parseFloat(result.getString("Discount"))
                );
                vec.addElement(orderDetail);
            }
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Error in getSimpleOrderDetail: " + sql + " Exception: " + e);
        }
        return vec;
    }
    public static int updateUnitPriceOrderDetail(Connection connection, OrderDetailData orderDetail) {
        String sql ="UPDATE [Order Details] "
            + "SET UnitPrice = ?"
            + " WHERE OrderID = ?";
        System.out.println("updateUnitPriceOrderDetail: " + sql);
        int n = 0;
        try {
            PreparedStatement stmtUpdate= connection.prepareStatement(sql);
            stmtUpdate.setFloat(1,orderDetail.unitPrice);
            stmtUpdate.setInt(2,orderDetail.orderId);
            n = stmtUpdate.executeUpdate();
            stmtUpdate.close();
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Error in updateUnitPriceOrderDetail: " + sql + " Exception: " + e);
        }
        return n;
    }
    public static int insertOrderDetail(Connection connection, OrderDetailData orderDetail) {
        String sql ="INSERT INTO [Order Details] (OrderID, ProductID, UnitPrice, Quantity, Discount) "
            + "VALUES (?, ?, ?, ?, ?)";
        System.out.println("updateProduct: " + sql);
        int n = 0;
        try {
            PreparedStatement stmtUpdate= connection.prepareStatement(sql);
            stmtUpdate.setInt(1,orderDetail.orderId);
            stmtUpdate.setString(2,orderDetail.productId);
            stmtUpdate.setFloat(3,orderDetail.unitPrice);
            stmtUpdate.setFloat(4,orderDetail.quantity);
            stmtUpdate.setFloat(5,orderDetail.discount);
            n = stmtUpdate.executeUpdate();
            stmtUpdate.close();
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Error in insertOrderDetail: " + sql + " Exception: " + e);
        }
        return n;
    }

}

class OrderDetailData {
    int    orderId;
    String    productId;
    String productName;
    float    unitPrice;
    float    quantity;
    float    discount;
    
    OrderDetailData (int orderId, String productId, String productName, float unitPrice, float quantity, float discount) {
        this.orderId    = orderId;
        this.productId   = productId;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.discount = discount;
    }
}

// To insert current time:
//        pstmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
//        pstmt.setTimestamp(1, new Timestamp(new Date().getTime()));
// https://stackoverflow.com/questions/24736427/how-to-get-date-from-a-resultset
