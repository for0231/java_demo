public class counter { 
 //初始化JavaBean的成员变量 
int count = 0; 
public counter() { 
} 
public int getCount() { 
//计数操作，每一次请求都进行计数器加一 
count++; 
return this.count; 
}  
public void setCount(int count) { 
this.count = count; 
} 
}