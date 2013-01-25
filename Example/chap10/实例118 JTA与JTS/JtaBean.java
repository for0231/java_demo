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
  //һ��������ʾ������������ֻ��ʵ��
  public String myTransation() {
    UserTransaction userTran = sessionContext.getUserTransaction();
    String ret="myTransation is start\n";
    try{
      userTran.begin();
      ret=ret+"The first business is begin:\n";
      //����ŵ�һ��ҵ�񷽷�first business method()
      ret=ret+"The first business is done:\n";
      ret=ret+"The second business is begin:\n";
      //����ŵڶ���ҵ�񷽷�second business method()
       ret=ret+"The second business is done:\n";
      userTran.commit();
      ret=ret+"myTransation is over!\n";
    }catch(Exception e){
      try{
      userTran.rollback();
      }catch(SystemException sysex){
        throw new EJBException( "Rollbackʧ�ܣ�"+sysex.toString());
      }
    }
    return ret;
  }
}