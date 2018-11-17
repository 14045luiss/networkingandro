package com.example.globalstore.a11retorit;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.globalstore.a11retorit.Model.GetPembeli;
import com.example.globalstore.a11retorit.Rest.ApiClient;
import com.example.globalstore.a11retorit.Rest.ApiInterface;
import com.squareup.picasso.Picasso;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LayarInsertPembeli extends AppCompatActivity {
    Context mContext ;
    ImageView mImageView ;
    Button btAddPhotoId , btAddBack , btAddData ;
    EditText   edtAddNamaPembeli , edtAddAlamatPembeli, edtAddIdPembeli ;
    EditText edtAddTelpPembeli ;
    TextView tvAddMessage ;
    String imagePath = "" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layar_insert_pembeli);

        mContext = getApplicationContext ();
        mImageView = ( ImageView ) findViewById ( R . id . imgAddPhotoId );
        btAddPhotoId = ( Button ) findViewById ( R . id . btAddPhotoId );
        edtAddIdPembeli = ( EditText ) findViewById ( R . id . edtAddIdPembeli );
        edtAddNamaPembeli = ( EditText ) findViewById ( R . id . edtAddNamaPembeli );
        edtAddAlamatPembeli = ( EditText ) findViewById ( R . id . edtAddAlamatPembeli );
        edtAddTelpPembeli = ( EditText ) findViewById ( R . id . edtAddTelpPembeli );
        btAddData = ( Button ) findViewById ( R . id . btAddData );
        btAddBack = ( Button ) findViewById ( R . id . btAddBack );
        tvAddMessage = ( TextView ) findViewById ( R . id . tvAddMessage );
        btAddData . setOnClickListener ( new View. OnClickListener () {
            @Override
            public void onClick ( View view ) {
                ApiInterface mApiInterface =
                        ApiClient. getClient (). create ( ApiInterface. class );
                MultipartBody. Part body = null ;
                if (! imagePath . isEmpty ()){
// Buat file dari image yang dipilih
                    File file = new File ( imagePath );
                    // Buat RequestBody instance dari file
                    RequestBody requestFile =
                            RequestBody . create ( MediaType. parse ( "image/jpg" ), file );
// MultipartBody.Part digunakan untuk mendapatkan nama file
                    body = MultipartBody . Part . createFormData ( "photo_url" , file . getName (),
                            requestFile );
                }
                RequestBody reqNama =
                        MultipartBody . create ( MediaType . parse ( "multipart/form-data" ),
                                ( edtAddNamaPembeli . getText (). toString (). isEmpty ())? "" : edtAddNamaPembeli . getText (). toString ());
                RequestBody reqAlamat =
                        MultipartBody . create ( MediaType . parse ( "multipart/form-data" ),
                                ( edtAddNamaPembeli . getText (). toString (). isEmpty ())? "" : edtAddAlamatPembeli . getText (). toString ());
                RequestBody reqTelp =
                        MultipartBody . create ( MediaType . parse ( "multipart/form-data" ),
                                ( edtAddNamaPembeli . getText (). toString (). isEmpty ())? "" : edtAddTelpPembeli . getText (). toString ());
                RequestBody reqAction =
                        MultipartBody . create ( MediaType . parse ( "multipart/form-data" ),
                                "insert" );
                Call<GetPembeli> mPembeliCall = mApiInterface . postPembeli ( body , reqNama ,
                        reqAlamat , reqTelp , reqAction );
                mPembeliCall . enqueue ( new Callback< GetPembeli >() {
                    @Override
                    public void onResponse ( Call < GetPembeli > call , Response< GetPembeli >
                            response ) {
// Log.d("Insert Retrofit",response.body().getMessage());
                        if ( response . body (). getStatus (). equals ( "failed" )){
                            tvAddMessage . setText ( "Retrofit Insert \n Status = " + response . body ()
                                            . getStatus ()+ " \n " +
                                            "Message = " + response . body (). getMessage ()+ " \n " );
                        } else {
                            String detail = " \n " + "id_pembeli = " + response . body (). getResult (). get ( 0 ). getIdPembeli ()+ " \n " + "nama = " + response . body (). getResult (). get ( 0 ). getNama ()+ " \n " +
                            "alamat = " + response . body (). getResult (). get ( 0 ). getAlamat ()+ " \n " +
                            "telp = " + response . body (). getResult (). get ( 0 ). getTelp ()+ " \n " +
                            "photo_url = " + response . body (). getResult (). get ( 0 ). getPhotoUrl () + " \n " ;
                            tvAddMessage . setText ( "Retrofit Insert \n Status = " + response . body (). getStatus ()+ " \n " + "Message = " + response . body (). getMessage ()+ detail );
                        }
                    }
                    @Override
                    public void onFailure ( Call < GetPembeli > call , Throwable t ) {
// Log.d("Insert Retrofit", t.getMessage());
                        tvAddMessage . setText ( "Retrofit Insert Failure \n Status = " +
                                t . getMessage
                                        ());
                    }
                });
            }
        });
        btAddBack . setOnClickListener ( new View . OnClickListener () {
            @Override
            public void onClick ( View view ) {
                Intent intent = new Intent( mContext , LayarListPembeli . class );
                startActivity ( intent );
            }
        });
        btAddPhotoId . setOnClickListener ( new View . OnClickListener () {
            @Override
            public void onClick ( View view ) {
                final Intent galleryIntent = new Intent ();
                galleryIntent . setType ( "image/*" );
                galleryIntent . setAction ( Intent . ACTION_PICK );
                Intent intentChoose = Intent . createChooser (
                        galleryIntent ,
                        "Pilih foto untuk di-upload" );
                startActivityForResult ( intentChoose , 10 );
            }
        });
    }
    @Override
    protected void onActivityResult ( int requestCode , int resultCode , Intent data ) {
        super . onActivityResult ( requestCode , resultCode , data );
        if ( resultCode == RESULT_OK && requestCode == 10 ){
            if ( data == null ){
                Toast . makeText ( mContext , "Foto gagal di-load" ,
                        Toast. LENGTH_LONG ). show ();
                return ;
            }
            Uri selectedImage = data . getData ();
            String [] filePathColumn = { MediaStore. Images . Media . DATA };
            Cursor cursor = getContentResolver (). query ( selectedImage , filePathColumn ,
                    null , null , null );
            if ( cursor != null ) {
                cursor . moveToFirst ();
                int columnIndex = cursor . getColumnIndex ( filePathColumn [ 0 ]);
                imagePath = cursor . getString ( columnIndex );
                Picasso. with ( mContext ). load ( new File ( imagePath )). fit (). into ( mImageView );
// Glide.with(mContext).load(new File(imagePath)).into(mImageView);
                cursor . close ();
            } else {
                Toast . makeText ( mContext , "Foto gagal di-load" ,
                        Toast . LENGTH_LONG ). show ();
            }
        }
    }
}
