package com.xavierlnc.testandroid;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xavierlnc.testandroid.Fibonacci.FibonacciNumber;
import com.xavierlnc.testandroid.Fibonacci.FibonacciSequence;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main activity";

    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private FibonacciSequence fs;

    private LinkedList<FibonacciNumber> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fs = new FibonacciSequence();

        //Initialisation de la liste avec les 2 premiers éléments incalculables plus 1000 éléments
        list = new LinkedList<>();
        list.add(new FibonacciNumber(0));
        list.add(new FibonacciNumber(1));
        for (int i =0;i<1477;i++) {
            list.add(fs.getNext());
        }

        //Mise en place du RecyclerView et de son adapter
        recyclerView = (RecyclerView) findViewById(R.id.main_recycler);
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerAdapter = new RecyclerAdapter(list,recyclerView);
        recyclerView.setAdapter(recyclerAdapter);

        // Gestion du chargement des données
        recyclerAdapter.setOnLoadMoreListener(new RecyclerAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                // L'ajout d'un objet null permet à l'adapter d'ajouter la progressBar en bas de page
                list.add(null);
                recyclerView.post(new Runnable() {
                    public void run() {
                        recyclerAdapter.notifyItemInserted(list.size() - 1);
                    }
                });

                Log.i(TAG,"Chargement des prochains nombres de la suite");
                new LoadNextData(list, new CallBack() {

                    //Lorsque les données sont chargées
                    @Override
                    public void onCallBack(LinkedList<FibonacciNumber> fn) {
                        // On enlève l'élément servant à la progressBar
                        list.remove(list.size() - 1);
                        recyclerAdapter.notifyItemRemoved(list.size());

                        // Mise à jour de la liste
                        list.addAll(fn);
                        recyclerAdapter.notifyItemInserted(list.size());
                        recyclerAdapter.setLoaded();
                        Log.i(TAG,"Fin du chargement des données");
                    }
                }).execute();
            }
        });
    }

    public interface CallBack {
        void onCallBack(LinkedList<FibonacciNumber> fn);
    }

    // Pour charger les prochaines données sur un thread séparé
    public class LoadNextData extends AsyncTask<Void, Void, Void> {

        private CallBack callBack;
        private LinkedList<FibonacciNumber> linkedList;

        public LoadNextData( LinkedList<FibonacciNumber> list, CallBack callBack) {
            this.callBack = callBack;
            this.linkedList = new LinkedList<>();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for (int i = 0; i < 1000; i++) {
                linkedList.add(fs.getNext());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            callBack.onCallBack(linkedList);
        }

    }

    //Adapter du RecyclerView
    public static class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

        private LinkedList<FibonacciNumber> list;
        private int visibleThreshold = 50;
        private int lastVisibleItem, totalItemCount;
        private boolean loading;
        private OnLoadMoreListener onLoadMoreListener;

        public RecyclerAdapter(LinkedList<FibonacciNumber> sequence, RecyclerView recyclerView) {
            this.list = sequence;

            // Gestion du Scroll
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager)recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                    if(!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)){
                        // Besoin de plus de données
                        loading = true;
                        if(onLoadMoreListener != null){
                            onLoadMoreListener.onLoadMore();
                        }
                    }
                }
            });

        }

        // Le résultat indique s'il faut afficher la progressBar ou un nombre de la suite
        @Override
        public int getItemViewType(int position) {
            return this.list.get(position) != null ? 1 : 0;
        }

        //Choisi le prochain élément à afficher
        @Override
        public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RecyclerViewHolder viewHolder;
            if (viewType == 1) { // Afficher un nombre de la suite
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
                viewHolder = new RecyclerViewHolder(view);
            } else { // Afficher la progressBar
                Log.i(TAG,"ProressHolder");
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_loader, parent, false);
                viewHolder = new ProgressHolder(view);
            }
            return viewHolder;
        }


        @Override
        public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
            // On agit en fonction de ce qu'il faut afficher
            if (holder instanceof ProgressHolder) {
                ((ProgressHolder) holder).progressBar.setIndeterminate(true);
            } else  {
                holder.textView.setText(String.valueOf(list.get(position).toString()));
            }
        }

        @Override
        public int getItemCount() {
            return this.list.size();
        }


        // Pour charger les prochaines données
        public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener){
            this.onLoadMoreListener = onLoadMoreListener;
        }
        public interface OnLoadMoreListener {
            void onLoadMore();
        }
        public void setLoaded() {
            loading = false;
        }


        // ViewHolder pour les nombres
        public class RecyclerViewHolder extends RecyclerView.ViewHolder {

            private TextView textView;

            public RecyclerViewHolder(View view) {
                super(view);
                textView = (TextView) view.findViewById(R.id.item_text);
            }
        }

        // ViewHolder pour les progressBar
        public class ProgressHolder extends RecyclerViewHolder {

            private ProgressBar progressBar;

            public ProgressHolder(View view) {
                super(view);
                progressBar = (ProgressBar) view.findViewById(R.id.main_progressbar);
            }
        }
    }
}
