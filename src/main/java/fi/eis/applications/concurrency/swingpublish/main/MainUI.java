package fi.eis.applications.concurrency.swingpublish.main;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.BorderFactory;

import fi.eis.applications.concurrency.swingpublish.worker.WebCommunicationTask;

/**
 * Example class based on
 * http://docs.oracle.com/javase/tutorial/uiswing/concurrency/interim.html
 * 
 * Using downloading from internet as an example
 * 
 * @author eis
 *
 */
@SuppressWarnings("serial")
public class MainUI extends JFrame implements ActionListener {
	private final GridBagConstraints constraints;
	private final JTextField currentText, totalText;
	private final Border border = BorderFactory.createLoweredBevelBorder();
	private final JButton startButton, stopButton;
	private WebCommunicationTask webTask;

	private JTextField makeText() {
		JTextField t = new JTextField(20);
		t.setEditable(false);
		t.setHorizontalAlignment(JTextField.RIGHT);
		t.setBorder(border);
		getContentPane().add(t, constraints);
		return t;
	}

	private JButton makeButton(String caption) {
		JButton b = new JButton(caption);
		b.setActionCommand(caption);
		b.addActionListener(this);
		getContentPane().add(b, constraints);
		return b;
	}

	public MainUI() {
		super("SwingPublisher");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Make text boxes
		getContentPane().setLayout(new GridBagLayout());
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(3, 10, 3, 10);
		currentText = makeText();
		totalText = makeText();

		// Make buttons
		startButton = makeButton("Start");
		stopButton = makeButton("Stop");
		stopButton.setEnabled(false);

		// Display the window.
		pack();
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if ("Start" == e.getActionCommand()) {
			startButton.setEnabled(false);
			stopButton.setEnabled(true);
			(webTask = new WebCommunicationTask(currentText, totalText)).execute();
		} else if ("Stop" == e.getActionCommand()) {
			startButton.setEnabled(true);
			stopButton.setEnabled(false);
			webTask.cancel(true);
			webTask = null;
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MainUI();
			}
		});
	}
}
