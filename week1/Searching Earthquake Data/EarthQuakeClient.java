import java.util.*;
import edu.duke.*;

public class EarthQuakeClient {
    public EarthQuakeClient() {
        // TODO Auto-generated constructor stub
    }

    public ArrayList<QuakeEntry> filterByMagnitude(ArrayList<QuakeEntry> quakeData,
    double magMin) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        
        for(int i=0; i < quakeData.size(); i++) {
            QuakeEntry quake = quakeData.get(i);
            if(quake.getMagnitude() > magMin) {
                answer.add(quake);
            }
        }
        
        return answer;
    }

    public ArrayList<QuakeEntry> filterByDistanceFrom(ArrayList<QuakeEntry> quakeData,
    double distMax,
    Location from) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();

        for(int i=0; i < quakeData.size(); i++) {
            QuakeEntry quake = quakeData.get(i);
            Location loc = quake.getLocation();
            if(loc.distanceTo(from) < distMax) {
                answer.add(quake);
            }
        }
        return answer;
    }

    public ArrayList<QuakeEntry> filterByDepth(ArrayList<QuakeEntry> QuakeData, double minDepth, double maxDepth) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        for(int i=0; i < QuakeData.size(); i++) {
            QuakeEntry quake = QuakeData.get(i);
            if(quake.getDepth() < maxDepth && quake.getDepth() > minDepth) {
                answer.add(quake);
            }
        }
        return answer;
    }
    
    public ArrayList<QuakeEntry> filterByPhrase(ArrayList<QuakeEntry> QuakeData, String where, String phrase) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        int i=0;
        
        if(where == "start") {
            for(i=0; i < QuakeData.size(); i++) {
                if(QuakeData.get(i).getInfo().indexOf(phrase) == 0) {
                    answer.add(QuakeData.get(i));
                }
            }
        }
        
        if(where == "any") {
            for(i=0; i < QuakeData.size(); i++) {
                if(QuakeData.get(i).getInfo().indexOf(phrase) > 0 && QuakeData.get(i).getInfo().indexOf(phrase) < QuakeData.get(i).getInfo().length() - phrase.length()) {
                    answer.add(QuakeData.get(i));
                }
            }
        }
        
        if(where == "end") {
            for(i=0; i < QuakeData.size(); i++) {
                if(QuakeData.get(i).getInfo().indexOf(phrase) == QuakeData.get(i).getInfo().length() - phrase.length()) {
                    answer.add(QuakeData.get(i));
                }
            }
        }
        
        return answer;
    }
    
    public void dumpCSV(ArrayList<QuakeEntry> list){
        System.out.println("Latitude,Longitude,Magnitude,Info");
        for(QuakeEntry qe : list){
            System.out.printf("%4.2f,%4.2f,%4.2f,%s\n",
                qe.getLocation().getLatitude(),
                qe.getLocation().getLongitude(),
                qe.getMagnitude(),
                qe.getInfo());
        }

    }

    public void bigQuakes() {
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        ArrayList<QuakeEntry> result = filterByMagnitude(list, 5);
        System.out.println("read data for " + list.size() + " quakes");
        for(int i=0; i < result.size(); i++) {
            System.out.println(result.get(i));
        }
        System.out.println("Found " + result.size() + " quakes that match that criteria");
    }

    public void closeToMe(){
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for "+list.size()+" quakes");

        // This location is Durham, NC
        //Location city = new Location(35.988, -78.907);

        // This location is Bridgeport, CA
        Location city =  new Location(38.17, -118.82);
        double distance = 1000;
        double distanceInKm = distance * 1000;
        ArrayList<QuakeEntry> result = filterByDistanceFrom(list,distanceInKm,city);
        for(int i=0; i<result.size(); i++) {
            QuakeEntry entry = result.get(i);
            distanceInKm = city.distanceTo(entry.getLocation())/1000;
            System.out.println(distanceInKm + " " + result.get(i).getInfo());
        }
        System.out.println("Found " + result.size() + " quakes that match that criteria");
    }

    public void quakesOfDepth() {
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list = parser.read(source);
        System.out.println("read data for "+list.size()+" quakes");
        
        ArrayList<QuakeEntry> result = filterByDepth(list, -8000, -5000);
        
        for(int i=0; i < result.size(); i++) {
            System.out.println(result.get(i));
        }
        System.out.println("Found " + result.size() + " quakes that match that criteria");
    }
    
    public void quakesByPhrase() {
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list = parser.read(source);
        System.out.println("read data for "+list.size()+" quakes");
        
        String where = "any", phrase = "Creek";
        
        ArrayList<QuakeEntry> result = filterByPhrase(list, where, phrase);
        
        for(int i=0; i < result.size(); i++) {
            System.out.println(result.get(i));
        }
        
        System.out.println("Found " + result.size() + " quakes that match " + phrase + " at " + where);
    }
    
    public void createCSV(){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedatasmall.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        dumpCSV(list);
        System.out.println("# quakes read: " + list.size());
        for (QuakeEntry qe : list) {
            System.out.println(qe);
        }
    }
    
}
