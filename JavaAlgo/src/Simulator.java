import java.util.List;
import static java.lang.Thread.sleep;

public class Simulator {

    private List<Fence> fences;
    private Fence lastFence;
    private FenceAlgorithm algo;

    public Simulator(FenceAlgorithm algo) {
        this.algo = algo;
    }

    public void startSimpleSimulation(int radius, int days) {
        fences = FenceAlgorithm.getFences();
        lastFence = fences.get(0);
        for (int d = 0; d < days; d++) {
            //von Zuhause zur Arbeit
            for (int i = 1; i < fences.size(); i++) {
                Fence f = fences.get(i);
                algo.update(lastFence, f);
                if (f.getRadius() > radius) {
                    break;
                }
                lastFence = f;
            }
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            fences = FenceAlgorithm.getFences();
            //von der Arbeit nach Hause
            for (int i = fences.size() - 1; i >= 0; i--) {
                Fence f = fences.get(i);
                if (f.getRadius() <= radius) {
                    algo.update(lastFence, f);
                }
                lastFence = f;
            }
        }
    }

    public void startMultiSimulation(int r1, int r2, int days) {
        fences = FenceAlgorithm.getFences();
        lastFence = fences.get(0);
        for (int d = 0; d < days; d++) {
            // von Zuhause zur Arbeit
            for (int i = 1; i < fences.size(); i++) {
                Fence f = fences.get(i);
                algo.update(lastFence, f);
                if (f.getRadius() > r1) {
                    break;
                }
                lastFence = f;
            }
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            fences = FenceAlgorithm.getFences();
            //von der Arbeit nach Hause
            for (int i = fences.size() - 1; i >= 0; i--) {
                Fence f = fences.get(i);
                if (f.getRadius() <= r1) {
                    algo.update(lastFence, f);
                }
                lastFence = f;
            }
            try {
                sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //von Zuhause zum Sport
            for (int i = 1; i < fences.size(); i++) {
                Fence f = fences.get(i);
                algo.update(lastFence, f);
                if (f.getRadius() > r2) {
                    break;
                }
                lastFence = f;
            }
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            fences = FenceAlgorithm.getFences();
            //vom Sport nach Hause
            for (int i = fences.size() - 1; i >= 0; i--) {
                Fence f = fences.get(i);
                if (f.getRadius() <= r2) {
                    algo.update(lastFence, f);
                }
                lastFence = f;
            }
        }
    }
}