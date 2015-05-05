package elements;

import java.util.LinkedList;

public class Tree {
	public Node root;
	public Tree(Node root){
		this.root=root;
	}
	public void toPrintf(int level){
		LinkedList<Node> thislevel = new LinkedList<Node>();
		LinkedList<Node> nextlevel = new LinkedList<Node>();
		thislevel.add(root);
		do{
			nextlevel.clear();
			for(int i=0;i<level;i++){
				System.out.print("     ");
			}
		while(!thislevel.isEmpty()){
			Node courrant = thislevel.remove();
			System.out.print(courrant.value());
			if(courrant.value!=-1 && courrant.left!=null){
			nextlevel.add(courrant.left);
			}else if(courrant.value!=-1 && courrant.left==null){
				nextlevel.add(new Node(-1));
			}
			
			if(courrant.value!=-1 && courrant.right!=null){
			nextlevel.add(courrant.right);
			}else if(courrant.value!=-1 && courrant.right==null){
				nextlevel.add(new Node(-1));
			}
			System.out.print("       ");
		}
		level--;
		System.out.println();
		System.out.println();
		thislevel.addAll(nextlevel);
		}while(!nextlevel.isEmpty());
	}
	public Node search(Node x, int k){
		x.lock.lock();
		while(x!=null && k!= x.value){

			if(x.left!=null){
				x.left.lock.lock();
			}if(x.right!=null){
				x.right.lock.lock();
			}
			x.lock.unlock();
			if(k<x.value){		
				if(x.right!=null){
				x.right.lock.unlock();
				}
				x=x.left;
			}else{
				if(x.left!=null){
					x.left.lock.unlock();
				}
				x=x.right;
			}
		}
		x.lock.unlock();
		return x;
	}
	public Node minimum(Node x){
		x.lock.lock();
		try{
		while(x.left!=null){
			x.left.lock.lock();
				x=x.left;
			x.parent.lock.unlock();
		}
		}finally{
			x.lock.unlock();
		}
		return x;
	}
	public Node maximum(Node x){
		x.lock.lock();
		try{
		while(x.right!=null){
			x.left.lock.lock();
			x=x.right;
			x.parent.lock.unlock();
		}
		}finally{
			x.lock.unlock();
		}
		return x;
	}
	public void insert(int x){
		Node z = new Node(x);
		Node y=null;
		Node k=root;
		k.lock.lock();
		while(k!=null){
			if(k.left!=null){
				k.left.lock.lock();
			}if(k.right!=null){
				k.right.lock.lock();
			}
			k.lock.unlock();
				y=k;
				if(z.value<k.value){
					if(k.right!=null){
						k.right.lock.unlock();
					}
					k=k.left;
				}else{
					if(k.left!=null){
						k.left.lock.unlock();
					}
					k=k.right;
				}
			}
			z.parent=y;
			if(z.value<y.value){
				y.left=z;
			}else{
				y.right=z;
			}
	}
	
	/**
	 * replaces subtree rooted at u with that rooted at v
	 * @param u a : Node
	 * @param v a : Node
	 */
	private void transplant(Node u, Node v){
		if(u.parent==null){
			root=v;
		}else if(u.parent.left==u){
			u.parent.left=v;
		}else{
			u.parent.right=v;
		}
		if(v!=null){
			v.parent=u.parent;
		}
	}
	
	public void delete(int x){
		Node z = search(root, x);
		z.lock.lock();
		if(z.left==null){
			z.parent.lock.lock();
			if(z.right!=null){
				z.right.lock.lock();
			}
			transplant(z, z.right);	
			z.parent.lock.unlock();
			if(z.right!=null){
				z.right.lock.unlock();
			}
			
		}else if(z.right==null){
			z.parent.lock.lock();
			if(z.left!=null){
				z.left.lock.lock();
			}
			transplant(z, z.left);
			z.parent.lock.unlock();
			if(z.left!=null){
				z.left.lock.unlock();
			}
		}else{
			Node y = minimum(z.right);
			y.lock.lock();
			if(y.parent!=z){
				transplant(y, y.right);
				y.right=z.right;
				y.right.parent=y;
			}
			transplant(z, y);
			y.left=z.left;
			y.left.parent=y;
			y.lock.unlock();
		}
	}
}
