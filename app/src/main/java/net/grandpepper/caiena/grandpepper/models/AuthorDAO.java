package net.grandpepper.caiena.grandpepper.models;

import android.content.Context;

import net.grandpepper.caiena.grandpepper.beans.Author;

public class AuthorDAO extends BaseDAO<Author> {

    private static AuthorDAO dao;

    public static AuthorDAO getInstance(Context ctx) {
        if (dao == null) {
            dao = new AuthorDAO(ctx);
        }
        return dao;
    }

    private AuthorDAO(Context ctx) {
        super();
        super.ctx = ctx;
    }

}
