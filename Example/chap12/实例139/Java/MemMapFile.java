package com.stanley.memmap;

public class MemMapFile {
  public static final int PAGE_READONLY = 0x02;
  public static final int PAGE_READWRITE = 0x04;
  public static final int PAGE_WRITECOPY = 0x08;

  public static final int FILE_MAP_COPY = 0x0001;
  public static final int FILE_MAP_WRITE = 0x0002;
  public static final int FILE_MAP_READ = 0x0004;

  static {
    System.loadLibrary("MemMapLib");
  }

  private MemMapFile() {
  }

  public static native int createFileMapping(int lProtect, int dwMaximumSizeHigh, int dwMaximumSizeLow, String name);
  public static native int openFileMapping(int dwDesiredAccess, boolean bInheritHandle, String name);
  public static native int mapViewOfFile(int hFileMappingObj, int dwDesiredAccess, int dwFileOffsetHigh, int dwFileOffsetLow, int dwNumberOfBytesToMap);
  public static native boolean unmapViewOfFile(int lpBaseAddress);
  public static native void writeToMem(int lpBaseAddress, String content);
  public static native String readFromMem(int lpBaseAddress);
  public static native boolean closeHandle(int hObject);

  public static native void broadcast();
}