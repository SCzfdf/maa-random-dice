package com.sczfdf.maa.random.dice;

import com.sczfdf.maa.random.dice.handler.StartHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


/**
 * @author cgb
 */
@SpringBootApplication(scanBasePackages = {
        "io.github.hanhuoer.spring.boot",
        "com.sczfdf.maa.random.dice"
})
public class MaaRandomDiceApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MaaRandomDiceApplication.class, args);
        StartHandler bean = context.getBean(StartHandler.class);

        bean.start();

//        context.close();

    }

}
