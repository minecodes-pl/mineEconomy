package pl.mineEconomy.objects;

public class User {

    private String name;
    private int money = 5;
    private boolean update;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
        this.update = true;
    }

    public void addMoney(int money) {
        this.money += money;
        this.update = true;
    }

    public void removeMoney(int money) {
        this.money -= money;
        this.update = true;
    }

    public boolean needUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }
    
}
