package com.holovko.kyivmommap.ui.select;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.holovko.kyivmommap.R;
import com.holovko.kyivmommap.data.Constant;
import com.holovko.kyivmommap.model.firebase.Rubric;
import com.holovko.kyivmommap.ui.map.MapActivity;
import com.holovko.kyivmommap.utils.ItemClickSupport;
import com.holovko.kyivmommap.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SelectFragment extends Fragment {

    @BindView(R.id.rv_select_rubric)
    RecyclerView mRvSelectRubric;
    private OnSelectFragmentListener mListener;
    private Unbinder mUnBinder;
    private GridLayoutManager mGridLayoutManager;
    private CardAdapter mAdapter;

    public SelectFragment() {
        // Required empty public constructor
    }

    public static SelectFragment newInstance() {
        return new SelectFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_select, container, false);
        ButterKnife.bind(this, view);
        mRvSelectRubric.setHasFixedSize(true);
        mGridLayoutManager = new GridLayoutManager(getContext(), 2);
        mRvSelectRubric.setLayoutManager(mGridLayoutManager);
        mAdapter = new CardAdapter(Constant.getListRubric());
        mRvSelectRubric.setAdapter(mAdapter);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSelectFragmentListener) {
            mListener = (OnSelectFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // mUnBinder.unbind();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnSelectFragmentListener {
        void onRubricSelect(@Constant.RubricType int rubricType);
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_background)
        ImageView mIvBackground;
        @BindView(R.id.tv_name)
        TextView mTvName;

        public CardViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select_rubric,
                    parent, false));
            ButterKnife.bind(this, itemView);
        }
    }

    public class CardAdapter extends RecyclerView.Adapter {
        private List<Rubric> mDataset;

        public CardAdapter(List<Rubric> myDataset) {
            mDataset = myDataset;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
            return new CardViewHolder(parent);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            CardViewHolder viewHolder = (CardViewHolder) holder;
            Rubric item = mDataset.get(position);
            viewHolder.mTvName.setText(item.getName());
            viewHolder.mIvBackground.setBackgroundResource(item.getBackground());
            ItemClickSupport.addTo(mRvSelectRubric).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    showSelectedType(position);
                }
            });
        }

        public void showSelectedType(int position) {
            //TODO in second version - remove this checking
            if(!Utils.isOnline(getActivity())){
                Toast.makeText(getActivity(),getString(R.string.msg_connect_to_internet),Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(getContext(), MapActivity.class);
            intent.putExtra(MapActivity.BUNDLE_RUBRIC_TYPE, mDataset.get(position).getType());
            startActivity(intent);
        }

        @Override
        public int getItemCount() {
            return mDataset.size();
        }
    }


}