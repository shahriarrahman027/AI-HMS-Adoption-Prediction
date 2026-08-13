"""
train_and_save_model.py
------------------------
Trains and saves the Logistic Regression pipeline exactly as defined in
Part 5 (ML Modeling) of the HMS Adoption Readiness research project.

This script does NOT change the methodology in any way. It:
  - loads the ORIGINAL synthetic CSV as-is
  - uses the same feature_columns / categorical_features / numerical_features
  - uses the same train_test_split (test_size=0.20, random_state=42, stratify=y)
  - uses the same ColumnTransformer (OneHotEncoder + StandardScaler)
  - uses the same LogisticRegression(max_iter=1000, random_state=42)
  - fits the pipeline on the training data (same as Part 5)
  - saves the fitted pipeline with joblib so the Flask app can load it

Run this once before starting the Flask app:
    python ml/train_and_save_model.py
"""

import os
import joblib
import pandas as pd

from sklearn.model_selection import train_test_split
from sklearn.preprocessing import OneHotEncoder, StandardScaler
from sklearn.compose import ColumnTransformer
from sklearn.pipeline import Pipeline
from sklearn.linear_model import LogisticRegression
from sklearn.metrics import accuracy_score

# ---------------------------------------------------------------------------
# Constants (identical to Part 5)
# ---------------------------------------------------------------------------
RANDOM_STATE = 42

BASE_DIR = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
DATA_PATH = os.path.join(BASE_DIR, "data", "synthetic_hms_adoption_dataset.csv")
MODEL_PATH = os.path.join(BASE_DIR, "model", "logistic_regression_pipeline.joblib")

# ---------------------------------------------------------------------------
# Section 1 — Load the ORIGINAL dataset exactly as-is (no regeneration)
# ---------------------------------------------------------------------------
df = pd.read_csv(DATA_PATH)
print("Dataset shape:", df.shape)

assert df.shape == (2000, 35), "Unexpected shape — check that the correct CSV is present."

# ---------------------------------------------------------------------------
# Section 2 — Same feature set as Part 5
# ---------------------------------------------------------------------------
feature_columns = [
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

target_column = "adoption_readiness_category"

categorical_features = ["gender", "profession", "education_level", "hospital_type", "hospital_location"]
numerical_features = ["age", "years_of_experience", "ai_awareness_score",
                       "privacy_score", "human_factor_score", "infrastructure_score"]

assert set(categorical_features + numerical_features) == set(feature_columns)
assert all(col in df.columns for col in feature_columns + [target_column])

X = df[feature_columns].copy()
y = df[target_column].copy()

# ---------------------------------------------------------------------------
# Section 3 — Same train/test split as Part 5
# ---------------------------------------------------------------------------
X_train, X_test, y_train, y_test = train_test_split(
    X, y,
    test_size=0.20,
    random_state=RANDOM_STATE,
    stratify=y
)

# ---------------------------------------------------------------------------
# Section 4 — Same preprocessing + Logistic Regression pipeline as Part 5
# ---------------------------------------------------------------------------
preprocessor_scaled = ColumnTransformer(
    transformers=[
        ("cat", OneHotEncoder(handle_unknown="ignore"), categorical_features),
        ("num", StandardScaler(), numerical_features),
    ]
)

logreg_pipeline = Pipeline(steps=[
    ("preprocessor", preprocessor_scaled),
    ("classifier", LogisticRegression(max_iter=1000, random_state=RANDOM_STATE))
])

# ---------------------------------------------------------------------------
# Section 5 — Fit and verify (same result as Part 5: ~0.592 test accuracy)
# ---------------------------------------------------------------------------
logreg_pipeline.fit(X_train, y_train)
y_pred = logreg_pipeline.predict(X_test)

print("Logistic Regression fitted.")
print("Test accuracy:", round(accuracy_score(y_test, y_pred), 3))
print("Model classes:", list(logreg_pipeline.named_steps["classifier"].classes_))

# ---------------------------------------------------------------------------
# Section 6 — Save the fitted pipeline for the Flask app
# ---------------------------------------------------------------------------
os.makedirs(os.path.dirname(MODEL_PATH), exist_ok=True)
joblib.dump(logreg_pipeline, MODEL_PATH)
print(f"\nSaved fitted pipeline to: {MODEL_PATH}")
