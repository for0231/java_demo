package order.common;
import java.io.Serializable;
// 建立一个订货项目类
public class OrderItem implements Serializable{
// 定义订货编号，产品号，数量，价格等变量
  String order_id;
  String product_id;
  int quantity;
  String name;
  int price;
  int totalprice;
// 初始化变量
  public OrderItem(String order_id,String product_id, int quantity){       	
    this.order_id=order_id;
    this.product_id=product_id;
    this.quantity=quantity;
  }
  public OrderItem(String order_id, String product_id, int quantity, 
  String name, int price, int totalprice){      	
    this.order_id=order_id;
    this.product_id=product_id;
    this.quantity=quantity;
    this.name=name;
    this.price=price;
    this.totalprice=totalprice;
  }
  public String getProduct_id(){
    return product_id;
  }
  public int getQuantity(){
    return quantity;
  }
  public String getOrder_id(){
    return order_id;
  }
  public String getName(){
    return name;
  } 
  public int getPrice(){
    return price;
  }
  public int getTotalprice(){
    return totalprice;
  }
}
