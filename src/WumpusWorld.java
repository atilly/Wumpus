import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

public class WumpusWorld {
	HashMap<String, TreeSet<String>> allAdjacent;
	TreeSet<String> pits, smellLocations, breezeLocations;
	private String wumpusLocation, goldLocation;
	int size;

	public WumpusWorld(int s) {
		size = s;
		allAdjacent = new HashMap<String, TreeSet<String>>();
		pits = new TreeSet<String>();
		smellLocations = new TreeSet<String>();
		breezeLocations = new TreeSet<String>();
		initWorld();

		do {
			wumpusLocation = getRandomLocation();
		} while (pits.contains(wumpusLocation));

		do {
			goldLocation = getRandomLocation();
		} while (pits.contains(goldLocation)
				|| goldLocation.equals(wumpusLocation));
		initWarnings();
	}

	private void initWarnings() {
		for (String adjLoc : allAdjacent.get(wumpusLocation)) {
			smellLocations.add(adjLoc);
		}

		for (String pit : pits) {
			for (String adjLoc : allAdjacent.get(pit)) {
				breezeLocations.add(adjLoc);
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

	public String getSmellsListString() {
		String result = "";
		for (String s : smellLocations) {
			result += "s" + s + " ";
		}
		return result;
	}

	public String getBreezeListString() {
		String result = "";
		for (String s : breezeLocations) {
			result += "b" + s + " ";
		}
		return result;
	}

	public String getPitAndRoomString() {
		String result = "";
		for (String p : pits) {
			result += "(at pit" + p + " r" + p + ")\n";
		}
		return result;
	}

	public String getSmellAndRoomString() {
		String result = "";
		for (String p : smellLocations) {
			result += "(at s" + p + " r" + p + ")\n";
		}
		return result;
	}

	public String getBreezeAndRoomString() {
		String result = "";
		for (String p : breezeLocations) {
			result += "(at b" + p + " r" + p + ")\n";
		}
		return result;
	}

	public String getRoomsString() {
		TreeSet<String> sortedKeys = new TreeSet<String>(allAdjacent.keySet());
		String result = "";
		for (String s : sortedKeys) {
			result += "r" + s + " ";
		}
		return result;
	}

	public String getAdjacencyString() {
		TreeSet<String> sortedKeys = new TreeSet<String>(allAdjacent.keySet());
		String result = "";
		for (String s : sortedKeys) {

			Set<String> adjs = allAdjacent.get(s);
			for (String sq : adjs) {
				result += "(adjacent r" + s + " r" + sq + ")\n";
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

				for (int i = x - 1; i <= x + 1; i++) {
					for (int j = y - 1; j <= y + 1; j++) {

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
		if (x == 1 && y == 1) {
			return getRandomLocation();
		}
		return "" + x + y;
	}

	private int getRandomIndex() {
		return 1 + (int) (Math.random() * size);
	}

	public String getWumpusLocation() {
		return "l" + wumpusLocation;
	}

	public String getGoldLocation() {
		return "r" + goldLocation;
	}

	/* For testing */
	public void printStringRepresentation() {
		String[][] world = getStringRepresentation();

		for (int i = world.length - 1; i >= 0; i--) {
			for (int j = 0; j < world.length; j++) {
				System.out.print(world[i][j]);
			}
			System.out.println();
		}
	}

	/*
	 * For testing.
	 */
	public String[][] getStringRepresentation() {
		String[][] world = new String[size][size];

		for (int i = 1; i <= size; i++) {
			for (int j = 1; j <= size; j++) {
				world[i - 1][j - 1] = "";
			}
		}
		world[0][0] = "A";

		// TreeSet<String> pits, smellLocations, breezeLocations;
		// private String wumpusLocation, goldLocation;
		for (String p : pits) {
			int x = Character.getNumericValue(p.charAt(0));
			int y = Character.getNumericValue(p.charAt(1));
			world[x - 1][y - 1] += "P";
		}

		for (String s : smellLocations) {
			int x = Character.getNumericValue(s.charAt(0));
			int y = Character.getNumericValue(s.charAt(1));
			world[x - 1][y - 1] += "S";
		}

		for (String b : breezeLocations) {
			int x = Character.getNumericValue(b.charAt(0));
			int y = Character.getNumericValue(b.charAt(1));
			world[x - 1][y - 1] += "B";
		}

		int x = Character.getNumericValue(wumpusLocation.charAt(0));
		int y = Character.getNumericValue(wumpusLocation.charAt(1));
		world[x - 1][y - 1] += "W";

		x = Character.getNumericValue(goldLocation.charAt(0));
		y = Character.getNumericValue(goldLocation.charAt(1));
		world[x - 1][y - 1] += "G";



		int cellSize = 6;
		int diff;
		for (int i = 1; i <= size; i++) {
			for (int j = 1; j <= size; j++) {
				String s = world[i - 1][j - 1];
				if (s.equals("")) {
					diff = cellSize;
					s = "";
				} else {
					diff = cellSize - s.length();
				}
				for (int k = 0; k < diff - 1; k++) {
					s += "_";
				}
				s += "|";
				world[i - 1][j - 1] = s;
			}
		}

		return world;
	}
}
