package com.dschulz.medidoresapi.service.impl;





import com.dschulz.medidoresapi.config.properties.ApplicationProperties;
import com.dschulz.medidoresapi.domain.Usuario;
import com.dschulz.medidoresapi.service.MailService;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.MessageSource;
import org.springframework.context.event.EventListener;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

/**
 * Service for sending emails.
 * <p>
 * We use the {@link Async} annotation to send emails asynchronously.
 */
@Service
public class MailServiceImpl implements MailService {
	
	
	@Value("${spring.profiles.active:}")
	private String activeProfiles;

	@Value("${application.mail-reports.notify-on-startup:false}")
	boolean notificarInicio;

	@Value("${application.mail-reports.daily-reports:false}")
	boolean reporteDiario;

	@Value("${application.mail-reports.admins:nobody@nowhere.notld}")
	List<String> admins;
	

    private final Logger log = LoggerFactory.getLogger(MailServiceImpl.class);

    private static final String USER = "user";

    private static final String BASE_URL = "baseUrl";

    private final ApplicationProperties applicationProperties;

    private final JavaMailSender javaMailSender;

    private final MessageSource messageSource;

    private final SpringTemplateEngine templateEngine;

    public MailServiceImpl(ApplicationProperties applicationProperties, JavaMailSender javaMailSender,
            MessageSource messageSource, SpringTemplateEngine templateEngine) {

        this.applicationProperties = applicationProperties;
        this.javaMailSender = javaMailSender;
        this.messageSource = messageSource;
        this.templateEngine = templateEngine;
    }

    @Override
    @Async
    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        log.debug("Enviar email[multipart '{}' y html '{}'] a '{}' con asunto '{}' y contenido={}",
            isMultipart, isHtml, to, subject, content);

        // Prepare message using a Spring helper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, StandardCharsets.UTF_8.name());
            message.setTo(to);
            message.setBcc(admins.toArray(new String[0]));
            message.setFrom(applicationProperties.getMail().getFrom());
            message.setSubject(subject);
            message.setText(content, isHtml);
            
            javaMailSender.send(mimeMessage);
            log.debug("Email enviado a '{}'", to);
        }  catch (MailException | MessagingException e) {
            log.warn("No se pudo enviar email al usuario '{}'", to, e);
        }
    }
    
    
	@Override
	@Async
	public void sendEmailToList(List<String> toList, String subject, String content, boolean isMultipart, boolean isHtml) {
		
		log.debug("Enviar email[multipart '{}' y html '{}'] a '{}' con asunto '{}' y contenido={}",
	            isMultipart, isHtml, toList.stream().sorted().collect(Collectors.joining(", ")) , subject, content);
		
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		
		String [] admins = toList.toArray(new String[0]);
		
		try {
			MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, StandardCharsets.UTF_8.name());
			message.setTo(admins);
            message.setFrom(applicationProperties.getMail().getFrom());
            message.setSubject(subject);
            message.setText(content, true);
			javaMailSender.send(mimeMessage);
			log.debug("Email enviado a '{}'", toList.stream().sorted().collect(Collectors.joining(",")));
		} catch (MessagingException e) {
			throw new RuntimeException("Error enviando email", e);
		}

	}
    

    @Override
    @Async
    public void sendEmailFromTemplate(Usuario user, String templateName, String titleKey) {
        if (user.getEmail() == null) {
            log.debug("Email doesn't exist for user '{}'", user.getNombre()); //user.getLogin()
            return;
        }
        Locale locale = Locale.forLanguageTag("en-US"); //user.getLangKey()
        Context context = new Context(locale);
        context.setVariable(USER, user);
        context.setVariable(BASE_URL, applicationProperties.getMail().getBaseUrl());
        String content = templateEngine.process(templateName, context);
        String subject = messageSource.getMessage(titleKey, null, locale);
        sendEmail(user.getEmail(), subject, content, false, true);
    }

    @Override
    @Async
    public void sendActivationEmail(Usuario user) {
        log.debug("Enviando email de activacion a '{}'", user.getEmail());
        sendEmailFromTemplate(user, "mail/activationEmail", "email.activation.title");
    }

    @Override
    @Async
    public void sendCreationEmail(Usuario user) {
        log.debug("Enviando email de creacion a '{}'", user.getEmail());
        sendEmailFromTemplate(user, "mail/creationEmail", "email.activation.title");
    }

    @Override
    @Async
    public void sendPasswordResetMail(Usuario user) {
        log.debug("Enviando email de reestablecimiento de contraseña a '{}'", user.getEmail());
        sendEmailFromTemplate(user, "mail/passwordResetEmail", "email.reset.title");
    }

	@Override
	@Async
	@EventListener
	public void applicationStarted(ApplicationReadyEvent event) {

		if (notificarInicio) {

			String mensaje = String.format("Hola! La aplicación inició normalmente.<br/>Profiles activos: [%s]", activeProfiles);
			
			this.sendEmailToList(admins, "Aplicación iniciada", mensaje, false, false);

			log.info(String.format("Email enviado a [%s]", String.join(", ", admins)));
		} else {
			log.info("Email de notificacion de inicio de aplicacion desactivado");
		}
	}

	@Override
	@Async
	@Scheduled(cron = "0 04 17 * * *")
	public void sendDailyReport() {

		if (reporteDiario) {
			log.info("Enviando reporte diario...");

			String mensaje = String.format("Hola!<br/>Solo quiero avisar que estoy en ejecución.<br/>Profiles activos: [%s]", activeProfiles);
			
			this.sendEmailToList(admins, "Aplicación esta corriendo", mensaje, false, false);

			log.info(String.format("Reporte diario enviado a [%s]", String.join(", ", admins)));
		} else {
			log.info("Reporte diario desactivado, no se envía email.");
		}

	}

}

