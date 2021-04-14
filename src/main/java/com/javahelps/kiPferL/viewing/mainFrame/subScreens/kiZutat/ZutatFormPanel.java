package com.javahelps.kiPferL.viewing.mainFrame.subScreens.kiZutat;

import com.javahelps.kiPferL.controlling.mainFrame.MainFrameWindowAdapter;
import com.javahelps.kiPferL.jpa.kiZutat.ZutatJPA;
import com.javahelps.kiPferL.model.kiTableObject.kiParameter.KiParameter;
import com.javahelps.kiPferL.model.kiUtils.kiStringUtils.KiStringUtils;
import com.javahelps.kiPferL.viewing.mainFrame.MyComponentItem;
import com.javahelps.kiPferL.viewing.mainFrame.subScreens.SubScreenFormPanel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Arrays.*;

/**
 * ZutatSubScreen-FormPanel (rechte Seite)
 *
 * @author Susanna Gruber
 * @version kiPferLGoesJPA2.0 2021.03.08       -->     Erweiterung um Zutaten
 * @since 1.14
 */
public class ZutatFormPanel  extends SubScreenFormPanel {

    // Ausgewählte ParameterGruppe
    private final String selectedEinkaufsKategorieKuerzel, selectedEinkaufsKategorieId;
    private final JComboBox<MyComponentItem<String>> masseinheitRezept, masseinheitHandel, lagerfaehigkeitDauer, lagerfaehigkeitTemperatur,
            lagerfaehigkeitLicht, lagerfaehigkeitLuftfeuchtigkeit;
    private final JTextField kleinsteVerkaufsEinheit, kommissionierungsEinheit, kiloGrammAequivalent, naehrWertProHundertGramm, brotEinheitenProHundertGramm;
    private final JList<MyComponentItem<String>> kiParameterAL, kiParameterDT;

