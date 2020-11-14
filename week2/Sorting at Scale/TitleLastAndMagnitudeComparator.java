
/**
 * Write a description of TitleLastAndMagnitudeComparator here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;
public class TitleLastAndMagnitudeComparator implements Comparator<QuakeEntry> {
    
    public int compare(QuakeEntry q1, QuakeEntry q2){
        String lastWord1 = q1.getInfo().substring(q1.getInfo().lastIndexOf(" ")+1, q1.getInfo().length());
        String lastWord2 = q2.getInfo().substring(q2.getInfo().lastIndexOf(" ")+1, q2.getInfo().length());

        if(lastWord1.compareTo(lastWord2) < 0) {
            return -1;
        } else if(lastWord1.compareTo(lastWord2) > 0) {
            return 1;
        } else if(lastWord1.compareTo(lastWord2) == 0) {
            if(q1.getMagnitude() < q2.getMagnitude()) {
                return -1;
            } else if(q1.getMagnitude() > q2.getMagnitude()) {
                return 1;}
        }
        return 0;
    }
}
