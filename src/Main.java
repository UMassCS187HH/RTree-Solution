import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class Main {

	public static void main(String[] a) {
		JFrame window = new JFrame();
		window.setBackground(Color.white);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setBounds(0, 0, Constants.WINDOW_WIDTH + 50, Constants.WINDOW_HEIGHT+50);
		window.getContentPane().add(new RMap(Constants.generateLocations(23, 1000)));
		window.setVisible(true);
	}
}

class RMap extends JComponent {
	private static final long serialVersionUID = 1L;
	private TreeNode[] locations;
	
	public RMap(TreeNode[] locations) {
		super();
		this.locations = locations;
	}
	
	public void paintComponent(Graphics g) {

		for(TreeNode Node : locations){
			Point location = ((TreeLeaf)Node).getPoint();
			g.setColor(Color.blue);
			g.drawOval((int)(location.getX()*Constants.WINDOW_WIDTH),
					(int)(location.getY()*Constants.WINDOW_HEIGHT),6,6);
		}
	}
}
