import java.net.*;
import java.io.*;
import java.util.*;

class FtpClient {
    static final boolean debug = false;

    public static final int FTP_PORT = 21; 

    static int  FTP_SUCCESS = 1;
    static int  FTP_TRY_AGAIN = 2;
    static int  FTP_ERROR = 3;

    /** socket for data transfer */
    private Socket      dataSocket = null;
    private boolean     replyPending = false;
    private boolean     binaryMode = false;
    private boolean     passiveMode = false;
    /** user name for login */
    String              user = null;
    /** password for login */
    String              password = null;

    /** last command issued */
    String              command;

    /** The last reply code from the ftp daemon. */
    int                 lastReplyCode;

    /** Welcome message from the server, if any. */
    public String       welcomeMsg;

    /** Array of strings (usually 1 entry) for the last reply
        from the server. */
    protected Vector    serverResponse = new Vector(1);

    /** Socket for communicating with server. */
    protected Socket    serverSocket = null;

    /** Stream for printing to the server. */
    public PrintWriter  serverOutput;

    /** Buffered stream for reading replies from server. */
    public InputStream  serverInput;


    /** Return server connection status */
    public boolean serverIsOpen() {
        return serverSocket != null;
    }

	/**Set Passive mode Trasfers*/
	public void setPassive(boolean mode) {
		passiveMode = mode;
	}
	
    public int readServerResponse() throws IOException {
        StringBuffer    replyBuf = new StringBuffer(32);
        int             c;
        int             continuingCode = -1;
        int             code = -1;
        String          response;
        if (debug) System.out.println("readServerResponse start");
 
        try{
        while (true) {
            //if (debug) System.out.println("readServerResponse outer while loop: "+ serverInput.available());
           
            while ((c = serverInput.read()) != -1) {
  
               if (c == '\r') {
                    if ((c = serverInput.read()) != '\n')
                        replyBuf.append('\r');
                }
                replyBuf.append((char)c);               
                if (c == '\n')
                    break;                
            }
 
            if (debug) System.out.println("Now past inner while loop");

            response = replyBuf.toString();
            replyBuf.setLength(0);
            if (debug) {
                System.out.print(response);
            }
            try {
                code = Integer.parseInt(response.substring(0, 3));
            } catch (NumberFormatException e) {
                code = -1;
            } catch (StringIndexOutOfBoundsException e) {
                /* this line doesn't contain a response code, so
                   we just completely ignore it */
                continue;
            }
            serverResponse.addElement(response);
            if (continuingCode != -1) {
                /* we've seen a XXX- sequence */
                if (code != continuingCode ||
                    (response.length() >= 4 && response.charAt(3) == '-')) {
                    continue;
                } else {
                    /* seen the end of code sequence */
                    continuingCode = -1;
                    break;
                }
            } else if (response.length() >= 4 && response.charAt(3) == '-') {
                continuingCode = code;
                continue;
            } else {
                break;
            }
        }
        }catch(Exception e){e.printStackTrace();}
        if (debug) System.out.println("readServerResponse done");
        return lastReplyCode = code;
    }

    /** Sends command <i>cmd</i> to the server. */
    public void sendServer(String cmd) {
        if (debug) System.out.println("sendServer start");
        serverOutput.println(cmd);
        if (debug) System.out.println("sendServer done");

    }
   
    /** Returns all server response strings. */
    public String getResponseString() {
        String s = new String();
        for(int i = 0;i < serverResponse.size();i++) {
           s+=serverResponse.elementAt(i);
        }
        serverResponse = new Vector(1);
        return s;
    }

   public String getResponseStringNoReset() {
        String s = new String();
        for(int i = 0;i < serverResponse.size();i++) {
           s+=serverResponse.elementAt(i);
        }
        return s;
    }


    /** 
     * issue the QUIT command to the FTP server and close the connection. 
     */
    public void closeServer() throws IOException {
        if (serverIsOpen()) {
            issueCommand("QUIT");
        if (! serverIsOpen()) {
              return;
            }
            serverSocket.close();
            serverSocket = null;
            serverInput = null;
            serverOutput = null;
        }
    }

