package com.example.bbarroo.awesome;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MSF_Main extends Fragment {
    public MSF_Main(){}

    String res;
    //retrofit이라는 라이브러리 사용 준비
    Retrofit retrofit;

    //만든 인터페이스 사용
    ApiService apiService;

    MSF_LVA adapter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.main_frag_2, container,false);
        ListView listview = view.findViewById(R.id.listview1);
        adapter = new MSF_LVA() ;
        listview.setAdapter(adapter);

        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.interceptors().add(new AddCookiesInterceptor(getActivity()));
        okHttpClient.interceptors().add(new ReceivedCookiesInterceptor(getActivity()));

        retrofit = new Retrofit.Builder().baseUrl(ApiService.API_URL).client(okHttpClient.build()).build();
        apiService = retrofit.create(ApiService.class);

        Call<ResponseBody> comment = apiService.post_act_second();

        comment.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {

                    res = response.body().string();
                    Log.i("출력", res);
                    //Log.i("Test2", res);

                    JSONArray LVIarr=new JSONArray(res);
                    for(int i=0; i<LVIarr.length(); i++){
                        JSONObject lvi=LVIarr.getJSONObject(i);
                        String title=lvi.getString("title");
                        String date=lvi.getString("date");
                        int _id=lvi.getInt("_id");
                        String photo = lvi.getString("photo");

                        adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.leechon_home),title,date,_id);

                    }
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
       /* adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.banpo_home),"한강 어른이 놀이터(여의도)","08.11.토 ~ 08.12.일","15:00 ~ 21:00");
        adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.ttukseom_home),"한강몽땅 종이배경주대회(잠실)","08.10.금 ~ 08. 12.일","09:00~16:30");
        adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.banpo_home),"한강 어른이 놀이터(여의도)","08.11.토 ~ 08.12.일","15:00 ~ 21:00");
        adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.ttukseom_home),"한강몽땅 종이배경주대회(잠실)","08.10.금 ~ 08. 12.일","09:00~16:30");
        adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.banpo_home),"한강 어른이 놀이터(여의도)","08.11.토 ~ 08.12.일","15:00 ~ 21:00");
        adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.ttukseom_home),"한강몽땅 종이배경주대회(잠실)","08.10.금 ~ 08. 12.일","09:00~16:30");
        adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.banpo_home),"한강 어른이 놀이터(여의도)","08.11.토 ~ 08.12.일","15:00 ~ 21:00");
        adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.ttukseom_home),"한강몽땅 종이배경주대회(잠실)","08.10.금 ~ 08. 12.일","09:00~16:30");
        adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.banpo_home),"한강 어른이 놀이터(여의도)","08.11.토 ~ 08.12.일","15:00 ~ 21:00");
        adapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.ttukseom_home),"한강몽땅 종이배경주대회(잠실)","08.10.금 ~ 08. 12.일","09:00~16:30");*/

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(), activity_detail.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("_id",adapter.get_id(position));
                startActivity(intent);

            }
        });

        return view;

    }
}
