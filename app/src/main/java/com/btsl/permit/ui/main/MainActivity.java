package com.btsl.permit.ui.main;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import com.btsl.permit.R;
import com.btsl.permit.data.datamodel.User;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  private static final int CAMERA_PERMISSION_CODE = 100;
  private TextView resultStatus;
  private Button btnCheckPermission;
  private Button btnShowDialog;
  private ProgressBar progressBar;

  private UserViewModel viewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_main);

    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
      Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
      return insets;
    });

    resultStatus = findViewById(R.id.tvResultStatus);
    btnCheckPermission = findViewById(R.id.btnCheckPermission);
    btnShowDialog = findViewById(R.id.btnShowDialog);
    progressBar = findViewById(R.id.progressBar);

    viewModel = new ViewModelProvider(this).get(UserViewModel.class);

    // Check initial permission status
    updatePermissionStatus();

    // Button to request camera permission
    btnCheckPermission.setOnClickListener(v -> checkCameraPermission());

    // Button to show custom dialog
    btnShowDialog.setOnClickListener(v -> showCustomDialog());

    //getObserveUserData();
  }

  /*
  * will return multiple user list and observe data from viewmodel
  * */
  private void getDummyUserData() {
    viewModel.getUserById(1).observe(this, user -> {
      progressBar.setVisibility(View.GONE);
      if (user != null) {
        displayUser(user);
        Toast.makeText(this, "User loaded: " + user.getName(), Toast.LENGTH_SHORT).show();
      } else {
        resultStatus.setText("User not found or error occurred");
        Toast.makeText(this, "Failed to load user", Toast.LENGTH_SHORT).show();
      }
    });
  }

  /*
   * This method is to observe data from view model
   * */
  private void getObserveUserData() {

    progressBar.setVisibility(View.VISIBLE);
    resultStatus.setText("Loading users...");

    viewModel.getUsers().observe(this, users -> {
      progressBar.setVisibility(View.GONE);
      if (users != null && !users.isEmpty()) {
        displayUsers(users);
        Toast.makeText(this, "Loaded " + users.size() + " users", Toast.LENGTH_SHORT).show();
      } else {
        resultStatus.setText("No users found or error occurred");
        Toast.makeText(this, "Failed to load users", Toast.LENGTH_SHORT).show();
      }
    });
  }

  /*
   * Display user
   * */
  private void displayUser(User user) {
    StringBuilder result = new StringBuilder();
    result.append("=== USER DETAILS ===\n\n");
    result.append("ID: ").append(user.getId()).append("\n");
    result.append("Name: ").append(user.getName()).append("\n");

    resultStatus.setText(result.toString());
  }

  /*
   * Display a list of user
   * */
  private void displayUsers(List<User> users) {
    StringBuilder result = new StringBuilder();
    result.append("=== USERS LIST ===\n\n");

    for (User user : users) {
      result.append("ID: ").append(user.getId()).append("\n");
      result.append("Name: ").append(user.getName()).append("\n");
      result.append("-------------------\n\n");
    }

    resultStatus.setText(result.toString());
  }

  private void checkCameraPermission() {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        == PackageManager.PERMISSION_GRANTED) {
      // Permission already granted
      Toast.makeText(this, "Camera permission already granted!", Toast.LENGTH_SHORT).show();
      updatePermissionStatus();
    } else {
      // Request permission
      ActivityCompat.requestPermissions(this,
          new String[] { Manifest.permission.CAMERA },
          CAMERA_PERMISSION_CODE);
    }
  }

  private void updatePermissionStatus() {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        == PackageManager.PERMISSION_GRANTED) {
      resultStatus.setText("Camera Permission: GRANTED");
      resultStatus.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
    } else {
      resultStatus.setText("Camera Permission: NOT GRANTED");
      resultStatus.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
    }
  }

  /*
  * A simple dialog
  * yes press will call api to get user data
  *
  * */
  private void showCustomDialog() {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle("Information");
    builder.setMessage("This is a custom dialog. Choose your action!");

    // Positive button
    builder.setPositiveButton("Accept", (dialog, which) ->
    {
      getDummyUserData();
      Toast.makeText(MainActivity.this, "You clicked Accept!", Toast.LENGTH_SHORT).show();
    });

    // Negative button
    builder.setNegativeButton("Cancel", (dialog, which) -> {
      Toast.makeText(MainActivity.this, "You clicked Cancel!", Toast.LENGTH_SHORT).show();
      dialog.dismiss();
    });

    AlertDialog dialog = builder.create();
    dialog.show();
  }
}