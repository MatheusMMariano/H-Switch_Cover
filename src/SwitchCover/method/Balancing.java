package SwitchCover.method;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import SwitchCover.graph.Graph;
import SwitchCover.graph.State;
import SwitchCover.graph.Transition;

public class Balancing {
	
	private int lim = 100;
	private int menor_valor;
	
	//-----------------------------------------------------------
	/*public int returnNumberOutputTransition(State state){
		if(state.getTransitions() != null) return state.getTransitions().size();
		else return 0;
	}*/
	
	public int retornaNumTransSaida(State pEstado){
		int numSaida = 0;
		
		/* Pega n�mero de transi��o de sa�da */
		if(pEstado.getTransitions()!= null){
    	    Iterator transIt = pEstado.getTransitionIterator();
    		while (transIt.hasNext()){     
    			Transition transicao = (Transition)transIt.next();
    			numSaida++;
    		}
		}
		return numSaida;
	}
	
	
	//-----------------------------------------------------------
	/*public int returnNumberInputTransition(Graph graph, State state){
		int numberInput = 0;
		Iterator<State> statesIterator = graph.getIteratorStateValue();
		
		while(statesIterator.hasNext()){
			State currentState = statesIterator.next();
			if(currentState.getTransitions() != null){
				Iterator<Transition> transitionIterator = currentState.getTransitionIterator();
				while(transitionIterator.hasNext()){
					if(transitionIterator.next().getDestination().getName().equals(state.getName())){
						numberInput++;
					}
				}
			}
		}
		return numberInput;
	}*/
	
	public int retornaNumTransEntrada(Graph pMaquina, State pEstado){
		
		int numEntrada = 0;
		Iterator estadosIt = pMaquina.getIteratorState();		
		while (estadosIt.hasNext()){
	
			String nomeEstado = (String)estadosIt.next();	
			State estado = pMaquina.getState(nomeEstado);
		
			if(estado.getTransitions()!= null){
				Iterator transIt = estado.getTransitionIterator();   		
				while (transIt.hasNext()){     
					Transition transicao = (Transition)transIt.next();
					String estadoDestino = transicao.getDestination().getName();
					if(estadoDestino.equals(pEstado.getName())){
						numEntrada++;
					}
				}    				
			}
		}
		return numEntrada;
	}
	
	
	//-----------------------------------------------------------
	//CHECK ALL STATES IS BALANCED
	/*public boolean checkEveryStateBalanced(Graph graph){
		Iterator<State> statesIterator = graph.getIteratorStateValue();

		while(statesIterator.hasNext()){
			State state = statesIterator.next();

			//Get the number of output's transitions //Get the number of input's transitions
			if(returnNumberOutputTransition(state) != returnNumberInputTransition(graph, state)) return false;
		}
		return true;
	}*/
	
	public Boolean verificaBalancTodosEstados(Graph pMaquina){
		
		int numTransicaoSaida = 0;
		int numTransicaoEntrada = 0;
		
		Iterator estadosIt = pMaquina.getIteratorState();
		
		while (estadosIt.hasNext()){
			String nomeEstado = (String)estadosIt.next();
			State estado = pMaquina.getState(nomeEstado);
			
			// Pega n�mero de transi��o de sa�da 
			numTransicaoSaida = retornaNumTransSaida(estado);
			
			// Pega n�mero de transi��o de entrada 
			numTransicaoEntrada = retornaNumTransEntrada(pMaquina, estado);
			
			if(numTransicaoSaida!= numTransicaoEntrada){
				return false;
			}
		}
		
		return true;
	 }
	
	
	//-----------------------------------------------------------
	
	/*public boolean checkBalancing(Graph graph, State state){
		if(returnNumberOutputTransition(state) != returnNumberInputTransition(graph, state)) return false;
		return true;
	}*/
	
	public Boolean verificaBalanceamento(Graph pMaquina, State pEstado){
		int numTransicaoSaida = 0;
		int numTransicaoEntrada = 0;
	
			// Pega n�mero de transi��o de sa�da 
			numTransicaoSaida = retornaNumTransSaida(pEstado);
			
			// Pega n�mero de transi��o de entrada 
			numTransicaoEntrada = retornaNumTransEntrada(pMaquina, pEstado);
			
				if(numTransicaoSaida!= numTransicaoEntrada){
						return false;
				}
			return true;
	 }
	
