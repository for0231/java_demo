package order.ejb;
import order.common.*;
import java.sql.*;
import javax.sql.*;
import java.util.*;
import javax.ejb.*;
import javax.naming.*;
public class OrderEJB implements EntityBean{
  public String order_id; // �������
  public String user_id; // �û��ʺ�
  public int totalPrice; // �����۸�
  public ArrayList orderItems; // ������Ŀ���������
  private Connection con;
  private String dbJndi="java:comp/env/jdbc/ExampleDB";
  // �������ݿ��id,password
  private String dbId="guest";
  private String dbPassword="guest123";
  private EntityContext context;
 // ȡ�����ݿ�����
  public void setEntityContext(EntityContext context){
    this.context=context;
    try{
      getConnection();
}
catch(Exception ex){
      throw new EJBException("Connect to DB failed:"+ex.getMessage());
     }
   }
  // �ͷ����ݿ�����
  public void unsetEntityContext(){
    try{
      con.close();
}
catch(SQLException ex){
      throw new EJBException("Close DB Connection failed:"+ex.getMessage());
    }
  }
// ��Ӷ�����ordertbl����Ӷ���ϸĿ��itemtbl��
  public String ejbCreate(String user_id, int totalPrice, ArrayList orderItems)
    throws CreateException{
     if(orderItems==null||orderItems.size()==0)
     throw new CreateException("ejbCreate exception : no any item");
    try{// ȡ�ö������
      this.order_id=genOrder_id();
        // ���һ�ʶ�����ordertbl��� 
      insertOrder(this.order_id, user_id, totalPrice);
       // ��Ӷ���ϸĿ��itemtbl���
      insertItems(orderItems);
}
catch(Exception ex){
      throw new EJBException("ejbCreate exception: "+ex.getMessage());
    }
    this.user_id=user_id;
    this.totalPrice=totalPrice;
    this.orderItems=orderItems;
   // ���ض������ 
    return order_id;
  }
  public void ejbPostCreate(String user_id,
    int totalPrice, ArrayList orderItems){ 
  } 
// ͨ��һ���������Ҷ���
  public String ejbFindByPrimaryKey(String orderId)throws FinderException{
    boolean result;
    try{
      result=selectByPrimaryKey(orderId);
}
catch(Exception ex){
     throw new EJBException("ejbFindByPrimaryKey exception:"+ex.getMessage());
    }
    if(result)
      return orderId;
    else
      throw new ObjectNotFoundException("Row for id "+orderId+" not found.");
  }
// ���ݶ������˺Ų���һ�������ߵ����ж���
  public Collection ejbFindByUserId(String userId)
    throws FinderException {
    Collection result;
    try{
      result=selectByUserId(userId);
}
catch(Exception ex){
      throw new EJBException(ex.getMessage());
     }
    if(result.isEmpty()){
      throw new ObjectNotFoundException("No rows found.");
}
else{
      return result;
        }
     }
// �������ж���
  public Collection ejbFindAll()throws FinderException {
    Collection result;
    try{
      result=selectAll();
}
catch(Exception ex){
      throw new EJBException(ex.getMessage());
    }
    if(result.isEmpty()){
      throw new ObjectNotFoundException("No rows found.");
}
else{
      return result;
      }
  }
 // ��ȡ���ݿⶩ�����ݺͶ���ϸĿ���ڴ��ֶα���
  public void ejbLoad(){
    try{
      loadOrder();
      loadItems();
}
catch(Exception ex){
      throw new EJBException("ejbLoad exception:"+ex.getMessage()+".....");
    }
  }
 // д���ڴ��ֶα��������ݿ��
  public void ejbStore(){
    try{
      storeOrder();
      deleteItems();
      insertItems(orderItems);
}
catch(Exception ex)
{
      throw new EJBException("ejbLoad exception:"+ex.getMessage());
    }
  }
  // ��ö���ϸĿ
  public ArrayList getOrderItems(){
    return orderItems;
  }
// ȡ���û�id
  public String getUser_id(){
    return user_id;
  }
 // ��ö����ܼ�
  public int getTotalPrice(){
    return totalPrice;
  }
  // �趨�����ܼ�
  public void setTotalPrice(int totalPrice){
    this.totalPrice=totalPrice;
  }
   // �趨�������
  public String getOrder_id(){
    return order_id;
  }
   // �趨����ϸĿ
  public void setOrderItems(ArrayList orderItems){
    if(orderItems==null||orderItems.size()==0)
      throw new EJBException("setOrderItems exception : no any item");
    this.orderItems=orderItems;
  }
// �ڼ���ִ��ǰ����ȥ������
  public void ejbActivate(){
    order_id=(String)context.getPrimaryKey();
  }
// �ۻ�����Beanʵ���Ż�Bean��
  public void ejbPassivate(){
    order_id=null;
  }
// ɾ�������Ͷ���ϸĿ
  public void ejbRemove(){
    try{
      deleteOrder();
      deleteItems();
}
catch(Exception ex){
      throw new EJBException("ejbRemove exception:"+ex.getMessage());
    }
  }  
  // ȡ�����ݿ�����
  private void getConnection() throws NamingException, SQLException{
    InitialContext ic=new InitialContext();
    DataSource ds=(DataSource) ic.lookup(dbJndi);
    con= ds.getConnection(dbId, dbPassword);
  }
  // ȡ�ö������
  private String genOrder_id() throws SQLException{
    ResultSet rs;
    String order_id;
    String selectStatement ="select order_id "+"from OrderTBL where order_id=? ";
    PreparedStatement ps = con.prepareStatement(selectStatement);
    try{      
      do{
        order_id=OrderId.genOrderId();
        ps.setString(1, order_id);
        rs=ps.executeQuery();
      }while(rs.next());
      return order_id;
}
finally{  
      ps.close();
     }    
  }
  // ���붩����ordertbl
  private void insertOrder(String order_id, String user_id, int totalPrice)
    throws SQLException{
    String insertStatement ="insert into OrderTBL values(? ,? ,?)";
    PreparedStatement ps= con.prepareStatement(insertStatement);
    try{
      ps.setString(1, order_id);
      ps.setString(2, user_id);
      ps.setInt(3, totalPrice);
      ps.executeUpdate();
}
finally{  
      ps.close();
     }      
   }
// ���붩��ϸĿ��itemtbl
  private void insertItems(ArrayList orderItems) throws SQLException{
    String insertStatement ="insert into ItemTBL values(? ,? ,?)";
    PreparedStatement ps=con.prepareStatement(insertStatement);
    try{ 
      // ��ʽ�����ϸĿ������ITEMTBL���    
      ps.setString(1, this.order_id);  
      for(int i=0; i<orderItems.size(); i++){
        OrderItem item=(OrderItem)orderItems.get(i);
        ps.setString(2, item.getProduct_id());
        ps.setInt(3, item.getQuantity());
        ps.executeUpdate();
      }
}
finally{  
      ps.close();
    }      
  }
  private boolean selectByPrimaryKey(String primaryKey) throws SQLException{
    String selectStatement ="select order_id "+"from OrderTBL where order_id=?";
    PreparedStatement ps =con.prepareStatement(selectStatement);
    try{  
      ps.setString(1, primaryKey);
      ResultSet rs=ps.executeQuery();
      return rs.next();
}
finally{  
      ps.close();
    }
  }
  private Collection selectAll() throws SQLException{
    String sqlStatement="select order_id from OrderTBL";
    PreparedStatement ps=con.prepareStatement(sqlStatement);
    try{
      ResultSet rs=ps.executeQuery();
      ArrayList al=new ArrayList();
      while(rs.next()){
        String id=rs.getString(1);
        al.add(id);
      }
      return al;
    }finally{  
      ps.close();
    }      
  }
  private Collection selectByUserId(String userId) throws SQLException{
    String sqlStatement="select order_id from OrderTBL "+"where user_id=?";
    PreparedStatement ps=con.prepareStatement(sqlStatement);
    try{  
      ps.setString(1, userId);
      ResultSet rs=ps.executeQuery();
      ArrayList al=new ArrayList();
      while(rs.next()){
        String id=rs.getString(1);
        al.add(id);
      }
      return al;
}
finally{
      ps.close();
    }
  }
   // ɾ��һ�ʶ���ϸĿ
  private void deleteItems() throws SQLException{
    String deleteStatement = "delete from ItemTBL  "+"where order_id=?";
    PreparedStatement ps =con.prepareStatement(deleteStatement);
    try{
      ps.setString(1, order_id);
      ps.executeUpdate();
}
finally{  
      ps.close();
    }  
  }
  // ɾ��һ�ʶ���
  private void deleteOrder() throws SQLException{
    String deleteStatement = "delete from OrderTBL  "+"where order_id=?";
    PreparedStatement ps =con.prepareStatement(deleteStatement);
    try{
      ps.setString(1, order_id);
      ps.executeUpdate();
}
finally{  
      ps.close();
    }  
  }
// ��ȡĿǰ�Ķ�����¼
  private void loadOrder() throws SQLException{
String selectStatement ="select user_id, totalPrice "
+"from OrderTBL where order_id=? ";
    PreparedStatement ps= con.prepareStatement(selectStatement);
    try{
      ps.setString(1, order_id);
      ResultSet rs=ps.executeQuery();
      if(rs.next()){
        user_id=rs.getString(1);
        totalPrice=rs.getInt(2);
      }
      else{
        throw new NoSuchEntityException(
          order_id+" not found.");
      }
}
finally{  
      ps.close();
    }  
  }
// ��ȡĿǰ�Ķ���ϸĿ��¼
  private void loadItems()throws SQLException{
    String selectStatement ="select product_id, quantity "+
      "from ItemTBL where order_id=? "+"order by product_id";
    PreparedStatement ps=con.prepareStatement(selectStatement);
    try{        
      ps.setString(1, order_id);
      ResultSet rs=ps.executeQuery();
      orderItems=new ArrayList();
      while(rs.next()){
        String product_id=rs.getString(1);
        int quantity=rs.getInt(2);
        orderItems.add( new OrderItem(order_id, product_id, quantity));
      }
}
finally{  
      ps.close();
    }  
  }
// ���¶�����¼
  private void storeOrder() throws SQLException{
    String updateStatement ="update OrderTBL set user_id=?, totalPrice=? "+
      "where order_id=?";
    PreparedStatement ps=con.prepareStatement(updateStatement);
    int rowCount;
    try{
      ps.setString(1, user_id);
      ps.setInt(2, totalPrice);
      ps.setString(3, order_id);
      rowCount=ps.executeUpdate();
}
finally{
      ps.close();
      }
    if(rowCount == 0){
      throw new EJBException("Storing "+order_id+ " failed.");
    }
  }
}
