import java.awt.*;

public class Food extends Sprite {

    public Food(Color color, int x, int y, int width, int height, Board board, boolean divisible, long spawnTime) {
        super(color, x, y, width, height, board);
        this.divisible = divisible;
        this.spawnTime = spawnTime;
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(getColor());
        g.fillRect(getX(), getY(), getWidth(), getHeight());
    }

}

