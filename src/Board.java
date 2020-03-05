import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Board extends JPanel implements ActionListener {

    Game game;
    Timer timer;
    ArrayList<Sprite> actors;
    int padding = 25, growthValue = 2;
    int playerInvulTime = 2000;
    int clockStopTime = 1500;
    boolean stopped = false;

    long nextMoment, stopMoment;

    public Board(Game game){
        this.game = game;
        setPreferredSize(new Dimension(600, 700));
        setBackground(Color.BLACK);
    }

    public void init(){
        actors = new ArrayList<>();

        actors.add(new Player(Color.WHITE, getWidth()/2, getHeight()/2, 25, 25, this, game));

        for (int i = 0; i < STATS.getNumEnemies(); i++){
            actors.add(new Enemy(Color.RED, (int)(Math.random()*(getWidth()-padding)+padding), (int)(Math.random()*(getHeight()-padding)+padding), 20, 20, this));
        }

        for (int i = 0; i < STATS.getNumFood(); i++){
            actors.add(new Food(Color.ORANGE, (int)(Math.random()*(getWidth()-padding)+padding), (int)(Math.random()*(getHeight()-padding)+padding), 20, 20, this, true, System.currentTimeMillis()));
        }

        actors.add(new Barrier(Color.YELLOW, (int)(Math.random()*(getWidth()-padding)+padding), (int)(Math.random()*(getHeight()-padding)+padding), 15, 30, this));


        timer = new Timer(1000/60, this);
        timer.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        if (STATS.isMENU()) {
            //Paint the Menu
            g.setColor(Color.LIGHT_GRAY);
            g.setFont(new Font("Comic Sans", Font.PLAIN, 32));
            printSimpleString("FortShape", getWidth(), 0, (getHeight()/2)-padding, g);
            g.setFont(new Font("Comic Sans", Font.ITALIC, 20));
            printSimpleString("Click to eventually lose", getWidth(), 0, (getHeight()/2)+padding, g);
        }

        if (STATS.isPLAY()){
            for (Sprite actor: actors) { actor.paint(g); }
        }

        if (STATS.isWIN()){
            g.setColor(Color.LIGHT_GRAY);
            g.setFont(new Font("Comic Sans", Font.PLAIN, 20));
            printSimpleString("Oh wow you won, and my game didn't die.", getWidth(), 0, (getHeight()/2)-padding, g);
        }

        if (STATS.isFAIL()){
            g.setColor(Color.LIGHT_GRAY);
            g.setFont(new Font("Comic Sans", Font.PLAIN, 20));
            printSimpleString("okay that makes sense. Git Gud.", getWidth(), 0, (getHeight()/2)-padding, g);
        }
    }

    private void printSimpleString(String s, int width, int xPos, int yPos, Graphics g){
        int stringLen = (int)g.getFontMetrics().getStringBounds(s, g).getWidth();
        int start = width/2 - stringLen/2;
        g.drawString(s, start + xPos, yPos);
    }

    public void checkCollisions(){
        int[] playerTemp = {actors.get(0).getHeight(), actors.get(0).getWidth()};

        for (int i = 1; i < actors.size(); i++){
            if (actors.get(0) instanceof Player && actors.get(0).collidesWith(actors.get(i))){
                if (actors.get(i) instanceof Enemy && !actors.get(0).isInvincible()){
                    actors.get(0).setRemove(true);
                    STATS.setPLAY(false);
                    STATS.setFAIL(true);
                }
                if (actors.get(i) instanceof Barrier && !actors.get(0).isInvincible()) {
                    nextMoment = System.currentTimeMillis();
                    actors.get(0).setInvincible(true);
                    actors.get(i).setRemove(true);
                }
                if (actors.get(i) instanceof Clock && !stopped){
                    stopMoment = System.currentTimeMillis();
                    stopped = true;
                    actors.get(i).setRemove(true);
                }
                if (actors.get(i) instanceof Food && !actors.get(i).isInvincible()) {
                    actors.get(0).setHeight(playerTemp[0] + growthValue);
                    actors.get(0).setWidth(playerTemp[1] + growthValue);
                    actors.get(i).setRemove(true);
                }
            }
        }

        for (int i = actors.size()-1; i >= 0; i--){
            if (actors.get(i).isRemove()){
                if (actors.get(i) instanceof Food && actors.get(i).isDivisible()){
                    int[] foodTemp = {actors.get(i).getX(), actors.get(i).getY(), actors.get(i).getWidth()/2, actors.get(i).getHeight()/2};
                    for (int j = STATS.getFoodSplit(); j > 0; j--){
                        actors.add(new Food(Color.ORANGE, foodTemp[0], foodTemp[1], foodTemp[2], foodTemp[3], this, false, System.currentTimeMillis()));
                    }
                }
                actors.remove(i);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (STATS.isMENU() && game.isMouseClicked()){
            game.setMouseClicked(false);
            nextMoment = System.currentTimeMillis();
            STATS.setMENU(false);
            STATS.setPLAY(true);
        }

        if (STATS.isPLAY() && actors.get(0).isInvincible() && (System.currentTimeMillis() - nextMoment) >= playerInvulTime){
            for (Sprite actor: actors) { actor.setInvincible(false); }
            actors.add(new Clock(Color.WHITE, (int)(Math.random()*(getWidth()-padding)+padding), (int)(Math.random()*(getHeight()-padding)+padding), 15, 30, this));
        }

        if (STATS.isPLAY() && stopped && (System.currentTimeMillis() - stopMoment) >= clockStopTime){
            stopped = false;
        }

        if (STATS.isPLAY()){
            checkCollisions();

            actors.get(0).move();

            for (Sprite actor: actors) {
                if (actor instanceof Food && System.currentTimeMillis() - actor.getSpawnTime() >= playerInvulTime){
                    actor.setInvincible(false);
                }
                if (!stopped)
                    actor.move();
            }

            if (actors.get(0) instanceof Player){
                if (actors.get(0).isInvincible())
                    actors.get(0).setColor(Color.YELLOW);
                else if (stopped)
                    actors.get(0).setColor(Color.WHITE);
                else
                    actors.get(0).setColor(Color.BLUE);
            }
        }

        if (actors.size() <= STATS.getNumEnemies()+1){
            STATS.setPLAY(false);
            STATS.setWIN(true);
        }

        repaint();

    }
}
