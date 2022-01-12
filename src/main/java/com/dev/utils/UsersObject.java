package com.dev.utils;


// this class is used in case we want to return an object to the client side and let him know if this object belongs to him
// for example: is this sale related to me? is this store related to me? am i belong to this organization ,  etc....
public class UsersObject {

    private Object object;
    private boolean belongsToUser;

    public UsersObject(Object object) {
        this.object = object;
    }

    public UsersObject(Object object, boolean belongsToUser) {
        this.object = object;
        this.belongsToUser = belongsToUser;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public boolean isBelongsToUser() {
        return belongsToUser;
    }

    public void setBelongsToUser(boolean belongsToUser) {
        this.belongsToUser = belongsToUser;
    }

}
