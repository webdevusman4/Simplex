package com.simplex.views;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import com.simplex.controllers.NavigationController;
import com.simplex.models.*;
import java.util.List;

public class DashboardView extends BaseView {
    private VBox view;
    private Label welcomeLabel;
    private Label balanceLabel;
    private VBox cryptoList;
    private VBox transactionList;

    public DashboardView(NavigationController navController) {
        super(navController);
        createView();
    }

    private void createView() {
        view = new VBox(20);
        view.getStyleClass().add("screen-container");
        view.setPadding(new Insets(20));

        // Header
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        header.getStyleClass().add("header");
        
        VBox userInfo = new VBox(4);
        welcomeLabel = new Label("Welcome back");
        welcomeLabel.getStyleClass().add("welcome-text");
        Label greeting = new Label("Good to see you!");
        greeting.getStyleClass().add("greeting-text");
        userInfo.getChildren().addAll(welcomeLabel, greeting);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button logoutBtn = new Button("⎋");
        logoutBtn.getStyleClass().addAll("btn", "btn-icon");
        logoutBtn.setOnAction(e -> {
            dataService.logout();
            navController.navigateTo("login");
        });

        header.getChildren().addAll(userInfo, spacer, logoutBtn);

        // Balance Card
        VBox balanceCard = new VBox(8);
        balanceCard.getStyleClass().add("balance-card");
        balanceCard.setAlignment(Pos.CENTER);
        balanceCard.setPadding(new Insets(24));

        Label balanceTitle = new Label("PKR Balance");
        balanceTitle.getStyleClass().add("balance-title");
        
        balanceLabel = new Label("PKR 0");
        balanceLabel.getStyleClass().add("balance-amount");

        HBox actionBtns = new HBox(12);
        actionBtns.setAlignment(Pos.CENTER);
        actionBtns.setPadding(new Insets(16, 0, 0, 0));

        Button buyBtn = createActionButton("Buy", "btn-success");
        buyBtn.setOnAction(e -> navController.navigateTo("buy"));
        
        Button sellBtn = createActionButton("Sell", "btn-danger");
        sellBtn.setOnAction(e -> navController.navigateTo("sell"));
        
        Button transferBtn = createActionButton("Transfer", "btn-secondary");
        transferBtn.setOnAction(e -> navController.navigateTo("transfer"));

        actionBtns.getChildren().addAll(buyBtn, sellBtn, transferBtn);
        balanceCard.getChildren().addAll(balanceTitle, balanceLabel, actionBtns);

        // Crypto Section
        Label cryptoTitle = new Label("Market");
        cryptoTitle.getStyleClass().add("section-title");

        cryptoList = new VBox(8);
        cryptoList.getStyleClass().add("crypto-list");

        // Recent Transactions
        Label transTitle = new Label("Recent Activity");
        transTitle.getStyleClass().add("section-title");

        transactionList = new VBox(8);
        transactionList.getStyleClass().add("transaction-list");

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.getStyleClass().add("scroll-pane");
        
        VBox scrollContent = new VBox(20);
        scrollContent.getChildren().addAll(balanceCard, cryptoTitle, cryptoList, transTitle, transactionList);
        scrollPane.setContent(scrollContent);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        view.getChildren().addAll(header, scrollPane);
    }

    private Button createActionButton(String text, String styleClass) {
        Button btn = new Button(text);
        btn.getStyleClass().addAll("btn", styleClass, "action-btn");
        return btn;
    }

    private HBox createCryptoCard(Crypto crypto) {
        HBox card = new HBox(12);
        card.getStyleClass().add("crypto-card");
        card.setAlignment(Pos.CENTER_LEFT);
        card.setPadding(new Insets(16));

        // Icon
        Label icon = new Label(crypto.getSymbol().substring(0, 1));
        icon.getStyleClass().add("crypto-icon");
        icon.setStyle("-fx-background-color: " + crypto.getIconColor() + ";");

        // Info
        VBox info = new VBox(2);
        Label name = new Label(crypto.getName());
        name.getStyleClass().add("crypto-name");
        Label symbol = new Label(crypto.getSymbol());
        symbol.getStyleClass().add("crypto-symbol");
        info.getChildren().addAll(name, symbol);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Price
        VBox priceBox = new VBox(2);
        priceBox.setAlignment(Pos.CENTER_RIGHT);
        Label price = new Label(crypto.getFormattedPrice());
        price.getStyleClass().add("crypto-price");
        Label change = new Label(crypto.getFormattedChange());
        change.getStyleClass().add(crypto.isPositiveChange() ? "change-positive" : "change-negative");
        priceBox.getChildren().addAll(price, change);

        card.getChildren().addAll(icon, info, spacer, priceBox);
        return card;
    }

    private HBox createTransactionItem(Transaction tx) {
        HBox item = new HBox(12);
        item.getStyleClass().add("transaction-item");
        item.setAlignment(Pos.CENTER_LEFT);
        item.setPadding(new Insets(12));

        Label icon = new Label(getTransactionIcon(tx.getType()));
        icon.getStyleClass().add("tx-icon");

        VBox info = new VBox(2);
        Label desc = new Label(tx.getDescription());
        desc.getStyleClass().add("tx-desc");
        Label date = new Label(tx.getFormattedDate());
        date.getStyleClass().add("tx-date");
        info.getChildren().addAll(desc, date);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label amount = new Label(tx.getFormattedPKR());
        amount.getStyleClass().add(tx.isCredit() ? "tx-credit" : "tx-debit");

        item.getChildren().addAll(icon, info, spacer, amount);
        return item;
    }

    private String getTransactionIcon(Transaction.Type type) {
        switch (type) {
            case BUY: return "↓";
            case SELL: return "↑";
            case DEPOSIT: return "+";
            case WITHDRAW: return "-";
            case TRANSFER_IN: return "←";
            case TRANSFER_OUT: return "→";
            default: return "•";
        }
    }

    private void updateRecentActivity(User currentUser) {
        transactionList.getChildren().clear();

        List<Transaction> transactions = currentUser.getTransactionHistory();

        if (transactions.isEmpty()) {
            Label empty = new Label("No recent activity yet. Make a deposit!");
            empty.getStyleClass().add("empty-text");
            transactionList.getChildren().add(empty);
            return;
        }

        for (Transaction transaction : transactions) {
            transactionList.getChildren().add(createTransactionItem(transaction));
        }
    }

    @Override
    public void refresh(Object data) {
        User user = dataService.getCurrentUser();
        if (user == null) return;

        welcomeLabel.setText("Welcome, " + user.getName());
        balanceLabel.setText(String.format("PKR %,.0f", user.getPkrBalance()));

        // Update crypto list
        cryptoList.getChildren().clear();
        for (Crypto crypto : dataService.getAllCryptos()) {
            cryptoList.getChildren().add(createCryptoCard(crypto));
        }

        // Update recent activity for the logged-in user only.
        updateRecentActivity(user);
    }

    @Override
    public Node getView() {
        return view;
    }
}
