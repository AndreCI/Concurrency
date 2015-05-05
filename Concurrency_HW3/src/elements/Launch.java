package elements;

public class Launch {

	public static void main(String[] args) {
		Node x1 = new Node(6);
		Tree T = new Tree(x1);
		T.insert(4);
		T.insert(8);
		T.insert(3);
		T.insert(5);
		T.insert(10);
		T.insert(1);
		T.insert(9);
		T.insert(11);


		System.out.println();
		T.delete(8);
		
		T.toPrintf(3);
	}

}
