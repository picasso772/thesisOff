package longnd.com.vn.thesis.ui.test.test_step_two;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;

import dev147.com.vn.projectpsychological.R;
import longnd.com.vn.thesis.adapter.QuestionPagerAdapter;
import longnd.com.vn.thesis.data.base.ObjectResponse;
import longnd.com.vn.thesis.data.model.Result;
import longnd.com.vn.thesis.data.model.ResultNeo;
import longnd.com.vn.thesis.data.model.ResultPsychological;
import longnd.com.vn.thesis.data.model.ResultRiasec;
import dev147.com.vn.projectpsychological.databinding.FragmentTestStepTwoBinding;
import longnd.com.vn.thesis.ui.base.BaseFragment;
import longnd.com.vn.thesis.ui.evaluate.EvaluateActivity;
import longnd.com.vn.thesis.utils.DataUtils;
import longnd.com.vn.thesis.utils.Define;
import longnd.com.vn.thesis.utils.Fields;
import longnd.com.vn.thesis.utils.PsyLoading;
import longnd.com.vn.thesis.utils.SharedPrefs;

public class TestStepTwoFragment extends BaseFragment<TestStepTwoViewModel, FragmentTestStepTwoBinding> implements QuestionPagerAdapter.SaveResult {
    private QuestionPagerAdapter questionPagerAdapter;
    private ResultNeo resultNeo;
    private ResultRiasec resultRiasec;
    private ResultPsychological resultPsychological;

    @Override
    protected void initListenerOnClick() {
        binding.btnSubmit.setOnClickListener(this);
    }

