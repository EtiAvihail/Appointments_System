import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.IOException;


public class PatientInterface extends JFrame implements ActionListener {
    JFrame frame;
    Patient patient;
    SystemManager system;

    Container container = getContentPane();
    JLabel doctorsNameLabel = new JLabel("Doctor's Name");
    JTextField doctorsNameTextField = new JTextField();
    JButton setAppointmentButton = new JButton("Set Appointment");
    JButton cancelAppointmentButton = new JButton("Cancel Appointment");

    PatientInterface(Patient patient, SystemManager system) {

        this.patient = patient;
        container.setLayout(null);
        setLocationAnsSize();
        addComponentsToContainer();
        addActionEvent();
        this.system = system;

    }

    public void setLocationAnsSize() {

        doctorsNameLabel.setBounds(50, 130, 100, 30);
        doctorsNameTextField.setBounds(150, 130, 150, 30);
        setAppointmentButton.setBounds(65, 340, 200, 30);
        cancelAppointmentButton.setBounds(65, 250, 200, 30);
    }

    public void addComponentsToContainer() {
        container.add(doctorsNameLabel);
        container.add(doctorsNameTextField);
        container.add(setAppointmentButton);
        container.add(cancelAppointmentButton);
    }

    public void addActionEvent() {

        setAppointmentButton.addActionListener(this);
        cancelAppointmentButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //LOGIN button
        if (e.getSource() == setAppointmentButton) {

            try {
                Doctor doctor = system.getDoctor(doctorsNameTextField.getText());
                if (doctor == null) {

                    JOptionPane.showMessageDialog(null, "No such Doctor exists!");
                } else {
                    patient.setAppointment(doctor);
                    JOptionPane.showMessageDialog(null, "Appointment was made!");
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if (e.getSource() == cancelAppointmentButton) {

            try {

                Doctor doctor = system.getDoctor(doctorsNameTextField.getText());
                if (doctor == null) {

                    JOptionPane.showMessageDialog(null, "No such Doctor exists!");
                } else {
                    this.patient.cancelAppointment(doctor);
                    JOptionPane.showMessageDialog(null, "Appointment is canceled!");
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void openPatientInterface() {
        PatientInterface frame = new PatientInterface(this.patient, this.system);
        frame.setTitle("Hello " + this.patient.name);
        frame.setVisible(true);
        frame.setBounds(10, 10, 370, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
    }
}
