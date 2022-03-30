package com.email;

import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

public class SendEmail {

	public static void main(String[] args) {
		sendEmail("vvpmayur@gmail.com", "test");
	}

	public static String sendEmail(String toEmail, String message) {
		Email from = new Email("bhoomuka511@gmail.com");
		Email to = new Email(toEmail);
		String subject = "Stock Pal Reset Password";
		Content content = new Content("text/plain", message);
		Mail mail = new Mail(from, subject, to, content);
		SendGrid sg = new SendGrid("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");
		Request request = new Request();
		try {
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			Response response = sg.api(request);
			System.out.println(response.getStatusCode());
			System.out.println(response.getBody());
			System.out.println(response.getHeaders());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}
}
