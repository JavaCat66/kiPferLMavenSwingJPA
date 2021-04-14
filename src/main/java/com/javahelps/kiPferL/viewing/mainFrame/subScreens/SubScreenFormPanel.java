package com.javahelps.kiPferL.viewing.mainFrame.subScreens;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * SuperKlasse aller FormularPanels (rechte Seite) eines UnterProgramms = SubScreen (für Parameter, Zutat, Rezept)
 *
 * @author Susanna Gruber
 * @version kiPferLGoesJPA 2021.03.01       -->     JPA statt JDBC
 * version Alpha 3.0 2021.02.15
 * @since 1.14
 */
public class SubScreenFormPanel extends JPanel {

    // boolean, der anzeigt, ob das Formular auf Neueintrag oder Änderung geschalten ist
    private boolean formEdit = false;
    // alle Komponenten, die in jedem FormPanel gebraucht werden, werden deklariert.
    private final JPanel northPanel, centerPanel;
    private final JLabel titleLabel, dialogLabel, idLabel, bezeichnungTitleLabel, infoTextTitleLabel, copyRightLabel;
    private final JTextField bezeichnungTextField;
    private final JTextArea infoTextTextArea;
    private final JButton formButton;
    private final List<JComponent> formFields;
    // div. Font- und ColorObjekte
    private static final Font textFont1 = new Font("Arial", Font.BOLD, 18), textFont2 = new Font("Arial", Font.PLAIN, 16),
            textFont3 = new Font("Arial", Font.BOLD, 16);
    private static final Color buttonColor = new Color(204,255,204);
    // Rahmen (Konstante)
    private static final EmptyBorder formEmptyBorderNorth = new EmptyBorder(5, 10, 0, 10);
    private static final EmptyBorder formEmptyBorderCenter = new EmptyBorder(0, 10, 5, 10);
    private static final Border formMatteBorder = BorderFactory.createMatteBorder(0, 2, 0, 0, Color.BLACK);
    private static final Border formCombiBorderNorth = BorderFactory.createCompoundBorder(formMatteBorder, formEmptyBorderNorth);
    private static final Border formCombiBorderCenter = BorderFactory.createCompoundBorder(formMatteBorder, formEmptyBorderCenter);
    private static final Border lineBorderForms = new LineBorder(Color.BLACK, 1);

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Konstruktor (Dimensionen des Haupt- und des NordPanels, LayoutManager NordPanel und FormularTitel) des Panals werden übergeben,
    // das HauptPanel wird erzeugt, die SubPanels werden erzeugt und an das HauptPanel übergeben, die Komponenten werden erzeugt und in eine Liste gestellt
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public SubScreenFormPanel
        (Dimension pPreferredSize, Dimension pPreferredSizeNorthPanel, LayoutManager pLayoutManagerNorthPanel, String pFormTitle) {
        // HauptPanel erzeugen, Größe und Layout(BorderLayout) zuweisen und durchsichtig machen
        super();
        // NordPanel erzeugen, Größe, Rahmen und LayoutManager zuweisen und durchsichtig machen
        this.northPanel = new JPanel();
        this.northPanel.setPreferredSize(pPreferredSizeNorthPanel);
        this.northPanel.setLayout(pLayoutManagerNorthPanel);
        this.northPanel.setBorder(formCombiBorderNorth);
        this.northPanel.setOpaque(false);
        // NordPanel erzeugen, Größe und Rahmen zuweisen und durchsichtig machen
        this.centerPanel = new JPanel();
        this.centerPanel.setPreferredSize(new Dimension(pPreferredSize.width, pPreferredSize.height - pPreferredSizeNorthPanel.height));
        this.centerPanel.setBorder(formCombiBorderCenter);
        this.centerPanel.setOpaque(false);
        // ...und auf das HauptPanel setzen
        this.setPreferredSize(pPreferredSize);
        this.setLayout(new BorderLayout());
        this.setOpaque(false);
        this.add(this.northPanel, BorderLayout.NORTH);
        this.add(this.centerPanel, BorderLayout.CENTER);

        // SammelListe für die FormularKomponenten, Komponenten erzeugen und in Liste stellen
        this.formFields = new ArrayList<>();

        this.titleLabel = new JLabel(pFormTitle);
        this.formFields.add(this.titleLabel);

        List<JLabel> tempList = IntStream.range(0, 2).mapToObj(i -> new JLabel()).collect(Collectors.toList());         // DummyLabels in einer temporären Liste erzeugen
        this.formFields.addAll(tempList);                                                                               // DummyLabels in SammelListe füllen

        this.dialogLabel = new JLabel();
        this.formFields.add(this.dialogLabel);

        JLabel idTitleLabel = new JLabel("ID: ");
        this.formFields.add(idTitleLabel);

        this.idLabel = new JLabel();
        this.formFields.add(this.idLabel);

        this.bezeichnungTitleLabel = new JLabel("Bezeichnung: ");
        this.formFields.add(this.bezeichnungTitleLabel);

        this.bezeichnungTextField = new JTextField();
        this.formFields.add(this.bezeichnungTextField);

        this.infoTextTitleLabel = new JLabel("Infotext: ");
        this.formFields.add(this.infoTextTitleLabel);

        this.infoTextTextArea = new JTextArea(2, 20);
        this.formFields.add(this.infoTextTextArea);

        this.formButton = new JButton();
        this.formFields.add(this.formButton);

        this.copyRightLabel = new JLabel("\u00a9  Susanna Gruber");
        this.copyRightLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        this.formFields.add(this.copyRightLabel);

        // den FormKomponenten Font, Color, Border und KeyListener!!!!! zuweisen
        this.formFields.forEach((JComponent jComponent) -> {
            if(jComponent instanceof JLabel && jComponent.equals(this.titleLabel)) {
                jComponent.setFont(textFont1);
            }
            else if((jComponent instanceof JLabel && !((JLabel) jComponent).getText().isEmpty()
                    && !jComponent.equals(this.idLabel) && !jComponent.equals(this.copyRightLabel) && !jComponent.equals(this.titleLabel))
                    || jComponent instanceof JButton) {
                jComponent.setFont(textFont3);
            } else {
                if(!(jComponent instanceof JLabel && ((JLabel) jComponent).getText().isEmpty())
                    || (jComponent.equals(this.idLabel) || jComponent.equals(this.copyRightLabel))) {
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
            if(jComponent instanceof JButton) {
                jComponent.setBackground(buttonColor);
            }
        });
        // Komponenten, die nicht gleich am Anfang ausgegeben werden (spezielle Komponenten der SubKlassen davor!),
        // werden wieder aus der Liste gelöscht (und behalten ihre Eigenschaften bei)
        this.formFields.remove(this.bezeichnungTitleLabel);
        this.formFields.remove(this.bezeichnungTextField);
        this.formFields.remove(this.infoTextTitleLabel);
        this.formFields.remove(this.infoTextTextArea);
        this.formFields.remove(this.formButton);
        this.formFields.remove(this.copyRightLabel);
    }

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Getter und Setter, damit aus den SubKlassen auf die Komponenten zugegriffen werden kann
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public boolean isFormEdit() {
        return this.formEdit;
    }
    public JPanel getNorthPanel() {
        return this.northPanel;
    }
    public JPanel getCenterPanel() {
        return this.centerPanel;
    }
    public List<JComponent> getFormFields() {
        return this.formFields;
    }
    public JLabel getDialogLabel() {
        return this.dialogLabel;
    }
    public JLabel getTitleLabel() {
        return this.titleLabel;
    }
    public JLabel getIdLabel() {
        return this.idLabel;
    }
    public JLabel getBezeichnungTitleLabel() {
        return this.bezeichnungTitleLabel;
    }
    public JLabel getInfoTextTitleLabel() {
        return this.infoTextTitleLabel;
    }
    public JLabel getCopyRightLabel() {
        return this.copyRightLabel;
    }
    public JTextField getBezeichnungTextField() {
        return this.bezeichnungTextField;
    }
    public JTextArea getInfoTextTextArea() {
        return this.infoTextTextArea;
    }
    public JButton getFormButton() {
        return this.formButton;
    }

    public void setFormEdit(boolean formEdit) {
        this.formEdit = formEdit;
    }
}
