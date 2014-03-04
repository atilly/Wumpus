import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

public class WumpusSquareManager {
	HashMap<String, TreeSet<String>> allAdjacent;
	int size;

	public WumpusSquareManager(int s) {
		size = s;
		allAdjacent = new HashMap<String, TreeSet<String>>();
		initAdjacentSquares();
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

	private void initAdjacentSquares() {
		String square, adjSquare;

		for (int x = 0; x <= size; x++) {
			for (int y = 0; y <= size; y++) {
				square = x + "" + y;

				for (int i = 0; i <= 3; i++) {
					for (int j = 0; j <= 3; j++) {

						if (isAdjacent(x, y, i, j)) {
							adjSquare = i + "" + j;
							if (!square.equals(adjSquare)) {
								addAdjacent(square, adjSquare);
								addAdjacent(adjSquare, square);
							}
						}

					}
				}
			}
		}
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
}
