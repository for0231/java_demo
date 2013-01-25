//实现Student的具体功能
package enroll.ejb;

import java.util.*;
import javax.ejb.*;

public class StudentEJB implements EntityBean{

  public String student_id;
  public String name; 
  private EntityContext context;
  
  public void setEntityContext(EntityContext context){
    this.context = context;
  }
   
  public void unsetEntityContext(){ 
    context = null;
  }

  public String ejbCreate(
    String student_id, String name)
    throws DuplicateKeyException, CreateException{

    this.student_id = student_id;
    this.name=name;
     
    return null;
  }
   
  public void ejbPostCreate(
    String student_id, String name){
  }
  
  public void setName(String name){
    this.name = name;
  }

  public String getName(){
    return name;
  }
  
  public void ejbLoad(){ 
  }

  public void ejbStore(){ 
  }

  public void ejbActivate(){ 
    student_id = (String)context.getPrimaryKey();
  }

  public void ejbPassivate(){
    student_id = null;
    name = null;
  }

  public void ejbRemove(){ 
  }
}
