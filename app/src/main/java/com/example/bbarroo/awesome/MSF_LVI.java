package com.example.bbarroo.awesome;

import android.graphics.drawable.Drawable;

public class MSF_LVI {
    private Drawable pic;
    private String what;
    private String when;
    private int _id;

    public void setPic_(Drawable dra){pic = dra;}
    public void setWhat_(String str){what= str;}
    public void setDate_(String str){when = str;}
    public void setid_(int id){_id = id;}

    public Drawable getPic_(){return this.pic;}
    public String getWhat_(){return this.what;}
    public String getDate_(){return this.when;}
    public int getid_(){return this._id;}

}
