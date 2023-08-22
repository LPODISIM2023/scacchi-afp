package it.univaq.disim.oop.scacchi.gui;

import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import it.univaq.disim.oop.scacchi.controller.ScacchieraController;
import it.univaq.disim.oop.scacchi.domain.Scacchiera;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Tabella {

	private final JFrame gameFrame;
	private final ScacchieraPanel scacchieraPanel;
	private final Scacchiera scacchiScacchiera;

	private final static Dimension DIMENSIONE_FRAME_ESTERNO = new Dimension(600, 600);
	private final static Dimension DIMENSIONE_PANNELLO_CONTROLLO = new Dimension(400, 350);
	private final static Dimension DIMENSIONE_PANNELLO_CASELLA = new Dimension(10, 10);
	private static String defaultImgPezzoPath = "art/pezzi/";

	private Color coloreCasellaChiara = Color.decode("#FFFACD");
	private Color coloreCasellaScura = Color.decode("#593E1A");

	public Tabella() {
		this.gameFrame = new JFrame("JScacchi");
		this.gameFrame.setLayout(new BorderLayout());
		final JMenuBar tabellaMenuBar = creaTabellaMenuBar();
		this.gameFrame.setJMenuBar(tabellaMenuBar);
		this.gameFrame.setSize(DIMENSIONE_FRAME_ESTERNO);
		this.scacchiScacchiera = Scacchiera.creaScacchieraStandard();
		this.scacchieraPanel = new ScacchieraPanel();
		this.gameFrame.add(this.scacchieraPanel, BorderLayout.CENTER);
		this.gameFrame.setVisible(true);
	}

	private JMenuBar creaTabellaMenuBar() {
		final JMenuBar tabellaMenuBar = new JMenuBar();
		tabellaMenuBar.add(creaFileMenu());
		return tabellaMenuBar;
	}

	private JMenu creaFileMenu() {
		final JMenu fileMenu = new JMenu("File");
		final JMenuItem openPGN = new JMenuItem("Carica PGN File");
		openPGN.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				System.out.println("aprire questo file pgn!");
			}
		});
		fileMenu.add(openPGN);

		final JMenuItem exitMenuItem = new JMenuItem("Esci");
		exitMenuItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		fileMenu.add(exitMenuItem);
		return fileMenu;
	}

	private class ScacchieraPanel extends JPanel {
		final List<CasellaPanel> caselleScacchiera;

		ScacchieraPanel() {
			super(new GridLayout(8, 8));
			this.caselleScacchiera = new ArrayList<CasellaPanel>();
			for (int i = 0; i < ScacchieraController.NUM_CASELLE; i++) {
				final CasellaPanel casellaPanel = new CasellaPanel(this, i);
				this.caselleScacchiera.add(casellaPanel);
				add(casellaPanel);
			}
			setPreferredSize(DIMENSIONE_PANNELLO_CONTROLLO);
			validate();
		}
	}

	private class CasellaPanel extends JPanel {

		private final int casellaId;

		CasellaPanel(final ScacchieraPanel pannelloDiControllo, final int casellaId) {
			super(new GridBagLayout());
			this.casellaId = casellaId;
			setPreferredSize(DIMENSIONE_PANNELLO_CASELLA);
			assegnaColoreCasella();
			assegnaIconaPezzoCasella(scacchiScacchiera);
			validate();
		}

		private void assegnaIconaPezzoCasella(final Scacchiera scacchiera) {
			this.removeAll();
			if (scacchiera.getCasella(this.casellaId).occupata()) {
				try {
					final BufferedImage img = ImageIO
							.read(new File(defaultImgPezzoPath
									+ scacchiera.getCasella(this.casellaId).getPezzo().getColorePezzo().toString()
											.substring(0, 1)
									+ scacchiera.getCasella(this.casellaId).getPezzo().toString() + ".gif"));
					add(new JLabel(new ImageIcon(img)));
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}

		private void assegnaColoreCasella() {
			if (ScacchieraController.RANGO_OTTO[this.casellaId] || ScacchieraController.RANGO_SEI[this.casellaId]
					|| ScacchieraController.RANGO_QUATTRO[this.casellaId]
					|| ScacchieraController.RANGO_DUE[this.casellaId]) {
				setBackground(this.casellaId % 2 == 0 ? coloreCasellaChiara : coloreCasellaScura);
			} else if (ScacchieraController.RANGO_SETTE[this.casellaId]
					|| ScacchieraController.RANGO_CINQUE[this.casellaId]
					|| ScacchieraController.RANGO_TRE[this.casellaId]
					|| ScacchieraController.RANGO_UNO[this.casellaId]) {
				setBackground(this.casellaId % 2 != 0 ? coloreCasellaChiara : coloreCasellaScura);
			}

		}

	}
}
