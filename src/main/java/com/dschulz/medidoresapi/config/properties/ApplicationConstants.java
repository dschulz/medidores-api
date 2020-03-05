package com.dschulz.medidoresapi.config.properties;

public interface ApplicationConstants {
	// Spring profiles for development, test and production, see https://www.jhipster.tech/profiles/
    /** Constant <code>SPRING_PROFILE_DEVELOPMENT="dev"</code> */
    String SPRING_PROFILE_DEVELOPMENT = "dev";
    /** Constant <code>SPRING_PROFILE_TEST="test"</code> */
    String SPRING_PROFILE_TEST = "test";
    /** Constant <code>SPRING_PROFILE_PRODUCTION="prod"</code> */
    String SPRING_PROFILE_PRODUCTION = "prod";
    /** Spring profile used when deploying with Spring Cloud (used when deploying to CloudFoundry)
        Constant <code>SPRING_PROFILE_CLOUD="cloud"</code> */
    String SPRING_PROFILE_CLOUD = "cloud";
    /** Spring profile used when deploying to Heroku
        Constant <code>SPRING_PROFILE_HEROKU="heroku"</code> */
    String SPRING_PROFILE_HEROKU = "heroku";
    /** Spring profile used when deploying to Amazon ECS
        Constant <code>SPRING_PROFILE_AWS_ECS="aws-ecs"</code> */
    String SPRING_PROFILE_AWS_ECS = "aws-ecs";
    /** Spring profile used to enable swagger
        Constant <code>SPRING_PROFILE_SWAGGER="swagger"</code> */
    String SPRING_PROFILE_SWAGGER = "swagger";
    /** Spring profile used to disable running liquibase
        Constant <code>SPRING_PROFILE_NO_LIQUIBASE="no-liquibase"</code> */
    String SPRING_PROFILE_NO_LIQUIBASE = "no-liquibase";
    /** Spring profile used when deploying to Kubernetes and OpenShift
        Constant <code>SPRING_PROFILE_K8S="k8s"</code> */
    String SPRING_PROFILE_K8S = "k8s";

}
