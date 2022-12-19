import java.util.ArrayList;

public class Grid {
	int numOfRows;
	int numOfColums;
	int numOfNodes;
	double minlon; 
	double minlat; 
	double maxlon; 
	double maxlat;
	double[] exactlat;
	double[] exactlon;
	ArrayList[][] boxes;
	Grid(double[] exactlon,double[] exactlat,double minlon, double minlat, double maxlon, double maxlat){
		this.minlon = minlon;
		this.minlat = minlat;
		this.maxlon = maxlon;
		this.maxlat = maxlat;
		this.exactlat = exactlat;
		this.exactlon = exactlon;
		numOfNodes = exactlon.length;
		boxes =  new ArrayList[numOfRows][numOfColums];
		for(int i = 0 ; i < numOfRows; i++) {
			for(int j = 0; j < numOfColums; j++) {
				for(int n=0;n < numOfNodes ;n++) {
					double rowstart = i * (maxlon-minlon)/numOfRows;
					double columstart = i * (maxlon-minlon)/numOfColums;
					if(exactlon[n]<= columstart && exactlon[n]>=columstart+(maxlon-minlon)/numOfColums && exactlat[n]<= rowstart && exactlat[n]>=rowstart+(maxlat-minlat)/numOfRows) {
						boxes[i][j].add(n);
					}
				}
			}
		
			
		}
	}
	int find(double lat,double lon){
		for(int i = 0 ; i < numOfRows; i++) {
			for(int j = 0; j < numOfColums; j++) {		
					double rowstart = i * (maxlon-minlon)/numOfRows;
					double columstart = i * (maxlon-minlon)/numOfColums;
					if(lon<= columstart && lon>=columstart+(maxlon-minlon)/numOfColums && lat<= rowstart && lat>=rowstart+(maxlat-minlat)/numOfRows) {
						double smallestdist = Double.MAX_VALUE;
						int closestnode;
						for(int n = 0; n < boxes[i][j].size();n++) {
							double newdist = calcdist(n,lon,lat);
							if (newdist < smallestdist) {
								closestnode = n;
								smallestdist = newdist;
							}
						}
					}
				
			}
		
			
		}
		return numOfColums;
		
	}
	private double calcdist(Integer node, double lon, double lat) {
		// TODO Auto-generated method stub
		double bx =	exactlon[node];
		double by = exactlat[node];
		double ax = lon;
		double ay = lat;
		return Math.sqrt((ax-bx)*(ax-bx)-(ay-by)*(ay-by)) * 1/360 * 2 * 3.14 * 6.371;
	}
	ArrayList<Integer> neighbours(){
		
		ArrayList result = new ArrayList();
		
		result.addAll()
		return ;
		
	}
	class point{
		
	}

}
