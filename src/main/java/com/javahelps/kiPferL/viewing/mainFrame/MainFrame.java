package com.javahelps.kiPferL.viewing.mainFrame;

import com.javahelps.kiPferL.controlling.mainFrame.MainFrameWindowAdapter;
import com.javahelps.kiPferL.controlling.mainFrame.MainFrameActionController;
import com.javahelps.kiPferL.controlling.mainFrame.MainFrameFocusController;
import com.javahelps.kiPferL.viewing.mainFrame.subScreens.kiParameter.KiParameterSubScreen;
import com.javahelps.kiPferL.viewing.mainFrame.subScreens.kiParameter.KiParameterFormPanel;
import com.javahelps.kiPferL.viewing.mainFrame.subScreens.kiZutat.ZutatSubScreen;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * HauptFrame des SWING-Programms kiPferL, welches die Möglichkeit bietet,
 * Parameter(Allergene, Diättauglichkeiten, RezeptKategorien, etc.), Zutaten und Rezepte zu erstellen und damit eine RezepteSammlung(KochBuch).
 * PassWortGeschützt.
 *
 * @author Susanna Gruber
 * @version kiPferLGoesJPA 2021.03.01       -->     JPA statt JDBC
 * version Alpha 3.0 2021.02.15        -->     Einführung einer KlassenHierarchie für die UnterProgramme = SubScreens
 * version Alpha 2.0 2021.02.08
 * @since 1.14
 */
