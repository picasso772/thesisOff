package dev147.com.vn.projectpsychological.ui.evaluate.detail;

import javax.inject.Inject;

import androidx.lifecycle.ViewModel;
import dev147.com.vn.projectpsychological.utils.EvaluateUtils;

public class DetailViewModel extends ViewModel {
    @Inject
    DetailViewModel(){

    }

    public int[] getResultsnNeo(int gender, int[] results) {
//        if (gender == 0) {
//            return EvaluateUtils.evaluateNeoFemale(results);
//        } else if (gender == 1) {
//            // là nam
        return EvaluateUtils.evaluateNeoMale(results);
//        } else {
//            return null;
//        }
    }
}
