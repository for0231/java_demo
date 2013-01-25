package javasecurity;
import java.security.*;
import javax.crypto.*;
// 用RSA公钥算法进行加密
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
		// 生成RAS密钥
		System.out.println("\nStart generating RSA key");
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		keyGen.initialize(1024);
		KeyPair key = keyGen.generateKeyPair();
		System.out.println("Finish generating RSA key");
		// 得到RSA cipher 对象，同是打印出算法的提供者   
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		System.out.println("\n" + cipher.getProvider().getInfo());
		// 使用公钥对明文进行加密
		System.out.println("\nStart encryption");
		cipher.init(Cipher.ENCRYPT_MODE, key.getPublic());
		byte[] cipherText = cipher.doFinal(plainText);
		System.out.println("Finish encryption: ");
		System.out.println(new String(cipherText, "UTF8"));
		// 使用私钥对加密密文进行解密
		System.out.println("\nStart decryption");
		cipher.init(Cipher.DECRYPT_MODE, key.getPrivate());
		byte[] newPlainText = cipher.doFinal(cipherText);
		System.out.println("Finish decryption: ");
		System.out.println(new String(newPlainText, "UTF8"));
	}
}
