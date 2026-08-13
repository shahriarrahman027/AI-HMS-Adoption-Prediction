package hospitalai;

import java.util.Locale;

/**
 * ReadinessPrediction is a simple data-holder (result object) representing
 * the outcome of a readiness prediction: the predicted category
 * (Low / Medium / High) plus the probability distribution across all
 * three categories.
 *
 * OOP CONCEPTS DEMONSTRATED HERE:
 *  - Class / Object
 *  - Encapsulation (private fields, public getters)
 *  - Constructor
 */
public class ReadinessPrediction {

    private final String predictedCategory;
    private final double highProbability;
    private final double mediumProbability;
    private final double lowProbability;

    public ReadinessPrediction(String predictedCategory,
                                double highProbability,
                                double mediumProbability,
                                double lowProbability) {
        this.predictedCategory = predictedCategory;
        this.highProbability = highProbability;
        this.mediumProbability = mediumProbability;
        this.lowProbability = lowProbability;
    }

    public String getPredictedCategory() {
        return predictedCategory;
    }

    public double getHighProbability() {
        return highProbability;
    }

    public double getMediumProbability() {
        return mediumProbability;
    }

    public double getLowProbability() {
        return lowProbability;
    }

    /**
     * Prints a formatted summary of this prediction to the console.
     */
    public void displayResult() {
        System.out.println("Predicted Readiness Category : " + predictedCategory);
        System.out.printf(Locale.US, "  High   Probability : %.2f%%%n", highProbability * 100);
        System.out.printf(Locale.US, "  Medium Probability : %.2f%%%n", mediumProbability * 100);
        System.out.printf(Locale.US, "  Low    Probability : %.2f%%%n", lowProbability * 100);
    }
}
