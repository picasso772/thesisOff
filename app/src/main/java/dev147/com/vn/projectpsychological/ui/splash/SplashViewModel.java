package dev147.com.vn.projectpsychological.ui.splash;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import dev147.com.vn.projectpsychological.data.base.ObjectResponse;
import dev147.com.vn.projectpsychological.data.model.Customer;
import dev147.com.vn.projectpsychological.data.model.Question;
import dev147.com.vn.projectpsychological.data.repository.CustomerRepository;
import dev147.com.vn.projectpsychological.data.repository.QuestionRepository;
import dev147.com.vn.projectpsychological.utils.Define;
import io.reactivex.disposables.CompositeDisposable;

public class SplashViewModel extends ViewModel {
    private static final String TAG = "SplashViewModel";
    private CompositeDisposable compositeDisposable;
    private CustomerRepository repository;
    private QuestionRepository questionRepository;

    private MutableLiveData<ObjectResponse<Customer>> customer;
    private MutableLiveData<ObjectResponse<Long>> isDataCustomer;
    private MutableLiveData<ObjectResponse<Boolean>> isInsertListQuestionSuccess;

    @Inject
    SplashViewModel(CustomerRepository customerRepository, QuestionRepository questionRepository) {
        compositeDisposable = new CompositeDisposable();
        this.repository = customerRepository;
        this.questionRepository = questionRepository;
        customer = new MutableLiveData<>();
        isDataCustomer = new MutableLiveData<>();
        isInsertListQuestionSuccess = new MutableLiveData<>();
    }

    public void getCount() {
        compositeDisposable.add(repository.getCount()
                .doOnSubscribe(dispose -> isDataCustomer.setValue(new ObjectResponse<Long>().loading()))
                .subscribe(response ->
                                isDataCustomer.setValue(new ObjectResponse<Long>().success(response))
                        , throwable -> {
                            throwable.printStackTrace();
                            isDataCustomer.setValue(new ObjectResponse<Long>().error(throwable));
                        })
        );
    }

    public List<Question> readDataNeo(Context context, int type) {
        String data;
        List<Question> questions = new ArrayList<>();
        int[] reverse = new int[]{2, 3, 4, 7, 8, 9, 12, 14, 17, 22, 23, 27, 32, 37, 38, 42, 43, 47, 48, 49, 50, 52, 53, 54, 57};
        try {
            InputStream in = context.getAssets().open("data/" + type + ".txt");
            InputStreamReader inreader = new InputStreamReader(in);
            BufferedReader bufreader = new BufferedReader(inreader);
            if (in != null) {
                while ((data = bufreader.readLine()) != null) {
                    String sNumber = data.substring(0, data.indexOf("@")).trim().replace(" ", "");
                    int number = Integer.parseInt(sNumber);
                    int isReverse = 0;
                    for (int i = 0; i < reverse.length; i++) {
                        if (number == reverse[i]) {
                            isReverse = 1;
                        }
                    }
                    Question question = new Question(type, number, data.substring(data.indexOf("@") + 2, data.length()).trim(), isReverse, 0);
                    questions.add(question);
                }
                in.close();
            }
        } catch (IOException e) {
            Log.d(TAG, "readData: " + e);
        }
        return questions;
    }

    public List<Question> readDataRiasec(Context context, int type) {
        String data;
        List<Question> questions = new ArrayList<>();
        try {
            InputStream in = context.getAssets().open("data/" + type + ".txt");
            InputStreamReader inreader = new InputStreamReader(in);
            BufferedReader bufreader = new BufferedReader(inreader);
            if (in != null) {
                while ((data = bufreader.readLine()) != null) {
                    String sNumber = data.substring(0, data.indexOf("@")).trim().replace(" ", "");
                    int number = Integer.parseInt(sNumber);
                    Question question = new Question(type, number, data.substring(data.indexOf("@") + 2, data.length()).trim(), 0, 0);
                    questions.add(question);
                }
                in.close();
            }
        } catch (IOException e) {
            Log.d(TAG, "readData: " + e);
        }
        return questions;
    }

    public List<Question> readDataPsy(Context context, int type) {
        String data;
        List<Question> questions = new ArrayList<>();
        try {
            InputStream in = context.getAssets().open("data/" + type + ".txt");
            InputStreamReader inreader = new InputStreamReader(in);
            BufferedReader bufreader = new BufferedReader(inreader);
            while ((data = bufreader.readLine()) != null) {
                int kind = Integer.parseInt(data.substring(0, data.indexOf("@")));
                int number = Integer.parseInt(data.substring(data.indexOf("@") + 1, data.lastIndexOf("@")).trim().replace(" ", "")) + 1;
                String content = data.substring(data.lastIndexOf("@") + 2);
                Question question = new Question(type, number, content, 0, kind);
                questions.add(question);
            }
            in.close();
        } catch (IOException e) {
            Log.d(TAG, "readData: " + e);
        }
        Log.d(TAG, "readDataPsy: " + questions.get(0).getNumberId() + " " + questions.get(0).getType() + " "
                + questions.get(0).getKind());
        return questions;
    }

    public void saveData(Context context) {
        List<Question> questions;
        questions = readDataPsy(context, Define.Question.TYPE_PSY_POCHOLIGICAL);
        for (Question question : readDataNeo(context, Define.Question.TYPE_NEO)) {
            questions.add(question);
        }
        for (Question question : readDataRiasec(context, Define.Question.TYPE_RIASEC)) {
            questions.add(question);
        }
        if (questions.size() != 0) {
            compositeDisposable.add(
                    questionRepository.insertListQuestionss(questions)
                            .doOnSubscribe(dispose -> isInsertListQuestionSuccess.setValue(new ObjectResponse<Boolean>().loading()))
                            .subscribe(() -> isInsertListQuestionSuccess.setValue(new ObjectResponse<Boolean>().success(true))
                                    , throwable -> isInsertListQuestionSuccess.setValue(new ObjectResponse<Boolean>().error(throwable)))
            );
        }
    }

    public MutableLiveData<ObjectResponse<Long>> getCountCustomer() {
        return isDataCustomer;
    }

    public MutableLiveData<ObjectResponse<Boolean>> getIsInsertListQuestionSuccess() {
        return isInsertListQuestionSuccess;
    }

    public void setIsInsertListQuestionSuccess(ObjectResponse<Boolean> value) {
        this.isInsertListQuestionSuccess.setValue(value);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }
}
