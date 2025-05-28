package org.isp.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AccommodationEventListener {
    private static final Logger logger = LoggerFactory.getLogger(AccommodationEventListener.class);

    @Async("taskExecutor")
    @EventListener
    public void handleAccommodationEvent(AccommodationEvent event) {
        logger.info("Processing {} event for accommodation: {}", 
            event.getAction(), 
            event.getAccommodation().getId());
        
        // Add your async processing logic here
        switch (event.getAction()) {
            case "CREATED":
                processAccommodationCreated(event);
                break;
            case "UPDATED":
                processAccommodationUpdated(event);
                break;
            case "DELETED":
                processAccommodationDeleted(event);
                break;
        }
    }

    private void processAccommodationCreated(AccommodationEvent event) {
        // Implement async processing for new accommodation
        logger.info("Processing new accommodation: {}", event.getAccommodation().getId());
    }

    private void processAccommodationUpdated(AccommodationEvent event) {
        // Implement async processing for updated accommodation
        logger.info("Processing updated accommodation: {}", event.getAccommodation().getId());
    }

    private void processAccommodationDeleted(AccommodationEvent event) {
        // Implement async processing for deleted accommodation
        logger.info("Processing deleted accommodation: {}", event.getAccommodation().getId());
    }
}
