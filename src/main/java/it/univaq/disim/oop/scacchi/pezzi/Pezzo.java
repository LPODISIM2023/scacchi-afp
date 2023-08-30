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
	private final int cachedHashCode;

	Pezzo(final TipoPezzo tipoPezzo, final int coordinatePezzo, final Colore colorePezzo, final boolean primaMossa) {
		this.tipoPezzo = tipoPezzo;
		this.colorePezzo = colorePezzo;
		this.coordinatePezzo = coordinatePezzo;
		this.primaMossa = primaMossa;
		this.cachedHashCode = eseguiHashCode();
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

	// equals pregenerati da eclipse
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
		return this.cachedHashCode;
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
	
	public int getValorePezzo() {
		return this.tipoPezzo.getValorePezzo();
	}
	
	// Dice quali mosse "legali" puo' effetturare un pezzo sulla scacchiera
		public abstract Collection<Mossa> calcolaMosseLegali(final Scacchiera scacchiera);

	// genera un nuovo pezzo in seguito a una mossa(movimento/cattura)
	public abstract Pezzo pezzoMosso(Mossa mossa);

	public enum TipoPezzo {

		PEDONE("P", 100) {
			@Override
			public boolean isRe() {
				return false;
			}
		},
		CAVALLO("C", 300) {
			@Override
			public boolean isRe() {
				return false;
			}
		},
		ALFIERE("A", 300) {
			@Override
			public boolean isRe() {
				return false;
			}
		},
		TORRE("T", 500) {
			@Override
			public boolean isRe() {
				return false;
			}
		},
		REGINA("Q", 900) {
			@Override
			public boolean isRe() {
				return false;
			}
		},
		RE("K", 10000) {
			@Override
			public boolean isRe() {
				return true;
			}
		};

		private String nomePezzo;
		private int valorePezzo;

		TipoPezzo(final String nomePezzo, final int valorePezzo) {
			this.nomePezzo = nomePezzo;
			this.valorePezzo = valorePezzo;
		}

		@Override
		public String toString() {
			return this.nomePezzo;
		}
		
		public int getValorePezzo() {
			return this.valorePezzo;
		}

		public abstract boolean isRe();
	}

}