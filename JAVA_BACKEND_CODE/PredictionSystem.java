package hospitalai;

/**
 * PredictionSystem is the main application/service class. It implements
 * the Predictable interface and holds a reference to a PredictionModel
 * (dependency), which it delegates the actual prediction work to.
 *
 * OOP CONCEPTS DEMONSTRATED HERE:
 *  - Interface implementation : "implements Predictable".
 *  - Abstraction / dependency  : PredictionSystem depends on the abstract
 *                                 PredictionModel type, not a concrete
 *                                 class, so any future model
 *                                 (e.g. a RandomForestModel) could be
 *                                 plugged in without changing this class.
 *  - Encapsulation             : the model reference is private.
 */
public class PredictionSystem implements Predictable {

    private final PredictionModel model;

    public PredictionSystem(PredictionModel model) {
        this.model = model;
    }

    @Override
    public ReadinessPrediction predictReadiness(HealthcareWorker person) {
        return model.predict(person);
    }
}
