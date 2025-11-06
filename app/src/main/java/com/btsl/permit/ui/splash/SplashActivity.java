package com.btsl.permit.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.btsl.permit.databinding.ActivitySplashBinding;
import com.btsl.permit.ui.main.MainActivity;
import java.util.Objects;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashActivity extends AppCompatActivity {

  private String TAG = SplashActivity.class.getSimpleName();

  /**
   * Some older devices needs a small delay between UI widget updates
   * and a change of the status and navigation bar.
   */
  private static final int UI_ANIMATION_DELAY = 3000;
  private ActivitySplashBinding binding;
  private final Runnable mHideRunnable = this::hide;

  private final Handler mHideHandler = new Handler(Objects.requireNonNull(Looper.myLooper()));
  private final Runnable mHidePart2Runnable = () -> {

    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
    startActivity(intent);

    finish();
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    binding = ActivitySplashBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    hide();
  }

  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);

    // Trigger the initial hide() shortly after the activity has been
    // created, to briefly hint to the user that UI controls
    // are available.
    delayedHide();
  }

  private void hide() {
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.hide();
    }

    // Schedule a runnable to remove the status and navigation bar after a delay
    mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
  }

  /**
   * Schedules a call to hide() in delay milliseconds, canceling any
   * previously scheduled calls.
   */
  private void delayedHide() {
    mHideHandler.removeCallbacks(mHideRunnable);
  }
}