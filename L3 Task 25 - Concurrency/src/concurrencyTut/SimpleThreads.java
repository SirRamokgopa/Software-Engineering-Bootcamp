package concurrencyTut;

public class SimpleThreads {
    public static void main(String[] args) throws InterruptedException {
        // Delay, in millsecs before we interrupt MessageLoop thread
        // (default is 1 hour)
        long patience = 1000 * 60 * 60;

        // Give patience in secconds if user command line argument is present.
        if (args.length >0) {
            try {
                patience = Long.parseLong(args[0]);
            }
            catch (NumberFormatException ex) {
                System.err.println("Argument must be an integer.");
                System.exit(1);
            }
        }

        threadMessage("Starting MessageLoop thread");
        long startTime = System.currentTimeMillis();
        Thread thready = new Thread(new MessageLoop());
        thready.start();

        threadMessage("Waiting for MessageLoop thread to finish");

        // Loop until MessageLoop thread exits
        while (thready.isAlive()) {
            threadMessage("Still waiting for this shandis... \n I'll give it another 2 seconds");

            thready.join(2000);
            if (((System.currentTimeMillis() - startTime) > patience) && thready.isAlive()) {
                threadMessage("I'm over waiting, yo.");
                
                // Now we're going to get stuff done
                thready.interrupt();
                // Interrupted thready's run method now we just return from the catch
                thready.join();
            }
        }
        threadMessage("It's about time!");
    }

    // Display a the name of the current thread then display a message
    static void threadMessage(String message) {
        String threadName = Thread.currentThread().getName();
        System.out.format("%s: %s%n", threadName, message);
    }

    private static class MessageLoop implements Runnable {
        public void run() {
            String importanInfo[] = {
                "Mumble and bumble",
                "This and that",
                "Stuff and things",
                "Word on the street"
            };

            try {
                for (int i=0; i<importanInfo.length; i++) {
                    // Pause for 4 secs
                    Thread.sleep(4000);
                    // Print a message
                    threadMessage(importanInfo[i]);
                }
            }
            catch (InterruptedException ex) {
                threadMessage("I was held up, man.");
            }
        }
    }
}
