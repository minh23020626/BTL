package com.example.library;

import javafx.fxml.FXML;

public class MainMenuController {
    private MainApplication mainApp;

    public void setMainApp(MainApplication mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void openManager() throws Exception {
        mainApp.showManageView();
    }

    @FXML
    private void openSearch() throws Exception {
        mainApp.showSearchView();
    }

}
