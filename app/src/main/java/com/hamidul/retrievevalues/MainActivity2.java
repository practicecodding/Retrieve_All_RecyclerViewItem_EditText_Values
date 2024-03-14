package com.hamidul.retrievevalues;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;

public class MainActivity2 extends AppCompatActivity {

    RecyclerView recyclerView;
    MyAdapter adapter;
    HashMap<String,String> hashMap;
    boolean onEdit = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        recyclerView = findViewById(R.id.recyclerView);

        adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity2.this));


    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.myViewHolder>{


        @NonNull
        @Override
        public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = getLayoutInflater();
            View myView = inflater.inflate(R.layout.item,parent,false);
            return new myViewHolder(myView);
        }

        @Override
        public void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") int position) {

            hashMap = MainActivity.purchase.get(position);
            String id = hashMap.get("id");
            String sku = hashMap.get("sku");
            String unit = hashMap.get("unit");

            holder.skuName.setText(sku);
            holder.tvUnit.setText(unit);
            holder.edUnit.setVisibility(View.GONE);
            holder.edit.setVisibility(View.VISIBLE);

            holder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onEdit){
                        onEdit = false;
                        holder.tvUnit.setVisibility(View.GONE);
                        holder.edUnit.setVisibility(View.VISIBLE);
                        holder.edit.setVisibility(View.GONE);
                        holder.done.setVisibility(View.VISIBLE);
                        holder.edUnit.setText(unit);
                    }
                }
            });

            holder.done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onEdit = true;
                    holder.tvUnit.setVisibility(View.VISIBLE);
                    holder.edUnit.setVisibility(View.GONE);
                    holder.edit.setVisibility(View.VISIBLE);
                    holder.done.setVisibility(View.GONE);

                    for (int x=0; x<MainActivity.purchase.size(); x++){
                        HashMap has = MainActivity.purchase.get(x);
                        String mID = (String) has.get("id");
                        if (mID.equals(id)){
                            if (!holder.edUnit.getText().toString().isEmpty()){
                                has.replace("unit",holder.edUnit.getText().toString());
                                adapter = new MyAdapter();
                                recyclerView.setAdapter(adapter);
                                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity2.this));
                            }
                            else {
                                MainActivity.purchase.remove(x);
                                adapter = new MyAdapter();
                                recyclerView.setAdapter(adapter);
                                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity2.this));
                            }

                            break;
                        }
                    }


                }
            });

        }


        @Override
        public int getItemCount() {
            return MainActivity.purchase.size();
        }

        public class myViewHolder extends RecyclerView.ViewHolder{
            TextView skuName,tvUnit;
            EditText edUnit;
            ImageView done,edit;
            public myViewHolder(@NonNull View itemView) {
                super(itemView);
                skuName = itemView.findViewById(R.id.skuName);
                edUnit = itemView.findViewById(R.id.edUnit);
                tvUnit = itemView.findViewById(R.id.tvUnit);
                done = itemView.findViewById(R.id.done);
                edit = itemView.findViewById(R.id.edit);

            }
        }

    }

}
