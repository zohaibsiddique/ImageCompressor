package com.example.dell.imagecompressor;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Images extends AppCompatActivity {
    private Util utils;
    private ArrayList<String> imagePaths = new ArrayList<String>();
    private ImagesGridViewImageAdapter adapter;
    private GridView gridView;
    private int columnWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        InitilizeGridLayout();
        String dir;
        Intent i = getIntent();
        String path = i.getStringExtra("path");
        imagePaths = utils.getAllFilesDirectoryWithNotNullImageDirectories(path);
        adapter = new ImagesGridViewImageAdapter(Images.this, imagePaths, columnWidth);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener((new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Images.this, FullScreenImage.class);
                String path = adapterView.getItemAtPosition(i).toString();
                Util.shortToast(Images.this, path);
                intent.putExtra("path", path);
                startActivity(intent);
            }
        }));

        gridView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        gridView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                final int checkedCount = gridView.getCheckedItemCount();
                mode.setTitle(checkedCount + " Selected");
                adapter.toggleSelection(position);
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                ArrayList<String> list = new ArrayList<>();
                switch (item.getItemId()) {
                    case R.id.compress:
                        SparseBooleanArray selected = gridView.getCheckedItemPositions();
                        for(int i= 0; i < selected.size(); i++) {
                            String imagePath = imagePaths.get(selected.keyAt(i));
                            list.add(imagePath);
//                            compressPicture(imagePath);
                        }
                        try{
                            new ProgressImages(Images.this).execute(list);
                        } catch (Exception e) {
                            Log.d("dddd", e.getMessage());
                        }
                        mode.finish();
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.contextual_actionbar, menu);
                getSupportActionBar().hide();
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                adapter.removeSelection();
                getSupportActionBar().show();
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }
        });

    }



    private void InitilizeGridLayout() {
        utils = new Util(this);
        gridView = (GridView) findViewById(R.id.grid_view);
        Resources r = getResources();
        float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, AppConstant.GRID_PADDING, r.getDisplayMetrics());
        columnWidth = (int) ((utils.getScreenWidth() - ((AppConstant.NUM_OF_COLUMNS + 1) * padding)) / AppConstant.NUM_OF_COLUMNS);
        gridView.setNumColumns(AppConstant.NUM_OF_COLUMNS);
        gridView.setColumnWidth(columnWidth);
        gridView.setStretchMode(GridView.NO_STRETCH);
        gridView.setPadding((int) padding, (int) padding, (int) padding, (int) padding);
        gridView.setHorizontalSpacing((int) padding);
        gridView.setVerticalSpacing((int) padding);
    }

}
