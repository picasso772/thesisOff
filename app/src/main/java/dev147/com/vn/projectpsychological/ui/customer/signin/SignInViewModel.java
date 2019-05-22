package dev147.com.vn.projectpsychological.ui.customer.signin;

import android.util.Patterns;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import dev147.com.vn.projectpsychological.data.base.ObjectResponse;
import dev147.com.vn.projectpsychological.data.model.Customer;
import dev147.com.vn.projectpsychological.data.repository.CustomerRepository;
import io.reactivex.disposables.CompositeDisposable;

public class SignInViewModel extends ViewModel {
    private CompositeDisposable compositeDisposable;
    private CustomerRepository repository;

    private MutableLiveData<ObjectResponse<Customer>> customerByEmail;

    @Inject
    SignInViewModel(CustomerRepository repository) {
        compositeDisposable = new CompositeDisposable();
        this.repository = repository;

        customerByEmail = new MutableLiveData<>();
    }

    /**
     * used to check email is valid
     *
     * @param email
     * @return
     */
    public boolean isValidDEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void signInCustomer(String email, String pass) {
        compositeDisposable.add(
                repository.signInCustomerByEmail(email, pass)
                        .doOnSubscribe(dispose -> {
                            customerByEmail.setValue(new ObjectResponse<Customer>().loading());
                        })
                        .subscribe(response -> customerByEmail.setValue(new ObjectResponse<Customer>().success(response))
                                , throwable -> customerByEmail.setValue(new ObjectResponse<Customer>().error(throwable)))
        );
    }

    public MutableLiveData<ObjectResponse<Customer>> getObserveCustomerByEmail() {
        return customerByEmail;
    }

    public void setCustomerByEmail(ObjectResponse<Customer> value) {
        customerByEmail.setValue(value);
    }
}
