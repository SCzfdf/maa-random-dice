package com.sczfdf.maa.random.dice.custom;

import cn.hutool.core.util.NumberUtil;
import com.sczfdf.maa.random.dice.bean.Dice;
import com.sczfdf.maa.random.dice.bean.RDContext;
import com.sczfdf.maa.random.dice.utils.Utils;
import io.github.hanhuoer.maa.core.custom.Context;
import io.github.hanhuoer.maa.core.custom.CustomAction;
import io.github.hanhuoer.maa.model.RecognitionDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cgb
 */
@Slf4j
public class RecognitionStonAction extends CustomAction {

    private final Dice monitorDice;
    private Object lastRecognitionImg = null;

    private final String recognitionBlueTitle;
    private final Map<String, Object> recognitionBlue;

    private final String recognitionRedTitle;
    private final Map<String, Object> recognitionRed;

    private final String recognitionOtherTitle1;
    private final Map<String, Object> recognitionOther1;

    private final String recognitionOtherTitle2;
    private final Map<String, Object> recognitionOther2;

    private final String recognitionOtherTitle3;
    private final Map<String, Object> recognitionOther3;

    private final String recognitionOtherTitle4;
    private final Map<String, Object> recognitionOther4;

    private final int[] roiOffset = {10, 20, 10, 10};

    public RecognitionStonAction(Dice monitorDice) {
        this.monitorDice = monitorDice;

        this.recognitionBlueTitle = "recognition_single_blue_ston_" + this.monitorDice.getIndex();
        this.recognitionBlue = new HashMap<>() {{
            put(recognitionBlueTitle, new HashMap<String, Object>() {{
                put("recognition", "TemplateMatch");
//                put("recognition", "FeatureMatch");
                put("roi", new int[]{monitorDice.getRoiX(), monitorDice.getRoiY(), monitorDice.getRoiH(), monitorDice.getRoiW()});
                put("roi_offset", roiOffset);
                put("template", List.of("b7b1.png", "b7b2.png"));
//                put("template", List.of("b1a2.png", "b1b.png", "b2b.png", "b2b2.png", "b3a.png", "b3b.png", "b3b2.png", "b4a1.png",
//                        "b4b.png", "b5a.png", "b5b.png", "b5b1.png", "b5b2.png", "b5b3.png", "b6a.png", "b6b.png",
//                        "b7a.png", "b7b.png", "b7b1.png", "b7b2.png"));
                put("threshold", List.of(0.5, 0.5));
                put("pre_delay", 0);
                put("post_delay", 0);
                put("method", 5);
                put("count", 4);
            }});
        }};

        this.recognitionRedTitle = "recognition_single_red_ston_" + this.monitorDice.getIndex();
        this.recognitionRed = new HashMap<>() {{
            put(recognitionRedTitle, new HashMap<String, Object>() {{
                put("recognition", "TemplateMatch");
//                put("recognition", "FeatureMatch");
                put("roi", new int[]{monitorDice.getRoiX(), monitorDice.getRoiY(), monitorDice.getRoiH(), monitorDice.getRoiW()});
                put("roi_offset", roiOffset);
                put("template", List.of("r1.png", "r2.png", "r3.png", "r4.png", "r5.png", "r6.png", "r7.png"));
                put("threshold", List.of(0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5));
                put("pre_delay", 0);
                put("post_delay", 0);
                put("method", 5);
                put("count", 4);
            }});
        }};

        // 成长
        this.recognitionOtherTitle1 = "recognition_single_other1_ston_" + this.monitorDice.getIndex();
        this.recognitionOther1 = new HashMap<>() {{
            put(recognitionOtherTitle1, new HashMap<String, Object>() {{
                put("recognition", "TemplateMatch");
//                put("recognition", "FeatureMatch");
                put("roi", new int[]{monitorDice.getRoiX(), monitorDice.getRoiY(), monitorDice.getRoiH(), monitorDice.getRoiW()});
                put("roi_offset", roiOffset);
                put("template", List.of("cz.png"));
                put("threshold", List.of(0.5));
                put("pre_delay", 0);
                put("post_delay", 0);
                put("method", 5);
                put("count", 4);
            }});
        }};

        // 扭曲
        this.recognitionOtherTitle2 = "recognition_single_other2_ston_" + this.monitorDice.getIndex();
        this.recognitionOther2 = new HashMap<>() {{
            put(recognitionOtherTitle2, new HashMap<String, Object>() {{
                put("recognition", "TemplateMatch");
//                put("recognition", "FeatureMatch");
                put("roi", new int[]{monitorDice.getRoiX(), monitorDice.getRoiY(), monitorDice.getRoiH(), monitorDice.getRoiW()});
                put("roi_offset", roiOffset);
                put("template", List.of("nq.png"));
                put("threshold", List.of(0.5));
                put("pre_delay", 0);
                put("post_delay", 0);
                put("method", 5);
                put("count", 4);
            }});
        }};

        // 小丑
        this.recognitionOtherTitle3 = "recognition_single_other3_ston_" + this.monitorDice.getIndex();
        this.recognitionOther3 = new HashMap<>() {{
            put(recognitionOtherTitle3, new HashMap<String, Object>() {{
                put("recognition", "TemplateMatch");
//                put("recognition", "FeatureMatch");
                put("roi", new int[]{monitorDice.getRoiX(), monitorDice.getRoiY(), monitorDice.getRoiH(), monitorDice.getRoiW()});
                put("roi_offset", roiOffset);
                put("template", List.of("xc.png"));
                put("threshold", List.of(0.5));
                put("pre_delay", 0);
                put("post_delay", 0);
                put("method", 5);
                put("count", 4);
            }});
        }};

        // 雪球
        this.recognitionOtherTitle4 = "recognition_single_other4_ston_" + this.monitorDice.getIndex();
        this.recognitionOther4 = new HashMap<>() {{
            put(recognitionOtherTitle4, new HashMap<String, Object>() {{
                put("recognition", "TemplateMatch");
//                put("recognition", "FeatureMatch");
                put("roi", new int[]{monitorDice.getRoiX(), monitorDice.getRoiY(), monitorDice.getRoiH(), monitorDice.getRoiW()});
                put("roi_offset", roiOffset);
                put("template", List.of("xq.png"));
                put("threshold", List.of(0.5));
                put("pre_delay", 0);
                put("post_delay", 0);
                put("method", 5);
                put("count", 4);
            }});
        }};
    }

