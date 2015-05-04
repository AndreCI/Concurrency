package elements;

public class Tree {
	public Node root;
	public Tree(Node root){
		this.root=root;
	}
	public Node search(Node x, int k){
		if(x==null || k==x.value){
			if(x!=null){
				x.lock.unlock();
			}
			return x;
		}else{
			x.lock.lock();
			x.parent.lock.unlock();
			if(k<x.value){
				return search(x.left,k);
			}else{
				return search(x.right,k);
			}
		}
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
		k.lock.lock();
		try{
		while(k!=null){
			k.left.lock.lock();
			k.right.lock.lock();
			try{
				y=k;
				if(z.value<k.value){
					k.right.lock.unlock();
					k=k.left;
				}else{
					k.left.lock.unlock();
					k=k.right;
				}
			}finally{
				k.parent.lock.unlock();
			}
		}
		}finally{
			k.lock.unlock();
		}
		y.lock.lock();
		try{
			z.parent=y;
			if(z.value<y.value){
				y.left=z;
			}else{
				y.right=z;
			}
		}finally{
			y.lock.unlock();
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
		root.lock.lock();
		Node z = search(root, x);
		z.lock.lock();
		try{
		if(z.left==null){
			if(z.right!=null){
				z.right.lock.lock();
				try{
				transplant(z, z.right);	
				}finally{
				z.right.lock.unlock();
				}
			}else{
				transplant(z, z.right);		
			}
			transplant(z, z.right);	
		}else if(z.right==null){
			z.left.lock.lock();
			try{
			transplant(z, z.left);
			}finally{
			z.left.lock.unlock();
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
		z.lock.unlock();
	}
	}
}
