package com.example.educasa.Chat.ChatFragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.educasa.Chat.Chat;
import com.example.educasa.Chat.ChatAdapters.AdaptadorChat;
import com.example.educasa.Chat.ChatModels.ContentChat;
import com.example.educasa.R;

import java.util.ArrayList;
import java.util.List;

public class ChatEjm extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    List<ContentChat> milista;

    public ChatEjm() {}

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_chat_ejm, container, false);
        Toolbar toolbar = v.findViewById(R.id.chat_toolbar);
        toolbar.setTitle("Chat General");
        if (getActivity() != null) {
            ((Chat)getActivity()).setSupportActionBar(toolbar);
        }
        recyclerView = v.findViewById(R.id.chat_recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        milista = new ArrayList<>();
        LlenaMILista();

        recyclerView.setAdapter(new AdaptadorChat(getActivity(), milista, new AdaptadorChat.OnItemClickListener() {
            @Override public void onItemClick(ContentChat item) {
                if (getActivity() != null){
                    Bundle bundle = new Bundle();
                    bundle.putString("datousuario", item.getTextodemo());
                    ChatDetalle fragmentDetalleChat = new ChatDetalle();
                    fragmentDetalleChat.setArguments(bundle);
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frag_chat_principal, fragmentDetalleChat, "fragmentDetalleChat")
                            .addToBackStack(null).commit();
                }
            }
        }));

        return v;
    }

    private void LlenaMILista() {
        milista.add(new ContentChat(1,"Juan Perez"));
        milista.add(new ContentChat(2,"Humberto Aguilar"));
        milista.add(new ContentChat(3,"Maria Sotelo"));
        milista.add(new ContentChat(4,"Daniela Avila"));
        milista.add(new ContentChat(5,"Edwin Cardenas"));
        milista.add(new ContentChat(6,"Willy Patiño"));
        milista.add(new ContentChat(7,"Julia Trujillo"));
        milista.add(new ContentChat(8,"Maria Huarcaya"));
        milista.add(new ContentChat(9,"Pedro Lopez"));
        milista.add(new ContentChat(10,"Humberto Santarosa"));
        milista.add(new ContentChat(11,"Benji Arenas"));
        milista.add(new ContentChat(12,"Juan Carlos Santos"));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.chat_buscador,menu);
        MenuItem mSearch = menu.findItem(R.id.chat_buscar);
        SearchView mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Toast.makeText(getActivity(), "submit chat", Toast.LENGTH_SHORT).show();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        super.onCreateOptionsMenu(menu,inflater);
    }
}
