package assignment4;
/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * <Miguel Angel Garza Robledo>
 * <Mag8238>
 * <16345>
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Fall 2016
 */


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */


public abstract class Critter {
	private static String myPackage;
	private boolean hasMoved = false;
	private boolean inFight = false;
	//private	static List<Critter> population = new java.util.ArrayList<Critter>();
	//private static List<Critter> babies = new java.util.ArrayList<Critter>();

	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}
	
	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }
	
	private int energy = 0;
	protected int getEnergy() { return energy; }
	
	private int x_coord;
	private int y_coord;
	
	/**method for critter to walk in given direction.
	 * critter only walks if it hasn't moved already in this
	 * time step and if it is moving to an unoccupied spot during
	 * a fight. energy is subtracted regardless
	 * @param direction
	 */
	protected final void walk(int direction) {
		if(!hasMoved) {
			move(false,direction);
			hasMoved = true;
		}
		energy -= Params.walk_energy_cost;
		
		if(inFight) {
			for(Critter c : CritterWorld.population) {
				if(x_coord == c.x_coord && y_coord == c.y_coord) {
					move(false,(direction+4)%8);
				}
			}
		}
	}

	/**method for critter to run in given direction.
	 * critter only runs if it hasn't moved already in this
	 * time step and if it is moving to an unoccupied spot during
	 * a fight. energy is subtracted regardless
	 * @param direction
	 */
	protected final void run(int direction) {
		if(!hasMoved) {
			move(true,direction);
			hasMoved = true;
		}
		energy -= Params.run_energy_cost;
		
		if(inFight) {
			for(Critter c : CritterWorld.population) {
				if(x_coord == c.x_coord && y_coord == c.y_coord) {
					move(true,(direction+4)%8);
				}
			}
		}
	}

	/**helper method to reuse code for walk and run methods.
	 * @param type
	 * @param direction
	 */
	protected final void move(boolean type, int direction) {
		
		int magnitude = 1;
		if(type) {
			magnitude = 2;
		}
		
		
		switch(direction) {
		case 0:
			x_coord += magnitude;
			if(x_coord >= Params.world_width) {
				x_coord = 0;
			}
			break;
		case 1:
			x_coord += magnitude;
			y_coord -= magnitude;
			if(x_coord >= Params.world_width) {
				x_coord = 0;
			}
			if(y_coord <= -1) {
				y_coord = Params.world_height-1;
			}
			break;
		case 2:
			y_coord -= magnitude;
			if(y_coord <= -1) {
				y_coord = Params.world_height-1;
			}
			break;
		case 3:
			x_coord -= magnitude;
			y_coord -= magnitude;
			if(x_coord <= -1) {
				x_coord = Params.world_width-1;
			}
			if(y_coord <= -1) {
				y_coord = Params.world_height-1;
			}
			break;
		case 4:
			x_coord -= magnitude;
			if(x_coord <= -1) {
				x_coord = Params.world_width-1;
			}
			break;
		case 5:
			x_coord -= magnitude;
			y_coord += magnitude;
			if(x_coord <= -1) {
				x_coord = Params.world_width-1;
			}
			if(y_coord >= Params.world_height) {
				y_coord = 0;
			}
			break;
		case 6: 
			y_coord += magnitude;
			if(y_coord >= Params.world_height) {
				y_coord = 0;
			}
			break;
		case 7:
			x_coord += magnitude;
			y_coord += magnitude;
			if(x_coord >= Params.world_width) {
				x_coord = 0;
			}
			if(y_coord >= Params.world_height) {
				y_coord = 0;
			}
			break;
		default:
			System.out.println("invalid direction, must be 0-7");
		}
		
		
	}

	/**reproduce method that takes an allocated Critter and a direction
	 * and adds it to the population and initializes its fields if the parent
	 * has the required amount of energy to reproduce
	 * @param offspring
	 * @param direction
	 */
	protected final void reproduce(Critter offspring, int direction) {
		if (this.getEnergy() > Params.min_reproduce_energy) {
		CritterWorld.babies.add(offspring);
		offspring.initializePosition(getX(), getY());
		offspring.walk(direction);
		
		}
		else {
		return;
		}
		
		offspring.setEnergy(this.getEnergy()/2);
		this.setEnergy(Math.round(this.getEnergy()/2));
	}

	public abstract void doTimeStep();
	public abstract boolean fight(String oponent);
	
	/**
	 * create and initialize a Critter subclass.
	 * critter_class_name must be the unqualified name of a concrete subclass of Critter, if not,
	 * an InvalidCritterException must be thrown.
	 * (Java weirdness: Exception throwing does not work properly if the parameter has lower-case instead of
	 * upper. For example, if craig is supplied instead of Craig, an error is thrown instead of
	 * an Exception.)
	 * @param critter_class_name
	 * @throws InvalidCritterException
	 */

		

	public static void makeCritter(String critter_class_name) throws InvalidCritterException {		
		String qualified = (myPackage + "." + critter_class_name);
		
		try {
			Class critter_type = Class.forName(qualified);
			Critter a = (Critter) critter_type.newInstance();
			CritterWorld.population.add(a);
			a.initializePosition(Critter.getRandomInt(Params.world_width), Critter.getRandomInt(Params.world_height));
			a.setEnergy(Params.start_energy);
			
		}
		catch(ClassNotFoundException e){
			
			throw new InvalidCritterException(qualified);
			
		}
		catch(IllegalAccessException f) {
			throw new InvalidCritterException(qualified);
		}
		catch(InstantiationException g) {
			throw new InvalidCritterException(qualified);
		}
		
	}

	
	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> result = new java.util.ArrayList<Critter>();
		
		String qualified = myPackage + "." + critter_class_name;
		
		try {
			Class critter_type = Class.forName(qualified);
			for(Critter crit : CritterWorld.population) {
				if(critter_type.isInstance(crit)) {
					result.add(crit);
				}
			}
			
		} catch (ClassNotFoundException c) {
			throw new InvalidCritterException(qualified);
		}
	
		return result;
	}
	
	/**
	 * Prints out how many Critters of each type there are on the board.
	 * @param critters List of Critters.
	 */
	public static void runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		System.out.println();		
	}
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure that the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}
		
		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}
		
		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}
		
		protected int getX_coord() {
			return super.x_coord;
		}
		
		protected int getY_coord() {
			return super.y_coord;
		}
		

		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return CritterWorld.population;
		}
		
		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population 
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return CritterWorld.babies;
		}
	}

	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
		
		CritterWorld.population.clear();
		
	}
	
	/**
	 * static method to perform a single worldtimestep
	 */
	public static void worldTimeStep() {
				
		for(Critter c : CritterWorld.population) {
			c.doTimeStep();
		}
		
		doEncounters();
		
		updateRestEnergy();
		
		generateAlgae();
		
		updatePopulation();
		
	}
	
	/**
	 * helper method for worldtimestep to generate the 
	 * appropriate amount of new algae per timestep
	 */
	protected static void generateAlgae() {
		
		for(int i=0;i<Params.refresh_algae_count;i++) {
			Algae a = new Algae();
			a.setEnergy(Params.start_energy);
			a.setX_coord(getRandomInt(Params.world_width));
			a.setY_coord(getRandomInt(Params.world_height));
			CritterWorld.population.add(a);
		}
	}
	

	/**
	 * helper method for worldtimestep to handle all encounters.
	 * uses a hashmap to keep track of all critters occupying the same spot
	 * and resolves fights for each conflict in a pairwise manner.
	 */
	protected static void doEncounters() {

		HashMap<Critter,ArrayList<Critter>> same = new HashMap<Critter,ArrayList<Critter>>();
		
		for(Critter A : CritterWorld.population) {
			
			same.put(A, new ArrayList<Critter>());
			
			
			for(Critter B : CritterWorld.population) {
				
				if(A == B) {
					continue;
				}
				
				if(A.getX() == B.getX() && A.getY() == B.getY()) {
					
					if(same.get(A).contains(B)) {
						break;
					} else if(same.get(B) != null) {
							
						if(same.get(B).contains(A)) {
							break;
						}
					}		
					same.get(A).add(B);
					
				}
			}
		}
		
		for(Critter c : CritterWorld.population) {
			
			int n = same.get(c).size();
			
			if(n == 0) {
				continue;
			}
			Critter current_winner = same.get(c).get(0);
			
			for(int i=1;i<n;i++) {
				current_winner = doFight(current_winner,same.get(c).get(i));
			}
			doFight(c,current_winner);
		}
		
	}
	
	/**handles a single pairwise fight between two critters according
	 * to project specifications. 
	 * @param a
	 * @param b
	 * @return
	 */
	protected static Critter doFight(Critter a, Critter b) {
		
		a.inFight = true;
		b.inFight = true;
		
		boolean a_status = a.fight(b.toString());
		boolean b_status = b.fight(a.toString());
		int a_roll = 0;
		int b_roll = 0;
		
		if(a_status == true && b_status == false) {
			// a wins
			if(a.getX() != b.getX() || a.getY() != b.getY()) {
				return a;
			}
			
			a_roll = 10;

		} else if(a_status == false && b_status == true) {
			// b wins
			if(a.getX() != b.getX() || a.getY() != b.getY()) {
				return b;
			}
			
			b_roll = 10;
			
		} else if(a_status == true && b_status == true) {
			// dice roll
			a_roll = getRandomInt(a.getEnergy());
			b_roll = getRandomInt(b.getEnergy());
		}
		
		if(a_roll >= b_roll) {
			a.setEnergy(a.getEnergy() + b.getEnergy()/2);
			b.setEnergy(0);
			return a;
		} else {
			b.setEnergy(b.getEnergy() + a.getEnergy()/2);
			a.setEnergy(0);
			return b;
		}
	}
	
	/**helper method for worldtimestep to remove all dead critters
	 * and add all baby critters to the population
	 * 
	 */
	protected static void updatePopulation() {
		
		ArrayList<Critter> dead = new ArrayList<Critter>();
		
		for(int i=0;i<CritterWorld.population.size();i++) {
			
			if(CritterWorld.population.get(i).getEnergy() <= 0) {
				dead.add(CritterWorld.population.get(i));
			}
			
		}
		
		CritterWorld.population.removeAll(dead);
		CritterWorld.population.addAll(CritterWorld.babies);
		CritterWorld.babies.clear();
		
		for(Critter c : CritterWorld.population) {
			c.hasMoved = false;
			c.inFight = false;
		}
		
	}
	
	/**
	 * helper method for worldtimestep to subtract the 
	 * appropriate energy from each critter in the population
	 */
	protected static void updateRestEnergy() {
		
		for(Critter c : CritterWorld.population) {
			
			int new_energy = c.getEnergy() - Params.rest_energy_cost;
			c.setEnergy(new_energy);
		}
	}
	
	/**method invoked during "show" command to display 2-D grid representing
	 * the critter world
	 * 
	 */
	public static void displayWorld() {
		
		for(int i=0;i<Params.world_height+2;i++) {
			
			for(int j=0;j<Params.world_width+2;j++) {
				
				if(i == 0 && j == 0) {
					System.out.print("+");
					continue;
				} else if(i == 0 && j == Params.world_width+1) {
					System.out.print("+");
					continue;
				}  else if(i == 0){
					System.out.print("-");
					continue;
				} else if(i == Params.world_height+1 && j == 0) {
					System.out.print("+");
					continue;
				} else if(i == Params.world_height+1 && j == Params.world_width+1) {
					System.out.print("+");
					continue;
				} else if(i == Params.world_height+1){
					System.out.print("-");
					continue;
				} else if(j == 0){
					System.out.print("|");
					continue;
				} else if(j == Params.world_width+1){
					System.out.print("|");
					continue;
				} else {
					boolean occupied = false;
					for(Critter crit : CritterWorld.population) {
						if(crit.x_coord == j-1 & crit.y_coord == i-1) {
							System.out.print(crit.toString());
							occupied = true;
							break;
						}
					}
					if(occupied == false) {
						System.out.print(" ");
					}
				}
			}
			
			System.out.println();
			
		}
	}
	
	/**method used to initialize private position fields of critter
	 * @param x
	 * @param y
	 */
	protected void initializePosition(int x, int y) {
		x_coord = x;
		y_coord = y;
	}
	/**method used to get private x_coord field of critter
	 * @return
	 */
	protected int getX() {
		return this.x_coord;
	}
	/**method used to get private y_coord field of critter
	 * @return
	 */
	protected int getY() {
		return this.y_coord;
	}

	/**method used to set private energy field of critter
	 * @param e
	 */
	protected void setEnergy(int e) {
		energy = e;
	}

}
