package game;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;

public class MyGameFrame extends Frame{
    Image planeImg=GameUtil.getImage("../images/plane.png");
    Image bg=GameUtil.getImage("../images/bg.jpg");
    Plane plane=new Plane(planeImg,250,250);
    Shell[] shells=new Shell[50];

    Expload bao;
    Date startTime=new Date();
    Date endTime;
    int period;

    @Override
    public void paint(Graphics g){
        Color oldColor=g.getColor();
        g.drawImage(bg,0,0,null);
        plane.drawSelf(g);
        for(int i=0;i<shells.length;i++){
            shells[i].draw(g);

            boolean peng=shells[i].getRect().intersects(plane.getRect());
            if(peng){
                plane.live=false;
                if(bao==null){
                    bao=new Expload(plane.x,plane.y);
                    endTime=new Date();
                    period=(int)((endTime.getTime()-startTime.getTime())/1000);
                }
                bao.draw(g);
            }
            if(!plane.live){
                g.setColor(Color.red);
                Font f=new Font("宋体",Font.BOLD,50);
                g.setFont(f);
                g.drawString("总时长:"+period+"秒",100,100);
                plane.x=-10;//让飞机消失
                plane.y=-10;
            }
        }

        g.setColor(oldColor);

    }

    class PaintThread extends Thread{
        @Override
        public void run(){
            while(true){
                repaint();
                try {
                    Thread.sleep(40);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class KeyMonitor extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            plane.addDirection(e);
        }
        @Override
        public void keyReleased(KeyEvent e){
            plane.minusDirection(e);
        }
    }


    public void launchFrame(){
        this.setTitle("AGame");
        this.setVisible(true);
        this.setSize(Constant.GAME_WIDTH,Constant.GAME_HEIGHT);
        this.setLocation(300,300);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        new PaintThread().start();
        addKeyListener(new KeyMonitor());

        for(int i=0;i<shells.length;i++){
            shells[i]=new Shell();
        }

    }

    public static void main(String[] args) {
        MyGameFrame f=new MyGameFrame();
        f.launchFrame();
    }

    private Image offScreenImage=null;
    public void update(Graphics g){
        if(offScreenImage==null)
            offScreenImage=this.createImage(Constant.GAME_WIDTH,Constant.GAME_HEIGHT);
        Graphics gOff=offScreenImage.getGraphics();
        paint(gOff);
        g.drawImage(offScreenImage,0,0,null);
    }
}
