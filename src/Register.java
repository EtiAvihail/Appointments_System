
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Pattern;

public class Register implements ActionListener {

    //Creating object of JFrame class

    SystemManager system;
    JFrame optionFrame;
    JFrame doctorFrame;
    JFrame patientFrame;
    boolean isADoctor;

    //Initial fields
    JButton doctorButton = new JButton("I am a Doctor");
    JButton patientButton = new JButton("I am a Patient");

    //Both clients fields
    JLabel nameLabel = new JLabel("FIRST NAME");
    JLabel surnameLabel = new JLabel("SURNAME");
    JLabel passwordLabel = new JLabel("PASSWORD");
    JLabel confirmPasswordLabel = new JLabel("CONFIRM PASSWORD");
    JTextField nameTextField = new JTextField();
    JTextField surnameTextField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JPasswordField confirmPasswordField = new JPasswordField();
    JButton registerButton = new JButton("REGISTER");
    JButton resetButton = new JButton("RESET");

    //Doctor fields
    JLabel specialityLabel = new JLabel("SPECIALITY");
    JLabel cityLabel = new JLabel("CITY");
    JTextField specialityTextField = new JTextField();
    JTextField cityTextField = new JTextField();
    JLabel instructions1 = new JLabel("(Please write city and specialty as one word");
    JLabel instructions2 = new JLabel("For example, \"Tel-Aviv\" or \"Heart-Disease\")");

    //Patient fields
    JLabel ageLabel = new JLabel("AGE (only number)");
    JTextField ageTextField = new JTextField();

    Register(SystemManager system) {

        this.system = system;
        optionFrame = new JFrame();
        createWindow(optionFrame, "What are you?");
        doctorButton.setBounds(20, 200, 200, 40);
        patientButton.setBounds(20, 300, 200, 40);
        optionFrame.add(doctorButton);
        optionFrame.add(patientButton);
        actionEvent();
    }

    //Creating user-defined method
    public void createWindow(JFrame frame, String title) {
        //Setting properties of JFrame
        frame.setTitle(title);
        frame.setBounds(40, 40, 380, 600);
        frame.getContentPane().setBackground(Color.gray);
        frame.getContentPane().setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
    }

    public void setSharedLocationAndSize() {

        //Setting Location and Size of Each Component
        nameLabel.setBounds(20, 20, 100, 70);
        nameTextField.setBounds(100, 43, 70, 23);
        surnameLabel.setBounds(180, 20, 100, 70);
        surnameTextField.setBounds(250, 43, 100, 23);
        passwordLabel.setBounds(20, 170, 100, 70);
        confirmPasswordLabel.setBounds(20, 220, 140, 70);
        passwordField.setBounds(180, 193, 165, 23);
        confirmPasswordField.setBounds(180, 243, 165, 23);
        registerButton.setBounds(130, 350, 100, 50);

    }

    public void addSharedObjects(JFrame frame) {

        frame.add(nameLabel);
        frame.add(nameTextField);
        frame.add(passwordLabel);
        frame.add(passwordField);
        frame.add(confirmPasswordLabel);
        frame.add(confirmPasswordField);
        frame.add(registerButton);
        frame.add(resetButton);
        frame.add(surnameLabel);
        frame.add(surnameTextField);
    }

    public void actionEvent() {

        doctorButton.addActionListener(this);
        patientButton.addActionListener(this);
        registerButton.addActionListener(this);
        resetButton.addActionListener(this);
    }


    public void doctorRegister() {

        doctorFrame = new JFrame();
        createWindow(doctorFrame, "Doctor Registration");
        addSharedObjects(doctorFrame);
        doctorFrame.add(specialityLabel);
        doctorFrame.add(specialityTextField);
        doctorFrame.add(cityLabel);
        doctorFrame.add(cityTextField);
        doctorFrame.add(resetButton);
        doctorFrame.add(instructions1);
        doctorFrame.add(instructions2);

        setSharedLocationAndSize();

        specialityLabel.setBounds(20, 60, 100, 70);
        cityLabel.setBounds(20, 90, 100, 70);
        specialityTextField.setBounds(180, 80, 165, 23);
        cityTextField.setBounds(180, 117, 165, 23);
        instructions1.setBounds(20, 110, 300, 70);
        instructions2.setBounds(20, 130, 300, 70);


    }


    public void patientRegister() {

        patientFrame = new JFrame();
        createWindow(patientFrame, "Patient Registration");
        addSharedObjects(patientFrame);
        patientFrame.add(ageLabel);
        patientFrame.add(ageTextField);
        patientFrame.add(resetButton);
        setSharedLocationAndSize();
        ageLabel.setBounds(20, 60, 200, 70);
        ageTextField.setBounds(180, 80, 165, 23);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == doctorButton) {
            isADoctor = true;
            optionFrame.dispose();
            doctorRegister();
        }
        if (e.getSource() == patientButton) {
            isADoctor = false;
            optionFrame.dispose();
            patientRegister();
        }
        if (e.getSource() == resetButton) {

            nameTextField.setText("");
            specialityTextField.setText("");
            cityTextField.setText("");
            passwordField.setText("");
            confirmPasswordField.setText("");

        }
        if (e.getSource() == registerButton) {

            if (isADoctor) {

                if (passwordField.getText().equalsIgnoreCase(confirmPasswordField.getText())) {
                    try {

                        Doctor doctor = new Doctor(nameTextField.getText() + " " + surnameTextField.getText(), passwordField.getText(), specialityTextField.getText(), cityTextField.getText());

                        this.system.doctors.add(doctor);
                        this.system.doctorsFile.write((doctor.name + " " + doctor.password + " " + doctor.speciality + " " + doctor.city + "\n").getBytes());
                        doctorFrame.dispose();
                        JOptionPane.showMessageDialog(null, "Data Registered Successfully");
                        DoctorInterface doctorInterface = new DoctorInterface(doctor, system);
                        doctorInterface.openDoctorsInterface();


                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }


                } else {
                    JOptionPane.showMessageDialog(null, "password did not match");
                }

            } else {
                if (passwordField.getText().equalsIgnoreCase(confirmPasswordField.getText())) {

                    try {

                        Patient patient = new Patient(nameTextField.getText() + " " + surnameTextField.getText(), passwordField.getText(), Integer.parseInt(ageTextField.getText()));
                        this.system.patients.add(patient);
                        this.system.patientsFile.write((patient.name + " " + patient.password + " " + patient.age + "\n").getBytes());
                        patientFrame.dispose();
                        JOptionPane.showMessageDialog(null, "Data Registered Successfully");
                        PatientInterface patientInterface = new PatientInterface(patient, this.system);
                        patientInterface.openPatientInterface();


                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "password did not match");
                }

            }

        }
    }
}
