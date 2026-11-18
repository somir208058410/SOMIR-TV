package com.somir.tv;

import android.app.*;
import android.app.WallpaperManager;
import android.content.*;
import android.content.pm.*;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.*;
import android.widget.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainActivity extends Activity {
    private final int white=Color.WHITE, muted=Color.rgb(180,197,220);
    private LinearLayout root, dock;
    private TextView clock,date,heroTitle,heroSub;
    private final Handler handler=new Handler();
    private final int cyan=Color.rgb(75,220,255);

    @Override public void onCreate(Bundle b){
        super.onCreate(b);
        getWindow().setFlags(1024,1024);
        getWindow().getDecorView().setSystemUiVisibility(5894|1024|512);
        build();
        tick();
    }
    private GradientDrawable shape(int c,int stroke,int radius){
        GradientDrawable d=new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
            new int[]{c, darken(c)});
        d.setCornerRadius(radius);
        if(stroke!=0)d.setStroke(2,stroke);
        return d;
    }
    private int darken(int c){return Color.rgb((int)(Color.red(c)*.78),(int)(Color.green(c)*.78),(int)(Color.blue(c)*.78));}
    private TextView label(String s,int size,int color,boolean bold){
        TextView t=new TextView(this);t.setText(s);t.setTextSize(size);t.setTextColor(color);
        if(bold)t.setTypeface(Typeface.create("sans-serif",Typeface.BOLD));
        return t;
    }
    private void build(){
        root=new LinearLayout(this);root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(54,30,54,28);
        GradientDrawable bg=new GradientDrawable(GradientDrawable.Orientation.TL_BR,
            new int[]{Color.rgb(7,22,43),Color.rgb(8,43,69),Color.rgb(4,12,27)});
        root.setBackground(bg);setContentView(root);

        LinearLayout header=new LinearLayout(this);header.setGravity(Gravity.CENTER_VERTICAL);
        TextView logo=label("◢  SOMIR TV",29,white,true);
        logo.setLetterSpacing(.04f);
        header.addView(logo,new LinearLayout.LayoutParams(0,-2,1));
        LinearLayout time=new LinearLayout(this);time.setOrientation(LinearLayout.VERTICAL);time.setGravity(Gravity.RIGHT);
        clock=label("",24,white,true);date=label("",12,muted,false);
        time.addView(clock);time.addView(date);header.addView(time);
        root.addView(header);

        LinearLayout hero=new LinearLayout(this);hero.setOrientation(LinearLayout.VERTICAL);
        hero.setGravity(Gravity.CENTER_VERTICAL);hero.setPadding(6,0,0,0);
        LinearLayout.LayoutParams hp=new LinearLayout.LayoutParams(-1,0,1.15f);hp.topMargin=12;
        root.addView(hero,hp);
        TextView eyebrow=label("WELCOME TO YOUR ENTERTAINMENT SPACE",11,cyan,true);
        eyebrow.setLetterSpacing(.16f);hero.addView(eyebrow);
        heroTitle=label("A world of stories.",35,white,true);
        LinearLayout.LayoutParams tp=new LinearLayout.LayoutParams(-1,-2);tp.topMargin=8;hero.addView(heroTitle,tp);
        heroSub=label("Everything you love, beautifully in one place.",15,muted,false);
        LinearLayout.LayoutParams sp=new LinearLayout.LayoutParams(-1,-2);sp.topMargin=5;hero.addView(heroSub,sp);

        TextView section=label("FAVOURITES",11,muted,true);
        section.setLetterSpacing(.18f);
        LinearLayout.LayoutParams sec=new LinearLayout.LayoutParams(-1,-2);sec.topMargin=12;sec.bottomMargin=13;root.addView(section,sec);

        dock=new LinearLayout(this);dock.setGravity(Gravity.CENTER_VERTICAL);
        LinearLayout.LayoutParams dp=new LinearLayout.LayoutParams(-1,126);root.addView(dock,dp);
        addTile("YouTube","▶",Color.rgb(255,45,55),()->openYoutube());
        addTile("My File Manager","▰",Color.rgb(255,190,65),()->openFiles());
        addTile("My Apps","▦",Color.rgb(75,190,255),()->showApps());
        addTile("Video Player","▷",Color.rgb(174,112,255),()->openVideo());
        addTile("Wallpaper","▧",Color.rgb(75,220,190),()->changeWallpaper());

        TextView hint=label("Use your remote  •  ← → Navigate  •  OK Select  •  Back Return",11,muted,false);
        LinearLayout.LayoutParams fp=new LinearLayout.LayoutParams(-1,-2);fp.topMargin=18;root.addView(hint,fp);
        if(dock.getChildCount()>0)dock.getChildAt(0).requestFocus();
    }
    private void addTile(String title,String symbol,int accent,Runnable action){
        LinearLayout tile=new LinearLayout(this);tile.setOrientation(LinearLayout.VERTICAL);
        tile.setGravity(Gravity.CENTER);tile.setFocusable(true);tile.setFocusableInTouchMode(true);
        tile.setBackground(shape(Color.rgb(23,48,72),Color.rgb(57,102,137),20));
        TextView icon=label(symbol,34,accent,true);icon.setGravity(Gravity.CENTER);
        TextView name=label(title,13,white,true);name.setGravity(Gravity.CENTER);
        tile.addView(icon);LinearLayout.LayoutParams np=new LinearLayout.LayoutParams(-1,-2);np.topMargin=7;tile.addView(name,np);
        LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(0,118,1);p.setMargins(5,4,9,4);dock.addView(tile,p);
        tile.setOnFocusChangeListener((v,focus)->{
            v.animate().scaleX(focus?1.07f:1f).scaleY(focus?1.07f:1f).setDuration(120).start();
            v.setBackground(shape(focus?Color.rgb(30,83,113):Color.rgb(23,48,72),focus?cyan:Color.rgb(57,102,137),20));
        });
        tile.setOnClickListener(v->action.run());
    }
    private void tick(){
        clock.setText(new SimpleDateFormat("h:mm a",Locale.getDefault()).format(new Date()));
        date.setText(new SimpleDateFormat("EEEE, d MMMM",Locale.getDefault()).format(new Date()));
        handler.postDelayed(this::tick,30000);
    }
    private void toast(String s){Toast.makeText(this,s,Toast.LENGTH_SHORT).show();}
    private void launch(String pkg){
        Intent i=getPackageManager().getLaunchIntentForPackage(pkg);
        if(i!=null)startActivity(i);else toast("এই অ্যাপটি ইনস্টল করা নেই");
    }
    private void openYoutube(){
        if(getPackageManager().getLaunchIntentForPackage("com.google.android.youtube.tv")!=null)launch("com.google.android.youtube.tv");
        else launch("com.google.android.youtube");
    }
    private void openFiles(){
        Intent i=new Intent(Intent.ACTION_OPEN_DOCUMENT);i.setType("*/*");i.addCategory(Intent.CATEGORY_OPENABLE);
        try{startActivity(Intent.createChooser(i,"Choose a file"));}catch(Exception e){toast("File picker পাওয়া যায়নি");}
    }
    private void openVideo(){
        Intent i=new Intent(Intent.ACTION_OPEN_DOCUMENT);i.setType("video/*");i.addCategory(Intent.CATEGORY_OPENABLE);
        try{startActivity(Intent.createChooser(i,"Choose a video"));}catch(Exception e){toast("Video picker পাওয়া যায়নি");}
    }
    private void changeWallpaper(){
        Intent i=new Intent(Intent.ACTION_SET_WALLPAPER);
        try{startActivity(Intent.createChooser(i,"Choose wallpaper"));}catch(Exception e){toast("Wallpaper picker পাওয়া যায়নি");}
    }
    private void showApps(){
        PackageManager pm=getPackageManager();
        Intent q=new Intent(Intent.ACTION_MAIN);q.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> apps=pm.queryIntentActivities(q,0);
        Collections.sort(apps,(a,b)->a.loadLabel(pm).toString().compareToIgnoreCase(b.loadLabel(pm).toString()));
        LinearLayout list=new LinearLayout(this);list.setOrientation(LinearLayout.VERTICAL);list.setPadding(24,12,24,12);
        ScrollView scroll=new ScrollView(this);scroll.addView(list);
        for(ResolveInfo r:apps){
            String name=r.loadLabel(pm).toString();
            TextView item=label("  "+name,18,white,true);item.setGravity(Gravity.CENTER_VERTICAL);
            item.setPadding(16,12,16,12);item.setFocusable(true);item.setBackground(shape(Color.rgb(22,45,68),Color.TRANSPARENT,12));
            list.addView(item,new LinearLayout.LayoutParams(-1,58));
            item.setOnClickListener(v->{Intent launch=pm.getLaunchIntentForPackage(r.activityInfo.packageName);if(launch!=null)startActivity(launch);});
        }
        new AlertDialog.Builder(this).setTitle("My Apps").setView(scroll).setNegativeButton("Close",null).show();
    }
    @Override public boolean onKeyDown(int code,KeyEvent e){
        if(code==KeyEvent.KEYCODE_MENU){showApps();return true;}
        return super.onKeyDown(code,e);
    }
    @Override protected void onDestroy(){handler.removeCallbacksAndMessages(null);super.onDestroy();}
}
