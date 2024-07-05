package game.gameplay;

import game.GameInfo;
import game.entities.*;
import game.hud.ShootButton;

import java.util.ArrayList;
import java.util.Random;

public class GameplayModel {
    private int ticksPassed;
    private final ArrayList<Entity> entities;
    private final ArrayList<Entity> entitiesToRemove;
    private final ArrayList<Entity> entitiesToAdd;
    private final BubbleSort bubbleSort;
    private final PlayerSquare playerSquare;
    private final BubbleSpawner bubbleSpawner;
    private boolean isPaused = false;
    private int score = 0;
    private int seed;
    private boolean ended = false;
    public GameplayModel(int seed) {
        this.seed = seed;
        ticksPassed = 1;
        Random r = new Random(seed);
        entities = new ArrayList<>();
        entitiesToRemove = new ArrayList<>();
        entitiesToAdd = new ArrayList<>();
        bubbleSort = new BubbleSort(r.nextInt());
        bubbleSpawner = new BubbleSpawner(r.nextInt());

        ShootButton shootButton = new ShootButton(GameInfo.frameWidth/2 - 50,GameInfo.heightLimit-75);
        playerSquare = new PlayerSquare(shootButton);


        entities.add(bubbleSort);
        entities.add(shootButton);
        entities.add(playerSquare);
    }

    // -------------------- simulate tick --------------------
    public void gameStep() {
        if (isPaused || ended) return;

        // spawn the needed bubbles before FOR cycle as it modifies array
        bubbleSpawner.doStep(this);
        // each entity does its job
        if (!entities.isEmpty()) {
            for (Entity thisEntity : entities) {
                thisEntity.doStep(this);
                // check lose condition
                if (thisEntity.getClass() == EnemyBubble.class) {
                    checkCollision((EnemyBubble) thisEntity);
                }
            }
        }
        // remove unnecessary entities
        entities.addAll(entitiesToAdd);
        entitiesToAdd.clear();
        entities.removeAll(entitiesToRemove);
        entitiesToRemove.clear();

        // signal passed tick
        ticksPassed++;
    }

    private void checkCollision(EnemyBubble enemyBubble) {
        if (playerSquare.getxPos() < enemyBubble.getPosX() + enemyBubble.getSize() &&
            playerSquare.getxPos() + playerSquare.getSize() > enemyBubble.getPosX() &&
            playerSquare.getyPos() < enemyBubble.getPosY() + enemyBubble.getSize() &&
            playerSquare.getyPos() + playerSquare.getSize() > enemyBubble.getPosY()) {
            setEnded(true);
        }
    }

    // -------------------- entity manager methods --------------------
    public void addEntity(Entity entity) {
        entitiesToAdd.add(entity);
    }
    public void removeEntity(Entity entity) {
        entitiesToRemove.add(entity);
    }

    // -------------------- set and get methods --------------------
    public int getArrayElement(int index) {
        int[] arr = bubbleSort.getArray();
        return arr[index];
    }
    public int getTicksPassed() {
        return ticksPassed;
    }
    public ArrayList<Entity> getEntities() {
        return entities;
    }
    public PlayerSquare getPlayerSquare() {
        return playerSquare;
    }
    public void setEnded(boolean ended) {
        this.ended = ended;
    }
    public boolean isEnded() {
        return ended;
    }
    public void increaseScore(int scoreGained) {
        score += scoreGained;
    }
    public int getScore() {
        return score;
    }
    public int getSeed() {
        return seed;
    }
    public void changePaused() {
        isPaused = !isPaused;
    }
}