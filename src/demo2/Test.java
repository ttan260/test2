package demo2;

public class Test {
    public static void main(String[] args) {
        //创建四个客户端下载进程
        Thread t1=new Client("下载1");
        Thread t2=new Client("下载2");
        Thread t3=new Client("下载3");
        Thread t4=new Client("下载4");
        t1.start();
        t2.start();
        t3.start();
        t4.start();

    }
}
