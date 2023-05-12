package io.github.suppennudel;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class MainController implements Initializable {

	@FXML
	private TableView<MtgCsvBean> table;

	@FXML
	private TableColumn<MtgCsvBean, String> colFolderName;
	@FXML
	private TableColumn<MtgCsvBean, String> colQuantity;
	@FXML
	private TableColumn<MtgCsvBean, String> colTradeQuantity;
	@FXML
	private TableColumn<MtgCsvBean, String> colCardName;
	@FXML
	private TableColumn<MtgCsvBean, String> colSetCode;
	@FXML
	private TableColumn<MtgCsvBean, String> colSetName;
	@FXML
	private TableColumn<MtgCsvBean, String> colCardNumber;
	@FXML
	private TableColumn<MtgCsvBean, String> colCondition;
	@FXML
	private TableColumn<MtgCsvBean, String> colPrinting;
	@FXML
	private TableColumn<MtgCsvBean, String> colLanguage;
	@FXML
	private TableColumn<MtgCsvBean, String> colPriceBought;
	@FXML
	private TableColumn<MtgCsvBean, String> colDateBought;

	private ObservableList<MtgCsvBean> tableData = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		table.setItems(tableData);

		colFolderName.setCellValueFactory(new PropertyValueFactory<>("folderName"));
		colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		colTradeQuantity.setCellValueFactory(new PropertyValueFactory<>("tradeQuantity"));
		colCardName.setCellValueFactory(new PropertyValueFactory<>("cardName"));
		colSetCode.setCellValueFactory(new PropertyValueFactory<>("setCode"));
		colSetName.setCellValueFactory(new PropertyValueFactory<>("setName"));
		colCardNumber.setCellValueFactory(new PropertyValueFactory<>("cardNumber"));
		colCondition.setCellValueFactory(new PropertyValueFactory<>("condition"));
		colPrinting.setCellValueFactory(new PropertyValueFactory<>("printing"));
		colLanguage.setCellValueFactory(new PropertyValueFactory<>("language"));
		colPriceBought.setCellValueFactory(new PropertyValueFactory<>("priceBought"));
		colDateBought.setCellValueFactory(new PropertyValueFactory<>("dateBought"));
	}

	private static final String LAST_OPENFILE_DIR = "last-openfile-dir";

	@FXML
	private void openFiles() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open collection .csv file");
		fileChooser.getExtensionFilters().setAll(new ExtensionFilter("Csv Collection File", "*.csv"));
		String lastOpenFileDir = Preferences.userRoot().get(LAST_OPENFILE_DIR, null);
		if (lastOpenFileDir != null) {
			fileChooser.setInitialDirectory(new File(lastOpenFileDir));
		}
		List<File> chosenFiles = fileChooser.showOpenMultipleDialog(Launcher.getStage());
		if (chosenFiles == null) {
			return;
		}
		tableData.clear();
		chosenFiles.forEach(file -> {
			Preferences.userRoot().put(LAST_OPENFILE_DIR, file.getParent());
			tableData.addAll(CsvHandler.readCsv(file, null));
		});
	}

	@FXML
	private void export() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open collection .csv file");
		fileChooser.getExtensionFilters().setAll(new ExtensionFilter("Csv Collection File", "*.csv"));
		String lastOpenFileDir = Preferences.userRoot().get(LAST_OPENFILE_DIR, null);
		if (lastOpenFileDir != null) {
			fileChooser.setInitialDirectory(new File(lastOpenFileDir));
		}
		File chosenFile = fileChooser.showSaveDialog(Launcher.getStage());
		if (chosenFile == null) {
			return;
		}
		CsvHandler.writeCsv(chosenFile, tableData, CsvProfile.DRAGON_SHIELD);
	}

}
