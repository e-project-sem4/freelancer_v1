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
		helper.setFrom(new InternetAddress(from, "TopWork Company"));
		helper.setTo(to);
		helper.setSubject("Top Work For You!");
		String text = "<style type='text/css'>" +
				"      table, td { color: #000000; } a { color: #0000ee; text-decoration: underline; } @media (max-width: 480px) { #u_content_menu_5 .v-padding { padding: 16px 18px 10px !important; } #u_content_text_4 .v-text-align { text-align: center !important; } #u_content_text_12 .v-text-align { text-align: left !important; } #u_content_text_19 .v-text-align { text-align: left !important; } #u_content_image_10 .v-src-width { width: 100% !important; } #u_content_image_10 .v-src-max-width { max-width: 100% !important; } #u_content_text_30 .v-text-align { text-align: center !important; } #u_content_text_33 .v-text-align { text-align: center !important; } #u_content_text_31 .v-text-align { text-align: center !important; } #u_content_text_34 .v-text-align { text-align: center !important; } #u_content_text_29 .v-text-align { text-align: center !important; } #u_content_text_32 .v-text-align { text-align: center !important; } }" +
				"@media only screen and (min-width: 620px) {" +
				"  .u-row {" +
				"    width: 600px !important;" +
				"  }" +
				"  .u-row .u-col {" +
				"    vertical-align: top;" +
				"  }" +
				"" +
				"  .u-row .u-col-33p33 {" +
				"    width: 199.98px !important;" +
				"  }" +
				"" +
				"  .u-row .u-col-50 {" +
				"    width: 300px !important;" +
				"  }" +
				"" +
				"  .u-row .u-col-100 {" +
				"    width: 600px !important;" +
				"  }" +
				"" +
				"}" +
				"" +
				"@media (max-width: 620px) {" +
				"  .u-row-container {" +
				"    max-width: 100% !important;" +
				"    padding-left: 0px !important;" +
				"    padding-right: 0px !important;" +
				"  }" +
				"  .u-row .u-col {" +
				"    min-width: 320px !important;" +
				"    max-width: 100% !important;" +
				"    display: block !important;" +
				"  }" +
				"  .u-row {" +
				"    width: calc(100% - 40px) !important;" +
				"  }" +
				"  .u-col {" +
				"    width: 100% !important;" +
				"  }" +
				"  .u-col > div {" +
				"    margin: 0 auto;" +
				"  }" +
				"}" +
				"body {" +
				"  margin: 0;" +
				"  padding: 0;" +
				"}" +
				"" +
				"table," +
				"tr," +
				"td {" +
				"  vertical-align: top;" +
				"  border-collapse: collapse;" +
				"}" +
				"" +
				"p {" +
				"  margin: 0;" +
				"}" +
				"" +
				".ie-container table," +
				".mso-container table {" +
				"  table-layout: fixed;" +
				"}" +
				"" +
				"* {" +
				"  line-height: inherit;" +
				"}" +
				"" +
				"a[x-apple-data-detectors='true'] {" +
				"  color: inherit !important;" +
				"  text-decoration: none !important;" +
				"}" +
				"" +
				"</style>" +
				"  " +
				"  " +
				"" +
				"<!--[if !mso]><!--><link href='https://fonts.googleapis.com/css?family=Cabin:400,700&display=swap' rel='stylesheet' type='text/css'><!--<![endif]-->" +
				"" +
				"" +
				"" +
				"<body class='clean-body u_body' style='margin: 0;padding: 0;-webkit-text-size-adjust: 100%;background-color: #ffffff;color: #000000'>" +
				"  <!--[if IE]><div class='ie-container'><![endif]-->" +
				"  <!--[if mso]><div class='mso-container'><![endif]-->" +
				"  <table style='border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;min-width: 320px;Margin: 0 auto;background-color: #ffffff;width:100%' cellpadding='0' cellspacing='0'>" +
				"  <tbody>" +
				"  <tr style='vertical-align: top'>" +
				"    <td style='word-break: break-word;border-collapse: collapse !important;vertical-align: top'>" +
				"    <!--[if (mso)|(IE)]><table width='100%' cellpadding='0' cellspacing='0' border='0'><tr><td align='center' style='background-color: #ffffff;'><![endif]-->" +
				"    " +
				"" +
				"<div class='u-row-container' style='padding: 0px;background-color: transparent'>" +
				"  <div class='u-row' style='Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: transparent;'>" +
				"    <div style='border-collapse: collapse;display: table;width: 100%;background-color: transparent;'>" +
				"      <!--[if (mso)|(IE)]><table width='100%' cellpadding='0' cellspacing='0' border='0'><tr><td style='padding: 0px;background-color: transparent;' align='center'><table cellpadding='0' cellspacing='0' border='0' style='width:600px;'><tr style='background-color: transparent;'><![endif]-->" +
				"      " +
				"<!--[if (mso)|(IE)]><td align='center' width='600' style='width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;' valign='top'><![endif]-->" +
				"<div class='u-col u-col-100' style='max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;'>" +
				"  <div style='width: 100% !important;'>" +
				"  <!--[if (!mso)&(!IE)]><!--><div style='padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;'><!--<![endif]-->" +
				"  " +
				"<table style='font-family:arial,helvetica,sans-serif;' role='presentation' cellpadding='0' cellspacing='0' width='100%' border='0'>" +
				"  <tbody>" +
				"    <tr>" +
				"      <td style='overflow-wrap:break-word;word-break:break-word;padding:21px 10px 20px;font-family:arial,helvetica,sans-serif;' align='left'>" +
				"        " +
				"<table width='100%' cellpadding='0' cellspacing='0' border='0'>" +
				"  <tr>" +
				"    " +
				"  </tr>" +
				"</table>" +
				"" +
				"      </td>" +
				"    </tr>" +
				"  </tbody>" +
				"</table>" +
				"" +
				"  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->" +
				"  </div>" +
				"</div>" +
				"<!--[if (mso)|(IE)]></td><![endif]-->" +
				"      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->" +
				"    </div>" +
				"  </div>" +
				"</div>" +
				"" +
				"" +
				"" +
				"<div class='u-row-container' style='padding: 0px;background-color: transparent'>" +
				"  <div class='u-row' style='Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #7e8c8d;'>" +
				"    <div style='border-collapse: collapse;display: table;width: 100%;background-image: url(\"https://res.cloudinary.com/hoadaica/image/upload/v1635932841/image-11_mrrqt3.png\");background-repeat: no-repeat;background-position: center top;background-color: transparent;'>" +
				"      <!--[if (mso)|(IE)]><table width='100%' cellpadding='0' cellspacing='0' border='0'><tr><td style='padding: 0px;background-color: transparent;' align='center'><table cellpadding='0' cellspacing='0' border='0' style='width:600px;'><tr style='background-image: url('images/image-11.png');background-repeat: no-repeat;background-position: center top;background-color: #7e8c8d;'><![endif]-->" +
				"      " +
				"<!--[if (mso)|(IE)]><td align='center' width='600' style='width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;' valign='top'><![endif]-->" +
				"<div class='u-col u-col-100' style='max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;'>" +
				"  <div style='width: 100% !important;'>" +
				"  <!--[if (!mso)&(!IE)]><!--><div style='padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;'><!--<![endif]-->" +
				"  " +
				"<table id='u_content_menu_5' style='font-family:arial,helvetica,sans-serif;' role='presentation' cellpadding='0' cellspacing='0' width='100%' border='0'>" +
				"  <tbody>" +
				"    <tr>" +
				"      <td style='overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;' align='left'>" +
				"        " +
				"<div class='menu' style='text-align:center'>" +
				"<!--[if (mso)|(IE)]><table role='presentation' border='0' cellpadding='0' cellspacing='0' align='center'><tr><![endif]-->" +
				"" +
				"  <!--[if (mso)|(IE)]><td style='padding:16px 35px 10px'><![endif]-->" +
				"  " +
				"" +
				"  <!--[if (mso)|(IE)]></td><![endif]-->" +
				"  " +
				"" +
				"  <!--[if (mso)|(IE)]><td style='padding:16px 35px 10px'><![endif]-->" +
				"   " +
				"  <span style='padding:16px 35px 10px;display:inline-block;color:#ecf0f1;font-family:helvetica,sans-serif;font-size:15px' class='v-padding'>" +
				"     <a href='https://job-hunting-vn.herokuapp.com/job-list' target='_blank' style='color:#00d09c;text-decoration: none;'>Find Job</a>" +
				"  </span>" +
				"  " +
				"  <!--[if (mso)|(IE)]></td><![endif]-->" +
				"  " +
				"" +
				"  <!--[if (mso)|(IE)]><td style='padding:16px 35px 10px'><![endif]-->" +
				"  " +
				"  <span style='padding:16px 35px 10px;display:inline-block;color:#ecf0f1;font-family:helvetica,sans-serif;font-size:15px' class='v-padding'>" +
				"    <a href='https://job-hunting-vn.herokuapp.com/post-a-job' target='_blank' style='color:#00d09c;text-decoration: none;'>Post a Job</a>" +
				"  </span>" +
				"  " +
				"  <!--[if (mso)|(IE)]></td><![endif]-->" +
				"  " +
				"" +
				"<!--[if (mso)|(IE)]></tr></table><![endif]-->" +
				"</div>" +
				"" +
				"      </td>" +
				"    </tr>" +
				"  </tbody>" +
				"</table>" +
				"" +
				"<table id='u_content_text_4' style='font-family:arial,helvetica,sans-serif;' role='presentation' cellpadding='0' cellspacing='0' width='100%' border='0'>" +
				"  <tbody>" +
				"    <tr>" +
				"      <td style='overflow-wrap:break-word;word-break:break-word;padding:23px 10px 10px;font-family:arial,helvetica,sans-serif;' align='left'>" +
				"        " +
				"  <div class='v-text-align' style='color: #ffffff; line-height: 150%; text-align: center; word-wrap: break-word;'>" +
				"    <p style='font-size: 14px; line-height: 150%;'><span style='font-family: Cabin, sans-serif; font-size: 14px; line-height: 21px;'><span style='font-size: 28px; line-height: 42px;'><span style='line-height: 42px; font-size: 28px;'>We're happy </span></span></span></p>" +
				"<p style='font-size: 14px; line-height: 150%;'><span style='font-family: Cabin, sans-serif; font-size: 14px; line-height: 21px;'><span style='font-size: 28px; line-height: 42px;'><span style='line-height: 42px; font-size: 28px;'>you're here.</span></span></span></p>" +
				"  </div>" +
				"" +
				"      </td>" +
				"    </tr>" +
				"  </tbody>" +
				"</table>" +
				"" +
				"<table style='font-family:arial,helvetica,sans-serif;' role='presentation' cellpadding='0' cellspacing='0' width='100%' border='0'>" +
				"  <tbody>" +
				"    <tr>" +
				"      <td style='overflow-wrap:break-word;word-break:break-word;padding:0px 10px 10px;font-family:arial,helvetica,sans-serif;' align='left'>" +
				"        " +
				"  <div class='v-text-align' style='color: #ffffff; line-height: 160%; text-align: center; word-wrap: break-word;'>" +
				"<p style='font-size: 14px; line-height: 160%;'><span style='font-size: 14px; line-height: 22.4px;'>Search or post jobs that match your needs!</span></p>" +
				"  </div>" +
				"" +
				"      </td>" +
				"    </tr>" +
				"  </tbody>" +
				"</table>" +
				"" +
				"<table style='font-family:arial,helvetica,sans-serif;' role='presentation' cellpadding='0' cellspacing='0' width='100%' border='0'>" +
				"  <tbody>" +
				"    <tr>" +
				"      <td style='overflow-wrap:break-word;word-break:break-word;padding:30px 10px 43px;font-family:arial,helvetica,sans-serif;' align='left'>" +
				"        " +
				"<div class='v-text-align' align='center'>" +
				"  <!--[if mso]><table width='100%' cellpadding='0' cellspacing='0' border='0' style='border-spacing: 0; border-collapse: collapse; mso-table-lspace:0pt; mso-table-rspace:0pt;font-family:arial,helvetica,sans-serif;'><tr><td class='v-text-align' style='font-family:arial,helvetica,sans-serif;' align='center'><v:roundrect xmlns:v='urn:schemas-microsoft-com:vml' xmlns:w='urn:schemas-microsoft-com:office:word' href='' style='height:40px; v-text-anchor:middle; width:160px;' arcsize='2.5%' stroke='f' fillcolor='#00d09c'><w:anchorlock/><center style='color:#FFFFFF;font-family:arial,helvetica,sans-serif;'><![endif]-->" +
				"    <a href='https://job-hunting-vn.herokuapp.com/' target='_blank' style='box-sizing: border-box;display: inline-block;font-family:arial,helvetica,sans-serif;text-decoration: none;-webkit-text-size-adjust: none;text-align: center;color: #FFFFFF; background-color: #00d09c; border-radius: 1px;-webkit-border-radius: 1px; -moz-border-radius: 1px; width:auto; max-width:100%; overflow-wrap: break-word; word-break: break-word; word-wrap:break-word; mso-border-alt: none;'>" +
				"      <span class='v-padding' style='display:block;padding:12px 30px;line-height:120%;'>Go Home<br /></span>" +
				"    </a>" +
				"  <!--[if mso]></center></v:roundrect></td></tr></table><![endif]-->" +
				"</div>" +
				"" +
				"      </td>" +
				"    </tr>" +
				"  </tbody>" +
				"</table>" +
				"" +
				"  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->" +
				"  </div>" +
				"</div>" +
				"<!--[if (mso)|(IE)]></td><![endif]-->" +
				"      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->" +
				"    </div>" +
				"  </div>" +
				"</div>" +
				"" +
				"" +
				"" +
				"<div class='u-row-container' style='padding: 0px;background-color: transparent'>" +
				"  <div class='u-row' style='Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #e4f1ec;'>" +
				"    <div style='border-collapse: collapse;display: table;width: 100%;background-color: transparent;'>" +
				"      <!--[if (mso)|(IE)]><table width='100%' cellpadding='0' cellspacing='0' border='0'><tr><td style='padding: 0px;background-color: transparent;' align='center'><table cellpadding='0' cellspacing='0' border='0' style='width:600px;'><tr style='background-color: #f6fefb;'><![endif]-->" +
				"      " +
				"<!--[if (mso)|(IE)]><td align='center' width='300' style='width: 300px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;' valign='top'><![endif]-->" +
				"<div class='u-col u-col-50' style='max-width: 320px;min-width: 300px;display: table-cell;vertical-align: top;'>" +
				"  <div style='width: 100% !important; padding: 10px;'>" +
				"  <!--[if (!mso)&(!IE)]><!--><div style='padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;'><!--<![endif]-->" +
				"" +
				"  " +
				"  <br>" +
				"    <b style='font-family: Cabin, sans-serif; font-size: 14px; line-height: 28px;'>Hey guys</b>,<br><i style='font-family: Cabin, sans-serif; font-size: 14px; line-height: 28px;'> We come from <a href='https://job-hunting-vn.herokuapp.com/home' target='_blank' style='color:#00d09c; text-decoration: none;' >TopWorker</a>.</i><br>" +
				"    <span style=' text-decoration: none; font-family: Cabin, sans-serif; font-size: 14px; line-height: 28px;'> "+content+" </span>" +
				"    <br><a href='"+linkJob+"' style='color:#00d09c; text-decoration: none; font-family: Cabin, sans-serif; font-size: 14px; line-height: 28px;' >Click here</a><span> for more details.</span>" +
				"    <br>" +

				"    " +
				"  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->" +
				"  </div>" +
				"</div>" +
				"" +
				"    </div>" +"<div style='text-align:center;'><span style=' text-decoration: none; font-family: Cabin, sans-serif; font-size: 18px; line-height: 28px;'><i><b>Thank you for using our service!</b></i></span></div><br><br>"+
				"  </div>" +
				"</div>" +
				"" +
				"" +
				"" +
				"" +
				"" +
				"" +
				"" +
				"" +
				"" +
				"" +
				"" +
				"" +
				"" +
				"<div class='u-row-container' style='padding: 0px;background-color: transparent'>" +
				"  <div class='u-row' style='Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: transparent;'>" +
				"    <div style='border-collapse: collapse;display: table;width: 100%;background-color: transparent;'>" +
				"      <!--[if (mso)|(IE)]><table width='100%' cellpadding='0' cellspacing='0' border='0'><tr><td style='padding: 0px;background-color: transparent;' align='center'><table cellpadding='0' cellspacing='0' border='0' style='width:600px;'><tr style='background-color: transparent;'><![endif]-->" +
				"      " +
				"<!--[if (mso)|(IE)]><td align='center' width='600' style='width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;' valign='top'><![endif]-->" +
				"<div class='u-col u-col-100' style='max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;'>" +
				"  <div style='width: 100% !important;'>" +
				"  <!--[if (!mso)&(!IE)]><!--><div style='padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;'><!--<![endif]-->" +
				"  " +
				"<table id='u_content_image_10' style='font-family:arial,helvetica,sans-serif;' role='presentation' cellpadding='0' cellspacing='0' width='100%' border='0'>" +
				"  <tbody>" +
				"    <tr>" +
				"      <td style='overflow-wrap:break-word;word-break:break-word;padding:0px;font-family:arial,helvetica,sans-serif;' align='left'>" +
				"        " +
				"<table width='100%' cellpadding='0' cellspacing='0' border='0'>" +
				"  <tr>" +
				"    <td class='v-text-align' style='padding-right: 0px;padding-left: 0px;' align='center'>" +
				"      " +
				"      <img align='center' border='0' src='https://phumyhung.vn/cardinalcourt/wp-content/uploads/2021/03/thank-you-page.jpg' alt='Image' title='Image' style='outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: inline-block !important;border: none;height: auto;float: none;width: 100%;max-width: 600px;' width='600' class='v-src-width v-src-max-width'/>" +
				"      " +
				"    </td>" +
				"  </tr>" +
				"</table>" +
				"" +
				"      </td>" +
				"    </tr>" +
				"  </tbody>" +
				"</table>" +
				"" +
				"  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->" +
				"  </div>" +
				"</div>" +
				"<!--[if (mso)|(IE)]></td><![endif]-->" +
				"      <!--[if (mso)|(IE)]></tr></table></td></tr></table><![endif]-->" +
				"    </div>" +
				"  </div>" +
				"</div>" +
				"" +
				"" +
				"" +
				"" +
				"<div class='u-row-container' style='padding: 0px;background-color: transparent'>" +
				"  <div class='u-row' style='Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #e4faf4;'>" +
				"    <div style='border-collapse: collapse;display: table;width: 100%;background-color: transparent;'>" +
				"      <!--[if (mso)|(IE)]><table width='100%' cellpadding='0' cellspacing='0' border='0'><tr><td style='padding: 0px;background-color: transparent;' align='center'><table cellpadding='0' cellspacing='0' border='0' style='width:600px;'><tr style='background-color: #e4faf4;'><![endif]-->" +
				"      " +
				"<!--[if (mso)|(IE)]><td align='center' width='600' style='width: 600px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;' valign='top'><![endif]-->" +
				"<div class='u-col u-col-100' style='max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;'>" +
				"  <div style='width: 100% !important;'>" +
				"  <!--[if (!mso)&(!IE)]><!--><div style='padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;'><!--<![endif]-->" +
				"  " +
				"<table style='font-family:arial,helvetica,sans-serif;' role='presentation' cellpadding='0' cellspacing='0' width='100%' border='0'>" +
				"  <tbody>" +
				"    <tr>" +
				"      <td style='overflow-wrap:break-word;word-break:break-word;padding:45px 10px 10px;font-family:arial,helvetica,sans-serif;' align='left'>" +
				"        " +
				"  <div class='v-text-align' style='line-height: 140%; text-align: center; word-wrap: break-word;'>" +
				"    <p style='font-size: 14px; line-height: 140%;'><span style='font-family: Cabin, sans-serif; font-size: 20px; line-height: 28px;'>Trusted by Millions of People</span></p>" +
				"  </div>" +
				"" +
				"      </td>" +
				"    </tr>" +
				"  </tbody>" +
				"</table>" +
				"<table style='font-family:arial,helvetica,sans-serif;' role='presentation' cellpadding='0' cellspacing='0' width='100%' border='0'>" +
				"  <tbody>" +
				"    <tr>" +
				"      <td style='overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;' align='left'>" +
				"        " +
				"<table width='100%' cellpadding='0' cellspacing='0' border='0'>" +
				"  <tr>" +
				"    <td class='v-text-align' style='padding-right: 0px;padding-left: 0px;' align='center'>" +
				"      " +
				"      <img align='center' border='0' src='https://res.cloudinary.com/hoadaica/image/upload/v1635932919/image-6_y7tyvk.png' alt='Image' title='Image' style='outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: inline-block !important;border: none;height: auto;float: none;width: 60%;max-width: 348px;' width='348' class='v-src-width v-src-max-width'/>" +
				"      <br>" +
				"      <br>" +
				"      <br>" +
				"    </td>" +
				"  </tr>" +
				"</table>" +
				"" +
				"      </td>" +
				"    </tr>" +
				"  </tbody>" +
				"</table>" +
				"  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->" +
				"  </div>" +
				"</div>" +
				"    </div>" +
				"  </div>" +
				"</div>" +
				"" +
				"" +
				"<div class='u-row-container' style='padding: 0px;background-color: transparent'>" +
				"  <div class='u-row' style='Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #d9f7ed;'>" +
				"    <div style='border-collapse: collapse;display: table;width: 100%;background-color: transparent;'>" +
				"      <!--[if (mso)|(IE)]><table width='100%' cellpadding='0' cellspacing='0' border='0'><tr><td style='padding: 0px;background-color: transparent;' align='center'><table cellpadding='0' cellspacing='0' border='0' style='width:600px;'><tr style='background-color: #d9f7ed;'><![endif]-->" +
				"      " +
				"<!--[if (mso)|(IE)]><td align='center' width='200' style='width: 200px;padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;' valign='top'><![endif]-->" +
				"<div class='u-col u-col-33p33' style='max-width: 320px;min-width: 200px;display: table-cell;vertical-align: top;'>" +
				"  <div style='width: 100% !important;'>" +
				"  <!--[if (!mso)&(!IE)]><!--><div style='padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;'><!--<![endif]-->" +
				"  " +
				"<table id='u_content_text_30' style='font-family:arial,helvetica,sans-serif;' role='presentation' cellpadding='0' cellspacing='0' width='100%' border='0'>" +
				"  <tbody>" +
				"    <tr>" +
				"      <td style='overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;' align='left'>" +
				"        " +
				"  <div class='v-text-align' style='color: #00d09c; line-height: 140%; text-align: center; word-wrap: break-word;'>" +
				"    <p style='font-size: 14px; line-height: 140%;'><span style='font-family: Cabin, sans-serif; font-size: 16px; line-height: 22.4px;'>Quick Links</span></p>" +
				"  </div>" +
				"" +
				"      </td>" +
				"    </tr>" +
				"  </tbody>" +
				"</table>" +
				"" +
				"<table id='u_content_text_33' style='font-family:arial,helvetica,sans-serif;' role='presentation' cellpadding='0' cellspacing='0' width='100%' border='0'>" +
				"  <tbody>" +
				"    <tr>" +
				"      <td style='overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;' align='left'>" +
				"        " +
				"  <div class='v-text-align' style='color: #4c5763; line-height: 170%; text-align: center; word-wrap: break-word;'>" +
				"    <p style='font-size: 14px; line-height: 170%;'><span style='font-family: Cabin, sans-serif; font-size: 14px; line-height: 23.8px;'>Latest Job</span></p>" +
				"<p style='font-size: 14px; line-height: 170%;'><span style='font-family: Cabin, sans-serif; font-size: 14px; line-height: 23.8px;'>Help Center</span></p>" +
				"<p style='font-size: 14px; line-height: 170%;'><span style='font-family: Cabin, sans-serif; font-size: 14px; line-height: 23.8px;'>Tour Guide</span></p>" +
				"<p style='font-size: 14px; line-height: 170%;'><span style='font-family: Cabin, sans-serif; font-size: 14px; line-height: 23.8px;'>FAQ's</span></p>" +
				"  </div>" +
				"" +
				"      </td>" +
				"    </tr>" +
				"  </tbody>" +
				"</table>" +
				"" +
				"  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->" +
				"  </div>" +
				"</div>" +
				"" +
				"<div class='u-col u-col-33p33' style='max-width: 320px;min-width: 200px;display: table-cell;vertical-align: top;'>" +
				"  <div style='width: 100% !important;'>" +
				"  <!--[if (!mso)&(!IE)]><!--><div style='padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;'><!--<![endif]-->" +
				"  " +
				"<table id='u_content_text_31' style='font-family:arial,helvetica,sans-serif;' role='presentation' cellpadding='0' cellspacing='0' width='100%' border='0'>" +
				"  <tbody>" +
				"    <tr>" +
				"      <td style='overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;' align='left'>" +
				"        " +
				"  <div class='v-text-align' style='color: #00d09c; line-height: 140%; text-align: center; word-wrap: break-word;'>" +
				"    <p style='font-size: 14px; line-height: 140%;'><span style='font-family: Cabin, sans-serif; font-size: 14px; line-height: 19.6px;'><span style='font-size: 16px; line-height: 22.4px;'>Contact Info</span><br /></span></p>" +
				"  </div>" +
				"" +
				"      </td>" +
				"    </tr>" +
				"  </tbody>" +
				"</table>" +
				"" +
				"<table id='u_content_text_34' style='font-family:arial,helvetica,sans-serif;' role='presentation' cellpadding='0' cellspacing='0' width='100%' border='0'>" +
				"  <tbody>" +
				"    <tr>" +
				"      <td style='overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;' align='left'>" +
				"        " +
				"  <div class='v-text-align' style='color: #4c5763; line-height: 170%; text-align: center; word-wrap: break-word;'>" +
				"    <p style='font-size: 14px; line-height: 170%;'>UK (+12) 456 7890 <br />US (+23) 456 7891</p>" +
				"<p style='font-size: 14px; line-height: 170%;'>topworker@gmail.com</p>" +
				"  </div>" +
				"" +
				"      </td>" +
				"    </tr>" +
				"  </tbody>" +
				"</table>" +
				"" +
				"  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->" +
				"  </div>" +
				"</div>" +
				"" +
				"<div class='u-col u-col-33p33' style='max-width: 320px;min-width: 200px;display: table-cell;vertical-align: top;'>" +
				"  <div style='width: 100% !important;'>" +
				"<div style='padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;'><!--<![endif]-->" +
				"  " +
				"<table id='u_content_text_29' style='font-family:arial,helvetica,sans-serif;' role='presentation' cellpadding='0' cellspacing='0' width='100%' border='0'>" +
				"  <tbody>" +
				"    <tr>" +
				"      <td style='overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;' align='left'>" +
				"        " +
				"  <div class='v-text-align' style='color: #00d09c; line-height: 140%; text-align: center; word-wrap: break-word;'>" +
				"    <p style='font-size: 14px; line-height: 140%;'><span style='font-family: Cabin, sans-serif; font-size: 16px; line-height: 22.4px;'>TOPWORK<br /></span></p>" +
				"  </div>" +
				"" +
				"      </td>" +
				"    </tr>" +
				"  </tbody>" +
				"</table>" +
				"" +
				"<table id='u_content_text_32' style='font-family:arial,helvetica,sans-serif;' role='presentation' cellpadding='0' cellspacing='0' width='100%' border='0'>" +
				"  <tbody>" +
				"    <tr>" +
				"      <td style='overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;' align='left'>" +
				"        " +
				"  <div class='v-text-align' style='color: #4c5763; line-height: 140%; text-align: center; word-wrap: break-word;'>" +
				"    <p style='font-size: 14px; line-height: 140%;'><span style='font-size: 12px; line-height: 16.8px;'>Social:</span></p>" +
				"  </div>" +
				"" +
				"      </td>" +
				"    </tr>" +
				"  </tbody>" +
				"</table>" +
				"" +
				"<table style='font-family:arial,helvetica,sans-serif;' role='presentation' cellpadding='0' cellspacing='0' width='100%' border='0'>" +
				"  <tbody>" +
				"    <tr>" +
				"      <td style='overflow-wrap:break-word;word-break:break-word;padding:10px 20px 10px 10px;font-family:arial,helvetica,sans-serif;' align='left'>" +
				"        " +
				"<div align='center'>" +
				"  <div style='display: table; max-width:140px;'>" +
				"" +
				"    <table align='left' border='0' cellspacing='0' cellpadding='0' width='32' height='32' style='border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;margin-right: 15px'>" +
				"      <tbody><tr style='vertical-align: top'><td align='left' valign='middle' style='word-break: break-word;border-collapse: collapse !important;vertical-align: top'>" +
				"        <a href='https://facebook.com/' title='Facebook' target='_blank'>" +
				"          <img src='https://res.cloudinary.com/hoadaica/image/upload/v1635932949/image-2_b7kw9m.png' alt='Facebook' title='Facebook' width='32' style='outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: block !important;border: none;height: auto;float: none;max-width: 32px !important'>" +
				"        </a>" +
				"      </td></tr>" +
				"    </tbody></table>" +
				"" +
				"    <table align='left' border='0' cellspacing='0' cellpadding='0' width='32' height='32' style='border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;margin-right: 15px'>" +
				"      <tbody><tr style='vertical-align: top'><td align='left' valign='middle' style='word-break: break-word;border-collapse: collapse !important;vertical-align: top'>" +
				"        <a href='https://twitter.com/' title='Twitter' target='_blank'>" +
				"          <img src='https://res.cloudinary.com/hoadaica/image/upload/v1635932966/image-1_a5ejvu.png' alt='Twitter' title='Twitter' width='32' style='outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: block !important;border: none;height: auto;float: none;max-width: 32px !important'>" +
				"        </a>" +
				"      </td></tr>" +
				"    </tbody></table>" +
				"" +
				"    <table align='left' border='0' cellspacing='0' cellpadding='0' width='32' height='32' style='border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;margin-right: 0px'>" +
				"      <tbody><tr style='vertical-align: top'><td align='left' valign='middle' style='word-break: break-word;border-collapse: collapse !important;vertical-align: top'>" +
				"        <a href='https://instagram.com/' title='Instagram' target='_blank'>" +
				"          <img src='https://res.cloudinary.com/hoadaica/image/upload/v1635932982/image-3_tulpef.png' alt='Instagram' title='Instagram' width='32' style='outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: block !important;border: none;height: auto;float: none;max-width: 32px !important'>" +
				"        </a>" +
				"      </td></tr>" +
				"    </tbody></table>" +
				"" +
				"  </div>" +
				"</div>" +
				"      </td>" +
				"    </tr>" +
				"  </tbody>" +
				"</table>" +
				"  <!--[if (!mso)&(!IE)]><!--></div><!--<![endif]-->" +
				"  </div>" +
				"</div>" +
				"    </div>" +
				"  </div>" +
				"</div>" +
				"<div class='u-row-container' style='padding: 0px;background-color: transparent'>" +
				"  <div class='u-row' style='Margin: 0 auto;min-width: 320px;max-width: 600px;overflow-wrap: break-word;word-wrap: break-word;word-break: break-word;background-color: #d9f7ed;'>" +
				"    <div style='border-collapse: collapse;display: table;width: 100%;background-color: transparent;'>" +
				"<div class='u-col u-col-100' style='max-width: 320px;min-width: 600px;display: table-cell;vertical-align: top;'>" +
				"  <div style='width: 100% !important;'>" +
				"<div style='padding: 0px;border-top: 0px solid transparent;border-left: 0px solid transparent;border-right: 0px solid transparent;border-bottom: 0px solid transparent;'><!--<![endif]-->" +
				"<table style='font-family:arial,helvetica,sans-serif;' role='presentation' cellpadding='0' cellspacing='0' width='100%' border='0'>" +
				"  <tbody>" +
				"    <tr>" +
				"      <td style='overflow-wrap:break-word;word-break:break-word;padding:10px;font-family:arial,helvetica,sans-serif;' align='left'>" +
				"  <table height='0px' align='center' border='0' cellpadding='0' cellspacing='0' width='100%' style='border-collapse: collapse;table-layout: fixed;border-spacing: 0;mso-table-lspace: 0pt;mso-table-rspace: 0pt;vertical-align: top;border-top: 0px solid #d9f7ed;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%'>" +
				"    <tbody>" +
				"      <tr style='vertical-align: top'>" +
				"        <td style='word-break: break-word;border-collapse: collapse !important;vertical-align: top;font-size: 0px;line-height: 0px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%'>" +
				"          <span>&#160;</span>" +
				"        </td>" +
				"      </tr>" +
				"    </tbody>" +
				"  </table>" +
				"      </td>" +
				"    </tr>" +
				"  </tbody>" +
				"</table>" +
				"</div>" +
				"  </div>" +
				"</div>" +
				"    </div>" +
				"  </div>" +
				"</div>" +
				"    </td>" +
				"  </tr>" +
				"  </tbody>" +
				"  </table>" +
				" " +
				"</body>";
		helper.setText(text, true);

		javaMailSender.send(msg);
	}




}
