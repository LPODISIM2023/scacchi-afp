package it.univaq.disim.oop.scacchi.saving;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.plaf.FileChooserUI;

import it.univaq.disim.oop.scacchi.gui.Tabella;

public class LetturaFile {

	private static final LetturaFile INSTANCE = new LetturaFile();

	private final GestioneFile gestioneFile;

	public LetturaFile() {
		this.gestioneFile = new GestioneFile();
	}

	public static LetturaFile get() {
		return INSTANCE;
	}

	private GestioneFile getGestioneFile() {
		return this.gestioneFile;
	}

	public void LeggiFile() {

		JFileChooser scegliFile = new JFileChooser("D:\\Desktop\\scacchi-afp\\file\\");
		int result = scegliFile.showSaveDialog(null);
		File FileSelezionato = null;
		if (result == JFileChooser.APPROVE_OPTION) {
			FileSelezionato = scegliFile.getSelectedFile();
			System.out.println("File selezionato: " + FileSelezionato.getAbsolutePath());
		}

		String file = FileSelezionato.getAbsolutePath();
		try {
			List<String> lista = Files.readAllLines(Paths.get(file), StandardCharsets.UTF_8);
			Iterator<String> iteratore = lista.iterator();

			while (iteratore.hasNext()) {
				System.out.println(iteratore.next());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		/*
		 * String percorsoCartella = "D:\\Desktop\\scacchi-afp\\file\\"; LocalDate data
		 * = java.time.LocalDate.now(); //prende la data corrente File myCartella = new
		 * File(percorsoCartella + data);
		 * 
		 * String pathLocation = myCartella.getAbsolutePath();
		 * 
		 * //Path file = gestioneFile.NomeFile(pathLocation); Path file =
		 * LetturaFile.get().getGestioneFile().NomeFile(pathLocation);
		 * 
		 * try { List<String> lista = Files.readAllLines(file,StandardCharsets.UTF_8 );
		 * 
		 * Iterator<String> iteratore = lista.iterator(); while(iteratore.hasNext()) {
		 * System.out.println(iteratore.next()); } } catch (IOException e) {
		 * e.printStackTrace(); }
		 */

	}

}
