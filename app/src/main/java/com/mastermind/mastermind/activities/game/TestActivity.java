package com.mastermind.mastermind.activities.game;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.mastermind.mastermind.R;
import com.mastermind.mastermind.algorithm.GeneticAlgorithm;
import com.mastermind.mastermind.bean.game.genetic.Genotype;
import com.mastermind.mastermind.bean.game.user.ColorList;
import com.mastermind.mastermind.enums.ColorEnum;
import com.mastermind.mastermind.enums.GameVariantEnum;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.content.Intent.ACTION_VIEW;

public class TestActivity extends AppCompatActivity {

    private static Integer popSize;

    private static Double crossProb;

    private static Double mutateProb;

    private static final String FILE_NAME = "result.xls";

    private Integer count ;

    private EditText popSizetext;

    private EditText crossProbText;

    private EditText mutProbText;

    private EditText countText;

    private GameVariantEnum gameVariant;

    private String gameVariantS="";

    private GeneticAlgorithm geneticAlgorithm;

    private ColorList colorList ;

    private List<ColorEnum> colorsRandom;

    private List<List<Genotype>> allPrevAttempts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }

    public void testButtonClick(View view) {

        boolean check = true;
        RadioButton radio = (RadioButton) findViewById(R.id.classicRadio);
        RadioButton radio2 = (RadioButton) findViewById(R.id.superRadio);


        popSizetext = (EditText)findViewById(R.id.popSizeTxt);

        if(TextUtils.isEmpty( popSizetext.getText().toString()))
                check = false;


        crossProbText = (EditText)findViewById(R.id.crossProbTxt);

        if(TextUtils.isEmpty(crossProbText.getText().toString()))
            check = false;



        mutProbText = (EditText)findViewById(R.id.mutProbTxt);


        if(TextUtils.isEmpty(mutProbText.getText().toString()))
            check = false;

        countText = (EditText)findViewById(R.id.countTxt);

        if(TextUtils.isEmpty(countText.getText().toString()))
            check = false;


        if(radio.isChecked()) {
            gameVariantS = GameVariantEnum.CLASSIC.toString();
        }

        else if(radio2.isChecked()) {
            gameVariantS = GameVariantEnum.SUPER.toString();
        }


        if(TextUtils.isEmpty(gameVariantS))
            check = false;



        if(check)
        {
             popSize = Integer.valueOf(popSizetext.getText().toString());
             crossProb = Double.valueOf(crossProbText.getText().toString());
             mutateProb = Double.valueOf(mutProbText.getText().toString());
             count = Integer.valueOf(countText.getText().toString());

            if(radio.isChecked()) {
                gameVariant = GameVariantEnum.CLASSIC;
            }

            else if(radio2.isChecked()) {
                gameVariant = GameVariantEnum.SUPER;
            }

            allPrevAttempts = new ArrayList<>();

            for(int i=0;i<count;i++) {
                colorList = new ColorList(gameVariant);
                colorsRandom = colorList.getColorsRand();
                geneticAlgorithm = new GeneticAlgorithm(colorsRandom, crossProb, mutateProb, popSize, gameVariant, this);
                geneticAlgorithm.mainGame();
                allPrevAttempts.add(geneticAlgorithm.getPrevAttempt());

            }

            saveExcelFile(this,FILE_NAME,allPrevAttempts);
            File file = new File(getExternalFilesDir(null), FILE_NAME);

            String extension = android.webkit.MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(file).toString());
            String mimetype = android.webkit.MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), mimetype);


            startActivity(intent);

        }
        else
        {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Wypełnij poprawnie wszystkie pola");

            alertDialogBuilder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

    }

    public void onCheckboxClicked(View view){

        RadioButton userCh = (RadioButton) findViewById(R.id.classicRadio);
        RadioButton algCh = (RadioButton) findViewById(R.id.superRadio);

        switch(view.getId()){

            case R.id.classicRadio:

                algCh.setChecked(false);
                break;

            case R.id.superRadio:
                userCh.setChecked(false);
                break;

        }



    }

    public static boolean saveExcelFile(Context context,String fileName, List<List<Genotype> > attempts) {

        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            Log.e("EXCEL", "Storage not available or read only");
            return false;
        }

        boolean success = false;

        //New Workbook
        Workbook wb = new HSSFWorkbook();
        Cell c = null;

        //Cell style for header row
        CellStyle cs = wb.createCellStyle();
        cs.setFillForegroundColor(HSSFColor.LIME.index);
        cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        //New Sheet
        Sheet sheet1 = null;
        sheet1 = wb.createSheet("result");

        // Generate column headings
        Row row = sheet1.createRow(0);

        c = row.createCell(0);
        c.setCellValue("rozm_pop");
        c.setCellStyle(cs);

        c = row.createCell(1);
        c.setCellValue("pr_krzyz");
        c.setCellStyle(cs);

        c = row.createCell(2);
        c.setCellValue("pr_mut");
        c.setCellStyle(cs);

        c = row.createCell(3);
        c.setCellValue("ilosc_prob");
        c.setCellStyle(cs);

        sheet1.setColumnWidth(0, (15 * 500));
        sheet1.setColumnWidth(1, (15 * 500));
        sheet1.setColumnWidth(3, (15 * 500));
        sheet1.setColumnWidth(4, (15 * 500));

        Cell c2 = null;



        for(int i=0;i<attempts.size();i++)
        {
            Row row2 = sheet1.createRow(i+1);
            c2= row2.createCell(0);
            c2.setCellValue(popSize);

            c2= row2.createCell(1);
            c2.setCellValue(crossProb);

            c2= row2.createCell(2);
            c2.setCellValue(mutateProb);

            c2 = row2.createCell(3);
            c2.setCellValue(attempts.get(i).size());

        }


        File file = new File(context.getExternalFilesDir(null), fileName);

        FileOutputStream os = null;

        try {
            os = new FileOutputStream(file);
            wb.write(os);
            Log.w("FileUtils", "Writing file" + file);
            success = true;
        } catch (IOException e) {
            Log.w("FileUtils", "Error writing " + file, e);
        } catch (Exception e) {
            Log.w("FileUtils", "Failed to save file", e);
        } finally {
            try {
                if (null != os)
                    os.close();
            } catch (Exception ex) {
            }
        }
        return success;
    }
    public static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    public static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }

}
