package com.sczfdf.maa.random.dice.configuration;

import io.github.hanhuoer.maa.Maa;
import io.github.hanhuoer.maa.MaaOptions;
import io.github.hanhuoer.maa.consts.MaaLoggingLevelEunm;
import io.github.hanhuoer.maa.util.StringUtils;
import io.github.hanhuoer.spring.boot.MaaProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
@EnableConfigurationProperties({MaaProperties.class})
public class MaaAutoConfiguration {
    private static final Logger log = LoggerFactory.getLogger(MaaAutoConfiguration.class);

    public MaaAutoConfiguration() {
    }

    @Bean(
        name = {"maa"}
    )
    @ConditionalOnMissingBean({Maa.class})
    public Maa maa(MaaProperties properties) {
        properties.setStdoutLevel(MaaLoggingLevelEunm.WARN);
        properties.setRecording(true);
        properties.setSaveDraw(true);
//        properties.setStdoutLevel(MaaLoggingLevelEunm.INFO);
        MaaOptions options = this.toOptions(properties);
        log.debug("Maa options: {}", options);
        return Maa.create(options);
    }

    private MaaOptions toOptions(MaaProperties properties) {
        MaaOptions options = new MaaOptions();
        if (properties == null) {
            return options;
        } else {
            if (StringUtils.hasText(properties.getAgentDir())) {
                options.setAgentDir(properties.getAgentDir());
            }

            if (StringUtils.hasText(properties.getLibDir())) {
                options.setLibDir(properties.getLibDir());
            }

            if (StringUtils.hasText(properties.getLogDir())) {
                options.setLogDir(properties.getLogDir());
            }

            if (Objects.nonNull(properties.getSaveDraw())) {
                options.setSaveDraw(properties.getSaveDraw());
            }

            if (Objects.nonNull(properties.getRecording())) {
                options.setRecording(properties.getRecording());
            }

            if (Objects.nonNull(properties.getShowHitDraw())) {
                options.setShowHitDraw(properties.getShowHitDraw());
            }

            if (Objects.nonNull(properties.getDebugMessage())) {
                options.setDebugMessage(properties.getDebugMessage());
            }

            if (Objects.nonNull(properties.getKillAdbOnShutdown())) {
                options.setKillAdbOnShutdown(properties.getKillAdbOnShutdown());
            }

            if (Objects.nonNull(properties.getStdoutLevel())) {
                options.setStdoutLevel(properties.getStdoutLevel());
            }

            return options;
        }
    }
}
