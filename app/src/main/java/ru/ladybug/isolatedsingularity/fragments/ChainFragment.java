package ru.ladybug.isolatedsingularity.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.Locale;
import java.util.Objects;

import ru.ladybug.isolatedsingularity.ChainData;
import ru.ladybug.isolatedsingularity.LocalState;
import ru.ladybug.isolatedsingularity.R;
import ru.ladybug.isolatedsingularity.adapters.ContributorsAdapter;
import ru.ladybug.isolatedsingularity.net.StatefulActivity;
import ru.ladybug.isolatedsingularity.net.StatefulFragment;

/** Stateful fragment with chain data, list of contributors and button for contribution */
public class ChainFragment extends StatefulFragment {

    private ContributorsAdapter contributorsAdapter;
    private TextView chainTitle;
    private TextView myContribution;
    private ProgressBar serverResponseBar;
    private Button smallBoostButton;

    private LocalState state;

    // зачем этот конструктор?
    public ChainFragment() {

    }

    /** {@inheritDoc} */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.chain_fragment, container, false);

        RecyclerView contributorsList = view.findViewById(R.id.contributorsView);
        contributorsList.setLayoutManager(new LinearLayoutManager(getContext()));
        contributorsAdapter = new ContributorsAdapter();
        contributorsList.setAdapter(contributorsAdapter);

        chainTitle = view.findViewById(R.id.chainTitle);
        myContribution = view.findViewById(R.id.myContribution);

        serverResponseBar = view.findViewById(R.id.serverResponseBar);
        serverResponseBar.setVisibility(View.INVISIBLE);

        smallBoostButton = view.findViewById(R.id.smallBoostButton);
        smallBoostButton.setOnClickListener(v -> {
            blockUI();
            state.makeContrib(this::ContribCallback);
        });


        state = ((StatefulActivity) Objects.requireNonNull(getActivity())).getState();

        return view;
    }

    // функции должны именоваться со строчной буквы
    private void ContribCallback(final String message) {
        getActivity().runOnUiThread(() -> {
            Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
            unblockUI();
        });
    }


    private void blockUI() {
        smallBoostButton.setClickable(false);
        serverResponseBar.setVisibility(View.VISIBLE);
    }

    private void unblockUI() {
        smallBoostButton.setClickable(true);
        serverResponseBar.setVisibility(View.INVISIBLE);
    }


    /** {@inheritDoc} */
    @Override
    public void initStatic() {
        // No static
    }

    /** {@inheritDoc} */
    @Override
    public void updateDynamic() {
        final ChainData currentChain = state.getCurrentChain();
        chainTitle.setText(currentChain.getView().getTitle());
        myContribution.setText(String.format(Locale.getDefault(), "My contribution to the chain: %d", currentChain.getMyContribution()));
        contributorsAdapter.setContributors(currentChain.getContributors());
    }
}
