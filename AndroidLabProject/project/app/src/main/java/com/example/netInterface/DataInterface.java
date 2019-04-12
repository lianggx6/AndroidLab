package com.example.netInterface;

import com.example.modelClass.demands_collect;
import com.example.modelClass.demands_publish_all;
import com.example.modelClass.goods_collect;
import com.example.modelClass.goods_publish_all;
import com.example.modelClass.my_collect;
import com.example.modelClass.my_publish;
import com.example.modelClass.user;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;
import com.example.modelClass.messages;

public interface DataInterface {
    @GET("goods_publish_all/get")
    Observable<List<goods_publish_all>> getgoods_publish_all();

    @GET("demands_publish_all/get")
    Observable<List<demands_publish_all>> getdemands_publish_all();

    @GET("user/get/{attr}/{value}")
    Observable<List<user>> getuser(@Path("attr") String attr,@Path("value") String value);


    @POST("goods_publish_all/getlike")
    @FormUrlEncoded
    Observable<List<goods_publish_all>> getlikebypara1(@Field("para1") String para1) ;

    @POST ("user/post")
    @FormUrlEncoded
    Observable<user> adduser(@Field("name") String name,
                             @Field("sex") String sex,
                             @Field("email") String email,
                             @Field("phone") String phone,
                             @Field("password") String password) ;

    @POST("goods_collect/post")
    @FormUrlEncoded
    Observable<goods_collect> addgoodscollect(@Field("collector_id") String collector_id,
                                                    @Field("goods_id") String goods_id);

    @POST("demands_collect/post")
    @FormUrlEncoded
    Observable<demands_collect> adddemandscollect(@Field("collector_id") String collector_id,
                                                        @Field("goods_id") String goods_id);


    @GET("chat/getlist/{id}")
    Observable<List<messages>> getmessage(@Path("id") String id);

    @GET("chat/get/{id1}/{id2}")
    Observable<List<messages>> getdialog(@Path("id1") String id1,@Path("id2") String id2);

    @POST("chat/post")
    @FormUrlEncoded
    Observable<messages> addchat(@Field("sender_id") String sender_id,
                             @Field("reciver_id") String reciver_id,
                             @Field("content") String content) ;

    //我收藏的（商品需求混在一起，judge_id=1 为需求，0为商品）
    @GET("mycollect/get/{myid}")
    Observable<List<my_collect>> getmycollect(@Path("myid") String myid);

    //我发布的（商品需求混在一起，judge_id=1 为需求，0为商品）
    @GET("mypublish/get/{myid}")
    Observable<List<my_publish>> getmypublish(@Path("myid") String myid);

    //发布商品（后五项参数是商品参数）
    @POST("goods_publish_all/post")
    @FormUrlEncoded
    Observable<goods_publish_all> post_goods(@Field("myid") String myid,
                                                   @Field("name") String name,
                                                   @Field("type") String type,
                                                   @Field("information") String information,
                                                   @Field("price") String price,
                                                   @Field("image") String image) ;
    //发布需求（后五项参数是商品参数）
    @POST("demands_publish_all/post")
    @FormUrlEncoded
    Observable<demands_publish_all> post_demands(@Field("myid") String myid,
                                                       @Field("name") String name,
                                                       @Field("type") String type,
                                                       @Field("information") String information,
                                                       @Field("price") String price,
                                                       @Field("image") String image) ;


}
