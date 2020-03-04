import java.awt.*;

public abstract class Sprite {

    Color color;
    int x, y, width, height;
    double dx, dy;
    boolean remove = false, invincible = true, divisible = true;

    long spawnTime;
    Board board;

    public Sprite(Color color, int x, int y, int width, int height, Board board){

        this.color = color;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.board = board;

        while ((int)dx == 0 || (int)dy == 0){
            double angle = 2 * Math.PI * (Math.random()+1);
            double speed = (STATS.getRangeSpeed()*Math.random() + STATS.getLowSpeed());
            dx = Math.cos(angle)*speed;
            dy = Math.sin(angle)*speed;
        }
    }

    public Rectangle getBounds(){
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    public boolean collidesWith(Sprite other){
        return getBounds().intersects(other.getBounds());
    }

    public abstract void paint(Graphics g);

    public void move(){

        //Predictive movement
        double nextLeft = x + dx;
        double nextRight = x+width + dx;
        double nextTop = y + dy;
        double nextBottom = y+height + dy;

        if (nextTop < 0 || nextBottom > (double)board.getHeight()){
            dy *= -1;
        }
        if (nextLeft < 0 || nextRight > (double)board.getWidth()){
            dx *= -1;
        }

        x += dx;
        y += dy;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isRemove() {
        return remove;
    }

    public void setRemove(boolean remove) {
        this.remove = remove;
    }

    public boolean isInvincible() {
        return invincible;
    }

    public void setInvincible(boolean invincible) {
        this.invincible = invincible;
    }

    public long getSpawnTime(){
        return spawnTime;
    }

    public void setSpawnTime(long spawnTime){
        this.spawnTime = spawnTime;
    }

    public boolean isDivisible() {
        return divisible;
    }

    public void setDivisible(boolean divisible){
        this.divisible = divisible;
    }
}
