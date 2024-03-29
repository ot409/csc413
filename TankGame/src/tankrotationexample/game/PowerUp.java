package tankrotationexample.game;

// PowerUp interface defines the contract for applying power-ups to game objects
public interface PowerUp {
    void applyPowerUp(Tank targetTank);    // apply the power-up effect to the specified Tank object
}
