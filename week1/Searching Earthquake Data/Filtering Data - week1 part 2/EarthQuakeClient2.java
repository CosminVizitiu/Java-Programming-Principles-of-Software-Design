import java.util.*;
import edu.duke.*;

public class EarthQuakeClient2 {
    public EarthQuakeClient2() {
        // TODO Auto-generated constructor stub
    }

    public ArrayList<QuakeEntry> filter(ArrayList<QuakeEntry> quakeData, Filter f) { 
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        for(QuakeEntry qe : quakeData) { 
            if (f.satisfies(qe)) { 
                answer.add(qe); 
            } 
        } 
        
        return answer;
    } 

    public void quakesWithFilter() { 
        EarthQuakeParser parser = new EarthQuakeParser(); 
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);         
        System.out.println("read data for "+list.size()+" quakes");

        /*Filter f = new MinMagFilter(4.0); 
        ArrayList<QuakeEntry> m7  = filter(list, f); 
        for (QuakeEntry qe: m7) { 
            System.out.println(qe);
        } */
        
        
        Filter f = new MagnitudeFilter(4.0, 5.0, "Magnitude");
        ArrayList<QuakeEntry> result = filter(list, f);
        
        f = new DepthFilter(-35000.0, -12000.0, "Magnitude");
        result = filter(result, f);
        
        System.out.println("Result of Magnitude Filter and Depth Filter:");
        
        for (QuakeEntry qe: result) { 
            System.out.println(qe);
        }
        
       
        /*Location japan = new Location(35.42, 139.43);
        Filter f = new DistanceFilter(japan, 10000000, "Distance");
        ArrayList<QuakeEntry> result = filter(list, f);
        
        f = new PhraseFilter("end", "Japan", "Distance");
        result = filter(result, f);
        
        System.out.println("Result of Distance Filter and Phrase Filter:");
        
        for (QuakeEntry qe: result) { 
            System.out.println(qe);
        }
        */
    }
    
    public void testMatchAllFilter() {
        EarthQuakeParser parser = new EarthQuakeParser(); 
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);         
        System.out.println("read data for "+list.size()+" quakes");
        
        MatchAllFilter maf = new MatchAllFilter();
        
        maf.addFilter(new MagnitudeFilter(0.0, 2.0, "Magnitude"));
        maf.addFilter(new DepthFilter(-100000.0, -10000.0, "Depth"));
        maf.addFilter(new PhraseFilter("any", "a", "Phrase"));
        
        ArrayList<QuakeEntry> result = filter(list, maf);
        System.out.println("Match all filter result: ");
        
        for(QuakeEntry qe : result) {
            System.out.println(qe);
        }
        
        System.out.println("Filters used are " + maf.getName());
        System.out.println("Number total of filters: " + result.size());
    }
    
    public void testMatchAllFilter2() {
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);       
        
        /*for(QuakeEntry qe : list) {
            System.out.println(qe);
        }*/
        
        System.out.println("read data for "+list.size()+" quakes");
        
        MatchAllFilter maf = new MatchAllFilter();
        maf.addFilter(new MagnitudeFilter(0.0, 3.0, "Magnitude"));
        maf.addFilter(new DistanceFilter(new Location(36.1314, -95.9372), 10000000, "Distance"));
        maf.addFilter(new PhraseFilter("any", "Ca", "Phrase"));
        
        ArrayList<QuakeEntry> result = filter(list, maf);
        
        System.out.println("Match all filter 2 result:");
        
        for (QuakeEntry qe : result) {
            System.out.println(qe);
        }
        
        System.out.println("Filters used are " + maf.getName());
        System.out.println("Number total of filters: " + result.size());
    }
    
    public void createCSV() {
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "../data/nov20quakedata.atom";
        String source = "data/nov20quakedatasmall.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        dumpCSV(list);
        System.out.println("# quakes read: "+list.size());
    }

    public void dumpCSV(ArrayList<QuakeEntry> list) {
        System.out.println("Latitude,Longitude,Magnitude,Info");
        for(QuakeEntry qe : list){
            System.out.printf("%4.2f,%4.2f,%4.2f,%s\n",
                qe.getLocation().getLatitude(),
                qe.getLocation().getLongitude(),
                qe.getMagnitude(),
                qe.getInfo());
        }
    }

}
