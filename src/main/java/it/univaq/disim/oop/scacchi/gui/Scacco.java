package it.univaq.disim.oop.scacchi.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Scacco extends JDialog {

	Scacco(final JFrame frame, final boolean modal) {
		super(frame, modal);
		final JPanel myPanel = new JPanel(new GridLayout(0, 1));

		getContentPane().add(myPanel);
		myPanel.add(new JLabel("Scacco"));

		final JButton okButton = new JButton("OK");

		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Scacco.this.setVisible(false);
			}
		});

		myPanel.add(okButton);

		setLocationRelativeTo(frame);
		pack();
		setVisible(false);

	}

	void promptScacco() {
		setVisible(true);
		repaint();
	}

}