    @Override
    public RunResult run(Context context, CustomAction.RunArg argv) {
//        log.info("start recognition");
        BufferedImage screenshot = RDContext.getScreenshot();
        if (screenshot == null) {
            log.warn("wait screenshot");
            return RunResult.success();
        }

        if (this.lastRecognitionImg != null && this.lastRecognitionImg.equals(screenshot)) {
            log.warn("is recognition screenshot");
            return RunResult.success();
        }
        this.lastRecognitionImg = screenshot;

        // Recognize blue dice
        double blueScore = Utils.getScore(context.runRecognition(this.recognitionBlueTitle, screenshot, this.recognitionBlue));

        // Recognize red dice
        double redScore = Utils.getScore(context.runRecognition(this.recognitionRedTitle, screenshot, this.recognitionRed));

        double otherScore1 = Utils.getScore(context.runRecognition(this.recognitionOtherTitle1, screenshot, this.recognitionOther1));
        double otherScore2 = Utils.getScore(context.runRecognition(this.recognitionOtherTitle2, screenshot, this.recognitionOther2));
        double otherScore3 = Utils.getScore(context.runRecognition(this.recognitionOtherTitle3, screenshot, this.recognitionOther3));
        double otherScore4 = Utils.getScore(context.runRecognition(this.recognitionOtherTitle4, screenshot, this.recognitionOther4));

        double max = NumberUtil.max(blueScore, redScore, otherScore1, otherScore2, otherScore3);
        if (blueScore == 0 && redScore == 0 && otherScore1 == 0 && otherScore2 == 0 && otherScore3 == 0 && otherScore4 == 0) {
            this.monitorDice.setType("-");
        }else {
            if (max == blueScore) {
                this.monitorDice.setType("B");
            }else if (max == redScore) {
                this.monitorDice.setType("R");
            }else if (max == otherScore1) {
                this.monitorDice.setType("1");
            }else if (max == otherScore2) {
                this.monitorDice.setType("2");
            }else if (max == otherScore3) {
                this.monitorDice.setType("3");
            }else if (max == otherScore4) {
                this.monitorDice.setType("4");
            }else {
                this.monitorDice.setType("-");
            }
        }
        log.info("{}, {}, {}, {}, {}, {}", blueScore, redScore, otherScore1, otherScore2, otherScore3, otherScore4);

        // Set type to "-" if neither recognized
//        if (blueScore == 0 && redScore == 0 && otherScore1 == 0 && otherScore2 == 0 && otherScore3 == 0) {
//            this.monitorDice.setType("-");
//
//        } else if (blueScore > redScore) {
//            this.monitorDice.setType("B");
//
//        } else {
//            this.monitorDice.setType("R");
//
//        }

        return RunResult.success();
    }


}
