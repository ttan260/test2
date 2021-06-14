package demo1;

/**
 * ��������
 */
public class Producer extends Thread{
    private String name;
    //��ͷ����
    private SteamedBuns steamedBuns;

    public Producer(String name,SteamedBuns steamedBuns) {
        this.steamedBuns = steamedBuns;
        this.name=name;
    }

    @Override
    public void run() {
        while (steamedBuns.getMnum()<50){
            synchronized (steamedBuns){
                //�ж���ͷ�Ƿ���
                if(steamedBuns.getSurplus()>0){

                    try {
                        //���������ͷ
                        System.out.println("����"+steamedBuns.getSurplus()+"����ͷ");
                        steamedBuns.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

                if(steamedBuns.getMnum()<50) {
//                    steamedBuns.setInstock(true);
                    steamedBuns.addSurplus();
                    steamedBuns.setMnum(steamedBuns.getMnum() + 1);
                    System.out.println("��"+name + "����������" + steamedBuns.getMnum() + "����ͷ");
                    System.out.println("�ܹ��Ѿ�����" + steamedBuns.getMnum() + "����ͷ");
                    steamedBuns.notifyAll();
                }else {
                    //����50����ͷ�����ڻ��ѽ�����
                    System.out.println("�ܹ��Ѿ�����50����ͷ���������ˣ�");
                }

            }
        }
    }
}
