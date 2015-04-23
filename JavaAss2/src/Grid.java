import java.util.ArrayList;
import java.util.LinkedList;

public class Grid extends Thread{
	private final int size;
	private int[][] tab;
	private ArrayList<LinkedList<Positions>> log;
	private ArrayList<LinkedList<Long>> ts;
	private LinkedList<Integer> snakes;
	private LinkedList<Integer> aliveSnakes;
	
	Grid(int size){
		this.size=size;
		this.tab = new int[size][size];
		log = new ArrayList<LinkedList<Positions>>();
		ts =  new ArrayList<LinkedList<Long>>();
		aliveSnakes=new LinkedList<Integer>();
		snakes = new LinkedList<Integer>();
		for(int i=0; i<size;i++){
			for(int j=0; j<size; j++){
			tab[i][j]=-1;
			}
		}
	}
	public void addSnake(int id){
		log.add(id, new LinkedList<Positions>());
		ts.add(id, new LinkedList<Long>());
		snakes.add(id);
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
	public void addLog(int x, int y, int id, long timeStamp){
		ts.get(id).add(timeStamp);
		log.get(id).add(new Positions(x,y));
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
	
	private void print() throws InterruptedException{
		if(aliveSnakes.isEmpty()){
			while(!snakes.isEmpty()){
				int id = snakes.get(0);
				LinkedList<Positions> logSnake = log.get(snakes.get(0));
				LinkedList<Long> tsSnake = ts.get(snakes.get(0));
				System.out.println("Snake "+id+" died at : " +logSnake.getLast().toString() + " at " + tsSnake.getLast());
				snakes.remove();
			}
			
			this.join();
		}
		System.out.println();
		for(int i=0; i<size;i++){
			for(int j=0; j<size; j++){
				if(tab[i][j]==-1){
					//System.out.print(" ("+i+","+j+") ");
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
	public class Positions{
		public int x;
		public int y;
		Positions(int x, int y){
			this.x=x;
			this.y=y;
		}
		public String toString(){
			return "("+x+","+y+")";
		}
	}
}
