package longnd.com.vn.thesis.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import longnd.com.vn.thesis.di.ViewModelFactory;
import longnd.com.vn.thesis.ui.customer.CustomerViewModel;
import longnd.com.vn.thesis.ui.customer.signin.SignInViewModel;
import longnd.com.vn.thesis.ui.customer.signup.SignUpViewModel;
import longnd.com.vn.thesis.ui.dialog.EditCustomerViewModel;
import longnd.com.vn.thesis.ui.evaluate.EvaluateViewModel;
import longnd.com.vn.thesis.ui.evaluate.charts.ChartsViewModel;
import longnd.com.vn.thesis.ui.evaluate.detail.DetailViewModel;
import longnd.com.vn.thesis.ui.history.HistoryViewModel;
import longnd.com.vn.thesis.ui.home.HomeViewModel;
import longnd.com.vn.thesis.ui.load_data.LoadDataViewModel;
import longnd.com.vn.thesis.ui.main.MainViewModel;
import longnd.com.vn.thesis.ui.question.QuestionViewModel;
import longnd.com.vn.thesis.ui.splash.SplashViewModel;
import longnd.com.vn.thesis.ui.test.TestViewModel;
import longnd.com.vn.thesis.ui.test.test_step_one.TestStepOneViewModel;
import longnd.com.vn.thesis.ui.test.test_step_two.TestStepTwoViewModel;
import longnd.com.vn.thesis.ui.tutorial.TutorialViewModel;

@Module
public abstract class ViewModelModule {
    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel.class)
    abstract ViewModel bindSplashViewModel(SplashViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    abstract ViewModel bindMainViewModel(MainViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(TutorialViewModel.class)
    abstract ViewModel bindTutorialViewModel(TutorialViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel.class)
    abstract ViewModel bindHomeViewModel(HomeViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(CustomerViewModel.class)
    abstract ViewModel bindCustomerViewModel(CustomerViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SignInViewModel.class)
    abstract ViewModel bindSignInCustomerViewModel(SignInViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SignUpViewModel.class)
    abstract ViewModel bindSignUpCustomerViewModel(SignUpViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(EditCustomerViewModel.class)
    abstract ViewModel bindEditCustomerViewModel(EditCustomerViewModel viewModel);


    @Binds
    @IntoMap
    @ViewModelKey(QuestionViewModel.class)
    abstract ViewModel bindQuestionViewModel(QuestionViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(LoadDataViewModel.class)
    abstract ViewModel bindLoadDataViewModel(LoadDataViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(TestViewModel.class)
    abstract ViewModel bindTestViewModel(TestViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(TestStepOneViewModel.class)
    abstract ViewModel bindTestStepOneViewModel(TestStepOneViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(TestStepTwoViewModel.class)
    abstract ViewModel bindTestStepTwoViewModel(TestStepTwoViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(EvaluateViewModel.class)
    abstract ViewModel bindEvaluateViewModel(EvaluateViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ChartsViewModel.class)
    abstract ViewModel bindChartsViewModel(ChartsViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel.class)
    abstract ViewModel bindDetailViewModel(DetailViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(HistoryViewModel.class)
    abstract ViewModel bindHistoryViewModel(HistoryViewModel viewModel);
}