    protected int issueCommand(String cmd) throws IOException {
        command = cmd;

        int reply;
        if (debug) System.out.println(cmd);
        if (replyPending) {
            if (debug) System.out.println("replyPending");
            if (readReply() == FTP_ERROR)
                System.out.print("Error reading pending reply\n");
        }
        replyPending = false;
        do {
            sendServer(cmd);
            reply = readReply();
            //if (debug) System.out.println("in while loop of issueCommand method");
        } while (reply == FTP_TRY_AGAIN);
        return reply;
    }

    protected void issueCommandCheck(String cmd) throws IOException {
        if (debug) System.out.println("issueCommandCheck");
        if (issueCommand(cmd) != FTP_SUCCESS) {            
            throw new FtpProtocolException(cmd);
            }
    }

    protected int readReply() throws IOException {
        lastReplyCode = readServerResponse();

        switch (lastReplyCode / 100) {
        case 1:
            replyPending = true;
            /* falls into ... */

        case 2://This case is for future purposes. If not properly used, it might cause an infinite loop. 
               //Don't add any code here , unless you know what you are doing.
                
        case 3:
            return FTP_SUCCESS;

        case 5:
            if (lastReplyCode == 530) {
                if (user == null) {
                    throw new FtpLoginException("Not logged in");
                }
                return FTP_ERROR;
            }
            if (lastReplyCode == 550) {
                if (!command.startsWith("PASS"))
                     throw new FileNotFoundException(command);
                else
                     throw new FtpLoginException("Error: Wrong Password!");     
            }
        }

        /* this statement is not reached */
        return FTP_ERROR;
    }

     protected Socket openDataConnection(String cmd) throws IOException {
        ServerSocket portSocket = null;
        String       portCmd;
        InetAddress  myAddress = InetAddress.getLocalHost();
        byte         addr[] = myAddress.getAddress();
        int          shift;
        String       ipaddress;
        int          port = 0;
        IOException  e;
		if (this.passiveMode) {

        /* First let's attempt to initiate Passive transfers */
	
      try {    // PASV code
        getResponseString();
        if (issueCommand("PASV") == FTP_ERROR) {
          e = new FtpProtocolException("PASV");
          throw e;
        }
        String reply = getResponseStringNoReset();
        reply = reply.substring(reply.lastIndexOf("(")+1,reply.lastIndexOf(")"));
        StringTokenizer st = new StringTokenizer(reply, ",");
        String[] nums = new String[6];
        int i = 0;
			  while(st.hasMoreElements()) {
				  try {
					  nums[i] = st.nextToken();
					  i++;
				  } catch(Exception a){a.printStackTrace();}
			  }
			  ipaddress = nums[0]+"."+nums[1]+"."+nums[2]+"."+nums[3];

			  try {
				  int firstbits = Integer.parseInt(nums[4]) << 8;
				  int lastbits = Integer.parseInt(nums[5]);
				  port = firstbits + lastbits;
			  } catch(Exception b) {b.printStackTrace();}

			  if((ipaddress != null) && (port != 0)) {
				  dataSocket = new Socket(ipaddress, port);}
			  else{
				  e = new FtpProtocolException("PASV");
				  throw e;
			  }

			  if (issueCommand(cmd) == FTP_ERROR) {
				  e = new FtpProtocolException(cmd);
				  throw e;
			  }

			} catch (FtpProtocolException fpe) {  // PASV was not supported...resort to PORT

			portCmd = "PORT ";

			/* append host addr */
			for (int i = 0; i < addr.length; i++) {
				portCmd = portCmd + (addr[i] & 0xFF) + ",";
			}
      try {
        portSocket = new ServerSocket(20000);
         /* append port number */
        portCmd = portCmd + ((portSocket.getLocalPort() >>> 8) & 0xff) + ","
             + (portSocket.getLocalPort() & 0xff);
        if (issueCommand(portCmd) == FTP_ERROR) {
          e = new FtpProtocolException("PORT");
          throw e;
        }

        if (issueCommand(cmd) == FTP_ERROR) {
          e = new FtpProtocolException(cmd);
          throw e;
        }
        dataSocket = portSocket.accept();
      }
      finally {
        if(portSocket != null)
          portSocket.close();
      }

			dataSocket = portSocket.accept();
			portSocket.close();

			}
		}//end if passive
		else
    {  //do a port transfer

      portCmd = "PORT ";

      /* append host addr */
      for (int i = 0; i < addr.length; i++) {
        portCmd = portCmd + (addr[i] & 0xFF) + ",";
      }
      try {
        portSocket = new ServerSocket(20000);
         /* append port number */
        portCmd = portCmd + ((portSocket.getLocalPort() >>> 8) & 0xff) + ","
             + (portSocket.getLocalPort() & 0xff);
        if (issueCommand(portCmd) == FTP_ERROR) {
          e = new FtpProtocolException("PORT");
          throw e;
        }

        if (issueCommand(cmd) == FTP_ERROR) {
          e = new FtpProtocolException(cmd);
          throw e;
        }
        dataSocket = portSocket.accept();
      }
      finally {
        if(portSocket != null)
          portSocket.close();
      }

      dataSocket = portSocket.accept();
      portSocket.close();
	   }//end of port transfer

        return dataSocket;     // return the dataSocket
    }


