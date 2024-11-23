package com.thomasvitale.ai.spring;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.instrumentation.logback.appender.v1_0.OpenTelemetryAppender;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RagAdvanced {

    public static void main(String[] args) {
        SpringApplication.run(RagAdvanced.class, args);
    }

    @Bean
    ApplicationListener<ApplicationReadyEvent> logbackOtelAppenderInitializer(OpenTelemetry openTelemetry) {
        return _ -> OpenTelemetryAppender.install(openTelemetry);
    }

}
