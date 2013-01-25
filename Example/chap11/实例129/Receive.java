import java.io.*;
import javax.microedition.io.*;
import javax.wireless.messaging.*;

MessageConnection conn = null;
String url = "sms://:5678"; // no phone number!

try {
    conn = (MessageConnection) Connector.open( url );
    while( true ){
        Message msg = conn.receive(); // blocks
        if( msg instanceof BinaryMessage ){
            byte[] data = 
                 ((BinaryMessage) msg).getPayloadData();
            // do something here
        } else {
            String text = 
                 ((TextMessage) msg).getPayloadText();
            // do something here
        }
    }
}
catch( Exception e ){
    // handle it
}
finally {
    if( conn != null ){
        try { conn.close(); } catch( Exception e ){}
    }
}

