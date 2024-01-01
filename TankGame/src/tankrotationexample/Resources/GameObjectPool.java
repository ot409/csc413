package tankrotationexample.Resources;

import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameObjectPool<T> {
    private final List<T> objectPool;
    private final String objectType;

    // Constructor to initialize the GameObjectPool
    public GameObjectPool(String type, int size) {
        this.objectPool = Collections.synchronizedList(new ArrayList<>(size));
        this.objectType = type;
    }

    // Fill the pool with game objects of the specified type and size
    public boolean fillPool(Class<T> type, int size){
        try {
            for(int i = 0; i < size; i++){
                // Create instances of the specified game object type and add them to the pool
                this.objectPool.add(type.getDeclaredConstructor(Float.TYPE,
                                Float.TYPE,
                                BufferedImage.class)
                        .newInstance(0,0,
                                GameResourceManager.getSprite(this.objectType))
                );
            }
        } catch (InvocationTargetException | InstantiationException  | IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    // Get a game object from the pool
    public T getGameObject(){
        // Remove and return the last element from the pool (LIFO behavior)
        return this.objectPool.remove(this.objectPool.size()-1);
    }

    // Return a game object back to the pool
    public boolean returnGameObject(T obj){
        // Add the specified object back to the pool
        return this.objectPool.add(obj);
    }
}
