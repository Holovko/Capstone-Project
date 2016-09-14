package com.holovko.kyivmommap.ui.favourite;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.holovko.kyivmommap.Injection;
import com.holovko.kyivmommap.R;
import com.holovko.kyivmommap.model.firebase.Place;
import com.holovko.kyivmommap.ui.BaseActivity;
import com.holovko.kyivmommap.ui.details.DetailsActivity;

import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavouriteActivity extends BaseActivity implements FavouriteView {

    @BindView(R.id.rv_favourite_list)
    RecyclerView mRvFavouriteList;
    @BindView(R.id.tv_empty)
    TextView mTvEmpty;
    private FavouritePresenter mPresenter;
    private FavouriteAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        ButterKnife.bind(this);
        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setDisplayShowHomeEnabled(true);
            bar.setDisplayHomeAsUpEnabled(true);
        }
        //Favourite list
        mRvFavouriteList.setLayoutManager(new LinearLayoutManager(this));
        mPresenter = new FavouritePresenter(
                Injection.provideDataRepository(this),
                this,
                Injection.provideSharedPreferences(this)
        );
    }


    @Override
    public void showSuccessDeletedFromFavourite() {
        Toast.makeText(this, R.string.msg_place_was_removed, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showFavouriteList(Map<String, Place> places) {
        mAdapter = new FavouriteAdapter(places, new FavouriteAdapter.OnAdapterInteraction() {
            @Override
            public void onRemoveFromFavourite(String placeKey) {
                mPresenter.removeFromFavourite(placeKey);
            }

            @Override
            public void onDetailClick(Map.Entry<String, Place> entry) {
                mPresenter.initDetailFavourite(entry);
            }
        });
        mRvFavouriteList.setAdapter(mAdapter);
    }

    @Override
    public void refreshList(Map<String, Place> stringPlaceMap) {
        mAdapter.refreshData(stringPlaceMap);
    }

    @Override
    public void showDetails(String key, Place place) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(DetailsActivity.BUNDLE_KEY, key);
        intent.putExtra(DetailsActivity.BUNDLE_PLACE, place);
        startActivity(intent);
    }

    @Override
    public void showEmptyList(boolean isVisible) {
        mTvEmpty.setVisibility(isVisible?View.VISIBLE:View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = NavUtils.getParentActivityIntent(this);
                NavUtils.navigateUpTo(this, intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTvTitle;
        private final ImageButton mBtnRemove;
        private final LinearLayout mViewLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            mBtnRemove = (ImageButton) itemView.findViewById(R.id.btn_remove_from_favourite);
            mViewLayout = (LinearLayout) itemView.findViewById(R.id.ll_favourite_item);
        }
    }


    public static class FavouriteAdapter extends RecyclerView.Adapter<MyViewHolder> {

        private LinkedHashMap<String, Place> mDataSet;
        private OnAdapterInteraction mListener;

        public FavouriteAdapter(Map<String, Place> dataSet, OnAdapterInteraction listener) {
            mListener = listener;
            mDataSet = new LinkedHashMap<>(dataSet);
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favourite, parent, false);
            return new MyViewHolder(v);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            final Map.Entry<String, Place> entry = getByPosition(position);
            holder.mTvTitle.setText(entry.getValue().title());
            holder.mBtnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onRemoveFromFavourite(entry.getKey());
                }
            });
            holder.mViewLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onDetailClick(entry);
                }
            });
        }

        public void refreshData(Map<String, Place> dataSet) {
            mDataSet = new LinkedHashMap<>(dataSet);
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return mDataSet.size();
        }


        private Map.Entry<String, Place> getByPosition(int position) {
            int j = 0;
            for (Map.Entry<String, Place> stringPlaceEntry : mDataSet.entrySet()) {
                if (j++ == position) return stringPlaceEntry;
            }
            throw new IllegalArgumentException();
        }

        interface OnAdapterInteraction {
            void onRemoveFromFavourite(String placeKey);

            void onDetailClick(Map.Entry<String, Place> entry);
        }
    }

}
