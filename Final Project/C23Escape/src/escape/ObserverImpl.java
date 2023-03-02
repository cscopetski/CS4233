package escape;

import escape.required.EscapeException;
import escape.required.GameObserver;

public class ObserverImpl implements GameObserver {

    public void notify(String message)
    {
        System.out.println(message);
    }

}
