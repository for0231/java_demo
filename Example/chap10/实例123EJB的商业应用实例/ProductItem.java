package order.common;
import java.io.Serializable;
public class ProductItem implements Serializable{
// �����Ʒ�ţ����ƣ��۸�ȱ���
  String product_id;
  String name;
  int price;
 // ��ʼ������ı���
  public ProductItem(String product_id, String name, int price){
    this.product_id=product_id;
    this.name=name;
    this.price=price;
  }
  public String getProduct_id(){
    return product_id;
  }
  public String getName(){
    return name;
  }
  public int getPrice(){
    return price;
  }
}
