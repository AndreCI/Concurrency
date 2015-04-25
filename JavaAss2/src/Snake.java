import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;
import java.util.Random;

public class Snake extends Thread{
    private final Random rand = new Random();
    private final int id;
	private final ReentrantLock lock = new ReentrantLock();
	private enum directions{
		N, S, E, O
	}
	private int lifeTime;
	private int age;
	private directions direction;
	private Grid world;
	private LinkedList<Positions> position;
	private final int size;
	
	
	public Snake(int id, Grid world, int size, int lifeTime){
		this.lifeTime = lifeTime;
		age=0;
		this.id=id;
		this.world=world;
		world.addSnake(id);
		this.size=size;
		position = new LinkedList<Positions>();
        for(int i=0; i<size; i++){
            int x = rand.nextInt(size);
            Positions p = new Positions(x, i);
            while(position.contains(p)){
                p = new Positions(rand.nextInt(size), i);
            }
			position.add(new Positions(id*3,i));
		}
        System.out.println(position.size());
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
					world.deleteSnake(id);
					this.join();
				}else{
					lock.lock();
					try{
						world.writeTab(position.get(size-1).x, position.get(size-1).y, id);
						world.writeTab(nextPos.x, nextPos.y, 8); //Should be id
						world.addLog(nextPos.x, nextPos.y, id, System.currentTimeMillis());
						position.remove(0);
						position.add(nextPos);
					}finally{
						lock.unlock();
					}
				}
	}
	
	@Override
	public void run(){
		try {
			while(true){
				move();
			if(age>=lifeTime){
				this.join();
			}
			Snake.sleep(300); //TODO : 100ms
			age++;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public class Positions{
		public int x;
		public int y;
		public Positions(int x, int y){
			this.x=x;
			this.y=y;
		}

        @Override
        public boolean equals(Object o){
            if(o == this) {
                return true;
            }
            if(!(o instanceof Positions)){
                return false;
            }
            Positions p = (Positions)o;
            return (p.x == this.x) && (p.y == this.y);
        }

        @Override
        public  int hashCode(){
         int hash = 0;
         hash = 23 * hash + this.x;
         hash = 23 * hash + this.y;
         return hash;
        }
	}


}



