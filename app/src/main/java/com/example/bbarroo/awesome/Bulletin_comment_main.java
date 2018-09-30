package com.example.bbarroo.awesome;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Bulletin_comment_main extends AppCompatActivity {

    int sel;
    TextView btn_comment;
    Bulletin_comment_LVA adapter;
    //retrofit이라는 라이브러리 사용 준비
    Retrofit retrofit;
    //만든 인터페이스 사용
    ApiService apiService;
    EditText w_content;
    int board_id;
    String res;

    TextView nick;
    TextView cont;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bulletin_detail);
       ListView listview = findViewById(R.id.comment);
        btn_comment = findViewById(R.id.comment_write);
        w_content = findViewById(R.id.write_comment);

        nick = findViewById(R.id.nickname);
        cont = findViewById(R.id.d_content);

        Intent intent = getIntent();
        sel = intent.getExtras().getInt("name");
        board_id = intent.getExtras().getInt("board_id");

        adapter = new Bulletin_comment_LVA() ;
        listview.setAdapter(adapter);

        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.interceptors().add(new AddCookiesInterceptor(Bulletin_comment_main.this));
        okHttpClient.interceptors().add(new ReceivedCookiesInterceptor(Bulletin_comment_main.this));

        retrofit = new Retrofit.Builder().baseUrl(ApiService.API_URL).client(okHttpClient.build()).build();
        apiService = retrofit.create(ApiService.class);

        Call<ResponseBody> comment = apiService.post_board_detail(board_id,sel);

        comment.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {

                    res = response.body().string();
                    Log.i("Test2", res);

                    JSONObject lvi = new JSONObject(res);
                    String v_title=lvi.getString("b_title");
                    String v_content=lvi.getString("b_content");
                    String v_nic=lvi.getString("b_nic");

                    nick.setText(v_nic);
                    cont.setText(v_content);

                  /*  JSONArray LVIarr=new JSONArray(res);
                    for(int i=0; i<LVIarr.length(); i++){
                        JSONObject lvi=LVIarr.getJSONObject(i);
                        String title=lvi.getString("title");
                        String what=lvi.getString("content");
                        String who=lvi.getString("nic");
                        String when=lvi.getString("time");
                        int id = lvi.getInt("_id");

                        Log.i("출력", title+what+who+when);
                        adapter.addItem(title,what,who,when,id);
                    }
*/
                    adapter.notifyDataSetChanged();



                    ///Intent intent=new Intent(getActivity(),MainActivity.class);
                    //  startActivity(intent);
                }
                catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

        // 아이템 추가하는 방법 (제목, 내용, 이름, 시간)
      adapter.addItem("날씨가 진짜 좋아요!","어사화");
        adapter.addItem("맞아요!!","하핫");
        adapter.addItem("저는 비가 좋은데","히힛");
        adapter.addItem("비오는날 막걸리","모두너");


        btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent=new Intent(Bulletin_comment_main.this, Bulletin_Main.class);
                intent.putExtra("name",sel);
                startActivity(intent);*/


                String v_c = w_content.getText().toString();

                Call<ResponseBody> comment = apiService.post_board_comment(v_c,board_id);

                comment.enqueue(new Callback<ResponseBody>(){
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            String res = response.body().string();
                            Log.i("Test2", res);

                            Intent intent=new Intent(Bulletin_comment_main.this, Bulletin_Main.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

                            intent.putExtra("name",sel);
                            startActivity(intent);

                            ///Intent intent=new Intent(getActivity(),MainActivity.class);
                            //  startActivity(intent);
                            adapter.notifyDataSetChanged();
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
}
