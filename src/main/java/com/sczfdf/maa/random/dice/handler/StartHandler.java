package com.sczfdf.maa.random.dice.handler;

import cn.hutool.core.thread.ThreadUtil;
import com.sczfdf.maa.random.dice.bean.Dice;
import com.sczfdf.maa.random.dice.bean.Node;
import com.sczfdf.maa.random.dice.bean.RDContext;
import com.sczfdf.maa.random.dice.configuration.MaaConfig;
import com.sczfdf.maa.random.dice.custom.RecognitionStonAction;
import com.sczfdf.maa.random.dice.utils.Utils;
import io.github.hanhuoer.maa.core.base.Controller;
import io.github.hanhuoer.maa.core.base.Resource;
import io.github.hanhuoer.maa.core.base.Tasker;
import io.github.hanhuoer.maa.core.util.TaskFuture;
import io.github.hanhuoer.maa.model.TaskDetail;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Created by cgb
 */
@Slf4j
@Component
public class StartHandler {
//    private final Tasker tasker;
    private final Resource resource;
    private final Controller controller;

    private static final List<Dice> diceList = new ArrayList<>();
    private static Node redDiceNode = null;
    private static Node blueDiceNode = null;

    public StartHandler(Resource resource, Controller controller) {
//        this.tasker = tasker;
        this.resource = resource;
        this.controller = controller;
    }


    public void start() {
        List<int[]> ints = RDContext.initDiceList();
        for (int i = 0; i < 15; i++) {
            int[] initDiceCoords = ints.get(i);
            Dice dice = new Dice(initDiceCoords[0], initDiceCoords[1], i);
            diceList.add(dice);
            resource.registerAction("recognition_ston_action_" + i, new RecognitionStonAction(dice));
        }
        redDiceNode = Node.listToCircularLinkedList(diceList);
        blueDiceNode = Node.listToCircularLinkedList(diceList);

//        loopScreenshot();
        new Thread(this::loopScreenshot).start();
        ThreadUtil.sleep(5000);

        for (int i = 0; i < 15; i++) {
            if (i == 1) {
                int finalI = i;
                Utils.executeRecognitionDiceTask(() -> loopRecognitionDice(finalI));
            }
        }

//        new Thread(this::loopSwipe).start();
    }

    private void loopSwipe() {
        while (true) {
            ThreadUtil.sleep(200);

            // Find red dice
            while (true) {
                redDiceNode = redDiceNode.next;
                if ("R".equals(redDiceNode.val.getType())) {
                    break;
                }
                ThreadUtil.sleep(50);
            }

            // Find blue dice
            do {
                blueDiceNode = blueDiceNode.next;
            } while (!"B".equals(blueDiceNode.val.getType()));


            log.info("Swipe");
            int randomInt = 35;
            controller.postSwipe(
                    blueDiceNode.val.getRoiX() + randomInt, blueDiceNode.val.getRoiY() + randomInt,
                    redDiceNode.val.getRoiX() + randomInt, redDiceNode.val.getRoiY() + randomInt,
                    200
            );
        }


    }

    @jakarta.annotation.Resource
    MaaConfig maaConfig;

    @SneakyThrows
    private void loopScreenshot() {
        try (Tasker tasker = new Tasker()){
            tasker.bind(resource, controller);
            while (true) {
                long now = System.currentTimeMillis();
                TaskFuture<TaskDetail> taskFuture = tasker.postPipeline("screenshot",
                        Map.of("screenshot",
                                Map.of("action", "Custom",
                                        "custom_action", "screenshotAction",
                                        "pre_delay", 0,
                                        "post_delay", 0)
                        ));
                taskFuture.wait();
                TaskDetail taskDetail = taskFuture.get();
                log.info("Screenshot time:{}, result:{}", System.currentTimeMillis() - now, taskDetail);
            }
        }
    }

    @SneakyThrows
    private void loopRecognitionDice(int finalI) {
        while (true) {
            TaskFuture<TaskDetail> taskFuture = maaConfig.getTasker(resource, controller).postPipeline("customMonitorDice",
                    Map.of("customMonitorDice",
                            Map.of("action", "Custom",
                                    "custom_action", "recognition_ston_action_" + finalI,
                                    "pre_delay", 0,
                                    "post_delay", 0)
                    ));
            log.info("Recognition dice:{}", taskFuture);
        }
    }

}
