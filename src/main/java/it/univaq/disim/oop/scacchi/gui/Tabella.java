package it.univaq.disim.oop.scacchi.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.google.common.collect.Lists;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import it.univaq.disim.oop.scacchi.controller.ScacchieraController;
import it.univaq.disim.oop.scacchi.pezzi.Pezzo;
import it.univaq.disim.oop.scacchi.player.TransizioneMossa;
import it.univaq.disim.oop.scacchi.scacchiera.Casella;
import it.univaq.disim.oop.scacchi.scacchiera.Mossa;
import it.univaq.disim.oop.scacchi.scacchiera.Scacchiera;

public class Tabella {

	private final JFrame gameFrame;
	private final StoricoGiocoPanel storicoGiocoPanel;
	private final PezziPresiPanel pezziPresiPanel;
	private final ScacchieraPanel scacchieraPanel;
	private final RegistroMosse registroMosse;
	private Scacchiera scacchiScacchiera;

	private Casella provenienzaCasella;
	private Casella destinazioneCasella;
	private Pezzo personaMuovePezzo;
	private DirezioneScacchiera direzioneScacchiera;

	private boolean evidenziaMosseLegali;

	private final static Dimension DIMENSIONE_FRAME_ESTERNO = new Dimension(600, 600);
	private final static Dimension DIMENSIONE_PANNELLO_CONTROLLO = new Dimension(400, 350);
	private final static Dimension DIMENSIONE_PANNELLO_CASELLA = new Dimension(10, 10);
	private static String defaultImgPezzoPath = "art/pezzi/";

	private Color coloreCasellaChiara = Color.decode("#FFFACD");
	private Color coloreCasellaScura = Color.decode("#593E1A");

	public Tabella() {
		this.gameFrame = new JFrame("JScacchi");
		final JMenuBar tabellaMenuBar = creaTabellaMenuBar();
		this.gameFrame.setJMenuBar(tabellaMenuBar);
		this.gameFrame.setLayout(new BorderLayout());
		this.scacchiScacchiera = Scacchiera.creaScacchieraStandard();
		this.direzioneScacchiera = DirezioneScacchiera.NORMALE;
		this.evidenziaMosseLegali = false;
		this.storicoGiocoPanel = new StoricoGiocoPanel();
		this.pezziPresiPanel = new PezziPresiPanel();
		this.scacchieraPanel = new ScacchieraPanel();
		this.registroMosse = new RegistroMosse();

		this.gameFrame.add(this.pezziPresiPanel, BorderLayout.WEST);
		this.gameFrame.add(this.scacchieraPanel, BorderLayout.CENTER);
		this.gameFrame.add(this.storicoGiocoPanel, BorderLayout.EAST);
		this.gameFrame.setSize(DIMENSIONE_FRAME_ESTERNO);
		this.gameFrame.setVisible(true);
	}

	private JFrame getGameFrame() {
		return this.gameFrame;
	}

	private Scacchiera getGameBoard() {
		return this.scacchiScacchiera;
	}

	private RegistroMosse getRegistroMosse() {
		return this.registroMosse;
	}

	private ScacchieraPanel getScacchieraPanel() {
		return this.scacchieraPanel;
	}

	private StoricoGiocoPanel getStoricoGiocoPanel() {
		return this.storicoGiocoPanel;
	}

	private PezziPresiPanel getPezziPresiPanel() {
		return this.pezziPresiPanel;
	}

	private boolean getEvidenziaMosseLegali() {
		return this.evidenziaMosseLegali;
	}

	private JMenuBar creaTabellaMenuBar() {
		final JMenuBar tabellaMenuBar = new JMenuBar();
		tabellaMenuBar.add(creaFileMenu());
		tabellaMenuBar.add(creaMenuPreferenze());
		return tabellaMenuBar;
	}

	// Menu File
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

