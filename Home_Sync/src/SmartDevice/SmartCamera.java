package SmartDevice;

public class SmartCamera extends SmartDevice{
    private double resolution;
    private double file_size;
    private double energy_cons;

    public SmartCamera() {
        this.resolution = 0;
        this.file_size = 0;
        this.energy_cons = 0;
    }

    public SmartCamera(double resolution, double file_size) {
        this.resolution = resolution;
        this.file_size = file_size;
        this.energy_cons = resolution * file_size;

    }
    public double getEnergy_cons() {
        return this.energy_cons;
    }

    public double getResolution() {
        return this.resolution;
    }

    public void setResolution(double resolution) {
        this.resolution = resolution;
    }

    public double getFile_size() {
        return this.file_size;
    }

    public void setFile_size(double file_size) {
        this.file_size = file_size;
    }

    public SmartCamera resolution(double resolution) {
        setResolution(resolution);
        return this;
    }

    public SmartCamera file_size(double file_size) {
        setFile_size(file_size);
        return this;
    }

    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof SmartCamera)) {
            return false;
        }
        SmartCamera smartCamera = (SmartCamera) o;
        return resolution == smartCamera.resolution && file_size == smartCamera.file_size && energy_cons == smartCamera.energy_cons;
    }

    public String toString() {
        return "{" +
            " resolution= " + getResolution() + " " +
            ", file_size= " + getFile_size() + " " +
            ", energy_cons= " + getEnergy_cons() + " " +
            "}";
    }

}

