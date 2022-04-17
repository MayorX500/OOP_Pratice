package Simulator;

public class Events {
    private int loops;


    public Events() {
        this(0);
    }

    public Events(int loops) {
        this.loops = loops;
    }

    public int getLoops() {
        return this.loops;
    }

    public void setLoops(int loops) {
        this.loops = loops;
    }


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Events)) {
            return false;
        }
        Events events = (Events) o;
        return loops == events.loops;
    }

    public Events clone(){
        return new Events(this.getLoops());
    }

    @Override
    public String toString() {
        return "{" +
            " loops='" + getLoops() + "'" +
            "}";
    }

}
