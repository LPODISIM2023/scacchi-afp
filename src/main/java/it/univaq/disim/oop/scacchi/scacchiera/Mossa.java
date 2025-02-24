package it.univaq.disim.oop.scacchi.scacchiera;

import it.univaq.disim.oop.scacchi.controller.ScacchieraController;
import it.univaq.disim.oop.scacchi.pezzi.Pedone;
import it.univaq.disim.oop.scacchi.pezzi.Pezzo;
import it.univaq.disim.oop.scacchi.scacchiera.Scacchiera.*;

public abstract class Mossa {

	protected final Scacchiera scacchiera;
	protected final int coordinateDestinazione;
	protected final Pezzo pezzoMosso;
	protected final boolean primaMossa;

	public static final Mossa PASSA = new Passa();

	private Mossa(final Scacchiera scacchiera, final Pezzo pezzoMosso, final int coordinateDestinazione) {
		this.scacchiera = scacchiera;
		this.pezzoMosso = pezzoMosso;
		this.coordinateDestinazione = coordinateDestinazione;
		this.primaMossa = pezzoMosso.primaMossa();
	}

	private Mossa(final Scacchiera scacchiera, final int coordinateDestinazione) {
		this.scacchiera = scacchiera;
		this.coordinateDestinazione = coordinateDestinazione;
		this.pezzoMosso = null;
		this.primaMossa = false;
	}

	@Override
	public int hashCode() {
		int risultato = 1;
		risultato = 31 * risultato + this.coordinateDestinazione;
		risultato = 31 * risultato + this.pezzoMosso.hashCode();
		risultato = 31 * risultato + this.pezzoMosso.getCoordinatePezzo();
		risultato = risultato + (primaMossa ? 1 : 0);
		return risultato;
	}

	@Override
	public boolean equals(final Object altra) {
		if (this == altra) {
			return true;
		}
		if (!(altra instanceof Mossa)) {
			return false;
		}
		final Mossa altraMossa = (Mossa) altra;
		return getCoordinateAttuali() == altraMossa.getCoordinateAttuali()
				&& getCoordinateDestinazione() == altraMossa.getCoordinateDestinazione()
				&& getPezzoMosso().equals(altraMossa.getPezzoMosso());
	}

	public int getCoordinateAttuali() {
		return this.getPezzoMosso().getCoordinatePezzo();
	}

	public int getCoordinateDestinazione() {
		return this.coordinateDestinazione;
	}

	public Pezzo getPezzoMosso() {
		return this.pezzoMosso;
	}

	public boolean Attacca() {
		return false;
	}

	public Pezzo getPezzoAttaccante() {
		return null;
	}

	public Scacchiera esegui() {

		final Costruttore costruttore = new Costruttore();

		this.scacchiera.giocatoreAttuale().getPezziAttivi().stream().filter(pezzo -> !this.pezzoMosso.equals(pezzo))
				.forEach(costruttore::setPezzo);
		this.scacchiera.giocatoreAttuale().getAvversario().getPezziAttivi().forEach(costruttore::setPezzo);
		costruttore.setPezzo(this.pezzoMosso.pezzoMosso(this));
		costruttore.setMossaFatta(this.scacchiera.giocatoreAttuale().getAvversario().getColore());
		costruttore.setTransizioneMossa(this);
		return costruttore.crea();

	}

	public Scacchiera undo() {
		final Scacchiera.Costruttore costruttore = new Costruttore();
		this.scacchiera.getPezziTotali().forEach(costruttore::setPezzo);
		costruttore.setMossaFatta(this.scacchiera.giocatoreAttuale().getColore());
		return costruttore.crea();
	}
	
	
	@Override
	public String toString() {
		return "Mossa [scacchiera=" + scacchiera + ", coordinateDestinazione=" + coordinateDestinazione
				+ ", pezzoMosso=" + pezzoMosso + ", primaMossa=" + primaMossa + "]";
	}


	public enum StatoMossa {
		FATTO {
			@Override
			public boolean isFatto() {
				return true;
			}
		},
		MOSSA_ILLEGALE {
			@Override
			public boolean isFatto() {
				return false;
			}
		},
		LASCIA_GIOCATORE_IN_SCACCO {
			@Override
			public boolean isFatto() {
				return false;
			}
		};

		public abstract boolean isFatto();
	}

	public static final class GrandeMossa extends Mossa {

		public GrandeMossa(final Scacchiera scacchiera, final Pezzo pezzoMosso, final int coordinateDestinazione) {
			super(scacchiera, pezzoMosso, coordinateDestinazione);
		}

