package pl.elpassion.cloudtimer.dao;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import pl.elpassion.cloudtimer.domain.Timer;

@RealmClass
public class TimerRealmObject extends RealmObject{

    @PrimaryKey private String uuid;
    private String title;
    private Long duration;
    private Long endTime;
    private String groupName;
    private Integer groupColor;
    private Boolean sync;

    public TimerRealmObject() {
    }

    public TimerRealmObject(Timer timer) {
        this.uuid = timer.getUid();
        this.title = timer.getTitle();
        this.duration = timer.getDuration();
        this.endTime = timer.getEndTime();
        if (timer.getGroup() != null) {
            this.groupName = timer.getGroup().getName();
            this.groupColor = timer.getGroup().getColor();
        }
        this.sync = timer.getSync();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getGroupColor() {
        return groupColor;
    }

    public void setGroupColor(Integer groupColor) {
        this.groupColor = groupColor;
    }

    public Boolean getSync() {
        return sync;
    }

    public void setSync(Boolean sync) {
        this.sync = sync;
    }
}