	// Menu Preferenze
	private JMenu creaMenuPreferenze() {
		final JMenu preferencesMenu = new JMenu("Preferenze");
		final JMenuItem flipBoardMenuItem = new JMenuItem("capovolgere scacchiera");
		flipBoardMenuItem.addActionListener(new ActionListener() {

			public void actionPerformed(final ActionEvent e) {
				direzioneScacchiera = direzioneScacchiera.opposite();
				scacchieraPanel.disegnaScacchiera(scacchiScacchiera);
			}
		});
		preferencesMenu.add(flipBoardMenuItem);

		preferencesMenu.addSeparator();

		final JCheckBoxMenuItem evidenziaDatiLegaliCheckBox = new JCheckBoxMenuItem("Evidenzia Mossa Legale", false);

		evidenziaDatiLegaliCheckBox.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				evidenziaMosseLegali = evidenziaDatiLegaliCheckBox.isSelected();
			}
		});

		preferencesMenu.add(evidenziaDatiLegaliCheckBox);

		return preferencesMenu;
	}

	enum DirezioneScacchiera {
		NORMALE {

			@Override
			List<CasellaPanel> traverse(final List<CasellaPanel> scacchiScacchiera) {
				return scacchiScacchiera;
			}

			@Override
			DirezioneScacchiera opposite() {
				return RIBALTATO;
			}
		},
		RIBALTATO {
			@Override
			List<CasellaPanel> traverse(final List<CasellaPanel> scacchiScacchiera) {
				return Lists.reverse(scacchiScacchiera);
			}

			@Override
			DirezioneScacchiera opposite() {
				return NORMALE;
			}
		};

		abstract List<CasellaPanel> traverse(final List<CasellaPanel> scacchiScacchiera);

		abstract DirezioneScacchiera opposite();

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

		public void disegnaScacchiera(final Scacchiera scacchiera) {
			removeAll();
			for (final CasellaPanel casellaPanel : direzioneScacchiera.traverse(caselleScacchiera)) {
				casellaPanel.disegnaCasella(scacchiera);
				add(casellaPanel);
			}
			validate();
			repaint();
		}
	}

	public static class RegistroMosse {
		private final List<Mossa> mosse;

		RegistroMosse() {
			this.mosse = new ArrayList<Mossa>();
		}

		public List<Mossa> getMosse() {
			return this.mosse;
		}

		public void addMosse(final Mossa mossa) {
			this.mosse.add(mossa);
		}

		public int size() {
			return this.mosse.size();
		}

		public void clear() {
			this.mosse.clear();
		}

		public Mossa rimuoviMossa(int index) {
			return this.mosse.remove(index);
		}

		public boolean rimuoviMossa(final Mossa mossa) {
			return this.mosse.remove(mossa);
		}

	}

	private class CasellaPanel extends JPanel {

		private final int casellaId;

		CasellaPanel(final ScacchieraPanel scacchieraPanel, final int casellaId) {
			super(new GridBagLayout());
			this.casellaId = casellaId;
			setPreferredSize(DIMENSIONE_PANNELLO_CASELLA);
			assegnaColoreCasella();
			assegnaIconaPezzoCasella(scacchiScacchiera);

			addMouseListener(new MouseListener() {

				public void mouseClicked(final MouseEvent e) {

					if (SwingUtilities.isRightMouseButton(e)) {
						provenienzaCasella = null;
						destinazioneCasella = null;
						personaMuovePezzo = null;
					} else if (SwingUtilities.isLeftMouseButton(e)) {
						if (provenienzaCasella == null) {
							provenienzaCasella = scacchiScacchiera.getCasella(casellaId);
							personaMuovePezzo = provenienzaCasella.getPezzo();
							if (personaMuovePezzo == null) {
								provenienzaCasella = null;
							}
						} else {
							destinazioneCasella = scacchiScacchiera.getCasella(casellaId);
							final Mossa mossa = Mossa.MossaFactory.creaMossa(scacchiScacchiera,
									provenienzaCasella.getCasella(), destinazioneCasella.getCasella());
							final TransizioneMossa transizione = scacchiScacchiera.giocatoreAttuale().fareMossa(mossa);
							if (transizione.getStatoMossa().isFatto()) {
								scacchiScacchiera = transizione.getTransizioneScacchiera();
								registroMosse.addMosse(mossa);
							}
							provenienzaCasella = null;
							destinazioneCasella = null;
							personaMuovePezzo = null;
						}
						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								storicoGiocoPanel.redo(scacchiScacchiera, registroMosse);
								pezziPresiPanel.redo(registroMosse);
								scacchieraPanel.disegnaScacchiera(scacchiScacchiera);
							}
						});
					}
				}

				public void mousePressed(final MouseEvent e) {

				}

				public void mouseReleased(final MouseEvent e) {

				}

				public void mouseEntered(final MouseEvent e) {

				}

				public void mouseExited(final MouseEvent e) {

				}
			});

			validate();

		}

		public void disegnaCasella(final Scacchiera scacchiera) {
			assegnaColoreCasella();
			assegnaIconaPezzoCasella(scacchiera);
			evidenziaDatiLegali(scacchiera);
			validate();
			repaint();
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

		private void evidenziaDatiLegali(final Scacchiera scacchiera) {
			if (evidenziaMosseLegali) {
				for (final Mossa mossa : mossePezziLegali(scacchiera)) {
					if (mossa.getCoordinateDestinazione() == this.casellaId) {
						try {
							add(new JLabel(new ImageIcon(ImageIO.read(new File("art/misc/green_dot.png")))));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}

		private Collection<Mossa> mossePezziLegali(final Scacchiera scacchiera) {
			if (personaMuovePezzo != null
					&& personaMuovePezzo.getColorePezzo() == scacchiera.giocatoreAttuale().getColore()) {
				return personaMuovePezzo.calcolaMosseLegali(scacchiera);
			}
			return Collections.emptyList();
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
