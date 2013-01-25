//client�˵ĳ������£�
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
         Label MyLabel=new Label("                      �ӭʹ�ñ�ϵͳΪ���ṩ�����");
         TextArea textarea;
         Button MyButton=new Button("�� �� �� Ϣ");

  public MyClient()
   {
                   setTitle("Client Window(�ͻ��˴���)");
                   setLayout(new BorderLayout());
                  
                   this.addWindowListener(new WinAdptClient(this));
                   MyButton.addActionListener(this);
                  

                   textarea=new TextArea(13,55);
//8888888888888888888888888888888888888888888888888888888888888888888888888888//
                   popM=new PopupMenu();
	           pi_New=new MenuItem(" �½� ");
	           pi_New.addActionListener(this);
	           popM.add(pi_New);
		   pi_Del=new MenuItem(" ɾ�� ");
		   pi_Del.addActionListener(this);
		   popM.add(pi_Del);
                   pi_Pro=new MenuItem(" ���� ");
                   pi_Pro.addActionListener(this);
		   popM.add(pi_Pro);
		 
		    m_Menu_Bar=new MenuBar();
		   menuFile=new Menu("�ļ�");
		   mi_File_Open=new MenuItem("��");
		   mi_File_Open.setShortcut(new MenuShortcut('f'));
		   mi_File_Close=new MenuItem("�ر�",new MenuShortcut('s'));
		   mi_File_Exit=new MenuItem("�˳�",new MenuShortcut('x'));

		   mi_File_Open.setActionCommand("��");
		   mi_File_Close.setActionCommand("�ر�");
		   mi_File_Exit.setActionCommand("�˳�");

		   mi_File_Open.addActionListener(this);
		   mi_File_Close.addActionListener(this);
		   mi_File_Exit.addActionListener(this);

		     menuFile.add(mi_File_Open);
		     menuFile.add(mi_File_Close);
		     menuFile.add(mi_File_Exit);

			 m_Menu_Bar.add(menuFile);


			 menuEdit=new Menu("�༭");
			  mi_Edit_Copy=new MenuItem("����");
			  mi_Edit_Paste=new MenuItem("ճ��");
			  mi_Edit_Cut=new CheckboxMenuItem("CUT");
			  mi_Edit_Copy.setActionCommand("����");
			  mi_Edit_Paste.setActionCommand("ճ��");

			  mi_Edit_Copy.addActionListener(this);
			  mi_Edit_Paste.addActionListener(this);
			  mi_Edit_Cut.addItemListener(this);

			  menuEdit.add(mi_Edit_Copy);
			  menuEdit.add(mi_Edit_Paste);
		          menuEdit.addSeparator();
		          menuEdit.add(mi_Edit_Cut);
			  m_Menu_Bar.add(menuEdit);

                          menuHelp=new Menu("����");
                          mi_Help_Sub=new MenuItem("����");
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

    public void itemStateChanged(ItemEvent e)//��ӦCHECKBOXMENUITEM������¼�
	{
		 if(e.getSource()==mi_Edit_Cut)
			 if(((CheckboxMenuItem)e.getSource()).getState())//�鿴�Ƿ�ѡ��
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
             if(e.getActionCommand()=="�˳�")
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
             try{//�رմ���ǰ����SERVER�˷��ͽ�����Ϣ�����رո����������������
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

     
            