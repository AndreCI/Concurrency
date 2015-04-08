import java.util.LinkedList;

public class Snake extends Thread{
	private final int id;
	private enum directions{
		N, S, E, O
	}
	private directions direction;
	private Grid world;
	private LinkedList<Positions> position;
	private final int size;
	
	
	Snake(int id, Grid world, int size){
		this.id=id;
		this.world=world;
		world.addSnake(id);
		this.size=size;
		position = new LinkedList<Positions>();
		for(int i=0; i<size; i++){
			position.add(new Positions(id,i));
		}System.out.println(position.size());
	}
	private void setNewDirection(){
		directions forbidenOne;
		if(direction==directions.N){
			forbidenOne=directions.S;
		}else if(direction==directions.S){
			forbidenOne=directions.N;
		}else if(direction==directions.E){
			forbidenOne=directions.O;
		}else {
			forbidenOne=directions.E;
		}
		double k =0;
		direction=forbidenOne;
		while(direction==forbidenOne){
			k=Math.random()*4;
			if(k>0 && k<1){
				direction=directions.N;
			}else if(k>=1 && k<2){
				direction=directions.E;
			}else if(k>=2 && k<3){
				direction=directions.S;
			}else{
				direction=directions.O;
			}
		}
	}
	
	private void move() throws InterruptedException{
		setNewDirection();
		Positions nextPos;
		if(direction==directions.N){
			if(position.get(size-1).y==world.getSize()-1){
				nextPos = new Positions(position.get(size-1).x, 0);
			}else{
				nextPos = new Positions(position.get(size-1).x ,position.get(size-1).y+ 1);
			}
		}else if(direction==directions.S){
			if(position.get(size-1).y==0){
				nextPos = new Positions(position.get(size-1).x, world.getSize()-1);
			}else{
				nextPos =new Positions(position.get(size-1).x ,position.get(size-1).y- 1);
			}
			
		}else if(direction==directions.E){
			if(position.get(size-1).x==world.getSize()-1){
				nextPos = new Positions(0, position.get(size-1).y);
			}else{
				nextPos =new Positions(position.get(size-1).x +1 ,position.get(size-1).y);
			}
			
		}else{
			if(position.get(size-1).x==0){
				nextPos=new Positions(world.getSize()-1, position.get(size-1).y);
			}	else{
				nextPos =new Positions(position.get(size-1).x - 1,position.get(size-1).y);
			}
		}
	
				world.writeTab(position.get(0).x, position.get(0).y, -1);
				if(world.getTab(nextPos.x, nextPos.y)!=-1){
					System.out.println("it's dead : " + id);
					world.deleteSnake(id);
					this.join();
				}
				world.writeTab(position.get(size-1).x, position.get(size-1).y, id);
				world.writeTab(nextPos.x, nextPos.y, 8);
			position.remove(0);
			position.add(nextPos);
	}
	
	@Override
	public void run(){
		try {
			while(true){
			move();
			Snake.sleep(300);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public class Positions{
		public int x;
		public int y;
		Positions(int x, int y){
			this.x=x;
			this.y=y;
		}
	}
}



