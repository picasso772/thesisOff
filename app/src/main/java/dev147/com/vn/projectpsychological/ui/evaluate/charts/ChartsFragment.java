package dev147.com.vn.projectpsychological.ui.evaluate.charts;

import android.view.View;

import dev147.com.vn.projectpsychological.R;
import dev147.com.vn.projectpsychological.data.model.Customer;
import dev147.com.vn.projectpsychological.databinding.FragmentChartsBinding;
import dev147.com.vn.projectpsychological.ui.base.BaseFragment;
import dev147.com.vn.projectpsychological.ui.custom.GraphNeoView;
import dev147.com.vn.projectpsychological.ui.custom.GraphRiasecView;
import dev147.com.vn.projectpsychological.ui.evaluate.EvaluateActivity;
import dev147.com.vn.projectpsychological.utils.DataUtils;
import dev147.com.vn.projectpsychological.utils.Define;
import dev147.com.vn.projectpsychological.utils.EvaluateUtils;
import dev147.com.vn.projectpsychological.utils.Fields;
import dev147.com.vn.projectpsychological.utils.Utils;

public class ChartsFragment extends BaseFragment<ChartsViewModel, FragmentChartsBinding> {

    private GraphNeoView neoView;
    private GraphRiasecView riasecView;
    private Customer customer;

    @Override
    protected void initListenerOnClick() {
        binding.btnBack.setOnClickListener(this);
        binding.btnViewAll.setOnClickListener(this);
    }

    @Override
    protected void initObserve() {

    }

    @Override
    protected void initView() {
        binding.mContainer.removeAllViews();
        binding.tvTitle.setTypeface(Utils.getTypeFace(getContext(), Fields.FONT_TIMES));
        neoView = new GraphNeoView(getContext());
        riasecView = new GraphRiasecView(getContext());
    }

    @Override
    protected void initData() {
        customer = DataUtils.getInstance().getCustomer();
        if (customer == null) {
            return;
        }
        switch (evaluateViewModel.getType()) {
            case Define.Question.TYPE_NEO:
                binding.layoutRiasec.setVisibility(View.GONE);
                showChartNeo();
                break;

            case Define.Question.TYPE_RIASEC:
                binding.layoutNeo.setVisibility(View.GONE);
                showChartRiasec();
                break;
        }

    }

    private void showChartRiasec() {
        // riasecView.setData();
        riasecView.setData(evaluateViewModel.getMin(), evaluateViewModel.getSpaceRiasec(), evaluateViewModel.getResults());
        binding.mContainer.addView(riasecView);

        binding.tvResultRiasec.setText(getContext().getResources().getString(R.string.evaluate_riasec,
                DataUtils.getInstance().getCustomer().getLastName().toUpperCase(),
                EvaluateUtils.resultRiasecTitle(getContext(), evaluateViewModel.getMaxInPosition()).toUpperCase()));
    }

    private void showChartNeo() {
        neoView.setData(evaluateViewModel.getMin(), evaluateViewModel.getSpaceNeo(), evaluateViewModel.getResults());
        binding.mContainer.addView(neoView);
        int[] results = evaluateViewModel.getResults();
        binding.tvResult01.setText(String.valueOf(results[0]));
        binding.tvResult02.setText(String.valueOf(results[1]));
        binding.tvResult03.setText(String.valueOf(results[2]));
        binding.tvResult04.setText(String.valueOf(results[3]));
        binding.tvResult05.setText(String.valueOf(results[4]));

        int[] nResults = viewModel.getResultsnNeo(customer.getGender(), results);
        binding.tvResult11.setText(EvaluateUtils.wordResults(nResults[0]));
        binding.tvResult22.setText(EvaluateUtils.wordResults(nResults[1]));
        binding.tvResult33.setText(EvaluateUtils.wordResults(nResults[2]));
        binding.tvResult44.setText(EvaluateUtils.wordResults(nResults[3]));
        binding.tvResult55.setText(EvaluateUtils.wordResults(nResults[4]));
    }

    @Override
    public Class<ChartsViewModel> getModelClass() {
        return ChartsViewModel.class;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_charts;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                getActivity().onBackPressed();
                break;
            case R.id.btnViewAll:
                ((EvaluateActivity) getActivity()).viewDetail();
                break;
        }
    }
}
