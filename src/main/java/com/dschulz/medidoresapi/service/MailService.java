package com.dschulz.medidoresapi.service;

import java.util.List;

import org.springframework.boot.context.event.ApplicationReadyEvent;

import com.dschulz.medidoresapi.domain.Usuario;

public interface MailService {
	public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml);
	public void sendEmailToList(List<String> toList, String subject, String message, boolean isMultipart, boolean isHtml );
	public void sendEmailFromTemplate(Usuario user, String templateName, String titleKey);
	public void sendActivationEmail(Usuario user);
	public void sendCreationEmail(Usuario user);
	public void sendPasswordResetMail(Usuario user);
	
	public void applicationStarted(ApplicationReadyEvent event);
	public void sendDailyReport();
}