	//-----------------------------------------------------------
	
	/*public boolean checkInputOutput(Graph graph, State state){
		if(returnNumberInputTransition(graph, state) > returnNumberOutputTransition(state)) return false;
		return true;
	}*/
	
	public Boolean verificaEntradaSaida(Graph pMaquina, State pEstado){
		int numTransicaoSaida = 0;
		int numTransicaoEntrada = 0;
		// Pega n�mero de transi��o de sa�da 
		numTransicaoSaida = retornaNumTransSaida(pEstado);
			
		// Pega n�mero de transi��o de entrada 
		numTransicaoEntrada = retornaNumTransEntrada(pMaquina, pEstado);
		
		if(numTransicaoEntrada > numTransicaoSaida){
			return false;
		}
		return true;
	 }	

	//-----------------------------------------------------------

	//A COISA PARA TROCAR
	/*public boolean checkIfHaveFather(Graph graph, State stateUnbalanced){		
		//List listFather = new LinkedList<>();
		Iterator<State> statesIterator = graph.getIteratorStateValue();
		
		//This while return only one list of father of a determined state
		while(statesIterator.hasNext()){
			State state = statesIterator.next();
			
			if(state.getTransitions() != null){
				Iterator<Transition> transitionsIterator = state.getTransitionIterator();
				
				while(transitionsIterator.hasNext()){					
					if(transitionsIterator.next().getDestination().getName().equals(stateUnbalanced.getName())) return true;
				}
			}
		}
		
		return false;
	}*/
	
	public boolean verificaSeTemPai(Graph pMaquina, State estadoDesbalanceado){
		List listaPais = new ArrayList();
		Iterator estadosIt = pMaquina.getIteratorState();
		
		//esse while vai retornar apenas a lista de pais de um determinado estado
		while (estadosIt.hasNext()){
				String nomeEstado = (String)estadosIt.next();	
				State estado = pMaquina.getState(nomeEstado);
				if(estado.getTransitions()!= null){
					Iterator transIt = estado.getTransitionIterator();   		
					while (transIt.hasNext()){     
						Transition transicao = (Transition)transIt.next();
						String nomeEstadoDestino = transicao.getDestination().getName();
						State estadoDestino = transicao.getDestination();
						if(nomeEstadoDestino.equals(estadoDesbalanceado.getName())){
							listaPais.add(estado);
						}
					}    				
				}
		    }
		if(listaPais.size()>0){
			return true;
		}
		else return false;
	}
	
	//-----------------------------------------------------------

	/*public State returnBetterFather(Graph graph, State stateInicialSearch){
		List<State> listFather = new ArrayList<State>();
		Iterator<State> statesIterator = graph.getIteratorStateValue();
		List<State> listFatherBalanced = new ArrayList<State>();
		List<State> listFatherDesbalanced = new ArrayList<State>();
		State stateUtil = null;
				
		//This while only returns a list of fathers of a determined state
		while(statesIterator.hasNext()){
			State state = statesIterator.next();
			if(state.getTransitions() != null){
				Iterator<Transition> transitionsIterator = state.getTransitionIterator();
				while(transitionsIterator.hasNext()){
					if(transitionsIterator.next().getDestination().getName().equals(stateInicialSearch.getName())) listFather.add(state);
				}
			}
		}
		
		if(listFather.size() > 0){
			for(State s: listFather){
				//If father is desbalanced, add in 'listFatherDesbalanced'
				if(checkBalancing(graph, s) == false) listFatherDesbalanced.add(s);
				else listFatherBalanced.add(s);
			}
			
			if(listFatherDesbalanced.size() > 0){
				for(State s: listFatherDesbalanced){
					if(checkInputOutput(graph, s)) stateUtil = s;
					else stateUtil = s;	
				}
			}
			else if(listFatherBalanced.size() > 1){
				lower_value = listFatherBalanced.get(0).getCounter();

				for(State s: listFatherBalanced){
					if(lower_value > s.getCounter()){
						lower_value = s.getCounter();
						stateUtil = s;
					}
				}				
				if(stateUtil == null) stateUtil = (State)listFatherBalanced.get(0);
			}
			else stateUtil = (State)listFatherBalanced.get(0);
		}
		return stateUtil;
	}*/
	
