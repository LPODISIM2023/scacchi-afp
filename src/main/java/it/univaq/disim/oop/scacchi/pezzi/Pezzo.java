package it.univaq.disim.oop.scacchi.pezzi;

import java.util.Collection;

import it.univaq.disim.oop.scacchi.player.Colore;
import it.univaq.disim.oop.scacchi.scacchiera.Mossa;
import it.univaq.disim.oop.scacchi.scacchiera.Scacchiera;

public abstract class Pezzo {

	protected final TipoPezzo tipoPezzo;
	protected final int coordinatePezzo;
	protected final Colore colorePezzo;
	protected final boolean primaMossa;
	private final int cacehashCode;

	Pezzo(final TipoPezzo tipoPezzo, final int coordinatePezzo, final Colore colorePezzo) {
		this.tipoPezzo = tipoPezzo;
		this.colorePezzo = colorePezzo;
		this.coordinatePezzo = coordinatePezzo;
		this.primaMossa = false;
		this.cacehashCode = eseguiHashCode();
	}
	
	protected int eseguiHashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((colorePezzo == null) ? 0 : colorePezzo.hashCode());
		result = prime * result + coordinatePezzo;
		result = prime * result + (primaMossa ? 1231 : 1237);
		result = prime * result + ((tipoPezzo == null) ? 0 : tipoPezzo.hashCode());
		return result;
	};

	//equals pregenerati da eclips
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pezzo other = (Pezzo) obj;
		return coordinatePezzo == other.getCoordinatePezzo() && tipoPezzo == other.getTipoPezzo()
				&& colorePezzo == other.getColorePezzo() && primaMossa == other.primaMossa();
	}

	@Override
	public int hashCode() {
		return this.cacehashCode;
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
	
	//genera un nuovo pezzo in seguito a una mossa(movimento/cattura)
	public abstract Pezzo pezzoMosso(Mossa mossa);
	
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