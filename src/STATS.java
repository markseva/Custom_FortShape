public class STATS {

    private static int numFood = 12;
    private static int numEnemies = 3;
    private static int foodSplit = 2;
    private static int life = 3;
    private static int level = 1;
    private static int lowSpeed = 4;
    private static int rangeSpeed = 8;
    private static boolean MENU = true;
    private static boolean PLAY = false;
    private static boolean WIN = false;
    private static boolean FAIL = false;

    public static int getNumFood() {
        return numFood;
    }

    public static int getNumEnemies() {
        return numEnemies;
    }

    public static int getFoodSplit() { return foodSplit; }

    public static int getLife() {
        return life;
    }

    public static int getLevel() {
        return level;
    }

    public static int getLowSpeed() {
        return lowSpeed;
    }

    public static int getRangeSpeed() {
        return rangeSpeed;
    }

    public static void setNumFood(int numFood) {
        STATS.numFood = numFood;
    }

    public static void setNumEnemies(int numEnemies) {
        STATS.numEnemies = numEnemies;
    }

    public static void setLife(int life) {
        STATS.life = life;
    }

    public static void setLevel(int level) {
        STATS.level = level;
    }

    public static void setLowSpeed(int lowSpeed) {
        STATS.lowSpeed = lowSpeed;
    }

    public static void setRangeSpeed(int rangeSpeed) {
        STATS.rangeSpeed = rangeSpeed;
    }

    public static boolean isMENU() {
        return MENU;
    }

    public static void setMENU(boolean MENU) {
        STATS.MENU = MENU;
    }

    public static boolean isPLAY() {
        return PLAY;
    }

    public static void setPLAY(boolean PLAY) {
        STATS.PLAY = PLAY;
    }

    public static boolean isWIN() {
        return WIN;
    }

    public static void setWIN(boolean WIN) {
        STATS.WIN = WIN;
    }

    public static boolean isFAIL() {
        return FAIL;
    }

    public static void setFAIL(boolean FAIL) {
        STATS.FAIL = FAIL;
    }

    public static void updateLevel(){
        switch(level){
            case 1:
                setNumFood(5);
                setNumEnemies(1);
                setLowSpeed(4);
                setRangeSpeed(4);
                break;
            case 2:
                setNumFood(8);
                setNumEnemies(3);
                setLowSpeed(8);
                setRangeSpeed(10);
        }
    }
}
//
