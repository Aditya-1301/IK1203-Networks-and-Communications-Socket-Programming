package tcpclient;
import java.net.*;
import java.io.*;

public class TCPClient {
    Socket socket;
    boolean shutdown;
    Integer timeout;
    Integer limit;

    public TCPClient(boolean shutdown, Integer timeout, Integer limit) {
        this.shutdown = shutdown;
        this.timeout = timeout;
        this.limit = limit;
    }

    public byte[] askServer(String hostname, int port, byte [] toServerBytes) throws IOException {
        socket = new Socket(hostname,port);
        if(timeout!=null) socket.setSoTimeout(this.timeout);
        //if(this.limit!=null) socket.setReceiveBufferSize(this.limit);
        byte [] dataFromServer = new byte[1024];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //Input stream and output stream
        InputStream iStream = socket.getInputStream(); // We use this to receive stuff from the server
        OutputStream oStream = socket.getOutputStream(); // We use this to send stuff to the server
        oStream.write(toServerBytes);
        if(shutdown == true) socket.shutdownOutput();
        int  totalData = 0;
        int length;
        while(socket.isConnected()){
            if(limit != null && totalData >= limit){
                break;
            }
            try{
                length = iStream.read(dataFromServer);
            }
            catch (Exception e){
                break;
            }
            if(length != -1) baos.write(dataFromServer,0,length);
            else break;
            totalData += length;
        }
        socket.close();
        return baos.toByteArray();
    }
}

//System.out.println("hostname:" + hostname);
//System.out.println("port:" + port);