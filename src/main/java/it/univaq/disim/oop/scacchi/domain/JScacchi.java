package it.univaq.disim.oop.scacchi.domain;

import it.univaq.disim.oop.scacchi.gui.Tabella;

public class JScacchi {

	public static void main(String[] args) {

		Scacchiera scacchiera = Scacchiera.creaScacchieraStandard();

		System.out.println(scacchiera);

		Tabella tabella = new Tabella();
	}

}
