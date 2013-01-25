package order.ejb;
import order.common.*;
import java.util.*;
import javax.ejb.*;
public class ProductEJB implements EntityBean{
  public String product_id;
  public String name;
  public int price;
  private EntityContext context;
  public void setEntityContext(EntityContext context){
    this.context = context;
  } 
  public void unsetEntityContext(){ 
    context = null;
  }
  // 根据产品id建立一个Bean
  public String ejbCreate(String product_id) 
    throws DuplicateKeyException, CreateException{ 
    this.product_id = product_id;
    this.name = "";
    this.price = 0;
    return null;
  }
  public void ejbPostCreate(String product_id){ 
  }
  //根据产品id,名称，价格建立一个Bean
  public String ejbCreate(String product_id, String name, int price)
    throws DuplicateKeyException, CreateException{
    this.product_id = product_id;
    this.name=name;
    this.price = price;
    return null;
  }
  public void ejbPostCreate(
    String product_id, String name, int price){
  }
  public void ejbLoad(){ 
  }
  public void ejbStore(){ 
  }
  // 设置产品名称
  public void setName(String name){
    this.name = name;
  }
 // 返回产品名称
  public String getName(){
    return name;
  }
  // 设置产品价格
  public void setPrice(int price){
    this.price = price;
  }
 // 返回产品价格
  public int getPrice(){
    return price;
  }
 // 设置产品名称和价格        
  public void setProductData(String name, int price){
    this.name = name;
    this.price = price;
  }
  // 返回产品项目
  public ProductItem getProductData(){
    return new ProductItem(product_id, name, price);    
  }
  // 激活一个Bean实例
  public void ejbActivate(){ 
    product_id = (String)context.getPrimaryKey();
  }
 // 调用钝化
  public void ejbPassivate(){
    product_id = null;
    name = null;
    price = 0;
  }
  public void ejbRemove(){ 
  }
}
