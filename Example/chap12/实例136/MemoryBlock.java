// MemoryBlock重复地分配内存块，大小从64KB开始，每次都比前一次大一倍。所有MemoryBlock都被保存
// 到一个ArrayList，并且使用一个无限循环，每次循环都会打印这个ArrayList的内容。
public class MemoryBlock { 
	int id;  
	int size; 
	byte[] block; 
	public MemoryBlock( int id, int size ) {   
		//设置内存区块初始状态
		this.id = id;  
		this.size = size;    
		block = new byte[size];    
		System.out.println( "MemoryBlock created: "+this ); 
	}  
	public String toString() {  
		return "{id="+id+",size="+size+"}";   
	} 
	protected void finalize() {    
		System.out.println( "MemoryBlock finalized: "+this ); 
	} 
}
