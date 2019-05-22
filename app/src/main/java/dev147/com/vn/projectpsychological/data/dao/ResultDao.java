package dev147.com.vn.projectpsychological.data.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import dev147.com.vn.projectpsychological.data.model.Result;
import dev147.com.vn.projectpsychological.data.model.ResultNeo;
import dev147.com.vn.projectpsychological.data.model.ResultPsychological;
import dev147.com.vn.projectpsychological.data.model.ResultRiasec;
import dev147.com.vn.projectpsychological.utils.Define;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface ResultDao {
    @Insert
    Single<Long> insertResultNeo(ResultNeo resultNeo);

    @Insert
    Single<Long> insertResultRiasec(ResultRiasec resultRiasec);

    @Insert
    Single<Long> insertResultPsycho(ResultPsychological resultPsychological);

    @Insert
    Single<Long> insertResult(Result result);

    @Query("SELECT * FROM " + Define.Result.TABLE_NAME + " WHERE " + Define.Result.ID_CUSTOMER + " = :idCustomer ORDER BY " + Define.Result.TIME + " DESC")
    Flowable<List<Result>> getListResult(int idCustomer);

    @Query("SELECT * FROM " + Define.ResultNeo.TABLE_NAME + " WHERE " + Define.Result.ID + " = :id")
    Flowable<ResultNeo> getNeo(int id);

    @Query("SELECT * FROM " + Define.ResultRiasec.TABLE_NAME + " WHERE " + Define.Result.ID + " = :id")
    Flowable<ResultRiasec> getRiasec(int id);
}
