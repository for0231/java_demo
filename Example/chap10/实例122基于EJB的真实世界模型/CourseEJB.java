//CourseEJB的具体实现
package enroll.ejb;

import java.util.*;
import javax.ejb.*;

public class CourseEJB implements EntityBean{

  public String course_id;
  public String name; 
  private EntityContext context;
  
  public void setEntityContext(EntityContext context){
    this.context = context;
  }
   
  public void unsetEntityContext(){ 
    context = null;
  }

  public String ejbCreate(
    String course_id, String name)
    throws DuplicateKeyException, CreateException{

    this.course_id = course_id;
    this.name=name;
     
    return null;
  }
   
  public void ejbPostCreate(
    String course_id, String name){
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
    course_id = (String)context.getPrimaryKey();
  }

  public void ejbPassivate(){
    course_id = null;
    name = null;
  }

  public void ejbRemove(){ 
  }
}
