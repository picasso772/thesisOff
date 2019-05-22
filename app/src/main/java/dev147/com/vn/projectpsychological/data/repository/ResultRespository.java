package dev147.com.vn.projectpsychological.data.repository;

import java.util.List;

import javax.inject.Inject;

import dev147.com.vn.projectpsychological.data.AbstractAppDatabase;
import dev147.com.vn.projectpsychological.data.model.Result;
import dev147.com.vn.projectpsychological.data.model.ResultNeo;
import dev147.com.vn.projectpsychological.data.model.ResultPsychological;
import dev147.com.vn.projectpsychological.data.model.ResultRiasec;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ResultRespository {
    private AbstractAppDatabase abstractAppDatabase;

    @Inject
    public ResultRespository(AbstractAppDatabase abstractAppDatabase) {
        this.abstractAppDatabase = abstractAppDatabase;
    }

    public Single<Long> insertResultNeo(ResultNeo resultNeo) {
        return abstractAppDatabase.resultDao().insertResultNeo(resultNeo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<Long> insertResult(Result result) {
        return abstractAppDatabase.resultDao().insertResult(result)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<Long> insertResultRiasec(ResultRiasec resultRiasec) {
        return abstractAppDatabase.resultDao().insertResultRiasec(resultRiasec)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<Long> insertResultPsycho(ResultPsychological resultPsychological) {
        return abstractAppDatabase.resultDao().insertResultPsycho(resultPsychological)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Flowable<List<Result>> getListResultByIdCustomer(int idCustomer) {
        return abstractAppDatabase.resultDao().getListResult(idCustomer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Flowable<ResultNeo> getResultNeo(int id) {
        return abstractAppDatabase.resultDao().getNeo(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Flowable<ResultRiasec> getResultRiasec(int id) {
        return abstractAppDatabase.resultDao().getRiasec(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
