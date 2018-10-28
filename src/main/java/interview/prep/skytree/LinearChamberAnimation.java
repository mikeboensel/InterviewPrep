package interview.prep.skytree;

import java.util.ArrayList;
import java.util.List;

/*
 * A collection of particles is contained in a linear chamber. They all have the same speed, but some are headed toward the right and others are headed toward the left. These particles can pass through each other without disturbing the motion of the particles, so all the particles will leave the chamber relatively quickly.

You will be given the initial conditions by a string 'init' containing at each position an 'L' for a leftward moving particle, an 'R' for a rightward moving particle, or a '.' for an empty location. 'init' shows all the positions in the chamber. Initially, no location in the chamber contains two particles passing through each other.

We would like an animation of the particles as they move. At each unit of time, we want a string showing occupied locations with an 'X' and unoccupied locations with a '.'. Create a method 'animate' that takes a positive integer 'speed' and a string 'init' giving the initial conditions. The speed is the number of positions each particle moves in one unit of time. The method will return an array of strings in which each successive element shows the occupied locations at each time step. The first element of the return should show the occupied locations at the initial instant (at time = 0) in the 'X', '.' format. The last element in the return should show the empty chamber at the first time that it becomes empty.

Again, imagine that the method you write will be called thousands of times for varying initial conditions with size ranging from 0 to 50, and also imagine the case when init is several hundred thousand locations in size (though with speed > size / 20 or so).  Try to handle both of these cases efficiently in your implementation.

Examples:

(Note that in the examples below, the double quotes should not be considered part of the input or output strings.)

0)  2, "..R...."

Returns:

  { "..X....",

    "....X..",

    "......X",

    "......." }


 */

public class LinearChamberAnimation {

	public static void main(String[] args) {
		for(String step: runFullAnimation(2, "..R...."))
			System.out.println(step);
	}

	public static List<String> runFullAnimation(int speed, String initialLocations) {
		
		List<String> output = new ArrayList<String>();
		
		Chamber chamber = new Chamber(initialLocations);
		
		while(chamber.isNotEmpty()) {
			output.add(chamber.print());
			chamber.takeTimeStep(speed);
		}
		
		//Final empty animation step
		output.add(chamber.print());
		return output;
	}


	public static class Chamber {
		int size;
		List<Boolean> leftMovingParticles;
		List<Boolean> rightMovingParticles;

		public Chamber(String initialLocations) {
			String[] locs = initialLocations.split("");
			this.size = locs.length;
			
			leftMovingParticles = new ArrayList<>(size);
			rightMovingParticles = new ArrayList<>(size);

			initList(leftMovingParticles, size);
			initList(rightMovingParticles, size);

			
			int i = 0;
			for (String loc : locs) {

				if (loc.equals("R"))
					rightMovingParticles.set(i, true);
				else if (loc.equals("L"))
					leftMovingParticles.set(i, true);
				else if (loc.equals("."))
					// We are ok with this, but take no action
					;
				else {
					System.out.println("Bad character input : " + loc);
				}

				i++;

			}
		}

		//TODO probably should have used an array instead
		private void initList(List<Boolean> l, int size) {
			for(int i =0; i<size; i++) {
				l.add(false);
			}
			
		}

		public boolean isNotEmpty() {
			return print().contains("X");
		}

		public void takeTimeStep(int speed) {
			//LEFT UPDATE
			
			for (int i = 0; i < leftMovingParticles.size(); i++) {
				if(leftMovingParticles.get(i)) {
					if(stillInChamber(i,-speed)) {
						leftMovingParticles.set(i-speed, true);
						
					}
					if(speed != 0 ) //So long as we aren't staying in place, we no longer have a particle there.
						leftMovingParticles.set(i, false);
				}
				
			}
			
			//RIGHT UPDATE
			
			for (int i = size-1; i > -1; i--) {
				if(rightMovingParticles.get(i)) {
					if(stillInChamber(i,speed)) {
						rightMovingParticles.set(i+speed, true);
						
					}
					if(speed != 0 ) //So long as we aren't staying in place, we no longer have a particle there.
						rightMovingParticles.set(i, false);				}
				
			}

		}

		private boolean stillInChamber(int i, int delta) {
			int newLoc = i + delta;
			
			return newLoc >=0 && newLoc <size;
		}

		public String print() {
			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < size; i++) {
				if (leftMovingParticles.get(i) || rightMovingParticles.get(i))
					sb.append("X");
				else
					sb.append(".");
			}

			return sb.toString();
		}
	}

}
