package demo2;

import java.io.*;
import java.net.Socket;

/**
 * 客户端类
 */
public class Client extends Thread {
    private String name;

    public Client(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name+Thread.currentThread().getName()+"客户端启动...");
            Socket socket = new Socket("127.0.0.1", 6666);
            InputStream is = socket.getInputStream();
//            OutputStream os=socket.getOutputStream();
            File file = new File("src\\download");
            if (!file.exists()) {
                file.mkdirs();
            }
//            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//            bufferedWriter.write(this.name+"请求下载");

            FileOutputStream fos = new FileOutputStream(file + "\\" + this.name + Thread.currentThread().getName() + System.currentTimeMillis() + "1.jpeg");
            //接受数据，写在本地
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = is.read(bytes)) != -1) {
                fos.write(bytes, 0, len);
            }
            System.out.println(this.name + Thread.currentThread().getName() + "下载成功！");
//            os.write((this.name+"申请下载").getBytes());
            // 5. 关流
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
