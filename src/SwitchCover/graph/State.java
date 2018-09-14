package SwitchCover.graph;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class State {
	
	//Tava ARRAY LIST
	private List<Transition> transitions = new LinkedList<Transition>();
	
	private String name = new String();
	private String typeState = new String();
	
	private int counter = 0;
	private int ponderosity = 0; //peso
	//private float ponderosity = 0; //peso
	private int idCPP = 0;
	
	private boolean balancedStatus = false;
	private boolean visited = false;
	private boolean initialSequence = false;
	
	public State(){
		this.name = "";
		this.typeState = "";
		this.balancedStatus = false;
		this.visited = false;
		this.initialSequence = false;
		this.counter = 0;
		this.ponderosity = 0;
	}
	
	public State(String name){
		this.name = name;
		this.typeState = "";
		this.balancedStatus = false;
		this.visited = false;
		this.initialSequence = false;
		this.counter = 0;
		this.ponderosity = 0;
	}
	
	public State(String name, String typeState){
		this.name = name;
		this.typeState = typeState;
		this.balancedStatus = false;
		this.visited = false;
		this.initialSequence = false;
		this.counter = 0;
		this.ponderosity = 0;
	}
	
	public void setTransition(Transition t){
		transitions.add(t);
	}
	
	public Iterator<Transition> getTransitionIterator(){
		return transitions.iterator();
	}
	
	public Transition getTransition(String transition){
		for(Transition t: transitions){
			if(t.getName().equals(transition)) return t;
		}	
		return null;		
	}
	
	public Transition getTransition(State stateDestination) {
		for(Transition t: transitions){
			if(t.getDestination() == stateDestination) return t;
		}	
		return null;	
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}

	public List<Transition> getTransitions() {
		return transitions;
	}

	/*public void setTransitions(ArrayList<Transition> transitions) {
		this.transitions = transitions;
	}*/

	public String getTypeState() {
		return typeState;
	}

	public void setTypeState(String typeState) {
		this.typeState = typeState;
	}

	public Boolean getBalancedStatus() {
		return balancedStatus;
	}

	public void setBalancedStatus(Boolean balancedStatus) {
		this.balancedStatus = balancedStatus;
	}

	public Boolean getVisited() {
		return visited;
	}

	public void setVisited(Boolean visited) {
		this.visited = visited;
	}

	public Boolean getInitialSequence() {
		return initialSequence;
	}

	public void setInitialSequence(boolean inicialSequence) {
		this.initialSequence = inicialSequence;
	}

	public Integer getCounter() {
		return counter;
	}

	public void setCounter(Integer counter) {
		this.counter = counter;
	}
	
	/*public float getPonderosity(){
		return ponderosity;
	}*/
	
	public Integer getPonderosity(){
		return ponderosity;
	}
	
	public void setPonderosity(Integer ponderosity){
		this.ponderosity = ponderosity;
	}
	
	public int getIdCPP(){
		return idCPP;
	}
	public void setIdCPP(int idCPP){
		this.idCPP = idCPP;
	}
}