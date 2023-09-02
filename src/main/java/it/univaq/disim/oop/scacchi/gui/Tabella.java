package it.univaq.disim.oop.scacchi.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
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
import java.util.Observable;
import java.util.Observer;

import static javax.swing.JFrame.setDefaultLookAndFeelDecorated;
import it.univaq.disim.oop.scacchi.controller.ScacchieraController;
import it.univaq.disim.oop.scacchi.pezzi.Pezzo;
import it.univaq.disim.oop.scacchi.player.Giocatore;
import it.univaq.disim.oop.scacchi.scacchiera.Casella;
import it.univaq.disim.oop.scacchi.scacchiera.Mossa;
import it.univaq.disim.oop.scacchi.scacchiera.Mossa.MossaFactory;
import it.univaq.disim.oop.scacchi.scacchiera.Scacchiera;
import it.univaq.disim.oop.scacchi.scacchiera.TransizioneMossa;

public class Tabella extends Observable {

	private final JFrame gameFrame;
	private final StoricoGiocoPanel storicoGiocoPanel;
	private final PezziPresiPanel pezziPresiPanel;
	private final DebugPanel debugPanel;
	private final ScacchieraPanel scacchieraPanel;
	private final RegistroMosse registroMosse;
	private final GiocoSetup giocoSetup;
	private Scacchiera scacchiScacchiera;
	private Mossa computerMossa;
	private Casella provenienzaCasella;
	private Casella destinazioneCasella;
	private Pezzo personaMuovePezzo;
	private DirezioneScacchiera direzioneScacchiera;
	private String iconaPezzoPath;
	private boolean evidenziaMosseLegali;
	private boolean usaLibro;
	private final static Dimension DIMENSIONE_FRAME_ESTERNO = new Dimension(600, 600);
	private final static Dimension DIMENSIONE_PANNELLO_CONTROLLO = new Dimension(400, 350);
	private final static Dimension DIMENSIONE_PANNELLO_CASELLA = new Dimension(10, 10);
	private static String defaultImgPezzoPath = "art/pezzi/";

	private Color coloreCasellaChiara = Color.decode("#FFFACD");
	private Color coloreCasellaScura = Color.decode("#593E1A");

	private static final Tabella INSTANCE = new Tabella();

	private Tabella() {
		this.gameFrame = new JFrame("JScacchi");
		final JMenuBar tabellaMenuBar = creaTabellaMenuBar();
		this.gameFrame.setJMenuBar(tabellaMenuBar);
		this.gameFrame.setLayout(new BorderLayout());
		this.scacchiScacchiera = Scacchiera.creaScacchieraStandard();
		this.direzioneScacchiera = DirezioneScacchiera.NORMALE;
		this.evidenziaMosseLegali = false;
		this.usaLibro = false;
		this.setIconaPezzoPath("art/pezzi/");
		this.storicoGiocoPanel = new StoricoGiocoPanel();
		this.debugPanel = new DebugPanel();
		this.pezziPresiPanel = new PezziPresiPanel();
		this.scacchieraPanel = new ScacchieraPanel();
		this.registroMosse = new RegistroMosse();
		// this.addObserver(new TabellaGiocoAIWatcher());
		this.giocoSetup = new GiocoSetup(this.gameFrame, true);
		this.gameFrame.add(this.pezziPresiPanel, BorderLayout.WEST);
		this.gameFrame.add(this.scacchieraPanel, BorderLayout.CENTER);
		this.gameFrame.add(this.storicoGiocoPanel, BorderLayout.EAST);
		this.gameFrame.add(debugPanel, BorderLayout.SOUTH);
		setDefaultLookAndFeelDecorated(true);
		this.gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.gameFrame.setSize(DIMENSIONE_FRAME_ESTERNO);
		center(this.gameFrame);
		this.gameFrame.setVisible(true);
	}

	public static Tabella get() {
		return INSTANCE;
	}

	private JFrame getGameFrame() {
		return this.gameFrame;
	}

