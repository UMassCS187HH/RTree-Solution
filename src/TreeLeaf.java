
public class TreeLeaf implements TreeNode {
	private Point point;
	
	public TreeLeaf(Point point) {
		this.point = point;
	}
	public Point getPoint(){
		return point;
	}
	@Override
	public boolean isLeaf() {
		return true;
	}
	public double distance(TreeLeaf that){
		//TODO
		return Math.sqrt(Math.pow(point.getX() - that.getPoint().getX(),2) + 
				Math.pow(point.getY() - that.getPoint().getY(),2));
	}
	@Override
	public boolean isFull() {
		return true;
	}
	@Override
	public boolean hasMin() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean contains(TreeNode that) {
		// TODO Auto-generated method stub
		return false;
	}
}
