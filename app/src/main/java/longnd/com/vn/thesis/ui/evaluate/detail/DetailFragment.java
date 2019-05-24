package longnd.com.vn.thesis.ui.evaluate.detail;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;

import dev147.com.vn.projectpsychological.R;
import longnd.com.vn.thesis.adapter.ShowResultPsyAdapter;
import dev147.com.vn.projectpsychological.databinding.FragmentDetailBinding;
import longnd.com.vn.thesis.ui.base.BaseFragment;
import longnd.com.vn.thesis.utils.DataUtils;
import longnd.com.vn.thesis.utils.Define;
import longnd.com.vn.thesis.utils.EvaluateUtils;

public class DetailFragment extends BaseFragment<DetailViewModel, FragmentDetailBinding> {
    private int type;

    @Override
    protected void initListenerOnClick() {

    }

    @Override
    protected void initObserve() {

    }

    @Override
    protected void initView() {
        int idImage = 0;
        type = evaluateViewModel.getType();
        switch (type) {
            case Define.Question.TYPE_NEO:
                binding.detailNeo.setVisibility(View.VISIBLE);
                idImage = R.drawable.image_neo_rectangle;
                break;
            case Define.Question.TYPE_RIASEC:
                binding.detailRiasec.setVisibility(View.VISIBLE);
                idImage = R.drawable.image_riasec_rectangle;
                break;
            case Define.Question.TYPE_PSY_POCHOLIGICAL:
                idImage = R.drawable.image_psy_rectangle;
                break;
        }
        Glide.with(getContext())
                .load(idImage)
                .into(binding.ivLogo);
    }

    @Override
    protected void initData() {
        switch (type) {
            case Define.Question.TYPE_NEO:
                detailNeo();
                break;
            case Define.Question.TYPE_RIASEC:
                detailRiasec();
                break;
            case Define.Question.TYPE_PSY_POCHOLIGICAL:
                detailPsycho();
                break;
        }
    }

    private void detailPsycho() {
        binding.detailPsycho.setVisibility(View.VISIBLE);
        String[] resultPsy = EvaluateUtils.evaluatePsycho(DataUtils.getInstance().resultPsychological);
        String[] kindPsy = getContext().getResources().getStringArray(R.array.kind_title_psycho);
        binding.recyclerPsycho.setLayoutManager(new LinearLayoutManager(getContext()));
        ShowResultPsyAdapter adapter = new ShowResultPsyAdapter(getContext(), kindPsy, resultPsy);
        binding.recyclerPsycho.setAdapter(adapter);
    }

    private void detailRiasec() {
        binding.detailRiasec.setVisibility(View.VISIBLE);
        binding.tvr1.setText(EvaluateUtils.resultRiasecTitle(getContext(), evaluateViewModel.getMaxInPosition()).toUpperCase());
        binding.tvr11.setText(EvaluateUtils.resultRiasecDetail(getContext(), evaluateViewModel.getMaxInPosition()));
    }

    private void detailNeo() {
        binding.detailNeo.setVisibility(View.VISIBLE);
        int[] results = evaluateViewModel.getResults();
        int[] nResults = viewModel.getResultsnNeo(DataUtils.getInstance().getCustomer().getGender(), results);
        binding.tv1.setText("DỄ CHẤP NHẬN: " + EvaluateUtils.wordResults(nResults[0]).toUpperCase());
        binding.tv11.setText(getContext().getResources().getStringArray(R.array.neo_a)[nResults[0]]);
        binding.tv2.setText("TẬN TÂM: " + EvaluateUtils.wordResults(nResults[1]).toUpperCase());
        binding.tv21.setText(getContext().getResources().getStringArray(R.array.neo_c)[nResults[0]]);
        binding.tv3.setText("CỞI MỞ, HAM HỌC HỎI: " + EvaluateUtils.wordResults(nResults[2]).toUpperCase());
        binding.tv31.setText(getContext().getResources().getStringArray(R.array.neo_o)[nResults[0]]);
        binding.tv4.setText("NHIỄU TÂM: " + EvaluateUtils.wordResults(nResults[3]).toUpperCase());
        binding.tv41.setText(getContext().getResources().getStringArray(R.array.neo_n)[nResults[0]]);
        binding.tv5.setText("HƯỚNG NGOẠI: " + EvaluateUtils.wordResults(nResults[4]).toUpperCase());
        binding.tv51.setText(getContext().getResources().getStringArray(R.array.neo_e)[nResults[0]]);
    }

    @Override
    public Class<DetailViewModel> getModelClass() {
        return DetailViewModel.class;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_detail;
    }

    @Override
    public void onClick(View v) {

    }
}
