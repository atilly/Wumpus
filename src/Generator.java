import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Generator {
	private final static String path = "Wumpusworld/";
	private final static String file = "problem2";
	WumpusWorld wsm;
	static String whiteSpace = "		  ";

	public Generator(int size) {
		wsm = new WumpusWorld(size);
		wsm.printStringRepresentation();
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
		appendSensors(sb, wsm);
		appendDangers(sb, wsm);
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
		sb.append("(:objects a0 - arrow\n");
		sb.append(whiteSpace + "g0 - gold\n");
		sb.append(whiteSpace + wsm.getRoomsString());
		sb.append(" - room\n");
		sb.append(whiteSpace + "p0 - player\n");

		sb.append(")\n");
	}

	private static void appendSensors(StringBuilder sb, WumpusWorld wsm2) {
		sb.append("(:sensors ");
		sb.append(wsm2.getSmellsListString());
		sb.append(" - smell\n");
		sb.append(whiteSpace + wsm2.getBreezeListString());
		sb.append(" - breeze\n");
		sb.append(")\n");
		// smell breeze - sensor
	}

	private static void appendDangers(StringBuilder sb, WumpusWorld wsm2) {
		sb.append("(:dangers w0 - wumpus\n");
		sb.append(whiteSpace + wsm2.getPitsListString());
		sb.append(" - pit\n");
		sb.append(")\n");
	}

	private static void appendInit(StringBuilder sb, WumpusWorld wsm) {
		sb.append("(:init\n");
		sb.append("(at p0 r11)\n");
		sb.append("(at g0 " + wsm.getGoldLocation() + ")\n");
		sb.append(wsm.getSmellAndRoomString());
		sb.append(wsm.getBreezeAndRoomString());
		sb.append("(at w0 " + wsm.getWumpusLocation() + ")\n");
		sb.append(wsm.getPitAndRoomString());
		sb.append("(have a0)\n");
		sb.append(wsm.getAdjacencyString());
		sb.append(")\n");
	}

	private static void appendGoal(StringBuilder sb) {
		sb.append("(:goal\n");
		sb.append("(and\n");
		sb.append("(at p0 r11)\n");
		sb.append("(have g0)\n");
		sb.append(")\n");
	}
}
