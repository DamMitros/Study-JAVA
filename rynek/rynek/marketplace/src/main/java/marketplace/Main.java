package marketplace;

public class Main {
    public static void main(String[] args) {
        System.out.println("Market Simulation Starting...");
        Simulation simulation = new Simulation(10); 
        simulation.runSimulation();
        System.out.println("Market Simulation Ended.");
    }
}