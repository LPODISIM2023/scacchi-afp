package it.univaq.disim.oop.scacchi.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;
import javax.swing.JTextArea;

class DebugPanel extends JPanel implements Observer {

	private static final Dimension DIMENSIONE_PANNELLO_CHAT = new Dimension(600, 150);
	private final JTextArea jTextArea;

	public DebugPanel() {
		super(new BorderLayout());
		this.jTextArea = new JTextArea("");
		add(this.jTextArea);
		setPreferredSize(DIMENSIONE_PANNELLO_CHAT);
		validate();
		setVisible(true);
	}

	public void redo() {
		validate();
	}

	public void update(final Observable obs, final Object obj) {
		this.jTextArea.setText(obj.toString().trim());
		redo();
	}

}