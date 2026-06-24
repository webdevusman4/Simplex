package com.simplex.views;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import com.simplex.controllers.NavigationController;
import com.simplex.models.*;
import java.util.List;

public class HistoryView extends BaseView {
    private VBox view;
    private HBox filterBar;
    private VBox transactionList;
    private Transaction.Type currentFilter;

    public HistoryView(NavigationController navController) {
        super(navController);
        createView();
    }

    private void createView() {
        view = new VBox(20);
        view.getStyleClass().add("screen-container");
        view.setPadding(new Insets(20));

        // Header
        Label title = new Label("Transaction History");
        title.getStyleClass().add("screen-title");

        // Filter Bar - Allows users to sort through their saved transactions
        filterBar = new HBox(8);
        filterBar.setAlignment(Pos.CENTER_LEFT);
        filterBar.getStyleClass().add("filter-bar");

        String[][] filters = {
            {"All", null},
            {"Buy", "BUY"},
            {"Sell", "SELL"},
            {"Deposit", "DEPOSIT"},
            {"Withdraw", "WITHDRAW"},
            {"Transfer", "TRANSFER_OUT"}
        };

        for (String[] filter : filters) {
            Button btn = new Button(filter[0]);
            btn.getStyleClass().addAll("btn", "filter-btn");
            String typeStr = filter[1];
            btn.setOnAction(e -> {
                currentFilter = typeStr != null ? Transaction.Type.valueOf(typeStr) : null;
                updateFilterButtons();
                updateTransactionList();
            });
            filterBar.getChildren().add(btn);
        }

        // Transaction List Container
        transactionList = new VBox(8);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.getStyleClass().add("scroll-pane");
        scrollPane.setContent(transactionList);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        view.getChildren().addAll(title, filterBar, scrollPane);
    }

    private void updateFilterButtons() {
        for (int i = 0; i < filterBar.getChildren().size(); i++) {
            Button btn = (Button) filterBar.getChildren().get(i);
            btn.getStyleClass().remove("filter-btn-active");
            
            boolean isActive = false;
            switch (i) {
                case 0: isActive = currentFilter == null; break;
                case 1: isActive = currentFilter == Transaction.Type.BUY; break;
                case 2: isActive = currentFilter == Transaction.Type.SELL; break;
                case 3: isActive = currentFilter == Transaction.Type.DEPOSIT; break;
                case 4: isActive = currentFilter == Transaction.Type.WITHDRAW; break;
                case 5: isActive = currentFilter == Transaction.Type.TRANSFER_OUT; break;
            }
            
            if (isActive) {
                btn.getStyleClass().add("filter-btn-active");
            }
        }
    }

    private void updateTransactionList() {
        transactionList.getChildren().clear();
        
        // This method now fetches data that was persisted in the User's transaction list
        List<Transaction> transactions = dataService.getFilteredTransactions(currentFilter);

        if (transactions == null || transactions.isEmpty()) {
            Label empty = new Label("No transactions found");
            empty.getStyleClass().add("empty-text");
            transactionList.getChildren().add(empty);
            return;
        }

        for (Transaction tx : transactions) {
            transactionList.getChildren().add(createTransactionItem(tx));
        }
    }

    private HBox createTransactionItem(Transaction tx) {
        HBox item = new HBox(12);
        item.getStyleClass().add("transaction-item");
        item.setAlignment(Pos.CENTER_LEFT);
        item.setPadding(new Insets(16));

        // Type Icon representing the transaction action
        VBox iconBox = new VBox();
        iconBox.setAlignment(Pos.CENTER);
        iconBox.getStyleClass().add("tx-icon-box");
        iconBox.getStyleClass().add(getIconStyle(tx.getType()));
        
        Label icon = new Label(getTransactionIcon(tx.getType()));
        icon.getStyleClass().add("tx-icon");
        iconBox.getChildren().add(icon);

        // Transaction Details
        VBox info = new VBox(4);
        Label desc = new Label(tx.getDescription());
        desc.getStyleClass().add("tx-desc");
        Label date = new Label(tx.getFormattedDate());
        date.getStyleClass().add("tx-date");
        
        HBox statusRow = new HBox(8);
        Label status = new Label(tx.getStatus().toString());
        status.getStyleClass().addAll("tx-status", getStatusStyle(tx.getStatus()));
        statusRow.getChildren().add(status);
        
        info.getChildren().addAll(desc, date, statusRow);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Monetary Values (Crypto and PKR)
        VBox amountBox = new VBox(4);
        amountBox.setAlignment(Pos.CENTER_RIGHT);
        
        if (tx.getCrypto() != null && !tx.getCrypto().isEmpty()) {
            Label cryptoAmount = new Label(tx.getFormattedAmount());
            cryptoAmount.getStyleClass().add("tx-crypto-amount");
            amountBox.getChildren().add(cryptoAmount);
        }
        
        Label pkrAmount = new Label(tx.getFormattedPKR());
        pkrAmount.getStyleClass().add(tx.isCredit() ? "tx-credit" : "tx-debit");
        amountBox.getChildren().add(pkrAmount);

        item.getChildren().addAll(iconBox, info, spacer, amountBox);
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

    private String getIconStyle(Transaction.Type type) {
        switch (type) {
            case BUY: return "tx-icon-buy";
            case SELL: return "tx-icon-sell";
            case DEPOSIT: return "tx-icon-deposit";
            case WITHDRAW: return "tx-icon-withdraw";
            default: return "tx-icon-transfer";
        }
    }

    private String getStatusStyle(Transaction.Status status) {
        switch (status) {
            case COMPLETED: return "status-completed";
            case PENDING: return "status-pending";
            case FAILED: return "status-failed";
            default: return "";
        }
    }

    @Override
    public void refresh(Object data) {
        // Essential: When navigating to History, reset filter and reload UI
        currentFilter = null;
        updateFilterButtons();
        updateTransactionList();
    }

    @Override
    public Node getView() {
        return view;
    }
}