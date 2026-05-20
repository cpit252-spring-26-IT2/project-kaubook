package sa.edu.kau.fcit.cpit252.project;

import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.net.URL;
import java.util.*;

public class MainController implements Initializable {

    @FXML private TextField searchField;
    @FXML private TableView<ListingRow>           listingsTable;
    @FXML private TableColumn<ListingRow, String> courseCol, typeCol,
            conditionCol, priceCol,
            tagsCol, ownerCol, summaryCol;
    @FXML private ComboBox<String>                sortCombo;
    @FXML private Label totalLabel, urgentLabel, verifiedLabel,
            statusLabel, userLabel, roleLabel;
    @FXML private Button removeBtn;

    private final databaseManager db      = databaseManager.getInstance();
    private final ListingManager  manager = new ListingManager();
    private String activeFilter = "ALL";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        courseCol   .setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        typeCol     .setCellValueFactory(new PropertyValueFactory<>("type"));
        conditionCol.setCellValueFactory(new PropertyValueFactory<>("condition"));
        priceCol    .setCellValueFactory(new PropertyValueFactory<>("price"));
        tagsCol     .setCellValueFactory(new PropertyValueFactory<>("tags"));
        ownerCol    .setCellValueFactory(new PropertyValueFactory<>("owner"));
        searchField.textProperty().addListener(
                (obs, oldVal, newVal) -> refreshTable()
        );
        summaryCol  .setCellValueFactory(new PropertyValueFactory<>("summary"));

        sortCombo.setItems(FXCollections.observableArrayList(
                "Price", "Type", "Course Code"
        ));
        sortCombo.setValue("Price");
        sortCombo.setOnAction(e -> refreshTable());
        searchField.textProperty().addListener(
                (obs, oldVal, newVal) -> refreshTable()
        );

        User current = db.getLoggedUser();
        if (current != null) {
            userLabel.setText("👤 " + current.getUsername());
            roleLabel.setText(current.isAdmin() ? "[ ADMIN ]" : "[ Student ]");
        }

