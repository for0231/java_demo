
import java.lang.*;
import java.awt.*;
import java.io.*;
import java.awt.datatransfer.*;
import java.awt.event.*;


public class EditorFrame extends Frame implements ActionListener {
   TextArea textArea = new TextArea();
   MenuBar menuBar = new MenuBar();
   Menu fileMenu = new Menu("File");
   MenuItem newItem = new MenuItem("New");
   MenuItem openItem = new MenuItem("Open");
   MenuItem saveItem = new MenuItem("Save");
   MenuItem saveAsItem = new MenuItem("Save As");
   MenuItem exitItem = new MenuItem("Exit");
   Menu editMenu = new Menu("Edit");
   MenuItem selectItem = new MenuItem("Select All");
   MenuItem copyItem = new MenuItem("Copy");
   MenuItem cutItem = new MenuItem("Cut");
   MenuItem pasteItem = new MenuItem("Paste");
   String fileName = null;
   Toolkit toolKit=Toolkit.getDefaultToolkit();
   Clipboard clipBoard=toolKit.getSystemClipboard();
   
    private FileDialog openFileDialog
= new FileDialog(this,"Open File",FileDialog.LOAD);
  private FileDialog saveAsFileDialog
= new FileDialog(this,"Save File As",FileDialog.SAVE);


  public EditorFrame(){
    setTitle("NotePad");
    setFont(new Font("Times New Roman",Font.PLAIN,12));
    setBackground(Color.white);
    setSize(400,300);
    fileMenu.add(newItem);
    fileMenu.add(openItem);
    fileMenu.addSeparator();
    fileMenu.add(saveItem);
    fileMenu.add(saveAsItem);
    fileMenu.addSeparator();
    fileMenu.add(exitItem);
    editMenu.add(selectItem);
    editMenu.addSeparator();
    editMenu.add(copyItem);
    editMenu.add(cutItem);
    editMenu.add(pasteItem);
    menuBar.add(fileMenu);
    menuBar.add(editMenu);
    setMenuBar(menuBar);
    add(textArea);
    addWindowListener(new WindowAdapter(){
                      public void windowClosing(WindowEvent e){
                          System.exit(0);
                        }
                      });
    newItem.addActionListener(this);
    openItem.addActionListener(this);
    saveItem.addActionListener(this);
    saveAsItem.addActionListener(this);
    exitItem.addActionListener(this);
    selectItem.addActionListener(this);
    copyItem.addActionListener(this);
    cutItem.addActionListener(this);
    pasteItem.addActionListener(this);
  }

  public void actionPerformed(ActionEvent e) {
    Object eventSource = e.getSource();
    if(eventSource == newItem){
      textArea.setText("");
    }else if(eventSource == openItem){
      openFileDialog.show();
      fileName = openFileDialog.getDirectory()+openFileDialog.getFile();
      if(fileName != null)
        readFile(fileName);
    }else if (eventSource == saveItem){
      if(fileName != null)
        writeFile(fileName);
    }else if(eventSource == saveAsItem){
      saveAsFileDialog.show();
      fileName = saveAsFileDialog.getDirectory()+saveAsFileDialog.getFile();
      if (fileName!= null)
        writeFile(fileName);
    }else if(eventSource == selectItem){
       textArea.selectAll();
    }else if(eventSource == copyItem){
       String text=textArea.getSelectedText();
       StringSelection selection=new StringSelection(text);
       clipBoard.setContents(selection,null);
    }else if(eventSource == cutItem){
       String text=textArea.getSelectedText();
       StringSelection selection=new StringSelection(text);
       clipBoard.setContents(selection,null);
       textArea.replaceRange("",textArea.getSelectionStart(),textArea.getSelectionEnd());
    }else if(eventSource == pasteItem){
       Transferable contents=clipBoard.getContents(this);
       if(contents==null) return;
       String text;
       text="";
       try{
            text=(String)contents.getTransferData(DataFlavor.stringFlavor);
        }catch(Exception exception){
        }
       textArea.replaceRange(text,textArea.getSelectionStart(),textArea.getSelectionEnd());
    }else if(eventSource == exitItem){
      System.exit(0);
    }
  }

 
  public void readFile(String fileName){
    try{
      File file = new File(fileName);
      FileReader readIn = new FileReader(file);
      int size = (int)file.length();
      int charsRead = 0;
      char[] content = new char[size];
      while(readIn.ready())
        charsRead += readIn.read(content, charsRead, size - charsRead);
      readIn.close();
      textArea.setText(new String(content, 0, charsRead));
    }
    catch(IOException e){
      System.out.println("Error opening file");
    }
  }

  public void writeFile(String fileName){
    try{
      File file = new File (fileName);
      FileWriter writeOut = new FileWriter(file);
      writeOut.write(textArea.getText());
      writeOut.close();
    }
    catch(IOException e){
      System.out.println("Error writing file");
    }
  }

    public static void main(String[] args){
      Frame frame = new EditorFrame();
      frame.show();
   }
}
