import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XMLDocumentHandler {
	final String domainAddress = "http://www.nbp.pl/kursy/xml/";

	public String getFileRatesURL(String ratesType, Calendar selectedDate) {

		String fileName;
		URL fileListURL;
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		int selectedYear = selectedDate.get(Calendar.YEAR);

		DateFormat df = new SimpleDateFormat("yyMMdd");
		String formattedDate = df.format(selectedDate.getTime());
		// System.out.println("formattedDate"+ formattedDate );

		if (currentYear == selectedYear) {
			fileName = "dir.txt";
		} else {
			fileName = "dir" + selectedYear + ".txt";
		}
		// System.out.println("fileName "+ fileName );
		try {
			fileListURL = new URL(domainAddress + fileName);
			// System.out.println("fullPath "+ domainAddress + fileName );
			BufferedReader in = new BufferedReader(new InputStreamReader(fileListURL.openStream()));
			String line = "";
			while ((line = in.readLine()) != null)
				if (line.startsWith(ratesType) && line.substring(5, 11).equals(formattedDate)) {
					// System.out.println(line);
					return line;
				}
			in.close();
		} catch (MalformedURLException e) {
			System.out.println("Malformed URL: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("I/O Error: " + e.getMessage());
		}
		return null;

	}

	public Document getXMLDocument(String ratesType, Calendar selectedDate) {
		String RatesFileName = getFileRatesURL(ratesType, selectedDate);
		URL RatesURL;
		try {
			RatesURL = new URL(domainAddress + RatesFileName + ".xml");
			URLConnection connection = RatesURL.openConnection();
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(connection.getInputStream());
			return document;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
}

/*
 * Calendar userDate = dateChooser.getCalendar();
 * 
 * urlClass.getFileRatesURL("a", userDate); try {
 * 
 * RatesURL = new URL(""); URLConnection connection = RatesURL.openConnection();
 * DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
 * DocumentBuilder builder = factory.newDocumentBuilder(); document =
 * builder.parse(connection.getInputStream());
 * 
 * } catch (MalformedURLException e) { e.printStackTrace(); } catch
 * (ParserConfigurationException e) { e.printStackTrace(); } catch (IOException
 * e) { e.printStackTrace(); } catch (SAXException e) {
 * 
 * e.printStackTrace(); }
 */
