import java.util.ArrayList;

public class Dijkstra2 {
	void doit(int root, int numOfVerticies, int[] offset, ArrayList<Integer> adjarray) {
		ArrayList<Integer> MinHeap = new ArrayList<Integer>();
		// https://www.geeksforgeeks.org/dijkstras-algorithm-for-adjacency-list-representation-greedy-algo-8/
		int[] tdistance = new int[numOfVerticies];
		// initialize
		for (int i = 0; i < numOfVerticies; i++) {
			tdistance[i] = Integer.MAX_VALUE;
			MinHeap.add(i);
		}
		
		tdistance[root] = 0;
		while (!MinHeap.isEmpty()) {
			int smallestweight = Integer.MAX_VALUE;
			int u = 0;
			int uindex = 0;
			for (int i = 0; i < MinHeap.size(); i++) {
				if (tdistance[MinHeap.get(i)] < smallestweight) {
					smallestweight = tdistance[MinHeap.get(i)];
					uindex = i;
					u = MinHeap.get(i);
					
				}
			}
			MinHeap.remove(uindex);
			// check neighboors of u
			
			int lengthofinfo = 0;
			if (offset.length - 1 == u) {
				lengthofinfo = adjarray.size() - offset[u];
			} else {
				lengthofinfo = offset[u + 1] - offset[u];
			}

			for (int i = 0; i < lengthofinfo; i = i + 2) {
				int v = adjarray.get(offset[u] + i);
				int weight = adjarray.get(offset[u] + i + 1);
				
				// update vertex values
				
				if (MinHeap.contains(v)) {
					if (weight+tdistance[u] < tdistance[v]+tdistance[u]) {
						tdistance[v] = weight+tdistance[u];
						
					}
				}
			}

		}
		//print results
		for(int i = 0; i < tdistance.length; i++) {
			System.out.println("vertex: "+i+" weight: "+tdistance[i] );
		}
	}

}
