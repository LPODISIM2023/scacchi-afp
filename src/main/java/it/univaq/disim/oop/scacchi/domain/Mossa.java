package it.univaq.disim.oop.scacchi.domain;

import it.univaq.disim.oop.scacchi.domain.Scacchiera.*;

public abstract class Mossa {

	final Scacchiera scacchiera;
	final Pezzo pezzoMosso;
	final int coordinateDestinazione;
	
	public static final Mossa PASSA = new Passa();

	private Mossa(final Scacchiera scacchiera, final Pezzo pezzoMosso, final int coordinateDestinazione) {
		this.scacchiera = scacchiera;
		this.pezzoMosso = pezzoMosso;
		this.coordinateDestinazione = coordinateDestinazione;
	}
	
	public int getCoordinateDestinazione() {
		return this.coordinateDestinazione;
	}
	
	public int getCoordianteAttuali() {
		return this.getPezzoMosso().getCoordinatePezzo();
	}
	
	public Pezzo getPezzoMosso() {
		return this.pezzoMosso;
	}
	
	public Scacchiera esegui() {
		
		final Costruttore ns = new Costruttore();
		//controllo tutti i pezzi del giocatore attuale,
		//controllo quali pezzi ha mosso e li inserisco nella nuova Scacchiera 
		for(final Pezzo pezzo: this.scacchiera.giocatoreAttuale().getPezziAttivi()) {
			if(this.pezzoMosso.equals(pezzo)) {
				ns.setPezzo(pezzo);
			}
		}
		
		for(final Pezzo pezzo: this.scacchiera.giocatoreAttuale().getAvversario().getPezziAttivi()) {
				ns.setPezzo(pezzo);
		}
		//sposta il pezzo dopo la mossa
		ns.setPezzo(this.pezzoMosso.pezzoMosso(this));
		//dopo aver fatto una mossa passo al giocatore di colore opposto
		ns.setMossaFatta(this.scacchiera.giocatoreAttuale().getAvversario().getColore());
		//restiruisco una nuova scacchira dopo ogni mossa fatta
		return ns.crea();
	}
	
	public static final class Muovi extends Mossa {

		public Muovi(final Scacchiera scacchiera,
				final Pezzo pezzoMosso,
				final int coordinateDestinazione) {
			super(scacchiera, pezzoMosso, coordinateDestinazione);
		}

	}

	public static class Attacco extends Mossa {

		final Pezzo pezzoAttaccante;

		public Attacco(final Scacchiera scacchiera,
				final Pezzo pezzoMosso,
				final int coordinateDestinazione,
				final Pezzo pezzoAttaccante) {
			super(scacchiera, pezzoMosso, coordinateDestinazione);
			this.pezzoAttaccante = pezzoAttaccante;
		}

		@Override
		public Scacchiera esegui() {
			return null;
		}
	}
	
	public static class MuoviPedone extends Mossa {

		public MuoviPedone(final Scacchiera scacchiera,
				final Pezzo pezzoMosso,
				final int coordinateDestinazione) {
			super(scacchiera, pezzoMosso, coordinateDestinazione);
		}

	}
	
	public static class MossaAttaccoPedone extends Attacco {

		public MossaAttaccoPedone(final Scacchiera scacchiera,
				final Pezzo pezzoMosso,
				final int coordinateDestinazione,
				final Pezzo pezzoAttaccante) {
			super(scacchiera, pezzoMosso, coordinateDestinazione, pezzoAttaccante);
		}

	}
	
	public static class PresaVarcoPedone extends MossaAttaccoPedone {

		public PresaVarcoPedone(final Scacchiera scacchiera,
				final Pezzo pezzoMosso,
				final int coordinateDestinazione,
				final Pezzo pezzoAttaccante) {
			super(scacchiera, pezzoMosso, coordinateDestinazione, pezzoAttaccante);
		}

	}

	public static class Passa extends Mossa {

		public Passa() {
			super(null, null, -1);
		}
		
		@Override
		public Scacchiera esegui() {
			throw new RuntimeException("Non posso passare");
		}

	}
	
}
