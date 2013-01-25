package address;

import java.rmi.*;
import javax.ejb.*;
//Addresses EJB的实现
public class AddressesBean implements EntityBean {
  private EntityContext entityContext;
  public String firstName;
  public String lastName;
  public String address;
  public String city;
  public String state;
  public String zip;
  // ejbCreate函数用于初始化一个EJB实例
  public AddressesPK ejbCreate(String firstName, String lastName, String address, String city, String state, String zip) throws CreateException {
    this.firstName = firstName;
    this.lastName = lastName;
    this.address = address;
    this.city = city;
    this.state = state;
    this.zip = zip;
    return null;
  }
  public AddressesPK ejbCreate(String firstName, String lastName) throws CreateException {
    return ejbCreate(firstName, lastName, null, null, null, null);
  }
  public void ejbPostCreate(String firstName, String lastName, String address, String city, String state, String zip) throws CreateException {
  }
  public void ejbPostCreate(String firstName, String lastName) throws CreateException {
    ejbPostCreate(firstName, lastName, null, null, null, null);
  }
  public void ejbLoad() {
  }
  public void ejbStore() {
  }
  public void ejbRemove() throws RemoveException {
  }
  public void ejbActivate() {
  }
  public void ejbPassivate() {
  }
  public void setEntityContext(EntityContext entityContext) {
    this.entityContext = entityContext;
  }
  public void unsetEntityContext() {
    entityContext = null;
  }
  //得到FirstName
  public String getFirstName() {
    return firstName;
  }
   //得到getLastName
  public String getLastName() {
    return lastName;
  }
  //得到address
  public String getAddress() {
    return address;
  }
  //设置address
  public void setAddress(String address) {
    this.address = address;
  }
  public String getCity() {
    return city;
  }
  public void setCity(String city) {
    this.city = city;
  }
  public String getState() {
    return state;
  }
  public void setState(String state) {
    this.state = state;
  }
  public String getZip() {
    return zip;
  }
  public void setZip(String zip) {
    this.zip = zip;
  }
}