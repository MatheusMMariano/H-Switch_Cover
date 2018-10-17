package SwitchCover.graph;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import SwitchCover.parserSax.*;

public class Graph implements Cloneable{
	
	private XMLFile file = new XMLFile();
	private SAXHandler handler = new SAXHandler(this);
    private HashMap<String, State> statesMap = new HashMap<String, State>();
    
    /*public Graph clone() throws CloneNotSupportedException {
    	return (Graph) super.clone();
    }*/
    
    public Graph clone() {
    	try {
			return (Graph) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
    }

	public Graph openXML(String dirXML) throws ParserConfigurationException, SAXException, IOException{
		file.readFile(dirXML, handler);
		return this;
	}
	
	public void setStateMap(String name, State state){
		statesMap.put(name, state);
	}
	
	public State getState(String name){
		return (State)statesMap.get(name);
	}
	
	public String showResult(){
		StringBuffer output = new StringBuffer();
		Iterator<State> it = statesMap.values().iterator();
		
		 while(it.hasNext()){
			 State state = (State)it.next();
	         output.append(">> State ["+state.getName()+"]\n");
	         Iterator<Transition> itt = state.getTransitionIterator();
	         
	         while (itt.hasNext()){
	        	 Transition transition = (Transition)itt.next();
	        	 output.append("     to ");
	             output.append(transition.toString());
	             output.append("\n");
	         }
	     }
		 return output.toString(); 
	}
	
	public void inicialState(){
		Iterator<State> it = statesMap.values().iterator();
		while(it.hasNext()){
			State state = it.next();
			state.setVisited(false);
			
			Iterator<Transition> itt = state.getTransitionIterator();
			while(itt.hasNext()){
				itt.next().setVisited(false);
			}
		}
	}
	
	public Iterator<String> getIteratorState(){
        return statesMap.keySet().iterator();
    }
	
    public Iterator<State> getIteratorStateValue(){
    	 return statesMap.values().iterator();
    }
    
	public void refresh() {
		Iterator<State> stateList = getIteratorStateValue();
		while(stateList.hasNext()) {
			State state = stateList.next();
			state.setVisited(false);
		}
	}

	public HashMap<String, State> getStatesMap() {
		return statesMap;
	}

	public void setStatesMap(HashMap<String, State> statesMap) {
		this.statesMap = statesMap;
	}
	
	public int getSize(){
		return statesMap.keySet().size();
	}
}
