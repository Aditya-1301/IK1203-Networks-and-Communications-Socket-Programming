import tcpclient.TCPClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class MyRunnable extends Thread{
    Socket client;
    public static String status200 = "HTTP/1.1 200 OK\r\n\r\n";
    public static String status400 = "HTTP/1.1 400 Bad Request\r\n\r\n";
    public static String status404 = "HTTP/1.1 404 Not Found\r\n\r\n";
    public MyRunnable(Socket client){
        this.client = client;
    }

    @Override
    public void run() {
        try {
            try {
                String httpResponse = hRequest(client);
                OutputStream outputStream = client.getOutputStream();
                outputStream.write(httpResponse.getBytes("UTF-8"));
                outputStream.close();
            }catch (IOException e){
                String httpResponse = status404;
                System.out.println("\r\n\r\n" + httpResponse);
                OutputStream outputStream = client.getOutputStream();
                outputStream.write(httpResponse.getBytes("UTF-8"));
                outputStream.close();
            }
            catch (Exception e){
                String httpResponse = status400;
                System.out.println("\r\n\r\n" + httpResponse);
                OutputStream outputStream = client.getOutputStream();
                outputStream.write(httpResponse.getBytes("UTF-8"));
                outputStream.close();
            }
        }catch (Exception e){
            System.out.println(e);
            System.exit(1);
        }
    }

    public static String hRequest(Socket client) throws Exception {
        InputStream input = client.getInputStream();
        byte[] buffer = new byte[1024];
        int length = input.read(buffer);
        String webRequest =  new String(buffer, 0, length);
        System.out.println(webRequest);

        String[] rLines = webRequest.split("\r\n");
        String[] rLine = rLines[0].split(" ");

        String host = null;
        byte [] string = new byte[0];
        Integer port = null;
        Integer timeout = null, limit = null;
        boolean shutdown = false;

        String response = "";

        if(!rLine[1].startsWith("/ask")){
            throw new IOException();
        }
        if(rLine.length == 3 &&  rLine[0].equals("GET") && rLine[1].startsWith("/ask") && rLine[2].startsWith("HTTP/1.1")){
            String[] webQuery = rLine[1].split("[?&]");
            for (String wQ : webQuery) {
                String[] values = wQ.split("=");
                if (values[0].equals("hostname")) {
                    host = values[1];
                } else if (values[0].equals("port")) {
                    port = Integer.parseInt(values[1]);
                } else if (values[0].equals("string")) {
                    string = values[1].getBytes();
                } else if (values[0].equals("shutdown")) {
                    shutdown = true;
                } else if (values[0].equals("timeout")) {
                    timeout = Integer.parseInt(values[1]);
                } else if (values[0].equals("limit")) {
                    limit = Integer.parseInt(values[1]);
                }
            }
            if(host!=null && port != null && (port > 0 && port < 65536)){
                TCPClient tcpClient = new TCPClient(shutdown, timeout, limit);
                byte[] serverBytes  = tcpClient.askServer(host, port, string);
                response = status200 + new String(serverBytes);
                System.out.println(response);
            }
            else if(host == null || port == null || port < 0 || port > 65535){
                System.out.println(status400);
                response = status400;
            }
        }
        else{
            throw new Exception();
        }
        return response;
    }
}
