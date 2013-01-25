package javasecurity;
import java.security.*;
import javax.crypto.*;
// ��RSA��Կ�㷨���м���
public class PublicExample
{
	public static void main(String[] args) throws Exception
	{
		if (args.length != 1)
		{
			System.err.println("Usage: java PublicExample text");
			System.exit(1);
		}
		byte[] plainText = args[0].getBytes("UTF8");
		// ����RAS��Կ
		System.out.println("\nStart generating RSA key");
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		keyGen.initialize(1024);
		KeyPair key = keyGen.generateKeyPair();
		System.out.println("Finish generating RSA key");
		// �õ�RSA cipher ����ͬ�Ǵ�ӡ���㷨���ṩ��   
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		System.out.println("\n" + cipher.getProvider().getInfo());
		// ʹ�ù�Կ�����Ľ��м���
		System.out.println("\nStart encryption");
		cipher.init(Cipher.ENCRYPT_MODE, key.getPublic());
		byte[] cipherText = cipher.doFinal(plainText);
		System.out.println("Finish encryption: ");
		System.out.println(new String(cipherText, "UTF8"));
		// ʹ��˽Կ�Լ������Ľ��н���
		System.out.println("\nStart decryption");
		cipher.init(Cipher.DECRYPT_MODE, key.getPrivate());
		byte[] newPlainText = cipher.doFinal(cipherText);
		System.out.println("Finish decryption: ");
		System.out.println(new String(newPlainText, "UTF8"));
	}
}
