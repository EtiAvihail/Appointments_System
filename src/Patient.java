import javax.swing.*;
import java.io.IOException;

public class Patient {

    String name;
    String password;
    int age;

    public Patient(String name, String password, int age) {
        this.name = name;
        this.password = password;
        this.age = age;
    }

    public void alertTurn() {

        JOptionPane.showMessageDialog(null, name + "! it's your turn");

    }

    public void setAppointment(Doctor doctor) throws IOException {

        doctor.patients.add(this);
        doctor.patientsFile.write((this.name + " " + this.password + " " + this.age + "\n").getBytes());
    }

    //assuming each patient can set an appointment to few doctors
    public synchronized void cancelAppointment(Doctor doctor) throws IOException {

        if (doctor.patients.contains(this)) {
            doctor.patients.remove(this);
        }
        removePatientFromFile(doctor);
    }


    private void removePatientFromFile(Doctor doctor) throws IOException {

        StringBuilder toOverWrite = new StringBuilder("");
        String line;
        String[] words;

        while ((line = doctor.patientsFile.readLine()) != null) {

            words = line.split(" ");
            if ((words[0] + " " + words[1]).toLowerCase().equals(this.name.toLowerCase())) {
                if ((line = doctor.patientsFile.readLine()) != null) {
                    toOverWrite.append(line + "\n");
                }
            } else {
                toOverWrite.append(line + "\n");
            }

        }

        String[] patientsAsSentences = toOverWrite.toString().split("\n");
        for(int i = 0; i<patientsAsSentences.length; i++){
            doctor.patientsFile.write(patientsAsSentences[i].getBytes());
        }
    }
}
