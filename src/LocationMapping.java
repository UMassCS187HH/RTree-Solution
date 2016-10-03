
public class LocationMapping implements LocationMappings {

	public TreeBranch head;

	public LocationMapping(TreeLeaf[] leaves) {
		// TODO
		head = new TreeBranch();
		for (TreeLeaf leaf : leaves) {
			AddLeaf(leaf);
		}
	}

	@Override
	public void AddLeaf(TreeLeaf leaf) {
		// TODO Auto-generated method stub
		TreeBranch current = head;
		while (!current.hasMin()) {
			for (TreeNode child : current.getChildren()) {
				if (child == null) {
					current.Insert(leaf);
					return;
				}
				if (!((TreeBranch) child).hasMin()) {
					current = (TreeBranch) child;
					break;
				}
			}
		}

		while (!current.isLeaf()) {
			if (current.isFull()) {
				if (ParentOf(current) == null) {
					head = SplitNode(current);
				} else {
					ParentOf(current).replaceChild(current, SplitNode(current));
				}
			}
			double minAreaDifference = 1;
			TreeNode next = null;
			for (TreeNode child : current.getChildren()) {
				if (child == null || !child.isLeaf())
					continue;
				if (((TreeBranch) child).getBoundingRectWithAdditionalNode(leaf).getArea()
						- ((TreeBranch) child).getBoundingRect().getArea() < minAreaDifference) {
					minAreaDifference = ((TreeBranch) child).getBoundingRectWithAdditionalNode(leaf).getArea()
							- ((TreeBranch) child).getBoundingRect().getArea();
					next = child;
				}
			}
			if (next.isLeaf()){
				break;
			}
			current = (TreeBranch) next;
		}
		current.Insert(leaf);
		return;
	}

	@Override
	// Given a parent branch add new top node
	// Use the center of the rectangles to sort through rectangles
	public TreeBranch SplitNode(TreeBranch branch) {
		// TODO Auto-generated method stub
		TreeBranch newNodeL = new TreeBranch();
		TreeBranch newNodeR = new TreeBranch();
		double maxDiff = 0;
		TreeNode farthest1 = null, farthest2 = null;
		for (TreeNode node1 : branch.getChildren()) {

			for (TreeNode node2 : branch.getChildren()) {
				if (maxDiff < node1.getPoint().distance(node2.getPoint())){
					farthest1 = node1;
					farthest2 = node2;
				}
			}
		}
		
		for (TreeNode node : branch.getChildren()) {
			if (farthest1.getPoint().distance(node.getPoint()) < farthest2.getPoint().distance(node.getPoint())){
				newNodeL.Insert(node);
			} else {
				newNodeR.Insert(node);
			}
		}
		TreeBranch newTop = new TreeBranch();
		newTop.Insert(newNodeR);
		newTop.Insert(newNodeL);
		return newTop;
	}

	public TreeBranch ParentOf(TreeNode node) {
		// TODO
		// This better be referenced
		TreeNode current = head;
		while (true) {// playing with fire, will fix. Edge cases exist
			boolean passThrough = false;
			for (TreeNode child : ((TreeBranch) current).getChildren()) {
				if (child != null && child.contains(node)) {
					passThrough = true;
					current = child;
					break;
				}
			}
			if (passThrough) {
				continue;
			}
			return (TreeBranch) current;
		}
	}



}
