import java.util.ArrayList;

public class Dijkstra {
	void calc(int Startnode,int numOfNodes,ArrayList<Integer> adjarray,int[] offset){
		boolean shouldstop =  false;
		//1
		int currentnode = Startnode;
		int nextnode; 
		int[] tDistance = new int[numOfNodes];
		boolean[] visited = new boolean[numOfNodes];
		ArrayList<Integer> unvisited = new ArrayList<Integer>();
		for(int i = 0; i < numOfNodes;i++) {
			unvisited.add(i);
		}
		//2
		tDistance[currentnode] = 0;
		for(int i : tDistance) {
			tDistance[i] = Integer.MAX_VALUE;
		}
		nextnode = adjarray.get(offset[currentnode]);
		//3
		while(!shouldstop) {
		int lengthofinfo;
		if(offset.length-1 == currentnode) {
			lengthofinfo = adjarray.size()-offset[currentnode];
		}else {
			lengthofinfo = offset[currentnode+1]-offset[currentnode];
		}
		
		for(int i = 0; i <lengthofinfo; i= i +2) {
			int neighbourId = adjarray.get(offset[currentnode]+i);
			int neighbourWeight = adjarray.get(offset[currentnode]+i+1);
			int updatedtdistance = tDistance[currentnode] + neighbourWeight;
			if(tDistance[neighbourId] > updatedtdistance) {
				tDistance[neighbourId] = updatedtdistance;
			}
			if(tDistance[nextnode] > tDistance[neighbourId] ) {
				nextnode = neighbourId;
			}
			System.out.println("node:"+neighbourId+" tdistance "+ tDistance[i]);
		}
		//4
		visited[currentnode] = true;
		for(int i = 0; i < unvisited.size() ; i++) {
			if(unvisited.get(i) == currentnode) {
				unvisited.remove(i);
			}
		}
		//5
		
		int smallest = Integer.MAX_VALUE;
		for(int i = 0; i < unvisited.size() ; i++) {
			if(smallest>unvisited.get(i)) {
				smallest = unvisited.get(i);
			}
		}
		if(visited[nextnode] || smallest == Integer.MAX_VALUE ) {
			shouldstop = true;
		}
		
		//6
		
		currentnode = nextnode;
		
		
		}
		
		//print output to check
		for(int i = 0; i < tDistance.length ; i++) {
			System.out.println("node:"+i+" tdistance "+ tDistance[i]);
		}
	}

}
