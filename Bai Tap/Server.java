import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private final static int PORT = 6868;
    private static final byte[] BUFFER = new byte[4096];

    public static void main(String[] args) {
        DatagramSocket ds = null;
        List<Student> studentList = new ArrayList<>();

        try {
            ds = new DatagramSocket(PORT);
            System.out.println("Server is listening on PORT 6868: ");
            while (true) {
                // Receive
                DatagramPacket incoming = new DatagramPacket(BUFFER, BUFFER.length);
                ds.receive(incoming);

                byte[] data = incoming.getData();
                ByteArrayInputStream in = new ByteArrayInputStream(data);
                ObjectInputStream is = new ObjectInputStream(in);

                List<Student> received = (List<Student>) is.readObject();

                // Manipulate and send back
                received.sort((s1, s2) -> {
                    if(s1.getAverageMark() > s2.getAverageMark()) return 1;
                    if(s1.getAverageMark() < s2.getAverageMark()) return -1;
                    return 0;
                });
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream ous = new ObjectOutputStream(baos);
                ous.writeObject(received);
                ous.flush();

                byte[] buffer = baos.toByteArray();
                DatagramPacket dataSendToClient = new DatagramPacket(buffer, buffer.length, incoming.getAddress(), incoming.getPort());
                ds.send(dataSendToClient);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
        if (ds != null) {
            ds.close();
        }
    }
    }
}
