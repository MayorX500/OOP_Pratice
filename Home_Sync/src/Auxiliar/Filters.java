package Auxiliar;

public class Filters {
/*            // Now let's sort the HashMap by values
        // there is no direct way to sort HashMap by values but you
        // can do this by writing your own comparator, which takes
        // Map.Entry object and arrange them in order increasing 
        // or decreasing by values.
        
        Comparator<Entry<String, Double>> valueComparator 
               = new Comparator<Entry<String,Double>>() {
            
            @Override
            public int compare(Entry<String, Double> e1, Entry<String, Double> e2) {
                Double v1 = e1.getValue();
                Double v2 = e2.getValue();
                return v1.compareTo(v2);
            }
        };
        
        // Sort method needs a List, so let's first convert Set to List in Java
        List<Entry<String, Double>> listOfEntries 
                  = new ArrayList<Entry<String, Double>>(entries);
        
        // sorting HashMap by values using comparator
        Collections.sort(listOfEntries, valueComparator);
        
        LinkedHashMap<String, Double> sortedByValue 
                    = new LinkedHashMap<String, Double>(listOfEntries.size());
        
        // copying entries from List to Map
        for(Entry<String, Double> entry : listOfEntries){
            sortedByValue.put(entry.getKey(), entry.getValue());
        }
    */
}
