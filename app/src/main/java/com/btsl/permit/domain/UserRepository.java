package com.btsl.permit.domain;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.btsl.permit.data.ApiService;
import com.btsl.permit.data.RetrofitClient;
import com.btsl.permit.data.datamodel.Post;
import com.btsl.permit.data.datamodel.User;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {

  private static final String TAG = "UserRepository";
  private ApiService apiService;

  public UserRepository() {
    apiService = RetrofitClient.getInstance().getApiService();
  }

  // Get all users
  public MutableLiveData<List<User>> getUsers() {
    MutableLiveData<List<User>> usersLiveData = new MutableLiveData<>();

    apiService.getUsers().enqueue(new Callback<List<User>>() {
      @Override
      public void onResponse(Call<List<User>> call, Response<List<User>> response) {
        if (response.isSuccessful() && response.body() != null) {
          usersLiveData.setValue(response.body());
          Log.d(TAG, "Users fetched successfully: " + response.body().size());
        } else {
          usersLiveData.setValue(null);
          Log.e(TAG, "Failed to fetch users: " + response.code());
        }
      }

      @Override
      public void onFailure(Call<List<User>> call, Throwable t) {
        usersLiveData.setValue(null);
        Log.e(TAG, "Error fetching users: " + t.getMessage());
      }
    });

    return usersLiveData;
  }

  // Get user by ID
  public MutableLiveData<User> getUserById(int userId) {
    MutableLiveData<User> userLiveData = new MutableLiveData<>();

    apiService.getUserById(userId).enqueue(new Callback<User>() {
      @Override
      public void onResponse(Call<User> call, Response<User> response) {
        if (response.isSuccessful() && response.body() != null) {
          userLiveData.setValue(response.body());
          Log.d(TAG, "User fetched: " + response.body().getName());
        } else {
          userLiveData.setValue(null);
          Log.e(TAG, "Failed to fetch user: " + response.code());
        }
      }

      @Override
      public void onFailure(Call<User> call, Throwable t) {
        userLiveData.setValue(null);
        Log.e(TAG, "Error fetching user: " + t.getMessage());
      }
    });

    return userLiveData;
  }

  // Get all posts
  public MutableLiveData<List<Post>> getAllPosts() {
    MutableLiveData<List<Post>> postsLiveData = new MutableLiveData<>();

    apiService.getAllPosts().enqueue(new Callback<List<Post>>() {
      @Override
      public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
        if (response.isSuccessful() && response.body() != null) {
          postsLiveData.setValue(response.body());
          Log.d(TAG, "Posts fetched successfully: " + response.body().size());
        } else {
          postsLiveData.setValue(null);
          Log.e(TAG, "Failed to fetch posts: " + response.code());
        }
      }

      @Override
      public void onFailure(Call<List<Post>> call, Throwable t) {
        postsLiveData.setValue(null);
        Log.e(TAG, "Error fetching posts: " + t.getMessage());
      }
    });

    return postsLiveData;
  }

  // Get posts by user ID
  public MutableLiveData<List<Post>> getPostsByUserId(int userId) {
    MutableLiveData<List<Post>> postsLiveData = new MutableLiveData<>();

    apiService.getPosts(userId).enqueue(new Callback<List<Post>>() {
      @Override
      public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
        if (response.isSuccessful() && response.body() != null) {
          postsLiveData.setValue(response.body());
          Log.d(TAG, "User posts fetched: " + response.body().size());
        } else {
          postsLiveData.setValue(null);
          Log.e(TAG, "Failed to fetch user posts: " + response.code());
        }
      }

      @Override
      public void onFailure(Call<List<Post>> call, Throwable t) {
        postsLiveData.setValue(null);
        Log.e(TAG, "Error fetching user posts: " + t.getMessage());
      }
    });

    return postsLiveData;
  }
}