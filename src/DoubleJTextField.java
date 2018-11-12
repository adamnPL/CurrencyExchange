import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.Format;
import javax.swing.JFormattedTextField;

public class DoubleJTextField extends JFormattedTextField {

	private static final long serialVersionUID = 1L;

	public DoubleJTextField(Format currencyFormat) {
		super(currencyFormat);
		addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char ch = e.getKeyChar();
				if (!isNumber(ch) && !isValidSignal(ch) && !validatePoint(ch) && ch != '\b') {
					System.out.println("Dupa");
					e.consume();
				}
			}
		});

	}

	private boolean isNumber(char ch) {
		return ch >= '0' && ch <= '9';
	}

	private boolean isValidSignal(char ch) {
		if ((getText() == null || "".equals(getText().trim())) && ch == '-') {
			return true;
		}

		return false;
	}

	private boolean validatePoint(char ch) {
		if ((ch != '.') || (getText().contains("."))) {
			return false;
		}

		if (getText() == null || "".equals(getText().trim())) {
			setValue("0.");
			return false;
		} else if ("-".equals(getText())) {
			setValue("-0.");
		}

		return true;
	}
}