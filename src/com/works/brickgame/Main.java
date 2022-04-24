package com.works.brickgame;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.xml.transform.ErrorListener;

import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.awt.*;
import java.util.*;
import static javax.swing.JOptionPane.*;

class Draw{

}
class Anime extends JPanel implements KeyListener{
    JFrame f;
    int x;
    int bx,by,score = 0,boundx,boundy,brickcount=12;
    Point p1;
    Rectangle base;
    Rectangle r1,r2,r3,r4,r5,r6,r7,r8,r9,r10,r11,r12;
    //JLabel l;
    Image ball,brick,baseimg,bg;
    AudioInputStream audioIn;
    Clip clip;



    boolean left=false,right=false,xlim=false,ylim=false,start = false;
    boolean isin(Rectangle r){
        if(r.contains(p1)){
            return true;
        }
        else
            return false;

    }
    void setBlocks(){
        r1 = new Rectangle(10,10,100,20);
        r2 = new Rectangle(120,10,100,20);
        r3 = new Rectangle(230,10,100,20);
        r4 = new Rectangle(340,10,100,20);
        r5 = new Rectangle(10,50,100,20);
        r6 = new Rectangle(120,50,100,20);
        r7 = new Rectangle(230,50,100,20);
        r8 = new Rectangle(340,50,100,20);
        r9 = new Rectangle(10,90,100,20);
        r10 = new Rectangle(120,90,100,20);
        r11= new Rectangle(230,90,100,20);
        r12= new Rectangle(340,90,100,20);
    }
    void start(Anime a){
        try {
            ball = ImageIO.read(new File("src/com/works/brickgame/ball1.png"));
            brick = ImageIO.read(new File("src/com/works/brickgame/brick.jpg"));
            baseimg = ImageIO.read(new File("src/com/works/brickgame/base.png"));
            bg = ImageIO.read(new File("src/com/works/brickgame/bg.jpg"));

        } catch (IOException e) {

            e.printStackTrace();
        }
        setBlocks();



        p1 = new Point(bx,by);

        f = new JFrame("BrickGame");


        f.setSize(460,600);
        boundx=f.getWidth();
        boundy = f.getHeight();
        bx = (boundx/2)-50;
        by = (boundy/2)-50;
        x = boundx/2-100;

        f.setVisible(true);
        f.addKeyListener(this);
        repaint();
        f.add(a);
        //f.setLayout(null);
        base = new Rectangle(x,f.getHeight()-100,100,20);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setResizable(false);
        f.setBackground(Color.BLACK);

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int a = e.getKeyCode();
        if(a==37)
            left = true;

        else if(a==39)
            right = true;
        if(a==32)
            start = !start;
        repaint();

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        int a = e.getKeyCode();
        if(a==37)
            left = false;
        else if(a==39)
            right = false;
        if(a==32)

            repaint();
    }
    void drawBlock(Graphics g,Rectangle r){
        Point p2 = new Point(p1.x,p1.y-20);
        if(r.contains(p2)&&start==true){
            try {
                audioIn = AudioSystem.getAudioInputStream(new File("src/com/works/brickgame/impactsound.wav"));
                clip = AudioSystem.getClip();
                clip.open(audioIn);

            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }

            clip.start();
            ylim = !ylim;
            score+=10;

            r.setSize(0,0);
            brickcount--;
        }
        if(r.isEmpty()==false)
            g.drawImage(brick,r.x,r.y,r.width,r.height,null);
        //repaint();
    }
    void buildBlock(Graphics g){
        drawBlock(g, r1);
        drawBlock(g, r2);
        drawBlock(g, r3);
        drawBlock(g, r4);
        drawBlock(g, r5);
        drawBlock(g, r6);
        drawBlock(g, r7);
        drawBlock(g, r8);
        drawBlock(g, r9);
        drawBlock(g, r10);
        drawBlock(g, r11);
        drawBlock(g, r12);

    }

    @Override
    public void paintComponent(Graphics g){

        //System.out.println(r1.x+" "+r1.y+" "+r1.height+" "+r1.width);
        boundx=f.getWidth();
        boundy = f.getHeight();
         try {
            Thread.sleep(3);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        super.paintComponent(g);
        g.drawImage(bg,0, 0, boundx, boundy,null);
        buildBlock(g);

        g.setColor(Color.blue);


        p1.setLocation(bx+10, by+20);
        //g.drawIma
        //System.out.println(p1.x+" "+p1.y+"\n"+base.x +" "+base.y);
        g.setColor(Color.white);
        g.drawImage(baseimg,x, boundy-100, 100, 20,null);
        g.drawString("Score : "+score,boundx/2-100,boundy);
        g.setColor(Color.red);
        g.drawImage(ball,bx, by, 50, 50,null);
        if(start){
            if(base.contains(p1)) {
                score--;
                if(p1.x>=x+60&&xlim==true)
                    xlim=false;
                else if(p1.x<=x+40&&xlim==false)
                    xlim = true;
            }

            if(base.contains(p1)||by==0){
                clip.start();
                ylim = !ylim;
                try {
                    audioIn = AudioSystem.getAudioInputStream(new File("src/com/works/brickgame/impactsound.wav"));
                    clip = AudioSystem.getClip();
                    clip.open(audioIn);

                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                    e.printStackTrace();
                }

                clip.start();
            }
            if(bx==0||bx==boundx-50){
                xlim =  !xlim;
                try {
                    audioIn = AudioSystem.getAudioInputStream(new File("src/com/works/brickgame/impactsound.wav"));
                    clip = AudioSystem.getClip();
                    clip.open(audioIn);

                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                    e.printStackTrace();
                }

                clip.start();
            }
            if(xlim){
                bx--;
            }
            else
                bx++;
            if(ylim)
                by+=2;
            else
                by-=2;
            if(left){
                // repaint();

                x--;
                base.setBounds(x,boundy-100,100,20);
                if(x<0)
                    x=0;
            }
            if(right){
                //repaint();
                x++;
                base.setBounds(x,boundy-100,100,20);
                if(x>boundx-100)
                    x=boundx-100;
            }

            if(by>boundy){
                try {
                    audioIn = AudioSystem.getAudioInputStream(new File("src/com/works/brickgame/wasted.wav"));
                    clip = AudioSystem.getClip();
                    clip.open(audioIn);

                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                    e.printStackTrace();
                }

                clip.start();

                showMessageDialog(null, "Game Over\nScore: "+score, "Game Over",JOptionPane.ERROR_MESSAGE);
                bx=boundx/2-50;by=boundy/2-50;
                x=boundx/2;
                brickcount = 12;
                start = false;

                score = 0;
                ylim = false;
                setBlocks();
                repaint();



            }

            if(brickcount==0){
                bx=boundx/2-50;by=boundy/2-50;
                x=boundx/2;
                try {
                    audioIn = AudioSystem.getAudioInputStream(new File("src/com/works/brickgame/Passed.wav"));
                    clip = AudioSystem.getClip();
                    clip.open(audioIn);

                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                    e.printStackTrace();
                }

                clip.start();
                showMessageDialog(null,"Victory!!\nScore :"+score,"Congratulations", INFORMATION_MESSAGE);
                brickcount = 12;

                start = false;

                score = 0;
                ylim = false;

                setBlocks();
                repaint();
            }

            repaint();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Anime a = new Anime();
        a.start(a);
    }
}

