package SwitchCover.graph;

import java.util.List;

public class Cycle {
	
	private State stateOrigin;
	private String cycle;
	private List<State> stateList;
	private boolean concatenated = false;
	
	public Cycle(){
		
	}

	public State getStateOrigin() {
		return stateOrigin;
	}

	public void setStateOrigin(State stateOrigin) {
		this.stateOrigin = stateOrigin;
	}

	public String getCycle() {
		return cycle;
	}

	public void setCycle(String cycle) {
		this.cycle = cycle;
	}

	public List<State> getStateList() {
		return stateList;
	}

	public void setStateList(List<State> stateList) {
		this.stateList = stateList;
	}

	public boolean getConcatenated() {
		return concatenated;
	}

	public void setConcatenated(boolean concatenated) {
		this.concatenated = concatenated;
	}
}
