class HelloWorld {
    public native void displayHelloWorld();// ���屾�ط�����ע��native�ؼ���
    static {
        System.loadLibrary("hello");// ���뱾�ؿ�
    }    
    public static void main(String[] args) {
        new HelloWorld().displayHelloWorld();
    }
}
