public void sendText( MessageConnection conn, String text )
              throws IOException, InterruptedIOException {
    TextMessage msg = conn.newMessage( conn.TEXT_MESSAGE );
    msg.setPayloadText( text );
    conn.send( msg );
}

Sending binary data is almost identical: 

public void sendBinary( MessageConnection conn, byte[] data )
              throws IOException, InterruptedIOException {
    BinaryMessage msg = 
                       conn.newMessage( conn.BINARY_MESSAGE );
    msg.setPayloadData( data );
    conn.send( msg );
}

