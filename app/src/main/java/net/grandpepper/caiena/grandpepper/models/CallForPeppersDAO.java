package net.grandpepper.caiena.grandpepper.models;

import android.content.Context;

import net.grandpepper.caiena.grandpepper.beans.CallForPeppers;

public class CallForPeppersDAO extends BaseDAO<CallForPeppers> {

    private static CallForPeppersDAO dao;

    public static CallForPeppersDAO getInstance(Context ctx) {
        if (dao == null) {
            dao = new CallForPeppersDAO(ctx);
        }
        return dao;
    }

    private CallForPeppersDAO(Context ctx) {
        super();
        super.ctx = ctx;
    }

}
