package Simulator;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Events implements Serializable{
    private String event;
    private LocalDateTime time;

    public Events(Events e) {
        this(e.getEvent(),e.getTime());
    }

    public Events() {
        this("",LocalDateTime.now());
    }

    public Events(String s, LocalDateTime d) {
        this.event = s;
        this.time = d;
    }

    public String getEvent() {
        return this.event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public LocalDateTime getTime() {
        return this.time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Events)) {
            return false;
        }
        Events events = (Events) o;
        return this.event.equals(events.event) && this.time.equals(events.time);
    }

    @Override
    public Events clone(){
        return new Events(this);
    }

    @Override
    public String toString() {
        return "{" +
            " event='" + getEvent() + "'" +
            ", time='" + getTime() + "'" +
            "}";
    }

}
