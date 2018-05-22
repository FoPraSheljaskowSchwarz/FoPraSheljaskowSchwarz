import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class FenceAlgorithm {

    private static List<Fence> fences;
    private Timer t;
    private Fence lastFence = null;

    //Konstanten
    private long standbyTime = 10;     //zeit nach welcher in standby gewechselt wird
    private int dwellCountLimit = 5;        //höhe des dwellCount ab welcher ein neuer temporärer Fence erstellt wird
    private int minDistance = 300;          // minimale Distanz zwischen 2 Geofences
    private int standbyTemp = 18;           // Temperatur im Standby

    public FenceAlgorithm (List<Fence> fences) {
        this.fences = fences;
        t = new Timer();
    }

    public void update(Fence f1, Fence f2) {
        t.cancel();

        // Passe nach Fence-Überschreitung Temperatur an
        adjustTemperature(f2.getTemp());
        if (f2.equals(fences.get(0))) {
            //keine Aktion falls Zuhause
            System.out.println(" -> zuhause");
        } else {
            // falls Timer abläuft, gehe in Standby und passe Fences an
            TimerTask tt = new TimerTask() {
                @Override
                public void run() {
                    //standby
                    adjustTemperature(standbyTemp);
                    Fence fence = f2;
                    if (fence.getDwellCount() != -1) fence.incDwellCount();
                    System.out.print("standby. dwellcount: " + f2.getDwellCount() + " currFence " + f2.getRadius());
                    adjustFence(f2);
                }
            };
            t = new Timer();
            t.schedule(tt, standbyTime);
        }
    }

    public void adjustFence (Fence fence) {
        //falls dwellCount > Limit, erstelle neuen Fence
        if (fence.getDwellCount() > dwellCountLimit) {
            // Folgend wir Radius des neuen Fences bestimmt
            float radius;
            int index = 0;
            for (Fence f:fences) {
                if (f.equals(fence)) {
                    break;
                }
                index++;
            }
            try {
                lastFence = fences.get(index-1);
                radius = lastFence.getRadius();
            } catch (ArrayIndexOutOfBoundsException e) {
                radius = 0;
            }
            radius = (fence.getRadius() + radius) / 2;
            if (fence.getRadius() - radius < minDistance) {
                // wenn abstand zu anderen Fences zu klein ist, wird kein neuer Fence erstellt
                fence.setDwellCount(-1);
                fixFences(lastFence,fence);
            } else {
                // erstellung des neuen Fence
                Fence newFence = new Fence(radius, fence.getTemp(), 2);
                fences.add(newFence);
                System.out.print(" -> added new Fence at " + radius);
                Collections.sort(fences);

                fence.setDwellCount(0);
            }
        }
    }

    private void fixFences (Fence exitFence, Fence enterFence) {
        //Setze bei kleinem Abstand zu nachbarfences den nächsten Fence statisch und entferne den Rest der variablen Fences
        if (exitFence.getFenceType() == 2) {
            exitFence.setFenceType(3);
            System.out.print(" -> set " + exitFence.getRadius() + " static");
        }
        if (enterFence.getFenceType() == 2) {
            enterFence.setFenceType(3);
            System.out.print(" -> set " + enterFence.getRadius() + " static");
        }
        for (int i = fences.size()-1; i > 0; i--) {
            Fence f = fences.get(i);
            if (f.getFenceType() == 2) {
                System.out.print(" -> removed fence: " + fences.get(i).getRadius());
                fences.remove(i);
            }
        }
    }

    private void adjustTemperature(int temperature) {
        //sende Signal an Heizung
        //System.out.println("neue temperatur " + temperature);
    }

    public static List<Fence> getFences() {
        return fences;
    }
}