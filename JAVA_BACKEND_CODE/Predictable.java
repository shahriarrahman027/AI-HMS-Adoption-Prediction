package hospitalai;

/**
 * Predictable is the public contract for anything that can turn a
 * HealthcareWorker into a ReadinessPrediction.
 *
 * OOP CONCEPTS DEMONSTRATED HERE:
 *  - Interface : a pure contract with no implementation, implemented by
 *                PredictionSystem.
 */
public interface Predictable {
    ReadinessPrediction predictReadiness(HealthcareWorker person);
}
