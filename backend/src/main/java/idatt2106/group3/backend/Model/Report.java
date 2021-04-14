package idatt2106.group3.backend.Model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reportId;
    private String description;

    public Report(String description) {
        this.description = description;
    }

    public Report(){}

    public long getReportId() {
        return reportId;
    }

    public void setReportId(long reportId) {
        this.reportId = reportId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
