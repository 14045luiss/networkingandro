package com.example.globalstore.a11retorit.Model;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class GetPembelian {
    @SerializedName ( "status" )
    String status ;
    @SerializedName ( "result" )
    List < Pembelian > listDataPembelian ;
    @SerializedName ( "message" )
    String message ;
    public void setStatus ( String status ) {
        this . status = status ;
    }
    public String getMessage () {
        return message ;
    }
    public void setMessage ( String message ) {
        this . message = message ;
    }
    public List < Pembelian > getListDataPembelian () {
        return listDataPembelian ;
    }
    public void setListDataPembelian ( List < Pembelian > listDataPembelian ) {
        this . listDataPembelian = listDataPembelian ;
    }
}
