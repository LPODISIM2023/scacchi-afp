package it.univaq.disim.oop.scacchi.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScaccoMatto extends JDialog {

	ScaccoMatto(final JFrame frame, final boolean modal) {
		super(frame, modal);
		final JPanel myPanel = new JPanel(new GridLayout(0, 1));

		getContentPane().add(myPanel);
		myPanel.add(new JLabel("Scacco Matto"));

		final JButton okButton = new JButton("OK");
		final JButton reset = new JButton("Nuova Partita");

		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ScaccoMatto.this.setVisible(false);
			}
		});
		
		reset.addActionListener(e -> Tabella.get().AnnullamentoTutteMosse());

		myPanel.add(reset);
		myPanel.add(okButton);

		setLocationRelativeTo(frame);
		pack();
		setVisible(false);

	}

	void promptScaccoMatto() {
		setVisible(true);
		repaint();
	}

}