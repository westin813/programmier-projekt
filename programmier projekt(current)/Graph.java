import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Graph {
//array containing exact postions of nodes [node id ][0] = double longitude [node id ][1] = double latitude
	//private double[][] exactPosition;
	private double[] exactPositionY;
	private double[] exactPositionX;
		
//determines how big the squares of the grid are supposed to be
	int gridSquareSize;
//array containing the edge ids of nodes in specific grid squares grid[longitude/gridSquareSize][latitude/gridSquareSize][0] = first node in square
	private int[][][] grid; // needs size adjustment
//an array which contains the offsets with which to enter the edge ids with in the Edges of nodes array
	private int[] nodesGridArrayOffset;
// an array which contains the offsets with which to enter the edge ids with in the Edges of nodes array 
	private int[] edgesOfNodesArrayOffset;
//an array that contains all of the edge ids associated with specific Nodes [Node id][0]=(first) edge id [Node id][arrayOffset]=(second) edge id
//private int[][] edgesOfNodes; // needs size adjusted
//an array that contains all information concering the edges of the graph [edge id][0]= source node [edge id][1]= target node [edge id][2]= edge weight
	private int[][] edges;
	//yeah that takes too much space
	private int[]sources;
	private int[]targets;
	private int[]weights;

//Adjency array for array size adjustment idea
	/*
	 * class Shelf{ private final int size; int[]contents; Shelf(int size){
	 * this.size = size; contents = new int[size]; }
	 * 
	 * } private ArrayList<Shelf> edgesOf = new ArrayList<Shelf>();
	 */

//am lazy so dont want to do array resizing stuff yet just get it working
	ArrayList<ArrayList<Integer>> edgesOfNodes;
	private int numOfNodes;

	void loadAndSortIntoGrid() {
		// reading the file
		// get the file
		try {
			String toy = "Mapfiles\\toy.fmi.txt";
			String realshit = "Mapfiles\\germany.fmi";
			String realshi2 = "C:\\Users\\westi\\Downloads\\MV.fmi";
			File file = new File(realshit);
			if (file != null) {
				// create a buffered reader instance to read the file
				BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

				Iterator<String> lines = bufferedReader.lines().iterator();
				// skip the first five lines
				for (int i = 0; i < 5; i++) {
					lines.next();
				}
				int numOfVerticies = Integer.parseInt(lines.next());
				int numOfEdges = Integer.parseInt(lines.next());
				// initialize arrays
				exactPositionX = new double[numOfVerticies];
				exactPositionY = new double[numOfVerticies];
				
				System.out.println(numOfVerticies+","+numOfEdges);
				//edges = new int[numOfEdges][3];
				sources = new int[numOfEdges];
				targets = new int[numOfEdges];
				//weights = new int[numOfEdges];
				/*
				edgesOfNodes = new ArrayList<ArrayList<Integer>>();
				for (int t = 0; t < numOfVerticies; t++) {
					edgesOfNodes.add(new ArrayList<Integer>());
				}
				 */
				int[] whiteSpaces = new int[4];
				// load verticies
				// invarianten lines != null, numofVertices >= 0
				for (int i = 0; i < numOfVerticies; i++) {
					String nums = lines.next();
					// check if numbers are at the same places else go through string letter for
					// letter
					if (!whiteSpacesAreTheSame(whiteSpaces, nums) || i == 0) {
						whiteSpaces = getWhiteSpaces(nums);
						//System.out.println("white spaces shifted");
					}
					// put numbers into exact position array
					double latitude = Double.parseDouble((String) nums.subSequence(whiteSpaces[1], whiteSpaces[2]));
					double longitude = Double.parseDouble((String) nums.subSequence(whiteSpaces[2], whiteSpaces[3]));

					exactPositionX[i] = latitude;
					exactPositionY[i] = longitude;
					//
					// System.out.println("saved: vert: " + i + " lat " + latitude + " long " +
					// longitude);
					// System.out.println("saved exact verts postions");
				}
				// clear old whitesspace postions
				whiteSpaces = new int[4];
				// load edges into edges array
				for (int i = 0; i < numOfEdges; i++) {
					String nums = lines.next();
					// check if numbers are at the same places else go through string letter for
					// letter

					if (!whiteSpacesAreTheSame(whiteSpaces, nums) || i == 0) {
						whiteSpaces = getWhiteSpaces(nums);
						/*
						 * System.out.println("white spaces shifted"); for(int j : whiteSpaces) {
						 * System.out.println(j); }
						 */
					}
					// put sourcenodeid targetnodeid and weight of edges into array

					int sourceNodeId = Integer.parseInt(((String) nums.subSequence(0, whiteSpaces[0])));
					int targetNodeId = Integer.parseInt((String) nums.subSequence(whiteSpaces[0] + 1, whiteSpaces[1]));
					int weight = Integer.parseInt((String) nums.subSequence(whiteSpaces[1] + 1, whiteSpaces[2]));

					sources[i] = sourceNodeId;
					targets[i] = targetNodeId;
					weights[i] = weight;
					/*
					 * System.out.println("edge id: " + i + " source: " + sourceNodeId + " target: "
					 * + targetNodeId + " weight: " + weight);
					 */
					// add edges to edgesOfNodes array
					//edgesOfNodes.get(sourceNodeId).add(i);
					//edgesOfNodes.get(targetNodeId).add(i);

				}
//print shit to check if correct
				boolean shouldprintstuff = false;
				if (shouldprintstuff) {
					for (ArrayList<Integer> n : edgesOfNodes) {
						System.out.println("\nnode " + edgesOfNodes.indexOf(n) + ":");
						for (int i : n) {
							System.out.print(+i + ",");
						}

					}
				}

				System.out.println("Loaded Map data");
			} else {
				System.out.println("map file not found");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	boolean whiteSpacesAreTheSame(int[] whiteSpaces, String s) {
		boolean result;

		for (int i = 0; i < 4; i++) {
			if (whiteSpaces[i] < s.length()) {
				if (s.charAt(whiteSpaces[i]) != ' ') {
					return false;
				}
			} else {
				return false;
			}

		}
		return true;

	}

	int[] getWhiteSpaces(String s) {
		int[] whitespaces = new int[4];
		int offset = 0;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == ' ') {
				whitespaces[offset] = i;
				offset++;
			}
		}

		return whitespaces;
	}
}
