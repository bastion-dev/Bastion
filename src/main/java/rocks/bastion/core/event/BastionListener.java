package rocks.bastion.core.event;

import rocks.bastion.core.BastionBuilderImpl;

/**
 * Interface that {@link BastionBuilderImpl} will use to notify implementors of call status.
 */
public interface BastionListener {

    /**
     * Event fired when a call has started.
     *
     * @param event Event object containing information about the Bastion request which was started
     */
    void callStarted(BastionStartedEvent event);

    /**
     * Event fired when a call completed successfully.
     *
     * @param event Event object containing information about the Bastion request which was completed
     */
    void callFinished(BastionFinishedEvent event);


    /**
     * Event fired when assertions on call failed.
     *
     * @param event Event object containing information about the Bastion request which has failed assertions
     */
    void callFailed(BastionFailureEvent event);

    /**
     * Event fired when an error (that is not an assertion error) on a call occurs.
     *
     * @param event Event object containing information about the Bastion request which could not be completed successfully
     */
    void callError(BastionErrorEvent event);

}
