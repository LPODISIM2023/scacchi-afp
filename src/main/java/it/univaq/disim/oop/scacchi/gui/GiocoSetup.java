package it.univaq.disim.oop.scacchi.gui;

import javax.swing.*;

import it.univaq.disim.oop.scacchi.gui.Tabella.GiocatoreTipo;
import it.univaq.disim.oop.scacchi.player.Colore;
import it.univaq.disim.oop.scacchi.player.Giocatore;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class GiocoSetup extends JDialog {

    private GiocatoreTipo tipoGiocatoreBianco;
    private GiocatoreTipo tipoGiocatoreNero;
    private JSpinner searchDepthSpinner;

    private static final String UMANO_TEXT = "Umano";
    private static final String COMPUTER_TEXT = "Computer";

    GiocoSetup(final JFrame frame,
              final boolean modal) {
        super(frame, modal);
        final JPanel myPanel = new JPanel(new GridLayout(0, 1));
        final JRadioButton biancoUmanoButton = new JRadioButton(UMANO_TEXT);
        final JRadioButton biancoComputerButton = new JRadioButton(COMPUTER_TEXT);
        final JRadioButton neroUmanoButton = new JRadioButton(UMANO_TEXT);
        final JRadioButton neroComputerButton = new JRadioButton(COMPUTER_TEXT);
        biancoUmanoButton.setActionCommand(UMANO_TEXT);
        final ButtonGroup gruppoBianco = new ButtonGroup();
        gruppoBianco.add(biancoUmanoButton);
        gruppoBianco.add(biancoComputerButton);
        biancoUmanoButton.setSelected(true);

        final ButtonGroup gruppoNero = new ButtonGroup();
        gruppoNero.add(neroUmanoButton);
        gruppoNero.add(neroComputerButton);
        neroUmanoButton.setSelected(true);

        getContentPane().add(myPanel);
        myPanel.add(new JLabel("Bianco"));
        myPanel.add(biancoUmanoButton);
        myPanel.add(biancoComputerButton);
        myPanel.add(new JLabel("Nero"));
        myPanel.add(neroUmanoButton);
        myPanel.add(neroComputerButton);

        myPanel.add(new JLabel("Cerca"));
        this.searchDepthSpinner = addLabeledSpinner(myPanel, "Ricerca Approfondita", new SpinnerNumberModel(6, 0, Integer.MAX_VALUE, 1));

        final JButton cancellaButton = new JButton("Cancella");
        final JButton okButton = new JButton("OK");

        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tipoGiocatoreBianco = biancoComputerButton.isSelected() ? GiocatoreTipo.COMPUTER : GiocatoreTipo.UMANO;
                tipoGiocatoreNero = neroComputerButton.isSelected() ? GiocatoreTipo.COMPUTER : GiocatoreTipo.UMANO;
                GiocoSetup.this.setVisible(false);
            }
        });

        cancellaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Cancella");
                GiocoSetup.this.setVisible(false);
            }
        });

        myPanel.add(cancellaButton);
        myPanel.add(okButton);

        setLocationRelativeTo(frame);
        pack();
        setVisible(false);
    }

    void promptUser() {
        setVisible(true);
        repaint();
    }

    boolean isCPUGiocatore(final Giocatore giocatore) {
        if(giocatore.getColore() == Colore.BIANCO) {
            return getTipoGiocatoreBianco() == GiocatoreTipo.COMPUTER;
        }
        return getTipoGiocatoreNero() == GiocatoreTipo.COMPUTER;
    }

    GiocatoreTipo getTipoGiocatoreBianco() {
        return this.tipoGiocatoreBianco;
    }

    GiocatoreTipo getTipoGiocatoreNero() {
        return this.tipoGiocatoreNero;
    }

    private static JSpinner addLabeledSpinner(final Container c,
                                              final String label,
                                              final SpinnerModel model) {
        final JLabel l = new JLabel(label);
        c.add(l);
        final JSpinner spinner = new JSpinner(model);
        l.setLabelFor(spinner);
        c.add(spinner);
        return spinner;
    }

    int getSearchDepth() {
        return (Integer)this.searchDepthSpinner.getValue();
    }
}