	public State retornaMelhorPai(Graph pMaquina, State pEstadoInicialBusca) {
		List listaPais = new ArrayList();
		Iterator estadosIt = pMaquina.getIteratorState();
		List listaPaiBalanceado = new ArrayList();
		List listaPaiDesbalanceado = new ArrayList();
		State estadoUtil = null;
		
		//esse while vai retornar apenas a lista de pais de um determinado estado
		while (estadosIt.hasNext()){
				String nomeEstado = (String)estadosIt.next();	
				State estado = pMaquina.getState(nomeEstado);
				if(estado.getTransitions()!= null){
					Iterator transIt = estado.getTransitionIterator();   		
					while (transIt.hasNext()){     
						Transition transicao = (Transition)transIt.next();
						String nomeEstadoDestino = transicao.getDestination().getName();
						State estadoDestino = transicao.getDestination();
						if(nomeEstadoDestino.equals(pEstadoInicialBusca.getName())){
							listaPais.add(estado);
						}
					}    				
				}
		    }
		 
		
		if (listaPais.size()>0){//tirar
		/**
		 * Percorre esses pais verificando o seu balaceamento, 
		 */
		Iterator itPais = listaPais.iterator();

		while (itPais.hasNext()){
			State estadoTemp = (State)itPais.next();
			//se pai estiver desbalanceado adiciona na lista
			if(verificaBalanceamento(pMaquina, estadoTemp)== false)
				listaPaiDesbalanceado.add(estadoTemp);
			else{
				listaPaiBalanceado.add(estadoTemp);
			}
		}
		
		if(listaPaiDesbalanceado.size()>0){
			Iterator itPaisDes = listaPaiDesbalanceado.iterator();
			while (itPaisDes.hasNext()){
				State estadoTempDes = (State)itPaisDes.next();
				if(verificaEntradaSaida(pMaquina, estadoTempDes)){
					estadoUtil = estadoTempDes;
				}else estadoUtil = estadoTempDes;
			}
		}else if(listaPaiBalanceado.size()>1){
			
			State menor = (State)listaPaiBalanceado.get(0); 
			menor_valor = menor.getCounter();
			for (int i = 0; i < listaPaiBalanceado.size(); i++) {
				State estadoContador = (State)listaPaiBalanceado.get(i);
				
				if (menor_valor > estadoContador.getCounter()) {
					menor_valor = estadoContador.getCounter();
					estadoUtil = estadoContador;
				}
			}
			if(estadoUtil==null){
				estadoUtil =(State)listaPaiBalanceado.get(0); 
			}
			
		}
		else
			estadoUtil =(State)listaPaiBalanceado.get(0);
		}
			return estadoUtil; 
	}
	
	//-----------------------------------------------------------
	
	/*public void addTransition(State stateSource, State stateDestination){
		Transition newTransition = new Transition();
		
		newTransition.setSource(stateSource);
		newTransition.setDestination(stateDestination);
		newTransition.setInput(stateSource.getName()+stateDestination.getName());
		
		stateSource.setTransition(newTransition);
		
		if(stateDestination.getBalancedStatus() == true) stateDestination.setBalancedStatus(false);
	}*/
	
	private String inputValue(State pEstadoOrigem, State pEstadoDestino) {
		List<Transition> transitions = pEstadoOrigem.getTransitions();
		for(Transition t: transitions) {
			if(t.getSource().equals(pEstadoOrigem) && t.getDestination().equals(pEstadoDestino)) {
				return t.getInput();
			}
		}
		return pEstadoOrigem.getName()+">0";
	}
	