	private Scacchiera getGiocoBoard() {
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

	private DebugPanel getDebugPanel() {
		return this.debugPanel;
	}

	private GiocoSetup getGiocoSetup() {
		return this.giocoSetup;
	}

	private boolean getEvidenziaMosseLegali() {
		return this.evidenziaMosseLegali;
	}

	private boolean getUsaLibro() {
		return this.usaLibro;
	}

	public void mostra() {
		Tabella.get().getRegistroMosse().clear();
		Tabella.get().getStoricoGiocoPanel().redo(scacchiScacchiera, Tabella.get().getRegistroMosse());
		Tabella.get().getPezziPresiPanel().redo(Tabella.get().getRegistroMosse());
		Tabella.get().getScacchieraPanel().disegnaScacchiera(Tabella.get().getGiocoBoard());
		Tabella.get().getDebugPanel().redo();
	}

	private JMenuBar creaTabellaMenuBar() {
		final JMenuBar tabellaMenuBar = new JMenuBar();
		tabellaMenuBar.add(creaFileMenu());
		tabellaMenuBar.add(creaMenuPreferenze());
		tabellaMenuBar.add(creaMenuOpzioni());
		return tabellaMenuBar;
	}

	private static void center(final JFrame frame) {
		final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		final int w = frame.getSize().width;
		final int h = frame.getSize().height;
		final int x = (dim.width - w) / 2;
		final int y = (dim.height - h) / 2;
		frame.setLocation(x, y);
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

	private static String playerInfo(final Giocatore giocatore) {
		return ("Il Giocatore è: " + giocatore.getColore() + "\nmosse legali (" + giocatore.getMosseLegali().size()
				+ ") = " + giocatore.getMosseLegali() + "\nèinScacco = " + giocatore.isInScacco()
				+ "\nèinScaccoMatto = " + giocatore.isInScaccoMatto()) + "\n";
	}

	private void aggiornaGiocoScacchiera(final Scacchiera scacchiera) {
		this.scacchiScacchiera = scacchiera;
	}

	private void updateComputerMove(final Mossa mossa) {
		this.computerMossa = mossa;
	}

	private void AnnullamentoTutteMosse() {
		for (int i = Tabella.get().getRegistroMosse().size() - 1; i >= 0; i--) {
			final Mossa ultimaMossa = Tabella.get().getRegistroMosse()
					.rimuoviMossa(Tabella.get().getRegistroMosse().size() - 1);
			this.scacchiScacchiera = this.scacchiScacchiera.giocatoreAttuale().mossaNonFatta(ultimaMossa)
					.getInScacchiera();
		}
		this.computerMossa = null;
		Tabella.get().getRegistroMosse().clear();
		Tabella.get().getStoricoGiocoPanel().redo(scacchiScacchiera, Tabella.get().getRegistroMosse());
		Tabella.get().getPezziPresiPanel().redo(Tabella.get().getRegistroMosse());
		Tabella.get().getScacchieraPanel().disegnaScacchiera(scacchiScacchiera);
		Tabella.get().getDebugPanel().redo();
	}

	private JMenu creaMenuOpzioni() {
		final JMenu opzioniMenu = new JMenu("Opzioni");
		opzioniMenu.setMnemonic(KeyEvent.VK_O);

		final JMenuItem resetMenuItem = new JMenuItem("Nuova Partita", KeyEvent.VK_P);
		resetMenuItem.addActionListener(e -> AnnullamentoTutteMosse());
		opzioniMenu.add(resetMenuItem);

		final JMenuItem legalMovesMenuItem = new JMenuItem("Stato Corrente", KeyEvent.VK_L);
		legalMovesMenuItem.addActionListener(e -> {
			System.out.println(scacchiScacchiera.getPezziBianchi());
			System.out.println(scacchiScacchiera.getPezziNeri());
			System.out.println(playerInfo(scacchiScacchiera.giocatoreAttuale()));
			System.out.println(playerInfo(scacchiScacchiera.giocatoreAttuale().getAvversario()));
		});
		opzioniMenu.add(legalMovesMenuItem);

		final JMenuItem undoMoveMenuItem = new JMenuItem("Ritorna all'ultima mossa", KeyEvent.VK_M);
		undoMoveMenuItem.addActionListener(e -> {
			if (Tabella.get().getRegistroMosse().size() > 0) {
				undoUltimaMossa();
			}
		});
		opzioniMenu.add(undoMoveMenuItem);

		final JMenuItem setupGiocoMenuItem = new JMenuItem("Setup Gioco");
		setupGiocoMenuItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Tabella.get().getGiocoSetup().promptUser();
				// Tabella.get().setupUpdate(Tabella.get().getGiocoSetup());
			}
		});

