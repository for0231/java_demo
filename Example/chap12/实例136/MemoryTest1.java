import java.util.*; 
public class MemoryTest1 {
	public static void main( String[] args ) {
		ArrayList blocks = new ArrayList();
		int size = 65536; 
		for ( int id=0; true; id++ ) {
			blocks.add( new MemoryBlock(id,size) ); 
			System.out.println( "blocks: "+blocks ); 
			size *= 2; 
		}
	}
}
