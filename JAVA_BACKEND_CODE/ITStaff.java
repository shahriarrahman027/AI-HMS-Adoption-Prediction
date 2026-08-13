package hospitalai;

/**
 * ITStaff extends HealthcareWorker.
 *
 * OOP CONCEPTS DEMONSTRATED HERE:
 *  - Inheritance
 *  - Method Overriding (getRoleDescription())
 */
public class ITStaff extends HealthcareWorker {

    public ITStaff(int age,
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
                humanFactorScore, infrastructureScore, "IT Staff");
    }

    @Override
    public String getRoleDescription() {
        return "IT Staff - responsible for technical infrastructure and "
                + "system maintenance; infrastructure readiness and "
                + "AI awareness scores weigh heavily for this role.";
    }
}
