package net.grandpepper.caiena.grandpepper.models;

import android.content.Context;

import net.grandpepper.caiena.grandpepper.beans.GrandPepper;

public class GrandPepperDAO extends BaseDAO<GrandPepper> {

    private static GrandPepperDAO dao;

    public static GrandPepperDAO getInstance(Context ctx) {
        if (dao == null) {
            dao = new GrandPepperDAO(ctx);
        }
        return dao;
    }

    private GrandPepperDAO(Context ctx) {
        super();
        super.ctx = ctx;
    }

}
