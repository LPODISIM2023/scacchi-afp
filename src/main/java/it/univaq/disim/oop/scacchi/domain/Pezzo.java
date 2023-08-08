package it.univaq.disim.oop.scacchi.domain;

import java.util.Collection;

public abstract class Pezzo {

	protected final int coordinatePezzo;
	protected final Colore colorePezzo;
	protected final boolean primaMossa;

	Pezzo(final int coordinatePezzo, final Colore colorePezzo) {
		this.colorePezzo = colorePezzo;
		this.coordinatePezzo = coordinatePezzo;
		// TODO ci sono altre cose da inserire qui
		this.primaMossa = false;
	}
	
	public int getCoordinatePezzo() {
		return this.coordinatePezzo;
	}

	public Colore getColorePezzo() {
		return this.colorePezzo;
	}

	public boolean primaMossa() {
		return this.primaMossa;
	}

	// Dice quali mosse "legali" può effetturare un pezzo sulla scacchiera
	public abstract Collection<Mossa> calcolaMosseLegali(final Scacchiera scacchiera);
	
	public enum TipoPezzo {
		
		Pedone("P"),
		Cavallo("C"),
		Alfiere("A"),
		Torre("T"),
		Regina("Q"),
		Re("K");
		
		private String nomePezzo;
		
		TipoPezzo(final String nomePezzo) {
			this.nomePezzo = nomePezzo;
		}
		
		@Override
		public String toString() {
			return this.nomePezzo;
		}
	}

}