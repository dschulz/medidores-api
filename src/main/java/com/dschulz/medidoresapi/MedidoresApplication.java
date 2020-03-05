package com.dschulz.medidoresapi;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

import com.dschulz.medidoresapi.config.DefaultProfileUtil;
import com.dschulz.medidoresapi.config.properties.ApplicationConstants;

@SpringBootApplication
@ComponentScan(basePackages = {"com.dschulz.medidoresapi", "com.dschulz.medidoresapi.config", "com.dschulz.medidoresapi.config.properties"})

public class MedidoresApplication {

    private static final Logger log = LoggerFactory.getLogger(MedidoresApplication.class);
    private final Environment env;

    public MedidoresApplication(Environment env) {
    	this.env = env;
    }
	
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication( MedidoresApplication.class);
		DefaultProfileUtil.addDefaultProfile(app);
        Environment env = app.run(args).getEnvironment();
        logApplicationStartup(env);
	}

    /**
     * Inicializa la aplicación.
     * <p>
     * Spring profiles can be configured with a program argument --spring.profiles.active=your-active-profile
     * <p>
     * You can find more information on how profiles work with JHipster on <a href="https://www.jhipster.tech/profiles/">https://www.jhipster.tech/profiles/</a>.
     */
    @PostConstruct
    public void initApplication() {
        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
        if (activeProfiles.contains(ApplicationConstants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(ApplicationConstants.SPRING_PROFILE_PRODUCTION)) {
            log.error("No se puede ejecutar la aplicación con ambos perfiles 'dev' y 'prod' simultáneamente.");
        }
        if (activeProfiles.contains(ApplicationConstants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(ApplicationConstants.SPRING_PROFILE_CLOUD)) {
            log.error("No se puede ejecutar la aplicación con ambos perfiles 'dev' y 'cloud' simultáneamente.");

        }
    }
    
    private static void logApplicationStartup(Environment env) {
        String protocol = "http";
        if (env.getProperty("server.ssl.key-store") != null) {
            protocol = "https";
        }
        String serverPort = env.getProperty("server.port");
        String contextPath = env.getProperty("server.servlet.context-path");
        if (StringUtils.isBlank(contextPath)) {
            contextPath = "/";
        }
        String hostAddress = "localhost";
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.warn("El nombre de host no se pudo determinar, usando `localhost`");
        }
        log.info("\n----------------------------------------------------------\n\t" +
                "'{}' está en ejecución! URLs:\n\t" +
                "Local: \t\t{}://localhost:{}{}\n\t" +
                "Externa: \t{}://{}:{}{}\n\t" +
                "Profile(s): \t{}\n----------------------------------------------------------",
            env.getProperty("spring.application.name"),
            protocol,
            serverPort,
            contextPath,
            protocol,
            hostAddress,
            serverPort,
            contextPath,
            env.getActiveProfiles());
    }
	
	
}
