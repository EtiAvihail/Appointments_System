import java.io.*;
import java.util.ArrayList;

public class SystemManager {

    ArrayList<Doctor> doctors;
    ArrayList<Patient> patients;
    RandomAccessFile doctorsFile;
    RandomAccessFile patientsFile;

    public SystemManager() throws IOException {

        this.doctorsFile = new RandomAccessFile("doctors.txt", "rw");
        this.patientsFile = new RandomAccessFile("patients.txt", "rw");
        getDataFromFiles();
        new Login(this);
    }

    public void getDataFromFiles() throws IOException {

        this.doctors = getAllDoctors();
        this.patients = getAllPatients();

        this.doctors.forEach((d) -> {
            try {
                d.getAllDoctorsPatients();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    public ArrayList<Doctor> getAllDoctors() throws IOException {

        ArrayList<Doctor> doctors = new ArrayList<>();
        String line = null;
        String[] words;

        while ((line = doctorsFile.readLine()) != null) {

            if (line.equals("")) break;
            else {
                words = line.split(" ");
                doctors.add(new Doctor(words[0] + " " + words[1], words[2], words[3], words[4]));
            }
        }
        return doctors;
    }

    public ArrayList<Patient> getAllPatients() throws IOException {

        ArrayList<Patient> patients = new ArrayList<>();
        String line;
        String[] words;
        while ((line = patientsFile.readLine()) != null) {

            if (line.equals("")) break;
            else {
                words = line.split(" ");
                patients.add(new Patient(words[0] + " " + words[1], words[2], Integer.parseInt(words[3])));
            }
        }

        return patients;
    }

    public Doctor getDoctor(String name) {

        for (int i = 0; i < doctors.size(); i++) {

            if (doctors.get(i).name.toLowerCase().equals(name.toLowerCase())) {

                return doctors.get(i);
            }
        }
        return null;
    }

    public Patient getPatient(String name) {

        for (int i = 0; i < patients.size(); i++) {
            if (patients.get(i).name.toLowerCase().equals(name.toLowerCase()))
                return patients.get(i);
        }
        return null;
    }
}
