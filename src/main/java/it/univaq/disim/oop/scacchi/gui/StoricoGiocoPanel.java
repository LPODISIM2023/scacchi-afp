package it.univaq.disim.oop.scacchi.gui;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import it.univaq.disim.oop.scacchi.gui.Tabella.RegistroMosse;
import it.univaq.disim.oop.scacchi.scacchiera.Mossa;
import it.univaq.disim.oop.scacchi.scacchiera.Scacchiera;

public class StoricoGiocoPanel extends JPanel {

	private final DataModel model;
	private final JScrollPane scrollPane;
	private static final Dimension DIMENSIONE_PANNELLO_STORICO = new Dimension(100, 400);

	StoricoGiocoPanel() {
		this.setLayout(new BorderLayout());
		this.model = new DataModel();
		final JTable table = new JTable(model);
		table.setRowHeight(15);
		this.scrollPane = new JScrollPane(table);
		scrollPane.setColumnHeaderView(table.getTableHeader());
		scrollPane.setPreferredSize(DIMENSIONE_PANNELLO_STORICO);
		this.add(scrollPane, BorderLayout.CENTER);
		this.setVisible(true);
	}

	void redo(final Scacchiera scacchiera, final RegistroMosse storicoMosse) {
		int rigaCorrente = 0;
		this.model.clear();
		for (final Mossa mosse : storicoMosse.getMosse()) {
			final String testoMossa = mosse.toString();
			if (mosse.getPezzoMosso().getColorePezzo().isBianco()) {
				this.model.setValueAt(testoMossa, rigaCorrente, 0);
			} else if (mosse.getPezzoMosso().getColorePezzo().isNero()) {
				this.model.setValueAt(testoMossa, rigaCorrente, 1);
				rigaCorrente++;
			}
		}

		if (storicoMosse.getMosse().size() > 0) {
			final Mossa ultimaMossa = storicoMosse.getMosse().get(storicoMosse.size() - 1);
			final String testoMossa = ultimaMossa.toString();
			if (ultimaMossa.getPezzoMosso().getColorePezzo().isBianco()) {
				this.model.setValueAt(testoMossa + calcolaCheckECheckMateHash(scacchiera), rigaCorrente, 0);
			} else if (ultimaMossa.getPezzoMosso().getColorePezzo().isNero()) {
				this.model.setValueAt(testoMossa + calcolaCheckECheckMateHash(scacchiera), rigaCorrente - 1, 1);
			}
		}

		final JScrollBar vertical = scrollPane.getVerticalScrollBar();
		vertical.setValue(vertical.getMaximum());

	}

	// calcolo Scacco e Scacco Matto
	private String calcolaCheckECheckMateHash(final Scacchiera scacchiera) {
		if (scacchiera.giocatoreAttuale().isInScaccoMatto()) {
			return "#";
		} else if (scacchiera.giocatoreAttuale().isInScacco()) {
			return "+";
		}
		return "";
	}

	private static class Riga {
		private String mossaBianco;
		private String mossaNero;

		Riga() {
		}

		public String getMossaBianco() {
			return this.mossaBianco;
		}

		public String getMossaNero() {
			return this.mossaNero;
		}

		public void setMossaBianco(final String mossa) {
			this.mossaBianco = mossa;
		}

		public void setMossaNero(final String mossa) {
			this.mossaNero = mossa;
		}

	}

	private static class DataModel extends DefaultTableModel {

		private final ArrayList<Riga> valori;
		private static final String[] NOMI = { "Bianco", "Nero" };

		DataModel() {
			this.valori = new ArrayList<>();
		}

		public void clear() {
			this.valori.clear();
			setRowCount(0);
		}
		@Override
		public int getRowCount() {
			if (this.valori == null) {
				return 0;
			}
			return this.valori.size();
		}
		@Override
		public int getColumnCount() {
			return NOMI.length;
		}
		@Override
		public Object getValueAt(final int riga, final int colonna) {
			final Riga rigaCorrente = this.valori.get(riga);
			if (colonna == 0) {
				return rigaCorrente.getMossaBianco();
			} else if (colonna == 1) {
				return rigaCorrente.getMossaNero();
			}
			return null;
		}
		@Override
		public void setValueAt(final Object unValore, final int riga, final int colonna) {
			final Riga rigaCorrente;
			if (this.valori.size() <= riga) {
				rigaCorrente = new Riga();
				this.valori.add(rigaCorrente);
			} else {
				rigaCorrente = this.valori.get(riga);
			}
			if (colonna == 0) {
				rigaCorrente.setMossaBianco((String) unValore);
				fireTableRowsUpdated(riga, riga);
			} else if (colonna == 1) {
				rigaCorrente.setMossaNero((String) unValore);
				fireTableCellUpdated(riga, colonna);
			}
		}
		@Override
		public Class<?> getColumnClass(final int colonna) {
			return Mossa.class;
		}
		@Override
		public String getColumnName(final int colonna) {
			return NOMI[colonna];
		}

	}

}
