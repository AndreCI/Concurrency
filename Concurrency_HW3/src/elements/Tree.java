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
			toPrintf(3);
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
	public Node successor(Node x){
		if(x.right!=null){
			return minimum(x.right);
		}else{
			Node y=x.parent;
			while(y!=null && x==y.right){
				x=y;
				y=y.parent;
			}
			return y;
		}
	}
	public Node predecessor(Node x){
		if(x.left!=null){
			return maximum(x.left);
		}else{
			Node y = x.parent;
			while(y!=null && x==y.left){
				x=y;
				y=y.parent;
			}
			return y;
		}
		}
	public void insert(int x){
		Node z = new Node(x);
		Node y=null;
		Node k=root;
		//k.lock.lock();
		try{
		while(k!=null){
			//k.left.lock.lock();
			//k.right.lock.lock();
			try{
				y=k;
				if(z.value<k.value){
				//	k.right.lock.unlock();
					k=k.left;
				}else{
				//	k.left.lock.unlock();
					k=k.right;
				}
			}finally{
		//		k.parent.lock.unlock();
			}
		}
		}finally{
	//		k.lock.unlock();
		}
	//	y.lock.lock();
		try{
			z.parent=y;
			if(z.value<y.value){
				y.left=z;
			}else{
				y.right=z;
			}
		}finally{
	//		y.lock.unlock();
		}
	}
	
	/**
	 * replaces subtree rooted at u with that rooted at v
	 * @param u a : Node
	 * @param v a : Node
	 */
	public void transplant(Node u, Node v){
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
//		z.lock.lock();
		try{
		if(z.left==null){
			if(z.right!=null){
	//			z.right.lock.lock();
				try{
				transplant(z, z.right);	
				}finally{
		//		z.right.lock.unlock();
				}
			}else{
				transplant(z, z.right);		
			}
			transplant(z, z.right);	
		}else if(z.right==null){
			//z.left.lock.lock();
			try{
			transplant(z, z.left);
			}finally{
		//	z.left.lock.unlock();
			}
		}else{
			Node y = minimum(z.right);
			if(y.parent!=z){
				transplant(y, y.right);
				y.right=z.right;
				y.right.parent=y;
			}
			transplant(z, y);
			y.left=z.left;
			y.left.parent=y;
		}
	}finally{
		//z.lock.unlock();
	}
	}
}
