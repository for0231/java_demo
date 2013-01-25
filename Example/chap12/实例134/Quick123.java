import Lot123.*; 
public class Quick123
{
	public static void main(java.lang.String[] args) {
		Application app; 
		Ranges ranges; 
		Document doc; 
		Range rangeA1, rangeA2; 
		try {
			// 建立com环境 
			com.ibm.bridge2java.OleEnvironment.Initialize();
			// 创建一个新的用来启动"123"的文档
			doc = new Document();
			// 获得 application object. 
			app = new Application(doc.get_Parent());
			// 使application可见. 
			app.set_Visible(new Boolean("true"));
			// 打开Range workbook. 
			doc = new Document(app.NewDocument());
			// 获得所有的 ranges. 
			ranges = new Ranges(doc.get_Ranges());
			// 获得cell A1. 
			rangeA1 = new Range(ranges.Item(new String("A1")) ); 
			// 设置cell A1的内容
			rangeA1.set_Contents("This is a test");
			// 获得 cell A2. 
			rangeA2 = new Range(ranges.Item(new String("A2")) ); 
			// 把 cell A1 的内容复制给 cell A2
			rangeA1.QuickCopy(rangeA2); 
			// 等待一秒
			Thread.sleep(1000); 
			// 推出"123"并且不保存
			app.Quit("false");
		} catch (com.ibm.bridge2java.ComException e)  {
			// 处理com异常
			System.out.println( "COM Exception:" ); 
			System.out.println( Long.toHexString((e.getHResult())) ); 
			System.out.println( e.getMessage() ); 
		} catch (Exception e) {
			System.out.println("message: " + e.getMessage());
		} finally {
			app = null; 
			com.ibm.bridge2java.OleEnvironment.UnInitialize();
		}
	}
}
