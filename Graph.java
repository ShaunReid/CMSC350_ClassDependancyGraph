/*
CMSC350 
8 Mar 2020
 Shaun Reid
 
 The Graph class is a Generic class used to build out the Graph*/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import java.util.Stack;


public class Graph<T> {

	int count = 0;
	HashMap<T, Integer> classHash = new HashMap<T, Integer>();
	HashMap<Integer, T> revClassHash = new HashMap<Integer, T>();
	ArrayList<LinkedList<Integer>> graph = new ArrayList<LinkedList<Integer>>();
	Stack<Integer> stack = new Stack<Integer>();
	StringBuilder orderedStr = new StringBuilder();
	String returnedStr = new String();
	ArrayList<Integer> discovered = new ArrayList<Integer>();
	ArrayList<Integer> finished = new ArrayList<Integer>();
	
	public Graph() {
		
	}
	
	//Initialize the graph
	public void initializeGraph(String classString) {
		T[] tokens = null;
		tokens = (T[]) classString.split("\\s");
		
		for(int i = 0; i < tokens.length; i++) {
			if(classHash.containsKey(tokens[i]) == false) {
				classHash.put(tokens[i], count);
				revClassHash.put(count, tokens[i]);
				graph.add(new LinkedList<Integer>());
				count++;
			}
		}
		
		for(int i = 1; i < tokens.length; i++) {
			addEdge(classHash.get(tokens[0]), classHash.get(tokens[i]));
		}
		
		
	}
	
		
	//Add an edge to linked lists
	public void addEdge(int source, int destination) {
		graph.get(source).add(destination);
	}
	
	//Generate topological order
	public void depthFirstSearch(Integer vertex) throws CycleFoundException {
		
		
		
		
		if(discovered.contains(vertex)) {
			
			throw new CycleFoundException("");
		}
		
		if(finished.contains(vertex)) {
			return;
		}
		
		discovered.add(vertex);
		
		for(int i = 0; i < graph.get(vertex).size(); i++) {
			depthFirstSearch(graph.get(vertex).get(i));
		}
		
		finished.add(vertex);
		stack.push(vertex);
	}
	
	//Check for valid class name
	public Boolean classNameCheck(String s) {
		if(classHash.containsKey(s)) {
			return true;
		} else {
			return false;
		}
	}
	
	//Get Vertex integer
	public Integer getVertex(String className) {
		return classHash.get(className);
	}
	
		
	//Gets order from the stack and builds the string to be presented
	public String getRecompileOrder() {
		while(stack.isEmpty() == false) {
			orderedStr.append(revClassHash.get(stack.pop()) + " ");
			
		}
		
		returnedStr = orderedStr.toString();
		return returnedStr;
	}
	
	
}
