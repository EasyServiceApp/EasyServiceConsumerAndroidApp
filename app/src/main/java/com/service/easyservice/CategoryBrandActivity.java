package com.service.easyservice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.service.easyservice.models.BrandCategory;
import com.service.easyservice.models.Myappliance;
import com.service.easyservice.util.CommonFunctions;
import com.service.easyservice.util.Constants;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CategoryBrandActivity extends AppCompatActivity implements View.OnClickListener,Constants {

    int CATEGORY = 1, BRAND = 2, MODEL = 3;
    int Stage = CATEGORY;
    TextView tvLabel,tvFooter;
    ListView lvCategory,lvBrand,lvModel;
    BrandCategory brandCategory = null;
    ArrayList<String> categoryList = new ArrayList<>();
    ArrayList<String> brandList = new ArrayList<>();
    ArrayList<String> modelList = new ArrayList<>();
    private ArrayAdapter<String> categoryAdapter;
    private ArrayAdapter<String> brandAdapter;
    private ArrayAdapter<String> modelAdapter;
    int selectedCategory = 0;
    private Myappliance myappliance = new Myappliance();
    ImageView ivProfile,ivDrawerHandel,ivToolbarHome;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_brand);
        init();
    }

    public void init()
    {
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        ivProfile = (ImageView)toolbar.findViewById(R.id.ivProfile);
        ivProfile.setVisibility(View.GONE);
        ivDrawerHandel = (ImageView)toolbar.findViewById(R.id.ivDrawerHandel);
        ivDrawerHandel.setImageResource(R.drawable.toolbar_back);
        ivDrawerHandel.setOnClickListener(this);
        ivToolbarHome = (ImageView)toolbar.findViewById(R.id.ivToolbarHome);
        ivToolbarHome.setOnClickListener(this);

        tvLabel = (TextView) findViewById(R.id.tvLabel);
        tvFooter = (TextView) findViewById(R.id.tvFooter);
        lvCategory = (ListView) findViewById(R.id.lvCategory);
        lvBrand = (ListView) findViewById(R.id.lvBrand);
        lvModel = (ListView) findViewById(R.id.lvModel);
        tvFooter.setOnClickListener(this);

        //populate brand category
        Type type = new TypeToken<BrandCategory>(){}.getType();
        Gson gson = new Gson();
        brandCategory = gson.fromJson(BRAND_CATEGORY_JSON,type);
        tvFooter.setText("");

        for(int i=0; i<brandCategory.getCategory().size();i++)
        {
            categoryList.add(brandCategory.getCategory().get(i).getCategoryName().toUpperCase());
        }

        //set list adapter for category
        categoryAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, categoryList);
        lvCategory.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();

        lvCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //hide the category list and show the brand list
                tvFooter.setText("<--Back");
                tvLabel.setText("Select Brand");
                lvCategory.setVisibility(View.GONE);
                lvBrand.setVisibility(View.VISIBLE);
                Stage = BRAND;
                selectedCategory = i;
                myappliance.setCategory(categoryList.get(i));
                for (int j=0;j<brandCategory.getCategory().get(i).getBrand().size();j++)
                {

                    brandList.add(brandCategory.getCategory().get(i).getBrand().get(j).getBrandName());
                    //set list adapter for category
                    brandAdapter = new ArrayAdapter<String>(CategoryBrandActivity.this,
                            android.R.layout.simple_list_item_1, brandList);
                    lvBrand.setAdapter(brandAdapter);
                    brandAdapter.notifyDataSetChanged();
                }

            }
        });

        lvBrand.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //hide the category list and show the brand list
                tvFooter.setText("<--Back");
                tvLabel.setText("Select Model");
                lvBrand.setVisibility(View.GONE);
                lvModel.setVisibility(View.VISIBLE);
                Stage = MODEL;
                myappliance.setBrand(brandList.get(i));
                for (int j=0;j<brandCategory.getCategory().get(selectedCategory).getBrand().get(i).getModel().size();j++)
                {
                    modelList.add(brandCategory.getCategory().get(selectedCategory).getBrand().get(i).getModel().get(j).toUpperCase());
                    //set list adapter for category
                    modelAdapter = new ArrayAdapter<String>(CategoryBrandActivity.this,
                            android.R.layout.simple_list_item_1, modelList);
                    lvModel.setAdapter(modelAdapter);
                    modelAdapter.notifyDataSetChanged();
                }

            }
        });
        lvModel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                myappliance.setModel(modelList.get(i));
                Intent intent = new Intent(CategoryBrandActivity.this,AddDeviceActivity.class);
                Gson gson = new Gson();
                Type type = new TypeToken<Myappliance>() {}.getType();
                intent.putExtra("myappliance",gson.toJson(myappliance,type));
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.ivToolbarHome:
                CommonFunctions.navigateToHome(CategoryBrandActivity.this);
                break;

            case R.id.ivDrawerHandel:
                finish();
                break;

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
