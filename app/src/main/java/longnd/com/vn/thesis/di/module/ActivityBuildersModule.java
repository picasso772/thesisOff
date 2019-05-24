package longnd.com.vn.thesis.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import longnd.com.vn.thesis.ui.customer.CustomerFragment;
import longnd.com.vn.thesis.ui.customer.signin.SignInFragment;
import longnd.com.vn.thesis.ui.customer.signup.SignUpFragment;
import longnd.com.vn.thesis.ui.dialog.DialogEditCustomer;
import longnd.com.vn.thesis.ui.evaluate.EvaluateActivity;
import longnd.com.vn.thesis.ui.evaluate.charts.ChartsFragment;
import longnd.com.vn.thesis.ui.evaluate.detail.DetailFragment;
import longnd.com.vn.thesis.ui.history.HistoryFragment;
import longnd.com.vn.thesis.ui.home.HomeFragment;
import longnd.com.vn.thesis.ui.load_data.LoadDataFragment;
import longnd.com.vn.thesis.ui.main.MainActivity;
import longnd.com.vn.thesis.ui.question.QuestionFragment;
import longnd.com.vn.thesis.ui.splash.SplashActivity;
import longnd.com.vn.thesis.ui.test.TestActivity;
import longnd.com.vn.thesis.ui.test.test_step_one.TestStepOneFragment;
import longnd.com.vn.thesis.ui.test.test_step_two.TestStepTwoFragment;
import longnd.com.vn.thesis.ui.tutorial.TutorialActivity;

@Module
public abstract class ActivityBuildersModule {
    @ContributesAndroidInjector
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector
    abstract SplashActivity bindSplashActivity();

    @ContributesAndroidInjector
    abstract TutorialActivity bindTutorialActivity();

    @ContributesAndroidInjector
    abstract HomeFragment bindHomeFragment();

    @ContributesAndroidInjector
    abstract CustomerFragment bindCustomerFragment();

    @ContributesAndroidInjector
    abstract DialogEditCustomer bindEditCustomerDialog();

    @ContributesAndroidInjector
    abstract SignInFragment bindSignInCustomerFragment();

    @ContributesAndroidInjector
    abstract SignUpFragment bindSignUpCustomerFragment();

    @ContributesAndroidInjector
    abstract QuestionFragment bindQuestionFragment();

    @ContributesAndroidInjector
    abstract LoadDataFragment bindLoadDataFragment();

    @ContributesAndroidInjector
    abstract TestActivity bindTestActivity();

    @ContributesAndroidInjector
    abstract TestStepOneFragment bindTestStepOneFragment();

    @ContributesAndroidInjector
    abstract TestStepTwoFragment bindTestStepTwoFragment();

    @ContributesAndroidInjector
    abstract EvaluateActivity bindEvaluateActivity();

    @ContributesAndroidInjector
    abstract ChartsFragment bindChartsFragment();

    @ContributesAndroidInjector
    abstract DetailFragment bindDetailFragment();

    @ContributesAndroidInjector
    abstract HistoryFragment bindHistoryFragment();
}
