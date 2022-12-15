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
			//
		}
		System.out.println("done adding");
		tdistance[root] = 0;
		//implement spiral thingy here
		while (!MinHeap.isEmpty()) {
			int smallestweight = Integer.MAX_VALUE;
			int u = 0;
			int uindex = 0;
			for (int i = 0; i < MinHeap.size(); i++) {
				if (tdistance[MinHeap.get(i)] < smallestweight) {
					smallestweight = tdistance[MinHeap.get(i)];
					uindex = i;
					u = MinHeap.get(i);
					//System.out.println("found"+u);
				}
			}
			MinHeap.remove(uindex);
			//System.out.println("test"+MinHeap.size());
			// check neighboors of u
			
			int lengthofinfo = 0;
			if (offset.length - 1 == u) {
				lengthofinfo = adjarray.size() - offset[u];
			} else {
				lengthofinfo = offset[u + 1] - offset[u];
				//System.out.println("did stuff"+offset[u]);
			}

			for (int i = 0; i < lengthofinfo; i = i + 2) {
				int v = adjarray.get(offset[u] + i);
				int weightOfv = adjarray.get(offset[u] + i + 1) + tdistance[u];
				
				// update vertex values
				
				if (MinHeap.contains(v)) {
					//System.out.println(v+":"+tdistance[v]);
					if (weightOfv + tdistance[u] < tdistance[v] && tdistance[u] != Integer.MAX_VALUE) {
						tdistance[v] = weightOfv;
						//System.out.println("update:"+tdistance[v]);
					}
				}
			}

		}
		System.out.println("done with dijkstra");
		//print results
		for(int i = 0; i < tdistance.length; i++) {
			System.out.println("vertex: "+i+" weight: "+tdistance[i] );
		}
	}
	

}
