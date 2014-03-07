import java.util.HashMap;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

public class WumpusWorld {
	private HashMap<String, TreeSet<String>> allAdjacent;
	private TreeSet<String> pits, goldRooms, wumpusRooms, stenchRooms,
			breezeRooms;
	private HashMap<String, TreeSet<String>> canShoot;
	private int size, nbrGold, nbrWumpus, goldIndex = 1;

	public WumpusWorld(int size, int nbrGold, int nbrWumpus) {
		this.size = size;
		this.nbrGold = nbrGold;
		this.nbrWumpus = nbrWumpus;
		allAdjacent = new HashMap<String, TreeSet<String>>();
		pits = new TreeSet<String>();
		canShoot = new HashMap<String, TreeSet<String>>();
		goldRooms = new TreeSet<String>();
		wumpusRooms = new TreeSet<String>();
		breezeRooms = new TreeSet<String>();
		stenchRooms = new TreeSet<String>();
		
		initWorld();
		initWumpusRooms();
		initGoldRooms();
		initCanShootRooms();
		initWarnings();
	}

	/* The 'pit rxx' String */
	public String getPitString() {
		String result = "";
		for (String p : pits) {
			result += "(pitAt" + " r" + p + ")\n";
		}
		return result;
	}

	/* List of all rooms */
	public String getRoomsString() {
		TreeSet<String> sortedKeys = new TreeSet<String>(allAdjacent.keySet());
		String result = "";
		for (String s : sortedKeys) {
			result += "r" + s + " ";
		}
		return result;
	}

	/* List of all the gold */
	public String getGoldListString() {
		String result = "";
		for (int i = 0; i < nbrGold; i++) {
			result += "g" + (i + 1) + " ";
		}
		result += "- gold\n";
		return result;
	}

	/* The rooms from which you can shoot the Wumpus */
	public String getCanShootString() {
		String result = "";

		for (String wumpusRoom : canShoot.keySet()) {
			for (String room : canShoot.get(wumpusRoom)) {
				result += "(canShoot r" + room + " r" + wumpusRoom + ")\n";
			}
		}
		return result;
	}

	public String getStenchString() {
		String result = "";
		for (String p : stenchRooms) {
			result += "(stenchAt r" + p + ")\n";
		}
		return result;
	}

	/* The rooms with breezes, can probably be removed. */
	public String getBreezeString() {
		String result = "";
		for (String p : breezeRooms) {
			result += "(breezeAt r" + p + ")\n";
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

	/* The 'wumpusAt rxx' String */
	public String getWumpusString() {
		String result = "";
		for (String s : wumpusRooms) {
			result += "(wumpusAt " + "r" + s + ")\n";
		}
		return result;
	}

	/* All the 'at gx rxx' etc. */
	public String getGoldString() {
		String result = "";
		int g = 1;
		for (String s : goldRooms) {
			result += "(at " + "g" + g + " r" + s + ")\n";
			g++;
		}
		return result;
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

	/* Initiates the rooms with stench and breeze, can probably be removed. */
	private void initWarnings() {
		for (String wumpusRoom : wumpusRooms) {
			for (String adjLoc : allAdjacent.get(wumpusRoom)) {
				stenchRooms.add(adjLoc);
			}
		}
		for (String pit : pits) {
			for (String adjLoc : allAdjacent.get(pit)) {
				breezeRooms.add(adjLoc);
			}
		}
	}

	private void initGoldRooms() {
		String room;
		for (int i = 0; i < nbrGold; i++) {
			do {
				room = getRandomRoom();
			} while (pits.contains(room));
			goldRooms.add(room);
		}
	}

	private void initWumpusRooms() {
		String room;
		for (int i = 0; i < nbrWumpus; i++) {
			do {
				room = getRandomRoom();
			} while (pits.contains(room));
			wumpusRooms.add(room);
		}
	}

	/* Initiates the rooms from which it is possible to shoot the Wumpus. */
	private void initCanShootRooms() {
		for (String w : wumpusRooms) {
			int x = getDigit(w, 0);
			int y = getDigit(w, 1);
			TreeSet<String> set = new TreeSet<String>();
			for (int i = 1; i <= size; i++) {
				set.add("" + x + i);
				set.add("" + i + y);
			}
			canShoot.put(w, set);
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
		addToWorldString(world, goldRooms, "G");
		addToWorldString(world, wumpusRooms, "W");
		addToWorldString(world, breezeRooms, "B");
		addToWorldString(world, stenchRooms, "S");

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

	/* Black magic */
	public String getGoldGoalString() {
		if (nbrGold == 1) {
			return "has g1";
		}
		StringBuilder result = new StringBuilder("and (has g" + goldIndex
				+ ") (has g" + (goldIndex + 1) + ")");
		goldIndex += 2;
		int i;
		for (i = 2; i < nbrGold - 1; i += 2) {
			result.insert(0, "and (");
			result.append(") (and (has g" + goldIndex + ") (has g"
					+ (goldIndex + 1) + "))");
			goldIndex += 2;
		}
		if (i == nbrGold - 1) {
			result.insert(0, "and (");
			result.append(") (has g" + (goldIndex) + ")");
		}
		return result.toString();
	}

	/* under construction typ */
	public void pushToStack(Stack<String> stack, int lvlsLeft) {
		if (lvlsLeft == 2) {
			stack.push("(and (has g) (has g))");
		} else {
			stack.push(")) ");
			pushToStack(stack, lvlsLeft / 2);
			pushToStack(stack, lvlsLeft / 2);
			stack.push("and (");
		}
	}
}
