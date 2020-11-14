package www.kaznu.kz.projects.m2.models;

import java.util.Comparator;

public class Directory {
    private int codeId;
    private String codeStr;
    private String value;

    public int getCodeId() {
        return codeId;
    }

    public void setCodeId(int codeId) {
        this.codeId = codeId;
    }

    public String getCodeStr() {
        return codeStr;
    }

    public void setCodeStr(String codeStr) {
        this.codeStr = codeStr;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static Comparator<Directory> StringComparator = new Comparator<Directory>() {

        public int compare(Directory s1, Directory s2) {
            String getValue1 = s1.getValue().toUpperCase();
            String getValue2 = s2.getValue().toUpperCase();

            //ascending order
            return getValue1.compareTo(getValue2);

            //descending order
            //return StudentName2.compareTo(StudentName1);
        }};
}
