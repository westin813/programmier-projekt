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

	Grid(double[] exactlon, double[] exactlat, double minlon, double minlat, double maxlon, double maxlat) {
		this.minlon = minlon;
		this.minlat = minlat;
		this.maxlon = maxlon;
		this.maxlat = maxlat;
		this.exactlat = exactlat;
		this.exactlon = exactlon;
		numOfNodes = exactlon.length;
		boxes = new ArrayList[numOfRows][numOfColums];
		for (int i = 0; i < numOfRows; i++) {
			for (int j = 0; j < numOfColums; j++) {
				for (int n = 0; n < numOfNodes; n++) {
					double rowstart = i * (maxlon - minlon) / numOfRows;
					double columstart = i * (maxlon - minlon) / numOfColums;
					if (exactlon[n] <= columstart && exactlon[n] >= columstart + (maxlon - minlon) / numOfColums
							&& exactlat[n] <= rowstart && exactlat[n] >= rowstart + (maxlat - minlat) / numOfRows) {
						boxes[i][j].add(n);
					}
				}
			}

		}
	}

	class Box {
		int x, y;

		Box(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	Box findbox(double lat, double lon) {
		Box result = null;
		if (inbounds(lon, lat)) {
			for (int i = 0; i < numOfRows; i++) {
				for (int j = 0; j < numOfColums; j++) {
					double rowstart = i * (maxlon - minlon) / numOfRows;
					double columstart = i * (maxlon - minlon) / numOfColums;
					if (lon <= columstart && lon >= columstart + (maxlon - minlon) / numOfColums && lat <= rowstart
							&& lat >= rowstart + (maxlat - minlat) / numOfRows) {
						result = new Box(i, j);
					}

				}

			}
		}

		return result;

	}

	int findclosestnodeinarray(ArrayList box, double lon, double lat) {
		double smallestdist = Double.MAX_VALUE;
		int closestnode = 0;
		for (int n = 0; n < box.size(); n++) {
			double newdist = calcdist(n, lon, lat);
			if (newdist < smallestdist) {
				closestnode = n;
				smallestdist = newdist;
			}
		}
		return closestnode;
	}

	private double calcdist(Integer node, double lon, double lat) {
		// TODO Auto-generated method stub
		double bx = exactlon[node];
		double by = exactlat[node];
		double ax = lon;
		double ay = lat;
		return Math.sqrt((ax - bx) * (ax - bx) - (ay - by) * (ay - by)) * 1 / 360 * 2 * 3.14 * 6.371;
	}

	boolean inbounds(int x, int y) {
		return -1 < x && x < numOfColums && -1 < y && y < numOfRows;
	}

	boolean inbounds(double lat, double lon) {
		return minlon < lon && lon < maxlon && minlat < lat && lat < maxlat;
	}

	ArrayList<Box> neighbours(Box b) {
		int x, y, rounds = 0;

		ArrayList result = new ArrayList();
		boolean nonodesfound = true;
		while (nonodesfound) {
			rounds++;
			for (int i = 0; i < ((rounds * (rounds - 1)) / 2) * 8; i++) {
				y = (i % 2) + b.x;
				x = (1 - (i % 2)) * i + b.y;
				result.add(new Box(x, y));
				if (rounds > 1 && nonodesfound == true) {
					if (inbounds(x, y)) {
						nonodesfound = boxes[x][y].isEmpty();
					}
				}
			}
		}
		return result;

	}

	void findclosestnode(double lon, double lat) {
		Box box = findbox(lon,lat);
		if( box != null) {
			findclosestnodeinarray(boxes[box.x][box.y],lon,lat);
		}else {
			ArrayList<Integer> nodes = new ArrayList<Integer>();
			ArrayList<Box> neighbours = neighbours(findbox(lon,lat));
			findclosestnodeinarray(neighbours,lon,lat);
		}
		
	}

}
