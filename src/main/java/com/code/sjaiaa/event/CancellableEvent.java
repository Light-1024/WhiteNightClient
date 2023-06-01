package com.code.sjaiaa.event;

/**
 * @author sjaiaa
 * @date 2023/5/19 20:37
 * @discription
 */
public class CancellableEvent {
    private boolean isCancelled = false;

    /**
     * Let you know if the event is cancelled
     *
     * @return state of cancel
     */
    public boolean isCancelled() {
        return isCancelled;
    }

    /**
     * Allows you to cancel a event
     */
    public void cancelEvent() {
        isCancelled = true;
    }
}

