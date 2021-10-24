package com.freelancer.sendmail;

import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.freelancer.JwtAuthServiceApp;

@Component
public class ThreadSendMail extends Thread {

    @Autowired
    private FormSendMail formSendMail;


    @Override
    public void run() {


        while (true) {

            if (JwtAuthServiceApp.listSendMailSuggest.size() > 0) {
                for (int i = 0; i < 10; i++) {
                    SendMailModel sendMailModel = JwtAuthServiceApp.listSendMailSuggest.poll();
                    if (sendMailModel != null) {
                        try {
                            formSendMail.sendMailSuggest(sendMailModel.getTo(), sendMailModel.getJobId());
                        } catch (MessagingException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}

