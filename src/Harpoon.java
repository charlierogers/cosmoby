import screen.Dim;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Harpoon {
    
    private String pointDownFilename = "images/harpoonDown.png";
    private String pointLeftFilename = "images/harpoonLeft.png";
    private String pointRightFilename = "images/harpoonRight.png";
    private ArrayList<Image> directionalImages;
    private double shotFPS;
    private int x;
    private int y;
    private int direction;
    private int winWidth;
    private int winHeight;
    Dim dim;
    
    Harpoon(int winWidth, int winHeight) {
        dim = new Dim(winWidth, winHeight, 1000, 700);
        directionalImages = getDirectionalImages();
        this.winWidth = winWidth;
        this.winHeight = winHeight;
        firstPosition();
        shotFPS = dim.adjWDouble(4);
    }

    private ArrayList<Image> getDirectionalImages() {
        ArrayList<Image> images = new ArrayList<>();
        images.add(new ImageIcon(pointLeftFilename).getImage());
        images.add(new ImageIcon(pointDownFilename).getImage());
        images.add(new ImageIcon(pointRightFilename).getImage());
        return images;
    }

    public int getRandomX() {
        int randX = (int) Math.floor(Math.random() * (winWidth - getImage().getWidth(null)) + dim.adjW(60));
        return randX;
    }
    
    public int getRandomY() {
        int randY = (int) Math.floor(Math.random() * winHeight + 1);
        return randY;
    }
    
    public Image getImage() {
        return directionalImages.get(direction + 1);
    }
    
    public Rectangle makeRectangle() {
        int rectX = x;
        int rectY = (int) Math.round(y + 0.8 * getImage().getHeight(null));
        int rectWidth = directionalImages.get(1).getWidth(null);
        int rectHeight = (int) Math.round(0.2 * getImage().getHeight(null));
        if (direction == 1)
            rectX += (int) Math.round(0.8 * getImage().getWidth(null));
        return new Rectangle(rectX, rectY, rectWidth, rectHeight);
    }

    private int getRandomDirection() {
        return new Random().nextInt(3) - 1;
    }
    
    public void putAtTop() {
        direction = getRandomDirection();
        x = getRandomX();
        y = dim.adjH(25) - getImage().getHeight(null);
    }
    
    public void firstPosition() {
        direction = getRandomDirection();
        x = getRandomX();
        y = getRandomY();
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public void move() {
        if (y > winHeight) {
            putAtTop();
        }
        y += shotFPS;
        x += direction * shotFPS;
    }
}