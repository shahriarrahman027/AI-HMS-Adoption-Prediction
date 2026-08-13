package hospitalai;

/**
 * LogisticRegressionModel extends the abstract PredictionModel class.
 *
 * ============================================================
 * IMPORTANT / HONESTY NOTE (read before using this class):
 * ============================================================
 * The ACTUAL trained Logistic Regression model — with its real learned
 * coefficients, scikit-learn preprocessing pipeline (encoding, scaling,
 * etc.) and evaluated accuracy — lives in the Python project, in
 * Part 5 (Machine Learning Modeling) and Part 6 (Prediction on new
 * examples). This Java class does NOT load, call, or re-implement that
 * trained sklearn model, and it does NOT reproduce its real accuracy.
 *
 * For this OOP course, Java only needs to demonstrate the ARCHITECTURE
 * around a prediction model: how an abstract PredictionModel is
 * subclassed, how a PredictionSystem depends on it, and how a result
 * object (ReadinessPrediction) flows back out.
 *
 * To make the demo runnable end-to-end without Python, this class uses a
 * small, clearly-labelled, DETERMINISTIC placeholder scoring function
 * that turns the four survey scores (AI awareness, privacy, human
 * factor, infrastructure) into a plausible-looking probability
 * distribution. It is a stand-in for illustration only — it is NOT a
 * trained statistical model and its numbers carry no real-world
 * scientific validity.
 * ============================================================
 */
public class LogisticRegressionModel extends PredictionModel {

    @Override
    public ReadinessPrediction predict(HealthcareWorker person) {

        // --- Placeholder "logit-style" combination of the four scores ---
        // Each score is on a 1-5 scale. We center them around 3 and combine
        // them with simple demonstration weights. This mimics the SHAPE of
        // a logistic regression linear combination (w1*x1 + w2*x2 + ...)
        // without claiming to be the real trained model.
        double ai = person.getAiAwarenessScore();
        double privacy = person.getPrivacyScore();
        double human = person.getHumanFactorScore();
        double infra = person.getInfrastructureScore();

        double lowLogit = (5 - ai) * 0.9 + (5 - privacy) * 0.4
                + (5 - human) * 0.7 + (5 - infra) * 0.8;
        double medLogit = 4.0; // flat baseline for "medium"
        double highLogit = ai * 0.9 + (5 - privacy) * 0.2
                + human * 0.6 + infra * 0.9;

        // Softmax to turn the three logits into a proper probability
        // distribution that sums to 1.0.
        double expLow = Math.exp(lowLogit);
        double expMed = Math.exp(medLogit);
        double expHigh = Math.exp(highLogit);
        double sum = expLow + expMed + expHigh;

        double lowProb = expLow / sum;
        double medProb = expMed / sum;
        double highProb = expHigh / sum;

        String category;
        if (highProb >= medProb && highProb >= lowProb) {
            category = "High";
        } else if (medProb >= lowProb) {
            category = "Medium";
        } else {
            category = "Low";
        }

        return new ReadinessPrediction(category, highProb, medProb, lowProb);
    }
}
