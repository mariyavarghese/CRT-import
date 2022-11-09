package com.example.crt.ui.gallery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.crt.DetailActivity;
import com.example.crt.R;
import com.example.crt.UserLoginpage;
import com.example.crt.databinding.FragmentGalleryBinding;
import com.example.crt.singleToneClass;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;
    private LinearLayout parentLinearLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);
        singleToneClass singleToneClass = com.example.crt.singleToneClass.getInstance();
        singleToneClass.setFrag(2);
        Log.d("frag",String.valueOf(singleToneClass.getFrag()));

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        parentLinearLayout=(LinearLayout) root.findViewById(R.id.alert_holder);
        for(int i=0;i<3;i++) {
            LayoutInflater inflaters = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View rowView = inflater.inflate(R.layout.alert_box, null);
            // Add the new row before the add field button.
            parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount() - 1);
        }
        //   final TextView textView = binding.textGallery;
        // galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}