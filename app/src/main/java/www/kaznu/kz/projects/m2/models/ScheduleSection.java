package www.kaznu.kz.projects.m2.models;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class ScheduleSection {

    private String sectionTitle;
    private ArrayList<BookingApplication> sectionData;

    public ScheduleSection(String sectionTitle, ArrayList<BookingApplication> sectionData) {
        this.sectionTitle = sectionTitle;
        this.sectionData = sectionData;
    }

    public String getSectionTitle() {
        return sectionTitle;
    }

    public void setSectionTitle(String sectionTitle) {
        this.sectionTitle = sectionTitle;
    }

    public ArrayList<BookingApplication> getSectionData() {
        return sectionData;
    }

    public void setSectionData(ArrayList<BookingApplication> sectionData) {
        this.sectionData = sectionData;
    }

    @NonNull
    @Override
    public String toString() {
        return "ScheduleSection{" +
                "sectionTitle='" + sectionTitle + '\'' +
                ", sectionData=" + sectionData +
                '}';
    }
}
