package com.metalsack.retrobus.events;


/**
 * Post this event on the EventBus to tell the {@link com.metalsack.retrobus.network.ApiClient} that a
 * request has finished. This will remove the request from the list of running requests.
 */
public class RequestFinishedEvent {
    /**
     * Identifies the request.
     */
    private final String requestTag;

    /**
     * Initialize the event with the passed tag.
     *
     * @param requestTag Identifies the request.
     */
    public RequestFinishedEvent(String requestTag) {
        this.requestTag = requestTag;
    }

    public String getRequestTag() {
        return requestTag;
    }

}
