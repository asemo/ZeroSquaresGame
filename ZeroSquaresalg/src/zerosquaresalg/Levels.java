
package zerosquaresalg;



public class Levels {

    public String[][] grid;
    public String[][] color;
    public String[][] status;

    public Levels() {}

    public Object newLevel(int levelNumber) {
        if (levelNumber == 1) {
            Level1 level1 = new Level1();
            this.grid = level1.grid;
            this.color = level1.color;
            this.status = level1.status;
            
            return this;
        } else if (levelNumber == 2) {
            Level2 level2 = new Level2();
            this.grid = level2.grid;
            this.color = level2.color;
            this.status = level2.status;
            
            return this;
        } else if (levelNumber == 3) {
            Level3 level3 = new Level3();
            this.grid = level3.grid;
            this.color = level3.color;
            this.status = level3.status;
            // level3.generateLevel3();
            return this;
        } else {
            return null;
        }
    }
}
