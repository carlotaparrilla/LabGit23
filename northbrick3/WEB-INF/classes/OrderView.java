import java.util.Vector;

public class OrderView {
    public static String orderHeader(OrderData order) {
        StringBuilder str = new StringBuilder();
        str.append("<!DOCTYPE HTML>");
        str.append("<table border='0'>");
        str.append("<tr><td>Id</td>");
        str.append("<td>" + order.orderId + "</td></tr>");
        str.append("<tr><td>Customer</td>");
        str.append("<td>" + order.customerId + "</td>");
        str.append("<tr><td>Date</td>");
        str.append("<td>" + order.orderDate + "</td>");
        str.append("</tr>");
        str.append("</table>");
        return str.toString();
    }

    public static String orderDetail(Vector<OrderDetailData> orderDetail) {
        StringBuilder str = new StringBuilder();
        str.append("<table border='0'>");
        for(int i=0; i< orderDetail.size(); i++){
            OrderDetailData detail = orderDetail.elementAt(i);
            str.append("<tr>");
            str.append("<td>" + detail.productId + " </td>");
            str.append("<td>" + detail.productName + " </td>");
            str.append("<td class='number'>" + detail.unitPrice + " </td>");
            str.append("<td class='number'>" + detail.quantity + " </td>");
            str.append("<td class='number'>" + detail.discount + " </td>");
            //str.append("<td><a href='ProductEdit?id=" + detail.productId + "'>Edit</a></td>");
            str.append("<td><img style='height:50px;' src='http://northbrick1.appspot.com/images/" + detail.productId + ".png'></td>");
            str.append("</tr>");
        }
        str.append("</table>");
        return str.toString();
    }
}