		opzioniMenu.add(setupGiocoMenuItem);

		return opzioniMenu;

	}

	private void undoUltimaMossa() {
		final Mossa ultimaMossa = Tabella.get().getRegistroMosse().rimuoviMossa(Tabella.get().getRegistroMosse().size() - 1);
		this.scacchiScacchiera = this.scacchiScacchiera.giocatoreAttuale().mossaNonFatta(ultimaMossa).getInScacchiera();
		this.computerMossa = null;
		Tabella.get().getRegistroMosse().rimuoviMossa(ultimaMossa);
		Tabella.get().getStoricoGiocoPanel().redo(scacchiScacchiera, Tabella.get().getRegistroMosse());
		Tabella.get().getPezziPresiPanel().redo(Tabella.get().getRegistroMosse());
		Tabella.get().getScacchieraPanel().disegnaScacchiera(scacchiScacchiera);
		Tabella.get().getDebugPanel().redo();
	}

	public String getIconaPezzoPath() {
		return iconaPezzoPath;
	}

	public void setIconaPezzoPath(String iconaPezzoPath) {
		this.iconaPezzoPath = iconaPezzoPath;
	}

	private static class TabellaGiocoAIWatcher implements Observer {

		public void update(final Observable o, final Object arg) {

			if (Tabella.get().getGiocoSetup().isAIGIocatore(Tabella.get().getGiocoBoard().giocatoreAttuale())
					&& !Tabella.get().getGiocoBoard().giocatoreAttuale().isInScaccoMatto()
					&& !Tabella.get().getGiocoBoard().giocatoreAttuale().isInStallo()) {
				// System.out.println(Tabella.get().getGiocoBoard().giocatoreAttuale() + " è
				// impostato con l'IA, penso....");
				// Gruppo di riflessione sull'intelligenza artificiale
				// final AIThinkTank thinkTank = new AIThinkTank();
				// thinkTank.execute();
			}

			if (Tabella.get().getGiocoBoard().giocatoreAttuale().isInScaccoMatto()) {
				System.out.println("fine, " + Tabella.get().getGiocoBoard().giocatoreAttuale() + "è Scacco Matto!");
			}

			if (Tabella.get().getGiocoBoard().giocatoreAttuale().isInStallo()) {
				System.out.println("fine, " + Tabella.get().getGiocoBoard().giocatoreAttuale() + "è in Stallo!");
			}
		}

	}

	enum GiocatoreTipo {
		UMANO, COMPUTER
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
			setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			setBackground(Color.decode("#8B4726"));
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
			evidenziaBordiCasella(scacchiScacchiera);
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
							final TransizioneMossa transizione = scacchiScacchiera.giocatoreAttuale().mossaFatta(mossa);
							if (transizione.getStatoMossa().isFatto()) {
								scacchiScacchiera = transizione.getInScacchiera();
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
								debugPanel.redo();
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
			evidenziaBordiCasella(scacchiera);
			evidenziaDatiLegali(scacchiera);
			validate();
			repaint();
		}

		void setColoreCasellaChiara(final Color color) {
			coloreCasellaChiara = color;
		}

		void setColoreCasellaScura(final Color color) {
			coloreCasellaScura = color;
		}

		private void evidenziaBordiCasella(final Scacchiera scacchiera) {
			if (personaMuovePezzo != null
					&& personaMuovePezzo.getColorePezzo() == scacchiera.giocatoreAttuale().getColore()
					&& personaMuovePezzo.getCoordinatePezzo() == this.casellaId) {
				setBorder(BorderFactory.createLineBorder(Color.cyan));
			} else {
				setBorder(BorderFactory.createLineBorder(Color.GRAY));
			}
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
