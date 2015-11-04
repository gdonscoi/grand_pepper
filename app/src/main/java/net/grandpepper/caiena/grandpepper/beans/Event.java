package net.grandpepper.caiena.grandpepper.beans;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import net.grandpepper.caiena.grandpepper.models.IEntidade;

import java.io.Serializable;

@DatabaseTable(tableName = "event")
public class Event implements Serializable, IEntidade {

    @DatabaseField(generatedId = true)
    public int version;

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
    public String startTime;

    @DatabaseField
    public String endTime;

    @DatabaseField(columnName = "info_version", foreign = true, foreignAutoRefresh = true, maxForeignAutoRefreshLevel = 1)
    public Info info;
}
