package it.univaq.disim.oop.scacchi.domain;

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
	};

	public abstract int getDirezione();

	public abstract boolean isBianco();

	public abstract boolean isNero();
}
