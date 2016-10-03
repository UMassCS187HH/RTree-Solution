
public interface LocationMappings {

	public void AddLeaf(TreeLeaf leaf);
	public void SplitNode(TreeLeaf leaf, TreeBranch branch);
	public TreeLeaf Closest(TreeLeaf leaf);

}
