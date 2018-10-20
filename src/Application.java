import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.border.CompoundBorder;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextArea;

public class Application {

	private JFrame frame;
	Calendar dateChoose;
	JComboBox comboBox;
	JTextArea textArea;

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

				try {
					XMLDocumentHandler url = new XMLDocumentHandler();
					Document doc = url.getXMLDocument((String) comboBox.getSelectedItem(), dateChooser.getCalendar());
					DOMSource domSource = new DOMSource(doc);
					StringWriter writer = new StringWriter();
					StreamResult result = new StreamResult(writer);
					TransformerFactory tf = TransformerFactory.newInstance();
					Transformer transformer;
					transformer = tf.newTransformer();
					transformer.transform(domSource, result);
					System.out.println(writer.toString());
					textArea.setText(writer.toString());
				} catch (TransformerConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (TransformerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		btnGetRates.setBounds(456, 20, 150, 40);
		frame.getContentPane().add(btnGetRates);

		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "a", "b", "c", "h" }));
		comboBox.setBounds(180, 20, 266, 40);
		frame.getContentPane().add(comboBox);

		textArea = new JTextArea();
		textArea.setBounds(540, 60, -520, 330);
		frame.getContentPane().add(textArea);

		List<CurrencyList> list = new ArrayList<>();
	//	list.add(new CurrencyList("PLN", "Polski z³oty", 1, 1.1));
	///	list.add(new CurrencyList("EUR", "Polski z³oty", 1, 1.1));
	//	list.add(new CurrencyList("USD", "Polski z³oty", 1, 1.1));

		MyComboBox comboBox_1 = new MyComboBox(list);
		comboBox_1.setBounds(20, 99, 426, 34);
		comboBox_1.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					CurrencyList currency = (CurrencyList) comboBox_1.getSelectedItem();
					System.out.println(currency.getCurrencyCode());
				}

			}
		});
		frame.getContentPane().add(comboBox_1);
	}
}
