package escape;

import escape.required.EscapeException;
import escape.required.GameObserver;

public interface Observable {

    /**
     * Add an observer to this manager. Whever the move() method returns
     * false, the observer will be notified with a message indication the
     * problem.
     * @param observer
     * @return the observer
     */
    GameObserver addObserver(GameObserver observer);

    /**
     * Remove an observer from this manager. The observer will no longer
     * receive notifications from this game manager.
     * @param observer
     * @return the observer that was removed or null if it had not previously
     *     been registered
     */
    GameObserver removeObserver(GameObserver observer);

    /**
     * Notifies all observers
     * @param message the message to notify
     */
    void notifyObservers(String message);


}
