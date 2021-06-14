package demo1;

/**
 * ��������
 */
public class Consumer extends Thread{
    private String name;
    //��ͷ����
    private SteamedBuns steamedBuns;
    //�Ե���ͷ������
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
                    //û��ʣ����ͷʱ
                    try {
                        System.out.println(this.name + ": û��ͷ�ˣ�������");
                        steamedBuns.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    if (steamedBuns.getEnum() < 50) {
                        System.out.println("[" + this.name + "]�����ڳ���ͷ...");
                        steamedBuns.redSurplus();
                        steamedBuns.setEnum(steamedBuns.getEnum() + 1);
                        num++;
                        System.out.println(name + "�Ѿ�����" + num + "����ͷ");
                        System.out.println("ȫ���˿��ܹ��Ѿ�����" + steamedBuns.getEnum() + "����ͷ");
                        steamedBuns.notifyAll();
                    } else {
                        //����50����ͷ�����ڻ��ѽ�����
                        System.out.println("�ܹ��Ѿ�����50����ͷ�ˣ����ٳ��ˣ�");
                    }

                }
            }
        }
    }


}
