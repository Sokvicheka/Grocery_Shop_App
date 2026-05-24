package ite.rupp.edu.test1.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import ite.rupp.edu.test1.R;

public class TrackingFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tracking, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupStep(view.findViewById(R.id.step_placed), "Order Placed", "Your order has been received", true);
        setupStep(view.findViewById(R.id.step_confirmed), "Order Confirmed", "Seller has confirmed your order", true);
        setupStep(view.findViewById(R.id.step_processing), "Processing", "Your items are being packed", true);
        setupStep(view.findViewById(R.id.step_delivered), "Delivered", "Enjoy your grocery!", false);
    }

    private void setupStep(View stepView, String title, String desc, boolean isDone) {
        TextView txtTitle = stepView.findViewById(R.id.txt_step_title);
        TextView txtDesc = stepView.findViewById(R.id.txt_step_desc);
        View indicator = stepView.findViewById(R.id.view_indicator);

        txtTitle.setText(title);
        txtDesc.setText(desc);
        indicator.setAlpha(isDone ? 1.0f : 0.3f);
    }
}