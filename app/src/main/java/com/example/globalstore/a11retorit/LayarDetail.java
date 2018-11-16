package com.example.globalstore.a11retorit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.globalstore.a11retorit.Model.PostPutDelPembelian;
import com.example.globalstore.a11retorit.Rest.ApiClient;
import com.example.globalstore.a11retorit.Rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LayarDetail extends AppCompatActivity {

    EditText edtIdPembelian , edtIdPembeli , edtTanggalBeli , edtIdTiket , edtTotalHarga;
    Button btInsert , btUpdate , btDelete , btBack ;
    TextView tvMessage ;
    ApiInterface mApiInterface ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layar_detail);
        edtIdPembelian = (EditText) findViewById ( R . id . edtIdPembelian );
        edtIdPembeli = ( EditText ) findViewById ( R . id . edtIdPembeli );
        edtTanggalBeli = ( EditText ) findViewById ( R . id . edtTanggalBeli );
        edtIdTiket = ( EditText ) findViewById ( R . id . edtIdTiket );
        edtTotalHarga = ( EditText ) findViewById ( R . id . edtTotalHarga );
        tvMessage = ( TextView ) findViewById ( R . id . tvMessage2 );
        btInsert = ( Button ) findViewById ( R . id . btInsert2 );
        btUpdate = ( Button ) findViewById ( R . id . btUpdate2 );
        btDelete = ( Button ) findViewById ( R . id . btDelete2 );
        btBack = ( Button ) findViewById ( R . id . btBack );

        Intent mIntent = getIntent ();

        edtIdPembelian . setText ( mIntent . getStringExtra ( "id_pembelian" ));
        edtIdPembeli . setText ( mIntent . getStringExtra ( "id_pembeli" ));
        edtTanggalBeli . setText ( mIntent . getStringExtra ( "tanggal_beli" ));
        edtIdTiket . setText ( mIntent . getStringExtra ( "id_tiket" ));
        edtTotalHarga . setText ( mIntent . getStringExtra ( "total_harga" ));
        mApiInterface = ApiClient. getClient (). create ( ApiInterface . class );
        btUpdate . setOnClickListener ( new View . OnClickListener () {
            @Override
            public void onClick ( View view ) {
                Call< PostPutDelPembelian > updatePembelianCall =
                        mApiInterface . putPembelian (
                                edtIdPembelian . getText (). toString (),
                                edtIdPembeli . getText (). toString (),
                                edtTanggalBeli . getText (). toString (),
                                edtTotalHarga . getText (). toString (),
                                edtIdTiket . getText (). toString ());
                updatePembelianCall . enqueue ( new Callback < PostPutDelPembelian >() {
                    @Override
                    public void onResponse ( Call < PostPutDelPembelian > call ,
                                             Response< PostPutDelPembelian > response ) {
                        tvMessage . setText ( " Retrofit Update: " +
                                " \n " + " Status Update : " + response . body (). getStatus ()
                                +
                                " \n " + " Message Update : " +
                                response . body (). getMessage ());
                    }
                    @Override
                    public void onFailure ( Call < PostPutDelPembelian > call , Throwable t ) {
                        tvMessage . setText ( "Retrofit Update: \n Status Update :" +
                                t . getMessage ());
                    }
                });
            }
        });
        btInsert . setOnClickListener ( new View . OnClickListener () {
            @Override
            public void onClick ( View view ) {
                Call < PostPutDelPembelian > postPembelianCall =
                        mApiInterface . postPembelian (
                                edtIdPembelian . getText (). toString (),
                                edtIdPembeli . getText (). toString (),
                                edtTanggalBeli . getText (). toString (),
                                edtTotalHarga . getText (). toString (),
                                edtIdTiket . getText (). toString ());
                postPembelianCall . enqueue ( new Callback<PostPutDelPembelian>() {
                    @Override
                    public void onResponse ( Call < PostPutDelPembelian > call ,
                                             Response < PostPutDelPembelian > response ) {
                        tvMessage . setText ( " Retrofit Insert: " +
                                " \n " + " Status Insert : " +
                                response . body (). getStatus () +
                                " \n " + " Message Insert : " +
                                response . body (). getMessage ());
                    }
                    @Override
                    public void onFailure ( Call < PostPutDelPembelian > call , Throwable t ) {
                        tvMessage . setText ( "Retrofit Insert: \n Status Insert :" +
                                t . getMessage ());
                    }
                });
            }
        });
        btDelete . setOnClickListener ( new View . OnClickListener () {
            @Override
            public void onClick ( View view ) {
                if (! edtIdPembelian . getText (). toString (). trim (). isEmpty ()){
                    Call < PostPutDelPembelian > deletePembelian =
                            mApiInterface . deletePembelian ( edtIdPembelian . getText (). toString ());
                    deletePembelian . enqueue ( new Callback < PostPutDelPembelian >() {
                        @Override
                        public void onResponse ( Call < PostPutDelPembelian > call ,
                                                 Response < PostPutDelPembelian > response ) {
                            tvMessage . setText ( " Retrofit Delete: " +
                                    " \n " + " Status Delete : "
                                    + response . body (). getStatus () +
                                    " \n " + " Message Delete : " +
                                    response . body (). getMessage ());
                        }
                        @Override
                        public void onFailure (Call <PostPutDelPembelian> call , Throwable
                                t ) {
                            tvMessage . setText ( "Retrofit Delete: \n Status Delete :" +
                                    t . getMessage ());
                        }
                    });
                } else {
                    tvMessage . setText ( "id_pembelian harus diisi" );
                }
            }
        });
        btBack . setOnClickListener ( new View . OnClickListener () {
            @Override
            public void onClick ( View view ) {
                Intent mIntent = new Intent ( getApplicationContext (), MainActivity . class );
                startActivity ( mIntent );
            }
        });
    }
}


