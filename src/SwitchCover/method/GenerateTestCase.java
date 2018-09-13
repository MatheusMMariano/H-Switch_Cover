package SwitchCover.method;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import SwitchCover.graph.Cycle;
import SwitchCover.graph.Graph;
import SwitchCover.graph.State;
import SwitchCover.graph.Transition;

/**
 * 1� fase
 * For em todas as transi��es setando elas como false em visitadas
 * come�ar a percorrer a partir do estado inicial at� achar o estado incial 
 * novamente. 
 * [Fazer depois -> colocar um peso nas transi��es para poder j� no primeiro ciclo
 * passar no maior n�mero de estados poss�veis. ]
 * O primeiro ciclo montado deve ser adicionado em uma lista de MAP<estadoinicial, ciclo1>
 * se existir estados ainda n�o visitados fa�a:
 * pergar ciclo 1 percorrer os estados visitados, se estado for diferente do estado chave, fa�a:
 * pegar o estado e pergunta: tem transi��es de saida desse estado, se sim, come�a a gerar um 
 * novo ciclo a partir desse estado, at� retornar a ele. 
 * quando terminar, adiciona na lista de map de ciclos. 
 * como est� dentro de um for, vai ser perguntado se o proximo estado tem transi��es de saida...
 * e assim vai at� terminar e todos os estados serem visitados.
 * 
 *  2� fase
 *
 * Nesse algoritmo para percorrer ser�o considerado os estados, e n�o as transi��es. Pois as
 * transi��es iniciais j� foram transformadas em estados, assim j� estar� passando por todas 
 * as transi��es. Dessa forma otimiza o tamanho da sequencia de teste, que poderia ser gigantesca. 
 *  
 */

public class GenerateTestCase {
	
	/*private Graph graph = new Graph();
	
	private List<State> stateInitialList = new LinkedList<State>();
	private List<State> stateList = new LinkedList<State>();
	private List<String> testCaseList = new LinkedList<String>();
	private List<Cycle> cycleList = new LinkedList<Cycle>();
	private List<List<State>> cycleFinale = new LinkedList<List<State>>();
	
	private State stateInitial = new State();
	private boolean caseFound = false;
	private String testCase;*/
	
	private List listaEstadoInicial = new ArrayList();
	private String casosTeste;
	private List listaCasosTeste = new ArrayList();
	private State estadoInicial = new State();
	private Boolean casoEncontrado = false;
	private List listaCiclos;
	private List listaEstados;
	private Graph maquina = new Graph(); 
	private List cicloFinal = new ArrayList();
	
	public GenerateTestCase(Graph graph){
		this.maquina = graph;
	}
	
	//-----------------------------------------------------------------------------------------
	
	/*private List<State> returnInicialStates(Graph graph){
		Iterator<State> statesIterator = graph.getIteratorStateValue();
		while(statesIterator.hasNext()){
			State state = (State)statesIterator.next();	
			if(state.getTypeState().equals("inicial")){
				stateInitialList.add(state);
			}
		}
		return stateInitialList;
	}*/
	
	public List retornaEstadosInciais(Graph pMaquina){
		
		Iterator estadosIterator = pMaquina.getIteratorState();
         while (estadosIterator.hasNext()){
        	String nomeEstado = (String)estadosIterator.next();
        	
        	State estado = pMaquina.getState(nomeEstado);
        		if(estado.getTypeState().equals("inicial")){
        			listaEstadoInicial.add(estado);
        		}
        }
         
       return listaEstadoInicial;
	}
	
	//-----------------------------------------------------------------------------------------
	
	/*private void setFalseTransitionsStates(){
		Iterator<State> statesIterator = graph.getIteratorStateValue();
		while(statesIterator.hasNext()){
			State state = (State)statesIterator.next();
			state.setVisited(false);
			
			Iterator<Transition> transitionsIterator = state.getTransitionIterator();
			
			while(transitionsIterator.hasNext()){
				Transition t = (Transition)transitionsIterator.next();
				t.setVisited(false);
			}
		}
	}*/
	
	public void setFalseTransicoesEstados(){
		
		Iterator estadosIterator = maquina.getIteratorState();
        while (estadosIterator.hasNext()){
        	String nomeEstado = (String)estadosIterator.next();
        	
        	State estado = maquina.getState(nomeEstado);
        	
        	estado.setVisited(false);
        		
        	Iterator transicoesIterator = estado.getTransitionIterator();
            while (transicoesIterator.hasNext()){
                Transition tran = (Transition)transicoesIterator.next();	               	                
                tran.setVisited(false);
            }
        }			
	}

	//-----------------------------------------------------------------------------------------
	
	/*private boolean checkStateNotVisited(){
		Iterator<State> stateIterator = graph.getIteratorStateValue();
		
		while(stateIterator.hasNext()){
			if(stateIterator.next().getVisited() == false){
				
				Iterator<State> stateIt = graph.getIteratorStateValue();
				while(stateIt.hasNext()){
					State stateTrans = (State)stateIt.next();
				
					if(stateTrans.getVisited() == true){
						Iterator<Transition> transitionIterator = stateTrans.getTransitionIterator();
						
						while(transitionIterator.hasNext()){							
							if(transitionIterator.next().getVisited() == false){
								stateInitial = stateTrans;
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}*/
	
