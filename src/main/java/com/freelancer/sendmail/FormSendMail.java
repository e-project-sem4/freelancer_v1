package com.freelancer.sendmail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.ArrayList;

@Service
public class FormSendMail{
    @Autowired
    private JavaMailSender javaMailSender;
    public  void sendMailInvite(String to,String jobId) throws MessagingException, IOException {
        String from = "jobfreelancer@gmail.com";
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg);
        helper.setFrom(new InternetAddress(from, "Rocket Company"));

        helper.setTo(to);
        helper.setSubject("Invite Apply To Job");
        String content = "<b>Hey guy</b>,<br><i> We come from the Website Jobfreelancer</i><br><span> You have received an invitation to apply for a job from a User Business.</span>" +
                "<br><a href='google.com'>Click here<a>"+"<span> for more details.</span>"+
                "<div style='text-align:left;'><a href='facebook.com'><img src='https://res.cloudinary.com/hoadaica/image/upload/v1634898297/freelancer4-e1531398303434_vu5puk.jpg'" +
                " alt='Rocket Team' title='Rocket Team' style='width:700px;height:400px;'></img></a></div>";
        helper.setText(content,true);


        javaMailSender.send(msg);
    }

    public  void sendMailSuggest(String to,String jobId) throws MessagingException, IOException {
        String from = "jobfreelancer@gmail.com";
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg);
        helper.setFrom(new InternetAddress(from, "Rocket Company"));
        helper.setTo(to);
        helper.setSubject("Suggest Apply To Job");
        String content = "<b>Hey guy</b>,<br><i> We come from the Website Jobfreelancer</i><br><span> We found a job that's perfect for you.</span>" +
                "<br><a href='google.com'>Click here<a>"+"<span> for more details.</span>"+
                "<div style='text-align:left;'><a href='facebook.com'><img src='https://res.cloudinary.com/hoadaica/image/upload/v1634898297/freelancer4-e1531398303434_vu5puk.jpg'" +
                " alt='Rocket Team' title='Rocket Team' style='width:700px;height:400px;'></img></a></div>";
        helper.setText(content,true);


        javaMailSender.send(msg);
    }



}
