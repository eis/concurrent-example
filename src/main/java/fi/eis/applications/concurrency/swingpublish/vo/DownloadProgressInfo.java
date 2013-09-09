package fi.eis.applications.concurrency.swingpublish.vo;

public class DownloadProgressInfo {
    private final int current;
	private final long total;

    public DownloadProgressInfo(int current, long total) {
        this.current = current;
        this.total = total;
    }
    
    public long getCurrent() {
    	return this.current;
    }
    public long getTotal() {
    	return this.total;
    }
}
