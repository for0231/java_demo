package javasecurity;
import java.security.*;
import javax.crypto.*;
public class DigitalSignatureExample
{
	public static void main(String[] args) throws Exception
	{
		// �������������õ�����
		if (args.length != 1)
		{
			System.err.println
				("Usage: java DigitalSignature1Example text");
			System.exit(1);
		}
		byte[] plainText = args[0].getBytes("UTF8");
		// �õ�MD5��ϢժҪ�����Ҽ�������ժҪ
		MessageDigest messageDigest = 
			MessageDigest.getInstance("MD5");
		System.out.println
			("\n" + messageDigest.getProvider().getInfo());
		messageDigest.update(plainText);
		byte[] md = messageDigest.digest();
		System.out.println("\nDigest: ");
		System.out.println(new String(md, "UTF8"));
		// ����RSA��Կ��
		System.out.println("\nStart generating RSA key");
		KeyPairGenerator keyGen=KeyPairGenerator.getInstance("RSA");
		keyGen.initialize(1024);
		KeyPair key = keyGen.generateKeyPair();
		System.out.println("Finish generating RSA key");
		// �õ�RSA cipherͬʱ�г��㷨���ṩ��
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		System.out.println("\n" + cipher.getProvider().getInfo());
		// ����Ϣ���ܲ�������RSA˽Կ����ǩ��
		System.out.println("\nStart encryption");
		cipher.init(Cipher.ENCRYPT_MODE, key.getPrivate());
		byte[] cipherText = cipher.doFinal(md);
		System.out.println("Finish encryption: ");
		System.out.println(new String(cipherText, "UTF8"));
		// ����RSA��Կ���н���
		System.out.println("\nStart decryption");
		cipher.init(Cipher.DECRYPT_MODE, key.getPublic());
		byte[] newMD = cipher.doFinal(cipherText);
		System.out.println("Finish decryption: ");
		System.out.println(new String(newMD, "UTF8"));
		// ���½�����Ϣ����
		System.out.println("\nStart signature verification");
		messageDigest.reset();
		messageDigest.update(plainText);
		byte[] oldMD = messageDigest.digest();
		// ���ǩ��ǰ�������Ƿ���ͬ
		int len = newMD.length;
		if (len > oldMD.length)
		{
			System.out.println("Signature failed, length error");
			System.exit(1);
		}
		for (int i = 0; i < len; ++i)
		{	
			if (oldMD[i] != newMD[i])
			{
				System.out.println("Signature failed, element error");
				System.exit(1);
			}
		}
		System.out.println("Signature verified");
	}
}
