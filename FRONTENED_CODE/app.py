"""
app.py
------
Flask web application for the AI-Assisted HMS Adoption Readiness Predictor.

This app loads the ALREADY-TRAINED Part 5 Logistic Regression pipeline
(saved as model/logistic_regression_pipeline.joblib) and uses it, unmodified,
to serve predictions through a simple REST API + HTML/CSS/JS frontend.

No retraining, no new methodology, no hardcoded predictions happen here.
"""

import os
import joblib
import pandas as pd
from flask import Flask, request, jsonify, render_template

BASE_DIR = os.path.dirname(os.path.abspath(__file__))
MODEL_PATH = os.path.join(BASE_DIR, "model", "logistic_regression_pipeline.joblib")

app = Flask(__name__)

# ---------------------------------------------------------------------------
# Load the trained pipeline ONCE when the server starts
# ---------------------------------------------------------------------------
if not os.path.exists(MODEL_PATH):
    raise FileNotFoundError(
        f"Trained model not found at {MODEL_PATH}.\n"
        f"Run 'python ml/train_and_save_model.py' first to create it."
    )

model = joblib.load(MODEL_PATH)
print("Loaded trained Logistic Regression pipeline from:", MODEL_PATH)

# ---------------------------------------------------------------------------
# Exact feature set and expected categorical values from Part 5 / the CSV.
# Used only for input validation — NOT for prediction logic.
# ---------------------------------------------------------------------------
FEATURE_COLUMNS = [
    "age",
    "gender",
    "profession",
    "education_level",
    "years_of_experience",
    "hospital_type",
    "hospital_location",
    "ai_awareness_score",
    "privacy_score",
    "human_factor_score",
    "infrastructure_score",
]

CATEGORICAL_FEATURES = ["gender", "profession", "education_level", "hospital_type", "hospital_location"]
NUMERICAL_FEATURES = ["age", "years_of_experience", "ai_awareness_score",
                       "privacy_score", "human_factor_score", "infrastructure_score"]

VALID_CATEGORIES = {
    "gender": ["Female", "Male", "Other/Prefer not to say"],
    "profession": ["Doctor", "Healthcare Student", "Hospital Administrator", "IT Staff", "Nurse", "Other"],
    "education_level": ["Bachelor's (completed)", "Bachelor's (ongoing)", "Postgraduate/Doctoral", "Secondary/HSC"],
    "hospital_type": ["NGO/Other", "Not Applicable", "Private", "Public"],
    "hospital_location": ["Chattogram", "Dhaka", "Not Applicable", "Other Urban", "Rural", "Semi-Urban"],
}

SCORE_FEATURES = ["ai_awareness_score", "privacy_score", "human_factor_score", "infrastructure_score"]


def validate_input(data):
    """Validate incoming JSON against the 11 expected features. Returns a list of error strings."""
    errors = []

    for col in FEATURE_COLUMNS:
        if col not in data or data[col] in (None, ""):
            errors.append(f"Missing value for '{col}'.")

    if errors:
        return errors  # stop early if fields are missing

    # Numeric checks
    try:
        age = float(data["age"])
        if not (18 <= age <= 100):
            errors.append("Age must be between 18 and 100.")
    except (ValueError, TypeError):
        errors.append("Age must be a number.")

    try:
        years = float(data["years_of_experience"])
        if not (0 <= years <= 50):
            errors.append("Years of experience must be between 0 and 50.")
    except (ValueError, TypeError):
        errors.append("Years of experience must be a number.")

    for score_col in SCORE_FEATURES:
        try:
            val = float(data[score_col])
            if not (1 <= val <= 5):
                errors.append(f"{score_col.replace('_', ' ').title()} must be between 1 and 5.")
        except (ValueError, TypeError):
            errors.append(f"{score_col.replace('_', ' ').title()} must be a number.")

    # Categorical checks
    for cat_col, valid_values in VALID_CATEGORIES.items():
        if data.get(cat_col) not in valid_values:
            errors.append(f"Invalid value for '{cat_col}'.")

    return errors


@app.route("/")
def index():
    """Serve the frontend page, including the valid dropdown values."""
    return render_template("index.html", valid_categories=VALID_CATEGORIES)


@app.route("/predict", methods=["POST"])
def predict():
    """
    Receive the 11 input features as JSON, run them through the EXISTING
    trained Logistic Regression pipeline, and return the predicted class
    plus the class probabilities from model.predict_proba().
    """
    data = request.get_json(silent=True)
    if data is None:
        return jsonify({"success": False, "errors": ["Request body must be valid JSON."]}), 400

    errors = validate_input(data)
    if errors:
        return jsonify({"success": False, "errors": errors}), 400

    # Build a single-row DataFrame in the exact column order the pipeline expects
    row = {
        "age": float(data["age"]),
        "gender": data["gender"],
        "profession": data["profession"],
        "education_level": data["education_level"],
        "years_of_experience": float(data["years_of_experience"]),
        "hospital_type": data["hospital_type"],
        "hospital_location": data["hospital_location"],
        "ai_awareness_score": float(data["ai_awareness_score"]),
        "privacy_score": float(data["privacy_score"]),
        "human_factor_score": float(data["human_factor_score"]),
        "infrastructure_score": float(data["infrastructure_score"]),
    }
    input_df = pd.DataFrame([row], columns=FEATURE_COLUMNS)

    # Run the ACTUAL trained pipeline — no hardcoded logic
    predicted_class = model.predict(input_df)[0]
    probabilities = model.predict_proba(input_df)[0]
    class_labels = model.named_steps["classifier"].classes_  # e.g. ['High', 'Low', 'Medium']

    prob_dict = {label: round(float(prob) * 100, 2) for label, prob in zip(class_labels, probabilities)}

    return jsonify({
        "success": True,
        "predicted_category": predicted_class,
        "probabilities": {
            "Low": prob_dict.get("Low", 0.0),
            "Medium": prob_dict.get("Medium", 0.0),
            "High": prob_dict.get("High", 0.0),
        }
    })


if __name__ == "__main__":
    app.run(debug=False, host="0.0.0.0", port=5001)
