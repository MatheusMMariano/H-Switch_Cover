package SwitchCover.method;

import java.util.ArrayList;
import java.util.List;

import SwitchCover.graph.Cycle;
import SwitchCover.graph.State;

public class EulerCycle {
	
	private List<State> path = new ArrayList<State>();
	private List<Cycle> cycleList = new ArrayList<Cycle>();
	private boolean equal;
	
	public EulerCycle(List<Cycle> cycleList){
		this.cycleList = cycleList;
		this.equal = false;
	}
	
	public List<State> course(Cycle cycle){
		for(State state: cycle.getStateList()){
			if(!state.equals(cycle.getStateOrigin())){
				for(Cycle cycleIt: cycleList){
					if(cycleIt.getStateOrigin().equals(state)){
						if(cycleIt.getConcatenated() == false){
							cycleIt.setConcatenated(true);
							path.add(state);
							course(cycleIt);
						}
					}
				}
				if(equal == false) path.add(state);
			}
		}
		return path;
	}
	
	public List<State> createEulerCycle(){
		for(Cycle cycle: cycleList){
			if(cycle.getConcatenated() == false){
				cycle.setConcatenated(true);
				path.add(cycle.getStateOrigin());
				course(cycle);
			}
		}
		return path;
	}
}