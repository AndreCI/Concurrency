import java.util.LinkedList;


public class Grid extends Thread{
	private final int size;
	private int[][] tab;
	private LinkedList<Integer> aliveSnakes;
	
	Grid(int size){
		this.size=size;
		this.tab = new int[size][size];
		aliveSnakes=new LinkedList<Integer>();
		for(int i=0; i<size;i++){
			for(int j=0; j<size; j++){
			tab[i][j]=-1;
			}
		}
	}
	public void addSnake(int id){
		aliveSnakes.add(id);
	}
	public void deleteSnake(int id){
		if(aliveSnakes.contains(id)){
			aliveSnakes.remove(aliveSnakes.indexOf(id));
			
		}else{
			throw new IllegalArgumentException();
		}
	}
	
	public int getSize(){
		return size;
	}
	
	public int getTab(int i, int j){
		if(i>=size || j>=size){
			throw new IllegalArgumentException();
		}else{
			return tab[i][j];
		}
	}
	public void writeTab(int i, int j, int id){
		if(i>=size || j>=size || i<0 || j<0){
			throw new IllegalArgumentException("i = "+i+", j= "+j);
		}else{
			tab[i][j]=id;
		}
	}
	
	@Override
	public void run(){
		try {
			while(true){
			print();
			Grid.sleep(300);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void print() throws InterruptedException{
		if(aliveSnakes.isEmpty()){
			this.join();
		}
		System.out.println();
		for(int i=0; i<size;i++){
			for(int j=0; j<size; j++){
				if(tab[i][j]==-1){
					System.out.print(" * ");
				}else{
					System.out.print(" "+tab[i][j]+" ");
				}
				if(j==size-1){
					System.out.println();
				}
			}
		}
	}
}
