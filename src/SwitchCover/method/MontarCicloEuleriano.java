package SwitchCover.method;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import SwitchCover.graph.Cycle;
import SwitchCover.graph.State;

public class MontarCicloEuleriano {
	
	public MontarCicloEuleriano (List pListaCiclos){
		listaCiclos = pListaCiclos;
	}
	
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

	public List percorrer(Cycle pCicloAtual){

		/** Come�a a percorrer os estados do ciclo atual **/
		Iterator estadosIterator = pCicloAtual.getStateList().iterator();
         while (estadosIterator.hasNext()){
        	 State estadoAtual = (State) estadosIterator.next();//pega o estado para compara��o
        	         	 
        	 if(!estadoAtual.equals(pCicloAtual.getStateOrigin())){
        		 
        		 /** Come�a a percorrer a lista de ciclos, perguntando: existe algum ciclo que tem como estado origem a esse estado atual? **/
        		 Iterator ciclosIterator = listaCiclos.iterator();
        		 while (ciclosIterator.hasNext()){
        			Cycle cicloAtual = (Cycle)ciclosIterator.next();

        			if(cicloAtual.getStateOrigin().equals(estadoAtual)){
        				if(cicloAtual.getConcatenated()==false){
        				
        					cicloAtual.setConcatenated(true);
        					
        				/**
        				 * adicionar o estado atual na listacaminho e chamar o m�todo percorrer passando o cicloAtual
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
	
	
	List caminho = new ArrayList();
	List listaCiclos = new ArrayList();
	Boolean igual = false;
	List cicloFinal = new ArrayList();
}

/** 
DESCRI��O DO M�TODO

1 - get(0) para pegar o primeiro ciclo da lista
2 - come�ar percorrer esse primeiro ciclo
3 - adicionar o primeiro estado do ciclo na lista de ciclo euleriano, a partir do segundo perguntar:
	existe algum outro ciclo que tem como estado origem a esse estado atual?
	sim: adicionar a lista inteira na lista de ciclo euleriano. 
	n�o: continuar perguntando para os outros estados at� terminar.
4 - se a resposta foi sim, depois de colar a lista inteira, continuar perguntando aos outros estados at� terminar.
5 - retornar lista final.
**/