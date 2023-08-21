package it.univaq.disim.oop.scacchi.player;

public enum StatoMossa {
	FATTO {
		@Override
		boolean isFatto() {
			return true;
		}
	},
	MOSSA_ILLEGALE {
		@Override
		boolean isFatto() {
			return false;
		}
	},
	LASCIA_GIOCATORE_IN_SCACCO {
		@Override
		boolean isFatto() {
			return false;
		}
	};
	
	abstract boolean isFatto();
}
