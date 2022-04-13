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

    public void addDivision(String s,SmartDevice m){

        if(divisoes.get(s) == null){
            divisoes.put(s,null);
        }
        else{
            ArrayList<SmartDevice> Arr = divisoes.get(s);
            divisoes.put(s,Arr);
        }

    }


}