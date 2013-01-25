// Dispatch Proxies 
package Lot123; 
import com.ibm.bridge2java.*; 
import java.util.Date; 
public class Application extends Dispatch implements COMconstants
{
	public static final String clsid = "{29130071-2EED-1069-BF5D-00DD011186B7}";
	public Application(){
		super(clsid); 
	}
	public Application(String clsidin) {
		super(clsidin); 
	}
	public Application(int IDispatch) {
		super(IDispatch); 
	}
	public Application(Object theObject) {
		super(theObject); 
	}
	public Application(int canvasHWND, int nullval) {
		super(clsid, canvasHWND); 
	}
	public Application(String clsidin, int canvasHWND, int nullval) {
		super(clsidin, canvasHWND); 
	}
	public String ExtendedName(Object nametype) {
		Jvariant args[] = {new Jvariant(nametype,VT_VARIANT)}; 
		return invoke_method(args, 0x1000300, DISPATCH_METHOD).StringVal();
	}
	public void Goto() {
		invoke_method_void(null, 0x1000304, DISPATCH_METHOD); 
	}
	public Object get_Run() {
		return invoke_method(null, 0x100ea00, DISPATCH_PROPERTYGET).ObjectVal();
	}
	public void set_Run(Object value) {
		Jvariant args[] = {new Jvariant(value, VT_VARIANT)}; 
		invoke_method_void(args, 0x100ea00, DISPATCH_PROPERTYPUT); 
	}
	public String get_ProductVersion() {
		return invoke_method(null, 0x14c0211, DISPATCH_PROPERTYGET).StringVal();
	}
	public void set_ProductVersion(String value) {
		Jvariant args[] = {new Jvariant(value, VT_BSTR)}; 
		invoke_method_void(args, 0x14c0211, DISPATCH_PROPERTYPUT); 
	}
}
