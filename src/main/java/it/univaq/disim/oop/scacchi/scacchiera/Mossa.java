package it.univaq.disim.oop.scacchi.scacchiera;

import it.univaq.disim.oop.scacchi.pezzi.Pedone;
import it.univaq.disim.oop.scacchi.pezzi.Pezzo;
import it.univaq.disim.oop.scacchi.scacchiera.Scacchiera.*;

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
	
	@Override
	public int hashCode() {
		final int primo = 3;
		int risultato = 1;
		
		risultato = primo + risultato + this.coordinateDestinazione;
		risultato = primo + risultato + this.pezzoMosso.hashCode();
		
		return risultato;
	}
	
	@Override
	public boolean equals(final Object obj) {
		if(this == obj) {
			return true;
		}
		if(!(obj instanceof Mossa)) {
			return false;
		}
		final Mossa m1 = (Mossa) obj;
		return this.getCoordinateDestinazione() == m1.getCoordinateDestinazione() &&
				this.getPezzoMosso() == m1.getPezzoMosso();
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
	
	public boolean Attacca() {
		return false;
	}
	
	public Pezzo getPezzoAtttaccante() {
		return null;
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
		public int hashCode() {
			return this.pezzoAttaccante.hashCode() + super.hashCode();
		}
		
		@Override
		public boolean equals(final Object obj) {
			if(this == obj) {
				return true;
			}
			if(!(obj instanceof Attacco)) {
				return false;
			}
			final Attacco a1 = (Attacco) obj;
			return super.equals(a1) && this.getPezzoAtttaccante().equals(a1.getPezzoAtttaccante());
		}
		
		@Override
		public Scacchiera esegui() {
			return null;
		}
		
		@Override
		public boolean Attacca() {
			return true;
		}
		
		@Override
		public Pezzo getPezzoAtttaccante() {
			return this.pezzoAttaccante;
		}
	}
	
	public static class MuoviPedone extends Mossa {

		public MuoviPedone(final Scacchiera scacchiera,
				final Pezzo pezzoMosso,
				final int coordinateDestinazione) {
			super(scacchiera, pezzoMosso, coordinateDestinazione);
		}
		
		@Override
		public Scacchiera esegui() {
			final Costruttore nc = new Costruttore();
			for(final Pezzo pezzo: this.scacchiera.giocatoreAttuale().getPezziAttivi()) {
				if(!this.pezzoMosso.equals(pezzo)) {
					nc.setPezzo(pezzo);
				}
			}
			for(final Pezzo pezzo: this.scacchiera.giocatoreAttuale().getAvversario().getPezziAttivi()) {
				nc.setPezzo(pezzo);
			}
			final Pedone pedonoMosso = (Pedone) this.pezzoMosso.pezzoMosso(this);
			nc.setPezzo(pedonoMosso);
			nc.setMossaFatta(this.scacchiera.giocatoreAttuale().getColore());
			return nc.crea();
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
			throw new RuntimeException("Non istanziabile");
		}
		
	}
	
	public static class Mosse{

		private Mosse() {
			throw new RuntimeException("Non istanziabile");
		}
		
		public static Mossa generaMossa(final Scacchiera scacchiera,
				final int coordinateAttuali,
				final int coordinateDestinazione) {
			
			for(final Mossa mossa: scacchiera.getMossePossibili()) {
				if(mossa.getCoordianteAttuali() == coordinateAttuali &&
						mossa.getCoordinateDestinazione() == coordinateDestinazione) {
					return mossa;
				}
			}
			
			return PASSA;
		}

	}
		
}
