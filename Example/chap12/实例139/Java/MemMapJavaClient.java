package com.stanley.memmap;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MemMapJavaClient extends JFrame implements MemMapFileObserver{
  protected JButton btnClose;
  protected JTextArea textArea;
  protected MemMapProxy proxy;
  public static final String fileMappingObjName = "Mem_Map_File-{70122C30-0239-4f98-9D21-36885C8A8121}";

  //Construct the frame
  public MemMapJavaClient() {
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
    JPanel contentPane = (JPanel) this.getContentPane();
    contentPane.setLayout(new BorderLayout());

    textArea = new JTextArea();
    contentPane.add(textArea, BorderLayout.CENTER);
    btnClose = new JButton("Close");
    btnClose.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
	destroy();
      }
    });

    JPanel panelBtn = new JPanel();
    panelBtn.add(btnClose);
    contentPane.add(panelBtn, BorderLayout.SOUTH);

    proxy = new MemMapProxy(this);

    setSize(new Dimension(400, 300));
    setTitle("Test Memory Map File - Java Client");
  }

  private void destroy() {
    proxy.destroy();
    System.exit(0);
  }

  public void onDataReady() {
    int mapFilePtr = MemMapFile.openFileMapping(MemMapFile.FILE_MAP_READ, false,
        fileMappingObjName);
    if(mapFilePtr != 0) {
      int viewPtr = MemMapFile.mapViewOfFile(mapFilePtr,
	  MemMapFile.FILE_MAP_READ, 0, 0, 0);
      if(viewPtr != 0) {
	String content = MemMapFile.readFromMem(viewPtr);
	textArea.setText(content);
	MemMapFile.unmapViewOfFile(viewPtr);
      }
      MemMapFile.closeHandle(mapFilePtr);
    }
  }
  //Overridden so we can exit when window is closed
  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      destroy();
    }
  }
}