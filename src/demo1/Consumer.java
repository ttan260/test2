package demo1;

/**
 * 消费者类
 */
public class Consumer extends Thread{
    private String name;
    //馒头对象
    private SteamedBuns steamedBuns;
    //吃的馒头的数量
    private int num=0;
    public Consumer(String name,SteamedBuns steamedBuns) {
        this.steamedBuns = steamedBuns;
        this.name=name;
    }
    @Override
    public void run() {
        while (steamedBuns.getEnum()<50){
            synchronized (steamedBuns) {
                if (steamedBuns.getSurplus() == 0) {
                    //没有剩余馒头时
                    try {
                        System.out.println(this.name + ": 没馒头了，快做！");
                        steamedBuns.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    if (steamedBuns.getEnum() < 50) {
                        System.out.println("[" + this.name + "]：正在吃馒头...");
                        steamedBuns.redSurplus();
                        steamedBuns.setEnum(steamedBuns.getEnum() + 1);
                        num++;
                        System.out.println(name + "已经吃了" + num + "个馒头");
                        System.out.println("全部顾客总共已经吃了" + steamedBuns.getEnum() + "个馒头");
                        steamedBuns.notifyAll();
                    } else {
                        //吃了50个馒头，不在唤醒进程了
                        System.out.println("总共已经吃了50个馒头了，不再吃了！");
                    }

                }
            }
        }
    }


}
