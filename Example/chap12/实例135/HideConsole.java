class HelloWorld {
    public native void displayHideConsole();// ���屾�ط���
    static {
        System.loadLibrary("HideConsole");// ���뱾�ؿ�
    }    
    public static void main(String[] args) {
        new HideConsole ().ActHideConsole ();
    }
}
