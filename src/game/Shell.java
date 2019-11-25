package game;

import java.awt.*;

public class Shell extends GameObject{
    double degree;
    public Shell(){
        this.x=200;
        this.y=200;
        this.width=10;
        this.height=10;
        this.speed=3;
        this.degree=Math.random()*Math.PI*2;
    }
    public void draw(Graphics g){
        Color oldColor=g.getColor();
        g.setColor(Color.YELLOW);
        g.fillOval((int)x,(int)y,width,height);
        x+=speed*Math.cos(degree);
        y+=speed*Math.sin(degree);
        if(x<0||x>Constant.GAME_WIDTH-width)
            degree=Math.PI-degree;
        if(y<30||y>Constant.GAME_HEIGHT-height)
            degree=-degree;
        g.setColor(oldColor);
    }
}
