import java.awt.*;

public class Clock extends Sprite {

    public Clock(Color color, int x, int y, int width, int height, Board board) {
        super(color, x, y, width, height, board);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(getColor());
        g.fillOval(getX(), getY(), getWidth(), getHeight());
    }
}

