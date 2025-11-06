package com.btsl.permit.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.btsl.permit.data.datamodel.Post;
import com.btsl.permit.data.datamodel.User;
import com.btsl.permit.domain.UserRepository;
import java.util.List;

public class UserViewModel extends ViewModel {

  private UserRepository repository;
  private MutableLiveData<List<User>> usersLiveData;
  private MutableLiveData<User> userLiveData;
  private MutableLiveData<List<Post>> postsLiveData;
  private MutableLiveData<Boolean> isLoading;
  private MutableLiveData<String> errorMessage;

  public UserViewModel() {
    repository = new UserRepository();
    usersLiveData = new MutableLiveData<>();
    userLiveData = new MutableLiveData<>();
    postsLiveData = new MutableLiveData<>();
    isLoading = new MutableLiveData<>(false);
    errorMessage = new MutableLiveData<>();
  }

  // Get all users
  public LiveData<List<User>> getUsers() {
    isLoading.setValue(true);
    usersLiveData = repository.getUsers();
    isLoading.setValue(false);
    return usersLiveData;
  }

  // Get user by ID
  public LiveData<User> getUserById(int userId) {
    isLoading.setValue(true);
    userLiveData = repository.getUserById(userId);
    isLoading.setValue(false);
    return userLiveData;
  }

  // Get all posts
  public LiveData<List<Post>> getAllPosts() {
    isLoading.setValue(true);
    postsLiveData = repository.getAllPosts();
    isLoading.setValue(false);
    return postsLiveData;
  }

  // Get posts by user ID
  public LiveData<List<Post>> getPostsByUserId(int userId) {
    isLoading.setValue(true);
    postsLiveData = repository.getPostsByUserId(userId);
    isLoading.setValue(false);
    return postsLiveData;
  }

  // Getters for loading and error states
  public LiveData<Boolean> isLoading() {
    return isLoading;
  }

  public LiveData<String> getErrorMessage() {
    return errorMessage;
  }

  // Refresh data
  public void refreshUsers() {
    getUsers();
  }

  public void refreshPosts() {
    getAllPosts();
  }
}