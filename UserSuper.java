public class UserSuper
{
  public static void main(String[] args)
  {
    Manager m = new Manager();
    m.name = "王飞";
    m.salary = 10000;
    m.department = "业务部";
    System.out.println(m.getSalary());
  }
}
class Employee
{
  public String name;
  public int salary;
  public String getSalary()
  {
    String str;
    str = "名字:" + name + "\nSalary:" + salary;
    return str;
  }
}

class Manager extends Employee
{
  public String department;
  public String getSalary()
  {
    // 使用super变量调用超类的方法
    return super.getSalary() + "\nDepartment:" + department;
  }
}
