package com.decentralizer.spreadr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.blockhound.BlockHound;
import reactor.tools.agent.ReactorDebugAgent;

import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.UUID;

@SpringBootApplication
public class SpreadrApplication {

    public static final String INSTANCE_ID;
    public static final String APPLICATION_LINEAR_HISTORY_KAFKA_TOPIC = "dbtopic";

    static {
        INSTANCE_ID = Base64.getEncoder()
                .encodeToString(
                        (System.getenv("INSTANCE")
                                + "_runtimeId:" + UUID.randomUUID()
                                + "_runtimeZonedDateTime:"
                                + ZonedDateTime.now().toString())
                                .getBytes()
                );

        BlockHound.install();
    }

    public static void main(String[] args) {
        ReactorDebugAgent.init();
        SpringApplication.run(SpreadrApplication.class, args);
    }

}
