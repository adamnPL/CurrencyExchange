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
import java.util.Calendar;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;

public class Application {

	private JFrame frame;
	private JComboBox<CurrencyTab> currencyTabCB;
	private JComboBox<Currency> currencyCB;
	private JTextField firstCurrencyTF;
	private JTextField secondCurrencyTF;
	private JTextField exchangeRateTF;
	private double exchangeRate;

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
		frame.setBounds(100, 100, 640, 480);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		Calendar actualDate = Calendar.getInstance();
		actualDate.add(Calendar.DAY_OF_MONTH, -1);
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setCalendar(actualDate);
		dateChooser.setBorder(new CompoundBorder());
		dateChooser.setBounds(20, 20, 150, 40);
		frame.getContentPane().add(dateChooser);

		JButton btnGetRates = new JButton("Pobierz kurs");
		btnGetRates.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				XMLDocumentHandler url = new XMLDocumentHandler();
				Document doc = url.getXMLDocument((String) currencyTabCB.getSelectedItem(), dateChooser.getCalendar());

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
						double exchangeRate = Double.parseDouble(exchangeRateStr.replaceAll(",", "."));
						currencyCB.addItem(new Currency(currencyCode, currencyName, convertRate, exchangeRate));

					}

				}
			}
		});
		btnGetRates.setBounds(456, 20, 150, 40);
		frame.getContentPane().add(btnGetRates);
		currencyTabCB = new JComboBox<CurrencyTab>();
		currencyTabCB.setModel(new DefaultComboBoxModel(new String[] { "a", "b", "c", "h" }));
		currencyTabCB.setBounds(180, 20, 266, 40);
		frame.getContentPane().add(currencyTabCB);

		currencyCB = new JComboBox<Currency>();
		currencyCB.setRenderer(new CustomComboRender());
		currencyCB.setBounds(20, 99, 426, 34);
		currencyCB.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					Currency currency = (Currency) currencyCB.getSelectedItem();
					exchangeRate = currency.getCurrencyRate();
					exchangeRateTF.setText(Double.toString(exchangeRate));
				}
			}
		});
		frame.getContentPane().add(currencyCB);

		exchangeRateTF = new JTextField();
		exchangeRateTF.setEditable(false);
		exchangeRateTF.setBounds(20, 209, 552, 40);
		frame.getContentPane().add(exchangeRateTF);
		exchangeRateTF.setColumns(10);

		firstCurrencyTF = new JTextField();
		firstCurrencyTF.setBounds(20, 277, 552, 46);
		firstCurrencyTF.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				Double wynik = exchangeRate * Double.parseDouble(firstCurrencyTF.getText());
				String wynikStr = wynik.toString();
				secondCurrencyTF.setText(wynikStr);
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				Double wynik = exchangeRate * Double.parseDouble(firstCurrencyTF.getText());
				String wynikStr = wynik.toString();
				secondCurrencyTF.setText(wynikStr);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				Double wynik = exchangeRate * Double.parseDouble(firstCurrencyTF.getText());
				String wynikStr = wynik.toString();
				secondCurrencyTF.setText(wynikStr);
			}
		});
		frame.getContentPane().add(firstCurrencyTF);
		firstCurrencyTF.setColumns(10);

		secondCurrencyTF = new JTextField();
		secondCurrencyTF.setColumns(10);
		secondCurrencyTF.setBounds(20, 348, 552, 46);
		frame.getContentPane().add(secondCurrencyTF);
	}
}
