import java.util.LinkedList;
import java.util.List;

public class Main {

    public static void main (String[] args) {
        Fence f1 = new Fence(100,23,1);
        Fence f2 = new Fence(500,22,1);
        Fence f3 = new Fence(1000,21,1);
        Fence f4 = new Fence(2000,20,1);
        Fence f5 = new Fence(3000,19,1);
        Fence f6 = new Fence(5000,18,1);
        LinkedList<Fence> fences = new LinkedList<>();
        fences.add(f1);
        fences.add(f2);
        fences.add(f3);
        fences.add(f4);
        fences.add(f5);
        fences.add(f6);
        FenceAlgorithm algo = new FenceAlgorithm(fences);
        Simulator sim = new Simulator(algo);
        //sim.startSimpleSimulation(4234,13);
        sim.startMultiSimulation(4234, 2500, 35);
    }
}
