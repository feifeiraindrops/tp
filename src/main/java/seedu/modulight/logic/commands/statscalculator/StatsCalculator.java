package seedu.modulight.logic.commands.statscalculator;

import java.util.Collections;
import java.util.List;

/**
 * Calculates the statics.
 */
public class StatsCalculator {
    private final List<Float> scores;

    /**
     * Constructs a stats calculator to generate various statistical measures
     *
     * @param scores a list of scores to be analyzed
     */
    public StatsCalculator(List<Float> scores) {
        Collections.sort(scores);
        this.scores = scores;
    }

    public float getMax() {
        return scores.get(scores.size() - 1);
    }

    public float getMin() {
        return scores.get(0);
    }

    private float getSum() {
        float sum = 0;
        for (float score : scores) {
            sum += score;
        }
        return sum;
    }

    private float getSumOfSquare() {
        float sum = 0;
        for (float score : scores) {
            sum += score * score;
        }
        return sum;
    }

    public float getMean() {
        return getSum() / scores.size();
    }

    public float getStd() {
        return (float) Math.pow(getSumOfSquare() / scores.size() - getMean() * getMean(), 0.5);
    }

    public float getUpperQuartile() {
        double upperIndex = (scores.size() + 1) * 0.75 - 1;
        double dec = upperIndex % 1;
        int floor = (int) Math.max(0, Math.floor(upperIndex));
        int ceiling = (int) Math.min(Math.ceil(upperIndex), scores.size() - 1);
        return (float) (scores.get(floor) + (scores.get(ceiling) - scores.get(floor)) * dec);
    }

    public float getLowerQuartile() {
        double lowerIndex = (scores.size() + 1) * 0.25 - 1;
        double dec = lowerIndex % 1;
        int floor = (int) Math.max(0, Math.floor(lowerIndex));
        int ceiling = (int) Math.min(Math.ceil(lowerIndex), scores.size() - 1);
        return (float) (scores.get(floor) + (scores.get(ceiling) - scores.get(floor)) * dec);
    }

    public float getSkewness() {
        float mean = getMean();
        float std = getStd();
        float sum = 0;
        for (float score : scores) {
            sum += (float) Math.pow(score - mean, 3);
        }
        return (float) (sum / Math.pow(std, 3) / (scores.size() - 1));
    }
}
