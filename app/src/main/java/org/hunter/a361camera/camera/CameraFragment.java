package org.hunter.a361camera.camera;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.hunter.a361camera.R;

/**
 * Main UI for the statistics screen.
 */
public class CameraFragment extends Fragment {

    private TextView mStatisticsTV;

    public static CameraFragment newInstance() {
        return new CameraFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.camera_frag, container, false);
        mStatisticsTV = (TextView) root.findViewById(R.id.camera);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