    /** open a FTP connection to host <i>host</i>. */
    public void openServer(String host) throws IOException, UnknownHostException {
        int port = FTP_PORT;
        if (serverSocket != null)
            closeServer(); 
        serverSocket = new Socket(host, FTP_PORT);    
        serverOutput = new PrintWriter(new BufferedOutputStream(serverSocket.getOutputStream()),true);
        serverInput = new BufferedInputStream(serverSocket.getInputStream());
    }

    /** open a FTP connection to host <i>host</i> on port <i>port</i>. */
    public void openServer(String host, int port) throws IOException, UnknownHostException {
        if (serverSocket != null)
            closeServer();
        serverSocket = new Socket(host, port);
        //serverSocket.setSoLinger(true,30000);
        serverOutput = new PrintWriter(new BufferedOutputStream(serverSocket.getOutputStream()),
                                       true);
        serverInput = new BufferedInputStream(serverSocket.getInputStream());

        if (readReply() == FTP_ERROR)
            throw new FtpConnectException("Welcome message");
    }


    /** 
     * login user to a host with username <i>user</i> and password 
     * <i>password</i> 
     */
    public void login(String user, String password) throws IOException {
       
        if (!serverIsOpen())
            throw new FtpLoginException("Error: not connected to host.\n");
        this.user = user;
        this.password = password;
        if (issueCommand("USER " + user) == FTP_ERROR)
            throw new FtpLoginException("Error: User not found.\n");
        if (password != null && issueCommand("PASS " + password) == FTP_ERROR)
            throw new FtpLoginException("Error: Wrong Password.\n");       
    }

    /** 
     * login user to a host with username <i>user</i> and no password 
     * such as HP server which uses the form "<username>/<password>,user.<group>
     */
    public void login(String user) throws IOException {

        if (!serverIsOpen())
            throw new FtpLoginException("not connected to host");
        this.user = user;        
        if (issueCommand("USER " + user) == FTP_ERROR)
            throw new FtpLoginException("Error: Invalid Username.\n");                 
    }

    /** GET a file from the FTP server in Ascii mode*/
    public BufferedReader getAscii(String filename) throws IOException {     
        Socket  s = null;
        try {
            s = openDataConnection("RETR " + filename);
        } catch (FileNotFoundException fileException) {fileException.printStackTrace();}
        return new BufferedReader( new InputStreamReader(s.getInputStream()));          
    }

    /** GET a file from the FTP server in Binary mode*/
    public BufferedInputStream getBinary(String filename) throws IOException {     
        Socket  s = null;
        try {
            s = openDataConnection("RETR " + filename);
        } catch (FileNotFoundException fileException) {fileException.printStackTrace();}
        return new BufferedInputStream(s.getInputStream());          
    }


