Perfect — let’s build a **personalized CSS Cheatsheet** from *your current style* (based on everything you’ve written so far: login/register form, header, and your general styling tendencies).

This will act as your **style DNA map** — so you can refine it consciously instead of coding by instinct.

---

## 🎨 Pyke’s Current CSS Design Cheatsheet

*(Snapshot of your existing style system — cleaned up, standardized, and explained)*

---

### 🧱 **Layout & Container Rules**

| Attribute                           | Example                                                       | Explanation                                                                                                       |
| ----------------------------------- | ------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------- |
| `display: flex;`                    | `display: flex; flex-direction: column; align-items: center;` | You use flexbox for centering and stacking — clean and modern. Keep it consistent for forms, headers, and modals. |
| `margin: auto;`                     | `margin: auto;`                                               | Centers your elements horizontally — ideal for single-column layouts.                                             |
| `border-radius: 1rem;`              | `border-radius: 1rem;`                                        | Gives your components a soft, friendly aesthetic.                                                                 |
| `box-shadow: 2px 2px 5px 5px grey;` | —                                                             | Adds depth. Consider using subtler RGBA shadows like `0 4px 10px rgba(0,0,0,0.15)` for a modern look.             |
| `width: auto;`                      | —                                                             | Adaptive width makes sense for forms, but consider a `max-width` to control layout spread.                        |

---

### 🖋️ **Typography & Titles**

| Attribute                  | Example               | Explanation                                                                                                          |
| -------------------------- | --------------------- | -------------------------------------------------------------------------------------------------------------------- |
| `text-align: center;`      | `text-align: center;` | Used for headers — visually centers the focal point.                                                                 |
| `font-size: 1.2rem - 2rem` | `font-size: 1.5rem;`  | Medium-large text keeps your UI readable; make sure to establish a **type scale** (e.g., 0.875, 1, 1.25, 1.5, 2rem). |
| `font-weight: bold;`       | —                     | Used in titles and button text to emphasize hierarchy.                                                               |

---

### 🎯 **Buttons**

| Class                                        | Example                                                                                  | Explanation                                                                              |
| -------------------------------------------- | ---------------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------- |
| `.loginButton, .registerButton, .viewButton` | `background-color: blue; color: white; border: 0.5rem ridge black; border-radius: 2rem;` | Strong contrast, heavy border → bold look. You clearly like defined boundaries.          |
| `transition: background-color 0.5s;`         | —                                                                                        | Gives your buttons life; consider shorter durations (0.2–0.3s) for more responsive feel. |
| `:disabled` styling                          | `background-color: grey;`                                                                | Communicates disabled state visually — good accessibility practice.                      |

---

### 📋 **Forms**

| Attribute                                                     | Example                                             | Explanation                                                                  |
| ------------------------------------------------------------- | --------------------------------------------------- | ---------------------------------------------------------------------------- |
| `display: flex; flex-direction: column; align-items: center;` | `.loginForm`, `.registerForm`                       | Keeps forms clean and centered vertically.                                   |
| `label + input` spacing                                       | Currently minimal                                   | Add `margin-bottom: 0.5rem;` to separate fields visually.                    |
| `input` sizing                                                | `width: 100%; padding: 0.7rem; border-radius: 6px;` | Helps readability — you’re already using consistent spacing, which is great. |

---

### 🧩 **Header / Navigation**

| Attribute | Example                                                                                 | Explanation                                                                                                             |
| --------- | --------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------- |
| `.header` | `background-color: black; color: white; width: 60%; margin: auto; border-radius: 1rem;` | Centered layout, high contrast. Could improve spacing with `justify-content: space-between;` for better link alignment. |
| `.title`  | `margin: auto;`                                                                         | Keeps it centered but limits flexibility — use flex spacing instead.                                                    |

---

### ⚡ **Color System (Observed)**

| Color   | Usage                             | Suggestion                                                                                |
| ------- | --------------------------------- | ----------------------------------------------------------------------------------------- |
| `blue`  | Buttons, accents                  | Use `royalblue (#4169E1)` for consistency and softer visual tone.                         |
| `black` | Header background, button borders | Works well as contrast, but consider slightly lighter tones like `#1a1a1a` for modernity. |
| `grey`  | Shadows, disabled states          | Keep it subtle; switch to RGBA (`rgba(0,0,0,0.15)`) for smoother shadows.                 |
| `white` | Text on dark backgrounds          | Perfect for contrast. Add `color: #f5f5f5` instead of pure white for better eye comfort.  |

---

### 🧠 **General Design Pattern**

✅ **Strengths:**

* You naturally center and align everything properly.
* You balance color contrast well (dark/light interplay).
* You use consistent spacing and rounded corners → cohesive design language.

⚠️ **Weaknesses to refine:**

* Shadows are a bit heavy — tone them down for polish.
* Borders (ridge + thick) make UI look dated; try flat or subtle shadows instead.
* Need a clear “system” for spacing and font sizes (so every component aligns naturally).

---

### 🧰 **Next Step (Design Evolution Path)**

If you want to level this up professionally:

1. **Define spacing scale** → e.g. `4px, 8px, 12px, 16px, 24px, 32px`
2. **Define color palette** → primary, secondary, neutral, success, error
3. **Define typography scale** → base font + headers (H1–H5)
4. **Define reusable components** → buttons, cards, modals, alerts
5. **Abstract in SCSS partials** → `/styles/_buttons.scss`, `/styles/_forms.scss`, etc.

---

Would you like me to **transform this cheatsheet into a living design system** (meaning: I’ll structure your SCSS files into a scalable, reusable architecture like a mini design framework)?
That would make your app both easier to style and faster to extend visually.
