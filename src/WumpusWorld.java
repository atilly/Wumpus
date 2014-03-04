import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

public class WumpusWorld {
	HashMap<String, TreeSet<String>> allAdjacent;
	TreeSet<String> pits, stenchLocations, breezeLocations;
	private String wumpusLocation, goldLocation;
	int size;

	public WumpusWorld(int s) {
		size = s;
		allAdjacent = new HashMap<String, TreeSet<String>>();
		pits = new TreeSet<String>();
		stenchLocations = new TreeSet<String>();
		breezeLocations = new TreeSet<String>();
		initWorld();
		wumpusLocation = getRandomLocation();
		goldLocation = getRandomLocation();

		initWarnings();
	}

	private void initWarnings() {
		TreeSet<String> adj = allAdjacent.get(wumpusLocation);

		if (adj != null) {
			for (String adjLoc : adj) {
				stenchLocations.add(adjLoc);
			}
		} else {
			/* Märkligt att pit inte har några rutor jämte sig väl? */
			System.out.println(wumpusLocation + " inga adj?? ");
		}

		for (String pit : pits) {
			TreeSet<String> adj2 = allAdjacent.get(pit);
			if (adj2 != null) {
				for (String adjLoc : adj2) {
					breezeLocations.add(adjLoc);
				}
			} else {
				/* Märkligt att pit inte har några rutor jämte sig väl? */
				System.out.println(pit + " inga adj?? ");
			}
		}
	}

	/* For listing of all the pit locations */
	public String getPitsListString() {
		String result = "";
		for (String s : pits) {
			result += "pit" + s + " ";
		}
		return result;
	}

	public String getPitAndLocationString() {
		String result = "";
		for (String p : pits) {
			result += "(at pit" + p + " l" + p + ")\n";
		}
		return result;
	}

	public String getLocationsString() {
		TreeSet<String> sortedKeys = new TreeSet<String>(allAdjacent.keySet());
		String result = "";
		for (String s : sortedKeys) {
			result += "l" + s + " ";
		}
		return result;
	}

	public String getAdjacencyString() {
		TreeSet<String> sortedKeys = new TreeSet<String>(allAdjacent.keySet());
		String result = "";
		for (String s : sortedKeys) {

			Set<String> adjs = allAdjacent.get(s);
			for (String sq : adjs) {
				result += "(adjacent l" + s + " l" + sq + ")\n";
			}
		}
		return result;
	}

	private void initWorld() {
		String square, adjSquare;

		for (int x = 1; x <= size; x++) {
			for (int y = 1; y <= size; y++) {
				square = "" + x + y;

				if (Math.random() < 0.2) {
					pits.add("" + x + y);
				}

				for (int i = x - 1; i <= x+1; i++) {
					for (int j = y - 1; j <= y+1; j++) {

						if (isAdjacent(x, y, i, j)) {
							adjSquare = "" + i + j;
							if (!square.equals(adjSquare)) {
								addAdjacent(square, adjSquare);
								addAdjacent(adjSquare, square);
							}
						}

					}
				}

			}
		}

		pits.remove("11");
	}

	private void addAdjacent(String square, String adjSquare) {
		TreeSet<String> adjacentSquares = allAdjacent.get(square);
		if (adjacentSquares == null) {
			adjacentSquares = new TreeSet<String>();
			allAdjacent.put(square, adjacentSquares);
		}
		adjacentSquares.add(adjSquare);
	}

	private boolean isAdjacent(int x, int y, int i, int j) {
		if (insideWorld(i, j) && insideWorld(x, y)) {
			if (Math.abs(x - i) == 1 && Math.abs(y - j) == 0
					|| Math.abs(y - j) == 1 && Math.abs(x - i) == 0) {
				return true;
			}
		}
		return false;
	}

	private boolean insideWorld(int i, int j) {
		return i > 0 && j > 0 && i <= size && j <= size;
	}

	private String getRandomLocation() {
		int x = getRandomIndex();
		int y = getRandomIndex();
		return "" + x + y;
	}

	private int getRandomIndex() {
		return 1 + (int) (Math.random() * size);
	}

	public String getWumpusLocation() {
		return "l" + wumpusLocation;
	}

	public String getGoldLocation() {
		return "l" + goldLocation;
	}

}
