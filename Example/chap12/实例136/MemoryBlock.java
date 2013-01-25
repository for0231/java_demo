// MemoryBlock�ظ��ط����ڴ�飬��С��64KB��ʼ��ÿ�ζ���ǰһ�δ�һ��������MemoryBlock��������
// ��һ��ArrayList������ʹ��һ������ѭ����ÿ��ѭ�������ӡ���ArrayList�����ݡ�
public class MemoryBlock { 
	int id;  
	int size; 
	byte[] block; 
	public MemoryBlock( int id, int size ) {   
		//�����ڴ������ʼ״̬
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
