package order.common;
import java.io.Serializable;
public class ProductItem implements Serializable{
// 定义产品号，名称，价格等变量
  String product_id;
  String name;
  int price;
 // 初始化定义的变量
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
