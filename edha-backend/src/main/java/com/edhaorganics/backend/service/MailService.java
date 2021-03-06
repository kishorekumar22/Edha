package com.edhaorganics.backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.thymeleaf.context.Context;

import com.edhaorganics.backend.beans.EdhaUser;
import com.edhaorganics.backend.beans.Order;
import com.edhaorganics.backend.repo.OrderRepository;
import com.edhaorganics.backend.repo.UserRepository;
import com.edhaorganics.backend.util.MailContentBuilder;

@Service
public class MailService {

	private static final String ORDER_EDHAORGANICS_COM = "mail4mycompany@gmail.com";

	private static final String ORDER_COPY_EMAIL = "kishorekumarirtt@gmail.com";

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private OrderRepository orderRepo;

	@Autowired
	private MailContentBuilder builder;

	@Autowired
	private UserRepository userRepo;

	@Async
	public void sendOrderConfirmation(Long orderId) {
		sendOrderInMail(orderId, "Order Confirmation");
	}

	private void sendOrderInMail(Long orderId, String mailMessage) {
		List<String> recipients = new ArrayList<>();
		Order orderPlaced = orderRepo.getOne(orderId);
		if (orderPlaced != null && !StringUtils.isEmpty(orderPlaced.getUser().getEmailId())) {
			recipients.add(orderPlaced.getUser().getEmailId());
		}
		if (orderPlaced != null && !StringUtils.isEmpty(orderPlaced.getCustomer().getEmailId())) {
			recipients.add(orderPlaced.getCustomer().getEmailId());
		}
		recipients.add(ORDER_COPY_EMAIL);
		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(ORDER_EDHAORGANICS_COM);
			helper.setBcc(recipients.toArray(new String[recipients.size()]));
			helper.setSubject("Edha Organics - Welcome User");
			Context context = new Context();
			context.setVariable("order", orderPlaced);
			context.setVariable("message", mailMessage);
			helper.setText(builder.build(context, "invoice.html"), true);
			mailSender.send(message);
		} catch (MessagingException | MailException e) {
			System.out.println("Exception in mailing:" + e.getMessage());
		}
	}

	@Async
	public void sendPaymentUpdate(Long orderId) {
		sendOrderInMail(orderId, "Payment Update");

	}

	@Async
	public void sendWelcomeMail(String username) {
		List<String> recipients = new ArrayList<>();
		Optional<EdhaUser> newUserCreated = userRepo.findById(username);
		EdhaUser newUser = newUserCreated.isPresent() ? newUserCreated.get() : null;
		if (newUser != null && !StringUtils.isEmpty(newUser.getEmailId())) {
			recipients.add(newUser.getEmailId());
		}
		if (newUser != null && newUser.getCreatedBy() != null
				&& !StringUtils.isEmpty(newUser.getCreatedBy().getEmailId())) {
			recipients.add(newUser.getCreatedBy().getEmailId());
		}
		recipients.add(ORDER_COPY_EMAIL);
		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(ORDER_EDHAORGANICS_COM);
			helper.setBcc(recipients.toArray(new String[recipients.size()]));
			helper.setSubject("Welcome to Edha Organics - " + newUser.getFullName() + " !!");
			Context context = new Context();
			context.setVariable("user", newUser);
			helper.setText(builder.build(context, "welcome.html"), true);
			mailSender.send(message);
		} catch (MessagingException | MailException e) {
			System.out.println("Exception in mailing:" + e.getMessage());
		}
	}
}
