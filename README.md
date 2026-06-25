<div align="center">

# 💱 RuPay Exchange

### A JavaFX Cryptocurrency Exchange Desktop Application

![Java](https://img.shields.io/badge/Java-17%2B-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![JavaFX](https://img.shields.io/badge/JavaFX-17-blue?style=for-the-badge&logo=java&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-3.6%2B-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
![Theme](https://img.shields.io/badge/Theme-Dark%20%7C%20Binance--inspired-F0B90B?style=for-the-badge)
![License](https://img.shields.io/badge/License-MIT-green?style=for-the-badge)
![Status](https://img.shields.io/badge/Status-Active-brightgreen?style=for-the-badge)

*A fully functional cryptocurrency exchange simulator — buy, sell, transfer, and manage crypto with a sleek dark UI.*

![Hero Screenshot](rupay-javafx/assets/dashboard.png)

</div>

---

## 📋 Table of Contents

**Overview**
- [Features](#-features)
- [Requirements](#-requirements)
- [Getting Started](#-getting-started)
- [Demo Credentials](#-demo-credentials)
- [Project Structure](#-project-structure)
- [Color Palette](#-color-palette)

**Technical Documentation**
1. [How Components Are Connected](#1-how-components-are-connected)
2. [App Startup Flow](#2-app-startup-flow)
3. [How Login Works](#3-how-login-works)
4. [How Registration Works](#4-how-registration-works)
5. [How Data is Saved & Loaded](#5-how-data-is-saved--loaded)
6. [How Navigation Works](#6-how-navigation-works)
7. [How PIN Verification Works](#7-how-pin-verification-works)
8. [How Buy Crypto Works](#8-how-buy-crypto-works)
9. [How Sell Crypto Works](#9-how-sell-crypto-works)
10. [How Transfer Works](#10-how-transfer-works)
11. [How Wallet (Deposit & Withdraw) Works](#11-how-wallet-deposit--withdraw-works)
12. [How Transaction History Works](#12-how-transaction-history-works)
13. [How Admin Panel Works](#13-how-admin-panel-works)
14. [How All Views Share Data](#14-how-all-views-share-data)

---

## ✨ Features

| Category | Details |
|---|---|
| 🖥️ **Screens** | Login, Registration, Dashboard, Buy, Sell, Transfer, Wallet, History, PIN Popup, Admin Panel |
| 🎨 **Theme** | Dark mode with Binance-inspired gold accent colors |
| 🔐 **Security** | 4-digit PIN required before every financial transaction |
| 💾 **Persistence** | Binary file-based local storage (`rupay_db.dat`) |
| 👤 **Roles** | Separate user and admin accounts with different access |
| 🪙 **Crypto** | Supports BTC, ETH, USDT, BNB, XRP |
| 📊 **History** | Full transaction log with type-based filtering |
| 🧪 **Testing** | Mock data — no backend required |

---

## 📸 Screenshots

<table>
  <tr>
    <td align="center"><b>Login</b></td>
    <td align="center"><b>Dashboard</b></td>
    <td align="center"><b>Buy Crypto</b></td>
  </tr>
  <tr>
    <td><img src="rupay-javafx/assets/login.png" alt="Login Screen"/></td>
    <td><img src="rupay-javafx/assets/dashboard.png" alt="Dashboard"/></td>
    <td><img src="rupay-javafx/assets/buy.png" alt="Buy Crypto"/></td>
  </tr>
  <tr>
    <td align="center"><b>Wallet</b></td>
    <td align="center"><b>Transaction History</b></td>
    <td align="center"><b>Admin Panel</b></td>
  </tr>
  <tr>
    <td><img src="rupay-javafx/assets/wallet.png" alt="Wallet"/></td>
    <td><img src="rupay-javafx/assets/history.png" alt="History"/></td>
    <td><img src="rupay-javafx/assets/admin_panel.png" alt="Admin Panel"/></td>
  </tr>
</table>

---

## 📦 Requirements

- **Java** 17 or higher
- **Maven** 3.6+

---

## 🚀 Getting Started

### Option 1 — Maven (Recommended)

```bash
cd rupay-javafx
mvn clean javafx:run
```

### Option 2 — IDE

1. Open the project as a **Maven project** in IntelliJ IDEA, Eclipse, or VS Code
2. Run the `RuPayApp.java` main class

---

## 🔑 Demo Credentials

| Role | Email | Password | PIN |
|---|---|---|---|
| 👤 User | `ahmed@example.com` | any | `1234` |
| 🛡️ Admin | `admin@rupay.com` | any | `0000` |

---

## 🗂️ Project Structure

```
rupay-javafx/
├── pom.xml                              # Maven configuration
└── src/main/
    ├── java/com/rupay/
    │   ├── RuPayApp.java              # Application entry point
    │   ├── controllers/
    │   │   └── NavigationController.java
    │   ├── models/
    │   │   ├── User.java
    │   │   ├── Crypto.java
    │   │   ├── Transaction.java
    │   │   └── WithdrawRequest.java
    │   ├── services/
    │   │   └── DataService.java         # Business logic & mock data (Singleton)
    │   └── views/
    │       ├── BaseView.java            # Shared base for all screens
    │       ├── LoginView.java
    │       ├── RegisterView.java
    │       ├── DashboardView.java
    │       ├── BuyView.java
    │       ├── SellView.java
    │       ├── TransferView.java
    │       ├── WalletView.java
    │       ├── HistoryView.java
    │       ├── PinPopupView.java
    │       └── AdminView.java
    └── resources/styles/
        └── main.css                     # Dark theme stylesheet
```

---

## 🎨 Color Palette

| Role | Hex | Preview |
|---|---|---|
| Background | `#0B0E11` | ⬛ Deep black |
| Card | `#1E2329` | 🔲 Dark gray |
| Gold Accent | `#F0B90B` | 🟡 Binance gold |
| Success | `#0ECB81` | 🟢 Green |
| Error | `#F6465D` | 🔴 Red |
| Text | `#EAECEF` | ⬜ Off-white |

---

<div align="center">

# 🔧 Technical Flow & System Documentation

*How the app is connected, how data flows, and how every operation works internally.*

</div>

---

## 🔗 1. How Components Are Connected

Every part of the app has a specific role. Here is how they connect:

```
┌─────────────────────────────────────────────────────────┐
│                    RuPayApp.java                        │
│              (Entry point — starts everything)          │
└───────────────────────┬─────────────────────────────────┘
                        │ creates
                        ▼
┌─────────────────────────────────────────────────────────┐
│              NavigationController.java                  │
│   • Manages which screen is currently visible           │
│   • Creates all 9 View objects at startup               │
│   • Shows/hides the bottom navigation bar               │
└──────┬──────────────────────────────────────────────────┘
       │ creates & stores
       ▼
┌─────────────────────────────────────────────────────────┐
│        All Views  (LoginView, DashboardView, etc.)      │
│   • Each view extends BaseView                          │
│   • BaseView gives every view:                          │
│       1. navController  →  to switch screens            │
│       2. dataService    →  to read/write data           │
└──────┬──────────────────────────────────────────────────┘
       │ all views call
       ▼
┌─────────────────────────────────────────────────────────┐
│                   DataService.java                      │
│   • Single Singleton instance shared across the app     │
│   • Holds all users, cryptos, and transactions          │
│   • Reads from and writes to  rupay_db.dat              │
└──────┬──────────────────────────────────────────────────┘
       │ reads / writes
       ▼
┌─────────────────────────────────────────────────────────┐
│                    rupay_db.dat                         │
│   • Binary file persisted on disk                       │
│   • Stores: users, passwords, withdrawal requests       │
└─────────────────────────────────────────────────────────┘
```

> **Key rule:** Views never communicate with each other directly. All data goes through `DataService`, and all screen changes go through `NavigationController`.

---

## ▶️ 2. App Startup Flow

```
mvn javafx:run   (or)   run-presentation.bat
        │
        ▼
RuPayApp.main()  →  JavaFX launch()
        │
        ▼
RuPayApp.start(Stage)
  ├── new NavigationController()
  │       ├── Creates root StackPane container
  │       ├── Instantiates all 9 View objects (stored in a Map)
  │       └── Creates bottom nav bar (hidden initially)
  │
  ├── DataService.getInstance()  [Singleton created]
  │       ├── loadDatabase()
  │       │     ├── File exists?  → load users, passwords, withdrawals
  │       │     └── First run?    → create demo data + save file
  │       └── initializeCryptos() → BTC, ETH, USDT, BNB, XRP prices
  │
  ├── Window: 400×800px, title = "RuPay Exchange", non-resizable
  └── navigateTo("login")
```

---

## 🔓 3. How Login Works

![Login Screen](rupay-javafx/assets/login.png)

The **Login Screen** is the app's entry point. The user provides their email and password to authenticate.

```
User types email + password  →  clicks "Sign In"
        │
        ▼
LoginView.handleLogin()
  ├── Empty fields?  →  error: "Please enter both email and password"
  └── DataService.login(email, password)
              │
              ▼
        ├── loadDatabase()       ← sync from disk first
        ├── Normalize email to lowercase
        ├── Look up user & password in Maps
        │
        ├── Match found  →  currentUser = user  →  return User
        └── No match     →  return null
              │
        ◄─────┘
        │
  ├── null    →  error: "Invalid credentials"
  └── User    →  isAdmin?
                  ├── true   →  navigateTo("admin")
                  └── false  →  navigateTo("dashboard")
```

**Authentication snippet:**

```java
// DataService.login()
User user             = users.get(cleanEmail);
String savedPassword  = userPasswords.get(cleanEmail);

if (user != null && savedPassword != null && savedPassword.equals(password)) {
    currentUser = user;  // remember session
    return user;         // ✅ success
}
return null;             // ❌ fail
```

---

## 📝 4. How Registration Works

![Register Screen](rupay-javafx/assets/register.png)

The **Register Screen** lets new users create an account with name, email, password, and a 4-digit PIN.

### Validation Rules

| # | Field | Rule | Error Message |
|---|---|---|---|
| 1 | Name | Must not be empty | `"Please enter your name"` |
| 2 | Email | Must end with `@gmail.com`, `@yahoo.com`, `@outlook.com`, or `@example.com` | `"Please use a valid Gmail, Yahoo, or Outlook email"` |
| 3 | Password | Minimum 6 characters | `"Password must be at least 6 characters"` |
| 4 | PIN | Exactly 4 numeric digits | `"PIN must be 4 digits"` |
| 5 | PIN confirm | Must match PIN | `"PINs do not match"` |
| 6 | Email | Must not already exist | `"Email already registered"` |

### Registration Flow

```
All validations pass
        │
        ▼
RegisterView  →  DataService.register(name, email, password, pin)
        │
        ▼
DataService.register()
  ├── loadDatabase()              ← check for latest registrations on disk
  ├── Normalize email to lowercase
  ├── Email already exists?  →  return null
  │
  ├── Create new User:
  │       ├── id             = random UUID
  │       ├── name, email, pin  from form
  │       ├── pkrBalance     = 0.0
  │       ├── isAdmin        = false
  │       └── cryptoHoldings = { BTC:0, ETH:0, USDT:0, BNB:0, XRP:0 }
  │
  ├── Add to users & userPasswords Maps
  ├── saveDatabase()             ← persist immediately
  ├── currentUser = new user
  └── return User
        │
        ▼
RegisterView  →  navigateTo("dashboard")
```

---

## 💾 5. How Data is Saved & Loaded

All app state is persisted in a single binary file: **`rupay_db.dat`**

### Stored Data Structure

```
rupay_db.dat
├── users       →  Map<String, User>          (email → User)
├── passwords   →  Map<String, String>        (email → password)
└── withdrawals →  List<WithdrawRequest>
```

> ℹ️ Crypto market prices are **not** persisted — they are re-initialized fresh on every startup.

### Save Process

```
DataService.saveDatabase()
  ├── Bundle { users, passwords, withdrawals } into a HashMap
  ├── Open FileOutputStream → rupay_db.dat
  ├── Wrap in ObjectOutputStream
  └── Write entire bundle as one serialized object
```

### Load Process

```
DataService.loadDatabase()
  ├── File exists?
  │     ├── YES → ObjectInputStream → extract users, passwords, withdrawals
  │     └── NO  → initializeFreshData()
  │                   ├── Create demo user "Ahmed Khan"
  │                   ├── Create admin account
  │                   ├── Add sample withdrawal request
  │                   └── saveDatabase()  ← write file for first time
```

### When Does a Save Trigger?

| Action | Saves to Disk |
|---|:---:|
| Register new user | ✅ |
| Buy crypto | ✅ |
| Sell crypto | ✅ |
| Transfer crypto | ✅ |
| Deposit PKR | ✅ |
| Request withdrawal | ✅ |
| Admin approve / reject withdrawal | ✅ |
| Login / Logout / Navigate | ❌ |

---

## 🧭 6. How Navigation Works

![Dashboard Screen](rupay-javafx/assets/dashboard.png)

The `NavigationController` manages all screen switching using a pre-built `Map` of all views:

```
views = {
    "login"     → LoginView
    "register"  → RegisterView
    "dashboard" → DashboardView
    "buy"       → BuyView
    "sell"      → SellView
    "transfer"  → TransferView
    "wallet"    → WalletView
    "history"   → HistoryView
    "admin"     → AdminView
}
```

### navigateTo("dashboard") — Step by Step

```
1. Look up "dashboard" in views Map  →  get DashboardView object
2. Bottom nav visibility:
       login / register / admin  →  HIDE
       all other screens         →  SHOW
3. Highlight the active nav button (Home / Wallet / History)
4. view.refresh(data)            ←  reload content from DataService
5. Clear content area
6. Add new view's UI into content area
```

### Layout Hierarchy

```
Window (400 × 800)
└── StackPane (root)
    ├── VBox
    │   ├── StackPane  ← active screen rendered here
    │   └── HBox       ← bottom nav: Home | Wallet | History
    │
    └── PinPopupView   ← overlay, shown on top when PIN is required
```

---

## 🔐 7. How PIN Verification Works

![PIN Popup](rupay-javafx/assets/pin_popup.png)

A PIN is required before every financial transaction (Buy, Sell, Transfer, Withdraw).

```
User clicks "Buy Now" / "Sell Now" / "Transfer" / "Request Withdrawal"
        │
        ▼
navController.showPinPopup(onSuccessCallback)
  └── Creates PinPopupView, overlays on top of StackPane

User sees PIN popup  (number pad + 4 indicator dots)
        │
        ▼
PinPopupView.handleNumber()
  ├── Append digit to pinBuilder
  ├── Update dots:  ○ → ●
  └── 4th digit entered?  →  auto-trigger verifyPin()

PinPopupView.verifyPin()
  ├── DataService.verifyPin(pin)
  │       └── currentUser.getPin().equals(enteredPin)
  │
  ├── ✅ CORRECT  →  closePinPopup()  →  run onSuccess callback
  └── ❌ WRONG    →  show "Invalid PIN"  →  clear dots, allow retry
```

---

## 🟢 8. How Buy Crypto Works

![Buy Crypto Screen](rupay-javafx/assets/buy.png)

The **Buy Crypto** screen lets users purchase any supported cryptocurrency using their PKR balance. The total cost is calculated live based on the current mock market price.

```
User selects crypto + enters amount  →  "Buy Now"
        │
        ▼
BuyView.handleBuy()
  ├── Crypto selected?              ← validate
  ├── Amount is a positive number?  ← validate
  ├── total cost = amount × crypto.priceInPKR
  ├── user.pkrBalance >= total?     ← else: "Insufficient balance"
  └── showPinPopup(callback)

[PIN ✅]
        │
        ▼
DataService.buyCrypto(symbol, amount)
  ├── Deduct cost from pkrBalance
  ├── Add amount to cryptoHoldings[symbol]
  ├── Create Transaction(BUY, COMPLETED)
  ├── Prepend to transactionHistory
  └── saveDatabase()

→ navigateTo("dashboard")
```

---

## 🔴 9. How Sell Crypto Works

![Sell Crypto Screen](rupay-javafx/assets/sell.png)

```
User selects crypto + enters amount  →  "Sell Now"
        │
        ▼
SellView.handleSell()
  ├── Crypto selected?                    ← validate
  ├── Amount is positive?                 ← validate
  ├── cryptoHoldings[symbol] >= amount?   ← else: "Insufficient crypto balance"
  └── showPinPopup(callback)

[PIN ✅]
        │
        ▼
DataService.sellCrypto(symbol, amount)
  ├── value = amount × crypto.priceInPKR
  ├── Deduct amount from cryptoHoldings[symbol]
  ├── Add value to pkrBalance
  ├── Create Transaction(SELL, COMPLETED)
  └── saveDatabase()
```

---

## ↗️ 10. How Transfer Works

![Transfer Crypto Screen](rupay-javafx/assets/transfer.png)

```
User selects crypto + recipient email + amount  →  "Transfer"
        │
        ▼
TransferView.handleTransfer()
  ├── All fields filled?                         ← validate
  ├── Recipient email ≠ current user email?       ← can't send to yourself
  └── showPinPopup(callback)

[PIN ✅]
        │
        ▼
DataService.transferCrypto(symbol, amount, recipientEmail)
  ├── loadDatabase()             ← get latest recipient state from disk
  ├── Recipient exists in users Map?
  ├── Sender has sufficient holdings?
  │
  ├── SENDER
  │     ├── Deduct amount from cryptoHoldings[symbol]
  │     └── Create Transaction(TRANSFER_OUT)
  │
  ├── RECIPIENT
  │     ├── Add amount to cryptoHoldings[symbol]
  │     └── Create Transaction(TRANSFER_IN)
  │
  └── saveDatabase()             ← persist both users
```

---

## 👛 11. How Wallet (Deposit & Withdraw) Works

![Wallet Screen](rupay-javafx/assets/wallet.png)

![Wallet Forms](rupay-javafx/assets/wallet_forms.png)

### Deposit PKR — Instant, No PIN Required

```
User enters amount  →  "Deposit"
        │
        ▼
DataService.depositPKR(amount)
  ├── Add amount to user.pkrBalance
  ├── Create Transaction(DEPOSIT, COMPLETED)
  └── saveDatabase()
```

### Withdraw PKR — Requires Admin Approval

```
User enters amount + bank account  →  "Request Withdrawal"
        │
        ▼
showPinPopup(callback)

[PIN ✅]
        │
        ▼
DataService.requestWithdraw(amount, bankAccount)
  ├── Create WithdrawRequest  { status = PENDING }
  ├── Create Transaction(WITHDRAW, PENDING)
  └── saveDatabase()
```

> ⚠️ **Note:** The PKR balance is **not deducted** when the request is submitted. It is only deducted when an Admin **approves** it.

---

## 📜 12. How Transaction History Works

![Transaction History Screen](rupay-javafx/assets/history.png)

```
HistoryView.refresh()
        │
        ▼
DataService.getUserTransactions()
  └── Returns currentUser.transactionHistory  (newest first)
```

Each transaction is displayed with:

| Field | Details |
|---|---|
| **Icon** | ↓ Buy · ↑ Sell · ＋ Deposit · － Withdraw · ← Transfer In · → Transfer Out |
| **Description** | e.g., `"Bought BTC"`, `"Sold ETH"`, `"Transfer to alice@example.com"` |
| **Timestamp** | Date and time of the operation |
| **Amount** | 🟢 Green = credit · 🔴 Red = debit |
| **Status** | `COMPLETED` · `PENDING` · `FAILED` |

**Filter bar:** `All` | `Buy` | `Sell` | `Deposit` | `Withdraw` | `Transfer`

```
DataService.getFilteredTransactions(type)
  └── Filters transactionHistory by Transaction.Type
```

---

## 🛡️ 13. How Admin Panel Works

![Admin Panel](rupay-javafx/assets/admin_panel.png)

The Admin Panel is only accessible via `admin@rupay.com`.

### Viewing All Users

```
AdminView.refresh()
  └── DataService.getAllUsers()
          └── All values from users Map
                  ↓
          Filter out admin accounts (isAdmin = true)
          Display per regular user:
            • Name & email
            • PKR balance
            • Crypto holdings (BTC, ETH, USDT if > 0)
```

### Approve Withdrawal

```
Admin clicks "✓"
        │
        ▼
DataService.approveWithdrawal(requestId)
  ├── Find WithdrawRequest by ID
  ├── Set status = APPROVED
  ├── Deduct request.amount from user.pkrBalance   ← deducted HERE
  └── saveDatabase()
```

### Reject Withdrawal

```
Admin clicks "✕"
        │
        ▼
DataService.rejectWithdrawal(requestId)
  ├── Find WithdrawRequest by ID
  ├── Set status = REJECTED   (balance is never touched)
  └── saveDatabase()
```

---

## 🔄 14. How All Views Share Data

Every view extends `BaseView`, which wires up access to both the navigation layer and the data layer:

```java
// BaseView.java
public abstract class BaseView {
    protected NavigationController navController;  // screen switching
    protected DataService dataService;             // data access

    public BaseView(NavigationController navController) {
        this.navController = navController;
        this.dataService = DataService.getInstance(); // same singleton every time
    }
}
```

Because `DataService` is a **Singleton**, every view always reads and writes to the **same single instance** — changes in one view are immediately reflected across all others.

### The `refresh()` Pattern

Every view implements a `refresh()` method. `navigateTo()` always calls `refresh()` before rendering the view, guaranteeing the latest data is always shown.

```
navigateTo("wallet")
  └── walletView.refresh(null)
          ├── getCurrentUser()     → latest user from memory
          ├── Update balance label
          ├── Rebuild holdings list
          └── Clear all form fields
```

### End-to-End Data Flow

```
User Action  (e.g., button click)
        │
        ▼
View Method  (e.g., handleBuy())
        │
        ▼
DataService  (e.g., buyCrypto())
  ├── Update User object in memory
  └── saveDatabase()  →  write to rupay_db.dat
        │
        ▼
navigateTo()  →  refresh()  on destination view
        │
        ▼
View reads updated data from DataService  →  UI reflects changes
```

---

![Dashboard Activity](rupay-javafx/assets/dashboard_activity.png)

*The Dashboard's Recent Activity section reflects all transactions in real time after every operation.*

---

<div align="center">

*RuPay Exchange — v1.0-SNAPSHOT · Built with JavaFX · Dark Theme · Mock Data*

</div>

---

## ⚠️ Disclaimer

> RuPay Exchange is a **simulation application** built for educational and demonstration purposes only.
> No real cryptocurrency, real money, or real blockchain transactions are involved.
> All data is stored locally and all prices are mock values.

---

## 🤝 Contributing

Contributions, issues, and feature requests are welcome!

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/your-feature-name`
3. Commit your changes: `git commit -m 'Add some feature'`
4. Push to the branch: `git push origin feature/your-feature-name`
5. Open a Pull Request

---

## 📄 License

This project is licensed under the **MIT License** — see the [LICENSE](LICENSE) file for details.

```
MIT License  Copyright (c) 2024 RuPay Exchange
Permission is hereby granted, free of charge, to any person obtaining a copy
of this software to use, copy, modify, merge, publish, distribute, sublicense,
and/or sell copies of the Software, subject to the above copyright notice.
```

---

<div align="center">

Made with ❤️ using **JavaFX** &nbsp;|&nbsp; Inspired by **Binance**

</div>
