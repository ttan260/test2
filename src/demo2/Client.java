package demo2;

import java.io.*;
import java.net.Socket;

/**
 * �ͻ�����
 */
public class Client extends Thread {
    private String name;

    public Client(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name+Thread.currentThread().getName()+"�ͻ�������...");
            Socket socket = new Socket("127.0.0.1", 6666);
            InputStream is = socket.getInputStream();
//            OutputStream os=socket.getOutputStream();
            File file = new File("src\\download");
            if (!file.exists()) {
                file.mkdirs();
            }
//            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//            bufferedWriter.write(this.name+"��������");

            FileOutputStream fos = new FileOutputStream(file + "\\" + this.name + Thread.currentThread().getName() + System.currentTimeMillis() + "1.jpeg");
            //�������ݣ�д�ڱ���
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = is.read(bytes)) != -1) {
                fos.write(bytes, 0, len);
            }
            System.out.println(this.name + Thread.currentThread().getName() + "���سɹ���");
//            os.write((this.name+"��������").getBytes());
            // 5. ����
            fos.close();
            is.close();
//            os.close();
            socket.close();
//            bufferedWriter.flush();
//            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
