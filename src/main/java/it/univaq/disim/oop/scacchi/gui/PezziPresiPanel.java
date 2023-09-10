package it.univaq.disim.oop.scacchi.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;

import com.google.common.primitives.Ints;

import it.univaq.disim.oop.scacchi.gui.Tabella.RegistroMosse;
import it.univaq.disim.oop.scacchi.pezzi.Pezzo;
import it.univaq.disim.oop.scacchi.scacchiera.Mossa;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PezziPresiPanel extends JPanel {

	private final JPanel pannelloNord;
	private final JPanel pannelloSud;

	private static final Color COLORE_PANNELLO = Color.BLUE;
	private static final Dimension DIMENSIONE_PEZZI_PRESI = new Dimension(40, 80);
	private static final EtchedBorder BORDO_PANNELLO = new EtchedBorder(EtchedBorder.RAISED);
	
	int pezziPresiTot;

	public PezziPresiPanel() {
		super(new BorderLayout());
		setBackground(COLORE_PANNELLO);
		setBorder(BORDO_PANNELLO);
		this.pannelloNord = new JPanel(new GridLayout(8, 2));
		this.pannelloSud = new JPanel(new GridLayout(8, 2));
		this.pannelloNord.setBackground(COLORE_PANNELLO);
		this.pannelloSud.setBackground(COLORE_PANNELLO);
		this.add(this.pannelloNord, BorderLayout.NORTH);
		this.add(this.pannelloSud, BorderLayout.SOUTH);
		setPreferredSize(DIMENSIONE_PEZZI_PRESI);
	}

	public void redo(final RegistroMosse registroMosse) {
		this.pannelloSud.removeAll();
		this.pannelloNord.removeAll();

		final List<Pezzo> pezziPresiBianchi = new ArrayList<Pezzo>();
		final List<Pezzo> pezziPresiNeri = new ArrayList<Pezzo>();

		for (final Mossa mossa : registroMosse.getMosse()) {
			if (mossa.Attacca()) {
				final Pezzo pezzoPreso = mossa.getPezzoAttaccante();
				if (pezzoPreso.getColorePezzo().isBianco()) {
					pezziPresiBianchi.add(pezzoPreso);
				} else if (pezzoPreso.getColorePezzo().isNero()) {
					pezziPresiNeri.add(pezzoPreso);
				} else {
					throw new RuntimeException("non dovrebbe arrivare qui");
				}
			}
		}

		Collections.sort(pezziPresiBianchi, new Comparator<Pezzo>() {
			public int compare(Pezzo p1, Pezzo p2) {
				return Ints.compare(p1.getValorePezzo(), p2.getValorePezzo());
			}
		});

		Collections.sort(pezziPresiNeri, new Comparator<Pezzo>() {
			public int compare(Pezzo p1, Pezzo p2) {
				return Ints.compare(p1.getValorePezzo(), p2.getValorePezzo());
			}
		});

		for (final Pezzo pezzoPreso : pezziPresiBianchi) {
			try {
				final BufferedImage image = ImageIO
						.read(new File("art/pezzi/" + pezzoPreso.getColorePezzo().toString().substring(0, 1) + ""
								+ pezzoPreso.toString() + ".gif"));
				final ImageIcon ic = new ImageIcon(image);
				final JLabel imageLabel = new JLabel(new ImageIcon(ic.getImage()
						.getScaledInstance(ic.getIconWidth() - 15, ic.getIconWidth() - 15, Image.SCALE_SMOOTH)));
				this.pannelloSud.add(imageLabel);
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
		
		for (final Pezzo pezzoPreso : pezziPresiNeri) {
			try {
				final BufferedImage image = ImageIO.read(new File("art/pezzi/"
						+ pezzoPreso.getColorePezzo().toString().substring(0, 1) + "" + pezzoPreso.toString()
						+ ".gif"));
				final ImageIcon ic = new ImageIcon(image);
				final JLabel imageLabel = new JLabel(new ImageIcon(ic.getImage().getScaledInstance(
                        ic.getIconWidth() - 15, ic.getIconWidth() - 15, Image.SCALE_SMOOTH)));
				this.pannelloNord.add(imageLabel);
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}

		validate();
		
		pezziPresiTot = pezziPresiBianchi.size() + pezziPresiNeri.size();
	}
	
	public int getPezziPresiTot() {
				
		return pezziPresiTot;
	}

}
