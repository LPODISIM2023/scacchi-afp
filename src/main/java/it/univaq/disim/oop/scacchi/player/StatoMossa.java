package it.univaq.disim.oop.scacchi.player;

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
