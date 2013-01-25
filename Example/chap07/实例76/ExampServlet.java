import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ExampServlet extends HttpServlet 
{
  public void doPost(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException
   {
	response.setContentType("text/html");
	PrintWriter out = response.getWriter();
        out.println("<title>Example</title>"+"<body bgcolor=FFFFFF>");
        String head = new String("显示你输入的内容");
        head = new String(head.getBytes("GB2312"),"8859_1");
        out.println("<h2 align='center'>"+head+"</h2>");
        String DATA = request.getParameter("DATA");
        if(!DATA.equals("")){
               out.println(DATA);
            } 
        else {
               String infor = new String("你没有文字输入");
               infor = new String(infor.getBytes("GB2312"),"8859_1");
                out.println(infor);
        }
        out.println("<p><A HREF=../servlets/Example.html>Back</A>");
        out.close();
}
}
