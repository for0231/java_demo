public class counter { 
 //��ʼ��JavaBean�ĳ�Ա���� 
int count = 0; 
public counter() { 
} 
public int getCount() { 
//����������ÿһ�����󶼽��м�������һ 
count++; 
return this.count; 
}  
public void setCount(int count) { 
this.count = count; 
} 
}