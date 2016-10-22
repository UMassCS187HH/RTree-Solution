

public class TreeBranch implements TreeNode{
	private TreeBranch parent;
	private TreeNode[] children;
	private int current;
	
	public TreeBranch(TreeNode[] children) {
		this(children,null);
	}
	public TreeBranch(TreeNode[] children,TreeBranch parent) {
		this.parent = parent;
		this.children = children;
		current = -1;

	}
	public TreeBranch() {
		this(new TreeNode[Constants.NODE_CHILDREN_MAX]);
	}
	public Rect getBoundingRect(){
		//TODO generate BoundingRect of Node
		double minX=1,minY=1,maxX=0,maxY=0;
		for(TreeNode node :  children) {
			if (node == null)
				continue;
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
	public TreeBranch Insert(TreeNode node){
		//TODO
		if (allChildrenPresent()) {
			return SplitNode(node);
		}
		current++;
		children[current] = node;
		node.setParent(this);
		return this;
	}
	public void Remove(TreeNode node){
		boolean hasRemoved = false;
		for(int i = 0; i < current; i ++){
			if (hasRemoved){
				children[i-1]=children[i];
			}
			if (children[i] == node){
				children[i] = null;
				hasRemoved = true;
			}
		}
		if (hasRemoved)
			current--;

	}
	private TreeBranch SplitNode(TreeNode node) {
		if (parent == null)
			parent = new TreeBranch();
		else
			parent.Remove(this);
		TreeBranch newNodeL = new TreeBranch();
		TreeBranch newNodeR = new TreeBranch();
		double maxDiff = 0;
		TreeNode farthest1 = null, farthest2 = null;
		for (TreeNode node1 : getChildren()) {
			if (node1 == null)
				continue;
			for (TreeNode node2 : getChildren()) {
				if (node2 == null)
					continue;
				if (maxDiff < node1.getPoint().distance(node2.getPoint())){
					farthest1 = node1;
					farthest2 = node2;
					maxDiff = node1.getPoint().distance(node2.getPoint());
				}
				if (maxDiff < node.getPoint().distance(node2.getPoint())){
					farthest1 = node;
					farthest2 = node2;
					maxDiff = node.getPoint().distance(node2.getPoint());
				}
				if (maxDiff < node1.getPoint().distance(node.getPoint())){
					farthest1 = node1;
					farthest2 = node;
					maxDiff = node.getPoint().distance(node1.getPoint());
				}
			}
		}
		
		for (TreeNode treeNode : getChildren()) {
			if (treeNode == null)
				continue;
			if ((farthest1.getPoint().distance(treeNode.getPoint()) < farthest2.getPoint().distance(treeNode.getPoint()) && !newNodeL.allChildrenPresent()) || newNodeR.allChildrenPresent()){
				newNodeL.Insert(treeNode);
			} else {
				newNodeR.Insert(treeNode);
			}
		}
		if ((farthest1.getPoint().distance(node.getPoint()) < farthest2.getPoint().distance(node.getPoint()) && !newNodeL.allChildrenPresent()) || newNodeR.allChildrenPresent()){
			newNodeL.Insert(node);
		} else {
			newNodeR.Insert(node);
		}
		parent = parent.Insert(newNodeR);
		parent = parent.Insert(newNodeL);
		return parent;
	}
	public boolean isEmpty(){
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
		return current >= Constants.NODE_CHILDREN_MIN-1;
	}
	public boolean allChildrenPresent(){
		return current >= Constants.NODE_CHILDREN_MAX-1;
	}
	public TreeNode[] getChildren(){
		return children;
	}
	public TreeBranch getParent(){
		return parent;
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
	@Override
	public void setParent(TreeBranch parent) {
		this.parent = parent;
		
	}
}
