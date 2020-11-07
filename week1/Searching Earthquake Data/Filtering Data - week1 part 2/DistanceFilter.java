public class DistanceFilter implements Filter{
    
    private Location location;
    private double distance;
    private String filterName;
    
    public DistanceFilter(Location loc, double dist, String filterName) {
        location = loc;
        distance = dist;
        this.filterName = filterName;
    }
    
    public boolean satisfies(QuakeEntry qe) {
        return location.distanceTo(qe.getLocation()) < distance;
    }
    
    public String getName() {
        return filterName;
    }
}
