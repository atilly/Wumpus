public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int size = 4, nbrGold = 4, nbrWumpus = 1;
		Generator generator = new Generator(size,nbrGold,nbrWumpus);
		generator.print();

	}

}
