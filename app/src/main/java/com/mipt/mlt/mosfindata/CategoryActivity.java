package com.mipt.mlt.mosfindata;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.mipt.mlt.mosfindata.model.Category;
import com.mipt.mlt.mosfindata.ui.CategoryRecyclerAdapter;
import com.mipt.mlt.mosfindata.utils.JsonConstant;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity implements CategoryRecyclerAdapter.ItemClickListener {

    public static String[] titles = {
            "Ремонт обуви", "Часовые мастерсике", "Парикмахерские",
            "Химическая чистка", "Фотоателье", "Название категории", "Название категории", "Название категории",
            "Название категории", "Название категории", "Название категории"};
    private String[] descs = {"Услуги ремонта, окраски и пошива обуви", "Услуги ремонта часов",
            "Салоны красоты и косметические услуги", "Услуги химической чистки и прачечной",
            "Фотосалоны, услуги печати фотографий", "Описание категории", "Описание категории", "Описание категории"
            , "Описание категории", "Описание категории", "Описание категории"};

    private int[] images = {R.drawable.shoes_bl, R.drawable.watches, R.drawable.haircut,
            R.drawable.wash, R.drawable.camera};

    private int[] bgColors = {Color.parseColor("#a29bfe"), Color.parseColor("#74b9ff"),
            Color.parseColor("#ff7675"), Color.parseColor("#fd79a8"),
            Color.parseColor("#55efc4")};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_activity);

        JsonConstant.fillFromAssets(this);

        Toolbar toolbar = findViewById(R.id.category_toolbar);
        toolbar.setTitle("Выбор рубрики");

        RecyclerView rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new CategoryRecyclerAdapter(createCategories(), this));
    }

    private List<Category> createCategories() {
        List<Category> categories = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            if (images.length <= i) {
                categories.add(new Category(titles[i], descs[i]));
            } else {
                categories.add(new Category(titles[i], descs[i], images[i], bgColors[i]));
            }
        }
        return categories;
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("categoryId", position);
        startActivity(intent);
    }
}
