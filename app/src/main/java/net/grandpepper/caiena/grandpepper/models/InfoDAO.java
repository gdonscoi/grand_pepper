package net.grandpepper.caiena.grandpepper.models;

import android.content.Context;

import net.grandpepper.caiena.grandpepper.beans.Info;

public class InfoDAO extends BaseDAO<Info> {

    private static InfoDAO dao;

    public static InfoDAO getInstance(Context ctx) {
        if (dao == null) {
            dao = new InfoDAO(ctx);
        }
        return dao;
    }

    private InfoDAO(Context ctx) {
        super();
        super.ctx = ctx;
    }

}
