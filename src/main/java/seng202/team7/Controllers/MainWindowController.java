package seng202.team7.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import seng202.team7.Windows.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controls all main window switching for main application
 * @author Connor McEwan-McDowall
 */
public class MainWindowController implements Initializable{

    @FXML public BorderPane mainBorderPane;
    @FXML public TreeView<String> navigationTree;
    @FXML public AnchorPane centerAnchorPane;
    @FXML public MenuItem mainMenuExitButton;
    @FXML public Menu mainMenuHelpButton;

    private HomeWindow homeViewer;
    private RoutePlannerViewerWindow routePlannerViewer;
    private MapViewerWindow mapViewer;
    private RetailerDataViewerWindow retailerViewer;
    private WifiDataViewerWindow wifiViewer;
    private TripDataViewerWindow tripViewer;
    private DataEntryWindow dataEntryViewer;
    private MapAnalyticWindow mapAnalyticViewer;
    private TripAnalyticWindow graphViewer;
    private String currentWindow; // Currently visible window

    /**
     * Runs as the program initially starts. Initialises each of the custom
     * JavaFX panels (loads them into memory) and calls for the navigation bar
     * to be populated. Home window is shown initially as default
     *
     * @param location Unused parameter (There to fit signature)
     * @param resources Unused parameter (There to fit signature)
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set home window as initial window
        homeViewer = new HomeWindow();
        centerAnchorPane.getChildren().add(homeViewer);
        currentWindow = "Home";

        // Initialize different custom panels (Saves loading panel again every time panel is changed)
        routePlannerViewer = new RoutePlannerViewerWindow();
        mapViewer = new MapViewerWindow();
        retailerViewer = new RetailerDataViewerWindow();
        wifiViewer = new WifiDataViewerWindow();
        tripViewer = new TripDataViewerWindow();
        dataEntryViewer = new DataEntryWindow();
        mapAnalyticViewer = new MapAnalyticWindow();
        graphViewer = new TripAnalyticWindow();

        populateNavigationBar();
    }

    /**
     * Populates the navigation bar with all necessary tree items. Also creates
     * an event listener for whenever a tree item in the navigation bar is clicked.
     * This listener calls the changeMainScreen function when tree selection changes.
     */
    private void populateNavigationBar() {
        // Tree root
        TreeItem<String> root = new TreeItem<>();
        root.setExpanded(true);

        // Home branch
        makeBranch("Home", root);

        // Route Planner branch
        makeBranch("Route Planning", root);

        // Map Viewer branch
        makeBranch("Map Viewer", root);

        // Analytics branch
        TreeItem<String> analyticsBranch = makeBranch("Analytics", root);
        //makeBranch("Map View", analyticsBranch);
        makeBranch("Graph View", analyticsBranch);

        // Raw Data Viewer branch
        TreeItem<String> dataViewerBranch = makeBranch("Data Viewer", root);
        makeBranch("Retailer", dataViewerBranch);
        makeBranch("Trip", dataViewerBranch);
        makeBranch("Wifi", dataViewerBranch);

        // Data Entry branch
        makeBranch("Data Entry", root);

        navigationTree.setRoot(root);
        navigationTree.setShowRoot(false);

        navigationTree.getSelectionModel().selectedItemProperty()
                .addListener((v, oldValue, newValue) -> {
                    String currentSelection = newValue.getValue();
                    if (!(currentWindow.equals(currentSelection))) {
                        changeMainScreen(currentSelection);
                    }
                });
    }

    /**
     * Creates a new TreeItem with Strings representing a branch. The new branch has the
     * text title in it and is a child of parent.
     * @param title A String representing the text title for the branch.
     * @param parent The parent TreeItem of new branch to be created.
     * @return The newly created branch (TreeItem).
     */
    private TreeItem<String> makeBranch(String title, TreeItem<String> parent) {
        TreeItem<String> item = new TreeItem<>(title);
        parent.getChildren().add(item);
        return item;
    }

    /**
     * Changes the window currently shown in the centerAnchorPane to be newScreen
     * @param newScreen The new window that will replace the current window
     */
    private void changeMainScreen(String newScreen) {
        // Removes the current window to make room for the new window
        switch (currentWindow) {
            case "Home": removeMainScreen(homeViewer); break;
            case "Route Planning": removeMainScreen(routePlannerViewer); break;
            case "Map Viewer": removeMainScreen(mapViewer); break;
            case "Analytics": break; // todo
            case "Map View": removeMainScreen(mapAnalyticViewer); break;
            case "Graph View": removeMainScreen(graphViewer); break;
            case "Data Viewer": break; // todo
            case "Retailer": removeMainScreen(retailerViewer); break;
            case "Trip": removeMainScreen(tripViewer); break;
            case "Wifi": removeMainScreen(wifiViewer); break;
            case "Data Entry": removeMainScreen(dataEntryViewer); break;
            default: System.out.println("ERROR: No such removal handle exists");
        }
        // Add the new window where the old one was
        switch (newScreen) {
            case "Home": setMainScreen(homeViewer); break;
            case "Route Planning": setMainScreen(routePlannerViewer); break;
            case "Map Viewer": setMainScreen(mapViewer); break;
            case "Analytics": break; // todo
            case "Map View": setMainScreen(mapAnalyticViewer); break;
            case "Graph View": setMainScreen(graphViewer); break;
            case "Data Viewer": break; // todo
            case "Retailer": setMainScreen(retailerViewer = new RetailerDataViewerWindow()); break; // Recreates viewer
            case "Trip": setMainScreen(tripViewer = new TripDataViewerWindow()); break; // Recreates viewer
            case "Wifi": setMainScreen(wifiViewer = new WifiDataViewerWindow()); break; // Recreates viewer
            case "Data Entry": setMainScreen(dataEntryViewer); break;
            default: System.out.println("ERROR: No such set handle exists");
        }
        // Update window tracker
        currentWindow = newScreen;
    }

    /**
     * Removes the current window inside centerAnchorPane
     * @param window The window to be removed
     */
    private void removeMainScreen(Node window) {
        centerAnchorPane.getChildren().remove(window);
    }

    /**
     * Adds the current window inside centerAnchorPane
     * @param window The window to be added
     */
    private void setMainScreen(Node window) {
        centerAnchorPane.getChildren().add(window);
    }
}