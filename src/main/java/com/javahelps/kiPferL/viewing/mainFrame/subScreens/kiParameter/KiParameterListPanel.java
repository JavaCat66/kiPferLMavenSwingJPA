package com.javahelps.kiPferL.viewing.mainFrame.subScreens.kiParameter;

import com.javahelps.kiPferL.controlling.mainFrame.MainFrameWindowAdapter;
import com.javahelps.kiPferL.jpa.kiParameter.KiParameterJPA;
import com.javahelps.kiPferL.model.kiTableObject.kiParameter.KiParameter;
import com.javahelps.kiPferL.viewing.mainFrame.subScreens.SubScreenListObject;
import com.javahelps.kiPferL.viewing.mainFrame.subScreens.SubScreenListPanel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ParameterSubScreen-ListPanel (linke Seite)
 *
 * @author Susanna Gruber
 * @version kiPferLGoesJPA 2021.03.01       -->     JPA statt JDBC
 * version Alpha 3.0 2021.02.17
 * @since 1.14
 */
public class KiParameterListPanel extends SubScreenListPanel {

    // Seite/Seiten der Liste
    private static int seite;
    private static int seiten;
    // Ausgewählte ParameterGruppe
    private final String selectedParameterGruppe, selectedParameterGruppenKuerzel;
    // SpezialUnterPanels für die Liste
    private JPanel centerWestWestPanel, centerWestEastPanel, centerEastWestPanel, centerEastEastPanel;
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
    public KiParameterListPanel(Dimension pPreferredSize, Dimension pPreferredSizeNorthPanel, String pListTitle, List<KiParameter> selectGruppen,
                                String selectedParameterGruppe, String selectedParameterGruppenKuerzel) {
        // Aufruf des SuperKlassenKonstruktors
        super(pPreferredSize, pPreferredSizeNorthPanel, 185, 411, pListTitle, selectGruppen);

        // default ParameterGruppe
        this.selectedParameterGruppe = selectedParameterGruppe;
        this.selectedParameterGruppenKuerzel = selectedParameterGruppenKuerzel;
        // Dimensionen
        this.preferredSize = pPreferredSize;
        this.preferredSizeNorthPanel = pPreferredSizeNorthPanel;
        // der ComboBox ein selectiertes Objekt zuweisen
        this.getSelectGruppenBox().setSelectedItem("  " + this.selectedParameterGruppe);
        // Panels produzieren
        this.producePanels();
        // Seite setzen
        KiParameterListPanel.seite = 1;
        // Liste produzieren
        this.findListObjects();
        this.produceList();
    }
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // eigener Konstruktor zum Blättern in der Liste, Seite und Listenobjekte werden übergeben
    public KiParameterListPanel(Dimension pPreferredSize, Dimension pPreferredSizeNorthPanel, String pListTitle, List<KiParameter> selectGruppen,
                                String selectedParameterGruppe, String selectedParameterGruppenKuerzel, int seite, List<SubScreenListObject> listObjects) {
        // Aufruf des SuperKlassenKonstruktors
        super(pPreferredSize, pPreferredSizeNorthPanel, 185, 411, pListTitle, selectGruppen);

        // default ParameterGruppe
        this.selectedParameterGruppe = selectedParameterGruppe;
        this.selectedParameterGruppenKuerzel = selectedParameterGruppenKuerzel;
        // Dimensionen
        this.preferredSize = pPreferredSize;
        this.preferredSizeNorthPanel = pPreferredSizeNorthPanel;
        // der ComboBox ein selectiertes Objekt zuweisen
        this.getSelectGruppenBox().setSelectedItem("  " + this.selectedParameterGruppe);
        // listenObjekte übergeben
        this.setListObjects(listObjects);
        // Panels produzieren
        this.producePanels();
        // Seite setzen
        KiParameterListPanel.seite = seite;
        // Liste produzieren
        this.produceList();
    }

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Getter und Setter zum Ändern der angezeigten Seite im ActionListener
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public static void setSeite(int seite) {
        KiParameterListPanel.seite = seite;
    }
    public static int getSeite() {
        return KiParameterListPanel.seite;
    }

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Methoden zur Ausgabe der Liste (werden auch von anderen Klassen (zb. controlling.mainFrame.subScreens.kiParameter.KiParameterSubScreenItemController) aufgerufen.
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    @Override
    public void producePanels() {
        // UnterPanelWestWest für Liste
        this.centerWestWestPanel = new JPanel();
        this.centerWestWestPanel.setPreferredSize
                (new Dimension(80, this.preferredSize.height - this.preferredSizeNorthPanel.height));
        this.centerWestWestPanel.setOpaque(false);
        // UnterPanelWesteast für Liste
        this.centerWestEastPanel = new JPanel();
        this.centerWestEastPanel.setPreferredSize
                (new Dimension(105, this.preferredSize.height - this.preferredSizeNorthPanel.height));
        this.centerWestEastPanel.setBorder(listMatteBorder);
        this.centerWestEastPanel.setOpaque(false);
        // UnterPanelEastWest für Liste
        this.centerEastWestPanel = new JPanel();
        this.centerEastWestPanel.setPreferredSize
                (new Dimension(305, this.preferredSize.height - this.preferredSizeNorthPanel.height));
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
        List<KiParameter> tempList = KiParameterJPA.findAllKiParameterByPraefixAndParametergruppe(
                MainFrameWindowAdapter.getEntityManagerFactory(), this.selectedParameterGruppenKuerzel, this.selectedParameterGruppe);
        if (tempList != null) {
            KiParameterListPanel.seiten = tempList.size() / 20;
            if (tempList.size() % 20 != 0) {
                ++KiParameterListPanel.seiten;
            }
            KiParameterListPanel.seite = KiParameterListPanel.seiten;
        }
        else {
            KiParameterListPanel.seiten = 1;
        }
        // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
        this.setListObjects(new ArrayList<>());
        if (tempList != null) {
            tempList.forEach(kiParameter -> {
                String id = "";
                String showName = "";
                String editName = "";
                String deleteName = "";
                String kuerzel = "";
                String bezeichnung = "";
                if (kiParameter.getId() != null && !kiParameter.getId().isEmpty()) {
                    id = kiParameter.getId();
                    showName = "show" + id;
                    editName = "edit" + id;
                    deleteName = "delete" + id;
                }
                if (kiParameter.getKuerzel() != null && !kiParameter.getKuerzel().isEmpty()) {
                    kuerzel = kiParameter.getKuerzel();
                }
                if (kiParameter.getBezeichnung() != null && !kiParameter.getBezeichnung().isEmpty()) {
                    bezeichnung = kiParameter.getBezeichnung();
                }
                KiParameterListObject kiParameterListObject = new KiParameterListObject
                        (id, bezeichnung, showName, editName, deleteName, kuerzel);
                KiParameterListPanel.this.getListObjects().add(kiParameterListObject);
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
            for (i = (KiParameterListPanel.seite - 1) * 20; i < KiParameterListPanel.seite * 20 && i < this.getListObjects().size(); i++) {
                KiParameterListObject temp = (KiParameterListObject) this.getListObjects().get(i);
                listLabelCWW = temp.getId();
                this.centerWestWestPanel.add(listLabelCWW);
            }
            for (; i < KiParameterListPanel.seite * 20; i++) {
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
        // 2.Spalte = Kürzel
        this.centerWestEastPanel.setLayout(new GridLayout(22, 1, 20, 3));
        JLabel listLabelCWE = new JLabel("Kürzel");
        listLabelCWE.setFont(textFont3);
        listLabelCWE.setBorder(listCombiBorder);
        this.centerWestEastPanel.add(listLabelCWE);
        if(this.getListObjects().size() > 0) {
            for (i = (KiParameterListPanel.seite - 1) * 20; i < KiParameterListPanel.seite * 20 && i < this.getListObjects().size(); i++) {
                KiParameterListObject temp = (KiParameterListObject) this.getListObjects().get(i);
                listLabelCWE = temp.getKuerzel();
                this.centerWestEastPanel.add(listLabelCWE);
            }
            for (; i < KiParameterListPanel.seite * 20; i++) {
                this.centerWestEastPanel.add(new JLabel());
            }
        } else {
            for (i = 0; i < 20; i++) {
                this.centerWestEastPanel.add(new JLabel());
            }
        }
        listLabelCWE = new JLabel("");
        listLabelCWE.setBorder(listCombiBorder1);
        this.centerWestEastPanel.add(listLabelCWE);
        // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
        // 3.Spalte = Bezeichnung
        this.centerEastWestPanel.setLayout(new GridLayout(22, 1, 20, 3));
        JLabel listLabelCEW = new JLabel("Bezeichnung");
        listLabelCEW.setFont(textFont3);
        listLabelCEW.setBorder(listCombiBorder);
        this.centerEastWestPanel.add(listLabelCEW);
        if(this.getListObjects().size() > 0) {
            for (i = (KiParameterListPanel.seite - 1) * 20; i < KiParameterListPanel.seite * 20 && i < this.getListObjects().size(); i++) {
                KiParameterListObject temp = (KiParameterListObject) this.getListObjects().get(i);
                listLabelCEW = temp.getBezeichnung();
                this.centerEastWestPanel.add(listLabelCEW);
            }
            for (; i < KiParameterListPanel.seite * 20; i++) {
                this.centerEastWestPanel.add(new JLabel());
            }
        } else {
            for (i = 0; i < 20; i++) {
                this.centerEastWestPanel.add(new JLabel());
            }
        }
        // letzte Zeile = Angabe der Seite/Seiten und Vorwärts- und Rückwärtsbutton
        JPanel listPanelCEW = new JPanel();
        listPanelCEW.setLayout(new GridLayout(1,5, 20, 3));
        listPanelCEW.setBorder(listCombiBorder1);
        listPanelCEW.setOpaque(false);
        if(KiParameterListPanel.seiten > 1) {
            if(KiParameterListPanel.seite > 1 && KiParameterListPanel.seite < KiParameterListPanel.seiten) {
                listPanelCEW.add(new JLabel());
                listPanelCEW.add(this.getBackwardButton());
                listLabelCEW = new JLabel(KiParameterListPanel.seite + " / " + KiParameterListPanel.seiten);
                listLabelCEW.setFont(textFont3);
                listLabelCEW.setHorizontalAlignment(SwingConstants.CENTER);
                listPanelCEW.add(listLabelCEW);
                listPanelCEW.add(this.getForwardButton());
                listPanelCEW.add(new JLabel());
            } else if(KiParameterListPanel.seite > 1 && KiParameterListPanel.seite == KiParameterListPanel.seiten) {
                listPanelCEW.add(new JLabel());
                listPanelCEW.add(this.getBackwardButton());
                listLabelCEW = new JLabel(KiParameterListPanel.seite + " / " + KiParameterListPanel.seiten);
                listLabelCEW.setFont(textFont3);
                listLabelCEW.setHorizontalAlignment(SwingConstants.CENTER);
                listPanelCEW.add(listLabelCEW);
                listPanelCEW.add(new JLabel());
                listPanelCEW.add(new JLabel());
            } else {
                listPanelCEW.add(new JLabel());
                listPanelCEW.add(new JLabel());
                listLabelCEW = new JLabel(KiParameterListPanel.seite + " / " + KiParameterListPanel.seiten);
                listLabelCEW.setFont(textFont3);
                listLabelCEW.setHorizontalAlignment(SwingConstants.CENTER);
                listPanelCEW.add(listLabelCEW);
                listPanelCEW.add(this.getForwardButton());
                listPanelCEW.add(new JLabel());
            }
        } else {
            listPanelCEW.add(new JLabel());
            listPanelCEW.add(new JLabel());
            listLabelCEW = new JLabel(String.valueOf(KiParameterListPanel.seite));
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
            for (i = (KiParameterListPanel.seite - 1) * 20; i < KiParameterListPanel.seite * 20 && i < this.getListObjects().size(); i++) {
                KiParameterListObject temp = (KiParameterListObject) this.getListObjects().get(i);
                this.centerEastEastPanel.add(temp.getListButtonPanel());
            }
            for(; i < KiParameterListPanel.seite * 20; i++) {
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
        this.getCenterWestPanel().setLayout(new BorderLayout());
        this.getCenterWestPanel().add(this.centerWestWestPanel, BorderLayout.WEST);
        this.getCenterWestPanel().add(this.centerWestEastPanel, BorderLayout.EAST);

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
