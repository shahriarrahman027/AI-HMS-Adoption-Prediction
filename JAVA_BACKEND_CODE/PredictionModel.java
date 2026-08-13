package hospitalai;

/**
 * PredictionModel is an ABSTRACT CLASS representing "some model that can
 * predict AI-adoption readiness". It does not know or care HOW the
 * prediction is produced internally — that is left to concrete subclasses.
 *
 * OOP CONCEPTS DEMONSTRATED HERE:
 *  - Abstraction    : PredictionModel cannot be instantiated directly; it
 *                      only defines the contract ("predict") that any
 *                      prediction model must fulfil.
 *  - Polymorphism    : any subclass can be substituted wherever a
 *                      PredictionModel reference is expected.
 */
public abstract class PredictionModel {

    /**
     * Predicts AI-adoption readiness for the given healthcare worker.
     * Concrete subclasses decide how this is actually computed.
     */
    public abstract ReadinessPrediction predict(HealthcareWorker person);
}
