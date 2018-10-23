package SwitchCover.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import SwitchCover.conversion.Reader;
import SwitchCover.graph.Graph;
import SwitchCover.method.NodeConverter;

class ChinesePostmanProblemTest {

	private Graph graph;
	private Graph dualGraphConverted;
	//private String path;
	
	
	private void generateGraphWithXML(String path) {
		try {
			this.graph = graph.openXML(path);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void generateGraphWithTXT(String path, Reader reader) {
		//this.graph = reader.openTXT(path);
	}
	
	private void generateDualGraph(String typeFile) {
		NodeConverter node = new NodeConverter(); 
		this.dualGraphConverted = node.transitionsConvertedNode(graph, typeFile);
	}
	
	@Before
	void createXMLGraph() {
		String path = "";
		generateGraphWithXML(path);
		generateDualGraph("xml");
	}
	
	@Test
	public void chinesePostmanProblemTest() {
		
	}

}
