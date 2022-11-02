package io.github.suppennudel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import org.apache.commons.io.FilenameUtils;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

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
	private TableView<CsvBean> table;

	@FXML
	private TableColumn<CsvBean, String> colFolderName;
	@FXML
	private TableColumn<CsvBean, String> colQuantity;
	@FXML
	private TableColumn<CsvBean, String> colTradeQuantity;
	@FXML
	private TableColumn<CsvBean, String> colCardName;
	@FXML
	private TableColumn<CsvBean, String> colSetCode;
	@FXML
	private TableColumn<CsvBean, String> colSetName;
	@FXML
	private TableColumn<CsvBean, String> colCardNumber;
	@FXML
	private TableColumn<CsvBean, String> colCondition;
	@FXML
	private TableColumn<CsvBean, String> colPrinting;
	@FXML
	private TableColumn<CsvBean, String> colLanguage;
	@FXML
	private TableColumn<CsvBean, String> colPriceBought;
	@FXML
	private TableColumn<CsvBean, String> colDateBought;

	private ObservableList<CsvBean> tableData = FXCollections.observableArrayList();

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
			tableData.addAll(readCsv(file));
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
		writeCsv(chosenFile, tableData);
	}

	private void writeCsv(File file, List<CsvBean> beans) {
		try (Writer writer = new FileWriter(file)) {
			StatefulBeanToCsv<CsvBean> beanToCsv = new StatefulBeanToCsvBuilder<CsvBean>(writer)
					.withProfile(CsvBean.PROFILE_DRAGON_SHIELD)
					.withApplyQuotesToAll(false).build();
			beanToCsv.write(beans);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CsvDataTypeMismatchException e) {
			e.printStackTrace();
		} catch (CsvRequiredFieldEmptyException e) {
			e.printStackTrace();
		}
	}

	private List<CsvBean> readCsv(File file) {
		for (String profile : CsvBean.ALL_PROFILES) {
			try (FileReader reader = new FileReader(file)) {
				List<CsvBean> beans = new CsvToBeanBuilder<CsvBean>(reader).withType(CsvBean.class).withProfile(profile)
						.build().parse();
				beans.forEach(bean -> {
					if (bean.getFolderName() == null) {
						String baseName = FilenameUtils.getBaseName(file.getName());
						bean.setFolderName(baseName);
					}
				});
				return beans;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (RuntimeException e) {
				System.err.println(String.format("Profile '%s' didn't match for file %s", profile, file));
			}
		}
		return null;
	}

}
