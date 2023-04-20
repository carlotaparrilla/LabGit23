import java.util.Vector;

public class ProductDataMockup {
    int    productId;
    String productName;
    int    supplierId;
    String companyName;
    float  unitPrice;
    
    ProductDataMockup (int productId, String productName, int supplierId, String companyName, float unitPrice) {
        this.productId    = productId;
        this.productName  = productName;
        this.supplierId   = supplierId;
        this.companyName = companyName;
        this.unitPrice = unitPrice;
    }
    public static Vector<ProductDataMockup> getProductList() {
        Vector<ProductDataMockup> vec = new Vector<ProductDataMockup>();
        vec.addElement(new ProductDataMockup(1, "Homemaker Bookcase 2 x 4 x 4", 1, "Brick Manufactures, Co.", 0.0337f));
        vec.addElement(new ProductDataMockup(2, "Homemaker Cupboard 4 x 4 x 4", 1, "Brick Manufactures, Co.", 0.0381f));
        vec.addElement(new ProductDataMockup(3, "Homemaker Drawer 4 x 4 x 2", 1, "Brick Manufactures, Co.", 0.0314f));
        vec.addElement(new ProductDataMockup(5, "Fabuland Fire Ladder Holder 2 x 4 x 2 1/2", 1, "Brick Manufactures, Co.", 0.0135f));
        return vec;
    }
    public static ProductDataMockup getProduct(int i) {
        return getProductList().elementAt(i - 1);
    }
    
}
