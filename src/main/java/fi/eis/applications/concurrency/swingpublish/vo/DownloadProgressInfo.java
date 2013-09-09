package fi.eis.applications.concurrency.swingpublish.vo;

public class DownloadProgressInfo {
	private static long total;
    private final long current;

    public DownloadProgressInfo(long current) {
        this.current = current;
        DownloadProgressInfo.total += current;
    }
    
    public long getCurrent() {
    	return this.current;
    }
    public long getTotal() {
    	return DownloadProgressInfo.total;
    }
}
