import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

public class WumpusWorld {
	HashMap<String, TreeSet<String>> allAdjacent;
	TreeSet<String> pits, stenchRooms, breezeRooms, canShoot;
	private String wumpusRoom, goldRoom;
	int size;

	public WumpusWorld(int s) {
		size = s;
		allAdjacent = new HashMap<String, TreeSet<String>>();
		pits = new TreeSet<String>();
		stenchRooms = new TreeSet<String>();
		breezeRooms = new TreeSet<String>();
		canShoot = new TreeSet<String>();
		initWorld();

		do {
			wumpusRoom = getRandomRoom();
		} while (pits.contains(wumpusRoom));
		initCanShootRooms();

		do {
			goldRoom = getRandomRoom();
		} while (pits.contains(goldRoom));
		initWarnings();
	}

	/* The rooms with pits. */
	public String getPitString() {
		String result = "";
		for (String p : pits) {
			result += "(pit" + " r" + p + ")\n";
		}
		return result;
	}

	/* The rooms with stench, can probably be removed. */
	public String getStenchString() {
		String result = "";
		for (String p : stenchRooms) {
			result += "(stench" + " r" + p + ")\n";
		}
		return result;
	}

	/* The rooms with breezes, can probably be removed. */
	public String getBreezeString() {
		String result = "";
		for (String p : breezeRooms) {
			result += "(breeze" + " r" + p + ")\n";
		}
		return result;
	}

	/* The rooms from which you can shoot the Wumpus */
	public String getCanShootString() {
		String result = "";
		for (String c : canShoot) {
			result += "(canShoot r" + c + " r" + wumpusRoom + ")\n";
		}
		return result;

	}

	/* All rooms */
	public String getRoomsString() {
		TreeSet<String> sortedKeys = new TreeSet<String>(allAdjacent.keySet());
		String result = "";
		for (String s : sortedKeys) {
			result += "r" + s + " ";
		}
		return result;
	}

	/* All the adjacency relations */
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
				System.out.print(world[j][i]);
			}
			System.out.println();
		}
	}

	/* Initiates the rooms from which it is possible to shoot the Wumpus. */
	private void initCanShootRooms() {
		int x = getDigit(wumpusRoom, 0);
		int y = getDigit(wumpusRoom, 1);
		for (int i = 1; i <= size; i++) {
			canShoot.add("" + x + i);
			canShoot.add("" + i + y);
		}
	}

	/* Initiates the rooms with stench and breeze, can probably be removed. */
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

	/* Calculates the adjacency relations and generates pits */
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

	/* Helper method */
	private void addAdjacent(String square, String adjSquare) {
		TreeSet<String> adjacentSquares = allAdjacent.get(square);
		if (adjacentSquares == null) {
			adjacentSquares = new TreeSet<String>();
			allAdjacent.put(square, adjacentSquares);
		}
		adjacentSquares.add(adjSquare);
	}

	/* Checks if the two rooms are adjacent */
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

	/* For testing */
	private String[][] getStringRepresentation() {
		String[][] world = new String[size][size];

		for (int i = 1; i <= size; i++) {
			for (int j = 1; j <= size; j++) {
				world[i - 1][j - 1] = "";
			}
		}
		world[0][0] = "A";

		addToWorldString(world, pits, "P");
		addToWorldString(world, stenchRooms, "S");
		addToWorldString(world, breezeRooms, "B");
		addToWorldString(world, canShoot, "C");

		int x = getDigit(wumpusRoom, 0);
		int y = getDigit(wumpusRoom, 1);
		world[x - 1][y - 1] += "W";

		x = getDigit(goldRoom, 0);
		y = getDigit(goldRoom, 1);
		world[x - 1][y - 1] += "G";

		int cellSize = 7;
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

	public static void addToWorldString(String[][] world, TreeSet<String> set,
			String string) {
		for (String s : set) {
			int x = getDigit(s, 0);
			int y = getDigit(s, 1);
			world[x - 1][y - 1] += string;
		}
	}

	/* Helper method */
	private static int getDigit(String s, int digit) {
		return Character.getNumericValue(s.charAt(digit));
	}
}
