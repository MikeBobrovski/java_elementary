package HW17;


import java.util.StringJoiner;

public class Student {
    private int id;
    private String lastName;

    public Student(int id, String lastName) {
        this.id = id;
        this.lastName = lastName;
    }


    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Student.class.getSimpleName() + "[", "]")
                .add("'" + lastName + "'")
                .add("id=" + id)
                .toString();
    }//toString


    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || this.getClass() != other.getClass()) return false;
        Student student = (Student) other;
        if (this.id != student.id) return false;
        return lastName != null ?
                this.lastName.equals(student.lastName) :
                student.lastName == null;
    }//equals

    @Override
    public int hashCode() {
        return this.id;
    }
}//Student
