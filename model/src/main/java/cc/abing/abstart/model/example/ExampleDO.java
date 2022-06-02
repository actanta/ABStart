package cc.abing.abstart.model.example;

import java.time.LocalDateTime;

public class ExampleDO {

    private Long id;

    private String varchar;

    private LocalDateTime dateTime;


    private LocalDateTime gmtCreate;

    private LocalDateTime gmtUpdate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVarchar() {
        return varchar;
    }

    public void setVarchar(String varchar) {
        this.varchar = varchar;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public LocalDateTime getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(LocalDateTime gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public LocalDateTime getGmtUpdate() {
        return gmtUpdate;
    }

    public void setGmtUpdate(LocalDateTime gmtUpdate) {
        this.gmtUpdate = gmtUpdate;
    }
}
