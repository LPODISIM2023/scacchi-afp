package it.univaq.disim.oop.scacchi.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Pareggio extends JDialog {

	Pareggio(final JFrame frame, final boolean modal) {
		super(frame, modal);
		final JPanel myPanel = new JPanel(new GridLayout(0, 1));

		getContentPane().add(myPanel);
		myPanel.add(new JLabel("Pareggio"));

		final JButton okButton = new JButton("OK");

		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Pareggio.this.setVisible(false);
			}
		});

		myPanel.add(okButton);

		setLocationRelativeTo(frame);
		pack();
		setVisible(false);

	}

	void promptPareggio() {
		setVisible(true);
		repaint();
	}

}
