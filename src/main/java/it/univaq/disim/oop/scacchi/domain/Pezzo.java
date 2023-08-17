package it.univaq.disim.oop.scacchi.domain;

import java.util.Collection;

public abstract class Pezzo {

	protected final TipoPezzo tipoPezzo;
	protected final int coordinatePezzo;
	protected final Colore colorePezzo;
	protected final boolean primaMossa;

	Pezzo(final TipoPezzo tipoPezzo, final int coordinatePezzo, final Colore colorePezzo) {
		this.tipoPezzo = tipoPezzo;
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
	
	public TipoPezzo getTipoPezzo() {
		return this.tipoPezzo;
	}
	
	// Dice quali mosse "legali" puo' effetturare un pezzo sulla scacchiera
	public abstract Collection<Mossa> calcolaMosseLegali(final Scacchiera scacchiera);
	
	public enum TipoPezzo {
		
		PEDONE("P"){
			@Override
			public boolean isRe() {
				return false;
			}
		},
		CAVALLO("C") {
			@Override
			public boolean isRe() {
				return false;
			}
		},
		ALFIERE("A") {
			@Override
			public boolean isRe() {
				return false;
			}
		},
		TORRE("T") {
			@Override
			public boolean isRe() {
				return false;
			}
		},	
		REGINA("Q") {
			@Override
			public boolean isRe() {
				return false;
			}
		},
		RE("K") {
			@Override
			public boolean isRe() {
				return true;
			}
		};		
		
		private String nomePezzo;
		
		TipoPezzo(final String nomePezzo) {
			this.nomePezzo = nomePezzo;
		}
		
		@Override
		public String toString() {
			return this.nomePezzo;
		}
		
		public abstract boolean isRe();
	}

}