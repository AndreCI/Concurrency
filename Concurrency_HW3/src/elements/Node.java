package elements;

import java.util.concurrent.locks.ReentrantLock;

public class Node {
	public int value;
	public Node left;
	public Node right;
	public Node parent;
	public ReentrantLock lock;
	
	public Node(int value, Node left, Node right){
		this.value=value;
		this.left=left; 
		this.right=right;
		this.lock=new ReentrantLock();
	}
	public Node(int value){
		this.left=null;
		this.right=null;
		this.parent=null;
		this.value=value;
		this.lock=new ReentrantLock();
	}
	public String value(){
		if(value==-1){return "";}
		else{return Integer.toString(value)+(lock.isLocked()?"[L]":"[ ]");}
	}
	public void setLeft(Node left){
		this.left=left;
	}
	public void setRigth(Node right){
		this.right=right;
	}
}
