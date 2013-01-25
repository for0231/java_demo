package order.javabean;
import order.ejb.*;
import order.common.*;
import java.util.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import javax.ejb.*;
public class QueryOrder{
  String user_id;
  HashMap pHashMap=new HashMap();
  ProductHome producthome=null;
  OrderHome orderhome=null;
  // 取得ProductHome和OrderHome
  public QueryOrder() throws Exception{
    Context initial=new InitialContext();
    Object objref=initial.lookup("ejb/ProductEntityBean");         
producthome=(ProductHome)
PortableRemoteObject.narrow(objref, ProductHome.class);
    objref=initial.lookup("ejb/OrderEntityBean");
orderhome=(OrderHome)
PortableRemoteObject.narrow(objref, OrderHome.class); 
    setProductHashMap();  
  }  
  // 设定或取得REQUEST网页传过来的资料 
  public void setUser_id(String user_id){
    this.user_id=user_id; 
  }
  public String getUser_id(){
    return this.user_id;
  } 
  //设定产品id与产品项目物件(ProductItem类别)对应的HashMap
  private void setProductHashMap() throws Exception{  
    Collection c=producthome.findAll();
    Iterator i=c.iterator(); 
    while (i.hasNext()) {
      ProductItem pItem=
        ((Product)i.next()).getProductData(); 
      pHashMap.put(pItem.getProduct_id(),pItem);
    }
  }
  //取得产品名称
  public String getProductName(String product_id){
    return ((ProductItem)pHashMap.get(product_id)).getName();
  }
  //取得产品价格
  public int getProductPrice(String product_id){
    return ((ProductItem)pHashMap.get(product_id)).getPrice();
  }      
  //取得所有订单的订购项目
  public Hashtable getOrders() throws Exception{
    Hashtable orderHt=new Hashtable();
    Iterator i=null; 
    try{
      i=orderhome.findByUserId(user_id).iterator();
}
catch(ObjectNotFoundException oex){
      throw new Exception("没有订购单记录!");
}
catch(Exception ex){
      throw new Exception("查询订单发生错误!");
    } 
    while(i.hasNext()){
      Order order=(Order)i.next();	
      ArrayList al=order.getOrderItems();
      orderHt.put(order.getOrder_id(),al);
    }  
    return orderHt;  
  }
}
