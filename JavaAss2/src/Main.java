
public class Main {

	public static void main(String[] args) {
		Grid world = new Grid(8);
		Snake s = new Snake(0, world, 3);
		Snake l = new Snake(1, world, 3);
		world.start();
		s.start();
		l.start();
	}
}
