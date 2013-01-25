package jtajts;

import javax.ejb.*;
import javax.transaction.*;
import javax.naming.*;

public class JtaBean implements SessionBean {
  SessionContext sessionContext;
  public void ejbCreate() throws CreateException {
  }
  public void ejbRemove() {
  }
  public void ejbActivate() {
  }
  public void ejbPassivate() {
  }
  public void setSessionContext(SessionContext sessionContext) {
    this.sessionContext = sessionContext;
  }
  //一个事务处理示例函数，这里只是实例
  public String myTransation() {
    UserTransaction userTran = sessionContext.getUserTransaction();
    String ret="myTransation is start\n";
    try{
      userTran.begin();
      ret=ret+"The first business is begin:\n";
      //这里放第一个业务方法first business method()
      ret=ret+"The first business is done:\n";
      ret=ret+"The second business is begin:\n";
      //这里放第二个业务方法second business method()
       ret=ret+"The second business is done:\n";
      userTran.commit();
      ret=ret+"myTransation is over!\n";
    }catch(Exception e){
      try{
      userTran.rollback();
      }catch(SystemException sysex){
        throw new EJBException( "Rollback失败："+sysex.toString());
      }
    }
    return ret;
  }
}