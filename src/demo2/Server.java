package demo2;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务器端类
 */
public class Server {

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(6666);
        System.out.println("服务器启动...");
        while (true) {
            Socket socket = server.accept();
            // 使用多线程
            // 每有一个客户端连接服务器，就开启一个线程，进行文件的传输
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(socket.getInetAddress().getHostAddress()+Thread.currentThread().getName()+"接入服务器");
                    try {
                        // 读取的文件数据源
                        FileInputStream fis = new FileInputStream("wallhaven-9mo66w.jpg");
                        OutputStream os = socket.getOutputStream();
//                        InputStream is=socket.getInputStream();
//                        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                        System.out.println(bufferedReader.readLine());
//                        bufferedReader.close();
                        int len = 0;
                        byte[] bytes = new byte[1024];
                        while ((len = fis.read(bytes)) != -1) {
                            os.write(bytes, 0, len);
                        }
////                        //读取"上传成功"的字节读取流
//                        int num=is.read(bytes);
//                        System.out.println(new String(bytes,0,num));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }finally {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
            // 服务端一直开着不关
            // serverSocket.close();
        }
    }
}