	public Boolean verificarEstadosNaoVisitados(){
		Iterator estadosIterator = maquina.getIteratorState();
        
		while (estadosIterator.hasNext()){
        	String nomeEstado = (String)estadosIterator.next();
        	State estado = maquina.getState(nomeEstado);
			
        	if(estado.getVisited()== false){
				/** Percorre todos os estados true, e verifica qual tem transi��o sem ser visitada
				 * o primeiro que tiver transi��o o estado inicial receber� ele. **/
				Iterator estadosIt= maquina.getIteratorState();
				
				while (estadosIt.hasNext()){
				   	String nome = (String)estadosIt.next();        	
				   	State estadoTrans = maquina.getState(nome);
				        	
				    if(estadoTrans.getVisited()==true){    	
				    Iterator tranIt = estadoTrans.getTransitionIterator();
					
				    while (tranIt.hasNext()){
					  	 Transition transicao = (Transition)tranIt.next();
					
					  	 if(transicao.getVisited() == false){
					  		 estadoInicial = estadoTrans;
					  		 return true;
					     }
					}
				}
			}
		}
	}
	return false;
}
	
	//-----------------------------------------------------------------------------------------
	
	/*private List<Cycle> course(State state){
		Iterator<Transition> transitionsIterator = state.getTransitionIterator();
		
		while(transitionsIterator.hasNext() && caseFound == false){
			Transition transition = (Transition)transitionsIterator.next();
			
			if(transition.getVisited() == false){
				State stateDestination = transition.getDestination();
				stateDestination.setPonderosity(stateDestination.getPonderosity()+1);
				
				if(!stateDestination.equals(stateInitial)){
					if (!stateDestination.equals(state)){
						stateList.add(stateDestination);
						transition.setVisited(true);
						stateDestination.setVisited(true);
						testCase = testCase + stateDestination.getName() + ", ";
						course(stateDestination);
					}
				}
				else{
					transition.setVisited(true);
					listTestCase.add(testCase);
					Cycle cycle = new Cycle();
					State keyState = new State();
					keyState = stateInitial;
					cycle.setStateOrigin(keyState);
					cycle.setCycle(testCase);
					cycle.setStateList(stateList);
					cycleList.add(cycle);
					
					if(checkStateNotVisited()){
						stateList = new ArrayList<State>();
						stateList.add(stateInitial);
						testCase = new String();
						stateInitial.setVisited(true);
						course(stateInitial);
					}
					
					caseFound = true;
					break;
				}
			}
		}
		return cycleList;
	}*/
	
	public List percorrer(State pEstadoAtual){
		
		//System.out.println("Atual -> " + pEstadoAtual.getNome());
	
		Iterator transicoesIterator = pEstadoAtual.getTransitionIterator();
		
		while (transicoesIterator.hasNext() && casoEncontrado==false){
			Transition transicao = (Transition)transicoesIterator.next();	
            
            if (transicao.getVisited() == false){
            	State estadoDestino = transicao.getDestination();
            	// System.out.println("Destino -> " + estadoDestino.getNome());
            	 
            	 /**
            	  * Cria m�todo que verifica se este estado destino � o melhor, baseando no valor do seu peso
            	  * verificar se compensa pegar um estado que tenha peso menor e que seja v�lido
            	  */
            	 
            	estadoDestino.setPonderosity(estadoDestino.getPonderosity()+1); //se passar pelo estado aumenta seu peso

                 	/* *
                 	 * Antes de partir para o estado destino � feito uma verifica��o se este estado 
                 	 * � um outro estado inicial diferente do estado que come�ou a percorrer, se for
                 	 * deve optar em pagar outra transi��o.
                 	 * */
                	 if(!estadoDestino.equals(estadoInicial)){
                		if(!estadoDestino.equals(pEstadoAtual)){//essa valida�ao vai parar de existir... retirar depois
                		
                		 /** ### inicio add **/
                		 listaEstados.add(estadoDestino);
       					 /** ### fim add **/
       					 
                		 transicao.setVisited(true);  
                		 estadoDestino.setVisited(true);
                		 
                		 casosTeste = casosTeste + estadoDestino.getName()+", ";
                		 
                		 percorrer(estadoDestino);
        
                		}
                	 }else {
                		// if(validarCicloCriado(listaEstados)==false){//mais de 50% dos estados n�o foram visitados
                			 /** ### inicio add **/
                		//	 listaEstados = new ArrayList();
                		//	 listaEstados.add(estadoInicial);
                			 /** ### fim add **/
                			 
                		//	 percorrer(estadoInicial);
                		// }

                		 transicao.setVisited(true);  
                		 
                		 listaCasosTeste.add(casosTeste);

                		 /** Aqui dever� ser criado um map dos ciclos criados **/
                		 Cycle ciclo = new Cycle();
                		 State estadoChave = new State();
                		 estadoChave = estadoInicial;
                		 ciclo.setStateOrigin(estadoChave);
                		 ciclo.setCycle(casosTeste);
                		 ciclo.setStateList(listaEstados);

                		 listaCiclos.add(ciclo);
                		 
                		 /** 
                		  *  Chamar m�todo que vai validar se existem estados sem ser visitados 
                		  *  Essa � um sugest�o para poder otimizar, mas poderia verificar as transi��es
                		  *  em vez dos estados. 
                		  **/
                    	 Boolean verifica = verificarEstadosNaoVisitados();               	 
                		 
                		 /** Se existir, chamar m�todo que vai percorrer o ciclo montado, ignorando o estado chave
                		  * e por cada estado que passar perguntar, tem transi��o de sa�da, se sim, retornar esse estado 
                		  * setar ele como inicial e chamar o m�todo percorrer, passando esse estado como parametro.  **/
                		
                		 /** Se n�o encontrar nenhum estado que ficou sem ser visitado, � porque passou por todas
                		  *  as transi��e, sendo assim existe uma sequ�ncia completa. Para ent�o de percorrer. Cai no break abaixo**/
                		 if(verifica){
                			 
                			 /** ### inicio add **/
        					 listaEstados = new ArrayList();
        					 listaEstados.add(estadoInicial);
        					 /** ### fim add **/
        					 
        					 casosTeste = new String();
        					 estadoInicial.setVisited(true);
        					 
                			 percorrer(estadoInicial);                   			 
                		 }

                		 casoEncontrado = true;
                		 break;
			}
		}
	}
		return listaCiclos;
	}
	
