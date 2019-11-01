package com.nano.Bush.model.generalMetrics;

public class JobDTO {

    private String name;
    private String startDate;
    private String finishDate;
    private String status;

    public JobDTO(String name, String startDate, String finishDate, String status) {
        this.name = name;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
