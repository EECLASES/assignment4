package assignment4;


/*
 * This critter has two randomized attributes: hungry and excited.
 * These attributes are changed depending on the critter's energy level.
 * Each combination of these attributes triggers a different response
 * in the timeStep() and in the fight() methods. What makes this critter
 * unique is that if it is hungry and excited, it increases its energy
 * spontaneously before deciding to fight.
 */
public class NumanCritter2 extends Critter {

	boolean hungry;
	boolean excited;
	
	public NumanCritter2() {
		if(Critter.getRandomInt(2) == 0) {
			hungry = false;
		} else {
			hungry = true;
		}
		
		if(Critter.getRandomInt(2) == 0) {
			excited = false;
		} else {
			excited = true;
		}
	}
	
	@Override
	public String toString() { return "H"; }
		
	@Override
	public void doTimeStep() {
		
		
		if(this.getEnergy() > 100) {
			hungry = false;
		}
		
		if(this.getEnergy() > 150) {
			excited = true;
		}
		
		if(hungry && excited) {
			run(0);
		} else if(excited) {
			NumanCritter2 child = new NumanCritter2();
			reproduce(child,0);
		} else if(hungry) {
			this.setEnergy(0);
		}
	}

	@Override
	public boolean fight(String oponent) {
		
		if(hungry && excited) {
			this.setEnergy(this.getEnergy() + 5);
			return true;
		} else if(excited) {
			return true;
		} else if(hungry){
			walk(Critter.getRandomInt(8));
			return false;
		} else {
			return false;
		}
		
	}
}
