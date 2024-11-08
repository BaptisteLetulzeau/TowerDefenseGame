package game;

public class Player {
    private int healthPoints;
    private int money;

    public Player(int initialHealth, int initialMoney) {
        this.healthPoints = initialHealth;
        this.money = initialMoney;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void decreaseHealth(int amount) {
        this.healthPoints -= amount;
        if (this.healthPoints < 0) {
            this.healthPoints = 0;
        }
    }

    public int getMoney() {
        return money;
    }

    public void increaseMoney(int amount) {
        this.money += amount;
    }

    public boolean decreaseMoney(int amount) {
        if (this.money >= amount) {
            this.money -= amount;
            return true;
        } else {
            return false;
        }
    }
}
