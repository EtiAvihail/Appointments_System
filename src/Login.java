import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Login extends JFrame implements ActionListener {


    SystemManager system;
    Container container = getContentPane();
    JLabel nameLabel = new JLabel("FULL NAME");
    JLabel passwordLabel = new JLabel("PASSWORD");
    JTextField nameTextField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JButton loginButton = new JButton("LOGIN");
    JButton resetButton = new JButton("RESET");
    JCheckBox showPassword = new JCheckBox("Show Password");
    JButton registerButton = new JButton("Don't have an account?");



    Login(SystemManager system) {

        this.system = system;
        this.setTitle("Login Form");
        this.setVisible(true);
        this.setBounds(10, 10, 370, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        setLayoutManager();
        setLocationAnsSize();
        addComponentsToContainer();
        addActionEvent();

    }

    public void setLayoutManager() {
        container.setLayout(null);
    }

    public void setLocationAnsSize() {
        nameLabel.setBounds(50, 130, 100, 30);
        passwordLabel.setBounds(50, 200, 100, 30);
        nameTextField.setBounds(150, 130, 150, 30);
        passwordField.setBounds(150, 200, 150, 30);
        showPassword.setBounds(150, 230, 150, 30);
        loginButton.setBounds(50, 280, 100, 30);
        resetButton.setBounds(200, 280, 100, 30);
        registerButton.setBounds(65, 380, 200, 30);

    }

    public void addComponentsToContainer() {
        container.add(nameLabel);
        container.add(passwordLabel);
        container.add(nameTextField);
        container.add(passwordField);
        container.add(showPassword);
        container.add(loginButton);
        container.add(resetButton);
        container.add(registerButton);
    }

    public void addActionEvent() {

        loginButton.addActionListener(this);
        resetButton.addActionListener(this);
        showPassword.addActionListener(this);
        registerButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //LOGIN button
        if (e.getSource() == loginButton) {

            Doctor doctor = system.getDoctor(nameTextField.getText());

            if (doctor != null){

                if(!passwordField.getText().equals(doctor.password)){
                    JOptionPane.showMessageDialog(null, "Password is incorrect!");
                }else {
                    DoctorInterface doctorInterface = new DoctorInterface(doctor, system);
                    doctorInterface.openDoctorsInterface();
                    this.dispose();
                }
            }
            else{
                Patient patient = system.getPatient(nameTextField.getText());
                if(patient != null) {
                    if(!passwordField.getText().equals(patient.password)){
                        JOptionPane.showMessageDialog(null, "Password is incorrect!");
                    }else {
                        PatientInterface patientInterface = new PatientInterface(patient, system);
                        patientInterface.openPatientInterface();
                        this.dispose();
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "No such user exists!");
                }
            }
        }

        //RESET button
        if (e.getSource() == resetButton) {
            nameTextField.setText("");
            passwordField.setText("");
        }

        //showPassword checkBox
        if (e.getSource() == showPassword) {
            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }
        }

        if(e.getSource() == registerButton){
            this.dispose();
            new Register(this.system);
        }
    }

    public static void login() {

    }

}