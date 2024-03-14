package com.hamidul.retrievevalues;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView,recyclerView2;
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
    public static ArrayList<HashMap<String,String>> purchase = new ArrayList<>();
    HashMap<String,String> hashMap;
    Button button;
    MyAdapter adapter;
    MyAdapter2 adapter2;
    Toast toast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        button = findViewById(R.id.button);
        recyclerView2 = findViewById(R.id.recyclerView2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (purchase.size()>0){
                    startActivity(new Intent(MainActivity.this, MainActivity2.class));
                }
                else {
                    setToast("Please Insert Product Quantity");
                }
            }
        });

        arrayList = new ArrayList<>();
        purchase = new ArrayList<>();

        hashMap = new HashMap<>();
        hashMap.put("id","10");
        hashMap.put("sku","Corn Flakes 120gm");
        hashMap.put("unit","36");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("id","2");
        hashMap.put("sku","Corn Flakes 250gm pp");
        hashMap.put("unit","15");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("id","3");
        hashMap.put("sku","Corn Flakes 250gm");
        hashMap.put("unit","16");
        arrayList.add(hashMap);

        hashMap = new HashMap<>();
        hashMap.put("id","4");
        hashMap.put("sku","Corn Flakes 475gm");
        hashMap.put("unit","12");
        arrayList.add(hashMap);

        adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        adapter2 = new MyAdapter2();
        recyclerView2.setAdapter(adapter2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(MainActivity.this));

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

            hashMap = arrayList.get(position);
            String id = hashMap.get("id");
            String sku = hashMap.get("sku");
            String unit = hashMap.get("unit");

            holder.skuName.setText(sku);
            holder.tvUnit.setVisibility(View.GONE);

            holder.edUnit.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    String string = holder.edUnit.getText().toString();

                    if (!string.isEmpty()){

                        if (purchase.size()>0){

                            String mID = "";

                            for (int x=0; x<purchase.size(); x++){
                                HashMap has = purchase.get(x);
                                mID = (String) has.get("id");
                                if (mID.equals(id)){
                                    has.replace("unit",string);
                                    adapter2 = new MyAdapter2();
                                    recyclerView2.setAdapter(adapter2);
                                    recyclerView2.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                                    break;
                                }// (mID.equals(id))

                            }// end for loop

                            if (!mID.equals(id)){
                                hashMap = new HashMap<>();
                                hashMap.put("id",id);
                                hashMap.put("sku",sku);
                                hashMap.put("unit",string);
                                purchase.add(hashMap);
                                adapter2 = new MyAdapter2();
                                recyclerView2.setAdapter(adapter2);
                                recyclerView2.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                            } // (!mID.equals(id))


                        }// (purchase.size()>0)
                        else {
                            hashMap = new HashMap<>();
                            hashMap.put("id",id);
                            hashMap.put("sku",sku);
                            hashMap.put("unit",string);
                            purchase.add(hashMap);
                            adapter2 = new MyAdapter2();
                            recyclerView2.setAdapter(adapter2);
                            recyclerView2.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        }

                    }// (!string.isEmpty())
                    else {

                        for (int x=0; x<purchase.size(); x++){
                            HashMap hashMap1 = purchase.get(x);
                            String id1 = (String) hashMap1.get("id");
                            if (id1.equals(id)){
                                purchase.remove(x);
                                adapter2 = new MyAdapter2();
                                recyclerView2.setAdapter(adapter2);
                                recyclerView2.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                            }// (id1.equals(id))

                        }// end for loop
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

                    String string = s.toString();

                    if (!string.isEmpty() && string.startsWith("0")){

                        s.delete(0,1);

                    }

                }
            });

        }


        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public class myViewHolder extends RecyclerView.ViewHolder{
            TextView skuName,tvUnit;
            EditText edUnit;
            public myViewHolder(@NonNull View itemView) {
                super(itemView);
                skuName = itemView.findViewById(R.id.skuName);
                edUnit = itemView.findViewById(R.id.edUnit);
                tvUnit = itemView.findViewById(R.id.tvUnit);
            }
        }

    }



    private void setToast(String s){
        if (toast!=null) toast.cancel();
        toast = Toast.makeText(MainActivity.this,s,Toast.LENGTH_LONG);
        toast.show();
    }


    public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.myViewHolder>{


        @NonNull
        @Override
        public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = getLayoutInflater();
            View myView = inflater.inflate(R.layout.item,parent,false);
            return new myViewHolder(myView);
        }

        @Override
        public void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") int position) {

            hashMap = purchase.get(position);
            String id = hashMap.get("id");
            String sku = hashMap.get("sku");
            String unit = hashMap.get("unit");

            holder.skuName.setText(sku);
            holder.tvUnit.setText(unit);
            holder.edUnit.setVisibility(View.GONE);

        }


        @Override
        public int getItemCount() {
            return purchase.size();
        }

        public class myViewHolder extends RecyclerView.ViewHolder{
            TextView skuName,tvUnit;
            EditText edUnit;
            public myViewHolder(@NonNull View itemView) {
                super(itemView);
                skuName = itemView.findViewById(R.id.skuName);
                edUnit = itemView.findViewById(R.id.edUnit);
                tvUnit = itemView.findViewById(R.id.tvUnit);

            }
        }

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        purchase.clear();
    }
}