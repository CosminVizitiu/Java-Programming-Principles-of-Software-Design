import java.util.*;
import edu.duke.*;

/**
 * Write a description of LargestQuakes here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LargestQuakes {
    
    public Integer indexOfLargest(ArrayList<QuakeEntry> data) {
        int answer=0;
        double contor = data.get(0).getMagnitude();
        for(int i=0; i< data.size(); i++) {
            if(contor <= data.get(i).getMagnitude()) {
                answer = i;
                contor = data.get(i).getMagnitude();
            }
        }
        return answer;
    }
    
    public ArrayList<QuakeEntry> getLargest(ArrayList<QuakeEntry> quakeData, int howMany) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        ArrayList<QuakeEntry> copy = new ArrayList<QuakeEntry>(quakeData);
        int index=0;
        while(howMany > 0) {
           index = indexOfLargest(copy);
           if(copy.size() > 0) {
                answer.add(copy.get(index));
                copy.remove(index);
           }
           howMany--;
        }
        return answer;
    }
    
    public void finLargestQuakes() {
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list = parser.read(source);

        //for(int i =0; i < list.size(); i++) {
        //    System.out.println(list.get(i));
        //}
        
        System.out.println("read data for "+list.size()+" quakes");
        
        //int result = indexOfLargest(list);
        //System.out.println("Index of largest magnitude quake is " + result + " and the quake is " + list.get(result));
        
        ArrayList<QuakeEntry> result = getLargest(list, 5);
        
        for(int i=0; i < result.size(); i++) {
            System.out.println(result.get(i));
        }
    }
}