		@Override
		public boolean equals(final Object obj) {
			return this == obj || obj instanceof GrandeMossa && super.equals(obj);
		}

		@Override
		public String toString() {
			return pezzoMosso.getTipoPezzo().toString()
					+ ScacchieraController.getPosizioneInCoordinate(this.coordinateDestinazione);
		}

	}

	public static class MossaAttacco extends Mossa {

		final Pezzo pezzoAttaccante;

		public MossaAttacco(final Scacchiera scacchiera, final Pezzo pezzoMosso, final int coordinateDestinazione,
				final Pezzo pezzoAttaccante) {
			super(scacchiera, pezzoMosso, coordinateDestinazione);
			this.pezzoAttaccante = pezzoAttaccante;
		}

		@Override
		public int hashCode() {
			return this.pezzoAttaccante.hashCode() + super.hashCode();
		}

		@Override
		public boolean equals(final Object obj) {
			if (this == obj) {
				return true;
			}
			if (!(obj instanceof MossaAttacco)) {
				return false;
			}
			final MossaAttacco a1 = (MossaAttacco) obj;
			return super.equals(a1) && this.getPezzoAttaccante().equals(a1.getPezzoAttaccante());
		}

		@Override
		public boolean Attacca() {
			return true;
		}

		@Override
		public Pezzo getPezzoAttaccante() {
			return this.pezzoAttaccante;
		}
	}

	public static class GrandeMossaAttacco extends MossaAttacco {

		public GrandeMossaAttacco(final Scacchiera scacchiera, final Pezzo pezzoMosso, final int coordinateDestinazione,
				final Pezzo pezzoAttaccante) {
			super(scacchiera, pezzoMosso, coordinateDestinazione, pezzoAttaccante);
		}

		@Override
		public boolean equals(final Object obj) {
			return this == obj || obj instanceof GrandeMossaAttacco && super.equals(obj);
		}

		@Override
		public String toString() {
			return pezzoMosso.getTipoPezzo().toString()
					+ ScacchieraController.getPosizioneInCoordinate(this.coordinateDestinazione);
		}

	}

	public static class MuoviPedone extends Mossa {

		public MuoviPedone(final Scacchiera scacchiera, final Pezzo pezzoMosso, final int coordinateDestinazione) {
			super(scacchiera, pezzoMosso, coordinateDestinazione);
		}

		@Override
		public boolean equals(final Object obj) {
			return this == obj || obj instanceof MuoviPedone && super.equals(obj);
		}

		@Override
		public String toString() {
			return ScacchieraController.getPosizioneInCoordinate(this.coordinateDestinazione);
		}

	}

	public static class MossaAttaccoPedone extends MossaAttacco {

		public MossaAttaccoPedone(final Scacchiera scacchiera, final Pezzo pezzoMosso, final int coordinateDestinazione,
				final Pezzo pezzoAttaccante) {
			super(scacchiera, pezzoMosso, coordinateDestinazione, pezzoAttaccante);
		}

		@Override
		public boolean equals(final Object obj) {
			return this == obj || obj instanceof MossaAttaccoPedone && super.equals(obj);
		}

		@Override
		public String toString() {
			return ScacchieraController.getPosizioneInCoordinate(this.pezzoMosso.getCoordinatePezzo()).substring(0, 1)
					+ "x" + ScacchieraController.getPosizioneInCoordinate(this.coordinateDestinazione);
		}

	}

	public static class Passa extends Mossa {

		public Passa() {
			super(null, -1);
		}

		@Override
		public int getCoordinateAttuali() {
			return -1;
		}

		@Override
		public int getCoordinateDestinazione() {
			return -1;
		}

		@Override
		public Scacchiera esegui() {
			throw new RuntimeException("Non puoi eseguire una mossa nulla");
		}

		@Override
		public String toString() {
			return "Mossa Nulla";
		}

	}

	public static class MossaFactory {

		private MossaFactory() {
			throw new RuntimeException("Non istanziabile");
		}

		public static Mossa getMossaNulla() {
			return PASSA;
		}

		public static Mossa creaMossa(final Scacchiera scacchiera, final int coordinateAttuali,
				final int coordinateDestinazione) {

			for (final Mossa mossa : scacchiera.getMossePossibili()) {
				if (mossa.getCoordinateAttuali() == coordinateAttuali
						&& mossa.getCoordinateDestinazione() == coordinateDestinazione) {
					return mossa;
				}
			}

			return PASSA;
		}

	}

}
