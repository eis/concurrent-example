package fi.eis.applications.concurrency.swingpublish.worker;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.SwingWorker;

import fi.eis.applications.concurrency.swingpublish.vo.DownloadProgressInfo;

public class WebCommunicationTask extends SwingWorker<Void, DownloadProgressInfo> {
	
	private JTextField currentField, totalField;
	
	public WebCommunicationTask(JTextField currentField, JTextField totalField) {
		this.currentField = currentField;
		this.totalField = totalField;
	}
	
	private static final String TARGET_URL
		= "http://cdimage.debian.org/debian-cd/7.1.0/amd64/iso-cd/debian-7.1.0-amd64-netinst.iso";
	
	/**
	 * Downloads a big file into memory.
	 * Doesn't actually do anything with it, instead throws the downloaded bytes away...
	 * 
	 */
    @Override
    protected Void doInBackground() {
    	
        BufferedInputStream in = null;
        ByteArrayOutputStream fout = null;
        try
        {
            in = new BufferedInputStream(new URL(TARGET_URL).openStream());
            fout = new ByteArrayOutputStream();

            byte data[] = new byte[1024];
            int count;
            while ((count = in.read(data, 0, 1024)) != -1
            		&& !isCancelled())
            {
                fout.write(data, 0, count);
                publish(new DownloadProgressInfo(count));
            }
        } catch (IOException e)
        {
        	// no-op
		}
        finally
        {
            if (in != null) {
				try {
					in.close();
				} catch (IOException e) { }
            }
            if (fout != null) {
				try {
					fout.close();
				} catch (IOException e) { }
            }
        }
        return null;
    }

    @Override
    protected void process(List<DownloadProgressInfo> pairs) {
    	DownloadProgressInfo pair = pairs.get(pairs.size() - 1);
        currentField.setText(String.format("%d", pair.getCurrent()));
        totalField.setText(String.format("%d", pair.getTotal()));
    }
}