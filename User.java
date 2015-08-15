import javax.swing.*;
import java.awt.*;

public class User {
	private final int x = 400;
	private int y = 270;
	private int velocity = 0;
	private boolean jumping = false;
	public User() {

	}
	public void draw(Graphics g) {
		g.setColor (Color.RED);
		g.fillRect(x, y, 30, 30);
	}
	public int getY() {
		return y;
	}
	public void update(int set) {
		if (velocity < 0) {
			velocity = 0;
			y = set;
			jumping = false;
		}
		else {
			update();
		}
	}
	public void update() {
		y -= velocity;
		velocity -= 1;
	}
	public void jump() {
		if (!jumping) {
			velocity = 15;
			jumping = true;
		}

	}
	public boolean isJumping() {
		return jumping;
	}
}