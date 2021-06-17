package concurrencyTut;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.Random;

public class SafeLock {
    
    static class Crush {
        private final String name;
        private final Lock lock = new ReentrantLock();

        public Crush (String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public boolean impendingText(Crush sender) {
            Boolean myLock = false;
            Boolean yourLock = false;

            try {
                myLock = lock.tryLock();
                yourLock = sender.lock.tryLock();
            }
            finally {
                if (!(myLock && yourLock)) {
                    if (myLock) {
                        lock.unlock();
                    }
                    if (yourLock) {
                        sender.lock.unlock();
                    }
                }
            }
            return myLock && yourLock;
        }

        public void sendText(Crush sender) {
            if (impendingText(sender)) {
                try {
                    System.out.format("%s: %s sent me a text! <3%n", this.name, sender.getName());
                    sender.textBack(this);
                }
                finally {
                    lock.unlock();
                    sender.lock.unlock();
                }
            }
            else {
                System.out.format("%s: %s stated to send me a text, but saw that I was already typing%n", 
                    this.name, sender.getName());
            }
        }

        public void textBack(Crush sender) {
            System.out.format("%s: %s texted me back!%n", this.name, sender.getName());
        }
    }

    static class TextLoop implements Runnable {
        private Crush sender;
        private Crush receiver;

        public TextLoop(Crush sender, Crush receiver) {
            this.sender = sender;
            this.receiver = receiver;
        }

        public void run() {
            Random random = new Random();
            for (;;) {
                try {
                    Thread.sleep(random.nextInt(10));
                }
                catch (InterruptedException ex) {}
                receiver.sendText(sender);
            }
        }
    }

    
    public static void main(String[] args) {
        final Crush jenny = new Crush("Jenny");
        final Crush jean = new Crush("Jean");

        new Thread(new TextLoop(jenny, jean)).start();
        new Thread(new TextLoop(jean, jenny)).start();
    }
}