    // div. FontObjekte (Konstante)
    private static final Font textFont2 = new Font("Arial", Font.PLAIN, 16), textFont3 = new Font("Arial", Font.BOLD, 16);
    // Rahmen (Konstante)
    private static final Border lineBorderForms = new LineBorder(Color.BLACK, 1);

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // KonstruktorParameter werden an die SuperKlasse weitergegeben oder gesetzt (default ParameterGruppe)
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public ZutatFormPanel
    (Dimension pPreferredSize, Dimension pPreferredSizeNorthPanel, LayoutManager pLayoutManagerNorthPanel, String pFormTitle,
     String selectedEinkaufsKategorieKuerzel, String selectedEinkaufsKategorieId) {
        // Aufruf des SuperKlassenKonstruktors
        super(pPreferredSize, pPreferredSizeNorthPanel, pLayoutManagerNorthPanel, pFormTitle);

        // default ParameterGruppe
        this.selectedEinkaufsKategorieKuerzel = selectedEinkaufsKategorieKuerzel;
        this.selectedEinkaufsKategorieId = selectedEinkaufsKategorieId;

        // Komponenten aus SuperKlasse in die SammelMappe stellen
        this.getFormFields().add(this.getBezeichnungTitleLabel());
        this.getFormFields().add(this.getBezeichnungTextField());

        this.getFormFields().add(this.getInfoTextTitleLabel());
        this.getFormFields().add(new JScrollPane(this.getInfoTextTextArea()));

        // SpezialKomponenten erzeugen und in die SammelMappe stellen
        JLabel temp1 = new JLabel("Allergene:");
        this.getFormFields().add(temp1);

        temp1 = new JLabel("Diättauglichkeiten:");
        this.getFormFields().add(temp1);

        List<KiParameter> parameter = ZutatJPA.findAllKiParameterByPraefixAndParametergruppeOrderByBezeichnung(
                MainFrameWindowAdapter.getEntityManagerFactory(), "AL", "Allergen");
        int[] i = {0};
        final MyComponentItem<String>[] finalParameter5 = new MyComponentItem[parameter.size()];
        parameter.forEach(kiParameter -> {
            finalParameter5[i[0]] = new MyComponentItem<>(kiParameter.getId(), "  " + kiParameter.getBezeichnung());
            ++i[0];
        });
        DefaultListModel<MyComponentItem<String>> listModelAL = new DefaultListModel<>();                               // JList für Allergene
        stream(finalParameter5).forEach(myComponentItem -> listModelAL.addElement(myComponentItem));
        this.kiParameterAL = new JList<>(listModelAL);
        this.getFormFields().add(this.kiParameterAL);

        parameter = ZutatJPA.findAllKiParameterByPraefixAndParametergruppeOrderByBezeichnung(
                MainFrameWindowAdapter.getEntityManagerFactory(), "DT", "DiätTauglichkeit");
        i[0] = 0;
        final MyComponentItem<String>[] finalParameter6 = new MyComponentItem[parameter.size()];
        parameter.forEach(kiParameter -> {
            finalParameter6[i[0]] = new MyComponentItem<>(kiParameter.getId(), "  " + kiParameter.getBezeichnung());
            ++i[0];
        });
        DefaultListModel<MyComponentItem<String>> listModelDT = new DefaultListModel<>();                               // JList für Diättauglichkeiten
        stream(finalParameter6).forEach(myComponentItem -> listModelDT.addElement(myComponentItem));
        this.kiParameterDT = new JList<>(listModelDT);
        this.getFormFields().add(this.kiParameterDT);

        temp1 = new JLabel("Maßeinheit Rezept:");
        this.getFormFields().add(temp1);
        parameter = ZutatJPA.findAllKiParameterByPraefixAndParametergruppeOrderByBezeichnung(
                MainFrameWindowAdapter.getEntityManagerFactory(), "ME", "Maßeinheit");
        i[0] = 0;
        final MyComponentItem<String>[] finalParameter = new MyComponentItem[parameter.size()];
        parameter.forEach(kiParameter -> {
            finalParameter[i[0]] = new MyComponentItem<>(kiParameter.getId(), "  " + kiParameter.getBezeichnung());
            ++i[0];
        });
        this.masseinheitRezept = new JComboBox<>();                                                                     // JComboBox für Masseinheit Rezept
        stream(finalParameter).forEach(myComponentItem -> this.masseinheitRezept.addItem(myComponentItem));
        this.getFormFields().add(this.masseinheitRezept);

        temp1 = new JLabel("Maßeinheit Handel:");
        this.getFormFields().add(temp1);
        this.masseinheitHandel = new JComboBox<>();                                                                     // JComboBox für Masseinheit Handel
        stream(finalParameter).forEach(myComponentItem -> this.masseinheitHandel.addItem(myComponentItem));
        this.getFormFields().add(this.masseinheitHandel);

        temp1 = new JLabel("Kleinste Verkaufseinheit:");
        this.getFormFields().add(temp1);
        this.kleinsteVerkaufsEinheit = new JTextField();
        this.getFormFields().add(this.kleinsteVerkaufsEinheit);

        temp1 = new JLabel("Kommissionierungseinheit:");
        this.getFormFields().add(temp1);
        this.kommissionierungsEinheit = new JTextField();
        this.getFormFields().add(this.kommissionierungsEinheit);

        temp1 = new JLabel("Kilogramm-Äquivalent:");
        this.getFormFields().add(temp1);
        this.kiloGrammAequivalent = new JTextField();
        this.getFormFields().add(this.kiloGrammAequivalent);

        temp1 = new JLabel("Nährwert pro hundert Gramm:");
        this.getFormFields().add(temp1);
        this.naehrWertProHundertGramm = new JTextField();
        this.getFormFields().add(this.naehrWertProHundertGramm);

        temp1 = new JLabel("Broteinheiten pro hundert Gramm:");
        this.getFormFields().add(temp1);
        this.brotEinheitenProHundertGramm = new JTextField();
        this.getFormFields().add(this.brotEinheitenProHundertGramm);

        temp1 = new JLabel("Lagerfähigkeit Dauer:");
        this.getFormFields().add(temp1);
        parameter = ZutatJPA.findAllKiParameterByPraefixAndParametergruppeOrderByBezeichnung(
                MainFrameWindowAdapter.getEntityManagerFactory(), "LD", "Lagerdauer");
        i[0] = 0;
        final MyComponentItem<String>[] finalParameter1 = new MyComponentItem[parameter.size()];
        parameter.forEach(kiParameter -> {
            finalParameter1[i[0]] = new MyComponentItem<>(kiParameter.getId(), "  " + kiParameter.getBezeichnung());
            ++i[0];
        });
        this.lagerfaehigkeitDauer = new JComboBox<>();                                                                  // JComboBox für Lagerdauer
        stream(finalParameter1).forEach(myComponentItem -> {
            this.lagerfaehigkeitDauer.addItem(myComponentItem);
            if(myComponentItem.getValue().equals("  Keine Angabe")) {
                this.lagerfaehigkeitDauer.setSelectedItem(myComponentItem);
            }
        });
        this.getFormFields().add(this.lagerfaehigkeitDauer);

        temp1 = new JLabel("Lagerfähigkeit Temperatur:");
        this.getFormFields().add(temp1);
        parameter = ZutatJPA.findAllKiParameterByPraefixAndParametergruppeOrderByBezeichnung(
                MainFrameWindowAdapter.getEntityManagerFactory(), "LT", "Lagertemperatur");
        i[0] = 0;
        final MyComponentItem<String>[] finalParameter2 = new MyComponentItem[parameter.size()];
        parameter.forEach(kiParameter -> {
            finalParameter2[i[0]] = new MyComponentItem<>(kiParameter.getId(), "  " + kiParameter.getBezeichnung());
            ++i[0];
        });
        this.lagerfaehigkeitTemperatur = new JComboBox<>();                                                             // JComboBox für Lagertemperatur
        stream(finalParameter2).forEach(myComponentItem -> {
            this.lagerfaehigkeitTemperatur.addItem(myComponentItem);
            if(myComponentItem.getValue().equals("  Keine Angabe")) {
                this.lagerfaehigkeitTemperatur.setSelectedItem(myComponentItem);
            }
        });
        this.getFormFields().add(this.lagerfaehigkeitTemperatur);

        temp1 = new JLabel("Lagerfähigkeit Licht:");
        this.getFormFields().add(temp1);
        parameter = ZutatJPA.findAllKiParameterByPraefixAndParametergruppeOrderByBezeichnung(
                MainFrameWindowAdapter.getEntityManagerFactory(), "LB", "LagerBeleuchtung");
        i[0] = 0;
        final MyComponentItem<String>[] finalParameter3 = new MyComponentItem[parameter.size()];
        parameter.forEach(kiParameter -> {
            finalParameter3[i[0]] = new MyComponentItem<>(kiParameter.getId(), "  " + kiParameter.getBezeichnung());
            ++i[0];
        });
        this.lagerfaehigkeitLicht = new JComboBox<>();                                                                  // JComboBox für LagerBeleuchtung
        stream(finalParameter3).forEach(myComponentItem -> {
            this.lagerfaehigkeitLicht.addItem(myComponentItem);
            if(myComponentItem.getValue().equals("  Keine Angabe")) {
                this.lagerfaehigkeitLicht.setSelectedItem(myComponentItem);
            }
        });
        this.getFormFields().add(this.lagerfaehigkeitLicht);

        temp1 = new JLabel("Lagerfähigkeit Luftfeuchtigkeit:");
        this.getFormFields().add(temp1);
        parameter = ZutatJPA.findAllKiParameterByPraefixAndParametergruppeOrderByBezeichnung(
                MainFrameWindowAdapter.getEntityManagerFactory(), "LL", "LagerLuftFeuchtigkeit");
        i[0] = 0;
        final MyComponentItem<String>[] finalParameter4 = new MyComponentItem[parameter.size()];
        parameter.forEach(kiParameter -> {
            finalParameter4[i[0]] = new MyComponentItem<>(kiParameter.getId(), "  " + kiParameter.getBezeichnung());
            ++i[0];
        });
        this.lagerfaehigkeitLuftfeuchtigkeit = new JComboBox<>();                                                       // JComboBox für LagerLuftFeuchtigkeit
        stream(finalParameter4).forEach(myComponentItem -> {
            this.lagerfaehigkeitLuftfeuchtigkeit.addItem(myComponentItem);
            if(myComponentItem.getValue().equals("  Keine Angabe")) {
                this.lagerfaehigkeitLuftfeuchtigkeit.setSelectedItem(myComponentItem);
            }
        });
        this.getFormFields().add(this.lagerfaehigkeitLuftfeuchtigkeit);

        List<JLabel> tempList = IntStream.range(0, 3).mapToObj(j -> new JLabel()).collect(Collectors.toList());         // DummyLabels in einer temporären Liste erzeugen
        this.getFormFields().addAll(tempList);                                                                          // DummyLabels in SammelListe füllen
        this.getFormButton().setText("Zutat anlegen");
        this.getFormFields().add(this.getFormButton());

        this.getFormFields().add(new JLabel());
        this.getFormFields().add(this.getCopyRightLabel());

        this.getFormFields().forEach((JComponent jComponent) -> {
            if((jComponent instanceof JLabel && !((JLabel) jComponent).getText().isEmpty()
                    && !jComponent.equals(this.getIdLabel()) && !jComponent.equals(this.getCopyRightLabel()) && !jComponent.equals(this.getTitleLabel()))
                    || jComponent instanceof JButton) {
                jComponent.setFont(textFont3);
            } else if(jComponent instanceof JComboBox) {
                jComponent.setFont(textFont2);
                jComponent.setBorder(lineBorderForms);
                ((JComboBox<MyComponentItem<String>>) jComponent).setEditable(true);
                ((JComboBox<MyComponentItem<String>>) jComponent).getEditor().getEditorComponent().setBackground(Color.WHITE);
            } else {
                if(!(jComponent instanceof JLabel && (((JLabel) jComponent).getText().isEmpty() || jComponent.equals(this.getTitleLabel())))
                    || jComponent.equals(this.getIdLabel()) || jComponent.equals(this.getCopyRightLabel())) {
                    jComponent.setFont(textFont2);
                }
            }
            if(!(jComponent instanceof JLabel)) {
                jComponent.setBorder(lineBorderForms);
            }
            if(jComponent instanceof JTextArea) {
                ((JTextArea)jComponent).setLineWrap(true);
                // bei Drücken der Tab-Taste springt der Fokus weiter (braucht einen KeyListener statt FocusListener)
                // kopiert von https://kodejava.org/how-do-i-move-focus-from-jtextarea-using-tab-key/
                jComponent.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_TAB) {
                            if (e.getModifiersEx() > 0) {
                                jComponent.transferFocusBackward();
                            } else {
                                jComponent.transferFocus();
                            }
                            e.consume();
                        }
                    }
                });
            }
        });

        // **************************************************************************************************************************************************************
        // NordPanel default füllen
        // **************************************************************************************************************************************************************
        final int[] y = {0};
        this.getFormFields().stream().filter(jComponent -> {
            ++y[0];
            return y[0] <= ((GridLayout)this.getNorthPanel().getLayout()).getRows() * ((GridLayout)this.getNorthPanel().getLayout()).getColumns();
        }).forEach(jComponent -> {
            // letzte ID der gewählten Parametergruppe aus der DB-Tabelle KiParameter abfragen, um eins erhöhen und dem entsprechenden Label übergeben
            int x = ZutatJPA.findMaxIdZutat(
                    MainFrameWindowAdapter.getEntityManagerFactory(), ZutatFormPanel.this.selectedEinkaufsKategorieId);
            ZutatFormPanel.this.getIdLabel().setText(KiStringUtils.idIncrement(ZutatFormPanel.this.selectedEinkaufsKategorieKuerzel, ++x));
            // Komponenten des oberen FormularTeils aus der Sammelliste auf das Panel stellen
            ZutatFormPanel.this.getNorthPanel().add(jComponent);
        });
        // **************************************************************************************************************************************************************
        // CenterPanel default füllen
        // **************************************************************************************************************************************************************
        // centerNord = InfoText
        JPanel centerNorthPanel = new JPanel();
        centerNorthPanel.setPreferredSize(new Dimension(this.getWidth(), ((this.getPreferredSize().height - 15) / 26) * 3));
        centerNorthPanel.setLayout(new GridLayout(1,2, 20, 3));
        centerNorthPanel.setOpaque(false);

        JPanel temp = new JPanel();
        temp.setLayout(new GridLayout(3,1, 20, 3));
        temp.setOpaque(false);
        temp.add(this.getInfoTextTitleLabel());
        temp.add(new JLabel());
        temp.add(new JLabel());
        centerNorthPanel.add(temp);
        centerNorthPanel.add(new JScrollPane(this.getInfoTextTextArea()));

        // centerCenter = Allergene + Diättauglichkeiten
        JPanel centerCenterPanel = new JPanel();
        centerCenterPanel.setPreferredSize(new Dimension(this.getWidth(), ((this.getPreferredSize().height - 15) / 26) * 5));
        centerCenterPanel.setOpaque(false);

        JPanel ccpN = new JPanel();
        ccpN.setPreferredSize(new Dimension(this.getWidth(), (this.getPreferredSize().height - 15) / 26));
        ccpN.setLayout(new GridLayout(1,2, 20, 3));
        ccpN.setOpaque(false);

        JPanel ccpS = new JPanel();
        ccpS.setPreferredSize(new Dimension(this.getWidth(), ((this.getPreferredSize().height - 15) / 26) * 4));
        ccpS.setLayout(new GridLayout(1,2, 20, 3));
        ccpS.setOpaque(false);

        y[0] = 0;
        this.getFormFields().stream().filter(jComponent -> {
            ++y[0];
            return y[0] > 10 && y[0] <= 14;
        }).forEach(jComponent -> {
            if(y[0] == 11 || y[0] == 12) {
                ccpN.add(jComponent);
            } else {
                JScrollPane scrollPane = new JScrollPane();
                scrollPane.setViewportView(jComponent);
                ((JList<MyComponentItem<String>>)jComponent).setLayoutOrientation(JList.VERTICAL);
                ccpS.add(scrollPane);
            }
        });

        centerCenterPanel.setLayout(new BorderLayout());
        centerCenterPanel.add(ccpN, BorderLayout.NORTH);
        centerCenterPanel.add(ccpS, BorderLayout.SOUTH);

        // centerSouth = restliche Zeilen
        JPanel centerSouthPanel = new JPanel();
        centerSouthPanel.setPreferredSize
            (new Dimension(this.getWidth(),
                ((this.getPreferredSize().height - 15) / 26) * (26 - ((GridLayout)this.getNorthPanel().getLayout()).getRows() - 8)));
        centerSouthPanel.setLayout(
                new GridLayout(
                        26 - ((GridLayout)this.getNorthPanel().getLayout()).getRows() - 8, 2, 20, 3));
        centerSouthPanel.setOpaque(false);

        y[0] = 0;
        this.getFormFields().stream().filter(jComponent -> {
            ++y[0];
            return y[0] > 14;
        }).forEach(centerSouthPanel::add);

        // centerNord und centerCenter auf das CenterPanel setzen
        this.getCenterPanel().setLayout(new BorderLayout());
        this.getCenterPanel().add(centerNorthPanel, BorderLayout.NORTH);
        this.getCenterPanel().add(centerCenterPanel, BorderLayout.CENTER);
        this.getCenterPanel().add(centerSouthPanel, BorderLayout.SOUTH);
    }

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Getter und Setter
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public JComboBox<MyComponentItem<String>> getMasseinheitRezept() {
        return masseinheitRezept;
    }

    public JComboBox<MyComponentItem<String>> getMasseinheitHandel() {
        return masseinheitHandel;
    }

    public JComboBox<MyComponentItem<String>> getLagerfaehigkeitDauer() {
        return lagerfaehigkeitDauer;
    }

    public JComboBox<MyComponentItem<String>> getLagerfaehigkeitTemperatur() {
        return lagerfaehigkeitTemperatur;
    }

    public JComboBox<MyComponentItem<String>> getLagerfaehigkeitLicht() {
        return lagerfaehigkeitLicht;
    }

    public JComboBox<MyComponentItem<String>> getLagerfaehigkeitLuftfeuchtigkeit() {
        return lagerfaehigkeitLuftfeuchtigkeit;
    }

    public JTextField getKleinsteVerkaufsEinheit() {
        return kleinsteVerkaufsEinheit;
    }

    public JTextField getKommissionierungsEinheit() {
        return kommissionierungsEinheit;
    }

    public JTextField getKiloGrammAequivalent() {
        return kiloGrammAequivalent;
    }

    public JTextField getNaehrWertProHundertGramm() {
        return naehrWertProHundertGramm;
    }

    public JTextField getBrotEinheitenProHundertGramm() {
        return brotEinheitenProHundertGramm;
    }

    public JList<MyComponentItem<String>> getKiParameterAL() {
        return kiParameterAL;
    }

    public JList<MyComponentItem<String>> getKiParameterDT() {
        return kiParameterDT;
    }
}
