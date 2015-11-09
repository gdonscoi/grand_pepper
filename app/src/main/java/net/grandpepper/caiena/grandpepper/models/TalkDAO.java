package net.grandpepper.caiena.grandpepper.models;

import android.content.Context;

import net.grandpepper.caiena.grandpepper.beans.Talk;

public class TalkDAO extends BaseDAO<Talk> {

    private static TalkDAO dao;

    public static TalkDAO getInstance(Context ctx) {
        if (dao == null) {
            dao = new TalkDAO(ctx);
        }
        return dao;
    }

    private TalkDAO(Context ctx) {
        super();
        super.ctx = ctx;
    }

}
