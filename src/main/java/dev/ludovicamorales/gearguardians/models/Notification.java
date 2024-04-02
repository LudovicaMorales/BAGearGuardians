package dev.ludovicamorales.gearguardians.models;

import lombok.Data;

@Data
public class Notification {

    private String destinationSMSNumber;
    private String smsMessage;
}
