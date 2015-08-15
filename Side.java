import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;


public class Side extends JPanel implements KeyListener, ActionListener {
	private JFrame frame = new JFrame();
	private Container canvas;
	private JPanel pane = new JPanel();
	private Ledge[] ledges = new Ledge()[5];
	private User user = new User();
	private javax.swing.Timer timer = new javax.swing.Timer(40, this);

	public Side() {
		canvas = frame.getContentPane();
		pane.setLayout(new BorderLayout());
		canvas.add(this, BorderLayout.CENTER);
		setBackground(Color.BLUE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(1200, 400));
		timer.start();
		frame.addKeyListener(this);
		frame.pack();
		frame.setVisible(true);

	}

	public void keyPressed(KeyEvent k) {
		int key = k.getKeyCode();
		if (key == KeyEvent.VK_UP) {
			user.jump();
		}
	}
	public void keyReleased(KeyEvent k) {

	}
	public void keyTyped(KeyEvent k) {

	}

	public void actionPerformed(ActionEvent a) {
		Object thing = a.getSource();
		if (thing.equals(timer)) {
			if (user.isJumping()) {	
				if (user.getY() < 270) {
					user.update();
				}
				else {
					user.update(270);
				}
				//System.out.println(user.getY());
			}
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.GREEN);
		g.fillRect(0, 300, 1200, 100);
		user.draw(g);
		frame.repaint();
	}

	public static void main(String[] args) {
		new Side();
	}
}