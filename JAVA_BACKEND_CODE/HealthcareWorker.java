package hospitalai;

/**
 * HealthcareWorker extends Person and adds the "profession" attribute.
 *
 * OOP CONCEPTS DEMONSTRATED HERE:
 *  - Inheritance   : HealthcareWorker extends Person and reuses all of its
 *                    fields/behaviour via the constructor "super(...)" call.
 *  - Encapsulation : profession is private with its own getter/setter.
 *  - Polymorphism  : getRoleDescription() is declared here and overridden
 *                    differently by Doctor, Nurse and ITStaff.
 */
public class HealthcareWorker extends Person {

    private String profession;

    public HealthcareWorker(int age,
                             String gender,
                             String educationLevel,
                             int yearsOfExperience,
                             String hospitalType,
                             String hospitalLocation,
                             int aiAwarenessScore,
                             int privacyScore,
                             int humanFactorScore,
                             int infrastructureScore,
                             String profession) {
        super(age, gender, educationLevel, yearsOfExperience, hospitalType,
                hospitalLocation, aiAwarenessScore, privacyScore,
                humanFactorScore, infrastructureScore);
        this.profession = profession;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    /**
     * Default role description. Subclasses (Doctor, Nurse, ITStaff)
     * override this method to provide profession-specific text, which is
     * the classic example of runtime polymorphism / method overriding.
     */
    public String getRoleDescription() {
        return "Healthcare worker (" + profession + ")";
    }
}
