import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Generator {
	private final static String path = "Wumpusworld/";
	private final static String file = "problem2";
	WumpusWorld wsm;

	public Generator(int size) {
		wsm = new WumpusWorld(size);
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
		appendObjects(sb, wsm);
		appendInit(sb, wsm);
		appendGoal(sb);
		sb.append(")\n");
		sb.append(")");
	}

	private static void appendHeader(StringBuilder sb) {
		sb.append("(define (problem wumpusworld1)\n");
		sb.append("(:domain wumpusworld)\n");
	}

	private static void appendObjects(StringBuilder sb, WumpusWorld wsm) {
		String whiteSpace = "		  ";
		sb.append("(:objects a0 - arrow\n");
		sb.append(whiteSpace + "g0 - gold\n");
		sb.append(whiteSpace + wsm.getLocationsString());
		sb.append(" - location\n");
		sb.append(whiteSpace + "p0 - player\n");
		sb.append(whiteSpace + "w0 - wumpus\n");
		sb.append(")\n");
	}

	private static void appendInit(StringBuilder sb, WumpusWorld wsm) {
		sb.append("(:init\n");
		sb.append("(at p0 l11)\n");
		sb.append("(at w0 " + wsm.getWumpusLocation() + ")\n");
		sb.append("(at g0 " + wsm.getGoldLocation() + ")\n");
		sb.append("(have a0)\n");
		sb.append(wsm.getAdjacencyString());
		sb.append(")\n");
	}

	private static void appendGoal(StringBuilder sb) {
		sb.append("(:goal\n");
		sb.append("(and\n");
		sb.append("(at p0 l11)\n");
		sb.append("(have g0)\n");
		sb.append(")\n");
	}

}
