import javax.swing.*;
import java.awt.*;
import java.util.prefs.Preferences;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
/**
 * Application class
 * 
 * serves the purpose of a currency converter
 * 
 * @version 1.0
 * @author Patric Pintescul
 */
public class Application extends JFrame {
    private static final double EUR_USD_CONV_RATE = 1.09,EUR_PND_CONV_RATE=0.86;

    // utilities for window position saving
    private static final String WIDTH_KEY = "width";
    private static final String HEIGHT_KEY = "height";
    private static final String POS_X = "x";
    private static final String POS_Y = "y";

    // app layout container
    private Container cp;

    private DoubleField euroField;
    private JRadioButton dollarButton;
    private JRadioButton britishPoundsButton;
    private ButtonGroup currencyGroup; // prevent radio buttons from being selected at the same time
    private JLabel outputLabel;

    // this variable stores where the window position that was previously used, 
    // or any quick things that dont need a separate file for saving
    private Preferences preferences;

    /**
     * This is the application's constructor, it will build all the necessary things for the app to function
     */
    public Application(){
        super();
        this.currencyGroup = new ButtonGroup();
        cp=this.getContentPane();
        cp.setLayout(new BorderLayout(10,10));
        this.setTitle("Application");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        preferences = Preferences.userNodeForPackage(Application.class);
        int width = preferences.getInt(WIDTH_KEY, 300);
        int height = preferences.getInt(HEIGHT_KEY, 400);
        int posx = preferences.getInt(POS_X, 100);
        int posy = preferences.getInt(POS_Y, 100);
        
        this.setSize(width, height);
        this.setLocation(posx, posy);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                saveUserDimensions();
                System.exit(0);
            }
        });
        this.setupApp();
    }

    /**
     * this method will setup most of the app, it will initialize almost all needed components and link them to their respective role
     */
    private void setupApp(){
        // Euro field
        JPanel fieldBox = new JPanel();
        fieldBox.setBorder(BorderFactory.createTitledBorder("EURO"));
        fieldBox.setLayout(new BoxLayout(fieldBox,BoxLayout.LINE_AXIS));
        this.euroField = new DoubleField(cp);
        fieldBox.add(euroField);
        cp.add(fieldBox, BorderLayout.NORTH);

        // Containers for radio buttons and submit button
        JPanel selectionBox = new JPanel();
        selectionBox.setLayout(new BoxLayout(selectionBox, BoxLayout.LINE_AXIS));
        selectionBox.setBorder(BorderFactory.createTitledBorder("Valuta di destinazione"));
        JPanel radioButtons = new JPanel();
        radioButtons.setLayout(new BoxLayout(radioButtons, BoxLayout.PAGE_AXIS));
        selectionBox.add(radioButtons);
        cp.add(selectionBox, BorderLayout.CENTER);

        // Radio buttons for selecting the currency
        dollarButton = new JRadioButton("Dollaro");
        dollarButton.setSelected(true);
        britishPoundsButton = new JRadioButton("Sterlina britannica");
        currencyGroup.add(dollarButton);
        radioButtons.add(dollarButton);
        currencyGroup.add(britishPoundsButton);
        radioButtons.add(britishPoundsButton);

        // Submit button
        JButton submitButton = new JButton("Trasforma");
        selectionBox.add(new JSeparator(SwingConstants.VERTICAL));
        selectionBox.add(submitButton);
        submitButton.addActionListener(e -> {
            handleSubmit();
        });

        // Output JLabel
        outputLabel = new JLabel("0,00$");
        outputLabel.setHorizontalAlignment(SwingConstants.CENTER);
        outputLabel.setBorder(BorderFactory.createTitledBorder("Output"));
        cp.add(outputLabel, BorderLayout.SOUTH);
    }

    /**
     * This method handles submit, which will change the outPutLabel, and will void itself after the change with no return
     */
    private void handleSubmit(){
        double euro = euroField.getValue();
        double dollar = euro * EUR_USD_CONV_RATE;
        double britishPounds = euro * EUR_PND_CONV_RATE;
        if(dollarButton.isSelected()) outputLabel.setText(String.format("%.2f$", dollar));
        else if(britishPoundsButton.isSelected()) outputLabel.setText(String.format("%.2f£", britishPounds));
    }

    /**
     * This method will save the current window position
     */
    private void saveUserDimensions() {
        preferences.putInt(WIDTH_KEY, getWidth());
        preferences.putInt(HEIGHT_KEY, getHeight());
        preferences.putInt(POS_X, getX());
        preferences.putInt(POS_Y, getY());
    }

    /**
     * This method will start the app, it will pack the app if packElements is true, and will set the app visible
     * @param packElements wether to call pack or not
     */
    public void startApp(boolean packElements){
        if(packElements) this.pack();
        this.setVisible(true);
    }
}