public class MainFrame extends JFrame {
    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // Variable, die im ganzen Programm benützt werden
    private final MyImagePanel mainPanel;
    private JPanel menuePanel;
    private final JLabel copyRight_Label;
    private List<JComponent> panel_components;
    // div. Font- und ColorObjekte
    private static final Font textFont1 = new Font("Arial", Font.BOLD, 18), textFont2 = new Font("Arial", Font.PLAIN, 16),
            textFontStart = new Font("Arial", Font.BOLD, 25);
    private static final Color textColor1 = Color.WHITE, textColor2 = Color.BLACK, buttonColor = new Color(204,255,204);
    // Listener
    private final MainFrameActionController myActionController;
    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // StartBildschirm
    private final JButton startButton_0_0, startButton_0_1, startButton_1_0, startButton_1_1, startButton_2_0, startButton_2_1;
    private BufferedImage startImage_0_0, startImage_0_1, startImage_1_0, startImage_1_1, startImage_2_0, startImage_2_1;               // auch für Menü
    private static final Border lineBorderButtonStart = new LineBorder(Color.BLACK, 3);
    private HashMap<String, MyImagePanel> menuePanelMap;
    private final LinkedHashMap<String, JButton> menueButtons;
    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // ParameterBildschirm
    private KiParameterSubScreen kiParameterSubScreen;
    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // ZutatBildschirm
    private ZutatSubScreen zutatSubScreen;
    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Konstruktor (1.Form = Login)
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public MainFrame() {

        // $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        // FokusListener erzeugen (manche Komponenten werden beim Erzeugen sofort angemeldet)
        // $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        MainFrameFocusController myFocusController = new MainFrameFocusController();

        // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
        // Loginbildschirm
        // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
        this.panel_components = new ArrayList<>();
        // *************************************************************************************************************************************************************************
        // Panel BorderLayout.CENTER (GridLayout(5, 4))
        // *************************************************************************************************************************************************************************
        // Linke Spalte des LoginForms (GridLayout 4, 1)
        // Komponenten (JLabel) in eine Liste füllen
        this.panel_components.add(new JLabel());
        this.panel_components.add(new JLabel("          Mitglied"));
        this.panel_components.add(new JLabel("          Passwort"));
        this.panel_components.add(new JLabel());
        // Lambda   -->     den JLabels in der Liste Schrift und SchriftFarbe übergeben
        this.panel_components.stream().filter(jComponent -> !((JLabel)jComponent).getText().isEmpty()).forEach(jComponent -> {
            jComponent.setFont(textFont1);
            jComponent.setForeground(textColor1);
        });
        // JPanel erzeugen
        JPanel panel_center_0_0 = new JPanel(new GridLayout(4,1,5,5));
        // Panel ist unsichtbar     -->     Hintergrundbild
        panel_center_0_0.setOpaque(false);
        // MethodReference      -->     JLabel auf JPanel setzen
        this.panel_components.forEach(panel_center_0_0::add);
        // -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        // Rechte Spalte des LoginForms (GridLayout 4, 1)
        this.panel_components = new ArrayList<>();

        JLabel loginLabel_0_1 = new JLabel("Login");
        loginLabel_0_1.setFont(textFont1);
        loginLabel_0_1.setForeground(textColor1);
        this.panel_components.add(loginLabel_0_1);

        // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        // LoginBildschirm
        JTextField loginTextField_1_1 = new JTextField();
        loginTextField_1_1.addFocusListener(myFocusController);
        this.panel_components.add(loginTextField_1_1);

        JPasswordField loginPasswordField_2_1 = new JPasswordField();
        loginPasswordField_2_1.addFocusListener(myFocusController);
        this.panel_components.add(loginPasswordField_2_1);

        JButton loginButton_3_1 = new JButton("einloggen");
        loginButton_3_1.setBackground(buttonColor);
        this.panel_components.add(loginButton_3_1);
        // Lambda
        this.panel_components.stream().filter(jComponent -> jComponent instanceof JTextField || jComponent instanceof JButton).forEach(jComponent -> {
            jComponent.setFont(textFont2);
            jComponent.setForeground(textColor2);
        });
        // JPanel erzeugen
        JPanel panel_center_0_1 = new JPanel(new GridLayout(4,1,5,5));
        // Panel ist unsichtbar     -->     Hintergrundbild
        panel_center_0_1.setOpaque(false);
        // MethodReference      -->     JLabel auf JPanel setzen
        this.panel_components.forEach(panel_center_0_1::add);
        // -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        // CenterPanel erzeugen und die beiden FormPanels setzen
        JPanel panel_center = new JPanel(new GridLayout(4,5));
        // Panel ist unsichtbar     -->     Hintergrundbild
        panel_center.setOpaque(false);
        panel_center.add(panel_center_0_0);
        panel_center.add(panel_center_0_1);
        // -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        // 18 DummyLabels in eine Liste stellen
        this.panel_components = IntStream.range(0, 18).mapToObj(i -> new JLabel()).collect(Collectors.toList());
        // DummyLabels auf das CenterPanel setzen
        this.panel_components.forEach(panel_center::add);
        // *************************************************************************************************************************************************************************
        // (Dummy)Labels BorderLayout.NORTH, EAST und WEST erzeugen und Größe zuordnen
        // *************************************************************************************************************************************************************************
        // Labels für Menü, CopyRight und (Eingabe)FehlerMeldungen
        JLabel label_north = new JLabel();
        label_north.setPreferredSize(new Dimension(30,85));                                                 //breite, höhe

        JLabel dummyLabel_east = new JLabel();
        dummyLabel_east.setPreferredSize(new Dimension(190,30));                                            //breite, höhe

        JLabel dummyLabel_west = new JLabel();
        dummyLabel_west.setPreferredSize(new Dimension(50,30));                                             //breite, höhe
        // *************************************************************************************************************************************************************************
        // JPanel BorderLayout.SOUTH (GridLayout(1, 4))
        // *************************************************************************************************************************************************************************
        // JPanel für 4. South-Feld erzeugen
        JPanel panel_south_0_4 = new JPanel(new GridLayout(4,1,5,5));
        panel_south_0_4.setOpaque(false);
        // 2 DummyLabel in eine Liste stellen und an Panel übergeben
        this.panel_components = IntStream.range(0, 2).mapToObj(i -> new JLabel()).collect(Collectors.toList());
        // DummyLabels auf das CenterPanel setzen
        this.panel_components.forEach(panel_south_0_4::add);
        // Label für das CopyRight
        this.copyRight_Label = new JLabel("\u00a9  Susanna Gruber         ");
        this.copyRight_Label.setFont(textFont2);
        this.copyRight_Label.setForeground(textColor1);
        this.copyRight_Label.setHorizontalAlignment(SwingConstants.RIGHT);
        // CopyRightLabel übergeben
        panel_south_0_4.add(this.copyRight_Label);
        // noch ein DummyLabel
        panel_south_0_4.add(new JLabel());
        // Panel BorderLayout.SOUTH erzeugen, Größe zuweisen, durchsichtig machen und GridLayout(1, 4) setzen
        JPanel panel_south = new JPanel();
        panel_south.setPreferredSize(new Dimension(30,85));                                                 //breite, höhe
        panel_south.setOpaque(false);
        panel_south.setLayout(new GridLayout(1, 4));
        // 3 DummyLabel in eine Liste stellen und an Panel übergeben
        this.panel_components = IntStream.range(0, 3).mapToObj(i -> new JLabel()).collect(Collectors.toList());
        // DummyLabels auf das CenterPanel setzen
        this.panel_components.forEach(panel_south::add);
        // CopyRightLabel setzen
        panel_south.add(panel_south_0_4);

        // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
        // LISTENEROBJEKTE
        // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
        // $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        // START-Bildschirm Buttons
        // $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        this.startButton_0_0 = new JButton("Kochbuch");
        this.startButton_0_1 = new JButton("Rezept");
        this.startButton_1_0 = new JButton("Zutat");
        this.startButton_1_1 = new JButton("Parameter");
        this.startButton_2_0 = new JButton("Werkstatt");
        this.startButton_2_1 = new JButton("Logout");
        // JButtons StartSeite in eine Liste stellen
        this.panel_components = new ArrayList<>();
        this.panel_components.add(this.startButton_0_0);
        this.panel_components.add(this.startButton_0_1);
        this.panel_components.add(this.startButton_1_0);
        this.panel_components.add(this.startButton_1_1);
        this.panel_components.add(this.startButton_2_0);
        this.panel_components.add(this.startButton_2_1);

        // $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        // MenüLeiste Buttons für die Unterprogramme erzeugen, damit sie dem ActionListener übergeben werden können
        // Dann sortiert in eine LinkedHashMap stellen, damit das Menü bei jedem Unterprogramm in der richtigen Reihenfolge ausgegeben wird
        // $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        // Eigene Sortierung in eine ArrayList eingeben
        List<String> sortList = new ArrayList<>();
        sortList.add("Kochbuch");
        sortList.add("Rezept");
        sortList.add("Zutat");
        sortList.add("Parameter");
        sortList.add("Werkstatt");
        sortList.add("Logout");
        // Menü in den UnterProgrammen
        JButton menueButton_0 = new JButton("Kochbuch");
        JButton menueButton_1 = new JButton("Rezept");
        JButton menueButton_2 = new JButton("Zutat");
        JButton menueButton_3 = new JButton("Parameter");
        JButton menueButton_4 = new JButton("Werkstatt");
        JButton menueButton_5 = new JButton("Logout");
        // JButtons Menü in eine temporäre HashMap stellen
        Map<String, JButton> tempMap = new HashMap<>();
        tempMap.put("Kochbuch", menueButton_0);
        tempMap.put("Rezept", menueButton_1);
        tempMap.put("Zutat", menueButton_2);
        tempMap.put("Parameter", menueButton_3);
        tempMap.put("Werkstatt", menueButton_4);
        tempMap.put("Logout", menueButton_5);
        // das KeySet der temporären HashMap nach der SortierListe sortieren
        List<String> list = new ArrayList<>(tempMap.keySet());
        list.sort((o1, o2) -> {
            int x = 0;
            for (String s : sortList) {
                if (o1.equals(s)) {
                    --x;
                    break;
                } else if (o2.equals(s)) {
                    ++x;
                    break;
                }
            }
            return x;
        });
        // MenüButtons in eine LinkedHashMap stellen,
        // damit die Sortierung des KeySets erhalten bleibt
        this.menueButtons = new LinkedHashMap<>();
        list.forEach(s -> this.menueButtons.put(s, tempMap.get(s)));

        // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
        // LISTENER
        // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
        // $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        // ActionListener erzeugen
        // $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        this.myActionController = new MainFrameActionController
                (this, label_north, loginTextField_1_1, loginPasswordField_2_1, loginButton_3_1, this.startButton_0_0, this.startButton_0_1,
                this.startButton_1_0, this.startButton_1_1, this.startButton_2_0, this.startButton_2_1, menueButton_0, menueButton_1, menueButton_2,
                        menueButton_3, menueButton_4, menueButton_5);

        // $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        // Listener zuweisen
        // $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        // dem Button im LoginFormular den ActionListener zuweisen
        loginButton_3_1.addActionListener(this.myActionController);
        // -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        // Lambda   -->     den JButtons für die StartSeite in der Liste Schrift und SchriftFarbe übergeben, einen Rahmen setzen und sie durchsichtig machen
        //                  ActionListener zuweisen
        this.panel_components.forEach(jComponent -> {
            jComponent.setFont(textFontStart);
            jComponent.setForeground(textColor2);
            jComponent.setBorder(MainFrame.lineBorderButtonStart);
            ((JButton)jComponent).setFocusPainted(false);
            ((JButton)jComponent).setContentAreaFilled(false);
            jComponent.setOpaque(false);
            ((JButton)jComponent).addActionListener(this.myActionController);
        });
        // -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        // Lambda   -->     den JButtons für das Menü in der Mappe Schrift und SchriftFarbe übergeben, einen (unsichtbaren) Rahmen setzen und sie durchsichtig machen
        //                  ActionListener zuweisen
        this.menueButtons.forEach((s, jButton) -> {
            jButton.setFont(textFont1);
            jButton.setForeground(textColor1);
            jButton.setBorder(new EmptyBorder(0,0,0,0));
            jButton.setFocusPainted(false);
            jButton.setContentAreaFilled(false);
            jButton.setOpaque(false);
            jButton.addActionListener(this.myActionController);
        });

        // $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        // WindowsListener erzeugen (zum Zumachen des Fensters,
        // gleichzeitig wird EntityManagerFactory erzeugt)
        // $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        this.addWindowListener(new MainFrameWindowAdapter());

        // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
        // HAUPTPANEL UND FRAME
        // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
        // $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        // HauptPanel = MyImagePanel mit Hintergrundbild des Login-Bildschirms
        // $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        // HintergrundBild
        BufferedImage backgroundImage = null;
        try {
            backgroundImage = ImageIO.read(getClass().getResource("/images/loginBackGround1.jpg"));
        } catch(Exception e) {
            System.out.println("FEHLER viewing.mainFrame.MainFrame: " + e);
        }
        // Layout setzen
        this.mainPanel = new MyImagePanel(new BorderLayout(5,5));
        // HintergrundBild setzen
        this.mainPanel.setImage(backgroundImage, true);
        // Komponenten setzen
        this.mainPanel.add(label_north, BorderLayout.NORTH);
        this.mainPanel.add(dummyLabel_east, BorderLayout.EAST);
        this.mainPanel.add(panel_south, BorderLayout.SOUTH);
        this.mainPanel.add(dummyLabel_west, BorderLayout.WEST);
        this.mainPanel.add(panel_center, BorderLayout.CENTER);

        // $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        // Frame
        // $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        // HauptPanel setzen
        this.add(this.mainPanel);
        // Frame Eigenschaften
        this.setLayout(new GridLayout(1,1));
        this.setTitle("kiPferL Login");
        this.setLocation(100,50);
        this.setSize(1400, 860);
        this.setResizable(false);
        this.setVisible(true);
        // setzt Fokus in das erste feld
        loginTextField_1_1.requestFocusInWindow();
        // macht, dass bei ENTER das Formular abgeschickt wird und man nicht auf den Button klicken muss
        this.getRootPane().setDefaultButton(loginButton_3_1);
    }

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Getter und Setter
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public KiParameterSubScreen getKiParameterSubScreen() {
        return this.kiParameterSubScreen;
    }
    public ZutatSubScreen getZutatSubScreen() {
        return zutatSubScreen;
    }

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Methoden zum Wechseln der Bildschirme
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public void changeLayout(int pFrame) {
        JPanel hidePanel;
        // $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        // LoginDaten wurden erfolgreich eingegeben, Startseite wird aufgebaut
        // $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        if(pFrame == 1) {
            // FensterTitel wird geändert
            this.setTitle("kiPferL Startseite");
            // HauptPanel unsichtbar machen
            this.mainPanel.setVisible(false);
            // Alle Komponenten vom HauptPanel löschen
            this.mainPanel.removeAll();
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // HintergrundBilder        -->     nur 1x pro Programmaufruf
            if(this.startImage_0_0 == null) {
                try {
                    this.startImage_0_0 = ImageIO.read(getClass().getResource("/images/kochbuch.jpg"));
                    this.startImage_0_1 = ImageIO.read(getClass().getResource("/images/rezepte.jpg"));
                    this.startImage_1_0 = ImageIO.read(getClass().getResource("/images/zutaten.jpg"));
                    this.startImage_1_1 = ImageIO.read(getClass().getResource("/images/parameter.jpg"));
                    this.startImage_2_0 = ImageIO.read(getClass().getResource("/images/einstellungen.jpg"));
                    this.startImage_2_1 = ImageIO.read(getClass().getResource("/images/logout.jpg"));
                } catch (Exception e) {
                    System.out.println("FEHLER viewing.mainFrame.MainFrame.changeLayout.1: " + e);
                }
            }
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // 1.Panel = Kochbuch erzeugen
            MyImagePanel start_0_0 = new MyImagePanel(new GridLayout(5,3,0,0));
            start_0_0.setImage(this.startImage_0_0, true);
            // 7 DummyLabel in eine Liste stellen und an Panel übergeben
            this.panel_components = IntStream.range(0, 7).mapToObj(i -> new JLabel()).collect(Collectors.toList());
            this.panel_components.forEach(start_0_0::add);
            // Button übergeben
            start_0_0.add(this.startButton_0_0);
            // nochmal 7 DummyLabel an Panel übergeben
            this.panel_components = IntStream.range(0, 7).mapToObj(i -> new JLabel()).collect(Collectors.toList());
            this.panel_components.forEach(start_0_0::add);
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // 2.Panel = Rezept
            MyImagePanel start_0_1 = new MyImagePanel(new GridLayout(5,3));
            start_0_1.setImage(this.startImage_0_1, true);
            this.panel_components = IntStream.range(0, 7).mapToObj(i -> new JLabel()).collect(Collectors.toList());
            this.panel_components.forEach(start_0_1::add);
            start_0_1.add(this.startButton_0_1);
            this.panel_components = IntStream.range(0, 7).mapToObj(i -> new JLabel()).collect(Collectors.toList());
            this.panel_components.forEach(start_0_1::add);
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // 3.Panel = Zutat
            MyImagePanel start_1_0 = new MyImagePanel(new GridLayout(5,3));
            start_1_0.setImage(this.startImage_1_0, true);
            this.panel_components = IntStream.range(0, 7).mapToObj(i -> new JLabel()).collect(Collectors.toList());
            this.panel_components.forEach(start_1_0::add);
            start_1_0.add(this.startButton_1_0);
            this.panel_components = IntStream.range(0, 7).mapToObj(i -> new JLabel()).collect(Collectors.toList());
            this.panel_components.forEach(start_1_0::add);
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // 4.Panel = Parameter
            MyImagePanel start_1_1 = new MyImagePanel(new GridLayout(5,3));
            start_1_1.setImage(this.startImage_1_1, true);
            this.panel_components = IntStream.range(0, 7).mapToObj(i -> new JLabel()).collect(Collectors.toList());
            this.panel_components.forEach(start_1_1::add);
            start_1_1.add(this.startButton_1_1);
            this.panel_components = IntStream.range(0, 7).mapToObj(i -> new JLabel()).collect(Collectors.toList());
            this.panel_components.forEach(start_1_1::add);
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // 5.Panel = Werkstatt
            MyImagePanel start_2_0 = new MyImagePanel(new GridLayout(5,5));
            start_2_0.setImage(this.startImage_2_0, true);
            this.panel_components = IntStream.range(0, 7).mapToObj(i -> new JLabel()).collect(Collectors.toList());
            this.panel_components.forEach(start_2_0::add);
            start_2_0.add(this.startButton_2_0);
            this.panel_components = IntStream.range(0, 7).mapToObj(i -> new JLabel()).collect(Collectors.toList());
            this.panel_components.forEach(start_2_0::add);
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // 6.Panel = Logout
            MyImagePanel start_2_1 = new MyImagePanel(new GridLayout(5,5));
            start_2_1.setImage(this.startImage_2_1, true);
            this.panel_components = IntStream.range(0, 7).mapToObj(i -> new JLabel()).collect(Collectors.toList());
            this.panel_components.forEach(start_2_1::add);
            start_2_1.add(this.startButton_2_1);
            this.panel_components = IntStream.range(0, 6).mapToObj(i -> new JLabel()).collect(Collectors.toList());
            this.panel_components.forEach(start_2_1::add);
            start_2_1.add(this.copyRight_Label);
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // alle Panels an ein weisses Panel übergeben
            hidePanel = new JPanel();
            hidePanel.setBackground(Color.WHITE);
            hidePanel.setLayout(new GridLayout(3,2,5,5));
            hidePanel.add(start_0_0);
            hidePanel.add(start_0_1);
            hidePanel.add(start_1_0);
            hidePanel.add(start_1_1);
            hidePanel.add(start_2_0);
            hidePanel.add(start_2_1);
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // weisses Panel auf HauptPanel setzen (damit das Bild verdeckt wird)
            this.mainPanel.setLayout(new GridLayout(1,1));
            this.mainPanel.add(hidePanel);
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // HauptPanel sichtbar machen
            this.mainPanel.setVisible(true);
            // umschreiben
            this.validate();
        }

