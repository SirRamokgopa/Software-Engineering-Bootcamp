package concurrencyTut;

public class HelloRunnable implements Runnable{
    public static void main(String[] args) {
        // Create a runnable object
        Runnable runny = new HelloRunnable();
        // Create and start a new thread
        new Thread(runny).start();
    }

    @Override
    public void run() {
        System.out.println("This be a thread, yo!");        
    }
}
