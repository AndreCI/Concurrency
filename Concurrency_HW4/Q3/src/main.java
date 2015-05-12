import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class main {
    ExecutorService execute = Executors.newFixedThreadPool(10);
	Future<Integer> futur = execute.submit(new MonCallable());
	
	public static void main(String[] args) {
		

	}

}

