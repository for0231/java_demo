package address;

import java.io.*;

public class AddressesPK implements Serializable {

  public String firstName;
  public String lastName;

  public AddressesPK() {
  }
	//设置复合键
  public AddressesPK(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }
  //用于匹配对象
  public boolean equals(Object obj) {
    if (this.getClass().equals(obj.getClass())) {
      AddressesPK that = (AddressesPK) obj;
      return this.firstName.equals(that.firstName) && this.lastName.equals(that.lastName);
    }
    return false;
  }
  public int hashCode() {
    return (firstName + lastName).hashCode();
  }
}