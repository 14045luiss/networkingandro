package com.example.globalstore.a11retorit.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetPembeli {
    @SerializedName( "status" )
    private String status ;
    @SerializedName ( "result" )
    private List < Pembeli > result = new ArrayList< Pembeli >();
    @SerializedName ( "message" )
    private String message ;
    public GetPembeli () {}
    public String getStatus () {
        return status ;
    }
    public void setStatus ( String status ) {
        this . status = status ;
    }
    public List<Pembeli> getResult () {
        return result ;
    }
    public void setResult ( List < Pembeli > result ) {
        this . result = result ;
    }
    public String getMessage () {
        return message ;
    }
    public void setMessage ( String message ) {
        this . message = message ;
    }
}