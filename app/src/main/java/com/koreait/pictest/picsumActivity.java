package com.koreait.pictest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class picsumActivity extends AppCompatActivity {

    private RecyclerView rvList;
    private MyAdapter adapter;
    private Retrofit rf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new MyAdapter();
        rvList = findViewById(R.id.rvList);
        rvList.setAdapter(adapter);

        rf = new Retrofit.Builder()
                .baseUrl("https://picsum.photos")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        getPicsumList();
    }

private void getPicsumList(){

    Service service = rf.create(Service.class);
    Call<List<picsumVO>> call = service.getList();

    call.enqueue(new Callback<List<picsumVO>>() {
        @Override
        public void onResponse(Call<List<picsumVO>> call, Response<List<picsumVO>> response) {
            if(response.isSuccessful())
            {
                List<picsumVO> list = response.body();
                adapter.setList(list);
                adapter.notifyDataSetChanged();
            }
            else{

            }
        }

        @Override
        public void onFailure(Call<List<picsumVO>> call, Throwable t) {

        }
    });

}


}

class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    private List<picsumVO> list;

    public void setList(List<picsumVO> list){
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_picsum, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
      picsumVO data = list.get(position);
      holder.setItem(data);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class MyViewHolder extends  RecyclerView.ViewHolder{

        private ImageView ivImg;
        private TextView tvAuthor;
        private View v;

        public MyViewHolder(@NonNull View v) {
            super(v);
            ivImg = v.findViewById(R.id.ivImg);
            tvAuthor = v.findViewById(R.id.tvAuthor);
        }

        public void setItem(picsumVO vo){
            Glide.with(v).load(vo.getDownload_url()).into(ivImg);
            tvAuthor.setText(vo.getAuthor());

        }
    }


}