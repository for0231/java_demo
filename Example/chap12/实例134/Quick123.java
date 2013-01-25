import Lot123.*; 
public class Quick123
{
	public static void main(java.lang.String[] args) {
		Application app; 
		Ranges ranges; 
		Document doc; 
		Range rangeA1, rangeA2; 
		try {
			// ����com���� 
			com.ibm.bridge2java.OleEnvironment.Initialize();
			// ����һ���µ���������"123"���ĵ�
			doc = new Document();
			// ��� application object. 
			app = new Application(doc.get_Parent());
			// ʹapplication�ɼ�. 
			app.set_Visible(new Boolean("true"));
			// ��Range workbook. 
			doc = new Document(app.NewDocument());
			// ������е� ranges. 
			ranges = new Ranges(doc.get_Ranges());
			// ���cell A1. 
			rangeA1 = new Range(ranges.Item(new String("A1")) ); 
			// ����cell A1������
			rangeA1.set_Contents("This is a test");
			// ��� cell A2. 
			rangeA2 = new Range(ranges.Item(new String("A2")) ); 
			// �� cell A1 �����ݸ��Ƹ� cell A2
			rangeA1.QuickCopy(rangeA2); 
			// �ȴ�һ��
			Thread.sleep(1000); 
			// �Ƴ�"123"���Ҳ�����
			app.Quit("false");
		} catch (com.ibm.bridge2java.ComException e)  {
			// ����com�쳣
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
