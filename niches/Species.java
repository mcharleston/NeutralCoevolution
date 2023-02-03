package niches;



/**
 * Created by Edward on 11/01/2016.
 */
public class Species { //Currently set up as a binary tree, can turn this into a binary tree instead of species
    public Species parent;
    private Species leftChild;
    private Species rightChild;
    private int ID; // unique ID for EVERY NODE.
    private static int numSpecies;
    private static int treeTracker;
    private double time;
    private boolean removed;

    public Species(Species parent, Species leftChild, Species rightChild){
        this.parent = parent;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        ID=numSpecies;
        numSpecies++;
        removed = false;
    }

    public Species getParent() {
        return parent;
    }

    public void setParent(Species parent) {
        this.parent = parent;
    }

    public Species getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Species leftChild) {
        this.leftChild = leftChild;
    }

    public Species getRightChild() {
        return rightChild;
    }

    public void setRightChild(Species rightChild) {
        this.rightChild = rightChild;
    }

    public double getAge() {
        return time;
    }

    public void setAge(double age) {
        this.time = age;
    }

    public int getID() {
        return ID;
    }

    public void setID(int id){
        this.ID = id;
    }

    public static int getNumSpecies() {
        return numSpecies;
    }

    public static void setNumSpecies(int numSpecies) {
        Species.numSpecies = numSpecies;
    }

    public boolean isLeaf() {
        return leftChild == null && rightChild == null;
    }

    public boolean checkRemoved(){
        return this.removed;
    }

    public void spawn(Niche curNiche, Niche nextNiche) {
        // needs to create a new Species based on this one and return it
        rightChild = new Species(this, null, null);
        rightChild.setAge(Niche.getGlobalTime());
        leftChild = new Species(this, null, null);
        leftChild.setAge(Niche.getGlobalTime());
        if (curNiche != null) {
            curNiche.setSpecies(leftChild);
        }
//        leftChild.ID = this.ID;
        leftChild.parent=this;
        rightChild.parent=this;
        if (nextNiche != null) {
            nextNiche.setSpecies(rightChild);
        }
        Niche.setGlobalTime(Niche.getGlobalTime()+1);
    }

    public Species removeSpecies(Species origin){
        if(!isLeaf()){
            throw new IllegalArgumentException();
        }
        else {
//            if (this.parent.leftChild != null && this.parent.leftChild == this){
//                if (this.parent.leftChild.isLeaf()){
////                    this.parent.setID(this.parent.rightChild.getID());
//                    this.parent.rightChild = null;
//                    this.parent.leftChild = null;
//
//                }
//            } else if (this.parent.rightChild != null && this.parent.rightChild == this){
//                if (this.parent.rightChild.isLeaf()){
////                    this.parent.setID(this.parent.leftChild.getID());
//                    this.parent.leftChild = null;
//                    this.parent.rightChild = null;
//                }
//            }
            Species child = this;
            Species parent = child.parent;
            if (parent != null) {
                Species grandparent = parent.parent;
                if (grandparent != null) {
                    if (grandparent.leftChild == parent) {
                        if (parent.leftChild == child) {
                            grandparent.leftChild = parent.rightChild;
                            child.removed = true;
                        } else if (parent.rightChild == child) {
                            grandparent.leftChild = parent.leftChild;
                            child.removed = true;
                        }
                    } else if (grandparent.rightChild == parent) {
                        if (parent.leftChild == child) {
                            grandparent.rightChild = parent.rightChild;
                            child.removed = true;
                        } else if (parent.rightChild == child) {
                            grandparent.rightChild = parent.leftChild;
                            child.removed = true;
                        }
                    }
                } else {
                    if (parent.leftChild == child){
                        origin = parent.rightChild;
                        parent.rightChild.parent = null;
                        parent.delChildren();
                        child.removed = true;
                    } else if (parent.rightChild == child){
                        origin = parent.leftChild;
                        parent.leftChild.parent = null;
                        parent.delChildren();
                        child.removed = true;
                    }
                }
            } else {

            }
            advAgeOfAllFinalDescendants();
        }
        return origin;
    }
    public void delChildren(){
        this.leftChild = null;
        this.rightChild = null;
    }

    public void advAgeOfAllFinalDescendants(){
        if(!isLeaf()){
            if(this.leftChild != null) {
                leftChild.advAgeOfAllFinalDescendants();
            }
            if (this.rightChild != null) {
                rightChild.advAgeOfAllFinalDescendants();
            }
        } else if(isLeaf()) {
            this.setAge(Niche.getGlobalTime());
        }
        if (leftChild != null) {
            if (time > leftChild.getAge()) {
                throw new RuntimeException("Incongruent age of node " + this.getID());
            }
        }
        if (rightChild != null) {
            if (time > rightChild.getAge()) {
                throw new RuntimeException("Incongruent age of node " + this.getID());
            }
        }
    }
    public static void treeTrackIncrement(){
        treeTracker++;
    }

    public String toString() {
        double branchLength;
        String str="";
        if (parent == null){
            str+= "origin"+treeTracker+"h";
        }
//        if (isLeaf()) {
//            str += this.ID;
//        } else {
//            str += "(";
//            if(leftChild != null) {
//                str += leftChild.toString() + ",";
//            }
//            if(rightChild != null) {
//                str += rightChild.toString() + ")";
//            }
//        }
        if (!isLeaf()) {
            str += "(";
            if (leftChild != null) {
                str += leftChild.toString() + ",";
            }
            if (rightChild != null) {
                str += rightChild.toString() + ")";
            }
        }
        if (this.parent != null){
            branchLength = this.time-this.parent.time;
        } else {
            branchLength = this.time;
        }
        if (isLeaf()) {
            if (this.parent != null) {
                str += "c" + this.ID + ":" + branchLength;
            }
            if (this.parent == null) {
                str += "p" + this.ID + ":" + branchLength +  ";";
            }
        }
        if (!isLeaf()){
            if (this.parent != null) {
                str += "p" + this.ID + ":" + branchLength;
            }
            if (this.parent == null){
                str += "p" + this.ID + ":" + branchLength +  ";";
            }
        }
//        if (!isLeaf()) {
//            str += ":(";
//            if (leftChild != null) {
//                str += leftChild.toString() + ",";
//            }
//            if (rightChild != null) {
//                str += rightChild.toString() + ")";
//            }

        return str;
    }

    public static int getTips(Species subject){
        if(subject == null){
            return 0;
        } else if (subject.isLeaf()){
            return 1;
        } else {
            return getTips(subject.getLeftChild()) + getTips(subject.getRightChild());
        }
    }
    
	public void checkAges() {
		// root of the species is origin
		if (isLeaf()) {
			return;
		}
		if (leftChild.time < time) {
			throw new RuntimeException("This nmde is older than its child: ID=" + ID + 
					", age = " + time + ", child ID=" + leftChild.ID + ", age=" + leftChild.time);
			
		}
		if (rightChild.time < time) {
			throw new RuntimeException("This nmde is older than its child: ID=" + ID + 
					", age = " + time + ", child ID=" + rightChild.ID + ", age=" + rightChild.time);
			
		}
		leftChild.checkAges();
		rightChild.checkAges();
	}


}
