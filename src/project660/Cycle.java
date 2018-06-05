package project660;

/**
 * Cycle class
 * 
 * @author Mike Gordo <mgordo@live.com> UAlbany CSI 660 2013
 */
public class Cycle {
    private int current;
    int maximum;
    int[] members;

    public Cycle(int size) {
        this.current = 0; // starts with 1!!! not 0!
        this.members = new int[size + 3];
        this.maximum = size;
    }

    /**
     * Makes a clone of a cycle
     * 
     * @param cycle
     */
    public Cycle(Cycle cycle) {
        this.current = cycle.current;
        this.maximum = cycle.maximum;
        this.members = new int[this.maximum + 3];

        for (int i = 1; i <= this.maximum; i++) {
            this.members[i] = cycle.members[i];
        }
    }

    /**
     * Checks if cycle is full
     * 
     * @return
     */
    public boolean isFull() {
        return (current - 0 > maximum);
    }

    public boolean isEmpty() {
        return (current < 1);
    }

    /**
     * Check for duplicate nodes inside the cycle we don't want any cycles that have
     * start-node inside
     */
    public boolean isValid() {
        for (int i = 1; i <= this.maximum; i++) {
            for (int j = 1; j <= this.maximum; j++) {
                if (members[i] == members[j] && j != i) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Check if v is in cycle
     * 
     * @param v
     * @return
     */
    private boolean inCycle(int v) {
        for (int i = 1; i <= this.maximum; i++) {
            if (members[i] == v) {
                return true;
            }
        }

        return false;
    }

    /**
     * Push and test for a complete cycle
     * 
     * @param v
     * @return true if this push creates a cycle
     */
    public boolean push(int v) {
        if (v == members[1] && current == maximum && isValid()) {
            return true;
        }

        if (isFull()) {
            return false;
        }

        current++;
        members[current] = v;

        return false;
    }

    public void rollback() {
        if (current < 1) {
            return;
        }

        members[current] = -1;
        current--;
    }

    /**
     * Create all variants of the given cycle (1 2 3) returns: (2 3 1) (3 1 2) (1 2
     * 3)
     */
    public Cycle[] generateVariants() {
        Cycle[] output = new Cycle[this.maximum];

        for (int i = 1; i <= this.maximum; i++) {
            this.perm();
            output[i - 1] = new Cycle(this);
        }

        return output;
    }

    /**
     * Create one variant of the given cycle (1 2 3 4) => (2 3 4 1)
     */
    public void perm() {
        int first = this.members[1];

        for (int i = 2; i <= this.maximum; i++) {
            this.members[i - 1] = this.members[i];
        }

        this.members[this.maximum] = first;
    }

    public String toString() {
        String result = "";

        for (int i = 1; i <= current; i++) {
            result += (members[i] + " ");
        }

        return result.trim();
    }

}
