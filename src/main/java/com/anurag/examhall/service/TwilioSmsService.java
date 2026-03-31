package com.anurag.examhall.service;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class TwilioSmsService {

    private static final Logger logger = LoggerFactory.getLogger(TwilioSmsService.class);

    @Value("${twilio.phone-number}")
    private String fromPhone;

    public void sendSms(String toPhone, String messageBody) {
        try {
            Message message = Message.creator(
                new PhoneNumber(toPhone),
                new PhoneNumber(fromPhone),
                messageBody
            ).create();
            logger.info("SMS sent to {} - SID: {}", toPhone, message.getSid());
        } catch (Exception e) {
            logger.error("Failed to send SMS to {}: {}", toPhone, e.getMessage());
        }
    }
}
