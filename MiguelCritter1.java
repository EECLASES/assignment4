package assignment4;

public class MiguelCritter1 extends Critter {

	boolean depressed;
	int[] stress ;
	int total = 0;
	
	
	public MiguelCritter1() {
		stress = new int[10];
		for (int i = 1; i< 10; i++) {
			total = total + stress[i];
		}
		if (total > 50) {
			depressed = true;
		}
		
	}
	@Override
	public String toString() { return "M"; }
	
	@Override
	public void doTimeStep() {

		if(depressed) {
			walk(Critter.getRandomInt(8));
		} 
		else {
			run(Critter.getRandomInt(8));
		
			MiguelCritter1 child = new MiguelCritter1();
			reproduce(child,Critter.getRandomInt(8));
		}
		
	}
public boolean fight(String oponent) {
		
		if(depressed) {
			return false;
		} else {
			return true;
		}
		
	}

}
