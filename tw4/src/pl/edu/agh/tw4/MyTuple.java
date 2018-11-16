package pl.edu.agh.tw4;

public class MyTuple {
    private int x;
    private int y;
    private int iter;

    public MyTuple(int x, int y, int iter) {
        this.x = x;
        this.y = y;
        this.iter = iter;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getIter() {
        return iter;
    }
}
