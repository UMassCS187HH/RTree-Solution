
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
			if (current.getChildren()[0] == null){
				current.Insert(leaf);
				return;
			}
			for (TreeNode child : current.getChildren()) {
				if (child != null && !child.hasMin()){
					current = (TreeBranch) current;
					break;
				}
			}
		}

		while (!current.isLeaf()) {
			double minAreaDifference = 1;
			TreeNode next = null;
			for (TreeNode child : current.getChildren()) {
				if (child == null){
					continue;
				}
				if ( child.isLeaf()) {
					current.Insert(leaf);
					if (head.getParent() != null)
						head = head.getParent();
					return;
				}
				if (((TreeBranch) child).getBoundingRectWithAdditionalNode(leaf).getArea()
						- ((TreeBranch) child).getBoundingRect().getArea() < minAreaDifference) {
					minAreaDifference = ((TreeBranch) child).getBoundingRectWithAdditionalNode(leaf).getArea()
							- ((TreeBranch) child).getBoundingRect().getArea();
					next = child;
				}
			}
			current = (TreeBranch) next;
		}
	}

	public TreeBranch SearchChildren(TreeBranch branch, TreeNode query) {
		TreeBranch answer = null;
		for(TreeNode node : branch.getChildren()){
			if (node == query) {
				answer = branch;
				break;
			}
			if (node == null || node.isLeaf())
				continue;
			TreeBranch childQuery = SearchChildren((TreeBranch)node, query);
			if (childQuery != null){
				answer = childQuery;
				break;
			}
		}
		return answer;
	}
}
