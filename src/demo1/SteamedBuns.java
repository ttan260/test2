package demo1;

/**
 * 馒头类
 */
public class SteamedBuns {
    //没有馒头
    private boolean Instock=false;
    //被制作的馒头总数量
    private int Mnum=0;
    //被吃的馒头总数量
    private int Enum=0;
    //馒头剩余数量
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
