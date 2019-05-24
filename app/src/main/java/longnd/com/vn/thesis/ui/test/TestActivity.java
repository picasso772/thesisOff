package longnd.com.vn.thesis.ui.test;

import android.view.View;

import androidx.lifecycle.ViewModelProviders;
import dev147.com.vn.projectpsychological.R;
import dev147.com.vn.projectpsychological.databinding.ActivityTestBinding;
import longnd.com.vn.thesis.directional.OpenStepTest;
import longnd.com.vn.thesis.directional.SettingTest;
import longnd.com.vn.thesis.ui.base.BaseActivity;
import longnd.com.vn.thesis.ui.dialog.DialogSetting;
import longnd.com.vn.thesis.ui.test.test_step_one.TestStepOneFragment;
import longnd.com.vn.thesis.ui.test.test_step_two.TestStepTwoFragment;
import longnd.com.vn.thesis.utils.Define;
import longnd.com.vn.thesis.utils.Fields;
import longnd.com.vn.thesis.utils.PsyLoading;
import longnd.com.vn.thesis.utils.SharedPrefs;
import longnd.com.vn.thesis.utils.Utils;

public class TestActivity extends BaseActivity<TestViewModel, ActivityTestBinding> implements OpenStepTest, SettingTest {
    private TestStepOneFragment testStepOne;
    private TestStepTwoFragment testStepTwo;

    @Override
    protected void initView() {
        testStepOne = new TestStepOneFragment();
        testStepOne.setOpenStepTest(this);
        testStepTwo = new TestStepTwoFragment();
    }

    @Override
    protected void initListenerOnClick() {
        binding.btnBack.setOnClickListener(this);
        binding.btnSettings.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        int type = getIntent().getIntExtra(Define.TYPE_QUESTION, Define.DEFAULT_VALUE);
        if (type == Define.DEFAULT_VALUE) {
            return;
        }
        binding.tvTitle.setText(Utils.getTitleQuestion(type));

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(TestViewModel.class);
        viewModel.setType(type);
        viewModel.setDetail(Utils.getDetailQuestion(this, type));
        viewModel.setNumberBack(Fields.ON_BACK);
        addFragmentTestScren(R.id.mContainer, testStepOne, TestStepOneFragment.class.getName());
        PsyLoading.getInstance(this).hidden();
        PsyLoading.getInstance(this).destroyLoadingDialog();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initObserve();
    }

    private void initObserve() {
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                onBackPressed();
                break;
            case R.id.btnSettings:
                DialogSetting setting = new DialogSetting();
                setting.setSettingTest(this);
                setting.show(getSupportFragmentManager(), setting.getClass().getName());
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        PsyLoading.getInstance(this).destroyLoadingDialog();
        // overridePendingTransition(R.anim.no_anim, R.anim.fade_right);
    }

    @Override
    public void openStepTwo() {
        viewModel.setListQuestion();
        addFragmentTestScren(R.id.mContainer, testStepTwo, TestStepTwoFragment.class.getName());
    }

    @Override
    public void setNextTap(boolean isChecked) {
        SharedPrefs.getInstance().putBoolean(Define.SharedPref.KEY_NEXT_TAP, isChecked);
    }
}
