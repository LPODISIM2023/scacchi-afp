package it.univaq.disim.oop.scacchi.scacchiera;

import it.univaq.disim.oop.scacchi.controller.ScacchieraController;
import it.univaq.disim.oop.scacchi.pezzi.Pedone;
import it.univaq.disim.oop.scacchi.pezzi.Pezzo;
import it.univaq.disim.oop.scacchi.scacchiera.Scacchiera.*;

public abstract class Mossa {

	protected final Scacchiera scacchiera;
	protected final Pezzo pezzoMosso;
	protected final int coordinateDestinazione;
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
		final int primo = 31;
		int risultato = 1;
		risultato = primo * risultato + this.coordinateDestinazione;
		risultato = primo * risultato + this.pezzoMosso.hashCode();
		risultato = primo * risultato + this.pezzoMosso.getCoordinatePezzo();
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

	public boolean Attacca() {
		return false;
	}

	public Pezzo getPezzoMosso() {
		return this.pezzoMosso;
	}

	public Pezzo getPezzoAttaccante() {
		return null;
	}

	public Scacchiera esegui() {

		final Costruttore costruttore = new Costruttore();
		// controllo tutti i pezzi del giocatore attuale,
		// controllo quali pezzi ha mosso e li inserisco nella nuova Scacchiera
		for (final Pezzo pezzo : this.scacchiera.giocatoreAttuale().getPezziAttivi()) {
			if (!this.pezzoMosso.equals(pezzo)) {
				costruttore.setPezzo(pezzo);
			}
		}

		for (final Pezzo pezzo : this.scacchiera.giocatoreAttuale().getAvversario().getPezziAttivi()) {
			costruttore.setPezzo(pezzo);
		}
		// sposta il pezzo dopo la mossa
		costruttore.setPezzo(this.pezzoMosso.pezzoMosso(this));
		// dopo aver fatto una mossa passo al giocatore di colore opposto
		costruttore.setMossaFatta(this.scacchiera.giocatoreAttuale().getAvversario().getColore());
		// restiruisco una nuova scacchira dopo ogni mossa fatta
		return costruttore.crea();
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

	public static class MuoviPedone extends Mossa {

		public MuoviPedone(final Scacchiera scacchiera, final Pezzo pezzoMosso, final int coordinateDestinazione) {
			super(scacchiera, pezzoMosso, coordinateDestinazione);
		}
		/*
		@Override
		public Scacchiera esegui() {
			final Costruttore costruttore = new Costruttore();
			for (final Pezzo pezzo : this.scacchiera.giocatoreAttuale().getPezziAttivi()) {
				if (!this.pezzoMosso.equals(pezzo)) {
					costruttore.setPezzo(pezzo);
				}
			}
			for (final Pezzo pezzo : this.scacchiera.giocatoreAttuale().getAvversario().getPezziAttivi()) {
				costruttore.setPezzo(pezzo);
			}
			final Pedone pedonoMosso = (Pedone) this.pezzoMosso.pezzoMosso(this);
			costruttore.setPezzo(pedonoMosso);
			costruttore.setMossaFatta(this.scacchiera.giocatoreAttuale().getColore());
			return costruttore.crea();
		}*/

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

	public static class PresaVarcoPedone extends MossaAttaccoPedone {

		public PresaVarcoPedone(final Scacchiera scacchiera, final Pezzo pezzoMosso, final int coordinateDestinazione,
				final Pezzo pezzoAttaccante) {
			super(scacchiera, pezzoMosso, coordinateDestinazione, pezzoAttaccante);
		}

	}

	public static class Passa extends Mossa {

		public Passa() {
			super(null, 65);
		}

		@Override
		public Scacchiera esegui() {
			throw new RuntimeException("Non puoi eseguire una mossa nulla");
		}

		public int getCoordinateAttuali() {
			return -1;
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
