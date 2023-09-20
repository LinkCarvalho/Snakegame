import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    static final int WIDTH = 800;
    static final int HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (WIDTH * HEIGHT)/UNIT_SIZE;
    static final int DELAY = 75;

    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts = 4;
    int applesEaten;
    int applex;
    int appley;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;


    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        start();
    }

    public void start(){
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        if(running){
            g.setColor(Color.red);
            g.fillOval(applex, appley, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < bodyParts; i++) {
                if (i == 0){
                    g.setColor(Color.GREEN);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE );
                }else{
                    g.setColor(new Color(45,180,0));
                    //g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE );
                }
            }
            g.setColor(Color.red);
            g.setFont(new Font("Comic sans", Font.BOLD, 42));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Pontos: " +applesEaten, (WIDTH - metrics.stringWidth("Pontos: "))/2, g.getFont().getSize());

        }else{
            gameOver(g);
        }
    }

    public void newApple(){
        applex = random.nextInt((int)(WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appley = random.nextInt((int)(HEIGHT/UNIT_SIZE))*UNIT_SIZE;

    }

    public void movement(){
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        switch (direction){
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }

    }

    public void checkApple(){
        if((x[0] == applex) && (y[0] == appley)){
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }

    public void checkCollisions(){
        for (int i = bodyParts; i > 0 ; i--) {//Corpo
            if((x[0]) == x[i] && (y[0]) == y[i]){
                running = false;
            }
        }
        if(x[0] < 0){
            running = false;
        }
        if(x[0] > WIDTH){
            running = false;
        }
        if(y[0] < 0){
            running = false;
        }
        if(y[0] > HEIGHT){
            running = false;
        }

        if (!running){
            timer.stop();
        }


    }

    public void gameOver(Graphics g){
        g.setColor(Color.red);
        g.setFont(new Font("Comic sans", Font.BOLD, 42));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Pontos: " +applesEaten, (WIDTH - metrics1.stringWidth("Pontos: "))/2, g.getFont().getSize());

        g.setColor(Color.red);
        g.setFont(new Font("Comic sans", Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("PERDESTE", (WIDTH - metrics2.stringWidth("PERDESTE"))/2, HEIGHT/2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            movement();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if(direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }


        }
    }
}
