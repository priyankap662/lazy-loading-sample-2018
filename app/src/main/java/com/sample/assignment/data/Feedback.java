package com.sample.assignment.data;

public class Feedback<Model> {

    /**
     * Invoked regardless of the response or lack of it.
     * @param success true if successful response is received.
     */
    public void received(boolean success) {
        //
    }

    /**
     * Invoked only if a successful response from server or session cache (short duration) is received.
     * @param model response data
     */
    public void success(Model model) {
        //
    }

    /**
     * Invoked if the response received from server is erroneous, or no response is received.
     * @param throwable Exception thrown.
     */
    public void error(Throwable throwable) {
        //
    }

    /**
     * Invoked if the response is from expired cache (stale), this only happens if cache is enabled
     * for the API and the call fails due to network or server errors.
     * @param model response data
     */
    public void stale(Model model) {
        //
    }
}
