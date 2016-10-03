import javax.swing.plaf.ColorUIResource;

public class TreeBranch implements TreeNode{
	private TreeNode[] children;
	private int current;
	
	public TreeBranch(TreeNode[] children) {
		this.children = children;
		current = -1;
	}
	public TreeBranch() {
		children = new TreeNode[Constants.NODE_CHILDREN_MAX];
	}
	public Rect getBoundingRect(){
		//TODO generate BoundingRect of Node
		double minX=1,minY=1,maxX=0,maxY=0;
		for(TreeNode node :  children) {
			if (node.isLeaf()){
				Point location = ((TreeLeaf) node).getPoint();
				minX = Math.min(location.getX(), minX);
				minY = Math.min(location.getY(), minY);
				maxX = Math.max(location.getX(), maxX);
				maxY = Math.max(location.getY(), maxY);
			} else {
				Rect location = ((TreeBranch) node).getBoundingRect();
				minX = Math.min(location.getX(), minX);
				minY = Math.min(location.getY(), minY);
				maxX = Math.max(location.getX() + location.getWidth(), maxX);
				maxY = Math.max(location.getY() + location.getHeight(), maxY);
			}
		}
		return new Rect(minX,minY,maxX-minX,maxY-minY);
	}
	public Rect getBoundingRectWithAdditionalNode(TreeLeaf leaf){
		//TODO generate BoundingRect of Node 
		Rect CurrentBoundingRect = this.getBoundingRect();
		Point newPoint = leaf.getPoint();
		double x = CurrentBoundingRect.getX() < newPoint.getX()?CurrentBoundingRect.getX():newPoint.getX();
		double y = CurrentBoundingRect.getY() < newPoint.getY()?CurrentBoundingRect.getY():newPoint.getY();
		double width = CurrentBoundingRect.getX() + CurrentBoundingRect.getWidth() < newPoint.getX()?CurrentBoundingRect.getWidth():newPoint.getX()-x;
		double height = CurrentBoundingRect.getY() + CurrentBoundingRect.getHeight() < newPoint.getY()?CurrentBoundingRect.getHeight():newPoint.getY()-y;
		return new Rect(x,y,width,height);
	}
	public void Insert(TreeNode node){
		//TODO
		current++;
		children[current] = node;
	}
	public boolean isEmpty(){
		//TODO
		return current == -1;
	}
	@Override
	public boolean isFull(){
		for(TreeNode node : children){
			if (node == null || !node.isFull())
				return false;
		}
		return true;
	}
	public boolean hasMin(){
		for(TreeNode node : children){
			if (node != null && !node.hasMin())
				return false;
		}
		return current >= Constants.NODE_CHILDREN_MIN;
	}
	public boolean allChildrenPresent(){
		return current == Constants.NODE_CHILDREN_MAX-1;
	}
	public TreeNode[] getChildren(){
		return children;
	}
	@Override
	public boolean isLeaf() {
		return false;
	}
	@Override
	public boolean contains(TreeNode that) {
		Rect rect = getBoundingRect();
		if (that.isLeaf()){
			Point thatBound = ((TreeLeaf)that).getPoint();
			return thatBound.getX() >= rect.getX() &&
				thatBound.getY() >= rect.getY() &&
				thatBound.getX() <= rect.getX() + rect.getWidth() &&
				thatBound.getY() <= rect.getY() + rect.getHeight();
		} else {
			Rect thatBound = ((TreeBranch)that).getBoundingRect();
			return thatBound.getX() >= rect.getX() &&
				thatBound.getY() >= rect.getY() &&
				thatBound.getWidth() <= rect.getWidth() &&
				thatBound.getHeight() <= rect.getHeight();
		}
	}
	public void replaceChild(TreeNode oldNode, TreeNode newNode) {
		for(int i = 0; i <= current; i++) {
			if (children[i] == oldNode) {
				children[i] = newNode;
			}
		}
	}
	@Override
	public Point getPoint() {
		Rect bound = getBoundingRect();
		return new Point(bound.getX() + bound.getWidth() / 2, bound.getY() + bound.getWidth() / 2);
	}
}
