package de.layla.abs.mainwindow;

import de.layla.abs.appointment.AppointmentBooker;
import de.layla.abs.appointment.AppointmentType;
import de.layla.abs.user.UserModel;
import de.layla.abs.appointment.AppointmentModel;
import de.layla.abs.utils.OptionsGetter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    @FXML
    private GridPane root;
    @FXML
    private Button bookAppointment;
    @FXML
    private TextField name;
    @FXML
    private TextField mailAddress;
    @FXML
    private TextField phoneNumber;
    @FXML
    private ComboBox<AppointmentType> serviceSelection;
    @FXML
    private TextField region;
    @FXML
    private TextField time;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // filling service selection
        for (AppointmentType a : AppointmentType.values()) {
            this.serviceSelection.getItems().add(a);
        }
        // customized DatePicker
        OptionsGetter.getMonthsWithYears();
        ArrayList<String> months = OptionsGetter.getMonths();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM", Locale.GERMAN);

        LocalDate minDate = LocalDate.of(Integer.parseInt(OptionsGetter.getYears().get(0)),
                Month.from(formatter.parse(months.get(0))), 1);

        LocalDate maxDate = LocalDate.of(Integer.parseInt(OptionsGetter.getYears().get(1)),
                Month.from(formatter.parse(months.get(1))),
                Month.from(formatter.parse(months.get(1))).length(false));

        DatePicker datePicker = new DatePicker();
        datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate localDate, boolean empty) {
                super.updateItem(localDate, empty);
                if (localDate.isBefore(minDate) || localDate.isAfter(maxDate)) {
                    setDisable(true);
                }
            }
        });
        this.root.add(datePicker, 1, 9);
    }

    private UserModel fetchUserData() {
        return new UserModel(this.name.getText(), this.mailAddress.getText(), this.phoneNumber.getText());
    }

    private AppointmentModel fetchAppointmentData() {
        // TODO: fetch appointment data
        return null;
    }

    @FXML
    private void book(ActionEvent actionEvent) {
        Runnable runnable = () -> {
            new AppointmentBooker(fetchAppointmentData(), fetchUserData());
        };
        Thread t = new Thread(runnable);
        t.start();
    }

    public Button getBookAppointment() {
        return bookAppointment;
    }

    public void setBookAppointment(Button bookAppointment) {
        this.bookAppointment = bookAppointment;
    }

    public TextField getName() {
        return name;
    }

    public void setName(TextField name) {
        this.name = name;
    }

    public TextField getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(TextField mailAddress) {
        this.mailAddress = mailAddress;
    }

    public TextField getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(TextField phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public TextField getRegion() {
        return region;
    }

    public void setRegion(TextField region) {
        this.region = region;
    }

    public TextField getTime() {
        return time;
    }

    public void setTime(TextField time) {
        this.time = time;
    }
}