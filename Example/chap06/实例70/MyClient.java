//client端的程序如下：
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
public class MyClient extends Frame implements ActionListener,ItemListener
{
          MenuBar m_Menu_Bar;
	  Menu menuFile,menuEdit,menuHelp;
	  MenuItem mi_File_Open,mi_File_Close,mi_File_Exit,mi_Edit_Copy,mi_Edit_Paste;
	  MenuItem pi_New,pi_Del,pi_Pro,mi_Help_Sub;
	  CheckboxMenuItem mi_Edit_Cut;
	  PopupMenu popM;
	  

         Socket ClientSocket;
         PrintStream os;
         DataInputStream is;
         String s;
         Label MyLabel=new Label("                      ☆欢迎使用本系统为您提供服务☆");
         TextArea textarea;
         Button MyButton=new Button("发 送 消 息");

  public MyClient()
   {
                   setTitle("Client Window(客户端窗口)");
                   setLayout(new BorderLayout());
                  
                   this.addWindowListener(new WinAdptClient(this));
                   MyButton.addActionListener(this);
                  

                   textarea=new TextArea(13,55);
//8888888888888888888888888888888888888888888888888888888888888888888888888888//
                   popM=new PopupMenu();
	           pi_New=new MenuItem(" 新建 ");
	           pi_New.addActionListener(this);
	           popM.add(pi_New);
		   pi_Del=new MenuItem(" 删除 ");
		   pi_Del.addActionListener(this);
		   popM.add(pi_Del);
                   pi_Pro=new MenuItem(" 属性 ");
                   pi_Pro.addActionListener(this);
		   popM.add(pi_Pro);
		 
		    m_Menu_Bar=new MenuBar();
		   menuFile=new Menu("文件");
		   mi_File_Open=new MenuItem("打开");
		   mi_File_Open.setShortcut(new MenuShortcut('f'));
		   mi_File_Close=new MenuItem("关闭",new MenuShortcut('s'));
		   mi_File_Exit=new MenuItem("退出",new MenuShortcut('x'));

		   mi_File_Open.setActionCommand("打开");
		   mi_File_Close.setActionCommand("关闭");
		   mi_File_Exit.setActionCommand("退出");

		   mi_File_Open.addActionListener(this);
		   mi_File_Close.addActionListener(this);
		   mi_File_Exit.addActionListener(this);

		     menuFile.add(mi_File_Open);
		     menuFile.add(mi_File_Close);
		     menuFile.add(mi_File_Exit);

			 m_Menu_Bar.add(menuFile);


			 menuEdit=new Menu("编辑");
			  mi_Edit_Copy=new MenuItem("复制");
			  mi_Edit_Paste=new MenuItem("粘贴");
			  mi_Edit_Cut=new CheckboxMenuItem("CUT");
			  mi_Edit_Copy.setActionCommand("复制");
			  mi_Edit_Paste.setActionCommand("粘贴");

			  mi_Edit_Copy.addActionListener(this);
			  mi_Edit_Paste.addActionListener(this);
			  mi_Edit_Cut.addItemListener(this);

			  menuEdit.add(mi_Edit_Copy);
			  menuEdit.add(mi_Edit_Paste);
		          menuEdit.addSeparator();
		          menuEdit.add(mi_Edit_Cut);
			  m_Menu_Bar.add(menuEdit);

                          menuHelp=new Menu("帮助");
                          mi_Help_Sub=new MenuItem("主题");
                          menuHelp.add(mi_Help_Sub);
                          m_Menu_Bar.add(menuHelp);
			  this.setMenuBar(m_Menu_Bar);
//*********************************************************************************//







                    add("North",MyLabel);
                    add("South",MyButton);
                    add("Center",textarea);
                    setResizable(false);
                    pack();
                    show();
                    connect();
    }

    public void connect()
      {
        try{
             ClientSocket=new Socket("localhost",6544);
             os=new PrintStream(
                  new BufferedOutputStream(ClientSocket.getOutputStream()));
             is=new DataInputStream(
                  new BufferedInputStream(ClientSocket.getInputStream()));
             s=is.readLine();
               textarea.appendText(s+"\n");
            }
             catch(Exception e){}
      }
//&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&//

    public void itemStateChanged(ItemEvent e)//响应CHECKBOXMENUITEM被点击事件
	{
		 if(e.getSource()==mi_Edit_Cut)
			 if(((CheckboxMenuItem)e.getSource()).getState())//查看是否被选中
		       textarea.setText("\n\n\n\n\n\n\t\t\t"+"you have chosen "+
				   ((CheckboxMenuItem)e.getSource()).getLabel());
			 else
				 textarea.setText("\n\n\n\n\n\n\t\t\t"+"you have not chosen "+

                     ((CheckboxMenuItem)e.getSource()).getLabel());


        }
//&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&//
      public void actionPerformed(ActionEvent e)
        {
          //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%//
             if(e.getActionCommand()=="退出")
			{
				dispose();
				System.exit(0);
			}
		 
			  
          //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%//

            if(e.getSource()==MyButton)
              {
                try{
                       os.print(textarea.getText());
                       os.flush();
                    }
                   catch(Exception e1){}
               }
          }
         public static void main(String args[])
         {
                new MyClient();
          }
 }

   class WinAdptClient extends WindowAdapter
     {
         MyClient m_Parent;
         WinAdptClient(MyClient p)
          {
                m_Parent=p;
           }
         public void windowClosing(WindowEvent e)
          {
             try{//关闭窗口前先向SERVER端发送结束信息，并关闭各输入输出流与连接
                       m_Parent.os.println("Bye");
                       m_Parent.os.flush();
                       m_Parent.is.close();
                       m_Parent.os.close();
                       m_Parent.ClientSocket.close();
                       m_Parent.dispose();
                       System.exit(0);
                }catch(IOException e2){}
            }
       }

     
            