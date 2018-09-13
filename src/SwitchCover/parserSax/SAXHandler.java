package SwitchCover.parserSax;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import SwitchCover.graph.*;

public class SAXHandler extends DefaultHandler{
	
	private StringBuffer value;
    private Graph graph;
    private boolean statusTransition;
    private boolean input;
    private boolean output;
    private Transition lastTransition;
    
    public SAXHandler(Graph pGraph){
    	graph = pGraph;
    	value = new StringBuffer();
    	statusTransition = false;
    }
    
    public void startDocument(){
    	
    }
    
    private String getValue(Attributes attributes, String qName){   	
    	for(int i = 0; attributes.getValue(i) != null; i++){
    		if (attributes.getQName(i).toLowerCase().equals(qName)) return attributes.getValue(i);
    	}
    	return null;
    }
    
    public void startElement(String uri, String localName, String qName, Attributes attributes){
    	qName = qName.toLowerCase();
        
    	// If the tag is state, create a new state and setting in list of states to graph
        if (qName.equals("state")){
        	State state = new State();
        	state.setName(getValue(attributes, "name"));

        	if(getValue(attributes,"type").equals("normal"))state.setTypeState("normal");
            else if(getValue(attributes,"type").equals("final")) state.setTypeState("inicial");
            else state.setTypeState("inicial");

        	graph.setStateMap(state.getName(), state);
        }
        /* Se for uma transição, cria uma nova trans, setando nela os estados de origem e destino, */
        else if (qName.equals("transition")){
        	Transition transition = new Transition();
        	
        	State source = graph.getState(getValue(attributes,"source"));
        	State destination = graph.getState(getValue(attributes,"destination"));
        	
        	transition.setDestination(destination);
        	transition.setSource(source);
        	
        	if(source != null) source.setTransition(transition);
        	
        	lastTransition = transition;
        	statusTransition = true;
        }
        else if (qName.equals("input") && statusTransition) input = true;
        else if (qName.equals("output") && statusTransition) output = true;
    }
    
    public void characters(char[] ch, int start, int length){
        if (statusTransition) value.append(ch, start, length);
    }
    
    public void endElement(String uri, String localName, String qName){
    	qName = qName.toLowerCase();
    	
    	if (qName.equals("transition")) statusTransition = false;
        else if (qName.equals("input") && input){
        	lastTransition.setInput(value.toString().trim());
        	value.delete(0, value.length());
        }
        else if (qName.equals("output") && output){
        	lastTransition.setOutput(value.toString().trim());
        	value.delete(0, value.length());
        }
    }
    
    public void endDocument(){
    	
    }
    
}
