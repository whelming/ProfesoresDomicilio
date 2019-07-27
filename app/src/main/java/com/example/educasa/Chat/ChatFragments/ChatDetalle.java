package com.example.educasa.Chat.ChatFragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.educasa.Chat.Chat;
import com.example.educasa.R;

public class ChatDetalle extends Fragment {

    public ChatDetalle() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chat_detalle, container, false);
        String bundlerecibido = getArguments().getString("datousuario");
        Toolbar toolbar = v.findViewById(R.id.chat_toolbar_detalle);
        toolbar.setTitle(bundlerecibido);

        if (getActivity() != null) {
            ((Chat) getActivity()).setSupportActionBar(toolbar);
            ((Chat) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((Chat) getActivity()).getSupportActionBar().show();
        }

        return v;
    }

}
