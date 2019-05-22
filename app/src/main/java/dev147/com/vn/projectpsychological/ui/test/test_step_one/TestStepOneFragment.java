package dev147.com.vn.projectpsychological.ui.test.test_step_one;

import android.view.View;

import dev147.com.vn.projectpsychological.R;
import dev147.com.vn.projectpsychological.databinding.FragmentTestStepOneBinding;
import dev147.com.vn.projectpsychological.directional.OpenStepTest;
import dev147.com.vn.projectpsychological.ui.base.BaseFragment;
import dev147.com.vn.projectpsychological.utils.DataUtils;
import dev147.com.vn.projectpsychological.utils.Define;
import dev147.com.vn.projectpsychological.utils.Fields;
import dev147.com.vn.projectpsychological.utils.PsyLoading;
import dev147.com.vn.projectpsychological.utils.ToastUtils;
import dev147.com.vn.projectpsychological.utils.Utils;

public class TestStepOneFragment extends BaseFragment<TestStepOneViewModel, FragmentTestStepOneBinding> {
    private OpenStepTest openStepTest;

    @Override
    protected void initListenerOnClick() {
        binding.btnStart.setOnClickListener(this);
    }

    @Override
    protected void initObserve() {

    }

    @Override
    protected void initView() {
        binding.tvDetail.setTypeface(Utils.getTypeFace(getContext(), Fields.FONT_TIMES));
    }

    @Override
    protected void initData() {
        binding.tvDetail.setText(testViewModel.getDetail());
    }

    @Override
    public Class<TestStepOneViewModel> getModelClass() {
        return TestStepOneViewModel.class;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_test_step_one;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStart:
                if (testViewModel.getType() == Define.Question.TYPE_NEO && DataUtils.getInstance().getCustomer().getGender() == -1) {
                    ToastUtils.showToastNotification(getContext(), "Bạn cần cập nhật giới tính trước!");
                    return;
                }
                PsyLoading.getInstance(getContext()).show();
                openStepTest.openStepTwo();
                break;
        }
    }

    public void setOpenStepTest(OpenStepTest openStepTest) {
        this.openStepTest = openStepTest;
    }
}
