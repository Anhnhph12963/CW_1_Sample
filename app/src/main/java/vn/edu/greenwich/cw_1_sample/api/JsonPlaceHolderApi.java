package vn.edu.greenwich.cw_1_sample.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import vn.edu.greenwich.cw_1_sample.models.UploadModels;
import vn.edu.greenwich.cw_1_sample.models.UploadResponse;

public interface JsonPlaceHolderApi {


//@POST("sendPayLoad")
//    Call<UploadResponse>createPost(@Body String a);

@FormUrlEncoded
    @POST("sendPayLoad")
    Call<UploadResponse>createPost(@Field("jsonpayload") String jsonpayload);
}
