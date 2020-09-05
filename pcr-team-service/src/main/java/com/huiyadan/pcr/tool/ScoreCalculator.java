package com.huiyadan.pcr.tool;

import java.math.BigDecimal;

/**
 * 根据伤害和boss周目计算积分
 *
 * @author lihh
 */
public class ScoreCalculator {

    private static final double[] ratio1 = new double[]{1, 1, 1.1, 1.1, 1.2};

    private static final double[] ratio2 = new double[]{1.2, 1.2, 1.5, 1.7, 2};

    /**
     * 计算积分
     *
     * @param lapNum  周目
     * @param bossNum 第几个boss
     * @param damage  伤害
     * @return
     */
    public static Integer compute(Integer lapNum, Integer bossNum, Integer damage) {
        if (lapNum == null || bossNum == null || damage == null) {
            return null;
        }

        if (lapNum == 1) {
            return compute(ratio1, bossNum, damage);
        } else {
            return compute(ratio2, bossNum, damage);
        }
    }

    private static Integer compute(double[] ratio, Integer bossNum, Integer damage) {
        BigDecimal score = new BigDecimal(ratio[bossNum - 1]).multiply(new BigDecimal(damage));
        return score.setScale(0, BigDecimal.ROUND_HALF_UP).intValue(); // 四舍五入取整
    }
}
