package com.metlife.producer;

import au.com.dius.pact.provider.junit.PactRunner;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactUrl;
import au.com.dius.pact.provider.junit.target.HttpTarget;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;

@RunWith(PactRunner.class)
@Provider("com.metlife.producer")
@PactUrl(urls = {"file:///Users/imrank/scratch/metlife/pact-spike/eclaims/com.metlife.consumer/target/pacts/com.metlife.consumer-com.metlife.producer.json"})
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
