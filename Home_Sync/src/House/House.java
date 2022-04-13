package House;
import SmartDevice.SmartDevice;

import java.util.ArrayList;
import java.util.HashMap;

public class House{
    private String morada;
    private HashMap<String,ArrayList<SmartDevice>> divisoes;
    private String fornecedor;
    private long consumoMax;

    public House() {
        this.morada = "";
        this.fornecedor = "";
        this.divisoes = null;
        this. consumoMax = 0;
    }

    public House(String s) {
        this.morada = "";
        this.fornecedor = "";
        this.divisoes = null;
        this. consumoMax = 0;
    }

    public void addDevice(String s,SmartDevice m) {

        if (divisoes.get(s) == null) {
            ArrayList<SmartDevice> Arr = new ArrayList<SmartDevice>();
            Arr.add(m);
            divisoes.put(s, Arr);
        } else {
            ArrayList<SmartDevice> Arr = divisoes.get(s);
            Arr.add(m);
            divisoes.put(s, Arr);
        }

    }

    public void turnAllOnOFF(String s,boolean state){

        ArrayList<SmartDevice> Arr = divisoes.get(s);

        for(int i=0;i<divisoes.size();i++){ Arr.get(i).setIs_on(state); }

        divisoes.put(s, Arr);
    }

    public void turnOneOnOFF(String div,SmartDevice smart,boolean state){

        ArrayList<SmartDevice> Arr = divisoes.get(div);

        int i =Arr.indexOf(smart);

        Arr.get(i).setIs_on(state);

        divisoes.put(div, Arr);
    }


}