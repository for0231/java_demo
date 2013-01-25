package order.common;
import java.io.Serializable;
// ����һ��������Ŀ��
public class OrderItem implements Serializable{
// ���嶩����ţ���Ʒ�ţ��������۸�ȱ���
  String order_id;
  String product_id;
  int quantity;
  String name;
  int price;
  int totalprice;
// ��ʼ������
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
