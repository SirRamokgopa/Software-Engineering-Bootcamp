package concurrencyTut;

public class HelloThread extends Thread {
    public static void main(String[] args) {
        // Create a new thread
        Thread thready = new HelloThread();
        // Start the thread 
        thready.start();
    }

    @Override
    public void run() {
        System.out.println("I be a thread too! XD");
    }
}
