import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class Generator {

	private final static String path = "Wumpusworld/";
	private final static String file = "adjacency";
	int[][] world;
	
	public Generator(int size){
		world = new int[size][size];
	}
	
	public void print(){
		
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(path + file));
			for(int i = 0; i<world.length; i++){
				for(int j = 0; j<world.length; j++){
					String line = makeAdjacentLine(i,j,i+1,j);
					bw.write(line);
					line = makeAdjacentLine(i+1,j,i,j);
					bw.write(line);
					line = makeAdjacentLine(i,j,i,j+1);
					bw.write(line);
					line = makeAdjacentLine(i,j+1,i,j);
					bw.write(line);
					line = makeAdjacentLine(i,j,i-1,j);
					bw.write(line);
					line = makeAdjacentLine(i-1,j,i,j);
					bw.write(line);
					line = makeAdjacentLine(i,j-1,i,j);
					bw.write(line);
					line = makeAdjacentLine(i,j,i,j-1);
					bw.write(line);
				}
			}
			bw.close();
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	
	public String makeAdjacentLine(int i, int j, int k, int l){
		if(i<0 || j<0 || i>world.length-1 || j> world.length-1 || k<0 || l<0 || k>world.length-1 || l> world.length-1){
			return "";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("(adjacent l");
		sb.append(i);
		sb.append(j);
		sb.append(" l");
		sb.append(k);
		sb.append(l);
		sb.append(")");
		sb.append(System.lineSeparator());
		return sb.toString();
	}
	
	
}
