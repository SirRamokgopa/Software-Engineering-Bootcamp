package interfacesTut;

public class Duke implements Homie {
    public static void main(String[] args) throws Exception {

        Homie bob = new Duke("Bob", true);
        System.out.println(Homie.encouragementPhrase);
        bob.giveHighFive(true);
    }

    public String name;
    public boolean isAPet;

    public Duke(String name, boolean isPet) {
        this.name = name;
        this.isAPet = isPet;
    }

    @Override
    public int giveCompany() {
        return 46;
    }

    @Override
    public void giveHighFive(boolean tooSlowBamboozle) {
        System.out.println("High five!");

        if (tooSlowBamboozle) {
            System.out.println("Oh, too slow!");
        }
        else {
            System.out.println("Shake and bake!");
        }
    }

    @Override
    public void offerSnack(String snack) {
        System.out.println("Have a " + snack + ", bro.");        
    }

}
