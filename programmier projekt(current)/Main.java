
public class Main {
public static void main(String args[]) {
	
	System.out.println("started");
	Graph2 g = new Graph2();
	g.loadAndSortIntoGrid("C:\\Users\\westi\\OneDrive\\Desktop\\germany.fmi");
	Dijkstra2 d = new Dijkstra2();
	d.doit(2,g.end.length,g.offset,g.adjacencyArray);
}
}
