
package containers;

/**
 *
 * @author Robert Popescu <robertp.org>
 */
public class Container {
    private int cantitate;

    public Container() {
        this.cantitate = 0;
    }

    public int contains() {
        return this.cantitate;
    }

    public void add(int amount) {
        if (amount > 0) {
            this.cantitate += amount;
            if (this.cantitate > 100) {
                this.cantitate = 100;
            }
        }
    }

    public void remove(int amount) {
        if (amount > 0) {
            this.cantitate -= amount;
            if (this.cantitate < 0) {
                this.cantitate = 0;
            }
        }
    }

    @Override
    public String toString() {
        return this.cantitate + "/100";
    }
}