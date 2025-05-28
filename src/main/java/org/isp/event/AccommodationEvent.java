package org.isp.event;

import org.isp.model.Accommodation;
import org.springframework.context.ApplicationEvent;

public class AccommodationEvent extends ApplicationEvent {
    private final String action;
    private final Accommodation accommodation;

    public AccommodationEvent(Object source, String action, Accommodation accommodation) {
        super(source);
        this.action = action;
        this.accommodation = accommodation;
    }

    public String getAction() {
        return action;
    }

    public Accommodation getAccommodation() {
        return accommodation;
    }
}
