import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Generator {
	private final static String path = "Wumpusworld/";
	private final static String file = "wumpus_problem";
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
		sb.append("\n");
		appendSensors(sb, wsm);
		sb.append("\n");
		appendDangers(sb, wsm);
		sb.append("\n");
		appendInit(sb, wsm);
		sb.append("\n");
		appendGoal(sb);
		sb.append(")\n");
		sb.append(")");
	}

	private static void appendHeader(StringBuilder sb) {
		sb.append("(define (problem wumpusworld)\n");
		sb.append("(:domain wumpusworld)\n");
	}

	private static void appendObjects(StringBuilder sb, WumpusWorld wsm) {
		sb.append("(:objects a0 - arrow\n");
		sb.append(whiteSpace + wsm.getRoomsString());
		sb.append(" - room\n");
		sb.append(whiteSpace + "p0 - player\n");
		sb.append(")\n");
	}

	private static void appendSensors(StringBuilder sb, WumpusWorld wsm2) {
		sb.append(";;(:sensors stench breeze\n");
		sb.append(";;)\n");
	}

	private static void appendDangers(StringBuilder sb, WumpusWorld wsm2) {
		sb.append(";;(:dangers wumpus pit\n");
		sb.append(";;)\n");
	}

	private static void appendInit(StringBuilder sb, WumpusWorld wsm) {
		sb.append("(:init\n");
		sb.append("(at p0 r11)\n");
		sb.append("(isAlive)\n");
		sb.append("(has a0)\n");
		sb.append("(wumpusAt " + wsm.getWumpusString() + ")\n");
		sb.append("(goldAt " + wsm.getGoldRoom() + ")\n");
		sb.append(wsm.getStenchString());
		sb.append(wsm.getBreezeString());
		sb.append(wsm.getPitString());
		sb.append(wsm.getCanShootString());
		sb.append(wsm.getAdjacencyString());
		sb.append(")\n");
	}

	private static void appendGoal(StringBuilder sb) {
		sb.append("(:goal\n");
		sb.append(whiteSpace + "(and\n");
		sb.append(whiteSpace + whiteSpace + "(and\n");
		sb.append(whiteSpace + whiteSpace + whiteSpace + "(at p0 r11)\n");
		sb.append(whiteSpace + whiteSpace + whiteSpace + "(hasGold)\n");
		sb.append(whiteSpace + whiteSpace + ")\n");
		sb.append(whiteSpace + whiteSpace + "(isAlive)\n");
		sb.append(whiteSpace + ")\n");
	}
}
