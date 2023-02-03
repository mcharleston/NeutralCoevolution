package niches;

import java.util.ArrayList;

import utilities.RobRandom;

/**
 * Created by Edward on 11/01/2016.
 */
public class NicheGenerator {
	public static ArrayList<Niche> occupiedNiches;
	public static ArrayList<Niche> emptyNiches;
	public static ArrayList<Niche> moveNiches;
	public static ArrayList<Niche> allNiches;
	public static double cascadeTimeStep = 0.01;

	public NicheGenerator() {
		occupiedNiches = new ArrayList<Niche>();
		emptyNiches = new ArrayList<Niche>();
		moveNiches = new ArrayList<Niche>();
		allNiches = new ArrayList<Niche>();
	}

	public void printList(String output) {
		if (output.equalsIgnoreCase("occupied")) {
			System.out.println("Occupied niches:");
			System.out.println("x\ty\tt\tSID");
			for (int i = 0; i < allNiches.size(); i++) {
				if (allNiches.get(i).getOccupant() != null) {
					System.out.println(allNiches.get(i).getxCoord() + "\t" + allNiches.get(i).getyCoord() + "\t"
							+ allNiches.get(i).getGlobalTime() + "\t" + allNiches.get(i).getOccupant().getID());
				}
			}
		} else if (output.equalsIgnoreCase("empty")) {
			System.out.println("Empty niches:");
			System.out.println("x\ty\tt");
			for (int i = 0; i < allNiches.size(); i++) {

				if (allNiches.get(i).getOccupant() == null) {
					System.out.println(allNiches.get(i).getxCoord() + "\t" + allNiches.get(i).getyCoord() + "\t"
							+ allNiches.get(i).getGlobalTime());
				}
			}
		} else {
			throw new IllegalArgumentException();
		}
	}
	public Species returnAncestor(Species ancestor){
		return ancestor;
	}

	public void printTree(Species ancestor) {
		System.out.println("Tree:" + ancestor);
	}

	public double getNicheDistance(Niche subject, Niche tempNiche) {
		double nextx = subject.getxCoord(); // x coordinate of the start
		double nexty = subject.getyCoord(); // y coordinate of the start
		double tempx = tempNiche.getxCoord(); // x coordinate of the tempNiche
		double tempy = tempNiche.getyCoord(); // y coordinate of the tempNiche
		double xDif = tempx - nextx; // Difference in x values
		double yDif = tempy - nexty; // Difference in y values
		return Math.hypot(xDif, yDif); // Euclidean distance using trig stuff
	}

	public boolean chainSpawnSpecies(int optimumDistance) {
		double shortestDistance = optimumDistance * 10000;
		Niche shortestNicheEmpty = null;
		Niche shortestNicheOccupied = null;
		// for (int i = 0; i<emptyNiches.size();i++){
		// Niche subject = emptyNiches.get(i);
		// spawnSpecies(subject, optimumDistance);
		// }
		for (int i = 0; i < emptyNiches.size(); i++) {
			Niche subject = emptyNiches.get(i);
			for (int j = 0; j < occupiedNiches.size(); j++) {
				Niche origin = occupiedNiches.get(j);
				double distance = getNicheDistance(subject, origin);
				if (distance < optimumDistance && distance < shortestDistance) {
					shortestDistance = distance;
					shortestNicheEmpty = subject;
					shortestNicheOccupied = origin;
				}
			}
		}
		if (shortestDistance < optimumDistance) {
			shortestNicheOccupied.getOccupant().spawn(shortestNicheOccupied, shortestNicheEmpty);
			Species novelSpecies = shortestNicheEmpty.getOccupant();
//			novelSpecies.setAge(shortestNicheOccupied.getOccupant().getAge()+cascadeTimeStep);
			emptyNiches.remove(shortestNicheEmpty);
			occupiedNiches.add(shortestNicheEmpty);
//			System.out.println("Spawning");
			return true;
		} else {
			return false;
		}
	}

	/**
	 * removes a niche from the list of all niches, then checks to see if it has
	 * an occupant and removes it from the specific lists. Removes the species
	 * at the same time.
	 * 
	 * @param chanceAction
	 *
	 */
	public Species removeNicheAtRandom(double chanceAction, Species origin) {
		double randomDouble = RobRandom.randDouble();
		if (randomDouble < chanceAction) {
			int randInt = RobRandom.randInt(allNiches.size());
			Niche delNiche = allNiches.get(randInt);
			if (delNiche.getOccupant() != null) {
				origin = delNiche.getOccupant().removeSpecies(origin);
				if (delNiche.getOccupant().checkRemoved()) {
					occupiedNiches.remove(delNiche);
					allNiches.remove(delNiche);
				}
			} else if (delNiche.getOccupant() == null) {
				emptyNiches.remove(delNiche);
				allNiches.remove(delNiche);
			}

		}
		return origin;
	}

