package com.fjsdfx.yyd.everyday;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.fjsdfx.yyd.everyday.fragment.AnecdoteFragment;
import com.fjsdfx.yyd.everyday.fragment.MeiziFragment;
import com.fjsdfx.yyd.everyday.fragment.NBAFragment;
import com.fjsdfx.yyd.everyday.fragment.TopNewsFragment;
import com.fjsdfx.yyd.everyday.fragment.ZhihuFragment;
import com.fjsdfx.yyd.everyday.util.SharePreferenceUtil;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Fragment currentFragment;
    private FloatingActionButton fab;
    private Toolbar mToolbar;
    private DrawerLayout drawer;
    long exitTime = 0;
    int nevigationId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initalView();
    }

    private void initalView(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        setSupportActionBar(mToolbar); //设置ActionBar
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "感谢你的支持", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //获取上次关闭时的页面ID，并切换到指定页面
        nevigationId = SharePreferenceUtil.getNevigationItem(this);
        switch (nevigationId)
        {
            case R.id.nav_zhihu:
                switchFragment(new ZhihuFragment());
                break;
            case R.id.nav_wangyi:
                switchFragment(new TopNewsFragment());
                break;
            case R.id.nav_look:
                switchFragment(new MeiziFragment());
                break;
            case R.id.nav_nba:
                switchFragment(new NBAFragment());
                break;
            case R.id.nav_terror:
                switchFragment(new AnecdoteFragment());
                break;
        }

    }
    //返回两次退出
    @Override
    public void onBackPressed() {
        //侧边菜单打开就先关闭
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //在2秒内按下两次就退出
            if((System.currentTimeMillis()- exitTime)>2000){
                Toast.makeText(MainActivity.this, "再点一次，退出", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            }else{
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    //侧边菜单监听器
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        SharePreferenceUtil.putNevigationItem(MainActivity.this, id); //保存当前的选择项
        if (id == R.id.nav_zhihu) {
            switchFragment(new ZhihuFragment());
        } else if (id == R.id.nav_wangyi) {
            switchFragment(new TopNewsFragment());
        } else if (id == R.id.nav_look) {
            switchFragment(new MeiziFragment());
        }else if(id == R.id.nav_nba){
            //switchFragment(new NBAFragment());
        }else if(id == R.id.nav_terror){
            switchFragment(new AnecdoteFragment());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //切换Fragment
    private void switchFragment(Fragment fragment) {

        if (currentFragment == null || !currentFragment
                .getClass().getName().equals(fragment.getClass().getName()))
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment)
                    .commit();  //替换FrameLayout中的fragment
        currentFragment = fragment;

    }
}
