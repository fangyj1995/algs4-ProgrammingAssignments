import edu.princeton.cs.algs4.Picture;

import java.awt.*;
import java.util.*;

/**
 * Created by fangyj on 2017/2/4.
 */
public class SeamCarver {
    private int width;
    private int height;
    private int [][] colors;
    private boolean transposed = false;

    private class Stp{

        int[] path;
        double [][] distTo ;
        int [][] pixelTo ;

        public Stp(double [][] energys){
            distTo = new double[height][width];
            pixelTo = new int[height][width];
            int pixelToLast = 0;
            double distTolast = Double.POSITIVE_INFINITY;

            for(int y = 0 ;y < height ; y++){
                for(int x = 0 ; x <width ; x++){
                    distTo[y][x] = Double.POSITIVE_INFINITY;
                }
            }

            //topological sort stp algorithm
            for(int c = 0 ; c < width ; c ++) {
                distTo[0][c] = energys[0][c];//1000
                pixelTo[0][c] = -1;
            }

            for(Integer id : topOrder()){
                int c = id % width;//col
                int r = id / width;//row
                //bottom pixel
                if(r == height - 1) {
                    if(distTolast > distTo[r][c]){
                        distTolast = distTo[r][c];
                        pixelToLast = c;
                    }
                }
                else {//3 downward edge
                    relax(c, r, c, r + 1, energys);
                    if (c > 0) relax(c, r, c - 1, r + 1, energys);
                    if (c < width - 1) relax(c, r, c + 1, r + 1, energys);
                }
            }

            path = new int[height];
            path[height- 1] = pixelToLast;

            int pid = pixelTo[height - 1][pixelToLast];
            while(pid != -1){
                int c = pid % width;//col
                int r = pid / width;//row
                path[r] = c;
                pid  = pixelTo[r][c];
            }

        }
        private Iterable<Integer> topOrder(){
            LinkedList<Integer> topOrder = new LinkedList<>() ;
            boolean [][] marked = new boolean[height][width];
            for(int x = 0 ;x < width ; x++){
                for(int y = 0 ; y < height ; y++){
                    if(!marked[y][x])
                        dfs(x , y,topOrder,marked);
                }
            }
            return topOrder;
        }

        private void dfs(int x ,int y,LinkedList<Integer> topOrder,boolean [][] marked){
            marked[y][x] = true;
            if(y == height - 1) {
                topOrder.push(id(x,y));
                return;
            }

            if(!marked[y + 1][x]) dfs(x , y+1,topOrder,marked);
            if(x > 0 && !marked[y + 1][x - 1]) dfs(x - 1 ,y + 1,topOrder,marked);
            if(x < width - 1 && !marked[y + 1][x + 1]) dfs(x + 1 , y + 1,topOrder,marked);

            topOrder.push(id(x,y));
        }

        private int id(int x , int y){
            return y * width + x;
        }

        private void relax(int fromx,int fromy ,int tox, int toy,double [][] energys){

            if(distTo[toy][tox] > distTo[fromy][fromx] + energys[toy][tox]){
                distTo[toy][tox] = distTo[fromy][fromx] + energys[toy][tox];
                pixelTo[toy][tox] = id(fromx,fromy);
            }
        }

        private int[] minPathToBottom(){
            return path;
        }
    }
    public SeamCarver(Picture picture){
        if(picture == null) throw new NullPointerException();
        width = picture.width();
        height = picture.height();
        colors = new int[height][width];

        for(int x = 0 ;x < width ; x++){
            for(int y = 0 ; y < height ; y++){
                colors[y][x] = picture.get(x,y).getRGB();
            }
        }

    }                // create a seam carver object based on the given picture
    public Picture picture(){
        if(transposed) transpose();
        Picture picture = new Picture(width , height);
        for(int x = 0 ;x < width ; x++){
            for(int y = 0 ; y <height ; y++){
                picture.set(x,y,new Color(colors[y][x]));
            }
        }
        return picture;
    }                     // current picture
    public int width() {
        if(!transposed) return width;
        return height;
    }                           // width of current picture
    public int height(){
        if(!transposed) return height;
        return width;
    }                           // height of current picture
    public double energy(int x, int y) {
        if(transposed){
            int t = x;
            x = y;
            y = t;
        }
        return calEnergy(x,y);
    }              // energy of pixel at column x and row y
    public int[] findHorizontalSeam(){
        if(!transposed) transpose();
        return  findSeam();
    }               // sequence of indices for horizontal seam
    public int[] findVerticalSeam() {
        if(transposed) transpose();
        return  findSeam();
    }            // sequence of indices for vertical seam
    public void removeHorizontalSeam(int[] seam) {
        if(!transposed) transpose();
        removeSeam(seam);
    }  // remove horizontal seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if(transposed) transpose();
        removeSeam(seam);
    }    // remove vertical seam from current picture


    private int[] findSeam(){
        //energy matrix
        double [][] energys = new double[height][width];
        for(int y = 0 ; y <height ; y++){
            for(int x = 0 ;x < width ; x++){
                energys[y][x] = calEnergy(x,y);
            }
        }

        //find seam
        Stp stp = new Stp(energys);
        return stp.minPathToBottom();
    }

    private void removeSeam(int[] seam){
        if(seam == null) throw  new NullPointerException();
        if(width <= 1 || seam.length != height) throw new IllegalArgumentException();
        for(int i = 0 ;i < seam.length ; i++){
            if(seam[i] < 0 || seam[i] > width - 1) throw new IllegalArgumentException();
            if((i > 0 && Math.abs(seam[i] - seam[i - 1] )> 1 ) || (i < seam.length - 1 && Math.abs(seam[i] - seam[i + 1])> 1))
                throw new IllegalArgumentException();
        }
        for(int y = 0 ; y < height ; y++){
            int split = seam[y];
            for(int x = split; x < width - 1 ;x++){
                colors[y][x] = colors[y][x+1];
            }
        }

        width --;
    }

    private double calEnergy(int x , int y){
        if(x < 0 || x > width - 1 || y < 0 || y > height - 1) throw new IndexOutOfBoundsException();
        if(x == width - 1 || x ==0 || y == 0 || y == height - 1) return 1000;

        Color top = new Color(colors[y - 1][x]);
        Color left = new Color(colors[y][x - 1]);
        Color right = new Color(colors[y][x + 1]);
        Color bottom = new Color(colors[y + 1][x]);

        int rx = left.getRed() - right.getRed() , ry = top.getRed() - bottom.getRed();
        int gx = left.getGreen() - right.getGreen() , gy = top.getGreen() - bottom.getGreen();
        int bx = left.getBlue() - right.getBlue() , by = top.getBlue() - bottom.getBlue();

        double deltaX = rx * rx + gx * gx + bx * bx;
        double deltaY = ry * ry + gy * gy + by * by;

        double energy = Math.sqrt(deltaX + deltaY);

        return energy;
    }

    private void transpose(){
        int temp = height;
        height = width;
        width = temp;

        int [][] colors = new int[height][width];

        for(int y = 0 ; y < height ; y++){
            for(int x = 0 ;x < width ; x++){
                colors[y][x] = this.colors[x][y];
            }
        }

        this.colors = colors;
        transposed = !transposed;
    }
}

