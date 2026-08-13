package hospitalai;

import java.util.ArrayList;
import java.util.List;

/**
 * Main is the entry point of the demonstration application.
 *
 * It creates three different HealthcareWorker subclasses (Doctor, Nurse,
 * ITStaff), stores them polymorphically as HealthcareWorker references,
 * builds a PredictionSystem backed by a LogisticRegressionModel, and
 * prints out each worker's information alongside their (demonstration)
 * readiness prediction.
 *
 * NOTE ON THE EXAMPLE DATA:
 * The three examples below are DEMONSTRATION EXAMPLES only, styled after
 * the Part 6 "prediction on new examples" section of the Python project.
 * They are not live survey data and the printed probabilities come from
 * the placeholder Java scoring function in LogisticRegressionModel, not
 * from the trained Python sklearn model.
 */
public class Main {

    public static void main(String[] args) {

        System.out.println("==================================================");
        System.out.println(" AI-Assisted Hospital Management System Readiness");
        System.out.println(" Java OOP Demonstration Application");
        System.out.println("==================================================\n");

        // ------------------------------------------------------------
        // 1. Create three different worker objects (Doctor, Nurse, ITStaff)
        //    -> demonstrates OBJECT CREATION and CONSTRUCTORS
        // ------------------------------------------------------------

        // Demonstration Example 1 (styled after Part 6, Example 1)
        Doctor doctor = new Doctor(
                45,                 // age
                "Male",             // gender (example placeholder)
                "MBBS",             // educationLevel (example placeholder)
                20,                 // yearsOfExperience
                "Public",           // hospitalType
                "Rural",            // hospitalLocation
                1,                  // aiAwarenessScore
                5,                  // privacyScore
                5,                  // humanFactorScore
                1                   // infrastructureScore
        );

        // Demonstration Example 2 (styled after Part 6, Example 2)
        Nurse nurse = new Nurse(
                30,
                "Female",
                "Diploma in Nursing",
                6,
                "Private",
                "Semi-Urban",
                3,
                3,
                3,
                3
        );

        // Demonstration Example 3 (styled after Part 6, Example 3)
        ITStaff itStaff = new ITStaff(
                27,
                "Male",
                "BSc",
                3,
                "Private",
                "Dhaka",
                5,
                1,
                1,
                5
        );

        // ------------------------------------------------------------
        // 2. Demonstrate ENCAPSULATION via getters/setters
        // ------------------------------------------------------------
        System.out.println("-- Encapsulation demo: reading & updating a field via getter/setter --");
        System.out.println("Nurse's original years of experience: " + nurse.getYearsOfExperience());
        nurse.setYearsOfExperience(7); // e.g. after an update / correction
        System.out.println("Nurse's updated years of experience : " + nurse.getYearsOfExperience());
        System.out.println();

        // ------------------------------------------------------------
        // 3. Demonstrate POLYMORPHISM: store different subclasses in a
        //    common HealthcareWorker list/reference and call the SAME
        //    method (getRoleDescription) which behaves differently per
        //    object at runtime.
        // ------------------------------------------------------------
        List<HealthcareWorker> workers = new ArrayList<>();
        workers.add(doctor);
        workers.add(nurse);
        workers.add(itStaff);

        System.out.println("-- Polymorphism demo: same method call, different behaviour per object --");
        for (HealthcareWorker worker : workers) {
            System.out.println(worker.getRoleDescription());
        }
        System.out.println();

        // ------------------------------------------------------------
        // 4 & 5. Create a LogisticRegressionModel and a PredictionSystem
        // ------------------------------------------------------------
        PredictionModel model = new LogisticRegressionModel(); // abstraction: model typed as PredictionModel
        PredictionSystem predictionSystem = new PredictionSystem(model);

        // ------------------------------------------------------------
        // 6 & 7. Send each worker to the prediction system and display results
        // ------------------------------------------------------------
        System.out.println("==================================================");
        System.out.println(" Demonstration Predictions (NOT the trained Python");
        System.out.println(" sklearn model output - see LogisticRegressionModel");
        System.out.println(" for the honesty note on how these numbers are made)");
        System.out.println("==================================================\n");

        for (HealthcareWorker worker : workers) {
            System.out.println("Profession    : " + worker.getProfession());
            System.out.println("Worker Info   : " + worker.getBasicInfo());
            System.out.println("Role          : " + worker.getRoleDescription());

            ReadinessPrediction prediction = predictionSystem.predictReadiness(worker);
            prediction.displayResult();

            System.out.println("--------------------------------------------------\n");
        }

        System.out.println("End of demonstration.");
    }
}
