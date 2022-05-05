package Auxiliar;

import java.io.Serializable;

public class Pair<L,R> implements Serializable{
    private L l;
    private R r;
    
    public Pair(L l, R r){
        this.l = l;
        this.r = r;
    }
    
    public Pair(){
        this(null, null);
    }

    public Pair(Pair<L,R> p){
        this(p.getL(), p.getR());
    }

    public L getL(){ return l; }
    
    public R getR(){ return r; }
    
    public void setL(L l){ this.l = l; }
    
    public void setR(R r){ this.r = r; }

    public String toString () {
        return ("("+l.toString()+", "+r.toString()+")");
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Pair<?,?>)) {
            return false;
        }
        Pair<?,?> pair = (Pair<?,?>) o;
        return this.l.equals(pair.getL()) && this.r.equals(pair.getR());
    }

    public Pair<L,R> clone(){
        return new Pair<>(this);
    }
}