package com.freelancer.sendmail;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FormSendMail {

	@Value("${sender_email}")
	String senderEmail;

	@Value("${link_job}")
	String linkJob;

	@Autowired
	private JavaMailSender javaMailSender;
//    public  void sendMailInvite(String to,String jobId) throws MessagingException, IOException {
//        String from = "jobfreelancer@gmail.com";
//        String linkJob = "https://job-hunting-vn.herokuapp.com/job-details?id="+jobId;
//        MimeMessage msg = javaMailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(msg);
//        helper.setFrom(new InternetAddress(from, "Rocket Company"));
//
//        helper.setTo(to);
//        helper.setSubject("Invite Apply To Job");
//        String content = "<b>Hey guy</b>,<br><i> We come from the Website Jobfreelancer</i><br><span> You have received an invitation to apply for a job from a User Business.</span>" +
//                "<br><a href='"+linkJob+"'>Click here<a>"+"<span> for more details.</span>"+
//                "<div style='text-align:left;'><a href='https://job-hunting-vn.herokuapp.com/home'><img src='https://res.cloudinary.com/hoadaica/image/upload/v1634898297/freelancer4-e1531398303434_vu5puk.jpg'" +
//                " alt='Rocket Team' title='Rocket Team' style='width:700px;height:400px;'></img></a></div>";
//        helper.setText(content,true);
//
//
//        javaMailSender.send(msg);
//    }

	public void sendMail(String to, String content, String jobId) throws MessagingException, IOException {
		String from = this.senderEmail;
		String linkJob = this.linkJob + jobId;
		MimeMessage msg = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg);
		helper.setFrom(new InternetAddress(from, "TopWorkRocker Company"));
		helper.setTo(to);
		helper.setSubject("Suggest Apply To Job");
		String text = "<b>Hey guy</b>,<br><i> We come from the Website <a href='https://job-hunting-vn.herokuapp.com/home'>TopWorkRocker</a></i><br><span> "
				+ content + ".</span>" + "<br><a href='" + linkJob + "'>Click here<a>"
				+ "<span> for more details.</span>"
				+ "<div style='text-align:left;'><a href='https://job-hunting-vn.herokuapp.com/home'><img src='https://res.cloudinary.com/hoadaica/image/upload/v1634898297/freelancer4-e1531398303434_vu5puk.jpg'"
				+ " alt='Rocket Team' title='Rocket Team' style='width:700px;height:400px;'></img></a></div>";
		helper.setText(text, true);

		javaMailSender.send(msg);
	}

}
