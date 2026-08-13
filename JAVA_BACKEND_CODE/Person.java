package hospitalai;

/**
 * Person is the base class for anyone participating in the survey/prediction
 * process. It holds all the demographic and score-based attributes that the
 * Python ML model (Part 4-6) used as input features.
 *
 * OOP CONCEPTS DEMONSTRATED HERE:
 *  - Class / Object       : Person is a class; instances of it (or its
 *                            subclasses) are objects.
 *  - Encapsulation         : all fields are private and only reachable
 *                            through public getters/setters.
 *  - Constructor           : a full-argument constructor initializes every
 *                            field at object-creation time.
 */
public class Person {

    private int age;
    private String gender;
    private String educationLevel;
    private int yearsOfExperience;
    private String hospitalType;
    private String hospitalLocation;
    private int aiAwarenessScore;
    private int privacyScore;
    private int humanFactorScore;
    private int infrastructureScore;

    public Person(int age,
                  String gender,
                  String educationLevel,
                  int yearsOfExperience,
                  String hospitalType,
                  String hospitalLocation,
                  int aiAwarenessScore,
                  int privacyScore,
                  int humanFactorScore,
                  int infrastructureScore) {
        this.age = age;
        this.gender = gender;
        this.educationLevel = educationLevel;
        this.yearsOfExperience = yearsOfExperience;
        this.hospitalType = hospitalType;
        this.hospitalLocation = hospitalLocation;
        this.aiAwarenessScore = aiAwarenessScore;
        this.privacyScore = privacyScore;
        this.humanFactorScore = humanFactorScore;
        this.infrastructureScore = infrastructureScore;
    }

    // ---------------- Getters ----------------

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public String getHospitalType() {
        return hospitalType;
    }

    public String getHospitalLocation() {
        return hospitalLocation;
    }

    public int getAiAwarenessScore() {
        return aiAwarenessScore;
    }

    public int getPrivacyScore() {
        return privacyScore;
    }

    public int getHumanFactorScore() {
        return humanFactorScore;
    }

    public int getInfrastructureScore() {
        return infrastructureScore;
    }

    // ---------------- Setters ----------------

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public void setHospitalType(String hospitalType) {
        this.hospitalType = hospitalType;
    }

    public void setHospitalLocation(String hospitalLocation) {
        this.hospitalLocation = hospitalLocation;
    }

    public void setAiAwarenessScore(int aiAwarenessScore) {
        this.aiAwarenessScore = aiAwarenessScore;
    }

    public void setPrivacyScore(int privacyScore) {
        this.privacyScore = privacyScore;
    }

    public void setHumanFactorScore(int humanFactorScore) {
        this.humanFactorScore = humanFactorScore;
    }

    public void setInfrastructureScore(int infrastructureScore) {
        this.infrastructureScore = infrastructureScore;
    }

    /**
     * A simple human-readable summary. Subclasses override or extend this
     * behaviour (see HealthcareWorker.getRoleDescription()).
     */
    public String getBasicInfo() {
        return "Age: " + age +
                ", Gender: " + gender +
                ", Education: " + educationLevel +
                ", Experience: " + yearsOfExperience + " yrs" +
                ", Hospital Type: " + hospitalType +
                ", Location: " + hospitalLocation +
                ", AI Awareness: " + aiAwarenessScore +
                ", Privacy: " + privacyScore +
                ", Human Factor: " + humanFactorScore +
                ", Infrastructure: " + infrastructureScore;
    }
}
