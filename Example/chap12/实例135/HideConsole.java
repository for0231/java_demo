class HelloWorld {
    public native void displayHideConsole();// 定义本地方法
    static {
        System.loadLibrary("HideConsole");// 调入本地库
    }    
    public static void main(String[] args) {
        new HideConsole ().ActHideConsole ();
    }
}
