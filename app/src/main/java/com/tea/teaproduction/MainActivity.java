package com.tea.teaproduction;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.tea.teaproduction.Adapter.DrawerAdapter;
import com.tea.teaproduction.Model.DrawerModel;
import com.tea.teaproduction.databinding.ActivityMainBinding;
import com.tea.teaproduction.utils.ConnectionReceiver;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    ActivityMainBinding binding;
    private AppBarConfiguration appBarConfiguration;
    private NavController navController;
    private NavOptions.Builder navBuilder;

    private ActionBarDrawerToggle mDrawerToggle;

    List<DrawerModel> modelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.baseColor));
        }

        //checkInternet();
        setDefaultView();
        setDrawerMenu();
    }

    /*public boolean checkInternet() {
        IntentFilter intentFilter = new IntentFilter();

        // add action
        intentFilter.addAction("android.new.conn.CONNECTIVITY_CHANGE");

        // register receiver
        registerReceiver(new ConnectionReceiver(), intentFilter);

        // Initialize listener
        ConnectionReceiver.Listener = this;

        // Initialize connectivity manager
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        // Initialize network info
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();
        return isConnected;
    }*/

    private void setDrawerMenu() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        binding.rvMenu.setLayoutManager(layoutManager);
        modelList = new ArrayList<>();

        modelList.add(new DrawerModel(R.drawable.ic_baseline_home_24, "Home"));
        modelList.add(new DrawerModel(R.drawable.ic_baseline_home_24, "Stock In"));
        modelList.add(new DrawerModel(R.drawable.ic_baseline_home_24, "Stock Out"));

        DrawerAdapter drawerMenuAdapter = new DrawerAdapter(modelList, this);
        binding.rvMenu.setAdapter(drawerMenuAdapter);

        drawerMenuAdapter.setListenerDrawerMenu(new OnDrawerMenuListener() {
            @Override
            public void onDrawerMenuClick(int pos) {
                Bundle bundle = new Bundle();
                switch (pos) {
                    case 0:
                        Log.e("Pos", "= 0");
                        navController.navigate(R.id.navigation_home, bundle, navBuilder.build());//This will open
                        openDrawer();
                        break;
                    case 1:
                        Log.e("Pos", "= 0");
                        navController.navigate(R.id.nav_stock_in, bundle, navBuilder.build());//This will open
                        openDrawer();
                        break;
                    case 2:
                        Log.e("Pos", "= 0");
                        navController.navigate(R.id.nav_stock_out, bundle, navBuilder.build());//This will open
                        openDrawer();
                        break;
                }
            }
        });


    }

    public void openDrawer() {
        if (!binding.drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            binding.drawerLayout.openDrawer(Gravity.LEFT);
        } else {
            binding.drawerLayout.closeDrawer(Gravity.LEFT);
        }
    }

    private void setDefaultView() {
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(binding.appBarMain.bottomNav, navController);
        NavigationUI.setupWithNavController(binding.navView, navController);
        navBuilder = new NavOptions.Builder();
        navBuilder.setEnterAnim(R.anim.fade_in_animation)
                .setExitAnim(R.anim.fade_out_animation)
                .setPopEnterAnim(R.anim.fade_in_animation)
                .setPopExitAnim(R.anim.fade_out_animation);

        /*mDrawerToggle = new ActionBarDrawerToggle(this, binding.drawerLayout,
                R.drawable.ic_dashboard_black_24dp, R.string.title_home) {*/
        mDrawerToggle = new ActionBarDrawerToggle(this, binding.drawerLayout,
                R.drawable.ic_baseline_home_24, R.string.title_home) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                // Do whatever you want here
                Log.e("onDrawerClosed= ", "Closed");
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // Do whatever you want here
                Log.e("onDrawerOpened= ", "Closed");
            }
        };
        binding.drawerLayout.addDrawerListener(mDrawerToggle);
    }


    public interface OnDrawerMenuListener {
        void onDrawerMenuClick(int pos);
    }
}