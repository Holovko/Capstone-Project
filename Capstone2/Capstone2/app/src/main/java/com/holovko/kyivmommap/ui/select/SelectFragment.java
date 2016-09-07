package com.holovko.kyivmommap.ui.select;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.holovko.kyivmommap.R;
import com.holovko.kyivmommap.data.Constant;
import com.holovko.kyivmommap.model.firebase.Place;
import com.holovko.kyivmommap.model.firebase.Rubric;
import com.holovko.kyivmommap.service.PhotoService;
import com.holovko.kyivmommap.utils.CollectionUtils;
import com.holovko.kyivmommap.utils.ItemClickSupport;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectFragment extends Fragment implements SelectView {

    private static final String BUNDLE_NUM_COLUMNS = "bundle_num_columns";
    public static final String TAG = SelectFragment.class.getSimpleName();
    @BindView(R.id.rv_select_rubric)
    RecyclerView mRvSelectRubric;
    private OnSelectFragmentListener mListener;
    private GridLayoutManager mGridLayoutManager;
    private CardAdapter mAdapter;
    private int mNumColumns;

    public SelectFragment() {
        // Required empty public constructor
    }

    public static SelectFragment newInstance(int numColumns)
    {
        SelectFragment selectFragment = new SelectFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(BUNDLE_NUM_COLUMNS,numColumns);
        selectFragment.setArguments(bundle);
        return selectFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            mNumColumns = getArguments().getInt(BUNDLE_NUM_COLUMNS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_select, container, false);
        ButterKnife.bind(this, view);
        mRvSelectRubric.setHasFixedSize(true);
        mGridLayoutManager = new GridLayoutManager(getContext(), mNumColumns);
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
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void startServiceToCollectPhoto(Map<String, Place> places) {
        Intent intent = new Intent(getActivity(),PhotoService.class);
        intent.putExtra(PhotoService.BUNDLE_MAP_PLACES, CollectionUtils.mapToBundle(places));
        getActivity().startService(intent);
    }

    public interface OnSelectFragmentListener {
        void onRubricSelect(int rubricType);
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
            mListener.onRubricSelect(mDataset.get(position).getType());
        }

        @Override
        public int getItemCount() {
            return mDataset.size();
        }
    }


}
