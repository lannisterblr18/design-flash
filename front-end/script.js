let mode = 'topic'; // or 'question'
let questionIndex = 0;
let questions = [];
const host = 'https://design-flash-1.onrender.com';
// const host = 'http://localhost:8080' // Base URL for API requests
const TOPIC_API = host+'/topics/v1/random';
const QUESTION_API = host+'/questions';
const TOPIC_LOCAL_API = host+'/topics/v2/random';
 // New endpoint serving parsed Q&A list

if (localStorage.getItem("dark-mode") === "true") {
  document.body.classList.add("dark-mode");
}

function toggleDarkMode() {
  document.body.classList.toggle("dark-mode");
  localStorage.setItem("dark-mode", document.body.classList.contains("dark-mode"));
}

document.addEventListener("DOMContentLoaded", () => {
  document.getElementById("next-btn").addEventListener("click", handleNext);
  document.getElementById("question-btn").addEventListener("click", switchToQuestionMode);
});

async function handleNext() {
  if (mode === 'question') {
    showNextQuestion();
  } else {
    loadRandomTopic();
  }
}

async function loadRandomTopic() {
  try {
    const response = await fetch(TOPIC_API);
    const data = await response.json();

    document.getElementById("topic-title").textContent = data.title;
    document.getElementById("topic-description").textContent = data.description;
  } catch (error) {
    document.getElementById("topic-title").textContent = "Error";
    document.getElementById("topic-description").textContent = "Failed to load topic.";
  }
}

async function switchToQuestionMode() {
  try {
    const response = await fetch(QUESTION_API);
    questions = await response.json();
    questionIndex = 0;
    mode = 'question';
    document.getElementById("topic-card").style.display = "none";
    document.getElementById("question-mode").style.display = "block";
    showNextQuestion();
  } catch (error) {
    document.getElementById("question-title").textContent = "Error";
    document.getElementById("question-answer").textContent = "Failed to load questions.";
    document.getElementById("topic-card").style.display = "none";
    document.getElementById("question-mode").style.display = "block";
  }
}

function showNextQuestion() {
  if (!questions || questions.length === 0) {
    document.getElementById("question-title").textContent = "No Questions";
    document.getElementById("question-answer").textContent = "Question bank is empty.";
    return;
  }

  const q = questions[questionIndex % questions.length];
  document.getElementById("question-title").textContent = "Q: " + q.question;
  document.getElementById("question-answer").textContent = "A: " + q.answer;
  questionIndex++;
}


document.getElementById("question-btn").addEventListener("click", () => {
  // Sample question, you should fetch it from backend instead
  const sample = {
    question: "Design a chat system",
    answer: "Use WebSockets for real-time messaging, store messages in DB or message queue."
  };

  document.getElementById("question-title").innerText = sample.question;
  document.getElementById("question-answer").innerText = sample.answer;

  document.getElementById("topic-card").style.display = "none";
  document.getElementById("question-mode").style.display = "block";
});

function exitQuestionMode() {
  document.getElementById("question-mode").style.display = "none";
  document.getElementById("topic-card").style.display = "flex";
  mode = 'topic';
}

function loadNextQuestion() {
  // Replace with API fetch
  const next = {
    question: "Design a URL shortener",
    answer: "Generate unique ID (Base62), map to full URL in DB, redirect using controller."
  };

  document.getElementById("question-title").innerText = next.question;
  document.getElementById("question-answer").innerText = next.answer;
}

function toggleDarkMode() {
  document.body.classList.toggle("dark-mode");
}

async function switchToLocalRandomTopic() {
  try {
    const response = await fetch(TOPIC_LOCAL_API);
    const topic = await response.json();
    document.getElementById("topic-title").textContent = topic.title;
    document.getElementById("topic-description").textContent = topic.description;
  } catch (err) {
    document.getElementById("topic-title").textContent = "Error";
    document.getElementById("topic-description").textContent = "Could not fetch a local topic.";
  }
}