    @Override
    protected void initView() {
        questionPagerAdapter = new QuestionPagerAdapter(getContext(), testViewModel.getListQuestion(), viewModel, testViewModel.getType());
        viewModel.initResults(testViewModel.getListQuestion().size());
        questionPagerAdapter.setSaveResult(this);
        binding.mViewPager.setOffscreenPageLimit(5);
        binding.mViewPager.setAdapter(questionPagerAdapter);
        binding.mViewPager.setAnimationEnabled(true);
        binding.mViewPager.setFadeEnabled(true);
        binding.mViewPager.setFadeFactor(0.5f);

        binding.indicator.setupWithViewPager(binding.mViewPager);
        binding.tvPage.setText("Page 1" + " of " + testViewModel.getListQuestion().size());

        binding.mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                binding.tvPage.setText("Page " + (position + 1) + " of " + testViewModel.getListQuestion().size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    protected void initData() {
        PsyLoading.getInstance(getContext()).hidden();
    }

    @Override
    public Class<TestStepTwoViewModel> getModelClass() {
        return TestStepTwoViewModel.class;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_test_step_two;
    }

    @Override
    public void onStart() {
        super.onStart();
        viewModel.getIsInsertResultNeo().observe(getViewLifecycleOwner(), this::observeInsertResultNeo);
        viewModel.getIsInsertResultRiasec().observe(getViewLifecycleOwner(), this::observeInsertResultRiasec);
        viewModel.getIsInsertResult().observe(getViewLifecycleOwner(), this::observeInsertResult);
        viewModel.getIsInsertResultPsycho().observe(getViewLifecycleOwner(), this::observeInsertPsycho);
    }

    private void observeInsertPsycho(ObjectResponse<Long> longObjectResponse) {
        if (longObjectResponse == null) {
            return;
        }
        switch (longObjectResponse.getStatus()) {
            case Define.ResponseStatus.LOADING:
                break;
            case Define.ResponseStatus.SUCCESS:
                viewModel.getIsInsertResultNeo().removeObservers(getViewLifecycleOwner());
                viewModel.setIsInsertResultNeo(null);
                viewModel.insertResult(new Result("psychological" + System.currentTimeMillis(), DataUtils.getInstance().getCustomer().getId(), longObjectResponse.getData().intValue(), testViewModel.getType()));
                break;
            case Define.ResponseStatus.ERROR:
                viewModel.getIsInsertResultNeo().removeObservers(getViewLifecycleOwner());
                viewModel.setIsInsertResultNeo(null);
                PsyLoading.getInstance(getContext(), binding.mViewPager).hidden();
                Toast.makeText(getContext(), "Lỗi", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected void initObserve() {

    }

    private void observeInsertResultRiasec(ObjectResponse<Long> longObjectResponse) {
        if (longObjectResponse == null) {
            return;
        }
        switch (longObjectResponse.getStatus()) {
            case Define.ResponseStatus.LOADING:
                break;
            case Define.ResponseStatus.SUCCESS:
                viewModel.getIsInsertResultNeo().removeObservers(getViewLifecycleOwner());
                viewModel.setIsInsertResultNeo(null);
                viewModel.insertResult(new Result("riasec" + System.currentTimeMillis(), DataUtils.getInstance().getCustomer().getId(), longObjectResponse.getData().intValue(), testViewModel.getType()));
                // openEvaluateActivity();
                break;
            case Define.ResponseStatus.ERROR:
                viewModel.getIsInsertResultNeo().removeObservers(getViewLifecycleOwner());
                viewModel.setIsInsertResultNeo(null);
                PsyLoading.getInstance(getContext(), binding.mViewPager).hidden();
                Toast.makeText(getContext(), "Lỗi", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void observeInsertResult(ObjectResponse<Long> longObjectResponse) {
        if (longObjectResponse == null) {
            return;
        }
        switch (longObjectResponse.getStatus()) {
            case Define.ResponseStatus.LOADING:
                break;
            case Define.ResponseStatus.SUCCESS:
                viewModel.getIsInsertResult().removeObservers(getViewLifecycleOwner());
                viewModel.setIsInsertResult(null);
                openEvaluateActivity();
                break;
            case Define.ResponseStatus.ERROR:
                viewModel.getIsInsertResult().removeObservers(getViewLifecycleOwner());
                viewModel.setIsInsertResult(null);
                PsyLoading.getInstance(getContext(), binding.mViewPager).hidden();
                Toast.makeText(getContext(), "Lỗi", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void observeInsertResultNeo(ObjectResponse<Long> longObjectResponse) {
        if (longObjectResponse == null) {
            return;
        }
        switch (longObjectResponse.getStatus()) {
            case Define.ResponseStatus.LOADING:
                break;
            case Define.ResponseStatus.SUCCESS:
                viewModel.getIsInsertResultNeo().removeObservers(getViewLifecycleOwner());
                viewModel.setIsInsertResultNeo(null);
                viewModel.insertResult(new Result("neo" + System.currentTimeMillis(), DataUtils.getInstance().getCustomer().getId(), longObjectResponse.getData().intValue(), testViewModel.getType()));
                break;
            case Define.ResponseStatus.ERROR:
                viewModel.getIsInsertResultNeo().removeObservers(getViewLifecycleOwner());
                viewModel.setIsInsertResultNeo(null);
                PsyLoading.getInstance(getContext(), binding.mViewPager).hidden();
                Toast.makeText(getContext(), "Lỗi", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSubmit:
                // kiểm tra điều kiện, thỏa mãn mới làm tiếp
//                for (int item = 0; item < viewModel.getResuls().length; item++) {
//                    if (viewModel.getResuls()[item] == -1) {
//                        ToastUtils.showToastNotification(getContext(), "Vui lòng chọn 1 trong các tùy chọn sau");
//                        binding.mViewPager.setCurrentItem(item);
//                        return;
//                    }
//                }
                PsyLoading.getInstance(getContext(), binding.mViewPager).show();
                if (testViewModel.getType() == Define.Question.TYPE_NEO) {
                    resultNeo = viewModel.getResulNeo();
                    viewModel.insertResultNeo(resultNeo);
                } else if (testViewModel.getType() == Define.Question.TYPE_RIASEC) {
                    resultRiasec = viewModel.getResultRiasec();
                    viewModel.insertResultRiasec(resultRiasec);
                } else {
                    resultPsychological = viewModel.getResultPsycho();
                    viewModel.insertResultPsycho(resultPsychological);
                }
                break;

        }
    }

    private void openEvaluateActivity() {
        PsyLoading.getInstance(getContext(), binding.mViewPager).hidden();
        Intent intent = new Intent(getActivity(), EvaluateActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtra(Fields.KEY_TYPE, testViewModel.getType());
        if (testViewModel.getType() == Define.Question.TYPE_NEO) {
            bundle.putSerializable(Fields.KEY_VALUE, resultNeo);
        } else if (testViewModel.getType() == Define.Question.TYPE_RIASEC) {
            bundle.putSerializable(Fields.KEY_VALUE, resultRiasec);
        } else {
            bundle.putSerializable(Fields.KEY_VALUE, resultPsychological);
        }
        intent.putExtra("QUA", bundle);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onSaveDataResult(int position, int result) {
        viewModel.setDataResults(position, result);
        if (SharedPrefs.getInstance().getBoolean(Define.SharedPref.KEY_NEXT_TAP, false)) {
            binding.mViewPager.setCurrentItem(position + 1);
        }
    }

    @Override
    public void onSavePsycho(int position, int kind, int numberId, int result) {
        onSaveDataResult(position, result);
        switch (kind) {
            case 1:
                viewModel.resultsLoAu[numberId - 1] = result;
                break;
            case 2:
                viewModel.resultsTramCam[numberId - 1] = result;
                break;
            case 3:
                viewModel.resultsStress[numberId - 1] = result;
                break;
            case 4:
                viewModel.resultsKhoTapTrung[numberId - 1] = result;
                break;
            case 5:
                viewModel.resultsTangDong[numberId - 1] = result;
                break;
            case 6:
                viewModel.resultsKKGiaoTiepXaHoi[numberId - 1] = result;
                break;
            case 7:
                viewModel.resultsKKHocTap[numberId - 1] = result;
                break;
            case 8:
                viewModel.resultsKKDinhHuongNgheNghiep[numberId - 1] = result;
                break;
            case 9:
                viewModel.resultsKKQuanHeChaMe[numberId - 1] = result;
                break;
            case 10:
                viewModel.resultsKKQuanHeThayCo[numberId - 1] = result;
                break;
            case 11:
                viewModel.resultsKKQuanHeBanBe[numberId - 1] = result;
                break;
            case 12:
                viewModel.resultsHanhViChongDoi[numberId - 1] = result;
                break;
            case 13:
                viewModel.resultsRoiLoanHanhVi[numberId - 1] = result;
                break;
            case 14:
                viewModel.resultsGayHan[numberId - 1] = result;
                break;
        }
    }
}
