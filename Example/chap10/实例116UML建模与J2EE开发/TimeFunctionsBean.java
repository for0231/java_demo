package simpleejb;

import javax.ejb.*;

public class TimeFunctionsBean implements SessionBean {
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
  public long getTime() {
   return  System.currentTimeMillis();
  }
}