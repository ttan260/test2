package demo1;

/**
 * 利用生产者和消费者模式，模拟做馒头和吃馒头的过程
 * 要求：有3个生产者，2个消费者，完成50个馒头的制作和消费的过程
 *
 * 测试
 */
public class Test {
    public static void main(String[] args) {
        SteamedBuns steamedBuns=new SteamedBuns();
        Thread producer1=new Producer("一号生产线",steamedBuns);
        Thread producer2=new Producer("二号生产线",steamedBuns);
        Thread producer3=new Producer("三号生产线",steamedBuns);
        Thread consumer1=new Consumer("一号顾客",steamedBuns);
        Thread consumer2=new Consumer("二号顾客",steamedBuns);
        producer1.start();
        producer2.start();
        producer3.start();
        consumer1.start();
        consumer2.start();
    }
}
