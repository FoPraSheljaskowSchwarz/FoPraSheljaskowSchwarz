public class Fence implements Comparable<Fence>{

    private float radius;
    private int temp;
    private int fenceType; // 1 fest, 2 variabel, 3 statisch
    private int dwellCount;

    public Fence(float radius, int temp, int fenceType) {
        this.radius = radius;
        this.temp = temp;
        this.fenceType = fenceType;
        dwellCount = 0;
    }

    public float getRadius() {
        return radius;
    }

    public int getTemp() {
        return temp;
    }

    public int getDwellCount() {
        return dwellCount;
    }

    public void incDwellCount() {
        dwellCount++;
    }

    public void setDwellCount(int count) {
        dwellCount = count;
    }

    public int getFenceType() {
        return fenceType;
    }

    public void setFenceType(int fenceType) {
        this.fenceType = fenceType;
    }

    @Override
    public int compareTo(Fence o) {
        if (this.radius > o.radius)
            return 1;
        else if (this.radius < o.radius)
            return -1;
        else
            return 0;
    }
}
