package demo1;

/**
 * ��ͷ��
 */
public class SteamedBuns {
    //û����ͷ
    private boolean Instock=false;
    //����������ͷ������
    private int Mnum=0;
    //���Ե���ͷ������
    private int Enum=0;
    //��ͷʣ������
    private int surplus=0;


    public SteamedBuns(){}

    public SteamedBuns(boolean instock) {
        this.Instock = instock;
    }

    public boolean isInstock() {
        return this.Instock;
    }

    public void setInstock(boolean instock) {
        this.Instock = instock;
    }

    public int getMnum() {
        return Mnum;
    }

    public void setMnum(int mnum) {
        Mnum = mnum;
    }

    public int getEnum() {
        return Enum;
    }

    public void setEnum(int anEnum) {
        Enum = anEnum;
    }

    public int getSurplus() {
        return surplus;
    }

    public void setSurplus(int surplus) {
        this.surplus = surplus;
    }
    public void addSurplus() {
        this.surplus=this.surplus+1;
    }
    public void redSurplus() {
        this.surplus=this.surplus-1;
    }
}
