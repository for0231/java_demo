import java.io.*;
  import javax.microedition.io.*;    
  public class HttpConnectionHelper { 
    public interface Callback {
      void prepareRequest( String originalURL, 
                           HttpConnection conn ) 
           throws IOException;
    }    
    public static HttpConnection connect( String url ) 
     	throws IOException {
      		return connect( url, null );
    	}   
    public static HttpConnection connect(String url, Callback callback ) 
        throws IOException {
      		HttpConnection conn = null;
      		String         originalURL = url;    
      		while( url != null ){
        	HttpConnection conn = (HttpConnection)
                          Connector.open( url );    
        	if( callback != null ){
          	callback.prepareRequest(originalURL,
					 conn );
        }    
        int rc = conn.getResponseCode();   
        switch( rc ){
            case HttpConnection.HTTP_MOVED_PERM:
            case HttpConnection.HTTP_MOVED_TEMP:
            case HttpConnection.HTTP_SEE_OTHER:
            case HttpConnection.HTTP_TEMP_REDIRECT:
            url = conn.getHeaderField( "Location");
            if( url != null && url.startsWith("/*") ){
                StringBuffer b = new StringBuffer();
                b.append( "http://" );
                b.append( conn.getHost() );
                b.append( ':' );
                b.append( conn.getPort() );
                b.append( url );
                url = b.toString();
              }
              conn.close();
              break;
            default:
              url = null;
              break;
        }
      }    
      return conn;
  }
}

  HttpConnection conn = HttpConnectionHelper.connect( "http://java.sun.com/jdc" );
  int  rc = conn.getResponseCode();    
  if( rc == HttpConnection.HTTP_OK ){
    InputStream in = conn.openInputStream();
    // 对响应操作....
} else {
    // 异常的响应代码
}
