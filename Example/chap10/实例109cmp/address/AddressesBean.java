package address;

import java.rmi.*;
import javax.ejb.*;
//Addresses EJB��ʵ��
public class AddressesBean implements EntityBean {
  private EntityContext entityContext;
  public String firstName;
  public String lastName;
  public String address;
  public String city;
  public String state;
  public String zip;
  // ejbCreate�������ڳ�ʼ��һ��EJBʵ��
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
  //�õ�FirstName
  public String getFirstName() {
    return firstName;
  }
   //�õ�getLastName
  public String getLastName() {
    return lastName;
  }
  //�õ�address
  public String getAddress() {
    return address;
  }
  //����address
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