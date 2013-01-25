<HTML>
<HEAD>
<script language="JavaScript">
function NameGotFocus() {
        document.Jsp2Form.usermessage.focus();
	}
function Logoutconfirm()
{
 return confirm("你要退出聊天室吗？");
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
        <div align="right">对 象：</div>
      </td>
      <td width="69%" height="42"> 
        <input type="text" name="talkwith" value= <%=  withstr2%> size="8">
        &nbsp <%-- Get nameValue from chat.jsp (username=session.getValue("Name"))  --%> 
        <select name="fontcolor" size="1">
          <option style="COLOR: #000000" value="colorstring">字体颜色 
          <option style="COLOR: #000000" value="#000000">黑色 
          <option style="COLOR: #7ec0ee" value="#7ec0ee">淡蓝 
          <option style="COLOR: #0088ff" value="#0088ff">海蓝 
          <option style="COLOR: #0000ff" value="#0000ff">草蓝 
          <option style="COLOR: #000088" value="#000088">深蓝 
          <option style="COLOR: #8800ff" value="#8800ff">蓝紫 
          <option style="COLOR: #ab82ff" value="#AB82FF">紫色 
          <option style="COLOR: #ff88ff" value="#ff88ff">紫金 
          <option style="COLOR: #ff00ff" value="#ff00ff">红紫 
          <option style="COLOR: #ff0088" value="#ff0088">玫红 
          <option style="COLOR: #ff0000" value="#ff0000">大红 
          <option style="COLOR: #f4a460" value="#f4a460">棕色 
          <option style="COLOR: #888800" value="#888800">卡其 
          <option style="COLOR: #888888" value="#888888">铁灰 
          <option style="COLOR: #90e090" value="#90E090">绿色 
          <option style="COLOR: #008800" value="#008800">橄榄 
          <option style="COLOR: #008888" value="#008888">灰蓝 
        </select>
        <select name="action" size="1">
          <option value="actionstring">流行趣语 
          <option value="抱拳团团一拜道:&quot;敝人对大家的景仰之情，有如涛涛江水连绵不绝。&quot;">讨好 
          <option value="抓了抓头皮，露出迷惑的神情.... ">迷惑 
          <option value="一脸的晦气，好象谁牵走了他的牛 ">晦气 
          <option value="觉得万念俱灰，难过地叹了口气。">丧气 
          <option value="举双脚赞成大家">同意 
          <option value="象断线的风筝一样徒劳地挣扎了两下，然后无可救药的落进了大家的温柔陷阱……">被温柔包围 
          <option value="突然对着大家大喊一声，这是我的第三千八百次初恋，求求你，接受我的这一小片痴情吧！">求爱 
          <option value="强忍住烦躁和一刹那的烈火焚心，从房间里冲到花园，果断地纵身跳进冰凉的游泳池中，大家惊讶的发现，不一会儿，诺大一个游泳池的水居然全沸腾了！">男人的冲动 
          <option value="紧紧咬住自己的嘴唇，眼睛象水滴一样生动地注视着大家，用可以摧毁一个国家，杀死一个国王，揉碎一颗心的声音说道，云想衣裳，花想容，我又怎么会不想呢。">女人的冲动 
          <option value="刷的抽出一把闪闪发光的戒刀，一刀将自己的大好头颅切了下来，嘴里还不停的嘀咕，和我比酷，谁能比我酷？">比酷 
          <option value="突然拔出一把手枪，指着自己的头，泪眼婆娑的看着大家。">想自杀 
          <option value="狂笑三声，从身后抽出一柄斧头，大喊着，天下没有公理了，让我来伸张正义！">要砍人 
          <option value="浑身酒气挣扎着踉跄走了两步，突然不留神，一个跟头直直插进了路边的水沟里。">喝醉了 
          <option value="重重地拍了一下脑袋, 终于想到了! ">想到了 
          <option value="作出生气的表情!火冒三丈～想要打人～ ">生气 
          <option value="使劲地拍巴掌,啪啪啪啪啪.....拍得手都红起来了! ">拍巴掌 
          <option value="脸都红了,恨没有地洞, 好钻进去躲起来 :~ ">脸红 
          <option value="象朵含羞草一样躲了起来，又突然跑回来，轻轻地、快快地吻了大家一下，转身就跑了。">羞答答地吻 
          <option value="象暴风雨一样扑了过来，嘴里说着，不能没有你，……可惜，脚下一滑，一嘴吻在大家的鞋帮子上。">狂吻对方 
          <option value="向大家道别: 走咯! 我会想你们的">再见1 
          <option value="对着大家无限深情地说：如果你记得我，别忘记给我留言哦！">再见2 
          <option value="对着大家轻轻地唱着：飘啊飘的一段情，有雨也有风……蓦然回首你仍在，浪漫红尘中……我走了……">再见3 
          <option value="对着大家凄婉地说道：“世上没有不散的宴席，我先走一步了，保重。” ">再见4 
          <option value="向大家说 BYE BYE! 千山我独行... 不必相送.">再见5 
          <option value="把胸脯拍得噼啪响：“武林中拳头大的说话，有种的上来比划比划！” ">拍胸脯 
          <option value="两眼翻白，腿儿蹬了几下，脑袋一歪，死了。 ">扮死 
          <option value="想啊想，结果把脑袋给想破了，白白的脑浆流了一地! ">想破头 
          <option value="被吓得双腿直哆嗦. ">害怕 
          <option value="口吐白沫，昏倒在地 ">昏倒 
          <option value="盈盈一拜,笑笑地说:各位大哥,小女子有礼了.. ">问好(美女) 
          <option value="大咧咧地说:大姐姐,小姐姐!本公子这厢有礼啦!&quot;">问好(帅哥) 
          <option value="环顾四周，全聊天室山河一片大好，于是清了清喉咙：“同志们好。” ">问好1 
          <option value="感动极了，两行热泪夺眶而出。 ">感动 
          <option value="一把眼泪一把鼻涕地喊：冤枉啊!! ">喊冤 
          <option value="自言自语道:“我,先天下人之忧而忧, 后天下人之乐而乐...这个这个好象不太妥” ">自言自语 
          <option value="大叫一声：“风紧，扯呼”,撒开腿就跑，片刻就消失在一片茫茫白雾中 ">逃跑 
          <option value="兴奋地唱：“我是一只小鸭子，伊呀伊呀哟！” ">兴奋地唱 
          <option value="深情地注视着大家，唱道：“我早已为你种下，九百九十九朵玫瑰。”">深情地唱 
          <option value="咚一声! 坐在地上哇啦大哭~ ">大哭 
          <option value="笑得象花儿一样 ">笑 
          <option value="露出像白痴般的笑容....:O~ ">傻笑 
          <option value="向天狂笑：“普天之下，竟然没有我的对手...">狂笑 
          <option value="深情的凝视前方 ">凝视 
          <option value="躲在一边一声不吭，独个儿大口大口地喝着闷酒。">喝闷酒 
          <option value="低着头，玩弄着自己的手指，羞得满脸通红">害羞 
          <option value="正疯狂的亲吻着镜子中的自己...">自吻 
          <option value="丢几罐冰凉的啤酒给大家，然后说:「干啦 !」">敬酒 
          <option value="像发了疯似的一个人在聊天室中跳起舞来">跳舞 
          <option value="躲在聊天室的角落偷偷哭泣">哭泣 
          <option value="嘴里念念有辞的,好像正在背三字经.">念经 
          <option value="对自己的长像极度不满意, 决定向双亲提出抗议.">自卑 
          <option value=" 大喊加油~ 加油~">喊加油 
          <option value="牙齿又在痒...想找跟狗骨头咬咬.">牙痒 
          <option value="像只小猫似的正在凌虐刚从聊天室抓到的蟑螂.">玩蟑螂 
          <option value="认为自己一定是头壳坏掉了,才会有这样的言行.">头坏 
          <option value="觉得自己的狗腿功夫已经到了炉火纯青的地步,只差还没出国比赛拿金牌而已.">狗腿 
          <option value="只觉得一阵头晕目眩.....">头晕 
          <option value="害怕地发起抖来，感觉背脊发凉, 头皮发麻~">发抖 
          <option value="一自命清高的样子，看了就叫人恶心.">清高 
          <option value="四处张望了一下,然后对着凳子深情款款地吻了一下.">吻凳 
          <option value="不知想到了什么,一个人在那吃吃的傻笑.">傻笑 
          <option value="嘿嘿嘿....地奸笑了几声，八成想到什么坏事头上。">奸笑 
        </select>
        <select name="face" size="1">
          <option value="facestring">来点表情</option>
          <option value="裂了裂嘴，露出半颗金牙">微笑</option>
          <option value="亲了亲大家美丽的脸颊">亲一下吧 
          <option value="很高兴的">高兴 
          <option value="哈!哈!哈!的笑着">大笑叁声 
          <option value="毛手毛脚的">毛手毛脚 
          <option value="嘟着嘴的">嘟嘴 
          <option value="快要哭的">快要哭 
          <option value="拳打脚踢的">拳打脚踢 
          <option value="不怀好意的">不怀好意 
          <option value="遗憾的">遗憾 
          <option value="瞪大了眼睛,很讶异的">讶异 
          <option value="幸福的">幸福 
          <option value="翻箱倒柜的">翻箱倒柜 
          <option value="悲伤的">悲伤 <!--<option value="淫淫笑的">淫淫笑--> 
          <option value="流口水的">流口水 
          <option value="正气凛然的">正气凛然 
          <option value="生气的">生气 
          <option value="大声的">大声 
          <option value="傻乎乎的">傻乎乎 
          <option value="一付很满足的">很满足 
          <option value="手足无措的">手足无措 
          <option value="很无辜的">很无辜 
          <option value="喃喃自语的">喃喃自语 
          <option value="恶狠狠的瞪着眼">瞪眼 
          <option value="快要吐的">想吐 
          <option value="不舒服的">不舒服 
          <option value="无精打采的">无精打采 
          <option value="依依不舍的">依依不舍 
          <option value="吐白沫着">白沫 
          <option value="掩饰不住狂喜的心情">狂喜 
          <option value="笑呵呵一拱手">拱手 
          <option value="很有礼貌地作了一揖">作揖 
          <option value="咳!慨叹万千">慨叹 
          <option value="很不好意思地致歉">致歉 
          <option value="望着窗外细雨淅淅，不由得双眼朦胧">伤感 
          <option value="含泪要哭">含泪 
          <option value="想到伤心处，泪流如注">大哭 
          <option value="抱头放声大哭">痛哭 
          <option value="好怕怕呀...">害怕 
          <option value="感到很是奇特">奇特 
          <option value="眯着小眼睛道">眯眼 
          <option value="咯咯一笑，很大方地">大方 
          <option value="脸上泛起了红晕，">脸红 
          <option value="气愤的嚷道">生气 
          <option value="提高嗓门说">大声 
          <option value="运足气一声断喝">断喝 
          <option value="一脸的迷茫">迷茫 
          <option value="无奈地耸耸肩">耸肩 
          <option value="使劲敲敲自己脑门">拍脑 
          <option value="看着别人谈笑，无聊的很">无聊 
          <option value="顾作沉思状">沉思 
          <option value="一付无辜的样子">无辜 
          <option value="有一种想呕吐的感觉">不适 
        </select>
        <select name="fontstyle" size="1" style=" font-size: 9pt">
          <option value="stylestring" >字体样式</option>
          <option value="span" >规则</option>
          <option value="I">斜体</option>
          <option value="B">粗体</option>
          <option value="U">下划线</option>
          <option value="TT">等宽体</option>
        </select>
        <% 
   String AloneTalk=request.getParameter("alonetalk");
      if(AloneTalk==null)
   AloneTalk=new String("no");
   if(AloneTalk.equals("no"))
      out.println("<input type=\"checkbox\" name=\"alonetalk\" value=\"yes\" > 悄悄话 ");
   else
      out.println("<input type=\"checkbox\" name=\"alonetalk\" value=\"yes\" checked > 悄悄话");
  	 session.putValue("alonetalk",AloneTalk); 
	     %> </td>
      <td width="12%" height="42"> 
        <div align="left"><a href="alonewidow.jsp" target=hiddenFrame  onClick="alert(' 进入私聊窗 ， 只能说悄悄话 ！并且看不见大家说的话！')"><font color=#ee0000><b><font size=2.5><img src="images/go2.gif" width="80" height="20" border="0"></font></b></font></a></div>
      </td>
      <td width="13%" height="42"> 
        <div align="right"><a href="jsp5.jsp" target=hiddenFrame  onClick="alert(' 如果你开了私聊窗，将退出私聊窗口！')"><img src="images/go1.gif" width="100" height="20" border="0"></a></div>
      </td>
    </tr>
  </table>
  <table border="0" width="90%" align="center">
    <tr>
      <td width="10%">　 <% 
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
        <input type="SUBMIT" name="Submit" value="发送">
        　　　　<a href="logout.jsp?logout=<%= new String("yes") %>" target="_top" onClick="return Logoutconfirm()"><img src="images/go.gif" width="78" height="20" border="0" align="middle"></a> 
      </td>
    </tr>
  </table>
  <table border="0" width="60%" align="center">
    <tr> 
      <td colspan="3" height="22"><%! String welcomestr="<font color=blue>各位好：我来了！！</font><br><font color=orage><b>[公告]来了一位新朋友，大家快欢迎！ </b></font>";%><%! String TotalMessage; %><%  String str1col=new String("<font color=#cc9933><b>");
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
   String str12=new String("大家");
          str12=new String(action.getBytes("iso-8859-1"),"GBK"); 
   String str13=(String)session.getValue("alonetalk");
   String str14=new String("yes");
   
   String withstr11=(String)session.getValue("talkwith");
   if(Name.equals(withstr11))
 {
  out.println("<font color=red><b>提示信息：哦，怎么对自己说话 ？ ！</b></font>");
 }
   else if((withstr11.equals(str11)||withstr11.compareTo(str11)==0)&&str13.equals(str14)) 
  {
    out.println("<font color=red><b>提示信息：哈哈，你在对[大家]说悄悄话 ？ ！</b></font>"); 
  }
  else if((TalkMessage.length()==0)&&action.equals(actionstr1))     
{
  out.println("<font color=red><b>提示信息：朋友：请输入信息。谢谢!!!</b></font>"); 
 }
 
 else if(TalkMessage.equals(session.getValue("TalkMessage")))
   {
     out.println("<font color=red><b>提示信息：朋友：何必要说同样的话呢？？</b></font>");
    }
 else if(TalkMessage.length()>100)
   {
     out.println("<font color=red><b>提示信息：朋友：太多了，我受不了，少一点吧！</b></font>");
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
      

       String s=new String("<font color=orage><b>说：</b> </font>");  
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
      out.println("提示信息：");
   Integer count = null;
       count = (Integer) session.getValue("change");
    if (count == null)
        count = new Integer(0);
    count = new Integer(count.intValue() + 1);
    session.putValue("change", count);
 } %> <font color=blue>发送的信息条数：</font> <%= session.getValue("change")%> </td>
    </tr>
  </table>
</FORM>
   
</BODY>
</HTML>
