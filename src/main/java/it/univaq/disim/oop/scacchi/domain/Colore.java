package it.univaq.disim.oop.scacchi.domain;

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
		protected Giocatore scegliGiocatore(GiocatoreBianco giocatoreBianco,
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
		protected Giocatore scegliGiocatore(GiocatoreBianco giocatoreBianco,
				GiocatoreNero giocatoreNero) {
			return giocatoreNero;
		}
	};

	public abstract int getDirezione();

	public abstract boolean isBianco();

	public abstract boolean isNero();

	protected abstract Giocatore scegliGiocatore(GiocatoreBianco giocatoreBianco, GiocatoreNero giocatoreNero);
}
