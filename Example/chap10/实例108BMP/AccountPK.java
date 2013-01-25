package Account;
import java.io.Serializable;
// 账户的主键类
public class AccountPK implements java.io.Serializable{
 public String accountID;
public AccountPK(String id){
   this.accountID= id ;
}
public AccountPK(){
}
// 返回被存储的域
public String toString(){
 return accountID;
}
// 主键存储在一个Hash表中
public int hashCode(){
  return accountID.hashCode();
}
// 比较两个主键，来决定实体Bean是否代表同一个数据库数据
public boolean equals(Object account){
  return ((AccountPK)account).accountID.equals(accountID);
}
