package assignment4;
/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * <Student1 Name>
 * <Student1 EID>
 * <Student1 5-digit Unique No.>
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Fall 2016
 */


import java.util.ArrayList;
import java.util.List;

/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */


public abstract class Critter {
	private static String myPackage;
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
	
	//private int x_coord;
	//private int y_coord;
	public int x_coord;
	public int y_coord;
	
	protected final void walk(int direction) {
		move(false,direction);
	}
	
	//stage 2: reuse walk code
	protected final void run(int direction) {
		move(true,direction);
	}
	
	//helper method for walk/run methods
	protected final void move(boolean type, int direction) {
		
		int magnitude = 1;
		if(type) {
			magnitude = 2;
		}
		
		if(type) {
			energy -= Params.run_energy_cost;
		} else {
			energy -= Params.walk_energy_cost;
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
				y_coord = Params.world_height;
			}
			break;
		case 2:
			y_coord -= magnitude;
			if(y_coord <= -1) {
				y_coord = Params.world_height;
			}
			break;
		case 3:
			x_coord -= magnitude;
			y_coord -= magnitude;
			if(x_coord <= -1) {
				x_coord = Params.world_width;
			}
			if(y_coord <= -1) {
				y_coord = Params.world_height;
			}
			break;
		case 4:
			x_coord -= magnitude;
			if(x_coord <= -1) {
				x_coord = Params.world_width;
			}
			break;
		case 5:
			x_coord -= magnitude;
			y_coord += magnitude;
			if(x_coord <= -1) {
				x_coord = Params.world_width;
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
			//invalid direction
			System.out.println("invalid direction, must be 0-7");
		}
	}
	
	//stage 2: look at project description
	protected final void reproduce(Critter offspring, int direction) {
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
	}

	
	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> result = new java.util.ArrayList<Critter>();
	
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
		// Complete this method.
		
		CritterWorld.population.clear();
		
	}
	
	public static void worldTimeStep() {
		// Complete this method.
		
		//System.out.println("doing worldtimestep");
		
		for(Critter c : CritterWorld.population) {
			c.doTimeStep();
		}
		
		//doEncounters()
		
		updateRestEnergy();
		
		//generateAlgae()
		
		updatePopulation();
		
	}
	
	//write for stage2
	protected static void doEncounters() {
		//check for all critters in the same location.
		//for every spot with more than one critter, call both of their fight methods.
		//if both are still alive, look at project description for what to do.
	}
	
	protected static void updatePopulation() {
		
		ArrayList<Critter> dead = new ArrayList<Critter>();
		
		for(int i=0;i<CritterWorld.population.size();i++) {
			
			if(CritterWorld.population.get(i).getEnergy() <= 0) {
				dead.add(CritterWorld.population.get(i));
			}
			
		}
		
		CritterWorld.population.removeAll(dead);
		
	}
	
	protected static void updateRestEnergy() {
		
		for(Critter c : CritterWorld.population) {
			
			int new_energy = c.getEnergy() - Params.rest_energy_cost;
			c.setEnergy(new_energy);
		}
	}
	
	public static void displayWorld() {
		// Complete this method.
		
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
					//print critter if it exists at (x,y), ie (i+1,j+1) (due to borders)
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
	
	//protected method to set position of critter in makeCritter(final) method and addCritter(testing) method
	protected void initializePosition(int x, int y) {
		x_coord = x;
		y_coord = y;
	}
	
	//protected method to set energy of critter in final and testing creation methods^^
	protected void setEnergy(int e) {
		energy = e;
	}

	//test mode only method to add critters to population and initialize them
	public static void addCritter(String critter_name) {
		// TODO Auto-generated method stub
		if(critter_name.equals("Craig")) {
			Craig c1 = new Craig();
			CritterWorld.population.add(c1);
			c1.initializePosition(getRandomInt(Params.world_width-1), getRandomInt(Params.world_height-1));
			c1.setEnergy(Params.start_energy);
		} else if(critter_name.equals("Algae")) {
			Algae a1 = new Algae();
			CritterWorld.population.add(a1);

			//testing algae's testcritter methods
			a1.setX_coord(getRandomInt(Params.world_width-1));
			a1.setY_coord(getRandomInt(Params.world_height-1));
			a1.setEnergy(Params.start_energy);
		}
		
		
	}
}
