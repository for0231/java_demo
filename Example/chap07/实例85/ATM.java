import java.awt.*;
import java.awt.event.*;
import java.applet.Applet;
import java.net.*;
public class ATM extends Applet implements Runnable {

   int index;
   Thread changer;	
   boolean stopFlag=false;
   Image[] images;
   Image mouth;

   Button b;
   boolean flag=false;
   
   public void init() {
       	
        setBackground(Color.white);
       	images=new Image[4];
     
        images[0]=getImage(getCodeBase(),"money1.jpg");
        images[1]=getImage(getCodeBase(),"money2.jpg");
        images[2]=getImage(getCodeBase(),"money3.jpg");
        images[3]=getImage(getCodeBase(),"money4.jpg");
       mouth=getImage(getCodeBase(),"mouth.jpg");

        
        b=new Button("  够啦！ ");
        add(b);
        
        b.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(flag) {start();flag=false;b.setLabel("  够啦！ ");}
                else{stop();flag=true;b.setLabel(" 开始取款 ");}
        }
        });


   }

   public void start(){
      changer=new Thread(this);
      stopFlag=false;
      index=0;
      changer.start();
   }


   public void paint(Graphics g) {
      g.drawImage(mouth,100,45,this);
      synchronized(this){if(index<4)g.drawImage(images[index],107,50,this);}
    }

   public void stop(){
     stopFlag=true;
   }

   public void run(){

    while(!stopFlag){

        repaint(); 
        try{
	        Thread.sleep(500);
        }catch(Exception e){}

        synchronized(this){
            index++;
            if(index>4) index=0;
        }



    }

    synchronized(this){index=4;}
    repaint();

}//run()


}
