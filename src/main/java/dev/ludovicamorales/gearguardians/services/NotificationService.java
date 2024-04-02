package dev.ludovicamorales.gearguardians.services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Value("${TWILIO_ACCOUNT_SID}")
    String ACCOUNT_SID;

    @Value("${TWILIO_AUTH_TOKEN}")
    String AUTH_TOKEN;

    @Value("${TWILIO_PHONE_NUMBER}")
    String OUTGOING_PHONE_NUMBER;

    private void setup(){
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }
    public String sendSMS(String smsNumber, String smsMessage){

        Message message = Message.creator(
                new PhoneNumber(smsNumber),
                new PhoneNumber(OUTGOING_PHONE_NUMBER),
                smsMessage).create();

        return message.getStatus().toString();
    }
}
