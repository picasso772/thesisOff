package dev147.com.vn.projectpsychological.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import dev147.com.vn.projectpsychological.data.model.Question;
import dev147.com.vn.projectpsychological.utils.Define;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

import java.util.List;

@Dao
public interface QuestionDao {
    @Insert()
    Completable insertListQuestion(List<Question> questions);

    @Query("SELECT * FROM " + Define.Question.TABLE_NAME + " WHERE " + Define.Question.TYPE + " = :type ORDER BY " + Define.Question.NUMBER_ID + " ASC")
    Flowable<List<Question>> getQuestionsByType(int type);
}