	public Species largeRemovalOfNiches(Species ancestor){
		double xUpperLimit = Niche.getMeanx() + 1*Niche.getSdx();
		double xLowerLimit = Niche.getMeanx() - 1*Niche.getSdx();
		double yUpperLimit = Niche.getMeany() + 1*Niche.getSdy();
		double yLowerLimit = Niche.getMeany() - 1*Niche.getSdy();
		for(int i = 0; i<allNiches.size(); i++){
			Niche subject = allNiches.get(i);
			if(subject.getxCoord() > xUpperLimit || subject.getxCoord() < xLowerLimit || subject.getyCoord() > yUpperLimit || subject.getyCoord() < yLowerLimit){
				if (subject.getOccupant() != null) {
					ancestor = subject.getOccupant().removeSpecies(ancestor);
					if (subject.getOccupant().checkRemoved()) {
						occupiedNiches.remove(subject);
						allNiches.remove(subject);
					}
				} else if (subject.getOccupant() == null) {
					emptyNiches.remove(subject);
					allNiches.remove(subject);
				}
			}
		}
		return ancestor;
	}

	public boolean spawnSpecies(Niche subject, int optimumDistance) {
		/**
		 * Returns true iff we are still looking for new species
		 */
		double shortestDistance = optimumDistance * 10000;
		Niche shortestNiche = null;
		// Pick the closest potential niche:
		for (int i = 0; i < allNiches.size(); i++) {
			Niche tempNiche = allNiches.get(i); // Temporary niche
			if (tempNiche.getOccupant() != null && subject.getOccupant() == null) {
				double distance = getNicheDistance(tempNiche, subject);
				if (distance < optimumDistance && distance < shortestDistance) {
					shortestDistance = distance;
					shortestNiche = tempNiche;
				}
			} else if (tempNiche.getOccupant() == null && subject.getOccupant() != null) {
				double distance = getNicheDistance(subject, tempNiche);
				if (distance < optimumDistance && distance < shortestDistance) { 
					shortestDistance = distance; 
					shortestNiche = tempNiche;
				}
			}
		}
		// If we have a potential niche, then spawn to it:
		if (shortestNiche != null) {
			if (shortestNiche.getOccupant() != null && subject.getOccupant() == null) {
				shortestNiche.getOccupant().spawn(shortestNiche, subject);
				occupiedNiches.add(subject);
				emptyNiches.remove(subject);
			} else if (shortestNiche.getOccupant() == null && subject.getOccupant() != null) {
				subject.getOccupant().spawn(subject, shortestNiche);
				occupiedNiches.add(shortestNiche);
				emptyNiches.remove(shortestNiche);
			}
			return true;
		} else {
//			emptyNiches.add(subject);
			return false;
		}
	}

	public Species runSimulation(int maxNiches, int maxSpecies, int optimumDistance) {
		// initialise
		RobRandom rand = new RobRandom();
		int numNiches;
		int numSpecies = 1;
		double chanceAction = 0.20;
		int seed = 666;
		allNiches.clear();
		occupiedNiches.clear();
		emptyNiches.clear();
		Species.setNumSpecies(0);
        Niche.resetGlobalTime();
		Niche.resetGlobalID();
		Niche origin = new Niche(); // with random coordinates, and put in
									// origin species
		Species ancestor = new Species(null, null, null);
		origin.setSpecies(ancestor);
		allNiches.add(origin);
		occupiedNiches.add(origin);
		numNiches = allNiches.size();
		while (numNiches < maxNiches && numSpecies < maxSpecies) {
			// createANiche
			// while there are any occupied niches that are close enough to
			// unoccupied niches
			// speciate into them -- closest ones first!
			Niche next = new Niche();
			allNiches.add(next);
			emptyNiches.add(next);
			boolean searching = true;
			spawnSpecies(next, optimumDistance);
			numSpecies = Species.getTips(ancestor);
			if (numSpecies >= maxSpecies) {
				// numSpecies = maxSpecies+1;
				// numSpecies = Species.getTips(ancestor);
				// System.out.println(numSpecies);
				break;
			}
			/*
			while (searching == true && numSpecies < maxSpecies) {

				searching = spawnSpecies(next, optimumDistance);
				// chainSpawnSpecies(optimumDistance);
				// ++numSpecies;
				numSpecies = Species.getTips(ancestor);
				if (numSpecies >= maxSpecies) {
					// numSpecies = maxSpecies+1;
					// numSpecies = Species.getTips(ancestor);
					// System.out.println(numSpecies);
					searching = false;
				}

				// printTree(ancestor);
			}*/
//			printTree(ancestor);
			for (int i = 0; i < emptyNiches.size(); i++) {
				if (emptyNiches.get(i).getOccupant() != null) {
					emptyNiches.remove(i);
				}
			}
			numSpecies = Species.getTips(ancestor);
			searching = true;
			while (searching == true && numSpecies < maxSpecies) {
				searching = chainSpawnSpecies(optimumDistance);
				numSpecies = Species.getTips(ancestor);
//				printTree(ancestor);
				if (numSpecies >= maxSpecies) {
					searching = false;
				}
			}
//			ancestor = removeNicheAtRandom(chanceAction, ancestor);
			seed++;
			// do stuff
			numNiches++;
			ancestor.advAgeOfAllFinalDescendants();
			ancestor.checkAges();

		}

		// System.out.println(numNiches);
		// System.out.println(numSpecies);
		ancestor = largeRemovalOfNiches(ancestor);
		Niche thing = new Niche();
		ancestor.advAgeOfAllFinalDescendants();
		return ancestor;
	}

}
