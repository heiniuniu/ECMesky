package com.mesky.ui.home.NewFragments;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.mesky.R;
import com.mesky.ui.home.BaseListAdapter;

/**
 * Created by Dimitry Ivanov (mail@dimitryivanov.ru) on 29.03.2015.
 * 资讯
 */
public class ListViewFragment1 extends BaseNewsFragment {

    static final String TAG = "tag.ListViewFragment1";

    public static ListViewFragment1 newInstance(int color) {
        final Bundle bundle = new Bundle();
        bundle.putInt(ARG_COLOR, color);

        final ListViewFragment1 fragment = new ListViewFragment1();
        fragment.setArguments(bundle);
        return fragment;
    }

    private ListView mListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle sis) {

        final View view = inflater.inflate(R.layout.fragment_list_view, parent, false);

        mListView = findView(view, R.id.list_view);
        final BaseListAdapter adapter = new BaseListAdapter(getActivity(), 100);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "Click: " + position, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public CharSequence getTitle(Resources r) {
        return "资讯";
    }

    @Override
    public String getSelfTag() {
        return TAG;
    }

    @Override
    public boolean canScrollVertically(int direction) {
        return mListView != null && mListView.canScrollVertically(direction);
    }
}
