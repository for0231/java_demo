<HTML>
<HEAD>
<%@ page session="true" %>
<%@ page contentType="text/html;charset=gb2312" %> 
<META http-equiv="refresh" content="3">
<script language="JavaScript">
 function scrollchange() 
 {
        parent.mainFrame.window.scroll(0,60000);
	parent.mainFrame.document.bgColor="#ffffff";
       	return true;
  }
</script>
<TITLE>
outputMessage
</TITLE>
</HEAD>
<BODY onload="scrollchange()" bgcolor="#FFFFFF">
<%@ page import="java.util.*" %> <%@ page import="java.io.*" %> <%@ page import="javax.servlet.*" %> 
<%@ page import="javax.servlet.http.*" %> 
<FORM method="post">  
 <%    
      String str1=new String("<font color=orage><b>[Äã¶Ô]</b></font>");
      String str2=new String("<font color=orage><b>[¶ÔÄã]</b></font>"); 
      String str3=new String("<font color=orage>[Ë½ÁÄ]</font>");
      String str4=new String("<br>");
      String str5=new String("<font color=orage><b>[¶Ô]</b></font>");      
      String Name =(String)session.getValue("name");
        if(Name==null)
	  Name=new String("noname");
	  Name =new String(Name.getBytes("iso-8859-1"),"GBK");	 
         %>   
    <%
       Integer MessageIndex =(Integer)session.getValue("MessageIndex");    
      if((MessageIndex==null )||( MessageIndex.intValue() >= 200 ))
            MessageIndex=new Integer(0);
      else 
            MessageIndex=new Integer(MessageIndex.intValue());
       
%>
<%
    synchronized(application)
    {  
        String alone=new String("yes");
        Vector DisplayMessage=new java.util.Vector();   
        DisplayMessage =(Vector)application.getAttribute("Message"); 
        
        if(DisplayMessage !=null)
	{  
	       if(DisplayMessage.size()< MessageIndex.intValue())
		          {
			  MessageIndex=new Integer(DisplayMessage.size());
			  }
                    
	     
	   if(MessageIndex.intValue()!= DisplayMessage.size())
	    {	   
               for(int i=MessageIndex.intValue();i<DisplayMessage.size()-1;i=i+4)
                { 

                int     aloneindex=i;
                int     nameindex=i+1;
                int     talkwithindex=i+2;
                String  Messagestr= (String)DisplayMessage.get(i+3);
                String  alonetag  = (String)DisplayMessage.get(aloneindex); 
		if(alonetag==null)
		    alonetag=new String("all");
		
                String  nametag =  (String)DisplayMessage.get(nameindex);
	              nametag =new String(nametag.getBytes("iso-8859-1"),"GBK");
		   
			
                String  talkwithtag =(String)DisplayMessage.get(talkwithindex);
		    talkwithtag =new String(talkwithtag.getBytes("iso-8859-1"),"GBK");
                 
	         if(alonetag.compareTo(alone)==0)
                   {
                    if(nametag.compareTo(Name)==0)
                         {
                           Messagestr=str3+str1+talkwithtag+Messagestr+str4;                           
                               
                          %>
                       <script language="JavaScript">
                                 parent.mainFrame.document.write("<%= Messagestr%>")
                       </script>
                       <%
                           
                            }
                      
                      if((talkwithtag.compareTo(Name)==0))   
                       { 
                          Messagestr=str3+nametag+str2+Messagestr+str4;  
                            
                            
                          %>
                       <script language="JavaScript">
                                 parent.mainFrame.document.write("<%= Messagestr%>")
                       </script>
                       <%              
                        }    
			
                   }
                 else  
                   {		    
                     
		       if(nametag.compareTo(Name)==0)
                         {
                           Messagestr=str1+talkwithtag+Messagestr+str4;
                             
                       %>
                       <script language="JavaScript">
                                 parent.mainFrame.document.write("<%= Messagestr%>")
                       </script>
                        <%             
                                  
                           
                            }
                      
                         else if((talkwithtag.compareTo(Name)==0))   
                         { 
                          Messagestr=nametag+str2+Messagestr+str4;  
                            
                              %>
                        <script language="JavaScript">
                                 parent.mainFrame.document.write("<%= Messagestr%>")
                        </script>
                        <%             
                          } 
		        else

                          {
		           Messagestr= nametag+str5+talkwithtag+Messagestr+str4;
                        
                                %>
                          <script language="JavaScript">
                                 parent.mainFrame.document.write("<%= Messagestr%>")
                           </script>
                            <%             
                            }                      
		      
			}   
                       
		     Integer count=new Integer(i+4);
		    
		     session.putValue("MessageIndex",count);
                   
		    }
	       }
	   }
	 else 
	     out.println("Welcome to here!");  	

    } 
    %>  
</form>
</body>
</html>

