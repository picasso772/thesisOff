package longnd.com.vn.thesis.ui.evaluate.charts;

import android.os.Handler;
import android.view.View;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Polar;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.PolarSeriesType;
import com.anychart.enums.ScaleStackMode;
import com.anychart.enums.ScaleTypes;
import com.anychart.scales.Linear;

import java.util.ArrayList;
import java.util.List;

import dev147.com.vn.projectpsychological.R;
import longnd.com.vn.thesis.data.model.Customer;
import longnd.com.vn.thesis.data.model.ResultPsychological;
import dev147.com.vn.projectpsychological.databinding.FragmentChartsBinding;
import longnd.com.vn.thesis.ui.base.BaseFragment;
import longnd.com.vn.thesis.ui.custom.GraphNeoView;
import longnd.com.vn.thesis.ui.custom.GraphRiasecView;
import longnd.com.vn.thesis.ui.evaluate.EvaluateActivity;
import longnd.com.vn.thesis.utils.DataUtils;
import longnd.com.vn.thesis.utils.Define;
import longnd.com.vn.thesis.utils.EvaluateUtils;
import longnd.com.vn.thesis.utils.Fields;
import longnd.com.vn.thesis.utils.PsyLoading;
import longnd.com.vn.thesis.utils.Utils;

public class ChartsFragment extends BaseFragment<ChartsViewModel, FragmentChartsBinding> {

    private GraphNeoView neoView;
    private GraphRiasecView riasecView;
    private Customer customer;

    @Override
    protected void initListenerOnClick() {
        binding.btnBack.setOnClickListener(this);
        binding.btnDownload.setOnClickListener(this);
        binding.btnViewAll.setOnClickListener(this);
    }

    @Override
    protected void initObserve() {

    }

    @Override
    protected void initView() {
        binding.mContainer.removeAllViews();
        binding.tvTitle.setTypeface(Utils.getTypeFace(getContext(), Fields.FONT_TIMES));
        neoView = new GraphNeoView(getContext());
        riasecView = new GraphRiasecView(getContext());
    }

    @Override
    protected void initData() {
        customer = DataUtils.getInstance().getCustomer();
        if (customer == null) {
            return;
        }
        switch (evaluateViewModel.getType()) {
            case Define.Question.TYPE_NEO:
                binding.layoutRiasec.setVisibility(View.GONE);
                showChartNeo();
                break;

            case Define.Question.TYPE_RIASEC:
                binding.layoutNeo.setVisibility(View.GONE);
                showChartRiasec();
                break;
            case Define.Question.TYPE_PSY_POCHOLIGICAL:
                binding.mContainer.setVisibility(View.GONE);
                binding.layoutNeo.setVisibility(View.GONE);
                binding.layoutRiasec.setVisibility(View.GONE);
                binding.chartAny.setVisibility(View.VISIBLE);
                showChartPsycho();
                break;
        }

    }

    private void showChartPsycho() {
        Handler handler = new Handler();
        PsyLoading.getInstance(getContext()).show();
        handler.postDelayed(() -> PsyLoading.getInstance(getContext()).hidden(),2000);
        ResultPsychological resultPsychological = DataUtils.getInstance().resultPsychological;
        AnyChartView anyChartView = binding.chartAny;
        Polar polar = AnyChart.polar();

        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("Lo âu", resultPsychological.getLoAu()));
        data.add(new ValueDataEntry("Trầm cảm", resultPsychological.getTramCam()));
        data.add(new ValueDataEntry("Stress", resultPsychological.getStress()));
        data.add(new ValueDataEntry("Khó tập trung", resultPsychological.getKhoTapTrung()));
        data.add(new ValueDataEntry("Tăng động", resultPsychological.getTangDong()));
        data.add(new ValueDataEntry("KK giao tiếp xã hội", resultPsychological.getKkGiaoTiepXaHoi()));
        data.add(new ValueDataEntry("KK học tập", resultPsychological.getKkHocTap()));
        data.add(new ValueDataEntry("KK trong định hướng nghề nghiệp", resultPsychological.getKkDinhHuongNgheNghiep()));
        data.add(new ValueDataEntry("KK trong mối quan hệ với cha mẹ", resultPsychological.getKkQuanHeChaMe()));
        data.add(new ValueDataEntry("KK trong mối quan hệ với thầy cô", resultPsychological.getKkQuanHeThayCo()));
        data.add(new ValueDataEntry("KK trong mối quan hệ với bạn bè", resultPsychological.getKkQuanHeBanBe()));
        data.add(new ValueDataEntry("Hành vi thách thức - chống đối", resultPsychological.getHanhViChongDoi()));
        data.add(new ValueDataEntry("Rối loạn hành vi ứng xử", resultPsychological.getRoiLoanHanhVi()));
        data.add(new ValueDataEntry("Gây hấn", resultPsychological.getGayHan()));

        Set set = Set.instantiate();
        set.data(data);
        Mapping seriesData = set.mapAs("{ x: 'x', value: 'value' }");
        polar.column(seriesData);

        polar.sortPointsByX(true)
                .defaultSeriesType(PolarSeriesType.COLUMN)
                .yAxis(true)
                .xScale(ScaleTypes.ORDINAL);
        polar.title().margin().bottom(20d);

        ((Linear) polar.yScale(Linear.class)).stackMode(ScaleStackMode.VALUE);

        anyChartView.setChart(polar);
    }

    private void showChartRiasec() {
        // riasecView.setData();
        riasecView.setData(evaluateViewModel.getMin(), evaluateViewModel.getSpaceRiasec(), evaluateViewModel.getResults());
        binding.mContainer.addView(riasecView);

        binding.tvResultRiasec.setTypeface(Utils.getTypeFace(getContext(), Fields.FONT_TIMES));
        binding.tvResultRiasec.setText(getContext().getResources().getString(R.string.evaluate_riasec,
                DataUtils.getInstance().getCustomer().getLastName().toUpperCase(),
                EvaluateUtils.resultRiasecTitle(getContext(), evaluateViewModel.getMaxInPosition()).toUpperCase()));
    }

    private void showChartNeo() {
        neoView.setData(evaluateViewModel.getMin(), evaluateViewModel.getSpaceNeo(), evaluateViewModel.getResults());
        binding.mContainer.addView(neoView);
        int[] results = evaluateViewModel.getResults();
        binding.tvResult01.setText(String.valueOf(results[0]));
        binding.tvResult02.setText(String.valueOf(results[1]));
        binding.tvResult03.setText(String.valueOf(results[2]));
        binding.tvResult04.setText(String.valueOf(results[3]));
        binding.tvResult05.setText(String.valueOf(results[4]));

        int[] nResults = viewModel.getResultsnNeo(customer.getGender(), results);
        binding.tvResult11.setText(EvaluateUtils.wordResults(nResults[0]));
        binding.tvResult22.setText(EvaluateUtils.wordResults(nResults[1]));
        binding.tvResult33.setText(EvaluateUtils.wordResults(nResults[2]));
        binding.tvResult44.setText(EvaluateUtils.wordResults(nResults[3]));
        binding.tvResult55.setText(EvaluateUtils.wordResults(nResults[4]));
    }

    @Override
    public Class<ChartsViewModel> getModelClass() {
        return ChartsViewModel.class;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_charts;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                getActivity().onBackPressed();
                break;
            case R.id.btnViewAll:
                ((EvaluateActivity) getActivity()).viewDetail();
                break;
            case R.id.btnDownload:
                ((EvaluateActivity) getActivity()).createFilePdf();
                break;
        }
    }
}