    /** PUT a file to the FTP server in Ascii mode*/
    public BufferedWriter putAscii(String filename) throws IOException {
        Socket s = openDataConnection("STOR " + filename);
        return new BufferedWriter(new OutputStreamWriter(s.getOutputStream()),4096);
    }
     
    /** PUT a file to the FTP server in Binary mode*/
    public BufferedOutputStream putBinary(String filename) throws IOException {
        Socket s = openDataConnection("STOR " + filename);
        return new BufferedOutputStream(s.getOutputStream());
    }

    /** APPEND (with create) to a file to the FTP server in Ascii mode*/
    public BufferedWriter appendAscii(String filename) throws IOException {
        Socket s = openDataConnection("APPE " + filename);
        return new BufferedWriter(new OutputStreamWriter(s.getOutputStream()),4096);
    }

    /** APPEND (with create) to a file to the FTP server in Binary mode*/
    public BufferedOutputStream appendBinary(String filename) throws IOException {
        Socket s = openDataConnection("APPE " + filename);
        return new BufferedOutputStream(s.getOutputStream());
    }



   /** NLIST files on a remote FTP server */
    public BufferedReader nlist() throws IOException {
        Socket s = openDataConnection("NLST");        
        return new BufferedReader( new InputStreamReader(s.getInputStream()));
    }

    /** LIST files on a remote FTP server */
    public BufferedReader list() throws IOException {
        Socket s = openDataConnection("LIST");        
        return new BufferedReader( new InputStreamReader(s.getInputStream()));
    }

    /** CD to a specific directory on a remote FTP server */
    public void cd(String remoteDirectory) throws IOException {
        issueCommandCheck("CWD " + remoteDirectory);
    }

    /** Rename a file on the remote server */
    public void rename(String oldFile, String newFile) throws IOException {
         issueCommandCheck("RNFR " + oldFile);
         issueCommandCheck("RNTO " + newFile);
    }
      
    /** Site Command */ 
    public void site(String params) throws IOException {
         issueCommandCheck("SITE "+ params);
    }            
	
    /** Set transfer type to 'I' */
    public void binary() throws IOException {
        issueCommandCheck("TYPE I");
        binaryMode = true;
    }

    /** Set transfer type to 'A' */
    public void ascii() throws IOException {
        issueCommandCheck("TYPE A");
        binaryMode = false;
    }

    /** Send Abort command */
    public void abort() throws IOException {
        issueCommandCheck("ABOR");
    }

    /** Go up one directory on remots system */
    public void cdup() throws IOException {
        issueCommandCheck("CDUP");
    }

    /** Create a directory on the remote system */
    public void mkdir(String s) throws IOException {
        issueCommandCheck("MKD " + s);
    }

    /** Delete the specified directory from the ftp file system */
    public void rmdir(String s) throws IOException {
        issueCommandCheck("RMD " + s);
    }

    /** Delete the file s from the ftp file system */
    public void delete(String s) throws IOException {
        issueCommandCheck("DELE " + s);
    }

    /** Get the name of the present working directory on the ftp file system */
    public void pwd() throws IOException {
        issueCommandCheck("PWD");
    }
    
    /** Retrieve the system type from the remote server */
    public void syst() throws IOException {
        issueCommandCheck("SYST");
    }


    /** New FTP client connected to host <i>host</i>. */
    public FtpClient(String host) throws IOException {      
        openServer(host, FTP_PORT);      
    }

    /** New FTP client connected to host <i>host</i>, port <i>port</i>. */
    public FtpClient(String host, int port) throws IOException {
        openServer(host, port);
    }
    
    /** Method to demonstrate use of FtpClient class */

