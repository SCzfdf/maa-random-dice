package com.sczfdf.maa.random.dice.custom;

import com.sczfdf.maa.random.dice.bean.RDContext;
import io.github.hanhuoer.maa.core.AdbController;
import io.github.hanhuoer.maa.core.base.Controller;
import io.github.hanhuoer.maa.core.custom.Context;
import io.github.hanhuoer.maa.core.custom.CustomAction;
import jakarta.annotation.Resource;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author cgb
 */
@Slf4j
@Getter
@Component("screenshotAction")
public class ScreenshotAction extends CustomAction {
    @Resource
    private Controller controller;

    private String name;

    public ScreenshotAction() {
        super();
        name = "screenshotAction";
    }

    @Override
    public RunResult run(Context context, RunArg arg) {
        RDContext.setScreenshot(controller.cachedImage());
        log.info("Screenshot action started.");
        return RunResult.success();
    }
}
