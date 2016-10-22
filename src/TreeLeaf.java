
public class TreeLeaf implements TreeNode {
	private Point point;
	private TreeBranch parent;
	
	public TreeLeaf(Point point,TreeBranch parent) {
		this.parent = parent;
		this.point = point;
	}
	public TreeLeaf(Point point) {
		this(point,null);
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
		return false;
	}
	@Override
	public TreeBranch getParent() {
		return parent;
	}
	@Override
	public void setParent(TreeBranch parent) {
		this.parent = parent;
		
	}
}
