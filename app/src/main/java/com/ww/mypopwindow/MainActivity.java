package com.ww.mypopwindow;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView mRv;
    ArrayList<String> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        mRv = findViewById(R.id.rv);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        initData();
    }

    public void initData() {
        for (int i = 0; i < 20; i++) {
            String str;
            if (i % 2 == 0) {
                str = getResources().getString(R.string.str);
            } else if (i % 3 == 0) {
                str = getResources().getString(R.string.str2);
            } else {
                str = getResources().getString(R.string.str3);
            }
            mList.add(str);
        }
        mRv.setAdapter(new MyAdapter());
    }

    public class MyAdapter extends RecyclerView.Adapter {

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            return new ItemVh(LayoutInflater.from(MainActivity.this).inflate(R.layout.rv_item_layout, parent, false));

        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
          final   ItemVh vh = (ItemVh) holder;
            vh.contentTv.setText(mList.get(position));
            vh.deleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DeletePopWindow popWindow=new DeletePopWindow(MainActivity.this);
                    popWindow.showPop(vh.deleteImage);
                }
            });
        }

        @Override
        public int getItemCount() {
            return 20;
        }
    }

    public static class ItemVh extends RecyclerView.ViewHolder {
        TextView contentTv;
        ImageView deleteImage;

        private ItemVh(@NonNull View itemView) {
            super(itemView);
            contentTv = itemView.findViewById(R.id.contentTv);
            deleteImage = itemView.findViewById(R.id.deleteImage);
        }
    }

}
