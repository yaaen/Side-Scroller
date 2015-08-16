import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;


public class Side extends JPanel implements KeyListener, ActionListener {
	private JFrame frame = new JFrame();
	private Container canvas;
	private JPanel pane = new JPanel();
	private User user = new User();
	private javax.swing.Timer timer = new javax.swing.Timer(20, this);
	private String[] levels = {"level1"};
	private ArrayList<Ledge> level = new ArrayList<Ledge>();
	private int orgX = 0;
	private boolean left, right;

	public Side() {
		canvas = frame.getContentPane();
		pane.setLayout(new BorderLayout());
		canvas.add(this, BorderLayout.CENTER);
		setBackground(Color.BLUE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(1200, 400));
		load("level1");
		System.out.println("HELLO");
		timer.start();
		frame.addKeyListener(this);
		frame.pack();
		frame.setVisible(true);
		
	}

	public void updateOrgin() {
		for (int i = 0; i < level.size(); i++) {
			level.get(i).updateOrg(orgX);
		}
		frame.repaint();
	}
	public void keyPressed(KeyEvent k) {
		int key = k.getKeyCode();
		if (key == KeyEvent.VK_UP) {
			user.jump();
		}
		else if (key == KeyEvent.VK_RIGHT) {
			right = true;
		}
		else if (key == KeyEvent.VK_LEFT) {
			left = true;
		}
	}
	public void keyReleased(KeyEvent k) {
		int key = k.getKeyCode();
		if (key == KeyEvent.VK_RIGHT) {
			right = false;
		}
		else if (key == KeyEvent.VK_LEFT) {
			left = false;
		}
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
			if (right) {
				orgX -= 8;
				updateOrgin();
			}
			else if (left) {
				orgX += 8;
				updateOrgin();
			}
			frame.repaint();
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.GREEN);
		g.fillRect(0, 300, 1200, 100);
		user.draw(g);
		for (int i = 0; i < level.size(); i++) {
			level.get(i).draw(g);
		}
		
	}
	private void load(String path) {
		try {
			Scanner in = new Scanner(new FileReader(path + ".txt"));
			int num = Integer.parseInt(in.nextLine());
			for (int i = 0; i < num; i++) {
				level.add(new Ledge(Integer.parseInt(in.nextLine()),Integer.parseInt(in.nextLine()),
					Integer.parseInt(in.nextLine()),Integer.parseInt(in.nextLine()),Integer.parseInt(in.nextLine()),
					Integer.parseInt(in.nextLine()),Integer.parseInt(in.nextLine())));
			}
		}
		catch(Exception e) {
			System.out.println("HGHG");
		}
	}



	public static void main(String[] args) {
		new Side();
	}
}