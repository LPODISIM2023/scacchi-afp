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
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.FileChooserUI;

import it.univaq.disim.oop.scacchi.gui.Tabella;

public class LetturaFile {

	public void LeggiFile() {

		JFileChooser scegliFile = new JFileChooser("D:\\Desktop\\scacchi-afp\\file\\");
		int result = scegliFile.showSaveDialog(null);
		File fileSelezionato = null;
		if (result == JFileChooser.APPROVE_OPTION) {
			fileSelezionato = scegliFile.getSelectedFile();
			System.out.println("File selezionato: " + fileSelezionato.getAbsolutePath());
		}

		String file = fileSelezionato.getAbsolutePath();
		try {
			List<String> lista = Files.readAllLines(Paths.get(file), StandardCharsets.UTF_8);
			Iterator<String> iteratore = lista.iterator();
					
			while (iteratore.hasNext()) {
				System.out.println(iteratore.next());
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public String ScegliCartella() {
		JFileChooser scegliCartella = new JFileChooser("D:\\Desktop\\scacchi-afp\\file\\");
		scegliCartella.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int result = scegliCartella.showSaveDialog(null);
		File cartellaSelezionata = null;
		if (result == JFileChooser.APPROVE_OPTION) {
			cartellaSelezionata = scegliCartella.getSelectedFile();
			System.out.println("Cartella selezionata: " + cartellaSelezionata.getAbsolutePath());
		}
		
		return cartellaSelezionata.getAbsolutePath();
	}

}
