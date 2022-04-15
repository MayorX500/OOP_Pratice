package Auxiliar;

import java.time.LocalDate;
import java.util.concurrent.atomic.*;

public class State {
    private static final AtomicInteger count = new AtomicInteger(0);
	private int log_id;
    private boolean old_state;
    private boolean new_state;
    private LocalDate update_day;
    


    public State(int log_id, boolean old_state, boolean new_state, LocalDate update_day) {
        this.log_id = log_id;
        this.old_state = old_state;
        this.new_state = new_state;
        this.update_day = update_day;
    }

    public State(boolean old_state, boolean new_state, LocalDate update_day) {
        this(count.incrementAndGet(), old_state, new_state, LocalDate.now());
    }

    public State(boolean old_state, boolean new_state) {
        this(old_state, new_state, LocalDate.now());
    }

    public State() {
        this(false, true);
    }
    
    public State(State s) {
        this(s.getLog_id(),s.getOld_state(),s.getNew_state(),s.getUpdate_day());
    }


    public int getLog_id() {
        return this.log_id;
    }

    public void setLog_id(int log_id) {
        this.log_id = log_id;
    }

    public boolean isOld_state() {
        return this.old_state;
    }

    public boolean getOld_state() {
        return this.old_state;
    }

    public void setOld_state(boolean old_state) {
        this.old_state = old_state;
    }

    public boolean isNew_state() {
        return this.new_state;
    }

    public boolean getNew_state() {
        return this.new_state;
    }

    public void setNew_state(boolean new_state) {
        this.new_state = new_state;
    }

    public LocalDate getUpdate_day() {
        return this.update_day;
    }

    public void setUpdate_day(LocalDate update_day) {
        this.update_day = update_day;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof State)) {
            return false;
        }
        State state = (State) o;
        return log_id == state.log_id && old_state == state.old_state && new_state == state.new_state && this.update_day.equals(state.getUpdate_day());
    }

    @Override
    public String toString() {
        return "{" +
            " log_id='" + getLog_id() + "'" +
            ", old_state='" + isOld_state() + "'" +
            ", new_state='" + isNew_state() + "'" +
            ", update_day='" + getUpdate_day() + "'" +
            "}";
    }

    @Override
    public State clone(){
        return new State(this);
    }

}
