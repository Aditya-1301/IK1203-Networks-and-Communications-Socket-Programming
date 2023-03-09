package tcpclient;
import java.net.*;
import java.io.*;

public class TCPClient {
    public TCPClient() {

    }

    public byte[] askServer(String hostname, int port, byte [] toServerBytes) throws IOException {
        Socket socket = new Socket(hostname,port);
        byte [] dataFromServer = new byte[1000];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //Input stream and output stream
        InputStream iStream = socket.getInputStream(); // We use this to receive stuff from the server
        OutputStream oStream = socket.getOutputStream(); // We use this to send stuff to the server
        oStream.write(toServerBytes);
        while(true){
            int length = iStream.read(dataFromServer);
            if(length != -1) baos.write(dataFromServer,0,length);
            else break;
        }
        socket.close();
        return baos.toByteArray();
    }

    public byte[] askServer(String hostname, int port) throws IOException {
        Socket socket = new Socket(hostname,port);
        byte [] dataFromServer = new byte[1000];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //Input stream
        InputStream iStream = socket.getInputStream(); // We use this to receive stuff from the server
        while(true){
            int length = iStream.read(dataFromServer);
            if(length != -1) baos.write(dataFromServer,0,length);
            else break;
        }
        socket.close();
        return baos.toByteArray();
    }
}