        // $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        // Parameter
        // $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        else if(pFrame == 2) {
            // FensterTitel wird geändert
            this.setTitle("kiPferL Parameter");
            // LayoutPanel unsichtbar machen
            this.mainPanel.setVisible(false);
            // Alle Komponenten vom HauptPanel löschen
            this.mainPanel.removeAll();
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // weisses Panel erzeugen
            hidePanel = new JPanel();
            hidePanel.setBackground(Color.WHITE);
            hidePanel.setLayout(new BorderLayout());
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // MenuePanel erzeugen und auf weisses Panel setzen
            this.produceMenue("Parameter");
            hidePanel.add(this.menuePanel, BorderLayout.NORTH);
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // SubScreenAbhängiges ListPanel und FormPanel erzeugen, Listener dazu erzeugen und anmelden
            // nur beim ersten Aufruf...
            if(this.kiParameterSubScreen == null) {
                this.kiParameterSubScreen = new KiParameterSubScreen
                    (this, this.menuePanel, "Parameter ausgeben:",
                            "Parameter anlegen / ändern:", "ParameterGruppe");
            }
            // dann wird die Methode changeLayout() aufgerufen
            else {
                this.kiParameterSubScreen.changeLayout
                        (this.kiParameterSubScreen.getActionController(),
                                this.kiParameterSubScreen.getItemController());
            }
            // SubScreenListPanel auf weisses Panel setzen
            hidePanel.add(this.kiParameterSubScreen.getListPanel(), BorderLayout.WEST);
            // SubScreenFormPanel auf weisses Panel setzen
            hidePanel.add(this.kiParameterSubScreen.getFormPanel(), BorderLayout.EAST);
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // weisses Panel auf HauptPanel setzen (damit das Bild überdeckt wird)
            this.mainPanel.setLayout(new GridLayout(1, 1));
            this.mainPanel.add(hidePanel);
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // HauptPanel sichtbar machen
            this.mainPanel.setVisible(true);
            // setzt Fokus in das erste TextField = id
            ((KiParameterFormPanel)this.kiParameterSubScreen.getFormPanel()).getKuerzelTextField().requestFocusInWindow();
            // macht, dass bei ENTER das Formular abgeschickt wird und man nicht auf den Button klicken muss
            this.getRootPane().setDefaultButton(this.kiParameterSubScreen.getFormPanel().getFormButton());
            // umschreiben
            this.validate();
        }
        // *************************************************************************************************************************************************************************
        // Zutaten
        // *************************************************************************************************************************************************************************
        else if(pFrame == 3) {
            // FensterTitel wird geändert
            this.setTitle("kiPferL Zutat");
            // LayoutPanel unsichtbar machen
            this.mainPanel.setVisible(false);
            // Alle Komponenten vom HauptPanel löschen
            this.mainPanel.removeAll();
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // weisses Panel erzeugen
            hidePanel = new JPanel();
            hidePanel.setBackground(Color.WHITE);
            hidePanel.setLayout(new BorderLayout());
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // MenuePanel erzeugen und auf weisses Panel setzen
            this.produceMenue("Zutat");
            hidePanel.add(this.menuePanel, BorderLayout.NORTH);
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // SubScreenAbhängiges ListPanel und FormPanel erzeugen, Listener dazu erzeugen und anmelden
            // nur beim ersten Aufruf...
            if(this.zutatSubScreen == null) {
                this.zutatSubScreen = new ZutatSubScreen
                        (this, this.menuePanel, "Zutaten ausgeben:", "Zutat anlegen / ändern:", "EinkaufsKategorie");
            }
            // dann wird die Methode changeLayout() aufgerufen
            else {
                this.zutatSubScreen.changeLayout(this.zutatSubScreen.getActionController(), this.zutatSubScreen.getItemController());
            }
            // SubScreenListPanel auf weisses Panel setzen
            hidePanel.add(this.zutatSubScreen.getListPanel(), BorderLayout.WEST);
            // SubScreenFormPanel auf weisses Panel setzen
            hidePanel.add(this.zutatSubScreen.getFormPanel(), BorderLayout.EAST);
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // weisses Panel auf HauptPanel setzen (damit das Bild überdeckt wird)
            this.mainPanel.setLayout(new GridLayout(1, 1));
            this.mainPanel.add(hidePanel);
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // HauptPanel sichtbar machen
            this.mainPanel.setVisible(true);
            // umschreiben
            this.validate();
        }
        // *************************************************************************************************************************************************************************
        // Rezepte
        // *************************************************************************************************************************************************************************
        else if(pFrame == 4) {
            // FensterTitel wird geändert
            this.setTitle("kiPferL Rezept");
            // LayoutPanel unsichtbar machen
            this.mainPanel.setVisible(false);
            // Alle Komponenten vom HauptPanel löschen
            this.mainPanel.removeAll();
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // weisses Panel erzeugen
            hidePanel = new JPanel();
            hidePanel.setBackground(Color.WHITE);
            hidePanel.setLayout(new BorderLayout());
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // MenuePanel erzeugen und auf weisses Panel setzen
            this.produceMenue("Rezept");
            hidePanel.add(this.menuePanel, BorderLayout.NORTH);
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // weisses Panel auf HauptPanel setzen (damit das Bild überdeckt wird)
            this.mainPanel.setLayout(new GridLayout(1, 1));
            this.mainPanel.add(hidePanel);
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // HauptPanel sichtbar machen
            this.mainPanel.setVisible(true);
            // umschreiben
            this.validate();
        }
        // *************************************************************************************************************************************************************************
        // Kochbuch
        // *************************************************************************************************************************************************************************
        else if(pFrame == 5) {
            // FensterTitel wird geändert
            this.setTitle("kiPferL Kochbuch");
            // LayoutPanel unsichtbar machen
            this.mainPanel.setVisible(false);
            // Alle Komponenten vom HauptPanel löschen
            this.mainPanel.removeAll();
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // weisses Panel erzeugen
            hidePanel = new JPanel();
            hidePanel.setBackground(Color.WHITE);
            hidePanel.setLayout(new BorderLayout());
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // MenuePanel erzeugen und auf weisses Panel setzen
            this.produceMenue("Kochbuch");
            hidePanel.add(this.menuePanel, BorderLayout.NORTH);
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // weisses Panel auf HauptPanel setzen (damit das Bild überdeckt wird)
            this.mainPanel.setLayout(new GridLayout(1, 1));
            this.mainPanel.add(hidePanel);
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // HauptPanel sichtbar machen
            this.mainPanel.setVisible(true);
            // umschreiben
            this.validate();
        }
    }

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // extra Methode zum Blättern in der Liste (Listeneinträge haben sich nicht geändert)
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public void changeLayout(int pFrame, int seite) {
        JPanel hidePanel;
        // $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        // Parameter
        // $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        if(pFrame == 2) {
            // FensterTitel wird geändert
            this.setTitle("kiPferL Parameter");
            // LayoutPanel unsichtbar machen
            this.mainPanel.setVisible(false);
            // Alle Komponenten vom HauptPanel löschen
            this.mainPanel.removeAll();
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // weisses Panel erzeugen
            hidePanel = new JPanel();
            hidePanel.setBackground(Color.WHITE);
            hidePanel.setLayout(new BorderLayout());
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // MenuePanel erzeugen und auf weisses Panel setzen
            this.produceMenue("Parameter");
            hidePanel.add(this.menuePanel, BorderLayout.NORTH);
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // SubScreenAbhängiges ListPanel und FormPanel erzeugen, Listener dazu erzeugen und anmelden
            // nur beim ersten Aufruf...
            if(this.kiParameterSubScreen == null) {
                this.kiParameterSubScreen = new KiParameterSubScreen
                        (this, this.menuePanel, "Parameter ausgeben:", "Parameter anlegen / ändern:", "ParameterGruppe");
            }
            // dann wird die Methode changeLayout() aufgerufen
            else {
                this.kiParameterSubScreen.changeLayout(seite, this.kiParameterSubScreen.getActionController(), this.kiParameterSubScreen.getItemController());
            }
            // SubScreenListPanel auf weisses Panel setzen
            hidePanel.add(this.kiParameterSubScreen.getListPanel(), BorderLayout.WEST);
            // SubScreenFormPanel auf weisses Panel setzen
            hidePanel.add(this.kiParameterSubScreen.getFormPanel(), BorderLayout.EAST);
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // weisses Panel auf HauptPanel setzen (damit das Bild überdeckt wird)
            this.mainPanel.setLayout(new GridLayout(1, 1));
            this.mainPanel.add(hidePanel);
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // HauptPanel sichtbar machen
            this.mainPanel.setVisible(true);
            // setzt Fokus in das erste TextField = id
            ((KiParameterFormPanel)this.kiParameterSubScreen.getFormPanel()).getKuerzelTextField().requestFocusInWindow();
            // macht, dass bei ENTER das Formular abgeschickt wird und man nicht auf den Button klicken muss
            this.getRootPane().setDefaultButton(this.kiParameterSubScreen.getFormPanel().getFormButton());
            // umschreiben
            this.validate();
        }
        // *************************************************************************************************************************************************************************
        // Zutaten
        // *************************************************************************************************************************************************************************
        else if(pFrame == 3) {
            // FensterTitel wird geändert
            this.setTitle("kiPferL Zutat");
            // LayoutPanel unsichtbar machen
            this.mainPanel.setVisible(false);
            // Alle Komponenten vom HauptPanel löschen
            this.mainPanel.removeAll();
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // weisses Panel erzeugen
            hidePanel = new JPanel();
            hidePanel.setBackground(Color.WHITE);
            hidePanel.setLayout(new BorderLayout());
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // MenuePanel erzeugen und auf weisses Panel setzen
            this.produceMenue("Zutat");
            hidePanel.add(this.menuePanel, BorderLayout.NORTH);
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // SubScreenAbhängiges ListPanel und FormPanel erzeugen, Listener dazu erzeugen und anmelden
            // nur beim ersten Aufruf...
            if(this.zutatSubScreen == null) {
                this.zutatSubScreen = new ZutatSubScreen
                        (this, this.menuePanel, "Zutat ausgeben:", "Zutat anlegen / ändern:", "EinkaufsKategorie");
            }
            // dann wird die Methode changeLayout() aufgerufen
            else {
                this.zutatSubScreen.changeLayout(seite, this.zutatSubScreen.getActionController(), this.zutatSubScreen.getItemController());
            }
            // SubScreenListPanel auf weisses Panel setzen
            hidePanel.add(this.zutatSubScreen.getListPanel(), BorderLayout.WEST);
            // SubScreenFormPanel auf weisses Panel setzen
            hidePanel.add(this.zutatSubScreen.getFormPanel(), BorderLayout.EAST);
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // weisses Panel auf HauptPanel setzen (damit das Bild überdeckt wird)
            this.mainPanel.setLayout(new GridLayout(1, 1));
            this.mainPanel.add(hidePanel);
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // HauptPanel sichtbar machen
            this.mainPanel.setVisible(true);
            // umschreiben
            this.validate();
        }
        // *************************************************************************************************************************************************************************
        // Rezepte
        // *************************************************************************************************************************************************************************
        else if(pFrame == 4) {
            // FensterTitel wird geändert
            this.setTitle("kiPferL Rezept");
            // LayoutPanel unsichtbar machen
            this.mainPanel.setVisible(false);
            // Alle Komponenten vom HauptPanel löschen
            this.mainPanel.removeAll();
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // weisses Panel erzeugen
            hidePanel = new JPanel();
            hidePanel.setBackground(Color.WHITE);
            hidePanel.setLayout(new BorderLayout());
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // MenuePanel erzeugen und auf weisses Panel setzen
            this.produceMenue("Rezept");
            hidePanel.add(this.menuePanel, BorderLayout.NORTH);
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // weisses Panel auf HauptPanel setzen (damit das Bild überdeckt wird)
            this.mainPanel.setLayout(new GridLayout(1, 1));
            this.mainPanel.add(hidePanel);
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // HauptPanel sichtbar machen
            this.mainPanel.setVisible(true);
            // umschreiben
            this.validate();
        }
        // *************************************************************************************************************************************************************************
        // Kochbuch
        // *************************************************************************************************************************************************************************
        else if(pFrame == 5) {
            // FensterTitel wird geändert
            this.setTitle("kiPferL Kochbuch");
            // LayoutPanel unsichtbar machen
            this.mainPanel.setVisible(false);
            // Alle Komponenten vom HauptPanel löschen
            this.mainPanel.removeAll();
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // weisses Panel erzeugen
            hidePanel = new JPanel();
            hidePanel.setBackground(Color.WHITE);
            hidePanel.setLayout(new BorderLayout());
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // MenuePanel erzeugen und auf weisses Panel setzen
            this.produceMenue("Kochbuch");
            hidePanel.add(this.menuePanel, BorderLayout.NORTH);
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // weisses Panel auf HauptPanel setzen (damit das Bild überdeckt wird)
            this.mainPanel.setLayout(new GridLayout(1, 1));
            this.mainPanel.add(hidePanel);
            // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
            // HauptPanel sichtbar machen
            this.mainPanel.setVisible(true);
            // umschreiben
            this.validate();
        }
    }

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // Methode zum Erzeugen der oberen Menüleiste in Unterprogrammen
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public void produceMenue(String not) {
        // die MenüUnterPanels werden nur erzeugt und in eine Map gestellt, wenn diese null ist (= nur 1x pro Programmaufruf)
        if(this.menuePanelMap == null) {
            this.menuePanelMap = new HashMap<>();

            MyImagePanel menue_0 = new MyImagePanel(new GridLayout(1, 1));
            menue_0.setImage(this.startImage_0_0, true);
            this.menuePanelMap.put("Kochbuch", menue_0);

            MyImagePanel menue_1 = new MyImagePanel(new GridLayout(1, 1));
            menue_1.setImage(this.startImage_0_1, true);
            this.menuePanelMap.put("Rezept", menue_1);

            MyImagePanel menue_2 = new MyImagePanel(new GridLayout(1, 1));
            menue_2.setImage(this.startImage_1_0, true);
            this.menuePanelMap.put("Zutat", menue_2);

            MyImagePanel menue_3 = new MyImagePanel(new GridLayout(1, 1));
            menue_3.setImage(this.startImage_1_1, true);
            this.menuePanelMap.put("Parameter", menue_3);

            MyImagePanel menue_4 = new MyImagePanel(new GridLayout(1, 1));
            menue_4.setImage(this.startImage_2_0, true);
            this.menuePanelMap.put("Werkstatt", menue_4);

            MyImagePanel menue_5 = new MyImagePanel(new GridLayout(1, 1));
            menue_5.setImage(this.startImage_2_1, true);
            this.menuePanelMap.put("Logout", menue_5);
        }
        this.menuePanel = new JPanel();
        this.menuePanel.setPreferredSize(new Dimension(1400, 102));
        this.menuePanel.setLayout(new GridLayout(1, 5));
        this.menueButtons.keySet().stream().filter(s -> !s.equalsIgnoreCase(not)).forEach(s -> {
            this.menuePanelMap.get(s).add(this.menueButtons.get(s));
            this.menuePanel.add(this.menuePanelMap.get(s));
        });
    }
}
