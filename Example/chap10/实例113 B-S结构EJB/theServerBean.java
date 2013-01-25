package bsejb;

import javax.ejb.*;
import java.util.Date;
public class theServerBean implements SessionBean {
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
  public String BServer() {
    String retStr="The server is running at the time:";
    retStr=retStr+(new Date().toString())+"\n";
    return retStr;
  }
}