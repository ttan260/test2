package demo1;

/**
 * 生产者类
 */
public class Producer extends Thread{
    private String name;
    //馒头对象
    private SteamedBuns steamedBuns;

    public Producer(String name,SteamedBuns steamedBuns) {
        this.steamedBuns = steamedBuns;
        this.name=name;
    }

    @Override
    public void run() {
        while (steamedBuns.getMnum()<50){
            synchronized (steamedBuns){
                //判断馒头是否还有
                if(steamedBuns.getSurplus()>0){

                    try {
                        //如果还有馒头
                        System.out.println("还有"+steamedBuns.getSurplus()+"个馒头");
                        steamedBuns.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

                if(steamedBuns.getMnum()<50) {
//                    steamedBuns.setInstock(true);
                    steamedBuns.addSurplus();
                    steamedBuns.setMnum(steamedBuns.getMnum() + 1);
                    System.out.println("【"+name + "】：做出第" + steamedBuns.getMnum() + "个馒头");
                    System.out.println("总共已经做了" + steamedBuns.getMnum() + "个馒头");
                    steamedBuns.notifyAll();
                }else {
                    //做了50个馒头，不在唤醒进程了
                    System.out.println("总共已经做了50个馒头，不再做了！");
                }

            }
        }
    }
}
