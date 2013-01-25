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
  // ���ݲ�Ʒid����һ��Bean
  public String ejbCreate(String product_id) 
    throws DuplicateKeyException, CreateException{ 
    this.product_id = product_id;
    this.name = "";
    this.price = 0;
    return null;
  }
  public void ejbPostCreate(String product_id){ 
  }
  //���ݲ�Ʒid,���ƣ��۸���һ��Bean
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
  // ���ò�Ʒ����
  public void setName(String name){
    this.name = name;
  }
 // ���ز�Ʒ����
  public String getName(){
    return name;
  }
  // ���ò�Ʒ�۸�
  public void setPrice(int price){
    this.price = price;
  }
 // ���ز�Ʒ�۸�
  public int getPrice(){
    return price;
  }
 // ���ò�Ʒ���ƺͼ۸�        
  public void setProductData(String name, int price){
    this.name = name;
    this.price = price;
  }
  // ���ز�Ʒ��Ŀ
  public ProductItem getProductData(){
    return new ProductItem(product_id, name, price);    
  }
  // ����һ��Beanʵ��
  public void ejbActivate(){ 
    product_id = (String)context.getPrimaryKey();
  }
 // ���öۻ�
  public void ejbPassivate(){
    product_id = null;
    name = null;
    price = 0;
  }
  public void ejbRemove(){ 
  }
}
