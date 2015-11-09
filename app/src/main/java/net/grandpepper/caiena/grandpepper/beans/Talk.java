package net.grandpepper.caiena.grandpepper.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Collection;
import java.util.List;

@DatabaseTable(tableName = "talk")
public class Talk {

    @Expose
    @DatabaseField(generatedId = true)
    public int id;

    @DatabaseField
    public String title;

    @DatabaseField
    public String subtitle;

    @Expose
    @ForeignCollectionField(eager = true, maxEagerForeignCollectionLevel = 1)
    public Collection<Author> authorCollection;

    @Expose
    @DatabaseField(columnName = "grandPepper_version", foreign = true, foreignAutoRefresh = true, maxForeignAutoRefreshLevel = 1)
    public GrandPepper grandPepper;

    @SerializedName("authors")
    public List<Author> authorsJson;
}
