package demo2;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * ����������
 */
public class Server {

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(6666);
        System.out.println("����������...");
        while (true) {
            Socket socket = server.accept();
            // ʹ�ö��߳�
            // ÿ��һ���ͻ������ӷ��������Ϳ���һ���̣߳������ļ��Ĵ���
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(socket.getInetAddress().getHostAddress()+Thread.currentThread().getName()+"���������");
                    try {
                        // ��ȡ���ļ�����Դ
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
////                        //��ȡ"�ϴ��ɹ�"���ֽڶ�ȡ��
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
            // �����һֱ���Ų���
            // serverSocket.close();
        }
    }
}
