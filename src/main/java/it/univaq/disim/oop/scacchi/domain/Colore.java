package it.univaq.disim.oop.scacchi.domain;

public enum Colore {
	Bianco {
		@Override
		public int getDirezione() {
			return -1;
		}

		@Override
		public boolean èBianco() {
			return true;
		}

		@Override
		public boolean èNero() {
			return false;
		}
	},
	Nero {
		@Override
		public int getDirezione() {
			return 1;
		}

		@Override
		public boolean èBianco() {
			return false;
		}

		@Override
		public boolean èNero() {
			return true;
		}
	};

	public abstract int getDirezione();

	public abstract boolean èBianco();

	public abstract boolean èNero();
}
