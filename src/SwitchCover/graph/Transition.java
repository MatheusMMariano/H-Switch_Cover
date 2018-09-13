package SwitchCover.graph;
public class Transition {
	
	private String input;
	private String output;
	private String name;
	
	private State destination;
	private State source;
    
    private boolean visited = false;
    private int counter;
    
    public Transition(){
    	this.destination = new State();
    	this.source = new State();
    	this.counter = 0;
    }
    
    public Transition(String input, String output, String name, State destination, State source, Boolean visited, Integer counter){
    	this.input = input;
    	this.output = output;
    	this.name = name;
    	this.destination = destination;
    	this.source = source;
    	this.visited = visited;
    	this.counter = counter;
    }

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public State getDestination() {
		return destination;
	}

	public void setDestination(State destination) {
		this.destination = destination;
	}

	public State getSource() {
		return source;
	}

	public void setSource(State source) {
		this.source = source;
	}

	public boolean getVisited() {
		return visited;
	}

	public void setVisited(Boolean visited) {
		this.visited = visited;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(Integer counter) {
		this.counter = counter;
	}
	
	public String toString(){
		StringBuffer output = new StringBuffer();
	    return output.append("["+getDestination().getName()+"]"+getInput()).toString();
	}

}