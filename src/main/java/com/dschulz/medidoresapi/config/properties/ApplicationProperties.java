package com.dschulz.medidoresapi.config.properties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;


@Configuration
@ConfigurationProperties(prefix = "application")
public class ApplicationProperties {
	

	private final Async asyc = new Async();
	
	private final Mail mail = new Mail();
	
    private final Http http = new Http();

	
	private final MailReports mailReports = new MailReports();
	
	private final Security security = new Security();
	
    private final CorsConfiguration cors = new CorsConfiguration();

	

	public Async getAsyc() {
		return asyc;
	}

	public Mail getMail() {
		return mail;
	}
	
	public Security getSecurity() {
		return security;
	}
	
	public MailReports getMailReports() {
		return mailReports;
	}


	public Http getHttp() {
		return http;
	}


	public CorsConfiguration getCors() {
		return cors;
	}


	public static class Async {

        private int corePoolSize = ApplicationDefaults.Async.corePoolSize;

        private int maxPoolSize = ApplicationDefaults.Async.maxPoolSize;

        private int queueCapacity = ApplicationDefaults.Async.queueCapacity;

        public int getCorePoolSize() {
            return corePoolSize;
        }

        public void setCorePoolSize(int corePoolSize) {
            this.corePoolSize = corePoolSize;
        }

        public int getMaxPoolSize() {
            return maxPoolSize;
        }

        public void setMaxPoolSize(int maxPoolSize) {
            this.maxPoolSize = maxPoolSize;
        }

        public int getQueueCapacity() {
            return queueCapacity;
        }

        public void setQueueCapacity(int queueCapacity) {
            this.queueCapacity = queueCapacity;
        }
    }
	

	public static class Mail {

		private boolean enabled = ApplicationDefaults.Mail.enabled;
		
        private String from = ApplicationDefaults.Mail.from;

        private String baseUrl = ApplicationDefaults.Mail.baseUrl;
        

		public boolean isEnabled() {
			return enabled;
		}

		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}

		public String getFrom() {
			return from;
		}

		public void setFrom(String from) {
			this.from = from;
		}

		public String getBaseUrl() {
			return baseUrl;
		}

		public void setBaseUrl(String baseUrl) {
			this.baseUrl = baseUrl;
		}

	}
	
	public static class MailReports {
		
		private Boolean dailyReports = ApplicationDefaults.MailReports.dailyReports;
		private Boolean notifyOnStartup = ApplicationDefaults.MailReports.notifyOnStartup;
		private String[] admins = ApplicationDefaults.MailReports.admins;

		public Boolean getDailyReports() {
			return dailyReports;
		}
		public void setDailyReports(Boolean dailyReports) {
			this.dailyReports = dailyReports;
		}
		public Boolean getNotifyOnStartup() {
			return notifyOnStartup;
		}
		public void setNotifyOnStartup(Boolean notifyOnStartup) {
			this.notifyOnStartup = notifyOnStartup;
		}
		public String[] getAdmins() {
			return admins;
		}
		public void setAdmins(String[] admins) {
			this.admins = admins;
		}
	}
	
	
    public static class Http {

        private final Cache cache = new Cache();

        public Cache getCache() {
            return cache;
        }

        public static class Cache {

            private int timeToLiveInDays = ApplicationDefaults.Http.Cache.timeToLiveInDays;

            public int getTimeToLiveInDays() {
                return timeToLiveInDays;
            }

            public void setTimeToLiveInDays(int timeToLiveInDays) {
                this.timeToLiveInDays = timeToLiveInDays;
            }
        }
    }
		
    public static class Security {

        private final ClientAuthorization clientAuthorization = new ClientAuthorization();

        private final Authentication authentication = new Authentication();

        private final RememberMe rememberMe = new RememberMe();

        private final OAuth2 oauth2 = new OAuth2();

        public ClientAuthorization getClientAuthorization() {
            return clientAuthorization;
        }

        public Authentication getAuthentication() {
            return authentication;
        }

        public RememberMe getRememberMe() {
            return rememberMe;
        }

        public OAuth2 getOauth2() {
            return oauth2;
        }

        public static class ClientAuthorization {

            private String accessTokenUri = ApplicationDefaults.Security.ClientAuthorization.accessTokenUri;

            private String tokenServiceId = ApplicationDefaults.Security.ClientAuthorization.tokenServiceId;

            private String clientId = ApplicationDefaults.Security.ClientAuthorization.clientId;

            private String clientSecret = ApplicationDefaults.Security.ClientAuthorization.clientSecret;

            public String getAccessTokenUri() {
                return accessTokenUri;
            }

            public void setAccessTokenUri(String accessTokenUri) {
                this.accessTokenUri = accessTokenUri;
            }

            public String getTokenServiceId() {
                return tokenServiceId;
            }

            public void setTokenServiceId(String tokenServiceId) {
                this.tokenServiceId = tokenServiceId;
            }

            public String getClientId() {
                return clientId;
            }

            public void setClientId(String clientId) {
                this.clientId = clientId;
            }

            public String getClientSecret() {
                return clientSecret;
            }

            public void setClientSecret(String clientSecret) {
                this.clientSecret = clientSecret;
            }
        }

        public static class Authentication {

            private final Jwt jwt = new Jwt();

            public Jwt getJwt() {
                return jwt;
            }

            public static class Jwt {

                private String secret = ApplicationDefaults.Security.Authentication.Jwt.secret;

                private String base64Secret = ApplicationDefaults.Security.Authentication.Jwt.base64Secret;

                private long tokenValidityInSeconds = ApplicationDefaults.Security.Authentication.Jwt
                    .tokenValidityInSeconds;

                private long tokenValidityInSecondsForRememberMe = ApplicationDefaults.Security.Authentication.Jwt
                    .tokenValidityInSecondsForRememberMe;

                public String getSecret() {
                    return secret;
                }

                public void setSecret(String secret) {
                    this.secret = secret;
                }

                public String getBase64Secret() {
                    return base64Secret;
                }

                public void setBase64Secret(String base64Secret) {
                    this.base64Secret = base64Secret;
                }

                public long getTokenValidityInSeconds() {
                    return tokenValidityInSeconds;
                }

                public void setTokenValidityInSeconds(long tokenValidityInSeconds) {
                    this.tokenValidityInSeconds = tokenValidityInSeconds;
                }

                public long getTokenValidityInSecondsForRememberMe() {
                    return tokenValidityInSecondsForRememberMe;
                }

                public void setTokenValidityInSecondsForRememberMe(long tokenValidityInSecondsForRememberMe) {
                    this.tokenValidityInSecondsForRememberMe = tokenValidityInSecondsForRememberMe;
                }
            }
        }

        public static class RememberMe {

            @NotNull
            private String key = ApplicationDefaults.Security.RememberMe.key;

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }
        }

        public static class OAuth2 {
            private List<String> audience = new ArrayList<>();

            public List<String> getAudience() {
                return Collections.unmodifiableList(audience);
            }

            public void setAudience(@NotNull List<String> audience) {
                this.audience.addAll(audience);
            }
        }
    }
	
}
