package niches;


import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Created by Edward on 25/01/2016.
 */
public class NicheModelDestruction {
    public static NicheGenerator ng;
    private static PrintWriter pw;

    public static void main(String[] args) {
        ng = new NicheGenerator();
        int numTrees = 1000;

        /**
         * What we want to do:
         * create a new instance of Species
         * create a new instance of Niche
         * Put the Species in the Niche
         * Create a new niche
         * Check distance between the new niche and the old one
         * If close enough
         *  Spawn the species
         *  put it in the new niche
         *
         */
        try {
            pw = new PrintWriter("tree_sd10_t10_delmass.txt");
            for (int i = 0; i < numTrees; i++) {
                Species ancestor = ng.runSimulation(1000, 100, 10);
//            ng.printList("empty");
//            ng.printList("occupied");
                if(!ancestor.isLeaf()) {
                    pw.println(ancestor + "\n");
                    Species.treeTrackIncrement();
                }
                if (i % 100 == 0) {
                    System.out.print(".");
                    System.out.flush();
                }
            }
            pw.close();
            System.out.println("All done.");
        } catch(FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
