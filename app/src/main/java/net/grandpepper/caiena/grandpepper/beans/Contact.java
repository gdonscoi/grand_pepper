package net.grandpepper.caiena.grandpepper.beans;

import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "contact")
public class Contact {

    @Expose
    @DatabaseField(generatedId = true)
    public int id;

    @DatabaseField
    public String name;

    @DatabaseField
    public String email;

    @Expose
    @DatabaseField(columnName = "callForpeppers_id", foreign = true, foreignAutoRefresh = true, maxForeignAutoRefreshLevel = 1)
    public CallForPeppers callForPeppers;
}
