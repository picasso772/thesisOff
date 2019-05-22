package dev147.com.vn.projectpsychological.ui.evaluate;

import android.content.Intent;
import android.view.View;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import dev147.com.vn.projectpsychological.R;
import dev147.com.vn.projectpsychological.adapter.EvaluatePagerAdapter;
import dev147.com.vn.projectpsychological.data.model.ResultNeo;
import dev147.com.vn.projectpsychological.data.model.ResultRiasec;
import dev147.com.vn.projectpsychological.databinding.ActivityEvaluateBinding;
import dev147.com.vn.projectpsychological.ui.base.BaseActivity;
import dev147.com.vn.projectpsychological.utils.Define;
import dev147.com.vn.projectpsychological.utils.Fields;
import dev147.com.vn.projectpsychological.utils.PsyLoading;

public class EvaluateActivity extends BaseActivity<EvaluateViewModel, ActivityEvaluateBinding> {
    private Intent intent;
    private int[] results;
    private ResultNeo resultNeo;
    private ResultRiasec resultRiasec;

    @Override
    protected void initView() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(EvaluateViewModel.class);
        if (getIntent() == null) {
            return;
        }
        intent = getIntent();
        int type = intent.getIntExtra(Fields.KEY_TYPE, -1);
        if (type == -1) {
            return;
        }
        if (intent.getBundleExtra("QUA").getSerializable(Fields.KEY_VALUE) == null) {
            return;
        }

        viewModel.setType(type);
        graphChartsType(type);

        viewModel.setResults(results);
//        GraphNeoView neoView = new GraphNeoView(this);
//        neoView.setData(viewModel.getMin(), viewModel.getSpaceNeo(), results);
//        binding.mContainer.addView(neoView);

//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        getWindow().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

//        binding.tvTitle.setTypeface(Utils.getTypeFace(this, Fields.FONT_TIMES));
        FragmentManager fm = getSupportFragmentManager();
        EvaluatePagerAdapter pagerAdapter = new EvaluatePagerAdapter(fm);
        binding.viewPager.setAdapter(pagerAdapter);
    }

    private void graphChartsType(int type) {
        switch (type) {
            case Define.Question.TYPE_NEO:
                results = new int[5];
                resultNeo = (ResultNeo) intent.getBundleExtra("QUA").getSerializable(Fields.KEY_VALUE);
                // type : 0 - A, 1 - C, 2 - O, 3 - N, 4 - E
                results[0] = resultNeo.getAgreeableness();
                results[1] = resultNeo.getConscientiousness();
                results[2] = resultNeo.getOpenness();
                results[3] = resultNeo.getNeuroticism();
                results[4] = resultNeo.getExtraversion();
                break;
            case Define.Question.TYPE_RIASEC:
                results = new int[6];
                resultRiasec = (ResultRiasec) intent.getBundleExtra("QUA").getSerializable(Fields.KEY_VALUE);
                // type: 0 - rule, 1- society, 2 - discover, 3 - reality, 4 - art, 5 - convince
                results[0] = resultRiasec.getRule();
                results[1] = resultRiasec.getSociety();
                results[2] = resultRiasec.getDiscover();
                results[3] = resultRiasec.getReality();
                results[4] = resultRiasec.getArt();
                results[5] = resultRiasec.getConvince();
                break;
            case Define.Question.TYPE_PSY_POCHOLIGICAL:
                break;
        }
    }

    @Override
    protected void initListenerOnClick() {
//        binding.btnBack.setOnClickListener(this);
//        binding.btnViewAll.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        PsyLoading.getInstance(this).hidden();
        PsyLoading.getInstance(this).destroyLoadingDialog();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_evaluate;
    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.btnBack:
//                onBackPressed();
//                break;
//            case R.id.btnViewAll:
//
//                break;
//            default:
//                break;
//        }
    }

    public void viewDetail() {
        binding.viewPager.setCurrentItem(1);
    }
}
