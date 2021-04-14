package com.javahelps.kiPferL.viewing.mainFrame.subScreens.kiZutat;

import com.javahelps.kiPferL.controlling.mainFrame.MainFrameWindowAdapter;
import com.javahelps.kiPferL.jpa.kiZutat.ZutatJPA;
import com.javahelps.kiPferL.model.kiTableObject.kiParameter.KiParameter;
import com.javahelps.kiPferL.model.kiTableObject.kiZutat.Zutat;
import com.javahelps.kiPferL.viewing.mainFrame.subScreens.SubScreenListObject;
import com.javahelps.kiPferL.viewing.mainFrame.subScreens.SubScreenListPanel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ZutatSubScreen-ListPanel (linke Seite)
 *
 * @author Susanna Gruber
 * @version kiPferLGoesJPA2.0 2021.03.08       -->     Erweiterung um Zutaten
 * @since 1.14
 */
public class ZutatListPanel extends SubScreenListPanel {

    // Seite/Seiten der Liste
    private static int seite;
    private static int seiten;
    // Ausgewählte ParameterGruppe
    private final String selectedEinkaufsKategorie, selectedEinkaufsKategorieId;
    // SpezialUnterPanels für die Liste
    private JPanel centerWestWestPanel, centerEastWestPanel, centerEastEastPanel;
    // Dimensionen
    private final Dimension preferredSize, preferredSizeNorthPanel;
    // Fonts
    private static final Font textFont3 = new Font("Arial", Font.BOLD, 16);
    // RahmenObjekte (Konstante)
    private static final EmptyBorder listEmptyBorder = new EmptyBorder(0, 4, 0, 4);
    private static final EmptyBorder listEmptyBorder1 = new EmptyBorder(3, 4, 0, 4);
    private static final Border listMatteBorder = BorderFactory.createMatteBorder(0, 1, 0, 0, Color.LIGHT_GRAY);
    private static final Border listMatteBorder1 = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY);
    private static final Border listMatteBorder2 = BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY);
    private static final Border listCombiBorder = BorderFactory.createCompoundBorder(listMatteBorder1, listEmptyBorder);
    private static final Border listCombiBorder1 = BorderFactory.createCompoundBorder(listMatteBorder2, listEmptyBorder1);

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // KonstruktorParameter werden an die SuperKlasse weitergegeben oder gesetzt (default ParameterGruppe, Panels, die nicht in allen ListSubScreens gleich sind)
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public ZutatListPanel(Dimension pPreferredSize, Dimension pPreferredSizeNorthPanel, String pListTitle, List<KiParameter> selectGruppen,
                                String selectedEinkaufsKategorie, String selectedEinkaufsKategorieId) {
        // Aufruf des SuperKlassenKonstruktors
        super(pPreferredSize, pPreferredSizeNorthPanel, 96, 500, pListTitle, selectGruppen);

        // default ParameterGruppe
        this.selectedEinkaufsKategorie = selectedEinkaufsKategorie;
        this.selectedEinkaufsKategorieId = selectedEinkaufsKategorieId;
        // Dimensionen
        this.preferredSize = pPreferredSize;
        this.preferredSizeNorthPanel = pPreferredSizeNorthPanel;
        // der ComboBox ein selectiertes Objekt zuweisen
        this.getSelectGruppenBox().setSelectedItem("  " + this.selectedEinkaufsKategorie);
        // Panels produzieren
        this.producePanels();
        // Seite setzen
        ZutatListPanel.seite = 1;
        // Liste produzieren
        this.findListObjects();
        this.produceList();
    }
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // eigener Konstruktor zum Blättern in der Liste, Seite und Listenobjekte werden übergeben
    public ZutatListPanel(Dimension pPreferredSize, Dimension pPreferredSizeNorthPanel, String pListTitle, List<KiParameter> selectGruppen,
                                String selectedEinkaufsKategorie, String selectedEinkaufsKategorieId, int seite, List<SubScreenListObject> listObjects) {
        // Aufruf des SuperKlassenKonstruktors
        super(pPreferredSize, pPreferredSizeNorthPanel, 96, 500, pListTitle, selectGruppen);

        // default ParameterGruppe
        this.selectedEinkaufsKategorie = selectedEinkaufsKategorie;
        this.selectedEinkaufsKategorieId = selectedEinkaufsKategorieId;
        // Dimensionen
        this.preferredSize = pPreferredSize;
        this.preferredSizeNorthPanel = pPreferredSizeNorthPanel;
        // der ComboBox ein selectiertes Objekt zuweisen
        this.getSelectGruppenBox().setSelectedItem("  " + this.selectedEinkaufsKategorie);
        // listenObjekte übergeben
        this.setListObjects(listObjects);
        // Panels produzieren
        this.producePanels();
        // Seite setzen
        ZutatListPanel.seite = seite;
        // Liste produzieren
        this.produceList();
    }

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Getter und Setter zum Ändern der angezeigten Seite im ActionListener
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public static void setSeite(int seite) {
        ZutatListPanel.seite = seite;
    }
    public static int getSeite() {
        return ZutatListPanel.seite;
    }

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Methoden zur Ausgabe der Liste (werden auch von anderen Klassen (zb. controlling.mainFrame.subScreens.kiParameter.KiParameterSubScreenItemController) aufgerufen.
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    @Override
    public void producePanels() {
        // UnterPanelWestWest für Liste
        this.centerWestWestPanel = new JPanel();
        this.centerWestWestPanel.setPreferredSize
                (new Dimension(96, this.preferredSize.height - this.preferredSizeNorthPanel.height));
        this.centerWestWestPanel.setOpaque(false);
        // UnterPanelEastWest für Liste
        this.centerEastWestPanel = new JPanel();
        this.centerEastWestPanel.setPreferredSize
                (new Dimension(394, this.preferredSize.height - this.preferredSizeNorthPanel.height));
        this.centerEastWestPanel.setBorder(listMatteBorder);
        this.centerEastWestPanel.setOpaque(false);
        // UnterPanelEastEast für Liste
        this.centerEastEastPanel = new JPanel();
        this.centerEastEastPanel.setPreferredSize
                (new Dimension(106, this.preferredSize.height - this.preferredSizeNorthPanel.height));
        this.centerEastEastPanel.setBorder(listMatteBorder);
        this.centerEastEastPanel.setOpaque(false);
    }
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // wird beim Blättern in der Liste nicht erneut aufgerufen, da sich die Einträge nicht geändert haben
    @Override
    public void findListObjects() {
        // Daten abfragen und ListenMap befüllen
        List<Zutat> tempList = ZutatJPA.findAllZutatByEinkaufsKategorieOrderById(
                MainFrameWindowAdapter.getEntityManagerFactory(), this.selectedEinkaufsKategorieId);
        if (tempList != null) {
            ZutatListPanel.seiten = tempList.size() / 20;
            if (tempList.size() % 20 != 0) {
                ++ZutatListPanel.seiten;
            }
            ZutatListPanel.seite = ZutatListPanel.seiten;
        }
        else {
            ZutatListPanel.seiten = 1;
        }
        // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
        this.setListObjects(new ArrayList<>());
        if (tempList != null) {
            tempList.forEach(zutat -> {
                String id = "";
                String showName = "";
                String editName = "";
                String deleteName = "";
                String bezeichnung = "";
                if (zutat.getId() != null && !zutat.getId().isEmpty()) {
                    id = zutat.getId();
                    showName = "show" + id;
                    editName = "edit" + id;
                    deleteName = "delete" + id;
                }
                if (zutat.getBezeichnung() != null && !zutat.getBezeichnung().isEmpty()) {
                    bezeichnung = zutat.getBezeichnung();
                }
                ZutatListObject zutatListObject = new ZutatListObject
                        (id, bezeichnung, showName, editName, deleteName);
                ZutatListPanel.this.getListObjects().add(zutatListObject);
            });
        }
    }
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    @Override
    public void produceList() {
        int i;
        // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
        // 1.Spalte = ID
        this.centerWestWestPanel.setLayout(new GridLayout(22, 1, 20, 3));
        JLabel listLabelCWW = new JLabel("ID");
        listLabelCWW.setFont(textFont3);
        listLabelCWW.setBorder(listMatteBorder1);
        this.centerWestWestPanel.add(listLabelCWW);
        if(this.getListObjects().size() > 0) {
            for (i = (ZutatListPanel.seite - 1) * 20; i < ZutatListPanel.seite * 20 && i < this.getListObjects().size(); i++) {
                ZutatListObject temp = (ZutatListObject) this.getListObjects().get(i);
                listLabelCWW = temp.getId();
                this.centerWestWestPanel.add(listLabelCWW);
            }
            for (; i < ZutatListPanel.seite * 20; i++) {
                this.centerWestWestPanel.add(new JLabel());
            }
        } else {
            for (i = 0; i < 20; i++) {
                this.centerWestWestPanel.add(new JLabel());
            }
        }
        listLabelCWW = new JLabel("");
        listLabelCWW.setBorder(listCombiBorder1);
        this.centerWestWestPanel.add(listLabelCWW);
        // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
        // 3.Spalte = Bezeichnung
        this.centerEastWestPanel.setLayout(new GridLayout(22, 1, 20, 3));
        JLabel listLabelCEW = new JLabel("Bezeichnung");
        listLabelCEW.setFont(textFont3);
        listLabelCEW.setBorder(listCombiBorder);
        this.centerEastWestPanel.add(listLabelCEW);
        if(this.getListObjects().size() > 0) {
            for (i = (ZutatListPanel.seite - 1) * 20; i < ZutatListPanel.seite * 20 && i < this.getListObjects().size(); i++) {
                ZutatListObject temp = (ZutatListObject) this.getListObjects().get(i);
                listLabelCEW = temp.getBezeichnung();
                this.centerEastWestPanel.add(listLabelCEW);
            }
            for (; i < ZutatListPanel.seite * 20; i++) {
                this.centerEastWestPanel.add(new JLabel());
            }
        } else {
            for (i = 0; i < 20; i++) {
                this.centerEastWestPanel.add(new JLabel());
            }
        }
        // letzte Zeile = Angabe der Seite/Seiten und Vorwärts- und Rückwärtsbutton
        JPanel listPanelCEW = new JPanel();
        listPanelCEW.setLayout(new GridLayout(1, 5, 20, 3));
        listPanelCEW.setBorder(listCombiBorder1);
        listPanelCEW.setOpaque(false);
        if(ZutatListPanel.seiten > 1) {
            if(ZutatListPanel.seite > 1 && ZutatListPanel.seite < ZutatListPanel.seiten) {
                listPanelCEW.add(new JLabel());
                listPanelCEW.add(this.getBackwardButton());
                listLabelCEW = new JLabel(ZutatListPanel.seite + " / " + ZutatListPanel.seiten);
                listLabelCEW.setFont(textFont3);
                listLabelCEW.setHorizontalAlignment(SwingConstants.CENTER);
                listPanelCEW.add(listLabelCEW);
                listPanelCEW.add(this.getForwardButton());
                listPanelCEW.add(new JLabel());
            } else if(ZutatListPanel.seite > 1 && ZutatListPanel.seite == ZutatListPanel.seiten) {
                listPanelCEW.add(new JLabel());
                listPanelCEW.add(this.getBackwardButton());
                listLabelCEW = new JLabel(ZutatListPanel.seite + " / " + ZutatListPanel.seiten);
                listLabelCEW.setFont(textFont3);
                listLabelCEW.setHorizontalAlignment(SwingConstants.CENTER);
                listPanelCEW.add(listLabelCEW);
                listPanelCEW.add(new JLabel());
                listPanelCEW.add(new JLabel());
            } else {
                listPanelCEW.add(new JLabel());
                listPanelCEW.add(new JLabel());
                listLabelCEW = new JLabel(ZutatListPanel.seite + " / " + ZutatListPanel.seiten);
                listLabelCEW.setFont(textFont3);
                listLabelCEW.setHorizontalAlignment(SwingConstants.CENTER);
                listPanelCEW.add(listLabelCEW);
                listPanelCEW.add(this.getForwardButton());
                listPanelCEW.add(new JLabel());
            }
        } else {
            listPanelCEW.add(new JLabel());
            listPanelCEW.add(new JLabel());
            listLabelCEW = new JLabel(String.valueOf(ZutatListPanel.seite));
            listLabelCEW.setFont(textFont3);
            listLabelCEW.setHorizontalAlignment(SwingConstants.CENTER);
            listPanelCEW.add(listLabelCEW);
            listPanelCEW.add(new JLabel());
            listPanelCEW.add(new JLabel());
        }
        this.centerEastWestPanel.add(listPanelCEW);
        // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
        // 4.Spalte = ActionButtons (show, edit, delete)
        this.centerEastEastPanel.setLayout(new GridLayout(22, 1, 20, 3));
        JLabel listLabelCEE = new JLabel("Aktion");
        listLabelCEE.setFont(textFont3);
        listLabelCEE.setBorder(listCombiBorder);
        this.centerEastEastPanel.add(listLabelCEE);
        if(this.getListObjects().size() > 0) {
            for (i = (ZutatListPanel.seite - 1) * 20; i < ZutatListPanel.seite * 20 && i < this.getListObjects().size(); i++) {
                ZutatListObject temp = (ZutatListObject) this.getListObjects().get(i);
                this.centerEastEastPanel.add(temp.getListButtonPanel());
            }
            for (; i < ZutatListPanel.seite * 20; i++) {
                this.centerEastEastPanel.add(new JLabel());
            }
        } else {
            for (i = 0; i < 20; i++) {
                this.centerEastEastPanel.add(new JLabel());
            }
        }
        listLabelCEE = new JLabel("");
        listLabelCEE.setBorder(listCombiBorder1);
        this.centerEastEastPanel.add(listLabelCEE);
        // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
        // SubScreenListPanel zusammensetzen
        this.getCenterWestPanel().setLayout(new GridLayout(1,1));
        this.getCenterWestPanel().add(this.centerWestWestPanel);

        this.getCenterEastPanel().setLayout(new BorderLayout());
        this.getCenterEastPanel().add(this.centerEastWestPanel, BorderLayout.WEST);
        this.getCenterEastPanel().add(this.centerEastEastPanel, BorderLayout.EAST);

        this.getCenterPanel().setLayout(new BorderLayout());
        this.getCenterPanel().add(this.getCenterWestPanel(), BorderLayout.WEST);
        this.getCenterPanel().add(this.getCenterEastPanel(), BorderLayout.EAST);

        this.getListPanel().setLayout(new BorderLayout());
        this.getListPanel().add(this.getNorthPanel(), BorderLayout.NORTH);
        this.getListPanel().add(this.getCenterPanel(), BorderLayout.CENTER);
    }
}
