package com.btsl.permit.data;


import com.btsl.permit.data.datamodel.Post;
import com.btsl.permit.data.datamodel.User;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/*
* Example of retrofit API interface
* */

public interface ApiService {

  // Get list of users
  @GET("users")
  Call<List<User>> getUsers();

  // Get single user by ID
  @GET("users/{id}")
  Call<User> getUserById(@Path("id") int userId);

  // Get posts with query parameters
  @GET("posts")
  Call<List<Post>> getPosts(@Query("userId") int userId);

  // Get all posts
  @GET("posts")
  Call<List<Post>> getAllPosts();

  // Get single post
  @GET("posts/{id}")
  Call<Post> getPostById(@Path("id") int postId);
}
