
public class Main {

	public static void main(String[] args) {
		Grid world = new Grid(8);
		Snake s = new Snake(0, world, 4);
		Snake l = new Snake(1, world, 5);
		world.start();
		s.start();
		l.start();
	}
}
