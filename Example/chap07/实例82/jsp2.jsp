<HTML>
<HEAD>
<script language="JavaScript">
function NameGotFocus() {
        document.Jsp2Form.usermessage.focus();
	}
function Logoutconfirm()
{
 return confirm("��Ҫ�˳���������");
  }
</script>
<%@ page session="true" %>
<%@ page contentType="text/html;charset=gb2312" %> 
<TITLE>
inputbar
</TITLE>
<link rel="stylesheet" href="a.css">
</HEAD>
<BODY  onload="NameGotFocus()" bgcolor=#CCCCFF topmargin="10" >
<%@ page import="java.util.*" %> <%@ page import="java.io.*" %> <%@ page import="javax.servlet.*" %> 
<%@ page import="javax.servlet.http.*" %> 
<FORM name="Jsp2Form"  method="post"  action="jsp2.jsp">
  <table border="0" width="100%" align="center" cellspacing="0" cellpadding="0">
    <tr> 
      <td width="6%" height="42"><%-- Get talkwith for jsp3.jsp  --%><%String   withstr=new String("ALL");
   
  String    Withsen =(String)request.getParameter("talkwith");    
            if(Withsen==null)  
	     { session.putValue("talkwith",withstr); 
	       
              }
            else 
	      {
	        session.putValue("talkwith",Withsen);	
	      }
	      
  String  withstr2= (String)session.getValue("talkwith");
  withstr2 =new String(withstr2.getBytes("iso-8859-1"),"GBK"); 
  
       %> 
        <div align="right">�� ��</div>
      </td>
      <td width="69%" height="42"> 
        <input type="text" name="talkwith" value= <%=  withstr2%> size="8">
        &nbsp <%-- Get nameValue from chat.jsp (username=session.getValue("Name"))  --%> 
        <select name="fontcolor" size="1">
          <option style="COLOR: #000000" value="colorstring">������ɫ 
          <option style="COLOR: #000000" value="#000000">��ɫ 
          <option style="COLOR: #7ec0ee" value="#7ec0ee">���� 
          <option style="COLOR: #0088ff" value="#0088ff">���� 
          <option style="COLOR: #0000ff" value="#0000ff">���� 
          <option style="COLOR: #000088" value="#000088">���� 
          <option style="COLOR: #8800ff" value="#8800ff">���� 
          <option style="COLOR: #ab82ff" value="#AB82FF">��ɫ 
          <option style="COLOR: #ff88ff" value="#ff88ff">�Ͻ� 
          <option style="COLOR: #ff00ff" value="#ff00ff">���� 
          <option style="COLOR: #ff0088" value="#ff0088">õ�� 
          <option style="COLOR: #ff0000" value="#ff0000">��� 
          <option style="COLOR: #f4a460" value="#f4a460">��ɫ 
          <option style="COLOR: #888800" value="#888800">���� 
          <option style="COLOR: #888888" value="#888888">���� 
          <option style="COLOR: #90e090" value="#90E090">��ɫ 
          <option style="COLOR: #008800" value="#008800">��� 
          <option style="COLOR: #008888" value="#008888">���� 
        </select>
        <select name="action" size="1">
          <option value="actionstring">����Ȥ�� 
          <option value="��ȭ����һ�ݵ�:&quot;���˶Դ�ҵľ���֮�飬�������ν�ˮ���಻����&quot;">�ֺ� 
          <option value="ץ��ץͷƤ��¶���Ի������.... ">�Ի� 
          <option value="һ���Ļ���������˭ǣ��������ţ ">���� 
          <option value="���������ң��ѹ���̾�˿�����">ɥ�� 
          <option value="��˫���޳ɴ��">ͬ�� 
          <option value="����ߵķ���һ��ͽ�͵����������£�Ȼ���޿ɾ�ҩ������˴�ҵ��������塭��">�������Χ 
          <option value="ͻȻ���Ŵ�Ҵ�һ���������ҵĵ���ǧ�˰ٴγ����������㣬�����ҵ���һСƬ����ɣ�">�� 
          <option value="ǿ��ס�����һɲ�ǵ��һ���ģ��ӷ�����嵽��԰�����ϵ�����������������Ӿ���У���Ҿ��ȵķ��֣���һ�����ŵ��һ����Ӿ�ص�ˮ��Ȼȫ�����ˣ�">���˵ĳ嶯 
          <option value="����ҧס�Լ����촽���۾���ˮ��һ��������ע���Ŵ�ң��ÿ��Դݻ�һ�����ң�ɱ��һ������������һ���ĵ�����˵�����������ѣ������ݣ�������ô�᲻���ء�">Ů�˵ĳ嶯 
          <option value="ˢ�ĳ��һ����������Ľ䵶��һ�����Լ��Ĵ��ͷ­�������������ﻹ��ͣ���ֹ������ұȿᣬ˭�ܱ��ҿ᣿">�ȿ� 
          <option value="ͻȻ�γ�һ����ǹ��ָ���Լ���ͷ��������涵Ŀ��Ŵ�ҡ�">����ɱ 
          <option value="��Ц�������������һ����ͷ�����ţ�����û�й����ˣ��������������壡">Ҫ���� 
          <option value="���������������������������ͻȻ������һ����ͷֱֱ�����·�ߵ�ˮ���">������ 
          <option value="���ص�����һ���Դ�, �����뵽��! ">�뵽�� 
          <option value="���������ı���!��ð���ɡ���Ҫ���ˡ� ">���� 
          <option value="ʹ�����İ���,žžžžž.....�ĵ��ֶ���������! ">�İ��� 
          <option value="��������,��û�еض�, �����ȥ������ :~ ">���� 
          <option value="��京�߲�һ��������������ͻȻ�ܻ���������ء��������˴��һ�£�ת������ˡ�">�ߴ����� 
          <option value="�󱩷���һ�����˹���������˵�ţ�����û���㣬������ϧ������һ����һ�����ڴ�ҵ�Ь�����ϡ�">���ǶԷ� 
          <option value="���ҵ���: �߿�! �һ������ǵ�">�ټ�1 
          <option value="���Ŵ�����������˵�������ǵ��ң������Ǹ�������Ŷ��">�ټ�2 
          <option value="���Ŵ������س��ţ�Ʈ��Ʈ��һ���飬����Ҳ�з硭����Ȼ���������ڣ������쳾�С��������ˡ���">�ټ�3 
          <option value="���Ŵ�������˵����������û�в�ɢ����ϯ��������һ���ˣ����ء��� ">�ټ�4 
          <option value="����˵ BYE BYE! ǧɽ�Ҷ���... ��������.">�ټ�5 
          <option value="���ظ��ĵ���ž�죺��������ȭͷ���˵�������ֵ������Ȼ��Ȼ����� ">���ظ� 
          <option value="���۷��ף��ȶ����˼��£��Դ�һ�ᣬ���ˡ� ">���� 
          <option value="�밡�룬������Դ��������ˣ��װ׵��Խ�����һ��! ">����ͷ 
          <option value="���ŵ�˫��ֱ����. ">���� 
          <option value="���°�ĭ���赹�ڵ� ">�赹 
          <option value="ӯӯһ��,ЦЦ��˵:��λ���,СŮ��������.. ">�ʺ�(��Ů) 
          <option value="�����ֵ�˵:����,С���!����������������!&quot;">�ʺ�(˧��) 
          <option value="�������ܣ�ȫ������ɽ��һƬ��ã������������������ͬ־�Ǻá��� ">�ʺ�1 
          <option value="�ж����ˣ����������������� ">�ж� 
          <option value="һ������һ�ѱ���غ���ԩ����!! ">��ԩ 
          <option value="���������:����,��������֮�Ƕ���, ��������֮�ֶ���...����������̫�ס� ">�������� 
          <option value="���һ�����������������,�����Ⱦ��ܣ�Ƭ�̾���ʧ��һƬãã������ ">���� 
          <option value="�˷ܵس���������һֻСѼ�ӣ���ѽ��ѽӴ���� ">�˷ܵس� 
          <option value="�����ע���Ŵ�ң���������������Ϊ�����£��Űپ�ʮ�Ŷ�õ�塣��">����س� 
          <option value="��һ��! ���ڵ����������~ ">��� 
          <option value="Ц���󻨶�һ�� ">Ц 
          <option value="¶����׳հ��Ц��....:O~ ">ɵЦ 
          <option value="�����Ц��������֮�£���Ȼû���ҵĶ���...">��Ц 
          <option value="���������ǰ�� ">���� 
          <option value="����һ��һ�����ԣ���������ڴ�ڵغ����ƾơ�">���ƾ� 
          <option value="����ͷ����Ū���Լ�����ָ���ߵ�����ͨ��">���� 
          <option value="�����������ž����е��Լ�...">���� 
          <option value="�����ޱ�����ơ�Ƹ���ң�Ȼ��˵:������ !��">���� 
          <option value="���˷��Ƶ�һ����������������������">���� 
          <option value="���������ҵĽ���͵͵����">���� 
          <option value="���������дǵ�,�������ڱ����־�.">� 
          <option value="���Լ��ĳ��񼫶Ȳ�����, ������˫���������.">�Ա� 
          <option value=" �󺰼���~ ����~">������ 
          <option value="����������...���Ҹ�����ͷҧҧ.">���� 
          <option value="��ֻСè�Ƶ�������Ű�մ�������ץ�������.">����� 
          <option value="��Ϊ�Լ�һ����ͷ�ǻ�����,�Ż�������������.">ͷ�� 
          <option value="�����Լ��Ĺ��ȹ����Ѿ�����¯����ĵز�,ֻ�û���������ý��ƶ���.">���� 
          <option value="ֻ����һ��ͷ��Ŀѣ.....">ͷ�� 
          <option value="���µط��������о���������, ͷƤ����~">���� 
          <option value="һ������ߵ����ӣ����˾ͽ��˶���.">��� 
          <option value="�Ĵ�������һ��,Ȼ����ŵ��������������һ��.">�ǵ� 
          <option value="��֪�뵽��ʲô,һ�������ǳԳԵ�ɵЦ.">ɵЦ 
          <option value="�ٺٺ�....�ؼ�Ц�˼������˳��뵽ʲô����ͷ�ϡ�">��Ц 
        </select>
        <select name="face" size="1">
          <option value="facestring">�������</option>
          <option value="�������죬¶����Ž���">΢Ц</option>
          <option value="�����״������������">��һ�°� 
          <option value="�ܸ��˵�">���� 
          <option value="��!��!��!��Ц��">��Ц���� 
          <option value="ë��ë�ŵ�">ë��ë�� 
          <option value="������">��� 
          <option value="��Ҫ�޵�">��Ҫ�� 
          <option value="ȭ����ߵ�">ȭ����� 
          <option value="���������">�������� 
          <option value="�ź���">�ź� 
          <option value="�ɴ����۾�,�������">���� 
          <option value="�Ҹ���">�Ҹ� 
          <option value="���䵹���">���䵹�� 
          <option value="���˵�">���� <!--<option value="����Ц��">����Ц--> 
          <option value="����ˮ��">����ˮ 
          <option value="������Ȼ��">������Ȼ 
          <option value="������">���� 
          <option value="������">���� 
          <option value="ɵ������">ɵ���� 
          <option value="һ���������">������ 
          <option value="�����޴��">�����޴� 
          <option value="���޹���">���޹� 
          <option value="�������">������ 
          <option value="��ݺݵĵ�����">���� 
          <option value="��Ҫ�µ�">���� 
          <option value="�������">����� 
          <option value="�޾���ɵ�">�޾���� 
          <option value="���������">�������� 
          <option value="�°�ĭ��">��ĭ 
          <option value="���β�ס��ϲ������">��ϲ 
          <option value="Ц�Ǻ�һ����">���� 
          <option value="������ò������һҾ">��Ҿ 
          <option value="��!��̾��ǧ">��̾ 
          <option value="�ܲ�����˼����Ǹ">��Ǹ 
          <option value="���Ŵ���ϸ�����������ɵ�˫������">�˸� 
          <option value="����Ҫ��">���� 
          <option value="�뵽���Ĵ���������ע">��� 
          <option value="��ͷ�������">ʹ�� 
          <option value="������ѽ...">���� 
          <option value="�е���������">���� 
          <option value="����С�۾���">���� 
          <option value="����һЦ���ܴ󷽵�">�� 
          <option value="���Ϸ����˺��Σ�">���� 
          <option value="���ߵ��µ�">���� 
          <option value="���ɤ��˵">���� 
          <option value="������һ���Ϻ�">�Ϻ� 
          <option value="һ������ã">��ã 
          <option value="���ε����ʼ�">�ʼ� 
          <option value="ʹ�������Լ�����">���� 
          <option value="���ű���̸Ц�����ĵĺ�">���� 
          <option value="������˼״">��˼ 
          <option value="һ���޹�������">�޹� 
          <option value="��һ����Ż�µĸо�">���� 
        </select>
        <select name="fontstyle" size="1" style=" font-size: 9pt">
          <option value="stylestring" >������ʽ</option>
          <option value="span" >����</option>
          <option value="I">б��</option>
          <option value="B">����</option>
          <option value="U">�»���</option>
          <option value="TT">�ȿ���</option>
        </select>
        <% 
   String AloneTalk=request.getParameter("alonetalk");
      if(AloneTalk==null)
   AloneTalk=new String("no");
   if(AloneTalk.equals("no"))
      out.println("<input type=\"checkbox\" name=\"alonetalk\" value=\"yes\" > ���Ļ� ");
   else
      out.println("<input type=\"checkbox\" name=\"alonetalk\" value=\"yes\" checked > ���Ļ�");
  	 session.putValue("alonetalk",AloneTalk); 
	     %> </td>
      <td width="12%" height="42"> 
        <div align="left"><a href="alonewidow.jsp" target=hiddenFrame  onClick="alert(' ����˽�Ĵ� �� ֻ��˵���Ļ� �����ҿ��������˵�Ļ���')"><font color=#ee0000><b><font size=2.5><img src="images/go2.gif" width="80" height="20" border="0"></font></b></font></a></div>
      </td>
      <td width="13%" height="42"> 
        <div align="right"><a href="jsp5.jsp" target=hiddenFrame  onClick="alert(' ����㿪��˽�Ĵ������˳�˽�Ĵ��ڣ�')"><img src="images/go1.gif" width="100" height="20" border="0"></a></div>
      </td>
    </tr>
  </table>
  <table border="0" width="90%" align="center">
    <tr>
      <td width="10%">�� <% 
    String testname=(String)request.getParameter("username");
     if(testname!=null)    
           {
	    session.putValue("name",testname);
	 
	    }
       else
             {
	     testname=(String)session.getValue("name");
	    
	     } 
          testname=new String(testname.getBytes("iso-8859-1"),"GBK");      
          out.println("["+testname+"]"+":");
      
 %> 
        <div align="right"></div>
      </td>
      <td width="90%"> 
        <input type="text" name="usermessage" size=50>
        <input type="SUBMIT" name="Submit" value="����">
        ��������<a href="logout.jsp?logout=<%= new String("yes") %>" target="_top" onClick="return Logoutconfirm()"><img src="images/go.gif" width="78" height="20" border="0" align="middle"></a> 
      </td>
    </tr>
  </table>
  <table border="0" width="60%" align="center">
    <tr> 
      <td colspan="3" height="22"><%! String welcomestr="<font color=blue>��λ�ã������ˣ���</font><br><font color=orage><b>[����]����һλ�����ѣ���ҿ컶ӭ�� </b></font>";%><%! String TotalMessage; %><%  String str1col=new String("<font color=#cc9933><b>");
      String str2col=new String("<font color=#cc3366><b>");
      String str3col=new String("</b></font>");
      String  TotalMessage=null; 
      String  headstr=null;
    %><%  String  Name =(String)session.getValue("name");
    
     
      String  TalkWith=(String)request.getParameter("talkwith");               
              Withsen =(String)session.getValue("talkwith");
              if((TalkWith!=null)&&(!(Withsen.compareTo(TalkWith)==0)))
	        {		       
                  session.putValue("talkwith",TalkWith);      
		  }                    
           

 %><% 
     
     String  fontcolor=(String)request.getParameter("fontcolor");
     if(fontcolor==null)
        { fontcolor=new String("#000000");
	    session.putValue("fontcolor",fontcolor);
	    }
   	String col1=new String("colorstring");
     if(!(fontcolor.equals(col1)))
        session.putValue("fontcolor",fontcolor);

  String  fontstyle=(String)request.getParameter("fontstyle");
     if(fontstyle==null)
        { fontstyle=new String("span");
	    session.putValue("fontstyle",fontstyle);
	    }
   	String fontstyle2=new String("stylestring");
     if(!(fontstyle.equals(fontstyle2)))
        session.putValue("fontstyle",fontstyle);
	
    
    
      String  face=(String)request.getParameter("face"); 
      if(face==null)
      face=new String("facestring");
      face=new String(face.getBytes("iso-8859-1"),"GBK");
   

    
      String  TalkMessage=(String)request.getParameter("usermessage");
      if(TalkMessage==null)
      TalkMessage=welcomestr;
      else
       TalkMessage=new String(TalkMessage.getBytes("iso-8859-1"),"GBK");        
      
     
     String  action=(String)request.getParameter("action"); 
     if(action==null)
       action=new String("actionstring");
       action=new String(action.getBytes("iso-8859-1"),"GBK"); 
   
   %><%
   String actionstr1 = new String("actionstring");
   String str11=new String("ALL");
   String str12=new String("���");
          str12=new String(action.getBytes("iso-8859-1"),"GBK"); 
   String str13=(String)session.getValue("alonetalk");
   String str14=new String("yes");
   
   String withstr11=(String)session.getValue("talkwith");
   if(Name.equals(withstr11))
 {
  out.println("<font color=red><b>��ʾ��Ϣ��Ŷ����ô���Լ�˵�� �� ��</b></font>");
 }
   else if((withstr11.equals(str11)||withstr11.compareTo(str11)==0)&&str13.equals(str14)) 
  {
    out.println("<font color=red><b>��ʾ��Ϣ�����������ڶ�[���]˵���Ļ� �� ��</b></font>"); 
  }
  else if((TalkMessage.length()==0)&&action.equals(actionstr1))     
{
  out.println("<font color=red><b>��ʾ��Ϣ�����ѣ���������Ϣ��лл!!!</b></font>"); 
 }
 
 else if(TalkMessage.equals(session.getValue("TalkMessage")))
   {
     out.println("<font color=red><b>��ʾ��Ϣ�����ѣ��α�Ҫ˵ͬ���Ļ��أ���</b></font>");
    }
 else if(TalkMessage.length()>100)
   {
     out.println("<font color=red><b>��ʾ��Ϣ�����ѣ�̫���ˣ����ܲ��ˣ���һ��ɣ�</b></font>");
    }
 else
  {  
      session.putValue("TalkMessage",TalkMessage);
       if(!(action.equals(actionstr1))&&!(TalkMessage.length()==0))
       {
          TalkMessage="<font color=blue><b>"+action+"</b></font>"+TalkMessage;
	}
	else if(!(action.equals(actionstr1)))
	{
	 TalkMessage="<font color=blue><b>"+action+"</b></font>";
	 }
      if( !(face.equals("facestring")) )
       {
        headstr=str2col.concat(face);   
        headstr=headstr.concat(str3col);
       }
     
       else
       {
        headstr=null;
        }
      
       fontstyle=(String)session.getValue("fontstyle");
	  if(fontstyle!=null)
       { 
         TalkMessage="<"+fontstyle+">"+TalkMessage+"</"+fontstyle+">";
        }

       fontcolor=(String)session.getValue("fontcolor");
       if(fontcolor!=null)
       {  String fontcolstr1=new String("<font color= ");
          String fontcolstr2=new String(">");
          String fontcolstr3=new String("</font>");
          String fontcolstr=fontcolstr1.concat(fontcolor);
                 fontcolstr=fontcolstr.concat(fontcolstr2);
                 TalkMessage=fontcolstr.concat(TalkMessage);
                 TalkMessage=TalkMessage.concat(fontcolstr3);
        }
      

       String s=new String("<font color=orage><b>˵��</b> </font>");  
       TalkMessage=s.concat(TalkMessage);
       if(headstr!=null)
          TotalMessage=headstr.concat(TalkMessage); 
       else        
	 TotalMessage=TalkMessage;	
       if(TotalMessage==null)
       return;
      else{
        Vector  Message = null;
        synchronized (application)
           { 
            Message= (Vector)application.getAttribute("Message");
           
	   if(Message==null) 
                   Message= new Vector(30,10);
           if(Message.size()>200)
	       Message.removeAllElements();
               
		
            
            Message.addElement(session.getValue("alonetalk")); 
            Message.addElement(session.getValue("name"));
            Message.addElement(session.getValue("talkwith"));
            Message.addElement(TotalMessage);
            application.setAttribute("Message", Message);  
	  
           } 
        }
      out.println("��ʾ��Ϣ��");
   Integer count = null;
       count = (Integer) session.getValue("change");
    if (count == null)
        count = new Integer(0);
    count = new Integer(count.intValue() + 1);
    session.putValue("change", count);
 } %> <font color=blue>���͵���Ϣ������</font> <%= session.getValue("change")%> </td>
    </tr>
  </table>
</FORM>
   
</BODY>
</HTML>
