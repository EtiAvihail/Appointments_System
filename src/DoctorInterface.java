import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;


public class DoctorInterface extends JFrame implements ActionListener {

    JFrame frame;
    Doctor doctor;
    SystemManager system;
    Container container = getContentPane();
    JButton startWorkingButton = new JButton("Start Working");
    JButton showAllMyPatientsButton = new JButton("Show my list of waiting patients");

    DoctorInterface(Doctor doctor, SystemManager system) {

        this.doctor = doctor;
        container.setLayout(null);
        startWorkingButton.setBounds(65, 200, 200, 30);
        showAllMyPatientsButton.setBounds(35, 300, 300, 30);
        container.add(startWorkingButton);
        container.add(showAllMyPatientsButton);
        startWorkingButton.addActionListener(this);
        showAllMyPatientsButton.addActionListener(this);

   }

    @Override
    public void actionPerformed(ActionEvent e) {
        //LOGIN button
        if (e.getSource() == startWorkingButton) {
            try {
                doctor.handlePatients();
                /*
                sending this message to the doctor so that a patient that arrives while this
                doctor finished his appointments list, meaning he is free will be handled.
                added this to meet the requirement "if the doctor is available, the patient will be treated immediately.."
                 */
                JOptionPane.showMessageDialog(null, "No patients at the moment, please check again now");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if(e.getSource() == showAllMyPatientsButton){

            displayPatients();
        }
    }

    public void displayPatients(){

        StringBuilder patientsList = new StringBuilder("");
        AtomicInteger i = new AtomicInteger(1);
        this.doctor.patients.forEach((p) -> patientsList.append(i.getAndIncrement() + ". " + p.name + "\n"));
        JOptionPane.showMessageDialog(null, patientsList);
    }

    public void openDoctorsInterface() {
        DoctorInterface frame = new DoctorInterface(this.doctor, this.system);
        frame.setTitle("Hello Doctor " + doctor.name + "!");
        frame.setVisible(true);
        frame.setBounds(10, 10, 370, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

    }
}
