package it.univaq.disim.oop.scacchi.pezzi;

import it.univaq.disim.oop.scacchi.player.*;

public enum Colore {
	BIANCO {
		@Override
		public int getDirezione() {
			return -1;
		}

		@Override
		public boolean isBianco() {
			return true;
		}

		@Override
		public boolean isNero() {
			return false;
		}

		@Override
		public Giocatore scegliGiocatore(GiocatoreBianco giocatoreBianco,
				GiocatoreNero giocatoreNero) {
			return giocatoreBianco;
		}
	},
	NERO {
		@Override
		public int getDirezione() {
			return 1;
		}

		@Override
		public boolean isBianco() {
			return false;
		}

		@Override
		public boolean isNero() {
			return true;
		}

		@Override
		public Giocatore scegliGiocatore(GiocatoreBianco giocatoreBianco,
				GiocatoreNero giocatoreNero) {
			return giocatoreNero;
		}
	};

	public abstract int getDirezione();

	public abstract boolean isBianco();

	public abstract boolean isNero();

	public abstract Giocatore scegliGiocatore(GiocatoreBianco giocatoreBianco, GiocatoreNero giocatoreNero);
}
