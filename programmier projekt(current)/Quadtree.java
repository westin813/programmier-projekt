import java.util.ArrayList;



public class Quadtree {
	double[] bounds;
	Box[] boxes;
	ArrayList<Quadtree> children = null;
	ArrayList<Integer> nodes;
	int index = 0;
	
	Quadtree(double[] bounds,ArrayList<Integer> nodes, double[] exactlon,double[] exactlat,boolean first){//dont for get to initiate nodes with number counting up till size before using quadtree constructor, as well as defining bounds of graph
		if(first) {
			for(int i = 0; i < nodes.size() ; i++) {
				nodes.add(i);
			}
		}
		this.bounds = bounds;
		
		//divide shit into 4 boxes
		//for first starter quadtree steal values of mionts with minlat minlon maxlat maxlon from Graph while loading
		double halflon = (bounds[0] - bounds[2])/2;
		double halflat = (bounds[1]-bounds[3])/2;
		Box[] b = {
			new Box(bounds[0],halflat,halflon,bounds[4]),
			new Box(halflon,halflat,bounds[3],bounds[4]),
			new Box(bounds[0],bounds[1],halflon,halflat),
			new Box(halflon,bounds[1],bounds[3],halflat)	
		} ;
		//todo: put nodes into boxes 
		for(Box box : b) {
			for(int i =0 ; i < nodes.size();i++ ) {
				if(inbounds(box.bounds,exactlon[nodes.get(i)],exactlat[nodes.get(i)])) {
					nodes.remove(i);
					box.NodesInBox.add(nodes.get(i));
				}
			}
		}
		boxes = b;
		
		for(int i = 0; i < 4;i++) {
			if(thereismorethanonenodeinbox(boxes[i])) {
				children.add(new Quadtree(boxes[i].bounds,boxes[i].NodesInBox,exactlon,exactlat,false));
			}
		}
		
	}
	private boolean thereismorethanonenodeinbox(Box box) {
		//fuck
		return box.NodesInBox.size() > 1;
	}
	class Box {
		ArrayList<Integer> NodesInBox;
		double[] bounds;//[0][1] minlonlat [2][3]maxlonlat
		Box(double minlon,double minlat,double maxlon,double maxlat){
			double[] b = {minlon,minlat,maxlon,maxlat};
			bounds = b;
			NodesInBox = new ArrayList<Integer>();
		}
		
	}
	void find(double lon, double lat,double[] exactlon, double[] exactlat) {
		for(int i = 0; i < 4;i++) {
			if(inbounds(boxes[i].bounds, lon, lat)) {
				if(boxisempty(boxes[i])) {
					//getneighbours
					//get current boxes
					
					int closestNode = 0;
					double smallestdistance = Double.MAX_EXPONENT;
					for(Box b : getneighbours(boxes[i],boxes[i].bounds)) {//cycle through all neighbouring boxes with one node in them and find the closest node to the coordinates
						if(calcdist(b.NodesInBox.get(0),lon,lat,exactlon,exactlat) < smallestdistance) {
							smallestdistance = calcdist(b.NodesInBox.get(0),lon,lat,exactlon,exactlat);
							closestNode = b.NodesInBox.get(0);
						}
					}
				}
				if(thereismorethanonenodeinbox(boxes[i])) {
					for(Quadtree q :children) {
						q.find(lon, lat,exactlon,exactlat);
					}
				}else {
					// return the node in said box
				}
			}
		}
	}
	private double calcdist(Integer node, double lon, double lat,double[] exactlon, double[] exactlat) {
		// TODO Auto-generated method stub
		double bx =	exactlon[node];
		double by = exactlat[node];
		double ax = lon;
		double ay = lat;
		return Math.sqrt((ax-bx)*(ax-bx)-(ay-by)*(ay-by)) * 1/360 * 2 * 3.14 * 6.371;
	}
	private boolean boxisempty(Box box) {
		// TODO Auto-generated method stub
		return box.NodesInBox.isEmpty();
	}
	private boolean inbounds(double[] bounds,double lon,double lat) {
		// TODO Auto-generated method stub
		return lon>=bounds[0] && lat >= bounds[1] && lon <= bounds[2] && lat <= bounds[3];
	}
	ArrayList<Box> getneighbours(Box b,double[] bounds) {
		//get current boxes
		//bounds should stay the same during recursion...
		ArrayList<Box>Neighbours = new ArrayList<Box>(); 
		for(int i = 0; i < 4;i++) {
			if(boxes[i] != b && oneboundryisthesame(boxes[i].bounds,bounds) && !boxisempty(boxes[i])) {
				if(thereismorethanonenodeinbox(boxes[i])) {
					for(Quadtree q: children) {
						if(q.index == i) {
							Neighbours.addAll(q.getneighbours(b,bounds));
						}
					}
				}else {
					Neighbours.add(boxes[i]);
				}
			}
			//check if box has more than one node
			//if it doesnt add to comparison list
			//if it does check if its children have one corner in common with box 
		}
		return Neighbours;
	}
	ArrayList<Integer> getneighboursnodes(Box b,double[] bounds) {
		//get current boxes
		//bounds should stay the same during recursion...
		ArrayList<Integer>Neighbours = new ArrayList<Integer>(); 
		for(int i = 0; i < 4;i++) {
			if(boxes[i] != b && oneboundryisthesame(boxes[i].bounds,bounds) && !boxisempty(boxes[i])) {
				if(thereismorethanonenodeinbox(boxes[i])) {
					for(Quadtree q: children) {
						if(q.index == i) {
							Neighbours.addAll(q.getneighboursnodes(b,bounds));
						}
					}
				}else {
					Neighbours.add(boxes[i].NodesInBox.get(0));
				}
			}
			//check if box has more than one node
			//if it doesnt add to comparison list
			//if it does check if its children have one corner in common with box 
		}
		return Neighbours;
	}
	private boolean oneboundryisthesame(double[] a, double[] b) {
		// TODO Auto-generated method stub
		return a[0] == b[0] && a[1] == b[1] || a[2] == b[2] && a[1] == b[1] ||  a[2] == b[2] && a[3] == b[3] ||  a[0] == b[0] && a[3] == b[3];
	}
	

}
