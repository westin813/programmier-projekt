
public class Main {
public static void main(String args[]) {
	
	System.out.println("started");
	Graph2 g = new Graph2();
	g.loadAndSortIntoGrid();
	Dijkstra d = new Dijkstra();
	d.calc(0, g.end.length, g.adjacencyArray, g.offset);
}
}
