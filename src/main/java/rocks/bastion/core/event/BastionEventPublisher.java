package rocks.bastion.core.event;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public interface BastionEventPublisher {

    void registerListener(BastionListener listener);

    void notifyListenersCallStarted(BastionStartedEvent event);

    void notifyListenersCallFailed(BastionFailureEvent event);

    void notifyListenersCallError(BastionErrorEvent event);

    void notifyListenersCallFinished(BastionFinishedEvent event);

}
