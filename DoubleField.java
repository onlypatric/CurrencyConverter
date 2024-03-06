import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * DoubleField
 */
public class DoubleField extends JTextField {

    private Component parent;
    private static final Pattern DOUBLE_PATTERN = Pattern.compile("^-?\\d+(\\.\\d+)?([eE][+-]?\\d+)?$");

    public DoubleField(Component parent) {
        super();
        this.parent = parent;
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (((int) (e.getKeyChar()) >= 48 && (int) (e.getKeyChar()) <= 57) || e.getKeyChar() == '.'
                        || e.getKeyChar() == ','
                        || e.getKeyChar() == 'e') {
                    // Check if "." or "," or "e" is present in JTextField
                    if (e.getKeyChar() == 'e' && getText().contains("e")) {
                        e.consume();
                    } else if ((e.getKeyChar() == '.' || e.getKeyChar() == ',')
                            && (getText().contains(".") || getText().contains(","))) {
                        e.consume();
                    }
                    return;
                }
                e.consume();
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
    }

    public boolean isValidDouble() {
        return DOUBLE_PATTERN.matcher(getText()).matches();
    }

    public double getValue() {
        try {
            return Double.parseDouble(getText().replace(',', '.'));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(parent, "This should give out an error");
            return 0;
        }
    }
}
