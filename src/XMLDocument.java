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

public class XMLDocument {
	final String domainAddress = "http://www.nbp.pl/kursy/xml/";

	public String getFileURL(String ratesType, Calendar selectedDate) {

		String fileName;
		URL fileListURL;
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		int selectedYear = selectedDate.get(Calendar.YEAR);

		DateFormat df = new SimpleDateFormat("yyMMdd");
		String formattedDate = df.format(selectedDate.getTime());

		if (currentYear == selectedYear) {
			fileName = "dir.txt";
		} else {
			fileName = "dir" + selectedYear + ".txt";
		}

		try {
			fileListURL = new URL(domainAddress + fileName);
			BufferedReader in = new BufferedReader(new InputStreamReader(fileListURL.openStream()));
			String line = "";
			while ((line = in.readLine()) != null)
				if (line.startsWith(ratesType) && line.substring(5, 11).equals(formattedDate)) {
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
		String ratesFileName = getFileURL(ratesType, selectedDate);
		URL ratesURL;
		try {
			ratesURL = new URL(domainAddress + ratesFileName + ".xml");
			System.out.println(domainAddress + ratesFileName + ".xml");
			URLConnection connection = ratesURL.openConnection();
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(connection.getInputStream());
			return document;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}

		return null;
	}
}