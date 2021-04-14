package com.javahelps.kiPferL.viewing.mainFrame.subScreens;

import com.javahelps.kiPferL.model.kiTableObject.kiParameter.KiParameter;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * abstracte SuperKLasse alle SubScreen-ListPanels (linke Seite), stellt die abstrakte Methode produceList() zur Verfügung, die in allen SubScreen-ListPanels gebraucht wird
 *
 * @author Susanna Gruber
 * @version kiPferLGoesJPA2.0 2021.03.08       -->     Erweiterung um Zutaten
 * version kiPferLGoesJPA 2021.03.01       -->     JPA statt JDBC
 * version Alpha 3.0 2021.02.17
 * @since 1.14
 */
public abstract class SubScreenListPanel extends JPanel {

    private final JPanel northPanel, centerPanel, centerWestPanel, centerEastPanel;
    private final JComboBox<String> selectGruppenBox;
    private List<SubScreenListObject> listObjects;
    private final JButton forwardButton, backwardButton;
    // Rahmen (Konstante)
    private static final Border lineBorderForms = new LineBorder(Color.BLACK, 1);
    private static final EmptyBorder formEmptyBorder = new EmptyBorder(5, 10, 5, 10);
    // div. Font- und ColorObjekte
    private static final Font textFont1 = new Font("Arial", Font.BOLD, 18),  textFont2 = new Font("Arial", Font.PLAIN, 16);
    private static final Font buttonFont = new Font("Code2000", Font.PLAIN, 24);
    private static final Color buttonColor = new Color(204,255,204);

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Konstruktor (Dimensionen des Haupt- und des NordPanels, LayoutManager NordPanel und FormularTitel) des Panals werden übergeben,
    // das HauptPanel wird erzeugt, die SubPanels werden erzeugt und an das HauptPanel übergeben, die Komponenten werden erzeugt und in eine Liste gestellt
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public SubScreenListPanel(Dimension pPreferredSize, Dimension pPreferredSizeNorthPanel, int cwWidth, int ceWidth, String pListTitle, List<KiParameter> selectGruppen) {
        // HauptPanel erzeugen, Größe und Layout(BorderLayout) zuweisen und durchsichtig machen
        super();
        // NordPanel erzeugen, Größe, Rahmen und LayoutManager zuweisen und durchsichtig machen
        this.northPanel = new JPanel();
        this.northPanel.setPreferredSize(pPreferredSizeNorthPanel);
        this.northPanel.setLayout(new GridLayout(4,2,20,3));
        this.northPanel.setOpaque(false);
        // CenterUnterPanel erzeugen, Größe und Rahmen zuweisen und durchsichtig machen
        // UnterPanelWest für Liste
        this.centerWestPanel = new JPanel();
        this.centerWestPanel.setPreferredSize
                (new Dimension(cwWidth, pPreferredSize.height - pPreferredSizeNorthPanel.height));
        this.centerWestPanel.setOpaque(false);
        // UnterPanelEast für Liste
        this.centerEastPanel = new JPanel();
        this.centerEastPanel.setPreferredSize
                (new Dimension(ceWidth, pPreferredSize.height - pPreferredSizeNorthPanel.height));
        this.centerEastPanel.setOpaque(false);
        // CenterPanel erzeugen, Größe und Rahmen zuweisen und durchsichtig machen
        this.centerPanel = new JPanel();
        this.centerPanel.setPreferredSize(new Dimension(pPreferredSize.width, pPreferredSize.height - pPreferredSizeNorthPanel.height));
        this.centerPanel.setOpaque(false);
        // UnterPanelWest und -East auf das centerPanel setzen
        this.centerPanel.setLayout(new BorderLayout());
        this.centerPanel.add(this.centerWestPanel, BorderLayout.WEST);
        this.centerPanel.add(this.centerEastPanel, BorderLayout.EAST);
        // ...und auf das HauptPanel setzen
        this.setPreferredSize(pPreferredSize);
        this.setLayout(new BorderLayout());
        this.setBorder(formEmptyBorder);
        this.setOpaque(false);
        this.add(this.northPanel, BorderLayout.NORTH);
        this.add(this.centerPanel, BorderLayout.CENTER);

        // ParameterAuswahlJComboBox
        // Liste der ParameterGruppen, die ausgewählt werden können
        // String[] für JComboBox erzeugen
        final int[] i = {0};
        String[] finalParameterGruppen = new String[selectGruppen.size()];
        selectGruppen.forEach(kiParameter -> {
            finalParameterGruppen[i[0]] = "  " + kiParameter.getBezeichnung();
            ++i[0];
        });
        this.selectGruppenBox = new JComboBox<>(                                                                        // JComboBox für ParameterGruppen
                Objects.requireNonNullElseGet(finalParameterGruppen, () -> new String[]{" "}));
        this.selectGruppenBox.setFont(textFont2);
        this.selectGruppenBox.setBorder(lineBorderForms);
        this.selectGruppenBox.setEditable(true);
        this.selectGruppenBox.getEditor().getEditorComponent().setBackground(buttonColor);

        // SubScreenListPanel Kopf
        JLabel listLabel1 = new JLabel(pListTitle);
        listLabel1.setFont(textFont1);
        this.northPanel.add(listLabel1);
        List<JComponent> tempList = IntStream.range(0, 3).mapToObj(i1 -> new JLabel()).collect(Collectors.toList());    // DummyLabels in einer temporären Liste erzeugen
        tempList.forEach(this.northPanel::add);
        this.northPanel.add(this.selectGruppenBox);
        tempList = IntStream.range(0, 3).mapToObj(i1 -> new JLabel()).collect(Collectors.toList());
        tempList.forEach(this.northPanel::add);

        // SubScreenListPanel Vorwärts- und Rückwärtsbutton
        int uniCodePoint = Integer.parseInt("21D2",16);
        this.forwardButton = new JButton(new String(Character.toChars(uniCodePoint)));
        this.forwardButton.setFont(buttonFont);
        this.forwardButton.setName("forward");
        this.forwardButton.setBackground(buttonColor);
        this.forwardButton.setBorder(lineBorderForms);

        uniCodePoint = Integer.parseInt("21D0",16);
        this.backwardButton = new JButton(new String(Character.toChars(uniCodePoint)));
        this.backwardButton.setFont(buttonFont);
        this.backwardButton.setName("backward");
        this.backwardButton.setBackground(buttonColor);
        this.backwardButton.setBorder(lineBorderForms);
    }

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Getter und Setter, damit aus den SubKlassen auf die Komponenten zugegriffen werden kann
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public JPanel getListPanel() {
        return this;
    }
    public JPanel getNorthPanel() {
        return this.northPanel;
    }
    public JPanel getCenterPanel() {
        return this.centerPanel;
    }
    public JPanel getCenterWestPanel() {
        return this.centerWestPanel;
    }
    public JPanel getCenterEastPanel() {
        return this.centerEastPanel;
    }
    public JComboBox<String> getSelectGruppenBox() {
        return this.selectGruppenBox;
    }
    public List<SubScreenListObject> getListObjects() {
        return this.listObjects;
    }
    public JButton getForwardButton() {
        return forwardButton;
    }
    public JButton getBackwardButton() {
        return backwardButton;
    }

    public void setListObjects(List<SubScreenListObject> listObjects) {
        this.listObjects = listObjects;
    }

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // abstrakte Methoden, die in den SubKlassen benötigt werden
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public abstract void producePanels();
    public abstract void findListObjects();
    public abstract void produceList();
}