	//-----------------------------------------------------------------------------------------
	
	/*public List<List<State>> initial(){
		Iterator<State> statesIterator = returnInicialStates(graph).iterator();
		
		while(statesIterator.hasNext()){
			stateInitial = (State)statesIterator.next();
			setFalseTransitionsStates();
			stateInitial.setVisited(true);
			stateInitial.setInitialSequence(true);
			
			stateList = new LinkedList<State>();
			cycleList = new LinkedList<Cycle>();
			stateList.add(stateInitial);
			
			EulerCycle makeCycle = new EulerCycle(course(stateInitial));
			List<State> eulerCycle = makeCycle.createEulerCycle();
			Iterator<State> it = eulerCycle.iterator();
			int cont = 0;
			
			while(it.hasNext()) System.out.println(cont++ + " - " + it.next().getName());
			cycleFinale.add(eulerCycle);
		}
		return cycleFinale;
	}*/
	
	public List inicio(){
		
		List listaRetornoCiclo = new ArrayList();
		
		/** Retorna os estados iniciais e come�ar a percorrer**/
		Iterator estadosIterator = retornaEstadosInciais(maquina).iterator();
		
		while(estadosIterator.hasNext()){///voltar essa linha
			
			estadoInicial = (State)estadosIterator.next();///voltar essa linha

			 /** aqui dever� setar todas as transi��es e estados como n�o visitadas **/
			setFalseTransicoesEstados();
			
			estadoInicial.setVisited(true);
			
			estadoInicial.setInitialSequence(true);
			
			 /** ### inicio add **/
			 listaEstados = new ArrayList();
			 listaEstados.add(estadoInicial);
			 /** ### fim add **/
			 
			 listaCiclos = new ArrayList();
			 	 
			 listaRetornoCiclo = percorrer(estadoInicial);
			 
			 /**
			  * APAGAR!!! APENAS EXIBE OS CICLOS CRIADOS
			  */
			 //System.out.println(" \n\n\n ------>>>>> Ciclo para o estado inicial: " + estadoInicial.getName());
				Iterator itc = listaRetornoCiclo.iterator();
		         while (itc.hasNext()){
		        	Cycle ciclo = (Cycle)itc.next();
		        	 //System.out.println("\n");
		        	 String cicloMontado = new String();
		        	 
		        	 Iterator itce = ciclo.getStateList().iterator();
			         while (itce.hasNext()){
			        	 State estado = (State)itce.next();
			        	cicloMontado = cicloMontado + estado.getName() + ", ";
			         }
			         
			         //System.out.println(cicloMontado);
		        	
		         }

		         casoEncontrado = false;
			 /**
			  * APAGAR Acima!!! APENAS EXIBE OS CICLOS CRIADOS
			  */
			 //System.out.println();
			 
			 EulerCycle montaCiclo = new EulerCycle(listaRetornoCiclo);
			 int cont=0;
			 Iterator it = montaCiclo.montaCominhoEuleriano().iterator();
	         while (it.hasNext()){
	        	State estado = (State)it.next();
	        	
	        	//System.out.println(cont++ + " - " + estado.getName());
	        	
	         	}
	         
			 cicloFinal.add(montaCiclo.montaCominhoEuleriano());

		}
		
		return cicloFinal;
	}
	//-----------------------------------------------------------------------------------------

}