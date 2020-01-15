import java.util.LinkedList;
import java.util.Queue;
import java.io.*;

public class Doctor {

    String name;
    String password;
    String speciality;
    String city;
    Queue<Patient> patients;
    RandomAccessFile patientsFile;


    public Doctor(String name, String password, String speciality, String city) throws IOException {

        this.name = name;
        this.password = password;
        this.speciality = speciality;
        this.city = city;
        this.patients = new LinkedList<>();
        try {
            patientsFile = new RandomAccessFile("C:\\Users\\Eti\\IdeaProjects\\Moveo_Project\\Lady Gaga_patients.txt", "rw");
        }catch (IOException e){
            System.out.println("there was a problem when creating the doctor's patient file");
            throw e;
        }
        this.patients = new LinkedList<>();
        getAllDoctorsPatients();
    }

    //Synchronised to avoid crashing with a patient trying to cancel
    public synchronized void handlePatients() throws IOException {

        while (!patients.isEmpty()) {

            Patient curPatient = patients.remove();
            curPatient.alertTurn();
            removePatientFromFile();
            //Handling a patient takes x time, where x = one minute
            try {
                Thread.sleep(1 * 60 * 1000);
            } catch (InterruptedException e) {
                // If interrupted, continue to the next patient
                e.printStackTrace();
            }
        }
    }

    private void removePatientFromFile() throws IOException {

        // Shift remaining lines upwards.
        long writePos = 0;
        patientsFile.readLine();
        long readPos = patientsFile.getFilePointer();

        byte[] buf = new byte[1024];
        int n;
        while (-1 != (n = patientsFile.read(buf))) {
            patientsFile.seek(writePos);
            patientsFile.write(buf, 0, n);
            readPos += n;
            writePos += n;
            patientsFile.seek(readPos);
        }

        patientsFile.setLength(writePos);
    }

    public void getAllDoctorsPatients() throws IOException {

        String line;
        String[] words;

        while ((line = this.patientsFile.readLine()) != null) {

            if(line.equals("")) break;
            else {
                words = line.split(" ");
                this.patients.add(new Patient(words[0] + " " + words[1], words[2], Integer.parseInt(words[3])));
            }
        }
    }
}
