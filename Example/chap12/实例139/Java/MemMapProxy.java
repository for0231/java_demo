package com.stanley.memmap;

public class MemMapProxy {
  static {
    System.loadLibrary("MemMapProxyLib");
  }
  private MemMapFileObserver observer;
  public MemMapProxy(MemMapFileObserver observer) {
    this.observer = observer;
    init();
  }

  public void fireDataReadyEvent() {
    observer.onDataReady();
  }

  private native boolean init();
  public native void destroy();
}