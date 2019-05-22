package dev147.com.vn.projectpsychological.ui.customer.signin;

import android.view.View;

import dev147.com.vn.projectpsychological.R;
import dev147.com.vn.projectpsychological.data.base.ObjectResponse;
import dev147.com.vn.projectpsychological.data.model.Customer;
import dev147.com.vn.projectpsychological.databinding.FragmentSignInBinding;
import dev147.com.vn.projectpsychological.di.OnOpenCustomer;
import dev147.com.vn.projectpsychological.ui.base.BaseFragment;
import dev147.com.vn.projectpsychological.utils.DataUtils;
import dev147.com.vn.projectpsychological.utils.Define;
import dev147.com.vn.projectpsychological.utils.Fields;
import dev147.com.vn.projectpsychological.utils.PsyLoading;
import dev147.com.vn.projectpsychological.utils.ToastUtils;
import dev147.com.vn.projectpsychological.utils.Utils;

public class SignInFragment extends BaseFragment<SignInViewModel, FragmentSignInBinding> {
    private Customer customer;
    private OnOpenCustomer onOpenCustomer;

    @Override
    protected void initListenerOnClick() {
        binding.tvSignUp.setOnClickListener(this);
        binding.btnSignIn.setOnClickListener(this);
    }

    @Override
    protected void initObserve() {
        viewModel.getObserveCustomerByEmail().observe(getViewLifecycleOwner(), this::observeGetCustomer);
    }

    @Override
    protected void initView() {
        binding.editEmail.setError(null);
        binding.editPass.setError(null);
        binding.tvTitle1.setTypeface(Utils.getTypeFace(getContext(), Fields.FONT_NABILA));
    }

    @Override
    protected void initData() {
        mainViewModel.setNumberBack(Fields.DONT_BACK);
    }

    @Override
    public Class<SignInViewModel> getModelClass() {
        return SignInViewModel.class;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_sign_in;
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.editPass.setText("");
        binding.editEmail.setText("");
    }

    @Override
    public void onClick(View v) {
        Utils.hideKeyboard(getActivity());
        switch (v.getId()) {
            case R.id.tvSignUp:
                onOpenCustomer.openSignUpCustomer();
                break;
            case R.id.btnSignIn:
                Utils.hideKeyboard(getActivity());
                String email = binding.editEmail.getText().toString();
                String pass = binding.editPass.getText().toString();
                if (email.isEmpty()) {
                    showInputError(binding.editEmail, "Thiếu trường email");
                    return;
                }
                if (pass.isEmpty()) {
                    showInputError(binding.editPass, "Thiếu trường password");
                    return;
                }
                if (!viewModel.isValidDEmail(email)) {
                    showInputError(binding.editEmail, "Email chưa chính xác");
                    return;
                }
                PsyLoading.getInstance(getContext()).show();
                viewModel.signInCustomer(email, Utils.encodeSrting(pass));
                break;
        }
    }

    private void observeGetCustomer(ObjectResponse<Customer> customerObjectResponse) {
        if (customerObjectResponse == null) {
            return;
        }
        switch (customerObjectResponse.getStatus()) {
            case Define.ResponseStatus.LOADING:
                // TODO: loading
                break;
            case Define.ResponseStatus.SUCCESS:
                viewModel.setCustomerByEmail(null);
                Customer customer = customerObjectResponse.getData();
                PsyLoading.getInstance(getContext()).hidden();
                Utils.onSaveCustomerInSharedPrefs(customer.getEmail(), customer.getPass());
                DataUtils.getInstance().setCustomer(customerObjectResponse.getData());
                onOpenCustomer.openCustomer();
                break;
            case Define.ResponseStatus.ERROR:
                viewModel.setCustomerByEmail(null);
                PsyLoading.getInstance(getContext()).hidden();
                ToastUtils.showToastError(getContext());
                break;
        }
    }


    public void setOnOpenCustomer(OnOpenCustomer onOpenCustomer) {
        this.onOpenCustomer = onOpenCustomer;
    }

}
