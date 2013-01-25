import Company.Manager;
public class UsePackage
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
