package com.pis.buy2gether.model.domain.pojo;

import com.pis.buy2gether.usecases.home.notifications.NotiType;

public class Notificacions {

    private NotiType notiType;
    private String idNotificacion; // notification ID
    private String fromUsername; // userName of who send notification
    private String groupName; // groupname
    private String fromID; // ID of who send notification


    public Notificacions(){

    }

    public Notificacions(NotiType notiType, String idNotificacion , String fromUsername, String groupName,String fromID) {
        this.notiType = notiType;
        this.idNotificacion = idNotificacion;
        this.fromUsername = fromUsername;
        this.groupName = groupName;
        this.fromID = fromID;
    }

    public String getIdNotificacion() {
        return idNotificacion;
    }

    public void setIdNotificacion(String idNotificacion) {
        this.idNotificacion = idNotificacion;
    }


    public void setFromID(String fromID) {
        this.fromID = fromID;
    }

    public String getFromUsername() {
        return fromUsername;
    }

    public void setFromUsername(String fromUsername) {
        this.fromUsername = fromUsername;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getFromID() {
        return fromID;
    }

    public NotiType getNotiType() {
        return notiType;
    }

    public void setNotiType(NotiType notiType) {
        this.notiType = notiType;
    }
}
