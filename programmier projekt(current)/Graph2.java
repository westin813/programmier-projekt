import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;



public class Graph2 {
	int[] offset;// [node id] = offset for that node in adjacencyArray
	ArrayList adjacencyArray;//target node followed by weight and array foreach node starts at offset
	int[][] edges;
	ArrayList<Shelf> Shelves = new ArrayList<Shelf>();
	int[] end;
	void loadAndSortIntoGrid() {
		// reading the file
		// get the file
		try {
			String toy = "Mapfiles\\toy.fmi.txt";
			String realshit = "Mapfiles\\germany.fmi";
			String realshi2 = "C:\\Users\\westi\\Downloads\\MV.fmi";
			
			File file = new File(toy);
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
				
				offset = new int[numOfVerticies];
				end = new int[numOfVerticies];
				edges = new int[numOfEdges][2];

				System.out.println(numOfVerticies + "," + numOfEdges);

				int[] whiteSpaces = new int[4];
				// load verticies
				// invarianten lines != null, numofVertices >= 0
				for (int i = 0; i < numOfVerticies; i++) {
					String nums = lines.next();
					// check if numbers are at the same places else go through string letter for
					// letter
					if (!whiteSpacesAreTheSame(whiteSpaces, nums) || i == 0) {
						whiteSpaces = getWhiteSpaces(nums);
						// System.out.println("white spaces shifted");
					}
					// put numbers into exact position array
					double latitude = Double.parseDouble((String) nums.subSequence(whiteSpaces[1], whiteSpaces[2]));
					double longitude = Double.parseDouble((String) nums.subSequence(whiteSpaces[2], whiteSpaces[3]));

					
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
					}
					// put sourcenodeid targetnodeid and weight of edges into array

					int sourceNodeId = Integer.parseInt(((String) nums.subSequence(0, whiteSpaces[0])));
					int targetNodeId = Integer.parseInt((String) nums.subSequence(whiteSpaces[0] + 1, whiteSpaces[1]));
					int weight = Integer.parseInt((String) nums.subSequence(whiteSpaces[1] + 1, whiteSpaces[2]));
					
					addIntoShelves(sourceNodeId,i,numOfVerticies);
					edges[i][0] = targetNodeId;
					edges[i][1] = weight; 
					
				}
				putintoArray(numOfVerticies);
				System.out.println("done");
				//printresults();
				
			
				//
				
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
	
	
	class Shelf{
		int[]nums;
		Shelf(int size){
			nums = new int[size];
		}
	}
	void addIntoShelves(int node,int edgeid,int numOfVertices) {
		end[node]++;
		if(end[node] > Shelves.size()) {
			Shelves.add(new Shelf(numOfVertices));
		}
		Shelves.get(end[node]-1).nums[node] = edgeid;
	}
	void printresults(){
		for(int i = 0; i< end.length ;i++ ) {
			System.out.println("node"+ i);
			for(int j = 0; j < end[i]; j++) {
				System.out.println("->"+ edges[Shelves.get(j).nums[i]][0] + " weight:" + edges[Shelves.get(j).nums[i]][1] );
			}
		}
	}
	void putintoArray(int numOfVerticies) {
		ArrayList<Integer> temp = new ArrayList<Integer>();
		for(int i = 0; i< numOfVerticies ;i++ ) {
			for(int j = 0; j < end[i]; j++) {
				offset[i] = temp.size();
				temp.add(edges[Shelves.get(j).nums[i]][0]);
				temp.add(edges[Shelves.get(j).nums[i]][1]);
				 
			}
		}
		adjacencyArray = temp;
	}
	
	
	

}
