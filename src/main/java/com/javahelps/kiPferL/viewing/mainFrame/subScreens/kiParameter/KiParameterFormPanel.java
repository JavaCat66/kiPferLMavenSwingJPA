package com.javahelps.kiPferL.viewing.mainFrame.subScreens.kiParameter;

import com.javahelps.kiPferL.controlling.mainFrame.MainFrameWindowAdapter;
import com.javahelps.kiPferL.jpa.kiParameter.KiParameterJPA;
import com.javahelps.kiPferL.model.kiUtils.kiStringUtils.KiStringUtils;
import com.javahelps.kiPferL.viewing.mainFrame.subScreens.SubScreenFormPanel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * ParameterSubScreen-FormPanel (rechte Seite)
 *
 * @author Susanna Gruber
 * @version kiPferLGoesJPA 2021.03.01       -->     JPA statt JDBC
 * version Alpha 3.0 2021.02.17
 * @since 1.14
 */
public class KiParameterFormPanel extends SubScreenFormPanel {

    // Ausgewählte ParameterGruppe
    private final String selectedParameterGruppe, selectedParameterGruppenKuerzel;
    private final JTextField kuerzelTextField;

    // div. FontObjekte (Konstante)
    private static final Font textFont2 = new Font("Arial", Font.PLAIN, 16), textFont3 = new Font("Arial", Font.BOLD, 16);
    // Rahmen (Konstante)
    private static final Border lineBorderForms = new LineBorder(Color.BLACK, 1);
    private static final EmptyBorder textAreaEmptyBorder = new EmptyBorder(2, 0, 0, 0);

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // KonstruktorParameter werden an die SuperKlasse weitergegeben oder gesetzt (default ParameterGruppe)
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public KiParameterFormPanel
            (Dimension pPreferredSize, Dimension pPreferredSizeNorthPanel, LayoutManager pLayoutManagerNorthPanel, String pFormTitle,
             String selectedParameterGruppe, String selectedParameterGruppenKuerzel) {
        // Aufruf des SuperKlassenKonstruktors
        super(pPreferredSize, pPreferredSizeNorthPanel, pLayoutManagerNorthPanel, pFormTitle);

        // default ParameterGruppe
        this.selectedParameterGruppe = selectedParameterGruppe;
        this.selectedParameterGruppenKuerzel = selectedParameterGruppenKuerzel;

        // SpezialKomponenten für ParameterFormular
        JLabel kuerzelLabel = new JLabel("Kürzel: ");
        kuerzelLabel.setFont(textFont3);
        this.getFormFields().add(kuerzelLabel);

        this.kuerzelTextField = new JTextField();
        this.kuerzelTextField.setFont(textFont2);
        this.kuerzelTextField.setBorder(lineBorderForms);
        this.getFormFields().add(kuerzelTextField);

        // restliche Komponenten aus SuperKlasse in die SammelMappe stellen
        this.getFormFields().add(this.getBezeichnungTitleLabel());
        this.getFormFields().add(this.getBezeichnungTextField());

        this.getFormFields().add(this.getInfoTextTitleLabel());
        this.getFormFields().add(this.getInfoTextTextArea());

        List<JLabel> tempList = IntStream.range(0, 3).mapToObj(i -> new JLabel()).collect(Collectors.toList());         // DummyLabels in einer temporären Liste erzeugen
        this.getFormFields().addAll(tempList);                                                                          // DummyLabels in SammelListe füllen
        this.getFormButton().setText("Parameter anlegen");
        this.getFormFields().add(this.getFormButton());
        tempList = IntStream.range(0, 31).mapToObj(i -> new JLabel()).collect(Collectors.toList());                     // DummyLabels in einer temporären Liste erzeugen
        this.getFormFields().addAll(tempList);                                                                          // DummyLabels in SammelListe füllen
        this.getFormFields().add(this.getCopyRightLabel());

        // **************************************************************************************************************************************************************
        // NordPanel default füllen
        // **************************************************************************************************************************************************************
        final int[] y = {0};
        this.getFormFields().stream().filter(jComponent -> {
            ++y[0];
            return y[0] <= ((GridLayout)this.getNorthPanel().getLayout()).getRows() * ((GridLayout)this.getNorthPanel().getLayout()).getColumns();
        }).forEach(jComponent -> {
            // letzte ID der gewählten Parametergruppe aus der DB-Tabelle KiParameter abfragen, um eins erhöhen und dem entsprechenden Label übergeben
            int x = KiParameterJPA.findMaxIdKiParameter(
                    MainFrameWindowAdapter.getEntityManagerFactory(), this.selectedParameterGruppenKuerzel, this.selectedParameterGruppe);
            KiParameterFormPanel.this.getIdLabel().setText(KiStringUtils.idIncrement(KiParameterFormPanel.this.selectedParameterGruppenKuerzel, ++x));
            // Komponenten des oberen FormularTeils aus der Sammelliste auf das Panel stellen
            KiParameterFormPanel.this.getNorthPanel().add(jComponent);
        });
        // **************************************************************************************************************************************************************
        // CenterPanel default füllen
        // **************************************************************************************************************************************************************
        // centerNord = TextArea
        JPanel centerNorthPanel = new JPanel();
        centerNorthPanel.setPreferredSize(new Dimension(this.getWidth(), ((this.getPreferredSize().height - 10) / 26) * 3 - 7));
        centerNorthPanel.setLayout(new GridLayout(1,2, 20, 3));
        centerNorthPanel.setOpaque(false);
        JPanel temp = new JPanel();
        temp.setLayout(new GridLayout(3,1, 20, 3));
        temp.setOpaque(false);
        temp.add(this.getInfoTextTitleLabel());
        temp.add(new JLabel());
        temp.add(new JLabel());
        centerNorthPanel.add(temp);
        JScrollPane pane = new JScrollPane();
        pane.setBorder(textAreaEmptyBorder);
        centerNorthPanel.add(new JScrollPane(this.getInfoTextTextArea()));

        // centerCenter = restliche Zeilen
        JPanel centerCenterPanel = new JPanel();
        centerCenterPanel.setPreferredSize
                (new Dimension(this.getWidth(),
                        ((this.getCenterPanel().getPreferredSize().height - centerNorthPanel.getPreferredSize().height) / 18) * (26 - ((GridLayout)this.getNorthPanel().getLayout()).getRows() - 3) + 5));
        centerCenterPanel.setLayout(
                new GridLayout(
                        26 - ((GridLayout)this.getNorthPanel().getLayout()).getRows() - 3, 2, 20, 3));
        centerCenterPanel.setOpaque(false);

        y[0] = 0;
        this.getFormFields().stream().filter(jComponent -> {
            ++y[0];
            return y[0] >= 26 - ((GridLayout)this.getNorthPanel().getLayout()).getRows() - 8;
        }).forEach(centerCenterPanel::add);

        // centerNord und centerCenter auf das CenterPanel setzen
        this.getCenterPanel().setLayout(new BorderLayout());
        this.getCenterPanel().add(centerNorthPanel, BorderLayout.NORTH);
        this.getCenterPanel().add(centerCenterPanel, BorderLayout.CENTER);
    }
    public JTextField getKuerzelTextField() {
        return this.kuerzelTextField;
    }
}
