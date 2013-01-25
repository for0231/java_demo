//实现Enroll的具体功能
package enroll.ejb;

import java.sql.*;
import javax.sql.*;
import java.util.*;
import javax.ejb.*;
import javax.naming.*;

public class EnrollEJB implements EntityBean{

  public String student_id;
  public ArrayList courseItems;
  private Connection con;
  private String dbJndi="java:comp/env/jdbc/ExampleDB";
  private String dbId="guest";
  private String dbPassword="guest123";
  private EntityContext context;

  public void setEntityContext(EntityContext context){

    this.context=context;
    try{
      getConnection();
    }catch(Exception ex){
      throw new EJBException(
        "Connect to DB failed:"+ex.getMessage());
    }
  }

  public void unsetEntityContext(){

    try{
      con.close();
    }catch(SQLException ex){
      throw new EJBException(
        "Close DB Connection failed:"+ex.getMessage());
    }
  }

  public String ejbCreate(
    String student_id, ArrayList courseItems)
    throws CreateException{
    
    if(courseItems==null||courseItems.size()==0)
      throw new CreateException(
        "ejbCreate exception : no any item");
        
    this.student_id=student_id; 
         
    try{
      enroll(courseItems);
    }catch(Exception ex){
      throw new EJBException(
        "ejbCreate exception: "+ex.getMessage());
    }

    this.courseItems=courseItems;
    
    return student_id;
  }

  public void ejbPostCreate(
    String student_id, ArrayList courseItems){ 
  }
     
  public String ejbFindByPrimaryKey(String student_id) 
    throws ObjectNotFoundException, FinderException{
    
    try{
      if(!selectByStudentId(student_id))
        throw new ObjectNotFoundException(
          student_id + "not found!");
    }catch(Exception ex){
      throw new FinderException(
        "ejbFindByPrimaryKey exception:"+
        ex.getMessage());
    }
    
    return student_id;  
  }
  
  public Collection ejbFindAll()
    throws FinderException {
    Collection result;

    try{
      result=selectAll();
    }catch(Exception ex){
      throw new FinderException(ex.getMessage());
    }

    if(result.isEmpty()){
      throw new ObjectNotFoundException("No rows found.");
    }else{
      return result;
    }
  }
      
  public ArrayList getCourseItems(){
  	
    return courseItems;
  }


  public String getStudent_id(){

    return student_id;
  }
    
  public void replaceCourseItems(ArrayList courseItems){
    if(courseItems==null||courseItems.size()==0)
      throw new EJBException(
        "replaceCourseItems exception : no any item");
        
    try{
      unenroll();
      enroll(courseItems);
    }catch(Exception ex){
      throw new EJBException(
        "ejbLoad exception:"+ex.getMessage());
    }
         
    this.courseItems=courseItems;
  }

  public void ejbLoad(){
    try{
      loadCourses();
    }catch(Exception ex){
      throw new EJBException(
        "ejbLoad exception:"+ex.getMessage());
    }
  }

  public void ejbStore(){
  }
  
  public void ejbActivate(){

    student_id=(String)context.getPrimaryKey();
  }

  public void ejbPassivate(){

    student_id=null;
  }	
   
  public void ejbRemove(){
    try{
      unenroll();
    }catch(Exception ex){
      throw new EJBException(
        "ejbRemove exception:"+ex.getMessage());
    }

  } 

  //=============================================
  
  //取得资料库连结
  private void getConnection() 
    throws NamingException, SQLException{
    
    InitialContext ic=new InitialContext();
    DataSource ds=(DataSource) ic.lookup(dbJndi);
    con= ds.getConnection(dbId, dbPassword);
  }

  private void enroll(ArrayList courseItems)
    throws SQLException{

    String insertStatement =
      "insert into EnrollTBL values(? ,?)";
    PreparedStatement ps=
      con.prepareStatement(insertStatement);
    try{ 
      //逐笔将选课项目新增至EnrollTBL表格    
      ps.setString(1, this.student_id);  
      for(int i=0; i<courseItems.size(); i++){
        String course_id=(String)courseItems.get(i);
        ps.setString(2, course_id);
        ps.executeUpdate();
      }
    }finally{  
      ps.close();
    }      
  }

  private boolean selectByStudentId(String student_id) 
    throws SQLException{

    String sqlStatement=
      "select distinct student_id from EnrollTBL "+
      "where student_id=?";
    PreparedStatement ps=
      con.prepareStatement(sqlStatement);
    try{  
      ps.setString(1, student_id);
      ResultSet rs=ps.executeQuery();
      if(rs.next())
        return true;
      return false;
    }finally{
      ps.close();
    }
  }
   
  private void unenroll() 
    throws SQLException{

    String deleteStatement =
      "delete from EnrollTBL  "+
      "where student_id=?";
    PreparedStatement ps =
      con.prepareStatement(deleteStatement);
    try{
      ps.setString(1, student_id);
      ps.executeUpdate();
    }finally{  
      ps.close();
    }  
  }

  private void loadCourses()
    throws SQLException{

    String selectStatement =
      "select course_id "+
      "from EnrollTBL where student_id=? ";
    PreparedStatement ps=
      con.prepareStatement(selectStatement);
    try{        
      ps.setString(1, student_id);
      ResultSet rs=ps.executeQuery();

      courseItems=new ArrayList();
      while(rs.next()){
        String course_id=rs.getString(1);
        courseItems.add(course_id);
      }
    }finally{  
      ps.close();
    }  
  }
  
  private Collection selectAll() 
    throws SQLException{

    String sqlStatement=
      "select distinct student_id " +
      "from EnrollTBL order by student_id";
    PreparedStatement ps=
      con.prepareStatement(sqlStatement);
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
}
