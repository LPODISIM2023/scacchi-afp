package it.univaq.disim.oop.scacchi.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class Pareggio extends JDialog {

	Pareggio(final JFrame frame, final boolean modal) {
		super(frame, modal);
		final JPanel myPanel = new JPanel(new GridLayout(0, 1));

		getContentPane().add(myPanel);
		myPanel.add(new JLabel("Pareggio"));

		final JButton okButton = new JButton("OK");
		final JButton reset = new JButton("Nuova Partita");

		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Pareggio.this.setVisible(false);
			}
		});
		
		reset.addActionListener(e -> Tabella.get().AnnullamentoTutteMosse());

		myPanel.add(reset);
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
