package pl.edu.agh.tw4;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

public class MandelbrotCallable implements Callable<List<List<MyTuple>>> {

    private static final int MAX_ITER = 5000;
    private static final double ZOOM = 250;
    private int width;
    private int start_y;
    private int end_y;
    private List<List<MyTuple>> list;

    public MandelbrotCallable(int width, int start_y, int end_y) {
        this.width = width;
        this.start_y = start_y;
        this.end_y = end_y;
        this.list = new LinkedList<>();
    }

    @Override
    public List<List<MyTuple>> call() throws Exception {
        double zx, zy, cX, cY, tmp;
        for (int y = start_y; y < end_y; y++) {
            List<MyTuple> tmpList = new LinkedList<>();
            for (int x = 0; x < width; x++) {
                zx = zy = 0;
                cX = (x - 400) / ZOOM;
                cY = (y - 300) / ZOOM;
                int iter = MAX_ITER;
                while (Double.compare(zx * zx + zy * zy, 4) < 0 && iter > 0) {
                    tmp = zx * zx - zy * zy + cX;
                    zy = 2.0 * zx * zy + cY;
                    zx = tmp;
                    iter--;
                }
                tmpList.add(new MyTuple(x, y, iter));

            }
            list.add(tmpList);
        }
        return list;
    }

}
