package org.hunter.a361camera.camera;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.hunter.a361camera.R;
import org.hunter.a361camera.util.ActivityUtils;

public class CameraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CameraFragment statisticsFragment = (CameraFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);
        if (statisticsFragment == null) {
            statisticsFragment = CameraFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    statisticsFragment, R.id.contentFrame);
        }
    }
}
