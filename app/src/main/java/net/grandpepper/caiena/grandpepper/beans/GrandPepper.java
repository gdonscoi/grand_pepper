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

@DatabaseTable(tableName = "grandPepper")
public class GrandPepper implements Serializable, IEntidade {

    @DatabaseField(id = true)
    public int version;

    @DatabaseField
    public String title;

    @DatabaseField
    public String subtitle;

    @DatabaseField
    public String date;

    @DatabaseField
    public String summary;

    @DatabaseField
    public String locationName;

    @DatabaseField
    public String backgroundImageUrl;

    @Expose
    @DatabaseField
    public String backgroundImagePath;

    @DatabaseField
    public String type;

    @DatabaseField(columnName = "callForPeppers_id", foreign = true, foreignAutoRefresh = true, maxForeignAutoRefreshLevel = 2)
    public CallForPeppers callForPeppers;

    @Expose
    @ForeignCollectionField(eager = true, maxEagerForeignCollectionLevel = 1)
    public Collection<Event> eventCollection;

    @Expose
    @ForeignCollectionField(eager = true, maxEagerForeignCollectionLevel = 1)
    public Collection<Location> locationCollection;

    @SerializedName("events")
    public List<Event> eventsJson;

    @SerializedName("locations")
    public List<Location> locationsJson;

}