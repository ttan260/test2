package demo1;

/**
 * ���������ߺ�������ģʽ��ģ������ͷ�ͳ���ͷ�Ĺ���
 * Ҫ����3�������ߣ�2�������ߣ����50����ͷ�����������ѵĹ���
 *
 * ����
 */
public class Test {
    public static void main(String[] args) {
        SteamedBuns steamedBuns=new SteamedBuns();
        Thread producer1=new Producer("һ��������",steamedBuns);
        Thread producer2=new Producer("����������",steamedBuns);
        Thread producer3=new Producer("����������",steamedBuns);
        Thread consumer1=new Consumer("һ�Ź˿�",steamedBuns);
        Thread consumer2=new Consumer("���Ź˿�",steamedBuns);
        producer1.start();
        producer2.start();
        producer3.start();
        consumer1.start();
        consumer2.start();
    }
}
