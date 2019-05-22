package dev147.com.vn.projectpsychological.ui.customer.signup;

import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import dev147.com.vn.projectpsychological.R;
import dev147.com.vn.projectpsychological.data.base.ObjectResponse;
import dev147.com.vn.projectpsychological.data.model.Customer;
import dev147.com.vn.projectpsychological.databinding.FragmentSignUpBinding;
import dev147.com.vn.projectpsychological.di.OnOpenCustomer;
import dev147.com.vn.projectpsychological.ui.base.BaseFragment;
import dev147.com.vn.projectpsychological.utils.DataUtils;
import dev147.com.vn.projectpsychological.utils.Define;
import dev147.com.vn.projectpsychological.utils.Fields;
import dev147.com.vn.projectpsychological.utils.PsyLoading;
import dev147.com.vn.projectpsychological.utils.PsyNotification;
import dev147.com.vn.projectpsychological.utils.ToastUtils;
import dev147.com.vn.projectpsychological.utils.Utils;

public class SignUpFragment extends BaseFragment<SignUpViewModel, FragmentSignUpBinding> {
    private OnOpenCustomer onOpenCustomer;
    private String email;
    private String pass;

    @Override
    protected void initListenerOnClick() {
        binding.tvSignIn.setOnClickListener(this);
        binding.btnSignUp.setOnClickListener(this);
    }

    @Override
    protected void initObserve() {
        viewModel.getIsInsertCustomer().observe(getViewLifecycleOwner(), this::observeInsertCustomer);
        viewModel.getObserveCustomerByEmail().observe(getViewLifecycleOwner(), this::observeGetCustomer);
    }

    private void observeInsertCustomer(ObjectResponse<Long> longObjectResponse) {
        if (longObjectResponse == null) {
            return;
        }
        switch (longObjectResponse.getStatus()) {
            case Define.ResponseStatus.LOADING:
                // TODO: loading
                break;
            case Define.ResponseStatus.SUCCESS:
                viewModel.setIsInsertCustomer(null);
                viewModel.getIsInsertCustomer().removeObservers(this);
                Utils.onSaveCustomerInSharedPrefs(email, pass);
                viewModel.signInCustomer(email, pass);
                break;
            case Define.ResponseStatus.ERROR:
                PsyLoading.getInstance(getContext()).hidden();
                Toast.makeText(getContext(), "Rất tiếc đăng ký đã xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                break;
            default:
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
                viewModel.getObserveCustomerByEmail().removeObservers(this);
                DataUtils.getInstance().setCustomer(customerObjectResponse.getData());
                PsyLoading.getInstance(getContext()).hidden();
                onOpenCustomer.openCustomer();
                PsyNotification.getInstance(getContext(), "Chúc mừng bạn đã đăng ký thành công!").show();
                break;
            case Define.ResponseStatus.ERROR:
                viewModel.setCustomerByEmail(null);
                viewModel.getObserveCustomerByEmail().removeObservers(this);
                PsyLoading.getInstance(getContext()).hidden();
                ToastUtils.showToastError(getContext());
                break;
        }
    }

    @Override
    protected void initView() {
        binding.tvTitle1.setTypeface(Utils.getTypeFace(getContext(), Fields.FONT_NABILA));
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.editFullName.setText("");
        binding.editEmail.setText("");
        binding.editPass.setText("");
        binding.editConfirmPass.setText("");
    }

    @Override
    protected void initData() {

    }

    @Override
    public Class<SignUpViewModel> getModelClass() {
        return SignUpViewModel.class;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_sign_up;
    }

    @Override
    public void onClick(View v) {
        Utils.hideKeyboard(getActivity());
        switch (v.getId()) {
            case R.id.tvSignIn:
                onOpenCustomer.openSignInCustomer();
                break;
            case R.id.btnSignUp:
                String fullname = binding.editFullName.getText().toString().trim();
                String email = binding.editEmail.getText().toString().trim();
                String pass = binding.editPass.getText().toString().trim();
                String confirmPass = binding.editConfirmPass.getText().toString().trim();

                if (fullname.isEmpty()) {
                    showInputError(binding.editFullName, "Thiếu trường email");
                    return;
                }

                if (email.isEmpty()) {
                    showInputError(binding.editEmail, "Thiếu trường email");
                    return;
                }

                if (!viewModel.isValidDEmail(email)) {
                    showInputError(binding.editEmail, "Email chưa chính xác");
                    return;
                }

                if (pass.isEmpty()) {
                    showInputError(binding.editPass, "Thiếu trường Password");
                    return;
                }

                if (confirmPass.isEmpty()) {
                    showInputError(binding.editConfirmPass, "Nhập lại mật khẩu");
                    return;
                }

                if (!confirmPass.equals(pass)) {
                    showInputError(binding.editConfirmPass, "Không trùng khớp");
                    return;
                }
                PsyLoading.getInstance(getContext()).show();
                byte[] encodePass = Base64.encode(pass.getBytes(), Base64.DEFAULT);
                Customer customer = new Customer(fullname, email, new String(encodePass));
                this.email = email;
                this.pass = new String(encodePass);
                viewModel.insertCustomer(customer);
                break;
        }
    }

    public void setOnOpenCustomer(OnOpenCustomer onOpenCustomer) {
        this.onOpenCustomer = onOpenCustomer;
    }
}
