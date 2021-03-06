package eu.alertproject.iccs.stardom.analyzers.its.connector;

import java.util.Date;

/**
 * User: fotis
 * Date: 26/08/11
 * Time: 20:56
 */
public class DefaultItsChangeAction implements ItsAction{

    private Integer bugId;
    private Date date;
    private String what;
    private String added;
    private String removed;


    @Override
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getWhat() {
        return what;
    }

    public Integer getBugId() {
        return bugId;
    }

    public void setBugId(Integer bugId) {
        this.bugId = bugId;
    }

    public void setWhat(String what) {
        this.what = what;
    }

    public String getAdded() {
        return added;
    }

    public void setAdded(String added) {
        this.added = added;
    }

    public String getRemoved() {
        return removed;
    }

    public void setRemoved(String removed) {
        this.removed = removed;
    }
}
