package officialInternalAssessment;

public class Teacher extends User {

    public Teacher(String username, String password) {
        super(username, password, "teacher");
    }

    @Override
    public boolean isStudent() {
        return false;
    }
}
