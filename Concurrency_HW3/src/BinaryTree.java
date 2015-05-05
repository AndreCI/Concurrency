import elements.*;


public class BinaryTree{
	public void main(){
		Node x1 = new Node(6);
		Tree T = new Tree(x1);
		T.insert(5);
		T.insert(7);
		T.toPrintf(x1);
	}
	
}
