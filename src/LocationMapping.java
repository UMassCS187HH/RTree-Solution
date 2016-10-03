
public class LocationMapping implements LocationMappings {

	private TreeBranch head;
	
	public LocationMapping(TreeLeaf[] leaves){
		//TODO
		head = new TreeBranch();
		for(TreeLeaf leaf : leaves){
			AddLeaf(leaf);
		}
	}
	@Override
	public void AddLeaf(TreeLeaf leaf) {
		// TODO Auto-generated method stub
		TreeBranch current = head;
		while (!current.hasMin()){
			for (TreeNode child : current.getChildren()){
				if (child == null){
					current.Insert(leaf);
					return;
				} 
				if (!((TreeBranch)child).hasMin()){
					current = (TreeBranch)child;
					break;
				}
			}
		}

		while (!current.isFull()){
			if (current.getChildren()[0].isLeaf()){
				current.Insert(leaf);
				return;
			}
			double minAreaDifference = 1;
			TreeNode next = null;
			for (TreeNode child : current.getChildren()){
				if (((TreeBranch)child).getBoundingRectWithAdditionalNode(leaf).getArea() - ((TreeBranch)child).getBoundingRect().getArea() < minAreaDifference){
					minAreaDifference = ((TreeBranch)child).getBoundingRectWithAdditionalNode(leaf).getArea() - ((TreeBranch) child).getBoundingRect().getArea(); 
					next = child;
				} 
			}
			current = (TreeBranch)next;
		}
		SplitNode(leaf,current);

	}

	@Override
	//Given a parent branch add new top node
	public void SplitNode(TreeLeaf leaf, TreeBranch branch) {
		// TODO Auto-generated method stub
		if (branch != null && ParentOf(branch).isFull()){
			SplitNode(leaf,ParentOf(branch));
			return;
		}
	}

	@Override
	public TreeLeaf Closest(TreeLeaf leaf) {
		// TODO Auto-generated method stub
		return null;
	}
	public TreeBranch ParentOf(TreeNode node) {
		//TODO 
		//This better be referenced
		TreeNode current = head;
		while (true) {//playing with fire, will fix. Edge cases exist
			boolean passThrough = false;
			for(TreeNode child : ((TreeBranch)current).getChildren()){
				if (child != null && child.contains(node)){
					passThrough = true;
					current = child;
					break;
				}
			}
			if (passThrough){
				continue;
			}
			return (TreeBranch)current;
		}
	}

}
