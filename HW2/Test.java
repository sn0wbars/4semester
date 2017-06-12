import java.util.concurrent.ThreadLocalRandom;

public class Test
{
	private static void level(TreeNode<Integer> node, int k)
	{
		int numOfNodes = ThreadLocalRandom.current().nextInt(0, 4);

		for (int i = 0; i < numOfNodes; ++i)
		{
			TreeNode<Integer> child = node.add(ThreadLocalRandom.current().nextInt(0, 1000));	
			if (k > 0)
				level(child, k - 1);
		}
	}

	public static void main(String[] args) 
	{
		TreeNode<Integer> tree = new TreeNode<>(17,5);

		level(tree, 7);

		TreeNode<Integer> node = tree.findMaxLel();
		System.out.println(node.getdata() + "\n");

		tree.print();
	}
}