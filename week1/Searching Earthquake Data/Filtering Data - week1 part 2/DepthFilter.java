public class DepthFilter implements Filter{
    
    private double minDepth;
    private double maxDepth;
    private String filterName;
    
    public DepthFilter(double min, double max, String filterName) {
        minDepth = min;
        maxDepth = max;
        this.filterName = filterName;
    }
    
    public boolean satisfies(QuakeEntry qe) {
        return qe.getDepth() >= minDepth && qe.getDepth() <= maxDepth;
    }
    
    public String getName() {
        return filterName;
    }
}
