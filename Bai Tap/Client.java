import java.io.*;
import java.net.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client {
    private final static String SERVER_IP = "127.0.0.1";
    private final static int PORT = 6868;
    private static final byte[] BUFFER = new byte[4096];

    public static void main(String[] args) {
        List<Student> studentList = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        String state = "y";

        System.out.println("Du Lieu Dang Gui Den Server\n");
        do{
            System.out.println("Ma Sinh Vien: ");
            int id = sc.nextInt();
            System.out.println("Ten Sinh Vien: ");
            String name = sc.next();
            System.out.println("Diem Trung Binh: ");
            double averageMark = sc.nextDouble();
            Student student = new Student(id, name, averageMark);
            studentList.add(student);

            System.out.println("Ban muon them sinh vien nua khong ? (y/n)");
            state = sc.next();
        }while (state.equals("y"));

        DatagramSocket ds = null;
        try {
            // Sending
            System.out.println("Sending students above ...");
            studentList.forEach(System.out::println);

            ds = new DatagramSocket();
            System.out.println("\nClient nhan , dang gui cho Server ... ");
            InetAddress ipServer = InetAddress.getByName(SERVER_IP);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream ous = new ObjectOutputStream(baos);
            ous.writeObject(studentList);
            ous.flush();

            byte[] buffer = baos.toByteArray();
            DatagramPacket data = new DatagramPacket(buffer, buffer.length, ipServer, PORT);
            ds.send(data);

            // Receiving
            DatagramPacket incoming = new DatagramPacket(BUFFER, BUFFER.length);
            ds.receive(incoming);
            byte[] dataReceivedFromServer = incoming.getData();
            ByteArrayInputStream in = new ByteArrayInputStream(dataReceivedFromServer);
            ObjectInputStream is = new ObjectInputStream(in);

            List<Student> received = (List<Student>) is.readObject();
            System.out.println("\nTop students were sorted by Server by average mark: ");
            received.forEach(System.out::println);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
        if (ds != null) {
            ds.close();
        }
    }
    }
}
