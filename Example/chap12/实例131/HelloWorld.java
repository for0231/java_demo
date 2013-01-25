class HelloWorld {
    public native void displayHelloWorld();// 定义本地方法，注意native关键字
    static {
        System.loadLibrary("hello");// 调入本地库
    }    
    public static void main(String[] args) {
        new HelloWorld().displayHelloWorld();
    }
}
