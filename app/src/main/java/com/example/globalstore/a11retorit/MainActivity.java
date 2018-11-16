package com.example.globalstore.a11retorit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.globalstore.a11retorit.Adapter.MyAdapter;
import com.example.globalstore.a11retorit.Model.GetPembelian;
import com.example.globalstore.a11retorit.Model.Pembelian;
import com.example.globalstore.a11retorit.Rest.ApiClient;
import com.example.globalstore.a11retorit.Rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Button btGet;
    ApiInterface mApiInterface;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private MenuItem item;

    public boolean onCreateOptionsMenu( Menu menu) {
        getMenuInflater (). inflate ( R . menu . menu_layout , menu );
        return super . onCreateOptionsMenu (menu );
    }

    public boolean onOptionsItemSelected ( MenuItem item ) {
        Intent mIntent ;
        switch ( item . getItemId ()) {
            case R . id . menuTambahTransPembelian :
                mIntent = new Intent ( this , LayarDetail . class );
                startActivity ( mIntent );
                return true ;
            case R . id . menuListPembeli :
                mIntent = new Intent ( this , LayarListPembeli . class );
                startActivity ( mIntent );
                return true ;
            default :
                return super . onOptionsItemSelected ( item );
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btGet = (Button) findViewById(R.id.btGet);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);

        btGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<GetPembelian> pembelianCall = mApiInterface.getPembelian();
                pembelianCall.enqueue(new Callback<GetPembelian>() {
                    @Override
                    public void onResponse(Call<GetPembelian> call, Response<GetPembelian>
                            response) {
                        List<Pembelian> pembelianList = response.body().getListDataPembelian();
                        Log.d("Retrofit Get", "Jumlah data pembelian: " + String.valueOf(pembelianList.size()));

                        mAdapter = new MyAdapter(pembelianList);
                        mRecyclerView.setAdapter(mAdapter);
                    }

                    @Override
                    public void onFailure(Call<GetPembelian> call, Throwable t) {
                        Log.e("Retrofit Get", t.toString());
                    }
                });
            }

        });

}}
