package com.stanley.memmap;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TestMemMapFrame extends JFrame {
  private JButton btnBroadCast;
  private JTextArea textArea;

  private int mapFilePtr;
  private int viewPtr;
  public final int dwMemFileSize = 2 * 1024;
  public static final String fileMappingObjName = "Mem_Map_File-{70122C30-0239-4f98-9D21-36885C8A8121}";

  //Construct the frame
  public TestMemMapFrame() {
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      init();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  //Component initialization
  private void init() throws Exception  {
    JPanel contentPane = (JPanel) getContentPane();
    contentPane.setLayout(new BorderLayout());

    mapFilePtr = MemMapFile.createFileMapping(MemMapFile.PAGE_READWRITE,
        0, dwMemFileSize, fileMappingObjName);

    if(mapFilePtr != 0) {
      viewPtr = MemMapFile.mapViewOfFile(mapFilePtr,
                                         MemMapFile.FILE_MAP_READ |
                                         MemMapFile.FILE_MAP_WRITE,
                                         0, 0, 0);
    }

    textArea = new JTextArea();
    contentPane.add(textArea, BorderLayout.CENTER);

    btnBroadCast = new JButton("Write and BroadCast");
    btnBroadCast.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if(viewPtr != 0) {
          MemMapFile.writeToMem(viewPtr, textArea.getText());
          MemMapFile.broadcast();
        }
      }
    });
    JPanel panelBtn = new JPanel();
    panelBtn.add(btnBroadCast);
    contentPane.add(panelBtn, BorderLayout.SOUTH);

    setSize(new Dimension(400, 300));
    setTitle("Memory Mapped File - Java Server");
  }

  private void destroy() {
    if(viewPtr != 0) MemMapFile.unmapViewOfFile(viewPtr);
    if(mapFilePtr != 0) MemMapFile.closeHandle(mapFilePtr);
  }
  //Overridden so we can exit when window is closed
  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      destroy();
      System.exit(0);
    }
  }
}