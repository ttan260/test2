package demo2;

public class Test {
    public static void main(String[] args) {
        //�����ĸ��ͻ������ؽ���
        Thread t1=new Client("����1");
        Thread t2=new Client("����2");
        Thread t3=new Client("����3");
        Thread t4=new Client("����4");
        t1.start();
        t2.start();
        t3.start();
        t4.start();

    }
}
