import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

public class WumpusWorld {
	HashMap<String, TreeSet<String>> allAdjacent;
	TreeSet<String> pits, stenchRooms, breezeRooms;
	private String wumpusRoom, goldRoom;
	int size;

	public WumpusWorld(int s) {
		size = s;
		allAdjacent = new HashMap<String, TreeSet<String>>();
		pits = new TreeSet<String>();
		stenchRooms = new TreeSet<String>();
		breezeRooms = new TreeSet<String>();
		initWorld();

		do {
			wumpusRoom = getRandomRoom();
		} while (pits.contains(wumpusRoom));

		do {
			goldRoom = getRandomRoom();
		} while (pits.contains(goldRoom) || goldRoom.equals(wumpusRoom));
		initWarnings();
	}

	private void initWarnings() {
		for (String adjLoc : allAdjacent.get(wumpusRoom)) {
			stenchRooms.add(adjLoc);
		}

		for (String pit : pits) {
			for (String adjLoc : allAdjacent.get(pit)) {
				breezeRooms.add(adjLoc);
			}
		}
	}

	public String getPitString() {
		String result = "";
		for (String p : pits) {
			result += "(pit" + " r" + p + ")\n";
		}
		return result;
	}

	public String getStenchString() {
		String result = "";
		for (String p : stenchRooms) {
			result += "(stench" + " r" + p + ")\n";
		}
		return result;
	}

	public String getBreezeString() {
		String result = "";
		for (String p : breezeRooms) {
			result += "(breeze" + " r" + p + ")\n";
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

	private String getRandomRoom() {
		int x = getRandomIndex();
		int y = getRandomIndex();
		if (x == 1 && y == 1) {
			return getRandomRoom();
		}
		return "" + x + y;
	}

	private int getRandomIndex() {
		return 1 + (int) (Math.random() * size);
	}

	public String getWumpusString() {
		return "r" + wumpusRoom;
	}

	public String getGoldRoom() {
		return "r" + goldRoom;
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

		// TreeSet<String> pits, stenchRooms, breezeRooms;
		// private String wumpusRoom, goldRoom;
		for (String p : pits) {
			int x = Character.getNumericValue(p.charAt(0));
			int y = Character.getNumericValue(p.charAt(1));
			world[x - 1][y - 1] += "P";
		}

		for (String s : stenchRooms) {
			int x = Character.getNumericValue(s.charAt(0));
			int y = Character.getNumericValue(s.charAt(1));
			world[x - 1][y - 1] += "S";
		}

		for (String b : breezeRooms) {
			int x = Character.getNumericValue(b.charAt(0));
			int y = Character.getNumericValue(b.charAt(1));
			world[x - 1][y - 1] += "B";
		}

		int x = Character.getNumericValue(wumpusRoom.charAt(0));
		int y = Character.getNumericValue(wumpusRoom.charAt(1));
		world[x - 1][y - 1] += "W";

		x = Character.getNumericValue(goldRoom.charAt(0));
		y = Character.getNumericValue(goldRoom.charAt(1));
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
