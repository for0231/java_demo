import java.lang.ref.*; 
public class MyReference extends SoftReference {
	public MyReference( Object referent ) {
		super( referent ); 
	}
	public MyReference( Object referent, ReferenceQueue q ) {
		super( referent, q ); 
	}
	public String toString() {
		return String.valueOf(get());
	}
}