        seedData();
        refreshTable();
    }

    private void seedData() {
        if (!db.getListings().isEmpty()) return;
        Listing s = new sellListingCreator(
                "CPIT-252", Condition.GOOD, 45.0).submitListing();
        Listing b = new borrowListingCreator(
                "MATH-101", Condition.FAIR, 7).submitListing();
        Listing e = new exchangeListingCreator(
                "PHYS-110", Condition.NEW, "CHEM-101").submitListing();
        db.addListing(new OwnedListing(s, "ahmed"));
        db.addListing(new OwnedListing(b, "sara"));
        db.addListing(new OwnedListing(
                new urgentDecorator(
                        new verifiedDecorator(e, "Sara", 4.8, 4.0)), "sara"));
    }

    @FXML
    private void filterListings(javafx.event.ActionEvent event) {
        activeFilter = (String) ((Button) event.getSource()).getUserData();
        refreshTable();
    }

    @FXML
    private void clearSearch() {
        searchField.clear();
    }

    @FXML
    private void openCreateDialog() {
        Dialog<Listing> dialog = new Dialog<>();
        dialog.setTitle("New Listing");
        dialog.setHeaderText("Fill in the listing details");

        dialog.getDialogPane().getStyleClass().add("modern-dialog");
        dialog.getDialogPane().getStylesheets().add(
                getClass().getResource("/css/style.css").toExternalForm()
        );

        ComboBox<String> typeBox = new ComboBox<>(
                FXCollections.observableArrayList("SELL", "BORROW", "EXCHANGE")
        );
        typeBox.setValue("SELL");

        TextField courseField = new TextField();
        courseField.setPromptText("e.g. CPIT-252");

        ComboBox<String> condBox = new ComboBox<>(
                FXCollections.observableArrayList("NEW", "GOOD", "FAIR")
        );
        condBox.setValue("GOOD");

        TextField extraField = new TextField();
        extraField.setPromptText("Price / Days / Wanted course");

        CheckBox urgentCheck = new CheckBox("🔥 Urgent (+5 SAR)");
        CheckBox verifiedCheck = new CheckBox("✓ Verified");

        TextField verifierField = new TextField();
        verifierField.setPromptText("Verifier name");

        TextField ratingField = new TextField();
        ratingField.setPromptText("Rating 0-5");

        typeBox.getStyleClass().add("dialog-input");
        courseField.getStyleClass().add("dialog-input");
        condBox.getStyleClass().add("dialog-input");
        extraField.getStyleClass().add("dialog-input");
        verifierField.getStyleClass().add("dialog-input");
        ratingField.getStyleClass().add("dialog-input");

        urgentCheck.getStyleClass().add("dialog-check");
        verifiedCheck.getStyleClass().add("dialog-check");

        Label typeLabel = new Label("Type:");
        Label courseLabel = new Label("Course:");
        Label conditionLabel = new Label("Condition:");
        Label extraLabel = new Label("Extra:");
        Label verifierLabel = new Label("Verifier:");
        Label ratingLabel = new Label("Rating:");
        Label noteLabel = new Label("For SELL enter price, for BORROW enter days, for EXCHANGE enter wanted course.");

        typeLabel.getStyleClass().add("dialog-label");
        courseLabel.getStyleClass().add("dialog-label");
        conditionLabel.getStyleClass().add("dialog-label");
        extraLabel.getStyleClass().add("dialog-label");
        verifierLabel.getStyleClass().add("dialog-label");
        ratingLabel.getStyleClass().add("dialog-label");
        noteLabel.getStyleClass().add("dialog-note");

        GridPane grid = new GridPane();
        grid.getStyleClass().add("dialog-content");
        grid.setHgap(12);
        grid.setVgap(12);

        grid.addRow(0, typeLabel, typeBox);
        grid.addRow(1, courseLabel, courseField);
        grid.addRow(2, conditionLabel, condBox);
        grid.addRow(3, extraLabel, extraField);
        grid.addRow(4, urgentCheck, verifiedCheck);
        grid.addRow(5, verifierLabel, verifierField);
        grid.addRow(6, ratingLabel, ratingField);
        grid.add(noteLabel, 0, 7, 2, 1);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(btn -> {
            if (btn != ButtonType.OK) return null;

            try {
                String type = typeBox.getValue();
                String course = courseField.getText().trim().toUpperCase();
                Condition cond = Condition.valueOf(condBox.getValue());
                Listing listing;

                switch (type) {
                    case "SELL" ->
                            listing = new sellListingCreator(
                                    course, cond,
                                    Double.parseDouble(extraField.getText())
                            ).submitListing();

                    case "BORROW" ->
                            listing = new borrowListingCreator(
                                    course, cond,
                                    Integer.parseInt(extraField.getText())
                            ).submitListing();

                    default ->
                            listing = new exchangeListingCreator(
                                    course, cond,
                                    extraField.getText().trim().toUpperCase()
                            ).submitListing();
                }

                if (verifiedCheck.isSelected()) {
                    listing = new verifiedDecorator(
                            listing,
                            verifierField.getText(),
                            Double.parseDouble(ratingField.getText()),
                            4.0
                    );
                }

                if (urgentCheck.isSelected()) {
                    listing = new urgentDecorator(listing);
                }

                return new OwnedListing(listing, db.getLoggedUser().getUsername());

            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR,
                        "Invalid input: " + ex.getMessage()).show();
                return null;
            }
        });

        dialog.showAndWait().ifPresent(l -> {
            db.addListing(l);
            refreshTable();
            statusLabel.setText("Added: " + l.getCourseCode());
        });
    }

    @FXML
    private void removeListing() {
        ListingRow selected = listingsTable
                .getSelectionModel().getSelectedItem();
        if (selected == null) {
            statusLabel.setText("Select a listing to remove.");
            return;
        }
        User current = db.getLoggedUser();
        boolean isOwner = selected.getOwner().equals(current.getUsername());
        boolean isAdmin = current.isAdmin();

        if (!isOwner && !isAdmin) {
            new Alert(Alert.AlertType.WARNING,
                    "You can only remove your own listings.").show();
            return;
        }

        db.removeListing(selected.getOriginalListing());
        refreshTable();
        statusLabel.setText("Removed: " + selected.getCourseCode());
    }

    @FXML
    private void logout() {
        db.logout();
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/LoginView.fxml")
            );
            Scene scene = new Scene(loader.load(), 420, 520);
            Stage stage = (Stage) listingsTable.getScene().getWindow();
            stage.setTitle("KauBook — Login");
            stage.setScene(scene);
            stage.setResizable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refreshTable() {
        switch (sortCombo.getValue()) {
            case "Price"       -> manager.setStrategy(new SortByPriceStrategy());
            case "Type"        -> manager.setStrategy(new SortByTypeStrategy());
            case "Course Code" -> manager.setStrategy(new SortByCourseCodeStrategy());
        }

        List<Listing> all = manager.sortListings(db.getListings());

        // filter by type
        if (!"ALL".equals(activeFilter))
            all = all.stream()
                    .filter(l -> l.getType().equals(activeFilter))
                    .toList();

        // filter by search text
        String query = searchField.getText().trim().toLowerCase();
        if (!query.isEmpty())
            all = all.stream()
                    .filter(l -> l.getCourseCode()
                            .toLowerCase()
                            .contains(query))
                    .toList();

        ObservableList<ListingRow> rows = FXCollections.observableArrayList();
        for (Listing l : all) rows.add(new ListingRow(l));
        listingsTable.setItems(rows);

        List<Listing> full = db.getListings();
        totalLabel   .setText("Total: "    + full.size());
        urgentLabel  .setText("Urgent: "   +
                full.stream().filter(this::hasUrgent).count());
        verifiedLabel.setText("Verified: " +
                full.stream().filter(this::hasVerified).count());
    }

    private boolean hasUrgent(Listing l) {
        Listing base = l instanceof OwnedListing ol
                ? ol.getWrappedListing() : l;
        Listing cur  = base;
        while (cur instanceof listingDecorator d) {
            if (cur instanceof urgentDecorator) return true;
            cur = d.getWrapped();
        }
        return false;
    }

    private boolean hasVerified(Listing l) {
        Listing base = l instanceof OwnedListing ol
                ? ol.getWrappedListing() : l;
        Listing cur  = base;
        while (cur instanceof listingDecorator d) {
            if (cur instanceof verifiedDecorator) return true;
            cur = d.getWrapped();
        }
        return false;
    }

    @FXML
    private void requestSelectedListing() {
        ListingRow selected = listingsTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            statusLabel.setText("Select a listing first.");
            return;
        }

        User current = db.getLoggedUser();
        if (current == null) {
            statusLabel.setText("No logged in user.");
            return;
        }

        if (selected.getOwner().equals(current.getUsername())) {
            statusLabel.setText("You cannot request your own listing.");
            return;
        }

        ListingRequest request = new ListingRequest(
                selected.getOriginalListing(),
                selected.getOwner(),
                current.getUsername()
        );

        db.addRequest(request);
        statusLabel.setText("Request sent successfully.");
    }
    @FXML
    private void reviewMyRequests() {
        User current = db.getLoggedUser();

        if (current == null) {
            statusLabel.setText("No logged in user.");
            return;
        }

        java.util.List<ListingRequest> myRequests = db.getRequestsForOwner(current.getUsername());

        if (myRequests.isEmpty()) {
            statusLabel.setText("No requests for your listings.");
            return;
        }

        ChoiceDialog<ListingRequest> dialog = new ChoiceDialog<>(myRequests.get(0), myRequests);
        dialog.setTitle("Review Requests");
        dialog.setHeaderText("Select a request");
        dialog.setContentText("Requests:");

        dialog.showAndWait().ifPresent(request -> {
            if (request.getStatus() != RequestStatus.PENDING) {
                statusLabel.setText("This request was already processed.");
                return;
            }

            Alert decision = new Alert(Alert.AlertType.CONFIRMATION);
            decision.setTitle("Approve or Reject");
            decision.setHeaderText("Requester: " + request.getRequesterUsername());
            decision.setContentText(request.getListing().getSummary());

            ButtonType approveBtn = new ButtonType("Approve");
            ButtonType rejectBtn = new ButtonType("Reject");
            ButtonType cancelBtn = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            decision.getButtonTypes().setAll(approveBtn, rejectBtn, cancelBtn);

            decision.showAndWait().ifPresent(choice -> {
                if (choice == approveBtn) {
                    Dialog<ButtonType> scheduleDialog = new Dialog<>();
                    scheduleDialog.setTitle("Schedule Pickup");
                    scheduleDialog.setHeaderText("Enter pickup details");

                    scheduleDialog.getDialogPane().getStyleClass().add("modern-dialog");
                    scheduleDialog.getDialogPane().getStylesheets().add(
                            getClass().getResource("/css/style.css").toExternalForm()
                    );

                    TextField timeField = new TextField();
                    timeField.setPromptText("e.g. Sunday 2:00 PM");
                    timeField.getStyleClass().add("dialog-input");

                    ComboBox<String> locationBox = new ComboBox<>(FXCollections.observableArrayList(
                            "FCIT Lobby",
                            "Main Library",
                            "Building 31 Entrance",
                            "Student Center"
                    ));
                    locationBox.setPromptText("Select campus location");
                    locationBox.getStyleClass().add("dialog-input");

                    Label timeLabel = new Label("Pickup Time:");
                    Label locationLabel = new Label("Campus Location:");

                    timeLabel.getStyleClass().add("dialog-label");
                    locationLabel.getStyleClass().add("dialog-label");

                    GridPane grid = new GridPane();
                    grid.getStyleClass().add("dialog-content");
                    grid.setHgap(12);
                    grid.setVgap(12);

                    grid.addRow(0, timeLabel, timeField);
                    grid.addRow(1, locationLabel, locationBox);

                    scheduleDialog.getDialogPane().setContent(grid);
                    scheduleDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

                    scheduleDialog.showAndWait().ifPresent(scheduleChoice -> {
                        if (scheduleChoice == ButtonType.OK) {
                            String pickupTime = timeField.getText().trim();
                            String campusLocation = locationBox.getValue();

                            if (pickupTime.isEmpty() || campusLocation == null || campusLocation.isEmpty()) {
                                statusLabel.setText("Pickup time and location are required.");
                                return;
                            }

                            request.schedulePickup(pickupTime, campusLocation);
                            request.approve();
                            statusLabel.setText("Request approved and pickup scheduled.");
                        }
                    });

                } else if (choice == rejectBtn) {
                    request.reject();
                    statusLabel.setText("Request rejected.");
                }
            });
        });
    }

    @FXML
    private void viewMyRequests() {
        User current = db.getLoggedUser();

        if (current == null) {
            statusLabel.setText("No logged in user.");
            return;
        }

        java.util.List<ListingRequest> myRequests =
                db.getRequestsByRequester(current.getUsername());

        if (myRequests.isEmpty()) {
            statusLabel.setText("You have not sent any requests.");
            return;
        }

        ChoiceDialog<ListingRequest> dialog =
                new ChoiceDialog<>(myRequests.get(0), myRequests);

        dialog.setTitle("My Requests");
        dialog.setHeaderText("طلباتك المرسلة");
        dialog.setContentText("Select request:");

        dialog.showAndWait().ifPresent(request -> {
            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setTitle("Request Details");
            info.setHeaderText("Request Status: " + request.getStatus());
            info.setContentText(
                    "Course: " + request.getListing().getCourseCode() + "\n" +
                            "Owner: " + request.getOwnerUsername() + "\n" +
                            "Requester: " + request.getRequesterUsername() + "\n" +
                            "Status: " + request.getStatus() + "\n" +
                            "Pickup Time: " + (request.getPickupTime() == null ? "-" : request.getPickupTime()) + "\n" +
                            "Campus Location: " + (request.getCampusLocation() == null ? "-" : request.getCampusLocation()) + "\n" +
                            "Summary: " + request.getListing().getSummary()
            );
            info.showAndWait();

            statusLabel.setText("Showing your requests.");
        });
    }
}