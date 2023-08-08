package it.univaq.disim.oop.scacchi.domain;
import it.univaq.disim.oop.scacchi.domain.Pezzo;

//potrebbe essere astratta come classe
public abstract class Mossa {

	final Scacchiera scacchiera;
	final Pezzo pezzoMosso;
	final int coordinateDestinazione;

	private Mossa(final Scacchiera scacchiera,
			final Pezzo pezzoMosso,
			final int coordinateDestinazione){
		this.scacchiera = scacchiera;
		this.pezzoMosso = pezzoMosso;
		this.coordinateDestinazione  = coordinateDestinazione;
	}
	
	public static final class Muovi extends Mossa{

		public Muovi(final Scacchiera scacchiera,
				final Pezzo pezzoMosso,
				final int coordinateDestinazione) {
			super(scacchiera, pezzoMosso, coordinateDestinazione);
		}
		
	}
	
	public static final class Attacco extends Mossa{

		final Pezzo pezzoAttaccante;
		
		public Attacco(final Scacchiera scacchiera,
				final Pezzo pezzoMosso,
				final int coordinateDestinazione,
				final Pezzo pezzoAttaccante) {
			super(scacchiera, pezzoMosso, coordinateDestinazione);
			this.pezzoAttaccante = pezzoAttaccante;
		}
		
	}
}