    public static void main (String args []) throws IOException{
      System.out.println("Demo of FtpClient class.\n");

      // standard login procedures, must be done on all ftp servers 

      FtpClient f = new FtpClient("ftp.sun.com");

      System.out.print(f.getResponseString());

      f.login("anonymous","me@abc.com");
      System.out.print(f.getResponseString());
                        
      f.pwd(); 
      System.out.println(f.command);                  
      System.out.print(f.getResponseString());

      f.setPassive(true);

      // here's how you can do a listing

      System.out.println("\nDemo of nlist() function");

      f.ascii();  // set client to ascii mode to get text listing   
      System.out.println(f.command);              
      System.out.print(f.getResponseString());
          
      BufferedReader t = f.nlist();     // f.list gives a few more details
      System.out.println(f.command);
      System.out.print(f.getResponseString());  
      while( true ) {
         String stringBuffer = t.readLine();
         if( stringBuffer == null ) break;
         else System.out.println(stringBuffer);               
      }
      t.close();
      System.out.print(f.getResponseString());  
      // here's how to get a file using the getAscii() function.  The getBinary() function is similar in use  
      
      System.out.println("\nDemo of getAscii() function");

      f.ascii();           //  set transfer mode to ASCII, it has to be done by the user.
      System.out.println(f.command);
      System.out.print(f.getResponseString());  
      BufferedReader bufGet = f.getAscii("welcome.msg");
      System.out.println(f.command);      
      System.out.print(f.getResponseString());      
      PrintWriter pOut = new PrintWriter(new BufferedWriter(new FileWriter("welcome.msg")));
      int i;                                
      char c[] = new char[4096];
      while ((i = bufGet.read(c)) != -1) 
        pOut.write(c,0,i);                                                   
      bufGet.close();
      pOut.close();            
        
      System.out.print(f.getResponseString());     

      
      // here's how to APPEND an ASCII file using the appendAscii() function.  The appendBinary() function
      // is similar in use.  I am leaving this code commented out because you can't send files to the 
      // sun ftp site but you can try this on another ftp server
    
	/*
      System.out.println("\nDemo of appendAscii() function");
      BufferedWriter bufAppe;
      String localFile = "file name goes here"; 
      f.ascii();
      System.out.println(f.command);
      try {
      bufAppe = f.appendAscii(localFile);
      System.out.println(f.command);
      System.out.print(f.getResponseString());     
      
	FileReader fIn = new FileReader(localFile);            
      BufferedReader bufIn = new BufferedReader(fIn);
      int k;
      char b[] = new char[1024];
      while ((k = bufIn.read(b)) != -1) 
        bufAppe.write(b,0,k);                                  
      bufIn.close();
      bufAppe.flush();
      bufAppe.close();                 
      }catch(Exception appendErr) {
         System.out.println(appendErr.toString());//printStackTrace();
         
        }

      System.out.print(f.getResponseString()); 
	


      // here's how to send a binary file using the putBianary() function.  The putAscii() function
      // is similar in use.  I am leaving this code commented out because you can't send files to the 
      // sun ftp site but you can try this on another ftp server
    

      System.out.println("\nDemo of putBinary() function");
 
      String localFile = "file name goes here"; 
      f.binary();
      System.out.println(f.command);
      BufferedOutputStream bufPut = f.putBinary(localFile);
      System.out.println(f.command);
      System.out.print(f.getResponseString());     
      BufferedInputStream bufIn = new BufferedInputStream(new FileInputStream(localFile));
      int j;
      byte b[] = new byte[1024];
      while ((j = bufIn.read(b)) != -1) 
        bufPut.write(b,0,j);                                  
      bufIn.close();
      bufPut.flush();
      bufPut.close();                 

      System.out.print(f.getResponseString()); 
*/
      // close the connection

      f.closeServer();
      System.out.println(f.command);
      System.out.print(f.getResponseString());
    }


}

class FtpLoginException extends FtpProtocolException {
    FtpLoginException(String s) {
        super(s);
    }
}
class FtpConnectException extends FtpProtocolException {
    FtpConnectException(String s) {
        super(s);
    }
}

class FtpProtocolException extends IOException {
    FtpProtocolException(String s) {
        super(s);     
    }
}