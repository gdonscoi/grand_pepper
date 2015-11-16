package net.grandpepper.caiena.grandpepper.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import net.grandpepper.caiena.grandpepper.models.IEntidade;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@DatabaseTable(tableName = "callForPeppers")
public class CallForPeppers implements Serializable, IEntidade {

    @Expose
    @DatabaseField(generatedId = true)
    public int id;

    @DatabaseField
    public String title;

    @DatabaseField
    public String subtitle;

    @DatabaseField
    public String backgroundImageUrl;

    @Expose
    @DatabaseField
    public String backgroundImagePath;

    @Expose
    @DatabaseField(columnName = "grandPepper_version", foreign = true, foreignAutoRefresh = true, maxForeignAutoRefreshLevel = 1)
    public GrandPepper grandPepper;

    @Expose
    @ForeignCollectionField(eager = true, maxEagerForeignCollectionLevel = 1)
    public Collection<Contact> contactCollection;

    @SerializedName("contacts")
    public List<Contact> contactsJson;
}