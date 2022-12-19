import java.util.ArrayList;

public class Main {
public static void main(String args[]) {
	
	System.out.println("started");
	//Graph2 g = new Graph2();
	//g.loadAndSortIntoGrid("C:\\Users\\westi\\OneDrive\\Desktop\\germany.fmi");
	//Dijkstra2 d = new Dijkstra2();
	//d.doit(2,g.end.length,g.offset,g.adjacencyArray);
	double[] bounds = {0.0,0.0,5.0,5.0};
	double[] bounds2 = {2.5, 2.5, 5, 5};
	ArrayList<Integer> nodes = new ArrayList<Integer>();
	double[] exactlon = {1.5,2.5,4,3.5,4.5};
	double[] exactlat = {3.0,2.0,4.5,0.5,2.0};
	Quadtree test = new Quadtree(bounds,nodes,exactlon,exactlat,true,5,0);
	ArrayList<Integer> neighbours = test.getneighboursnodes(bounds2);
	//System.out.println("found neighbours of node");
	//neighbours.forEach((x)-> System.out.println("|"+x));
	test.find(4.8, 0.5, exactlon, exactlat);
}
}
