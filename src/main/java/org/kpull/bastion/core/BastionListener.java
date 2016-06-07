package org.kpull.bastion.core;

/**
 * Interface that {@link Bastion} will use to notify implementors of call status.
 */
public interface BastionListener {

    /**
     * Event fired when a call has started.
     */
    void callStarted();

    /**
     * Event fired when a call completed successfully.
     */
    void callFinished();


    /**
     * Event fired when assertions on call failed.
     */
    void callFailed();

    /**
     * Event fired when an error (that is not an assertion error) on a call occurs.
     */
    void callError();

}
