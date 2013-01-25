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
  // ȡ��ProductHome��OrderHome
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
  // �趨��ȡ��REQUEST��ҳ������������ 
  public void setUser_id(String user_id){
    this.user_id=user_id; 
  }
  public String getUser_id(){
    return this.user_id;
  } 
  //�趨��Ʒid���Ʒ��Ŀ���(ProductItem���)��Ӧ��HashMap
  private void setProductHashMap() throws Exception{  
    Collection c=producthome.findAll();
    Iterator i=c.iterator(); 
    while (i.hasNext()) {
      ProductItem pItem=
        ((Product)i.next()).getProductData(); 
      pHashMap.put(pItem.getProduct_id(),pItem);
    }
  }
  //ȡ�ò�Ʒ����
  public String getProductName(String product_id){
    return ((ProductItem)pHashMap.get(product_id)).getName();
  }
  //ȡ�ò�Ʒ�۸�
  public int getProductPrice(String product_id){
    return ((ProductItem)pHashMap.get(product_id)).getPrice();
  }      
  //ȡ�����ж����Ķ�����Ŀ
  public Hashtable getOrders() throws Exception{
    Hashtable orderHt=new Hashtable();
    Iterator i=null; 
    try{
      i=orderhome.findByUserId(user_id).iterator();
}
catch(ObjectNotFoundException oex){
      throw new Exception("û�ж�������¼!");
}
catch(Exception ex){
      throw new Exception("��ѯ������������!");
    } 
    while(i.hasNext()){
      Order order=(Order)i.next();	
      ArrayList al=order.getOrderItems();
      orderHt.put(order.getOrder_id(),al);
    }  
    return orderHt;  
  }
}
