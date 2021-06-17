package concurrencyTut;
public class Deadlock {
    /* Deadlock describes a situation where two or more threads are blocked forever, 
     * waiting for each other.  
     * Lets's say that Jean and Jenny hava a crush on eachother. They send each other
     * a text and text back only when they recieve a text. But now they text each other
     * at the same time and each are waiting for a respnonse before they text back.
     */
    static class Crush {
        private final String name;

        public Crush(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public synchronized void sendText(Crush crush) {
            System.out.format("%s: %s has texted me!%n", this.name, crush.getName());
            crush.textBack(this);
        }

        public synchronized void textBack(Crush crush) {
            System.out.format("%s: %s has texted me back! <3%n", this.name, crush.getName());
        }
    }

    public static void main(String[] args) {
        final Crush jean = new Crush("Jean");
        final Crush jenny = new Crush("Jenny");

        new Thread(new Runnable(){
            public void run() { jean.sendText(jenny); }
        }).start();

        new Thread(new Runnable(){
            public void run() { jenny.sendText(jean); }
        }).start();
    }
}
