import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class Main {

	public static void main(String[] a) {
		JFrame window = new JFrame();
		window.setBackground(Color.white);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setBounds(0, 0, Constants.WINDOW_WIDTH + 50, Constants.WINDOW_HEIGHT + 50);
		LocationMapping lmap = new LocationMapping(Constants.generateLocations(23, 400));
		window.getContentPane().add(new RMap(lmap));
		window.setVisible(true);
	}
}

class RMap extends JComponent {
	private static final long serialVersionUID = 1L;
	private LocationMapping locations;

	public RMap(LocationMapping locations) {
		super();
		this.locations = locations;
	}

	public void paintComponent(Graphics g) {

		DrawNode(g, locations.head);
	}

	public void DrawNode(Graphics g, TreeBranch node) {
		for (TreeNode child : node.getChildren()) {
			if (child == null)
				continue;
			if (child.isLeaf()) {
				Point location = ((TreeLeaf) child).getPoint();
				g.setColor(Color.blue);
				g.drawOval((int) (location.getX() * Constants.WINDOW_WIDTH),
						(int) (location.getY() * Constants.WINDOW_HEIGHT), 6, 6);

			} else {
				Rect location = ((TreeBranch) child).getBoundingRect();
				g.setColor(Color.red);
				g.drawRect((int) (location.getX() * Constants.WINDOW_WIDTH),
						(int) (location.getY() * Constants.WINDOW_HEIGHT), 
						(int)((location.getWidth()) * Constants.WINDOW_WIDTH+7), 
						(int)((location.getHeight()) * Constants.WINDOW_HEIGHT+7));

				DrawNode(g,(TreeBranch) child);
			}
		}
	}
}
