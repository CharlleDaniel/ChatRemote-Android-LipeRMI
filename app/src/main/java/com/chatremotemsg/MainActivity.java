package com.chatremotemsg;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.chatremotemsg.adapters.TabAdapters;
import com.chatremotemsg.conn.ConnGetMessages;
import com.chatremotemsg.conn.ConnHasMessage;
import com.chatremotemsg.controller.ClientController;
import com.chatremotemsg.extras.CircleImageView;
import com.chatremotemsg.extras.SettingsActivity;
import com.chatremotemsg.extras.SlidingTabLayout;
import com.chatremotemsg.fragments.FragUsers;
import com.server.User;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity {
    private Toolbar bar;
    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;
    private int tabPosition;

    private static final int RESULT_LOAD_IMAGE = 500;
    private CircleImageView circleImageView;
    private byte[] img;
    private Dialog d;
    public static String name;
    public static ClientController sistema;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sistema= new ClientController(this);
        bar = (Toolbar) findViewById(R.id.toolbar);
        bar.setTitle("ChatRemote");
        bar.setTitleTextAppearance(this, R.style.AppThemeBarTitle);
        bar.setSubtitleTextAppearance(this, R.style.AppThemeBarSubTitle);
        setSupportActionBar(bar);
        mViewPager = (ViewPager) findViewById(R.id.vp_tabs);
        mViewPager.setAdapter(new TabAdapters(getSupportFragmentManager(), this));

        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.stl_tabs);
        mSlidingTabLayout.setDistributeEvenly(true);
        mSlidingTabLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        mSlidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this, android.R.color.white));
        mSlidingTabLayout.setCustomTabView(R.layout.custom_tab_view, R.id.tv_tab);

        mSlidingTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                tabPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        mSlidingTabLayout.setViewPager(mViewPager);
        hasUpdate();
        loginviews();
    }


    public void loginviews() {
        progressBar= (ProgressBar)findViewById(R.id.progressBar);
        User profile= sistema.getProfile();
        if(profile!=null){

            if(!sistema.hasLogged(profile.getNumber())){
                showDialog();
            }else{
                sistema.refreshUsers(progressBar);
                bar.setTitle(profile.getName());
                bar.setSubtitle("Online");
            }

        }else{
            showDialog();
        }
    }
    public void showDialog(){
        d = new Dialog(this);
        d.setContentView(R.layout.dialog_sim_nao_text);
        d.setTitle("Login");
        final RelativeLayout rlNumber= (RelativeLayout)d.findViewById(R.id.rl_number);
        final RelativeLayout rlProfile= (RelativeLayout)d.findViewById(R.id.rl_profile);
        Button btOk = (Button) d.findViewById(R.id.bt_ok);
        final Button btProximo = (Button) d.findViewById(R.id.bt_proximo);
        final ProgressBar pbNumber= (ProgressBar)d.findViewById(R.id.pb_number);


        final EditText mEDNumber = (EditText) d.findViewById(R.id.ed_number);
        final EditText mEDName = (EditText) d.findViewById(R.id.ed_name);

        circleImageView= (CircleImageView) d.findViewById(R.id.CIV_User);
        circleImageView.setDrawingCacheEnabled(true);
        circleImageView.buildDrawingCache();
        btProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = mEDNumber.getText().toString().trim();

                if (number.length() < 1 || number.length() < 10) {
                    showMessage("Preencha o com um número válido."+number.length());
                } else {
                    btProximo.setVisibility(View.GONE);
                    mEDNumber.setVisibility(View.GONE);
                    pbNumber.setVisibility(View.VISIBLE);
                    if (!sistema.hasLogged(number)) {

                        pbNumber.setVisibility(View.GONE);
                        rlProfile.setVisibility(View.VISIBLE);

                    } else {

                        bar.setTitle(sistema.getProfile().getName());
                        bar.setSubtitle("Online");
                        d.dismiss();
                        sistema.refreshUsers(progressBar);

                    }


                }
            }
        });

        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = mEDName.getText().toString().trim();
                boolean teste = false;
                if (img == null) {
                    Bitmap bm = circleImageView.getDrawingCache();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bm.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    img = stream.toByteArray();
                }
                String number = mEDNumber.getText().toString().trim();
                teste = sistema.login(name, img, number);

                if (teste) {
                    d.dismiss();

                    bar.setTitle(sistema.getProfile().getName());
                    bar.setSubtitle("Online");
                    d.dismiss();
                    sistema.refreshUsers(progressBar);
                }
            }
        });
        d.setCancelable(false);

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFoto(v);
            }
        });
        d.show();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent it = new Intent(this,SettingsActivity.class);
            startActivity(it);
            return true;
        }
        if (id == android.R.id.home) {
            onBackPressed();
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void hasUpdate() {
        showMessage("Aguardando.");
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    if(sistema.getProfile()!=null){
                        ConnHasMessage conn=new ConnHasMessage(sistema.getIPServer(),sistema.getProfile().getId());
                        try {

                            if(conn.execute().get().booleanValue()){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        sistema.reciveMSG();
                                    }
                                });
                            }
                            Thread.sleep(2500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        }).start();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    public void addFoto(View v) {

        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            try {
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
                bitmap = getResizedBitmap(bitmap, 100, 100);
                circleImageView.setImageBitmap(bitmap);
                ByteArrayOutputStream saida = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, saida);
                img = saida.toByteArray();
            }catch (Exception e){
                requestPermission(this);
            }
        }
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }
    private static void requestPermission(final Context context){
        if(ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example if the user has previously denied the permission.

            new AlertDialog.Builder(context)
                    .setMessage("Você precisa ativar as permissões do ChatRemote.")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                                    1);
                        }
                    }).show();

        }else {
            // permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions((Activity)context,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                if (grantResults.length >= 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showMessage("Permissão concedida.");

                } else {
                    showMessage("Permissão Negada.");
                    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                }
                return;
            }
        }
    }

}