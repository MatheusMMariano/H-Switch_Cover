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
 * 1ª fase
 * For em todas as transições setando elas como false em visitadas
 * começar a percorrer a partir do estado inicial até achar o estado incial 
 * novamente. 
 * [Fazer depois -> colocar um peso nas transições para poder já no primeiro ciclo
 * passar no maior número de estados possíveis. ]
 * O primeiro ciclo montado deve ser adicionado em uma lista de MAP<estadoinicial, ciclo1>
 * se existir estados ainda não visitados faça:
 * pergar ciclo 1 percorrer os estados visitados, se estado for diferente do estado chave, faça:
 * pegar o estado e pergunta: tem transições de saida desse estado, se sim, começa a gerar um 
 * novo ciclo a partir desse estado, até retornar a ele. 
 * quando terminar, adiciona na lista de map de ciclos. 
 * como está dentro de um for, vai ser perguntado se o proximo estado tem transições de saida...
 * e assim vai até terminar e todos os estados serem visitados.
 * 
 *  2ª fase
 *
 * Nesse algoritmo para percorrer serão considerado os estados, e não as transições. Pois as
 * transições iniciais já foram transformadas em estados, assim já estará passando por todas 
 * as transições. Dessa forma otimiza o tamanho da sequencia de teste, que poderia ser gigantesca. 
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
				/** Percorre todos os estados true, e verifica qual tem transição sem ser visitada
				 * o primeiro que tiver transição o estado inicial receberá ele. **/
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
            	  * Cria método que verifica se este estado destino é o melhor, baseando no valor do seu peso
            	  * verificar se compensa pegar um estado que tenha peso menor e que seja válido
            	  */
            	 
            	estadoDestino.setPonderosity(estadoDestino.getPonderosity()+1); //se passar pelo estado aumenta seu peso

                 	/* *
                 	 * Antes de partir para o estado destino é feito uma verificação se este estado 
                 	 * é um outro estado inicial diferente do estado que começou a percorrer, se for
                 	 * deve optar em pagar outra transição.
                 	 * */
                	 if(!estadoDestino.equals(estadoInicial)){
                		if(!estadoDestino.equals(pEstadoAtual)){//essa validaçao vai parar de existir... retirar depois
                		
                		 /** ### inicio add **/
                		 listaEstados.add(estadoDestino);
       					 /** ### fim add **/
       					 
                		 transicao.setVisited(true);  
                		 estadoDestino.setVisited(true);
                		 
                		 casosTeste = casosTeste + estadoDestino.getName()+", ";
                		 
                		 percorrer(estadoDestino);
        
                		}
                	 }else {
                		// if(validarCicloCriado(listaEstados)==false){//mais de 50% dos estados não foram visitados
                			 /** ### inicio add **/
                		//	 listaEstados = new ArrayList();
                		//	 listaEstados.add(estadoInicial);
                			 /** ### fim add **/
                			 
                		//	 percorrer(estadoInicial);
                		// }

                		 transicao.setVisited(true);  
                		 
                		 listaCasosTeste.add(casosTeste);

                		 /** Aqui deverá ser criado um map dos ciclos criados **/
                		 Cycle ciclo = new Cycle();
                		 State estadoChave = new State();
                		 estadoChave = estadoInicial;
                		 ciclo.setStateOrigin(estadoChave);
                		 ciclo.setCycle(casosTeste);
                		 ciclo.setStateList(listaEstados);

                		 listaCiclos.add(ciclo);
                		 
                		 /** 
                		  *  Chamar método que vai validar se existem estados sem ser visitados 
                		  *  Essa é um sugestão para poder otimizar, mas poderia verificar as transições
                		  *  em vez dos estados. 
                		  **/
                    	 Boolean verifica = verificarEstadosNaoVisitados();               	 
                		 
                		 /** Se existir, chamar método que vai percorrer o ciclo montado, ignorando o estado chave
                		  * e por cada estado que passar perguntar, tem transição de saída, se sim, retornar esse estado 
                		  * setar ele como inicial e chamar o método percorrer, passando esse estado como parametro.  **/
                		
                		 /** Se não encontrar nenhum estado que ficou sem ser visitado, é porque passou por todas
                		  *  as transiçõe, sendo assim existe uma sequência completa. Para então de percorrer. Cai no break abaixo**/
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
		
		/** Retorna os estados iniciais e começar a percorrer**/
		Iterator estadosIterator = retornaEstadosInciais(maquina).iterator();
		
		while(estadosIterator.hasNext()){///voltar essa linha
			
			estadoInicial = (State)estadosIterator.next();///voltar essa linha

			 /** aqui deverá setar todas as transições e estados como não visitadas **/
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