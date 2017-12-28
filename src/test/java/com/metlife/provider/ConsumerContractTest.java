package com.metlife.provider;

import au.com.dius.pact.provider.junit.PactRunner;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit.target.HttpTarget;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;

@RunWith(PactRunner.class)
@Provider("com.metlife.provider")
@PactFolder("src/test/resources/pacts")
public class ConsumerContractTest {

    @TestTarget
    public final Target target = new HttpTarget(8080);

    @BeforeClass
    public static void setUpService() {
        //Run DB, create schema
        //Run service
        //...
        SpringApplication.run(Application.class);
    }


    @State("test state")
    public void toTestState() {
        // Prepare service before interaction that require "default" state
        // ...
//        LOGGER.info("Now service in default state");
    }
}
