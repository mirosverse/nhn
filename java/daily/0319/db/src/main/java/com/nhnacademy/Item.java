package com.nhnacademy;

public class Item {
    private String id;
    private String model;
    private int health;
    private int attack;
    private int defense;
    private int speed;
    private int attackSpeed;

    public Item(String id, String model, int health, int attack, int defense, int speed, int attackSpeed) {
        this.id = id;
        this.model = model;
        this.health = health;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.attackSpeed = attackSpeed;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getAttackSpeed() {
        return attackSpeed;
    }

    public void setAttackSpeed(int attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    @Override
    public String toString() {
        return "id: " + id
                + ", model: " + model
                + ", health: " + health
                + ", attack: " + attack
                + ", defense: " + defense
                + ", speed: " + speed
                + ", attackSpeed: " + attackSpeed;

    }

}
