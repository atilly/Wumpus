import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Generator {
	private final static String path = "Wumpusworld/";
	private final static String file = "wumpus_problem";
	WumpusWorld ww;
	static String whiteSpace = "		  ";

	public Generator(int size, int nbrGold, int nbrWumpus) {
		ww = new WumpusWorld(size, nbrGold, nbrWumpus);
		ww.printStringRepresentation();
	}

	public void print() {
		StringBuilder sb = new StringBuilder();
		createProblemString(sb);
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(path + file));
			bw.write(sb.toString());
			bw.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private void createProblemString(StringBuilder sb) {
		appendHeader(sb);
		appendObjects(sb, ww);
		sb.append("\n");
		appendInit(sb, ww);
		sb.append("\n");
		appendGoal(sb, ww);
		sb.append(")\n");
		sb.append(")");
	}

	private static void appendHeader(StringBuilder sb) {
		sb.append("(define (problem wumpusworld)\n");
		sb.append("(:domain wumpusworld)\n");
	}

	private static void appendObjects(StringBuilder sb, WumpusWorld ww) {
		sb.append("(:objects a1 - arrow\n");
		sb.append(whiteSpace + ww.getRoomsString());
		sb.append(" - room\n");
		sb.append(whiteSpace + "p1 - player\n");
		sb.append(whiteSpace + ww.getGoldListString());
		sb.append(")\n");
	}

	private static void appendInit(StringBuilder sb, WumpusWorld ww) {
		sb.append("(:init\n");
		sb.append("(at p1 r11)\n");
		sb.append("(has a1)\n");
		sb.append(ww.getWumpusString());
		sb.append(ww.getGoldString());
		sb.append(ww.getPitString());
		sb.append(ww.getCanShootString());
		sb.append(ww.getAdjacencyString());
		sb.append(")\n");
	}

	private static void appendGoal(StringBuilder sb, WumpusWorld ww) {
		sb.append("(:goal\n");
		sb.append(whiteSpace + "(and\n");
		sb.append(whiteSpace + whiteSpace +"(at p1 r11)\n");
		sb.append(whiteSpace + whiteSpace +"("
				+ ww.getGoldGoalString() + ")\n");
		sb.append(whiteSpace + ")\n");
	}
}
