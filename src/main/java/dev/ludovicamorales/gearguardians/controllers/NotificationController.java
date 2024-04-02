package dev.ludovicamorales.gearguardians.controllers;

import dev.ludovicamorales.gearguardians.models.Notification;
import dev.ludovicamorales.gearguardians.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;

public class NotificationController {

    @Autowired
    NotificationService notificationService;

    public String processSMS(Notification notification){
        return notificationService.sendSMS(notification.getDestinationSMSNumber(), notification.getSmsMessage());
    }
}
