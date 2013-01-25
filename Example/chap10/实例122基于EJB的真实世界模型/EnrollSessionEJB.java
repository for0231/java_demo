//实现EnrollSession的具体功能
package enroll.ejb;

import javax.ejb.*;
import java.sql.*;
import javax.sql.*;
import java.util.*;
import javax.naming.*;
import java.rmi.RemoteException;
import javax.rmi.PortableRemoteObject;

public class EnrollSessionEJB implements SessionBean {
 
  private Connection con;
  private String dbJndi="java:comp/env/jdbc/ExampleDB";
  private String dbId="guest";
  private String dbPassword="guest123";
  public StudentHome sHome;
  public EnrollHome eHome;
  public String student_id;
  public String name;
  
  public EnrollSessionEJB(){
  }

  public void setSessionContext(SessionContext sc){

    try{
   
      Context initial = new InitialContext();
      Object objref=
      	initial.lookup("ejb/StudentEntityBean");
      sHome=(StudentHome)PortableRemoteObject
      	.narrow(objref, StudentHome.class);
      	     	
      objref=
      	initial.lookup("ejb/EnrollEntityBean"); 
      eHome=(EnrollHome)PortableRemoteObject
      	.narrow(objref, EnrollHome.class);
      	      
    }catch (Exception ex){
      throw new EJBException(
        "Unable to connect to database. "+
        ex.getMessage());
    }
  }  
  
  public void ejbCreate(String student_id)
    throws CreateException{
    
    /*
      先检查student_id的基本资料是否已在StudentTBL里,
      如果不在则不允许注册
    */
    try{
      Student student=sHome.findByPrimaryKey(student_id);
      name=student.getName();
    }catch (ObjectNotFoundException ex){
      throw new CreateException("Student: "
        +student_id+" not found in StudentTBL!");      
    }catch(Exception ex){
      throw new EJBException("unenroll: "+
        ex.getMessage());
    }  
        
    this.student_id=student_id;
  }
  
  //取得学生姓名
  public String getStudentName(){
    return name;   
  }
  
  //注册选课资料
  public void enroll(ArrayList courseItems){
    
    Enroll enroll=null;
    
    //检查是否已经注册过了
    try{
      enroll=eHome.findByPrimaryKey(student_id);
    }catch( ObjectNotFoundException onfex){
    }catch (Exception ex){
    }
    
    //如果已经注册过了则更新注册资料,否则新增注册资料
    try{
      if(enroll!=null)
        enroll.replaceCourseItems(courseItems);
      else
        eHome.create(student_id, courseItems);
    }catch (Exception ex){
      throw new EJBException("enroll: "+
        ex.getMessage());
    }
  }
  
  //移除注册资料
  public void unenroll(){

    try{
      Enroll enroll=eHome.findByPrimaryKey(student_id);
      enroll.remove();
    }catch (Exception ex){
      throw new EJBException("unenroll: "+
        ex.getMessage());
    }
  }

  //移除学生基本资料和注册资料
  public void deleteStudent()
    throws FinderException{

    try{
      Enroll enroll=
        eHome.findByPrimaryKey(student_id);
      Student student=
        sHome.findByPrimaryKey(student_id);
      enroll.remove();
      student.remove();
    }catch (Exception ex){
      throw new EJBException("deleteStudent: "+
        ex.getMessage());
    }
  }
  
  //移除学生的某个课程资料
  public void deleteCourse(String course_id){
  	
    PreparedStatement ps=null;
    
    try{
      getConnection();
      
      String deleteStatement =
        "delete from EnrollTBL  "+
        "where student_id=? and course_id=?";
        
      ps=con.prepareStatement(deleteStatement);
      ps.setString(1, student_id);
      ps.setString(2, course_id);
      ps.executeUpdate();
          
    }catch (Exception ex){
      throw new EJBException("deleteCourse: "+
        ex.getMessage());  
    }finally{
      try{ 
        ps.close();
        con.close();
      }catch(Exception ex){
        throw new EJBException("deleteCourse: "+
          ex.getMessage()); 
      }
    }  
  }

  public void ejbActivate(){
  }

  public void ejbPassivate(){
  }

  public void ejbRemove(){

    sHome=null;
    eHome=null;
  }

//---------------------------------------------

  private void getConnection() 
    throws NamingException, SQLException{
    
    InitialContext ic=new InitialContext();
    DataSource ds=(DataSource) ic.lookup(dbJndi);
    con= ds.getConnection(dbId, dbPassword);
  } 
}