	public void adicionaAresta(State pEstadoOrigem, State pEstadoDestino){

		Transition novaAresta = new Transition();
		novaAresta.setSource(pEstadoOrigem);
		novaAresta.setDestination(pEstadoDestino);
		
		novaAresta.setInput(inputValue(pEstadoOrigem, pEstadoDestino));
		//novaAresta.setInput(pEstadoOrigem.getName()+pEstadoDestino.getName());
		
		pEstadoOrigem.setTransition(novaAresta);
		
		if(pEstadoDestino.getBalancedStatus()==true){
			pEstadoDestino.setBalancedStatus(false);
	
		
		}
	}
	
	//-----------------------------------------------------------

	
	/*public State returnBetterSon(Graph graph, State stateUnbalanced){
		List<State> listSonBalanced = new ArrayList<State>();
		List<State> listSonUnbalanced = new ArrayList<State>();
		State stateUtil = null;
		
		List<Transition> listTransitions = stateUnbalanced.getTransitions();
		Iterator<Transition> transitionIterator = listTransitions.iterator();
		
		//This while return every state's son, and list the that is balanced and don't balanced
		while(transitionIterator.hasNext()){
			Transition transitionSon = transitionIterator.next();
			State stateSon = transitionSon.getDestination();
			
			if(checkBalancing(graph, stateSon) == false) listSonUnbalanced.add(stateSon);
			else listSonBalanced.add(stateSon);
		}
		
		if(listSonUnbalanced.size() > 0){
			Iterator<State> iteratorSonDesbalanced = listSonUnbalanced.iterator();
			
			while(iteratorSonDesbalanced.hasNext()){
				State stateUnbalancedTemp = (State) iteratorSonDesbalanced.next();
				
				if(checkInputOutput(graph, stateUnbalancedTemp)) stateUtil = stateUnbalancedTemp;
				else stateUtil = stateUnbalancedTemp;
			}
		}
		else if(listSonBalanced.size() > 1){
			//In son balanced's list, if have a state more balanced to that other, get the lower balanced
			// for add the transition. For this, get the value of counter of balancing that each state have.
			State lower = (State)listSonBalanced.get(0);
			lower_value = lower.getCounter();
			
			for(State stateCounter: listSonBalanced){
				if(lower_value > stateCounter.getCounter()){
					lower_value = stateCounter.getCounter();
					stateUtil = stateCounter;
				}
			}			
			if(stateUtil == null) stateUtil = (State)listSonBalanced.get(0);
		}
		else stateUtil = listSonBalanced.get(0);
		
		return stateUtil;
	}*/
	
public State retornaMelhorFilho(Graph pMaquina, State pEstadoDesbalanceado) {
		
		List listaFilhos = new ArrayList();
		Iterator estadosIt = pMaquina.getIteratorState();
		List listaFilhoBalanceado = new ArrayList();
		List listaFilhoDesbalanceado = new ArrayList();
		State estadoUtil = null;
	
		
		List listaTrans = pEstadoDesbalanceado.getTransitions();
	    Iterator itrans = listaTrans.iterator();
	    
	    /*
	     * Este while vai retornar todos os filhos do estado em quest�o
	     * e lista os que est�o balanceados e os que nao est�o balanceados
	     */
	    while (itrans.hasNext()) {
	    	Transition transicaoFilho = (Transition) itrans.next();
			State estadoFilho = transicaoFilho.getDestination();
			
			if(verificaBalanceamento(pMaquina, estadoFilho)== false){
				listaFilhoDesbalanceado.add(estadoFilho);
			}else{
				listaFilhoBalanceado.add(estadoFilho);
			}
	    }
	    
		if(listaFilhoDesbalanceado.size()>0){
			Iterator itFilhosDes = listaFilhoDesbalanceado.iterator();
			while (itFilhosDes.hasNext()){
				State estadoTempDes = (State)itFilhosDes.next();
				if(verificaEntradaSaida(pMaquina, estadoTempDes)){
					estadoUtil = estadoTempDes;
				}else estadoUtil = estadoTempDes;
			}
		}else if(listaFilhoBalanceado.size()>1){
			
			/**
			 * Dentre a lista de filhos balanceados, se tiver alguns dos estados ja foi balnceado mais do que 
			 * o outro, pegar� o menos balanceado para atribuir a aresta. Para isso pega o valor do contador
			 * de balanceamento que cada estado tem.
			 */
			State menor = (State)listaFilhoBalanceado.get(0); 
			menor_valor = menor.getCounter();
			for (int i = 0; i < listaFilhoBalanceado.size(); i++) {
				State estadoContador = (State)listaFilhoBalanceado.get(i);
				
				if (menor_valor > estadoContador.getCounter()) {
					menor_valor = estadoContador.getCounter();
					estadoUtil = estadoContador;
				}
			}
			if(estadoUtil==null){
				estadoUtil =(State)listaFilhoBalanceado.get(0); 
			}
		}
		else estadoUtil =(State)listaFilhoBalanceado.get(0); 

		return estadoUtil;
		
	}
	
