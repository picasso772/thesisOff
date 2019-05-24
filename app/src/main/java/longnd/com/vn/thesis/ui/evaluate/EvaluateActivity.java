package longnd.com.vn.thesis.ui.evaluate;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import dev147.com.vn.projectpsychological.R;
import longnd.com.vn.thesis.adapter.EvaluatePagerAdapter;
import longnd.com.vn.thesis.data.model.ResultNeo;
import longnd.com.vn.thesis.data.model.ResultPsychological;
import longnd.com.vn.thesis.data.model.ResultRiasec;
import dev147.com.vn.projectpsychological.databinding.ActivityEvaluateBinding;
import longnd.com.vn.thesis.ui.base.BaseActivity;
import longnd.com.vn.thesis.utils.DataUtils;
import longnd.com.vn.thesis.utils.Define;
import longnd.com.vn.thesis.utils.Fields;
import longnd.com.vn.thesis.utils.PsyLoading;

public class EvaluateActivity extends BaseActivity<EvaluateViewModel, ActivityEvaluateBinding> {
    private Intent intent;
    private int[] results;
    private ResultNeo resultNeo;
    private ResultRiasec resultRiasec;

    @Override
    protected void initView() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(EvaluateViewModel.class);
        if (getIntent() == null) {
            return;
        }
        intent = getIntent();
        int type = intent.getIntExtra(Fields.KEY_TYPE, -1);
        if (type == -1) {
            return;
        }
        if (intent.getBundleExtra("QUA").getSerializable(Fields.KEY_VALUE) == null) {
            return;
        }

        viewModel.setType(type);
        graphChartsType(type);

        viewModel.setResults(results);
        FragmentManager fm = getSupportFragmentManager();
        EvaluatePagerAdapter pagerAdapter = new EvaluatePagerAdapter(fm);
        binding.viewPager.setAdapter(pagerAdapter);
    }

    private void graphChartsType(int type) {
        switch (type) {
            case Define.Question.TYPE_NEO:
                results = new int[5];
                resultNeo = (ResultNeo) intent.getBundleExtra("QUA").getSerializable(Fields.KEY_VALUE);
                // type : 0 - A, 1 - C, 2 - O, 3 - N, 4 - E
                results[0] = resultNeo.getAgreeableness();
                results[1] = resultNeo.getConscientiousness();
                results[2] = resultNeo.getOpenness();
                results[3] = resultNeo.getNeuroticism();
                results[4] = resultNeo.getExtraversion();
                break;
            case Define.Question.TYPE_RIASEC:
                results = new int[6];
                resultRiasec = (ResultRiasec) intent.getBundleExtra("QUA").getSerializable(Fields.KEY_VALUE);
                // type: 0 - rule, 1- society, 2 - discover, 3 - reality, 4 - art, 5 - convince
                results[0] = resultRiasec.getRule();
                results[1] = resultRiasec.getSociety();
                results[2] = resultRiasec.getDiscover();
                results[3] = resultRiasec.getReality();
                results[4] = resultRiasec.getArt();
                results[5] = resultRiasec.getConvince();
                break;
            case Define.Question.TYPE_PSY_POCHOLIGICAL:
                DataUtils.getInstance().resultPsychological = (ResultPsychological) intent.getBundleExtra("QUA").getSerializable(Fields.KEY_VALUE);
                break;
        }
    }

    @Override
    protected void initListenerOnClick() {
    }

    @Override
    protected void initData() {
        PsyLoading.getInstance(this).hidden();
        PsyLoading.getInstance(this).destroyLoadingDialog();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_evaluate;
    }

    @Override
    public void onClick(View v) {
    }

    public void viewDetail() {
        binding.viewPager.setCurrentItem(1);
    }

    public void createFilePdf(){
        // create a new document
        PdfDocument document = new PdfDocument();
        // crate a page description
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();
        // start a page
        PdfDocument.Page page = document.startPage(pageInfo);
        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        canvas.drawCircle(50, 50, 30, paint);
        paint.setColor(Color.BLACK);
        canvas.drawText("LONG ND", 80, 50, paint);
        //canvas.drawt
        // finish the page
        document.finishPage(page);
        // draw text on the graphics object of the page
        // Create Page 2
        pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 2).create();
        page = document.startPage(pageInfo);
        canvas = page.getCanvas();
        paint = new Paint();
        paint.setColor(Color.BLUE);
        canvas.drawCircle(100, 100, 100, paint);
        document.finishPage(page);
        // write the document content
        String directory_path = Fields.ROOT_FOLDER;
        File file = new File(directory_path);
        if (!file.exists()) {
            file.mkdirs();
        }
        String targetPdf = directory_path+"/test-2.pdf";
        File filePath = new File(targetPdf);
        try {
            filePath.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            document.writeTo(new FileOutputStream(filePath));
            Toast.makeText(this, "Done", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Log.e("main", "error "+e.toString());
            Toast.makeText(this, "Something wrong: " + e.toString(),  Toast.LENGTH_LONG).show();
        }
        // close the document
        document.close();
    }
}
