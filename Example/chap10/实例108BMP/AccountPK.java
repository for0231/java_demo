package Account;
import java.io.Serializable;
// �˻���������
public class AccountPK implements java.io.Serializable{
 public String accountID;
public AccountPK(String id){
   this.accountID= id ;
}
public AccountPK(){
}
// ���ر��洢����
public String toString(){
 return accountID;
}
// �����洢��һ��Hash����
public int hashCode(){
  return accountID.hashCode();
}
// �Ƚ�����������������ʵ��Bean�Ƿ����ͬһ�����ݿ�����
public boolean equals(Object account){
  return ((AccountPK)account).accountID.equals(accountID);
}
