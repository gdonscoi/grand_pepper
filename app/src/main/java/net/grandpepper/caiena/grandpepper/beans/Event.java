package net.grandpepper.caiena.grandpepper.beans;

import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import net.grandpepper.caiena.grandpepper.models.IEntidade;

import java.io.Serializable;

@DatabaseTable(tableName = "event")
public class Event implements Serializable, IEntidade {

    @Expose
    @DatabaseField(generatedId = true)
    public int id;

    @DatabaseField
    public String eventType;

    @DatabaseField
    public String title;

    @DatabaseField
    public String subtitle;

    @DatabaseField
    public String date;

    @DatabaseField
    public String summary;

    @DatabaseField
    public String authorName;

    @DatabaseField
    public String authorAvatarUrl;

    @Expose
    @DatabaseField
    public String authorAvatarPath;

    @DatabaseField
    public String startTime;

    @DatabaseField
    public String endTime;

    @Expose
    @DatabaseField(columnName = "grandPepper_version", foreign = true, foreignAutoRefresh = true, maxForeignAutoRefreshLevel = 1)
    public GrandPepper grandPepper;
}
