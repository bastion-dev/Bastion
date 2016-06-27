package rocks.bastion.core.event;

import rocks.bastion.core.Bastion;

/**
 * Interface that {@link Bastion} will use to notify implementors of call status.
 */
public interface BastionListener {

    /**
     * Event fired when a call has started.
     *
     * @param event
     */
    void callStarted(BastionStartedEvent event);

    /**
     * Event fired when a call completed successfully.
     *
     * @param event
     */
    void callFinished(BastionFinishedEvent event);


    /**
     * Event fired when assertions on call failed.
     *
     * @param event
     */
    void callFailed(BastionFailureEvent event);

    /**
     * Event fired when an error (that is not an assertion error) on a call occurs.
     *
     * @param event
     */
    void callError(BastionErrorEvent event);

}
