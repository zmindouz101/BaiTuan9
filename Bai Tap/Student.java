import java.io.Serializable;

public class Student implements Serializable {
    private String name;
    private int id;
    private double averageMark;

    public Student(int id, String name, double averageMark) {
        this.name = name;
        this.id = id;
        this.averageMark = averageMark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAverageMark() {
        return averageMark;
    }

    public void setAverageMark(double averageMark) {
        this.averageMark = averageMark;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", averageMark=" + averageMark +
                '}';
    }
}
