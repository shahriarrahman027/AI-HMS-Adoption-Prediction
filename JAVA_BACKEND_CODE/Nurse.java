package hospitalai;

/**
 * Nurse extends HealthcareWorker.
 *
 * OOP CONCEPTS DEMONSTRATED HERE:
 *  - Inheritance
 *  - Method Overriding (getRoleDescription())
 */
public class Nurse extends HealthcareWorker {

    public Nurse(int age,
                 String gender,
                 String educationLevel,
                 int yearsOfExperience,
                 String hospitalType,
                 String hospitalLocation,
                 int aiAwarenessScore,
                 int privacyScore,
                 int humanFactorScore,
                 int infrastructureScore) {
        super(age, gender, educationLevel, yearsOfExperience, hospitalType,
                hospitalLocation, aiAwarenessScore, privacyScore,
                humanFactorScore, infrastructureScore, "Nurse");
    }

    @Override
    public String getRoleDescription() {
        return "Nurse - front-line care provider with frequent day-to-day "
                + "system interaction; human-factor comfort with new "
                + "technology strongly influences their adoption readiness.";
    }
}
