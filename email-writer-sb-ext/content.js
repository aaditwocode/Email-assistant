console.log("MailCraft content script loaded 🚀");

function findComposeToolbar() {
    const selectors = [
        '.aDh',
        '.btC',
        '[role="dialog"] .aDh',
        '.Am .aDh',
        '.gU .aDh'
    ];

    for (let selector of selectors) {
        const el = document.querySelector(selector);
        if (el) return el;
    }

    return null;
}


function getEmailContent() {
  const selectors = ['.a3s.aiL', '.ii.gt'];

  for (let sel of selectors) {
    const el = document.querySelector(sel);
    if (el) {
      let text = el.innerText;

      
      text = text
        .replace(/Reply|More|Forward|Send/gi, '')
        .replace(/Add reaction/gi, '')
        .replace(/no-reply@.*\n?/gi, '')
        .replace(/\n{2,}/g, '\n')
        .trim();

      return text;
    }
  }

  return "";
}


function insertReply(text) {
    const textarea = document.querySelector('[role="textbox"]');

    if (textarea) {
        textarea.focus();

        // Clear existing text
        textarea.innerHTML = "";

        // Insert formatted text
        textarea.innerHTML = text.replace(/\n/g, "<br>");
    }
}

function injectButton() {
    const toolbar = findComposeToolbar();
    if (!toolbar) return;

    // Prevent duplicate
    if (toolbar.querySelector('#mailcraft-btn')) return;

    const button = document.createElement("button");
    button.textContent = "✉ AI Reply";
    button.id = "mailcraft-btn";

    // 🎨 Styling
    button.style.marginLeft = "8px";
    button.style.padding = "6px 12px";
    button.style.background = "#6C63FF";
    button.style.color = "#fff";
    button.style.border = "none";
    button.style.borderRadius = "6px";
    button.style.cursor = "pointer";
    button.style.fontSize = "12px";

    // ⚡ Click handler
    button.addEventListener("click", async () => {
        console.log("AI Reply clicked ✨");

        button.textContent = "Generating...";
        button.disabled = true;

        try {
            const emailContent = getEmailContent();
            console.log("Extracted email:", emailContent);

            const response = await fetch("https://email-assistant-3.onrender.com/api/email/generate", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    originalemail: emailContent,
                    tone: "professional"
                })
            });

            const data = await response.json();

            console.log("AI Response:", data);

            insertReply(data.email);

            button.textContent = "✉ AI Reply";
            button.disabled = false;

        } catch (err) {
            console.error("Error:", err);
            button.textContent = "Error ❌";
        }
    });

    toolbar.appendChild(button);
}


const observer = new MutationObserver((mutations) => {
    for (const mutation of mutations) {
        const addedNodes = Array.from(mutation.addedNodes);

        const hasCompose = addedNodes.some(node => {
            if (node.nodeType !== Node.ELEMENT_NODE) return false;

            return node.matches?.('.aDh, .btC, [role="dialog"]') ||
                   node.querySelector?.('.aDh, .btC, [role="dialog"]');
        });

        if (hasCompose) {
            console.log("Compose detected 📩");
            setTimeout(injectButton, 500);
        }
    }
});


observer.observe(document.body, {
    childList: true,
    subtree: true
});