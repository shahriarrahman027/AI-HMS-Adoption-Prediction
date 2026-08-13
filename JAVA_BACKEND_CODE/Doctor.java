package hospitalai;

/**
 * Doctor extends HealthcareWorker.
 *
 * OOP CONCEPTS DEMONSTRATED HERE:
 *  - Inheritance         : Doctor inherits all Person/HealthcareWorker fields.
 *  - Method Overriding   : getRoleDescription() is overridden with the
 *                          "@Override" annotation.
 */
public class Doctor extends HealthcareWorker {

    public Doctor(int age,
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
                humanFactorScore, infrastructureScore, "Doctor");
    }

    @Override
    public String getRoleDescription() {
        return "Doctor - responsible for clinical decision-making and "
                + "direct patient care; a key stakeholder in AI adoption "
                + "readiness because of professional/clinical trust factors.";
    }
}
