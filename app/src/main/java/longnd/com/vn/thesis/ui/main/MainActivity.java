package longnd.com.vn.thesis.ui.main;

import android.view.View;

import androidx.lifecycle.ViewModelProviders;
import dev147.com.vn.projectpsychological.R;
import dev147.com.vn.projectpsychological.databinding.ActivityMainBinding;
import longnd.com.vn.thesis.di.OnOpenCustomer;
import longnd.com.vn.thesis.ui.base.BaseActivity;
import longnd.com.vn.thesis.ui.customer.CustomerFragment;
import longnd.com.vn.thesis.ui.customer.signin.SignInFragment;
import longnd.com.vn.thesis.ui.customer.signup.SignUpFragment;
import longnd.com.vn.thesis.ui.history.HistoryFragment;
import longnd.com.vn.thesis.ui.home.HomeFragment;
import longnd.com.vn.thesis.utils.DataUtils;
import longnd.com.vn.thesis.utils.Fields;

public class MainActivity extends BaseActivity<MainViewModel, ActivityMainBinding> implements OnOpenCustomer {
    private HomeFragment homeFragment;
    private SignInFragment signInFragment;
    private SignUpFragment signUpFragment;
    private CustomerFragment customerFragment;
    private HistoryFragment historyFragment;

    @Override
    protected void initView() {
        homeFragment = new HomeFragment();
        customerFragment = new CustomerFragment();
        historyFragment = new HistoryFragment();
        signInFragment = new SignInFragment();
        signInFragment.setOnOpenCustomer(this);
        signUpFragment = new SignUpFragment();
        signUpFragment.setOnOpenCustomer(this);
        customerFragment.setOnOpenCustomer(this);

    }

    @Override
    protected void initData() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel.class);
        viewModel.setNumberBack(Fields.ON_BACK);
        addFragment(R.id.mContainer, new HomeFragment(), HomeFragment.class.getName());
    }

    @Override
    protected void initListenerOnClick() {
        binding.ivHome.setOnClickListener(this);
        binding.ivUser.setOnClickListener(this);
        binding.tvHome.setOnClickListener(this);
        binding.tvUser.setOnClickListener(this);
        binding.tvHistory.setOnClickListener(this);
        binding.ivHistory.setOnClickListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivHome:
            case R.id.tvHome:
                openHomeFragment();
                break;
            case R.id.ivUser:
            case R.id.tvUser:
                openCustomerFragment();
                break;
            case R.id.tvHistory:
            case R.id.ivHistory:
                openHistoryFragment();
                break;
            default:
                break;
        }
    }

    private void openHistoryFragment() {
        addFragment(R.id.mContainer, historyFragment, HistoryFragment.class.getName());
        binding.ivHistory.setImageResource(R.drawable.ic_history_selected);
        binding.tvHistory.setTextColor(getResources().getColor(R.color.main_text_select));
        binding.ivUser.setImageResource(R.drawable.icon_user_main);
        binding.tvUser.setTextColor(getResources().getColor(R.color.main_text));
        binding.ivHome.setImageResource(R.drawable.icon_home);
        binding.tvHome.setTextColor(getResources().getColor(R.color.main_text));
    }


    private void openHomeFragment() {
        addFragment(R.id.mContainer, homeFragment, HomeFragment.class.getName());
        binding.ivHome.setImageResource(R.drawable.icon_home_select);
        binding.tvHome.setTextColor(getResources().getColor(R.color.main_text_select));
        binding.ivUser.setImageResource(R.drawable.icon_user_main);
        binding.tvUser.setTextColor(getResources().getColor(R.color.main_text));
        binding.ivHistory.setImageResource(R.drawable.ic_history);
        binding.tvHistory.setTextColor(getResources().getColor(R.color.main_text));
    }

    private void openCustomerFragment() {
        if (DataUtils.getInstance().getCustomer() != null) {
            addFragment(R.id.mContainer, customerFragment, CustomerFragment.class.getName());
        } else {
            addFragment(R.id.mContainer, signInFragment, SignInFragment.class.getName());
        }
        binding.ivUser.setImageResource(R.drawable.icon_user_main_select);
        binding.tvUser.setTextColor(getResources().getColor(R.color.main_text_select));
        binding.ivHome.setImageResource(R.drawable.icon_home);
        binding.tvHome.setTextColor(getResources().getColor(R.color.main_text));
        binding.ivHistory.setImageResource(R.drawable.ic_history);
        binding.tvHistory.setTextColor(getResources().getColor(R.color.main_text));
    }

    @Override
    public void onBackPressed() {
        if (viewModel.getNumberBack() == Fields.ON_BACK) {
            super.onBackPressed();
        } else {
            openHomeFragment();
        }

    }

    @Override
    public void openSignInCustomer() {
        addFragment(R.id.mContainer, signInFragment, SignInFragment.class.getName());
    }

    @Override
    public void openSignUpCustomer() {
        addFragment(R.id.mContainer, signUpFragment, SignUpFragment.class.getName());
    }

    @Override
    public void openCustomer() {
        addFragment(R.id.mContainer, customerFragment, CustomerFragment.class.getName());
    }
}
