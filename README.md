```markdown
# ✉️ MailCraft: AI Email Assistant

![Java](https://img.shields.io/badge/Java-17-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.13-brightgreen.svg)
![Google Gemini](https://img.shields.io/badge/AI-Google%20Gemini-blue.svg)
![Chrome Extension](https://img.shields.io/badge/Extension-Chrome-yellow.svg)

## What is MailCraft?

MailCraft is an AI-powered email assistant designed to help you write better emails in seconds. Powered by Google's Gemini AI and built on a robust Spring Boot backend, it allows users to instantly generate contextual, highly polished email replies. 

The project includes two primary components:
1. A **Standalone Web Application** featuring a beautiful, responsive, dark/light-themed UI to paste emails and generate replies.
2. A **Chrome Extension** that seamlessly integrates directly into your Gmail compose toolbar, letting you generate AI replies without ever leaving your inbox.

## Why use MailCraft?

* **🤖 Intelligent Context Extraction**: Automatically reads the original email context and drafts a highly relevant response.
* **🎭 Tone Customization**: Choose how you want to sound—Professional, Friendly, Formal, Concise, Persuasive, Empathetic, or Assertive.
* **✉️ Seamless Gmail Integration**: Injects an elegant "✉ AI Reply" button directly into the Gmail UI.
* **📊 Usage & Cost Metrics**: Built-in token counting and cost estimation to keep track of your AI usage.
* **🌓 Modern Web UI**: A sleek standalone interface featuring responsive design and dark mode.

## Getting Started

### Prerequisites
* **Java 17** or higher
* **Maven** (Included via wrapper)
* **Google Gemini API Key** (Get it from Google AI Studio)
* **Google Chrome** (for the extension)

### 1. Setting up the Backend

1. Clone the repository and navigate to the backend folder:
   ```bash
   git clone [https://github.com/aaditwocode/Email-assistant.git](https://github.com/aaditwocode/Email-assistant.git)
   cd Email-assistant/email-writer-sb

```

2. Set your environment variables for the Gemini API:
```bash
export GEMINI_API_URL="[https://generativelanguage.googleapis.com](https://generativelanguage.googleapis.com)"
export GEMINI_API_KEY="your_api_key_here"

```


3. Run the Spring Boot application:
```bash
./mvnw spring-boot:run

```


4. Open your browser and navigate to `http://localhost:8080` to access the standalone MailCraft Web UI.

### 2. Installing the Chrome Extension (Local Development)

1. Open Google Chrome and navigate to `chrome://extensions/`.
2. Toggle **Developer mode** ON in the top right corner.
3. Click the **Load unpacked** button in the top left.
4. Select the `email-writer-sb-ext` folder from this repository.
5. **Note on Local Testing:** By default, the extension points to a deployed Render URL. To use your local backend, open `email-writer-sb-ext/content.js` and change the fetch URL to `http://localhost:8080/api/email/generate`.
6. Open Gmail, click **Reply** on an email, and click the new **✉ AI Reply** button next to the "Send" button!

## Usage

### Using the Web App

1. Paste the email you received into the "Original Email" text area.
2. Select your desired tone using the pills (e.g., Professional, Friendly).
3. Click **Generate Reply**.
4. Review your generated response, view token usage/cost stats, and easily copy the text to your clipboard.

### Using the Chrome Extension

1. Open an email thread in Gmail and hit **Reply**.
2. Click the purple **✉ AI Reply** button injected into the Gmail toolbar.
3. Wait a few seconds while MailCraft reads the context and types out a professional draft directly into your compose box.

## Getting Help

If you run into any issues, have questions, or want to suggest new features:

* Please [open an issue](https://www.google.com/search?q=https://github.com/aaditwocode/Email-assistant/issues) on the GitHub repository.
* Ensure you include any relevant error logs (especially from the Spring Boot console or the Chrome DevTools console).

## Contributing & Maintainers

MailCraft is an open-source project and contributions are incredibly welcome!

**To contribute:**

1. Fork the repository.
2. Create a new branch for your feature (`git checkout -b feature/amazing-feature`).
3. Commit your changes (`git commit -m 'Add some amazing feature'`).
4. Push to the branch (`git push origin feature/amazing-feature`).
5. Open a Pull Request.

Please ensure your code follows the existing style conventions and that any new backend endpoints are documented.

```

```
