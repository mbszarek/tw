package pl.edu.agh.tw4;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Mandelbrot extends JFrame {

    private BufferedImage I;
    private static final int threadAmount = 8;

    public Mandelbrot(int height, int width, int x, int y) throws HeadlessException {
        super("Mandelbrot Set");
        setBounds(x, y, width, height);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        I = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        int chunk = (int) Math.ceil((double) getHeight() / (threadAmount * 10));
        List<Future<List<List<MyTuple>>>> resultList = new LinkedList<>();
        int tmpHeight = getHeight();
        ExecutorService executorService = Executors.newFixedThreadPool(threadAmount);
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
        int i;
        for (i = 0; i + chunk < tmpHeight; i += chunk) {
            resultList.add(executorService.submit(new MandelbrotCallable(getWidth(), i, i + chunk)));
        }
        resultList.add(executorService.submit(new MandelbrotCallable(getWidth(), i, tmpHeight)));
        long time = System.currentTimeMillis();
        resultList.forEach(result -> {
            try {
                result.get().forEach(list -> list.forEach(element -> I.setRGB(element.getX(), element.getY(), element.getIter() | (element.getIter() << 8))));
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
        System.out.println(System.currentTimeMillis() - time);
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(I, 0, 0, this);
    }

    public static void main(String[] args) {
        new Mandelbrot(600,  800, 200, 200);
    }

}
