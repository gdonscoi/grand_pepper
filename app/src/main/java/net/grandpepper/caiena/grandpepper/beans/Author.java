package net.grandpepper.caiena.grandpepper.beans;

import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "contact")
public class Author {

    @Expose
    @DatabaseField(generatedId = true)
    public int id;

    @DatabaseField
    public String name;

    @DatabaseField
    public String occupation;

    @DatabaseField
    public String authorImageUrl;

    @Expose
    @DatabaseField
    public String authorImagePath;

    @Expose
    @DatabaseField(columnName = "talk_id", foreign = true, foreignAutoRefresh = true, maxForeignAutoRefreshLevel = 1)
    public Talk talk;
}
