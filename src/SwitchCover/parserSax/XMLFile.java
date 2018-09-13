package SwitchCover.parserSax;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLFile {
	
	public XMLFile(){
		
	}
	
	//O SAX FAZ A LEITURA DO XML E REALIZA O PARSE
	public void readFile(String XMLfile, DefaultHandler handlerXML) throws ParserConfigurationException, SAXException, IOException{
		SAXParser parserXML = SAXParserFactory.newInstance().newSAXParser();
		InputSource inputXML = new InputSource(XMLfile);
		
		try {
        	parserXML.parse(inputXML, handlerXML);
		}
		catch (MalformedURLException e){
			inputXML = new InputSource("file:/"+ XMLfile);
			parserXML.parse(inputXML, handlerXML);
		}
	}
}
