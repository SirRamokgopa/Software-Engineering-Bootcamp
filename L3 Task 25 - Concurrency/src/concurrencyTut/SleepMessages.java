package concurrencyTut;

public class SleepMessages {
    public static void main(String[] args) throws InterruptedException {
        /* main declares that it throws InterruptedException. This is an exception 
           that sleep throws when another thread interrupts the current thread while
           sleep is active.
        */
        String importantInfo[] = {
            "Mumble and bumble",
            "This and that",
            "Stuff and things",
            "Word on the street"
        };

        for (int i=0; i<importantInfo.length; i++) {
            // Pause for 4 seconds
            Thread.sleep(4000);
            // Print a message every 4 seconds
            System.out.println(importantInfo[i]);
        }
    }
}
