//ʵ��EnrollSession�ľ��幦��
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
      �ȼ��student_id�Ļ��������Ƿ�����StudentTBL��,
      �������������ע��
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
  
  //ȡ��ѧ������
  public String getStudentName(){
    return name;   
  }
  
  //ע��ѡ������
  public void enroll(ArrayList courseItems){
    
    Enroll enroll=null;
    
    //����Ƿ��Ѿ�ע�����
    try{
      enroll=eHome.findByPrimaryKey(student_id);
    }catch( ObjectNotFoundException onfex){
    }catch (Exception ex){
    }
    
    //����Ѿ�ע����������ע������,��������ע������
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
  
  //�Ƴ�ע������
  public void unenroll(){

    try{
      Enroll enroll=eHome.findByPrimaryKey(student_id);
      enroll.remove();
    }catch (Exception ex){
      throw new EJBException("unenroll: "+
        ex.getMessage());
    }
  }

  //�Ƴ�ѧ���������Ϻ�ע������
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
  
  //�Ƴ�ѧ����ĳ���γ�����
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
