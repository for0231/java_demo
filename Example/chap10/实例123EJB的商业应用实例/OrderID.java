package order.common;
// ����ฺ��������������
public class OrderId{
public OrderId()
{ }
// ��������������ֺ���ĸ���
static char[] digits = { 
    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 
    'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 
'U', 'V', 'W', 'X', 'Y', 'Z' };
// ������ɶ��������			       
static public String genOrderId(){
int temp;
    String orderid = "";
    for( int i=0; i<8; i++ ){
      temp = ( new Double( Math.random() *  997 ) ).intValue() % 36;
      orderid += String.valueOf( digits[ temp ] );
}
// ���ض��������
    return  orderid;
  }
}
