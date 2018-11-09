import java.awt.ComponentOrientation;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.border.CompoundBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.util.Calendar;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.SystemColor;
import java.awt.Color;
import javax.swing.UIManager;
import java.awt.Font;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.border.MatteBorder;

public class Application implements DocumentListener {

	private JFrame frame;
	private JComboBox<CurrencyTableType> currencyTableCB;
	private JComboBox<Currency> currencyCB;
	private JTextField firstCurrencyTF;
	private JTextField secondCurrencyTF;
	private JTextField exchangeRateTF;
	private BigDecimal exchangeRate;
	private JLabel lblCurrency;
	private JLabel lblExchangeRate;
	private JLabel lbCurrencyAmount;
	private JLabel lblAmount;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Application window = new Application();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Application() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(153, 153, 153));
		frame.setBounds(100, 100, 470, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		JDateChooser dateChooser = new JDateChooser();
		BorderLayout borderLayout = (BorderLayout) dateChooser.getLayout();
		dateChooser.setCalendar(calendar);
		dateChooser.setBounds(20, 30, 150, 40);
		frame.getContentPane().add(dateChooser);

		// get data JButton
		JButton btnGetRates = new JButton("Pobierz kurs");
		btnGetRates.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnGetRates.setForeground(new Color(245, 245, 245));
		btnGetRates.setBackground(new Color(153, 153, 153));
		btnGetRates.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				CurrencyTableType selectedTable = (CurrencyTableType) currencyTableCB.getSelectedItem();
				String selectedItem = selectedTable.getCode();
				XMLDocument url = new XMLDocument();
				Document doc = url.getXMLDocument(selectedItem, dateChooser.getCalendar());
				currencyCB.removeAllItems();

				NodeList nList = doc.getElementsByTagName("pozycja");
				for (int i = 0; i < nList.getLength(); i++) {

					Node nNode = nList.item(i);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;
						String currencyCode = eElement.getElementsByTagName("kod_waluty").item(0).getTextContent();
						String currencyName = eElement.getElementsByTagName("nazwa_waluty").item(0).getTextContent();
						String convertRateStr = eElement.getElementsByTagName("przelicznik").item(0).getTextContent();
						int convertRate = Integer.parseInt(convertRateStr);
						String exchangeRateStr = eElement.getElementsByTagName("kurs_sredni").item(0).getTextContent();
						BigDecimal exchangeRate = new BigDecimal(exchangeRateStr.replaceAll(",", "."));
						currencyCB.addItem(new Currency(currencyCode, currencyName, convertRate, exchangeRate));
					}

				}

			}
		});
		btnGetRates.setBounds(20, 92, 430, 50);
		frame.getContentPane().add(btnGetRates);

		// currency table ComboBox
		currencyTableCB = new JComboBox(new CurrencyTableTypeList().getCurrencyTabList());
		currencyTableCB.setBounds(180, 30, 266, 40);
		frame.getContentPane().add(currencyTableCB);

		// currency ComboBox
		currencyCB = new JComboBox<Currency>();
		currencyCB.setRenderer(new CustomComboRender());
		currencyCB.setBounds(20, 168, 430, 35);
		currencyCB.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					Currency currency = (Currency) currencyCB.getSelectedItem();
					exchangeRate = currency.getCurrencyRate();
					exchangeRateTF.setText(exchangeRate.toString());
				}
			}
		});
		frame.getContentPane().add(currencyCB);

		// currency exchange rate JTextField
		exchangeRateTF = new JTextField();
		exchangeRateTF.setFont(new Font("Tahoma", Font.BOLD, 15));
		exchangeRateTF.setBackground(SystemColor.activeCaptionText);
		exchangeRateTF.setHorizontalAlignment(SwingConstants.RIGHT);
		exchangeRateTF.setEditable(false);
		exchangeRateTF.setBounds(20, 232, 430, 35);
		exchangeRateTF.setColumns(10);
		exchangeRateTF.getDocument().addDocumentListener(this);
		frame.getContentPane().add(exchangeRateTF);

		// amount of currency to exchange
		firstCurrencyTF = new DoubleJTextField();
		firstCurrencyTF.setFont(new Font("Tahoma", Font.BOLD, 15));
		firstCurrencyTF.setBackground(SystemColor.activeCaptionText);
		firstCurrencyTF.setHorizontalAlignment(SwingConstants.RIGHT);
		firstCurrencyTF.setBounds(20, 292, 430, 35);
		firstCurrencyTF.setColumns(10);
		firstCurrencyTF.getDocument().addDocumentListener(this);
		frame.getContentPane().add(firstCurrencyTF);

		// amount of currency after exchange
		secondCurrencyTF = new JTextField();
		secondCurrencyTF.setFont(new Font("Tahoma", Font.BOLD, 15));
		secondCurrencyTF.setBackground(SystemColor.activeCaptionText);
		secondCurrencyTF.setHorizontalAlignment(SwingConstants.RIGHT);
		secondCurrencyTF.setEditable(false);
		secondCurrencyTF.setBounds(20, 355, 430, 35);
		secondCurrencyTF.setColumns(10);
		frame.getContentPane().add(secondCurrencyTF);
		
		JLabel lblCurrencyDate = new JLabel("Data kursu");
		lblCurrencyDate.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCurrencyDate.setForeground(new Color(255, 255, 255));
		lblCurrencyDate.setBounds(22, 11, 148, 14);
		frame.getContentPane().add(lblCurrencyDate);
		
		JLabel lblTableType = new JLabel("Rodzaj tabeli");
		lblTableType.setForeground(new Color(255, 255, 255));
		lblTableType.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTableType.setBounds(181, 11, 265, 14);
		frame.getContentPane().add(lblTableType);
		
		lblCurrency = new JLabel("Waluta");
		lblCurrency.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCurrency.setForeground(new Color(255, 255, 255));
		lblCurrency.setBounds(20, 152, 426, 14);
		frame.getContentPane().add(lblCurrency);
		
		lblExchangeRate = new JLabel("Obowi\u0105zuj\u0105cy kurs");
		lblExchangeRate.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblExchangeRate.setForeground(new Color(255, 255, 255));
		lblExchangeRate.setBounds(20, 213, 426, 14);
		frame.getContentPane().add(lblExchangeRate);
		
		lbCurrencyAmount = new JLabel("Ilo\u015B\u0107 w walucie");
		lbCurrencyAmount.setForeground(new Color(255, 255, 255));
		lbCurrencyAmount.setFont(new Font("Tahoma", Font.BOLD, 11));
		lbCurrencyAmount.setBounds(20, 277, 426, 14);
		frame.getContentPane().add(lbCurrencyAmount);
		
		lblAmount = new JLabel("Warto\u015B\u0107 w PLN");
		lblAmount.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblAmount.setForeground(new Color(255, 255, 255));
		lblAmount.setBounds(20, 338, 430, 14);
		frame.getContentPane().add(lblAmount);
	}

	private void setCurrencyTF() {
		try {
			String value = firstCurrencyTF.getText();
			BigDecimal currencyAmount = new BigDecimal(value);
			BigDecimal result = currencyAmount.multiply(exchangeRate).setScale(2, BigDecimal.ROUND_HALF_UP);
			secondCurrencyTF.setText(String.format("%1$,.2f", result));

		} catch (NumberFormatException ex) {
			secondCurrencyTF.setText("");
		}
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		setCurrencyTF();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		setCurrencyTF();
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		setCurrencyTF();
	}
}
