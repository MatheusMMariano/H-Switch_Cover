package SwitchCover.method;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import SwitchCover.graph.Cycle;
import SwitchCover.graph.State;

public class EulerCycle {
	
	/*private List<State> path = new ArrayList<State>();
	private List<Cycle> cycleList = new ArrayList<Cycle>();
	private boolean equal = false;*/
	
	List caminho = new ArrayList();
	List listaCiclos = new ArrayList();
	Boolean igual = false;
	List cicloFinal = new ArrayList();
	
	public EulerCycle(List cycleList){
		this.listaCiclos = cycleList;
	}

	//--------------------------------------------------------------

	/*public List<State> course(Cycle cycle){
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
	}*/
	
	public List percorrer(Cycle pCicloAtual){

		/** Começa a percorrer os estados do ciclo atual **/
		Iterator estadosIterator = pCicloAtual.getStateList().iterator();
         while (estadosIterator.hasNext()){
        	 State estadoAtual = (State) estadosIterator.next();//pega o estado para comparação
        	         	 
        	 if(!estadoAtual.equals(pCicloAtual.getStateOrigin())){
        		 
        		 /** Começa a percorrer a lista de ciclos, perguntando: existe algum ciclo que tem como estado origem a esse estado atual? **/
        		 Iterator ciclosIterator = listaCiclos.iterator();
        		 while (ciclosIterator.hasNext()){
        			Cycle cicloAtual = (Cycle)ciclosIterator.next();

        			if(cicloAtual.getStateOrigin().equals(estadoAtual)){
        				if(cicloAtual.getConcatenated()==false){
        				
        					cicloAtual.setConcatenated(true);
        					
        				/**
        				 * adicionar o estado atual na listacaminho e chamar o método percorrer passando o cicloAtual
        				 */
        				caminho.add(estadoAtual);
        				percorrer(cicloAtual);
        				}
        			}     			
        		 } 
        		 if(igual==false){
        			 caminho.add(estadoAtual);
        		 }
        	 }
         }
		
		return caminho;
	}
	
	//--------------------------------------------------------------
	
	/*public List<State> createEulerCycle(){
		for(Cycle cycle: cycleList){
			if(cycle.getConcatenated() == false){
				cycle.setConcatenated(true);
				path.add(cycle.getStateOrigin());
				course(cycle);
			}
		}
		return path;
	}*/
	
	public List montaCominhoEuleriano(){
		
		Iterator cicloit = listaCiclos.iterator();
        while (cicloit.hasNext()){
       	Cycle cicloAtual = (Cycle) cicloit.next();  
       	
       	if(cicloAtual.getConcatenated()==false){
       	
       		cicloAtual.setConcatenated(true);
       	
       		caminho.add(cicloAtual.getStateOrigin());
       		
       		percorrer(cicloAtual);
       	}
       		
        }
        
		return caminho;
	}
	
	//---------------------------------------------------------------
}