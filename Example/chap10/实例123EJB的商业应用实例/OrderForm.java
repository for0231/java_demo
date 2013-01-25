package order.javabean;
import order.ejb.*;
import order.common.*;
import java.util.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import javax.ejb.*;
public class OrderForm{
  String user_id;
  int[] itemNum;
  String order_id="";
  String[] product_id;
  String[] quantity;   
  String[] name;
  int[] price;
  int totalprice=0;
  ArrayList oArrayList=null; 
  ProductHome producthome=null;
  OrderHome orderhome=null;
    
  //取得ProductHome和OrderHome
  public OrderForm() throws Exception{  
    Context initial=new InitialContext();   
    Object objref=initial.lookup("ejb/ProductEntityBean");                
  producthome=(ProductHome)
PortableRemoteObject.narrow(objref,ProductHome.class);
    objref=initial.lookup("ejb/OrderEntityBean");
orderhome=(OrderHome)
PortableRemoteObject.narrow(objref, OrderHome.class);  
  }  
  //  设定或取得REQUEST网页传过来的内容 
  public void setOrder_id(String order_id){
    this.order_id=order_id; 
  }
  public String getOrder_id(){
    return this.order_id;
  }  
  public void setUser_id(String user_id){
    this.user_id=user_id; 
  }
  public String getUser_id(){
    return this.user_id;
  }
  public void setItemNum(int[] itemNum){
    this.itemNum=itemNum; 
  }
  public int[] getItemNum(){
    return this.itemNum;
  }
  public void setProduct_id(String[] product_id){
    this.product_id=product_id; 
  } 
  public String[] getProduct_id(){
    return this.product_id;
  }
  public void setName(String[] name){
    this.name=name; 
  }
  public String[] getName(){
    return this.name;
  }
  public void setPrice(int[] price){
    this.price=price; 
  }
  public int[] getPrice(){
    return this.price;
  }
  public void setQuantity(String[] quantity){
    this.quantity=quantity; 
  }
  public String[] getQuantity(){
    return this.quantity;
  }
  public int getTotalprice(){
    return this.totalprice;
  }    
  // 取得PRODUCTTBL内所有产品项目      
  public Vector getProductList() throws Exception{	
    Vector pVector=new Vector();	
    Collection c=producthome.findAll();
    Iterator i=c.iterator();    
    while (i.hasNext()) {
      Product product=(Product)i.next();
      pVector.add(product.getProductData());
    }
    return pVector;
  }
  // 设定购买的产品项目
  public void setOrderList() throws Exception{
    oArrayList=new ArrayList();  
    if(itemNum==null) return;
    this.totalprice=0;
    for(int n=0; n<itemNum.length; n++){
      int index=itemNum[n];
      int nquantity=0;
      try{
        nquantity =Integer.parseInt("0"+quantity[index].trim());
      }catch(Exception ex){
      }  
      if(nquantity!=0){
        int totalprice=nquantity*price[index];;
        oArrayList.add(
          new OrderItem(order_id, product_id[index], 
            nquantity, name[index], price[index], 
            totalprice)
        );
        this.totalprice+=totalprice;
      }
    }    
  } 
  // 取得购买的产品项目
  public ArrayList getOrderList(){
    return oArrayList;
  }
  //确认订购,将订购资讯写入资料库
  public void Order() throws Exception{
    if(totalprice==0) return
    Order order=orderhome.create(user_id, totalprice, oArrayList);
    order_id=order.getOrder_id();    
  }  
  //取消定购买单
  public void cancelOrder() throws Exception{
    if(order_id=="")
      throw new Exception("取消订单失败!");
    try{
      orderhome.remove(order_id);
}
catch(Exception ex){	
      throw new Exception("取消订单失败!");
    }   
  }  
  // 更改购买的产品项目
  public void updateOrder() throws Exception{
    if(order_id=="")
      throw new Exception("更改订单失败!");
    try{
      Order order=orderhome.findByPrimaryKey(order_id);
      order.setOrderItems(oArrayList);
      order.setTotalPrice(totalprice);
    }catch(Exception ex){
      throw new Exception("更改订单失败!");
    }   
  }     
}
