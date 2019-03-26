package com.xavierlnc.testandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "Main activity";

    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;

    private LinkedList<FibonacciNumber> array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FibonacciSequence fs = new FibonacciSequence();
        int target = 5000;
        fs.compute(target);
        //Sample of fibonacci sequence
        array = new LinkedList<>();
        for (int i =0;i<target;i++) {
            array.add(fs.sequence.get(i));
        }


        //Set recyclerView
        recyclerView = (RecyclerView) findViewById(R.id.main_recycler);
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerAdapter = new RecyclerAdapter();
        recyclerView.setAdapter(recyclerAdapter);
    }

    //Adapter for the RecyclerView
    public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

        private LinkedList<FibonacciNumber> list;

        public RecyclerAdapter() {
            list = array;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            Log.d(TAG,"Position : "+String.valueOf(position));
            holder.textView.setText(String.valueOf(list.get(position).toString()));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            private TextView textView;

            public ViewHolder(View view) {
                super(view);
                textView = (TextView) view.findViewById(R.id.item_text);
            }

        }
    }
}
