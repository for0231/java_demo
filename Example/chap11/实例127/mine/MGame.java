  package mine;

  import java.util.*;
  import com.siemens.mp.io.*;
  import com.siemens.mp.game.*;
  import java.io.IOException;
  import javax.microedition.lcdui.*;

  public class MGame extends Canvas implements CommandListener
  {
      private static final int GRIDSIZE = 8;
      private static final int MINECOUNT = 15;

      private static Mine pMain;
      private static Display display;
      private static int Width, Height, selx, sely, leftbomb;
      private static byte grid[][];       // 用于存放扫雷数据
      private static boolean gameover;    // 是否结束

      private static Random rand;         // 产生随机数

      private static ExtendedImage ExScreenImg;   // 扩展图像层
      private static Graphics Exg;
      private static Image TitleImg, MineImg, fMineImg, HideImg, fHideImg, FlagImg, fFlagImg, NumImg[], fNumImg[];
      private static MelodyComposer melody;       // 音调

      // 定义菜单
      private static Command ContCmd = new Command("继续游戏",Command.OK, 0);
      private static Command StartCmd = new Command("新游戏",Command.OK, 1);
      private static Command ExitCmd = new Command("退出",Command.EXIT, 2);
      private static Command HelpCmd = new Command("帮助信息",Command.OK, 3);
      private static Command OKCmd = new Command("确定",Command.OK, 0);

      final private static String openSnd="开启声音", closeSnd="关闭声音", openVib="开启振动", closeVib="关闭振动";
      private static Command SndCmd, VibCmd;

      public static boolean Snd, Vib;

      public MGame(Mine pmine)
      {
          pMain = pmine;
          Width = 88 / GRIDSIZE;
          Height = (getHeight()-1) / GRIDSIZE;

          grid = new byte[Height][Width];
          rand = new Random((new Date()).getTime());
          NumImg = new Image[8];
          fNumImg = new Image[8];

          gameover = true;
          int i;
          // 预先载入图片
          try
          {
              TitleImg = Image.createImage("images/title.png");
              MineImg = Image.createImage("images/mine.png");
              fMineImg = Image.createImage("images/minef.png");
              HideImg = Image.createImage("images/hide.png");
              fHideImg = Image.createImage("images/hidef.png");
              FlagImg = Image.createImage("images/flag.png");
              fFlagImg = Image.createImage("images/flagf.png");
              for (i=1; i<=8; i++)
              {
                  NumImg[i-1] = Image.createImage("images/n"+i+".png");
                  fNumImg[i-1] = Image.createImage("images/n"+i+"f.png");
              }
          }
          catch(Exception exception){}

          // 初始化图像缓存（宽度必须为8的倍数）
          ExScreenImg = new ExtendedImage(Image.createImage(104, getHeight()));
          Exg = ExScreenImg.getImage().getGraphics();
          melody = new MelodyComposer();

          Snd = true;
          Vib = true;
          /////////////////////////////////////////////
          // 读取参数
          try
          {
              byte bflag[] = new byte[2];
              bflag[0] = bflag[1] = 1;
              File keyfile = new File();
              int fid = keyfile.open("para");
              if(keyfile.length(fid) > 0) keyfile.read(fid, bflag, 0, 2);
              Snd = (bflag[0] == 1);
              Vib = (bflag[1] == 1);
              keyfile.close(fid);
          }
          catch(IOException ioexception) { }
          catch(NullPointerException npe){ }
          //////////////////////////////////////////////

          if (Snd) SndCmd = new Command(closeSnd, Command.OK, 4);
          else SndCmd = new Command(openSnd, Command.OK, 4);
          if (Vib) VibCmd = new Command(closeVib, Command.OK, 5);
          else VibCmd = new Command(openVib, Command.OK, 5);

          // 添加菜单
          addCommand(StartCmd);
          addCommand(ExitCmd);
          addCommand(HelpCmd);
          addCommand(SndCmd);
          addCommand(VibCmd);

          // 画标题
          Exg.drawImage(TitleImg,0,0,20);
      }

      public void paint(Graphics grap)
      {
          ExScreenImg.blitToScreen(0,0);    // 将图像缓存内容在屏幕上显示出来
      }

      protected void keyPressed(int kcode)  // 按键响应
      {
          if (gameover) return;

          int bomb = 0;
          int oldx = selx;
          int oldy = sely;
          switch(kcode)
          {
          case '1':     // 1,挖开
              System.gc();  // 先释放无用的资源
              if (grid[sely][selx] >= 10 && grid[sely][selx] < 20)
              {
                  grid[sely][selx] -= 10;
                  if (grid[sely][selx] == 0) Expand(selx, sely);
              }
              else if (grid[sely][selx] < 10)
              {
                  if (!SafeExp(selx, sely)) bomb = 1;
              }
              break;

          case '3':     // 3,作标记
              System.gc();
              if (grid[sely][selx] >= 10)
              {
                  if (grid[sely][selx] < 20)
                  {
                      grid[sely][selx] += 10;
                      leftbomb --;
                  }
                  else
                  {
                      grid[sely][selx] -= 10;
                      leftbomb ++;
                  }
              }
              break;

          case '2':     // 2
          case -59:     //上键
              sely --;
              break;
          case '4':     // 4
          case -61:   // 左向键
              selx--;
              break;
          case '6':     // 6
          case -62:   // 右向键
              selx++;
              break;
          case '5':     // 5
          case '8':     // 8
          case -60:   // 下键
              sely++;
              break;
          }
          if (selx < 0) selx = 0;
          if (selx > Width-1) selx = Width-1;
          if (sely < 0) sely = 0;
          if (sely > Height-1) sely = Height-1;

          DrawBlock(oldx, oldy, false);
          if (bomb == 0)
              bomb = DrawBlock(selx, sely, true);
          else
              DrawBlock(selx, sely, true);

          Exg.setColor(0xffffff);
          Exg.fillRect(89, 26, 13, 13);
          Exg.setColor(0);
          Exg.drawString(""+leftbomb, 101, 26, Graphics.RIGHT | Graphics.TOP);

          if (bomb == 1)
          {
              gameover = true;
              SoundPlay(1);
              if (Vib) Vibrator.triggerVibrator(150);
              Exg.drawString("爆", 101, 39, Graphics.RIGHT | Graphics.TOP);
              Exg.drawString("炸", 101, 52, Graphics.RIGHT | Graphics.TOP);
          }
          if (Judge())
          {
              gameover = true;
              SoundPlay(0);
              Exg.drawString("成", 101, 39, Graphics.RIGHT | Graphics.TOP);
              Exg.drawString("功", 101, 52, Graphics.RIGHT | Graphics.TOP);
          }
          ExScreenImg.blitToScreen(0,0);
      }

      protected void keyRepeated(int kcode) //按钮连按响应
      {
          if (gameover) return;

          int oldx = selx;
          int oldy = sely;
          switch(kcode)
          {
          case '2':
          case -59:   //up
              sely --;
              break;
          case '4':
          case -61:   // LEFT
              selx--;
              break;
          case '6':
          case -62:   // RIGHT
              selx++;
              break;
          case '5':
          case '8':
          case -60:   // DOWN
              sely++;
              break;
          }
          if (selx < 0) selx = 0;
          if (selx > Width-1) selx = Width-1;
          if (sely < 0) sely = 0;
          if (sely > Height-1) sely = Height-1;

          DrawBlock(oldx, oldy, false);
          DrawBlock(selx, sely, true);
          ExScreenImg.blitToScreen(0,0);
      }

      // 菜单选择响应
      public void commandAction(Command cmd, Displayable displayable)
      {
          boolean savepara = false;
          if (cmd == ExitCmd)
              pMain.destroyApp(true);
          else if (cmd == StartCmd)
          {
              newGame();
          }
          else if (cmd == HelpCmd)
          {
              Form pHelp = new Form("扫雷 v0.1.0");
              pHelp.append("\u00A9Siemens Shanghai Mobile Communication Ltd.\n\n"
                  +"按键说明：\n2、4、6、8-移动焦点，1-挖，3-做标记。");
              pHelp.addCommand(OKCmd);
              display.setCurrent(pHelp);
              pHelp.setCommandListener(this);
          }
          else if (cmd == OKCmd)
          {
              activate(display);
          }
          else if (cmd == SndCmd)
          {
              Snd = !Snd;
              removeCommand(SndCmd);
              if (Snd) SndCmd = new Command(closeSnd,Command.OK, 4);
              else SndCmd = new Command(openSnd,Command.OK, 4);
              addCommand(SndCmd);
              savepara = true;
          }
          else if (cmd == VibCmd)
          {
              Vib = !Vib;
              removeCommand(VibCmd);
              if (Vib) VibCmd = new Command(closeVib,Command.OK, 5);
              else VibCmd = new Command(openVib,Command.OK, 5);
              addCommand(VibCmd);
              savepara = true;
          }

          if (savepara)
          {
              /////////////////////////////////////////////
              // 写入参数
              try
              {
                  byte bflag[] = new byte[2];
                  File keyfile = new File();
                  int fid = keyfile.open("para");
                  bflag[0] = (byte)(Snd ? 1 : 0);
                  bflag[1] = (byte)(Vib ? 1 : 0);
                  keyfile.write(fid, bflag, 0, 2);
                  keyfile.close(fid);
              }
              catch(IOException ioexception) {}
              catch(NullPointerException npe){}
              //////////////////////////////////////////////
          }
      }

      public void activate(Display disp)
      {
          display = disp;
          display.setCurrent(this); // 设置显示目标
          setCommandListener(this); // 监视菜单选择
      }

      // 是否全部完成判断
      private boolean Judge()
      {
          if (leftbomb == 0)
          {
              int i,j;
              for(i=0;i<Height; i++)
              {
                  for(j=0; j<Width; j++)
                  {
                      if (grid[i][j] >= 10 && grid[i][j] < 20) return false;
                  }
              }
              return true;
          }
          else
              return false;
      }

      // 开始新游戏
      private void newGame()
      {
          gameover = false;
          selx = 0;
          sely = 0;
          leftbomb = MINECOUNT;

          int i,j,x,y;
          for(i=0;i<Height; i++)
          {
              for(j=0; j<Width; j++)
                  grid[i][j] = 10;
          }

          for(i=0; i<MINECOUNT; i++)
          {
              while(true)
              {
                  x = Math.abs(rand.nextInt()) % Width;
                  y = Math.abs(rand.nextInt()) % Height;
                  if (grid[y][x] != 19)
                  {
                      grid[y][x] = 19;
                      break;
                  }
              }
          }

          for(i=0;i<Height; i++)
          {
              for(j=0; j<Width; j++)
              {

                  if (grid[i][j] == 19) continue;
                  int k, l;
                  for (k=-1; k<2; k++)
                  {
                      if (i+k<0) continue;
                      if (i+k>=Height) continue;
                      for (l=-1; l<2; l++)
                      {
                          if (l+j<0) continue;
                          if (l+j>=Width) continue;
                          if (k==0 && l==0) continue;

                          if (grid[i+k][j+l] == 19) grid[i][j]++;
                      }
                  }
              }
          }

          ExScreenImg.clear((byte)0);
          for (i=0; i<=Width; i++)
          {
              Exg.drawLine(i*GRIDSIZE, 0, i*GRIDSIZE, Height*GRIDSIZE);
          }
          for (i=0; i<=Height; i++)
          {
              Exg.drawLine(0, i*GRIDSIZE, Width*GRIDSIZE, i*GRIDSIZE);
          }

          for(i=0;i<Height; i++)
          {
              for(j=0; j<Width; j++)
              {
                  Exg.drawImage(HideImg, j*GRIDSIZE+1, i*GRIDSIZE+1, 20);
              }
          }
          Exg.drawImage(fHideImg, selx*GRIDSIZE+1, sely*GRIDSIZE+1, 20);
          Exg.drawString("剩", 101, 0, Graphics.RIGHT | Graphics.TOP);
          Exg.drawString("余", 101, 13, Graphics.RIGHT | Graphics.TOP);
          Exg.drawString(""+leftbomb, 101, 26, Graphics.RIGHT | Graphics.TOP);

          SoundPlay(2);
      }

      // 画一个格子
      // focus标示着个格子是否为焦点,如果为true,则要画反色图形
      private int DrawBlock(int x, int y, boolean focus)
      {
          int retval = 0;
          if (grid[y][x] == 0)
          {
              if (!focus) Exg.setColor(0xffffff);
              Exg.fillRect(x*GRIDSIZE+1, y*GRIDSIZE+1, GRIDSIZE-1, GRIDSIZE-1);
              if (!focus) Exg.setColor(0);
          }
          else if (grid[y][x]>0 && grid[y][x]<9)
          {
              if (focus)
                  Exg.drawImage(fNumImg[grid[y][x]-1], x*GRIDSIZE+1, y*GRIDSIZE+1, 20);
              else
                  Exg.drawImage(NumImg[grid[y][x]-1], x*GRIDSIZE+1, y*GRIDSIZE+1, 20);
          }
          else if (grid[y][x] == 9)
          {
              int i, j;
              for(i=0;i<Height; i++)
              {
                  for(j=0; j<Width; j++)
                  {
                      if (grid[i][j] == 19 || grid[i][j] == 29)
                          Exg.drawImage(MineImg, j*GRIDSIZE+1, i*GRIDSIZE+1, 20);
                  }
              }
              if (focus)
                  Exg.drawImage(fMineImg, x*GRIDSIZE+1, y*GRIDSIZE+1, 20);

              retval = 1;
          }
          else if (grid[y][x]>=10 && grid[y][x]<20)
          {
              if (focus)
                  Exg.drawImage(fHideImg, x*GRIDSIZE+1, y*GRIDSIZE+1, 20);
              else
                  Exg.drawImage(HideImg, x*GRIDSIZE+1, y*GRIDSIZE+1, 20);
          }
          else
          {
              if (focus)
                  Exg.drawImage(fFlagImg, x*GRIDSIZE+1, y*GRIDSIZE+1, 20);
              else
                  Exg.drawImage(FlagImg, x*GRIDSIZE+1, y*GRIDSIZE+1, 20);
          }

          return retval;    // 返回值：1-画的是地雷；0-不是
      }

      private void Expand(int x, int y)
      {
          int i, j;
          for (i=-1; i<2; i++)
          {
              if (y+i<0) continue;
              if (y+i>=Height) continue;
              for (j=-1; j<2; j++)
              {
                  if (x+j<0) continue;
                  if (x+j>=Width) continue;
                  if (i==0 && j==0) continue;

                  if (grid[y+i][x+j] >= 10 && grid[y+i][x+j] < 20)
                  {
                      grid[y+i][x+j] -= 10;
                      DrawBlock(x+j, y+i, false);
                      if (grid[y+i][x+j] == 0) Expand(x+j, y+i);
                  }
              }
          }
      }

      private boolean SafeExp(int x, int y)
      {
          int i, j, flag = 0;
          for (i=-1; i<2; i++)
          {
              if (y+i<0) continue;
              if (y+i>=Height) continue;
              for (j=-1; j<2; j++)
              {
                  if (x+j<0) continue;
                  if (x+j>=Width) continue;
                  if (i==0 && j==0) continue;
                  if (grid[y+i][x+j] > 20) flag ++;
              }
          }
          if (flag != grid[y][x]) return true;


          boolean retval = true;
          for (i=-1; i<2; i++)
          {
              if (y+i<0) continue;
              if (y+i>=Height) continue;
              for (j=-1; j<2; j++)
              {
                  if (x+j<0) continue;
                  if (x+j>=Width) continue;
                  if (i==0 && j==0) continue;
                  if (grid[y+i][x+j] == 19) // 翻到地雷
                  {
                      grid[y+i][x+j] = 9;
                      DrawBlock(x+j, y+i, true);
                      retval = false;
                  }
                  else if (grid[y+i][x+j] > 20 && grid[y+i][x+j] != 29) // 在标记错误的地方画叉
                  {
                      Exg.drawLine((x+j)*GRIDSIZE+1, (y+i)*GRIDSIZE+1, (x+j+1)*GRIDSIZE-1, (y+i+1)*GRIDSIZE-1);
                      Exg.drawLine((x+j)*GRIDSIZE+1, (y+i+1)*GRIDSIZE-1, (x+j+1)*GRIDSIZE-1, (y+i)*GRIDSIZE+1);
                  }
              }
          }

          if (retval)
          {
              for (i=-1; i<2; i++)
              {
                  if (y+i<0) continue;
                  if (y+i>=Height) continue;
                  for (j=-1; j<2; j++)
                  {
                      if (x+j<0) continue;
                      if (x+j>=Width) continue;
                      if (i==0 && j==0) continue;

                      if (grid[y+i][x+j] >= 10 && grid[y+i][x+j] < 20)
                      {
                          grid[y+i][x+j] -= 10;
                          DrawBlock(x+j, y+i, false);
                          if (grid[y+i][x+j] == 0) Expand(x+j, y+i);
                      }
                  }
              }
          }
          return retval;
      }

      // 游戏音效
      private void SoundPlay(int n)
      {
          if (!Snd) return;
          melody.resetMelody();
          try
          {
              if (n == 0)       //通关
              {
                  melody.setBPM(110);
                  melody.appendNote(MelodyComposer.TONE_C2, MelodyComposer.TONELENGTH_1_16);
                  melody.appendNote(MelodyComposer.TONE_E2, MelodyComposer.TONELENGTH_1_16);
                  melody.appendNote(MelodyComposer.TONE_G2, MelodyComposer.TONELENGTH_1_16);
                  melody.appendNote(MelodyComposer.TONE_E2, MelodyComposer.TONELENGTH_1_16);
                  melody.appendNote(MelodyComposer.TONE_G2, MelodyComposer.TONELENGTH_1_16);
                  melody.appendNote(MelodyComposer.TONE_C3, MelodyComposer.TONELENGTH_1_8);
              }
              else if(n == 1)   // 爆炸
              {
                  melody.setBPM(132);
                  melody.appendNote(MelodyComposer.TONE_D1, MelodyComposer.TONELENGTH_1_64);
                  melody.appendNote(MelodyComposer.TONE_E1, MelodyComposer.TONELENGTH_1_32);
                  melody.appendNote(MelodyComposer.TONE_D1, MelodyComposer.TONELENGTH_1_32);
                  melody.appendNote(MelodyComposer.TONE_C1, MelodyComposer.TONELENGTH_1_64);
                  melody.appendNote(MelodyComposer.TONE_A1, MelodyComposer.TONELENGTH_1_64);
              }
              else              //新游戏
              {
                  melody.setBPM(100);
                  melody.appendNote(MelodyComposer.TONE_G3, MelodyComposer.TONELENGTH_1_16);
                  melody.appendNote(MelodyComposer.TONE_G3, MelodyComposer.TONELENGTH_1_16);
                  melody.appendNote(MelodyComposer.TONE_H3, MelodyComposer.TONELENGTH_1_16);
                  melody.appendNote(MelodyComposer.TONE_G3, MelodyComposer.TONELENGTH_1_8);
              }

              Melody music = melody.getMelody();
              music.play();
              music = null;
          }
          catch (Exception exception){}
      }

  }
