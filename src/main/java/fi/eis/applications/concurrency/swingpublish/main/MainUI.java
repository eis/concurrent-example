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

/*
 * Example class based on
 * http://docs.oracle.com/javase/tutorial/uiswing/concurrency/interim.html
 * 
 * Using downloading from internet as an example
 * 
 * For the source code released by Oracle:
 *
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
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
