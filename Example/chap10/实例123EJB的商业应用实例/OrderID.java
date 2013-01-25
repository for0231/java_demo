package order.common;
// 这个类负责产生订购单编号
public class OrderId{
public OrderId()
{ }
// 订购单编号有数字和字母组成
static char[] digits = { 
    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 
    'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 
'U', 'V', 'W', 'X', 'Y', 'Z' };
// 随机生成订购单编号			       
static public String genOrderId(){
int temp;
    String orderid = "";
    for( int i=0; i<8; i++ ){
      temp = ( new Double( Math.random() *  997 ) ).intValue() % 36;
      orderid += String.valueOf( digits[ temp ] );
}
// 返回订购单编号
    return  orderid;
  }
}
