import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.*;
import java.io.*;

public class LevelCreator extends JPanel implements ActionListener, MouseListener, MouseMotionListener, ChangeListener{
	private JFrame frame = new JFrame();
	private Container canvas;
	private int x = 0;
	private int y = 0;
	private int dragX = 0;
	private int dragY = 0;
	private boolean dragging = false;
	private ArrayList<Ledge> level = new ArrayList<Ledge>();
	private JPanel pane = new JPanel();
	private JSlider red = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
	private JSlider blue = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
	private JSlider green = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
	private int redVal = 0, greenVal = 0, blueVal = 0;
	private JButton saveBut = new JButton("Save");

	public LevelCreator() {
		// pane.setLayout(new BorderLayout());
		Hashtable redH = new Hashtable();
		redH.put(0, new JLabel("0"));
		redH.put(255, new JLabel("RED"));
		red.setLabelTable(redH);
		red.setPaintLabels(true);
		pane.add(red);
		Hashtable blueH = new Hashtable();
		blueH.put(0, new JLabel("0"));
		blueH.put(255, new JLabel("BLUE"));
		blue.setLabelTable(blueH);
		blue.setPaintLabels(true);
		pane.add(blue);
		Hashtable greenH = new Hashtable();
		greenH.put(0, new JLabel("0"));
		greenH.put(255, new JLabel("GREEN"));
		green.setLabelTable(greenH);
		green.setPaintLabels(true);
		pane.add(green);
		red.addChangeListener(this);
		blue.addChangeListener(this);
		green.addChangeListener(this);
		saveBut.addActionListener(this);

		canvas = frame.getContentPane();
		canvas.add(pane, BorderLayout.SOUTH);
		canvas.add(this, BorderLayout.CENTER);
		canvas.add(saveBut, BorderLayout.NORTH);
		setPreferredSize(new Dimension(1200, 400));
		setBackground(Color.WHITE);
		addMouseListener(this);
		addMouseMotionListener(this);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);

	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.fillRect(0, 300, 1200, 100);
		// g.setColor(Color.BLACK);
		for (int i = 0; i < 1200; i+= 10) {
			g.drawLine(i, 0, i, 300);
		}
		for (int i = 0; i < 300; i += 10) {
			g.drawLine(0, i, 1200, i);
		}
		
		for (int i = 0; i < level.size(); i++) {
			level.get(i).draw(g);
		}
		g.setColor(new Color(redVal, greenVal, blueVal));
		if (dragging) {
			if (x > dragX) {
				if (y > dragY) {
					g.fillRect(dragX, dragY, x - dragX, y - dragY);
					g.setColor(Color.BLACK);
					g.drawRect(dragX, dragY, x - dragX, y - dragY);
				}
				else {
					g.fillRect(dragX, y, x - dragX, dragY - y);
					g.setColor(Color.BLACK);
					g.drawRect(dragX, y, x - dragX, dragY - y);
				}
			}
			else {
				if (y > dragY) {
					g.fillRect(x, dragY, dragX - x, y - dragY);
					g.setColor(Color.BLACK);
					g.drawRect(x, dragY, dragX - x, y - dragY);
				}
				else {
					g.fillRect(x, y, dragX - x, dragY - y);
					g.setColor(Color.BLACK);
					g.drawRect(x, y, dragX - x, dragY - y);
				}
			}
		}
		g.setColor(new Color(redVal, greenVal, blueVal));
		g.fillOval(10, 310, 80, 80);
		// frame.repaint();
	}
	public void actionPerformed(ActionEvent a){
		Object input = a.getSource();
		if (input.equals(saveBut)) {
			String things;
			boolean cont = true;
			boolean contin = true;
			while(cont) {
				cont = false;
				things = (String)JOptionPane.showInputDialog("Enter the name of the save file");
				if (things != null) {
					things += ".txt";
					save(things);
				}					
			}
		}
	}
	public void mouseClicked(MouseEvent m){
		x = m.getX();
		y = m.getY();
		dragX = x;
		dragY = y;
		//System.out.println("Hello");
	}
	public void mousePressed(MouseEvent m){}
	public void mouseReleased(MouseEvent m){
		int rX = (int)(Math.floor(x / 10.0) * 10);
		int rY = (int)(Math.floor(y / 10.0) * 10);
		int rBX = (int)(Math.ceil(dragX / 10.0) * 10);
		int rBY = (int)(Math.ceil(dragY / 10.0) * 10);
		// System.out.println(rX + " " + rY);
		if (rX > rBX) {
			int temp = rX;
			rX = rBX;
			rBX = temp;
		}
		if (rY > rBY) {
			int temp = rY;
			rY = rBY;
			rBY = temp;
		}

		level.add(new Ledge(rX, rY, rBX - rX, rBY - rY, redVal, greenVal, blueVal));
		// System.out.println(level.size());
		x = 0;
		y = 0;
		dragX = 0;
		dragY = 0;
		//System.out.println(dragging);
		dragging = false;
		frame.repaint();
	}
	public void mouseDragged(MouseEvent m){
		if (!dragging) {
			x = m.getX();
			y = m.getY();
		}
		dragX = m.getX();
		dragY = m.getY();
		//System.out.println(dragX + " " + dragY);
		dragging = true;
		frame.repaint();
	}
	public void mouseMoved(MouseEvent m){}
	public void mouseEntered(MouseEvent m) {}
	public void mouseExited(MouseEvent m) {}
	public void stateChanged(ChangeEvent c) {
		Object input = c.getSource();
		if (input.equals(red)) {
			redVal = red.getValue();
		}
		else if (input.equals(blue)) {
			blueVal = blue.getValue();
		}
		else if (input.equals(green)) {
			greenVal = green.getValue();
		}
		frame.repaint();
	}

	private void save(String path) {
		BufferedWriter writer = null;
		try {
			File logFile = new File(path);
			writer = new BufferedWriter(new FileWriter(logFile));
			writer.write(level.size() + "\n");
			for (int i = 0; i < level.size(); i++) {
				writer.write(level.get(i).toString());
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				writer.close();
			}
			catch (Exception e) {}
		}
	}

	public static void main(String[] args) {
		new LevelCreator();
	}
}