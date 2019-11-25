package game;

import java.awt.*;

public class Expload {
    double x,y;
    int count;

    public Expload(double x,double y){
        this.x=x;
        this.y=y;
    }

    static Image[] imgs=new Image[16];
    static {
        for(int i=0;i<16;i++){
            imgs[i]=GameUtil.getImage("../images/explode/e"+(i+1)+".gif");
            imgs[i].getWidth(null);
        }
    }

    public void draw(Graphics g){
        if(count<=15){
            g.drawImage(imgs[count],(int)x,(int)y,null);
            count++;
        }
    }

}
