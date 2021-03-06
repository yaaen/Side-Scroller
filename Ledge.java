import java.awt.*;
import javax.swing.*;

public class Ledge {
	private int x, y, width, height, red, green, blue;
	private Color color;
	private int orgX = 0;

	public Ledge(int iX, int iY, int iWidth, int iHeight, int r, int g, int b) {
		x = iX;
		y = iY;
		width = iWidth;
		height = iHeight;
		red = r;
		green = g;
		blue = b;
		color = new Color(red, green, blue);
	}
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillRect(x + orgX, y, width, height);
		g.setColor(Color.BLACK);
		g.drawRect(x + orgX, y, width, height);
		// System.out.println(x + " " + y + " " + width + " " + height);
	}
	public void updateOrg(int newOrgin) {
		orgX = newOrgin;
	}
	public String toString() {
		return x + "\n" + y + "\n" + width + "\n" + height + "\n" + red + "\n" + green + "\n" + blue + "\n";
	}
}