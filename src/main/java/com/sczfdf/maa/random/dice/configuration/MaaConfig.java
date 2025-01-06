package com.sczfdf.maa.random.dice.configuration;

import cn.hutool.extra.spring.SpringUtil;
import io.github.hanhuoer.maa.Maa;
import io.github.hanhuoer.maa.core.AdbController;
import io.github.hanhuoer.maa.core.base.Controller;
import io.github.hanhuoer.maa.core.base.Resource;
import io.github.hanhuoer.maa.core.base.Tasker;
import io.github.hanhuoer.maa.core.custom.Context;
import io.github.hanhuoer.maa.core.custom.CustomAction;
import io.github.hanhuoer.maa.core.custom.CustomRecognition;
import io.github.hanhuoer.maa.jna.MaaToolkit;
import io.github.hanhuoer.maa.model.AdbInfo;
import io.github.hanhuoer.maa.util.FileUtils;
import io.github.hanhuoer.spring.boot.MaaProperties;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

/**
 * @author Created by cgb
 */
@Configuration
@ComponentScan(basePackages = "com.sczfdf.maa.random.dice.configuration") // 确保此包路径正确
public class MaaConfig {
    private final ApplicationContext applicationContext;

    @Autowired
    public MaaConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public Tasker getTasker(Resource resource, Controller controller) {
        Tasker tasker = new Tasker();
        tasker.bind(resource, controller);

        return tasker;
    }

    @Bean
    public Controller controller(Maa maa) {
        List<AdbInfo> adbInfoList = AdbController.find();
        if (adbInfoList.isEmpty()) {
            throw new RuntimeException("[getTasker] no adb info found.");
        }

        AdbController adbController = new AdbController(adbInfoList.get(0));
        adbController.connect();
        if (!adbController.connected()) {
            throw new RuntimeException("[getTasker] field connected failed.");
        }

        return adbController;
    }

    @Bean
    public Resource resource() {
        Resource resource = new Resource();
        resource.load(FileUtils.joinUserDir("resources").getAbsolutePath());
        registerRecognition(resource);
        registerAction(resource);

        return resource;
    }

    private void registerRecognition(Resource resource) {
        Map<String, CustomRecognition> customRecognitionNameAndInfoMap = applicationContext.getBeansOfType(CustomRecognition.class);
        for (Map.Entry<String, CustomRecognition> entry : customRecognitionNameAndInfoMap.entrySet()) {
            resource.registerRecognition(entry.getKey(), entry.getValue());
        }

    }

    private void registerAction(Resource resource) {
        Map<String, CustomAction> customActionNameAndInfoMap = applicationContext.getBeansOfType(CustomAction.class);
        for (Map.Entry<String, CustomAction> entry : customActionNameAndInfoMap.entrySet()) {
            resource.registerAction(entry.getKey(), entry.getValue());
        }
    }
}
