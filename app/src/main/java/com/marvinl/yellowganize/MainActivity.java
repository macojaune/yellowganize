package com.marvinl.yellowganize;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import static android.content.ContentValues.TAG;

public class MainActivity extends Activity {
    List<Post> postList;
    ListView listView;
    final AppBdd db = Room.databaseBuilder(MainActivity.this, AppBdd.class, "Yellowganize").build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new GetListTask().execute();

        listView = (ListView) findViewById(R.id.list_posts);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddPostActivity.class));
            }
        });
    }

    private class GetListTask extends AsyncTask<Void, Void, List> {

        @Override
        protected List<Post> doInBackground(Void... voids) {
            return db.postDao().getAll();
        }


        @Override
        protected void onPostExecute(List list) {
            Log.d(TAG, "onPostExecute: " + list);
            postList = list;
            listView.setAdapter(new MyAdapter());

        }
    }

    private class MyAdapter extends BaseAdapter{
        @Override
        public Post getItem(int i) {
            return postList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return postList.get(i).getId();
        }

        @Override
        public int getCount() {
            return postList.size();
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if(view == null){
                view = getLayoutInflater().inflate(R.layout.list_item, viewGroup,false);
            }
            ((TextView) view.findViewById(R.id.listdate)).setText(getItem(i).getDay()+"/"+getItem(i).getMonth()+"/"+getItem(i).getYear());
            ((TextView) view.findViewById(R.id.listtime)).setText(getItem(i).getHour()+":"+getItem(i).getMinute());
            ((TextView) view.findViewById(R.id.listcaption)).setText(getItem(i).getCaption());
            ((ImageView) view.findViewById(R.id.listpicture)).setImageURI(Uri.parse(getItem(i).getPicture()));
            return view;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
