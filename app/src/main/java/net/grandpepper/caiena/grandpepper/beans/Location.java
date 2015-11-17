package net.grandpepper.caiena.grandpepper.beans;

import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import net.grandpepper.caiena.grandpepper.models.IEntidade;

import java.io.Serializable;

@DatabaseTable(tableName = "location")
public class Location implements Serializable, IEntidade {

    @Expose
    @DatabaseField(generatedId = true)
    public int id;

    @DatabaseField
    public String name;

    @DatabaseField
    public String address;

    @DatabaseField
    public String action;

    @DatabaseField
    public String latlong;

    @Expose
    @DatabaseField(columnName = "grandPepper_version", foreign = true, foreignAutoRefresh = true, maxForeignAutoRefreshLevel = 1)
    public GrandPepper grandPepper;
}
