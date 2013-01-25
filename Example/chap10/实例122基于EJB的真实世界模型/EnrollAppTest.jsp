<%@ page 
    language="java"
    import=
     "java.sql.*,
      java.util.*,
      enroll.ejb.*,
      javax.naming.Context,
      javax.naming.InitialContext,
      javax.rmi.PortableRemoteObject"
    contentType="text/html; charset=Big5" %>
    
<%
  try{

    Context initial=new InitialContext();
    Object objref=
      initial.lookup("ejb/StudentEntityBean");
    StudentHome sHome=
        (StudentHome)PortableRemoteObject.narrow(objref,
          StudentHome.class);
          
//新增学生基本资料	
    sHome.create("11111111", "Tony");
    out.println("[新增学生基本资料]student_id:11111111,"
      +"name:Tony<br>");
    sHome.create("22222222", "Mary");
    out.println("[新增学生基本资料]student_id:22222222,"
      +"name:Mary<br>");
      
    objref=initial.lookup("ejb/CourseEntityBean");
    CourseHome cHome=
      (CourseHome)PortableRemoteObject.narrow(
        objref, CourseHome.class);
        
//新增课程资料	
    cHome.create("00000001", "EJB Programming");
    out.println("[新增课程资料]course_id:00000001,"
      +"name:EJB Programming<br>");  
    cHome.create("00000002", "Java Server Page");
    out.println("[新增课程资料]course_id:00000002,"
      +"name:Java Server Page<br>");  
    cHome.create("00000003", "Servlet Programming");
    out.println("[新增课程资料]course_id:00000003,"
      +"name:Servlet Programming<br>"); 
  
    out.println(
      "--注册Tony的选课资料:00000001和00000003--<br>");
      
    objref=initial.lookup("ejb/EnrollSessionBean");
    EnrollSessionHome eHome=
      (EnrollSessionHome)PortableRemoteObject.narrow(
        objref, EnrollSessionHome.class);
          
    ArrayList courseItems=new ArrayList();
    courseItems.add("00000001");
    courseItems.add("00000003");
        
    EnrollSession enrollsession=eHome.create("11111111");
    enrollsession.enroll(courseItems);
    
    out.println("--查询Tony的选课资料--<br>");
    
    objref=initial.lookup("ejb/EnrollEntityBean");
    EnrollHome enrollhome=
      (EnrollHome)PortableRemoteObject.narrow(
        objref, EnrollHome.class);
    Enroll enroll=
      enrollhome.findByPrimaryKey("11111111");
              
    ArrayList courses=enroll.getCourseItems();
    Iterator i=courses.iterator();
    while (i.hasNext()) {
      String course_id=(String)i.next();
      Course course=cHome.findByPrimaryKey(course_id);
      out.println(course_id+":"+course.getName()+"<br>");
    }
    
    out.println("完成!");
	
  }catch (Exception ex){
    out.println(ex.toString());
    ex.printStackTrace();
  }    
%>