	//-----------------------------------------------------------

	
	/*public void balancesState(Graph graph, State state){
		if(checkInputOutput(graph, state)){
			//Here was found that is better add a father's transition of desbalanced state, but have that know
			//after turned the transitions in states, if every of state have father
			//if not have, puts in the son transitions
			if(checkIfHaveFather(graph, state)){
				State stateFather = returnBetterFather(graph, state);
				addTransition(stateFather, state);
				state.setCounter(1);
			}
			else{
				State stateSon = returnBetterSon(graph, state);
				addTransition(state, stateSon);
				state.setCounter(1);
			}
		}
		else{//If input at > that output
			State stateSon = returnBetterSon(graph, state);
			addTransition(state, stateSon);
			state.setCounter(1);
		}
	}*/
	
	public void balanceiaEstado(Graph pMaquina, State estadoDesbalanceado){
		
		/**
		 * Primeira coisa a se fazer � verificar onde o estado esta carente de aresta, na entrada ou na saida.
		 * Se a saida for maior que a entrada, ja sabemos que a melhor op��o para se adicionar uma aresta � 
		 * na entrada. 
		 */
		if(verificaEntradaSaida(pMaquina, estadoDesbalanceado)){//se a saida for maior retorna true
			//aqui verificou-se que � melhor adicinar uma aresta do pai do estado desbalanceado, por�m tem que saber
			//depois que transformou as arestas em estados, se todos dos estados continuam tendo pais
			//se nao tiver, coloca no filho as arestas
			if(verificaSeTemPai(pMaquina, estadoDesbalanceado)){
				State estadoPai = retornaMelhorPai(pMaquina, estadoDesbalanceado);
				adicionaAresta(estadoPai, estadoDesbalanceado);
				estadoDesbalanceado.setCounter(1);
			}
			else{
				State estadoFilho = retornaMelhorFilho(pMaquina, estadoDesbalanceado);
				adicionaAresta(estadoDesbalanceado, estadoFilho);
				estadoDesbalanceado.setCounter(1);
			}
		}
		else{//se entrada for maior que saida
			State estadoFilho = retornaMelhorFilho(pMaquina, estadoDesbalanceado);
			adicionaAresta(estadoDesbalanceado, estadoFilho);
			estadoDesbalanceado.setCounter(1);
		}
		
	}
	
	//-----------------------------------------------------------

	
	/*public Graph first(Graph graph){
		int cont = 0;
		
		while(checkEveryStateBalanced(graph) == false && cont < lim){
			cont++;
			Iterator<State> statesIterator = graph.getIteratorStateValue();
			
			while(statesIterator.hasNext()){
				State state = statesIterator.next();
				if(checkBalancing(graph, state) == false) balancesState(graph, state);
			}
		}
		return graph;
	}*/
	
	public Graph inicio(Graph pMaquina){
		int cont=0;
			   
		while (verificaBalancTodosEstados(pMaquina) == false && cont<lim) {
			cont++;
			//System.out.println("Balanceando...");
				Iterator estadosIt = pMaquina.getIteratorState();

				while (estadosIt.hasNext()) {

					String nomeEstado = (String) estadosIt.next();
					State estado = pMaquina.getState(nomeEstado);

					if (verificaBalanceamento(pMaquina, estado) == false) {
						balanceiaEstado(pMaquina, estado);
					}
				}
	    }
			return pMaquina;

		}
}
