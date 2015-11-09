package net.grandpepper.caiena.grandpepper.models;

import android.content.Context;

import net.grandpepper.caiena.grandpepper.beans.CallForPeppers;
import net.grandpepper.caiena.grandpepper.beans.Contact;

public class ContactDAO extends BaseDAO<Contact> {

    private static ContactDAO dao;

    public static ContactDAO getInstance(Context ctx) {
        if (dao == null) {
            dao = new ContactDAO(ctx);
        }
        return dao;
    }

    private ContactDAO(Context ctx) {
        super();
        super.ctx = ctx;
    }

}
