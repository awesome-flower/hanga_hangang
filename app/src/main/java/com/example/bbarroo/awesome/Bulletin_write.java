package com.example.bbarroo.awesome;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Bulletin_write  extends AppCompatActivity {
    //retrofit이라는 라이브러리 사용 준비
    Retrofit retrofit;
    //만든 인터페이스 사용
    ApiService apiService;

    Toolbar toolbar;
    Button btn_write;
    ActionBar actionBar;
    int sel;
    private int PICK_IMAGE_REQUEST = 1;
    ImageView imgView;
    TextView title;
    TextView w_title;
    TextView w_content;
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.bulletin_wirte);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        btn_write = (Button) findViewById(R.id.btn_write);
        title = (TextView) findViewById(R.id.toolbar_title);
        w_title = (TextView) findViewById(R.id.write_title);
        w_content = (TextView) findViewById(R.id.write_content);

        ImageView camera = findViewById(R.id.camera);

        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //getSupportActionBar().setTitle(name);
        title.setText("새 글 작성");
        //actionBar.setTitle("새 글 작성");

        Intent intent = getIntent();
        sel = intent.getExtras().getInt("name");

        /*camera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
// Show only images, no videos or anything else
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
// Always show the chooser (if there are multiple options available)
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });*/

        btn_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
                okHttpClient.interceptors().add(new AddCookiesInterceptor(Bulletin_write.this));
                okHttpClient.interceptors().add(new ReceivedCookiesInterceptor(Bulletin_write.this));

                retrofit = new Retrofit.Builder().baseUrl(ApiService.API_URL).client(okHttpClient.build()).build();
                apiService = retrofit.create(ApiService.class);

                String v_t = w_title.getText().toString();
                String v_c = w_content.getText().toString();

                Call<ResponseBody> comment = apiService.post_board_write(v_t,v_c,sel);

                comment.enqueue(new Callback<ResponseBody>(){
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            String res = response.body().string();
                            Log.i("Test2", res);

                            Intent intent=new Intent(Bulletin_write.this, Bulletin_Main.class);
                            intent.putExtra("name",sel);
                            startActivity(intent);

                            ///Intent intent=new Intent(getActivity(),MainActivity.class);
                            //  startActivity(intent);
                        }
                        catch (Exception e){

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.i("text", "fail");
                    }
                });

            }
        });

    }

  /*  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            //이미지를 하나 골랐을때
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && null != data) {
                //data에서 절대경로로 이미지를 가져옴
                Uri uri = data.getData();

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                //이미지가 한계이상(?) 크면 불러 오지 못하므로 사이즈를 줄여 준다.
                int nh = (int) (bitmap.getHeight() * (1024.0 / bitmap.getWidth()));
                Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 1024, nh, true);

                imgView = (ImageView) findViewById(R.id.pic);
                imgView.setImageBitmap(scaled);

            } else {
                Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            Toast.makeText(this, "Oops! 로딩에 오류가 있습니다.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }*/
}
