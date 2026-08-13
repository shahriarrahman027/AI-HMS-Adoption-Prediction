document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("predict-form");
  const predictBtn = document.getElementById("predict-btn");
  const resetBtn = document.getElementById("reset-btn");
  const errorsBox = document.getElementById("form-errors");
  const resultCard = document.getElementById("result-card");
  const resultContent = document.getElementById("result-content");

  const REQUIRED_FIELDS = [
    "age", "gender", "profession", "education_level", "years_of_experience",
    "hospital_type", "hospital_location",
    "ai_awareness_score", "privacy_score", "human_factor_score", "infrastructure_score"
  ];

  function clearFieldErrors() {
    REQUIRED_FIELDS.forEach((name) => {
      const input = form.elements[name];
      if (input) input.closest(".field").classList.remove("invalid");
    });
  }

  function showErrors(messages) {
    errorsBox.hidden = false;
    errorsBox.innerHTML =
      "<strong>Please fix the following:</strong><ul>" +
      messages.map((m) => `<li>${m}</li>`).join("") +
      "</ul>";
  }

  function hideErrors() {
    errorsBox.hidden = true;
    errorsBox.innerHTML = "";
  }

  function validateClientSide(data) {
    const errors = [];
    clearFieldErrors();

    REQUIRED_FIELDS.forEach((name) => {
      const value = data[name];
      if (value === undefined || value === null || value === "") {
        errors.push(`Please provide a value for "${name.replace(/_/g, " ")}".`);
        const input = form.elements[name];
        if (input) input.closest(".field").classList.add("invalid");
      }
    });

    if (data.age && (data.age < 18 || data.age > 100)) {
      errors.push("Age must be between 18 and 100.");
      form.elements["age"].closest(".field").classList.add("invalid");
    }

    if (data.years_of_experience && (data.years_of_experience < 0 || data.years_of_experience > 50)) {
      errors.push("Years of experience must be between 0 and 50.");
      form.elements["years_of_experience"].closest(".field").classList.add("invalid");
    }

    return errors;
  }

  function setLoading(isLoading) {
    if (isLoading) {
      predictBtn.disabled = true;
      predictBtn.innerHTML = `<span class="loading"><span class="spinner"></span> Predicting...</span>`;
    } else {
      predictBtn.disabled = false;
      predictBtn.textContent = "Predict Readiness";
    }
  }

  function renderResult(payload) {
    const { predicted_category, probabilities } = payload;
    const order = ["Low", "Medium", "High"];

    const bars = order.map((label) => {
      const pct = probabilities[label];
      return `
        <div class="prob-bar-row">
          <div class="prob-label">${label}</div>
          <div class="prob-track">
            <div class="prob-fill fill-${label}" style="width: ${pct}%;"></div>
          </div>
          <div class="prob-value">${pct.toFixed(2)}%</div>
        </div>
      `;
    }).join("");

    resultContent.innerHTML = `
      <div class="result-summary">
        <span class="result-badge badge-${predicted_category}">${predicted_category.toUpperCase()} READINESS</span>
        <span class="result-note">Predicted using the trained Logistic Regression pipeline (Part 5).</span>
      </div>
      ${bars}
    `;

    resultCard.hidden = false;
    resultCard.scrollIntoView({ behavior: "smooth", block: "start" });
  }

  form.addEventListener("submit", async (e) => {
    e.preventDefault();
    hideErrors();

    const formData = new FormData(form);
    const data = Object.fromEntries(formData.entries());

    const clientErrors = validateClientSide(data);
    if (clientErrors.length > 0) {
      showErrors(clientErrors);
      return;
    }

    setLoading(true);
    resultCard.hidden = true;

    try {
      const response = await fetch("http://127.0.0.1:5001/predict", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data),
      });

      const payload = await response.json();

      if (!response.ok || !payload.success) {
        showErrors(payload.errors || ["Something went wrong. Please try again."]);
        return;
      }

      renderResult(payload);
    } catch (err) {
      showErrors(["Could not reach the prediction server. Please make sure the Flask app is running."]);
    } finally {
      setLoading(false);
    }
  });

  resetBtn.addEventListener("click", () => {
    form.reset();
    clearFieldErrors();
    hideErrors();
    resultCard.hidden = true;
  });
});
