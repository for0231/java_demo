import java.awt.*;
import java.applet.*;
import java.applet.Applet;
public class UsePanel extends Applet
{
  Label lblName, lblNumber, lblSex, lblJob,lblText;
  TextField tfName, tfNumber;
  Checkbox chMale, chFemale;
  CheckboxGroup c;
  TextArea taText;
  Choice chJob;
  Button btnOk, btnCancel;
  Panel p1,p2,p3,p4,p5,p6,p7,p8,p9;
  public void init()
  {
    lblName = new Label("姓名: ");
    lblNumber = new Label("身份证号: ");
    lblSex = new Label("性别");
    lblJob = new Label("职业");
    lblText = new Label("个性化宣言: ");
    tfName = new TextField(23);
    tfNumber = new TextField(20);
    taText = new TextArea(10, 20);
    c = new CheckboxGroup();
    chMale = new Checkbox("男", c, true);
    chFemale = new Checkbox("女", c, false);
    chJob = new Choice();
    chJob.add("计算机业");
    chJob.add("医生");
    chJob.add("教师");
    chJob.add("军队");
    btnOk = new Button("确定");
    btnCancel = new Button("取消");
    p1 = new Panel();
    p2 = new Panel();
    p3 = new Panel();
    p4 = new Panel();
    p5 = new Panel();
    p6 = new Panel();
    p7 = new Panel(new BorderLayout());
    p8 = new Panel();
    p9 = new Panel(new BorderLayout());
    p1.add(lblName);
    p1.add(tfName);
    p2.add(lblNumber);
    p2.add(tfNumber);
    p3.add(lblSex);
    p3.add(chMale);
    p3.add(chFemale);
    p4.add(chFemale);
    p4.add(chJob);
    p5.add(p3);
    p5.add(p4);
    p6.setLayout(new BorderLayout());
    p6.add(p1, BorderLayout.NORTH);
    p6.add(p2, BorderLayout.CENTER);
    p6.add(p5, BorderLayout.SOUTH);
    p7.add(lblText, BorderLayout.NORTH);
    p7.add(taText, BorderLayout.CENTER);
    p8.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));
    p8.add(btnOk);
    p8.add(btnCancel);
    p9.add(p6, BorderLayout.NORTH);
    p9.add(p7, BorderLayout.CENTER);
    p9.add(p8, BorderLayout.SOUTH);
    add(p9);
  }
}
