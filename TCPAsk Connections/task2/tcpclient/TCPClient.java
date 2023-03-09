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
        this.socket = new Socket(hostname,port);
        if(this.timeout!=null) this.socket.setSoTimeout(this.timeout);
        if(this.limit!=null) this.socket.setReceiveBufferSize(this.limit);
        byte [] dataFromServer = new byte[1000];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //Input stream and output stream
        InputStream iStream = this.socket.getInputStream(); // We use this to receive stuff from the server
        OutputStream oStream = this.socket.getOutputStream(); // We use this to send stuff to the server
        oStream.write(toServerBytes);
        if(this.shutdown == true) this.socket.shutdownOutput();
        int  totalData = 0;
        int length;
        while(this.socket.isConnected()){
            try{
                if(limit != null && totalData == limit){
                    break;
                }
                length = iStream.read(dataFromServer);
                if(length != -1) baos.write(dataFromServer,0,length);
                else break;
            }catch (Exception e){
                break;
            }
            totalData += length;
        }
        this.socket.close();
        return baos.toByteArray();
    }
}
