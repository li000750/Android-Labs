package com.cst2335.li000750;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

public class TestToolbar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setActionBar(myToolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,myToolbar,
                R.String.open,R.String.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar, menu);
        return true;
        }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.item1:
                Toast.makeText(this, "You clicked on item1", Toast.LENGTH_LONG).show();
            break;

            case R.id.item2:
                Toast.makeText(this, "You clicked on item2", Toast.LENGTH_LONG).show();
            break;

            case R.id.item3:
                Toast.makeText(this, "You clicked on item3", Toast.LENGTH_LONG).show();
            break;

            case R.id.item4:
                Toast.makeText(this, "You clicked on the overflow menu", Toast.LENGTH_LONG).show();
            break;
        }
    return true;
    }




    }
