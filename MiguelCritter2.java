package assignment4;

public class MiguelCritter2 extends Critter {

	boolean lovable;
	boolean angry;
	
	public MiguelCritter2() {
		lovable = true;
		if(CritterWorld.population.size() > 15) {
			angry = true;
		}
		
		
		
	}
	@Override
	public String toString() { return "Z"; }
	
	@Override
	public void doTimeStep() {

		
		if (angry) {
			run(Critter.getRandomInt(8));
		}
		else if (lovable && !angry){
			MiguelCritter1 child = new MiguelCritter1();
			reproduce(child,Critter.getRandomInt(8));
			lovable = false;
		}
		else {
		lovable = true;
		}
	}
public boolean fight(String oponent) {
		
		if(angry) {
			return true;
		} else {
			return false;
		}
		
	}

}