package com.itproger.calcjava;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private  DataBase dataBase;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private ImageButton btn_next_activity;
    Button btn_add_db;
     TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataBase = new DataBase(this);
        listView =  findViewById(R.id.test_1);

        loadAllTask();
        btn_add_db = findViewById(R.id.btn_add_db);


    }



    public void onOptionsItemSelected(View v) {
        TextView result_text = findViewById(R.id.result_text);


        if (result_text.getText().equals("")) {
            Toast.makeText(getApplicationContext(), "Произведите расчет", Toast.LENGTH_SHORT).show();

        }

        else   {


            final EditText userTaskField = new EditText(this);
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Добавление нового задание")
                    .setMessage("Чтобы вы хотели добавить?")
                    .setView(userTaskField)
                    .setPositiveButton("Добавить", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String task = String.valueOf(userTaskField.getText());


                            String task_result = task +" "+ "-"+" " + " " + result_text.getText();

                            dataBase.insertData(task_result);
                            loadAllTask();
                        }
                    })

                    .setNegativeButton("Ничего", null)
                    .create();
            dialog.show();

        }

        }

//адаптер добавление стиля и строк всего массива в listview
    private void loadAllTask() {
        ArrayList<String> allTask = dataBase.getAllTask();

        if (arrayAdapter == null){

            arrayAdapter = new ArrayAdapter<String>(this, R.layout.task_list_row, R.id.text_label_row, allTask);
            listView.setAdapter(arrayAdapter);

        }
        else {
            arrayAdapter.clear();
            arrayAdapter.addAll(allTask);
            arrayAdapter.notifyDataSetChanged();
        }
    }









    public void ClearEditTextAndResult (View v) {
        EditText edit_stoimost = findViewById(R.id.edit_stoimost);
        EditText edit_price = findViewById(R.id.edit_price);
        EditText edit_price_za = findViewById(R.id.edit_price_za);
        TextView result_text = findViewById(R.id.result_text);
        result_text.setText("");
        edit_price.setText("");
        edit_price_za.setText("");
        edit_stoimost.setText("");

    }











    public void deleteTask(View button) {
        View parent = (View)button.getParent();
        TextView textView = parent.findViewById(R.id.text_label_row);
        String task = String.valueOf(textView.getText());
        dataBase.deleteData(task);
        loadAllTask();
    }



    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    public void ConvertStoimost(View v) {

        EditText edit_stoimost = findViewById(R.id.edit_stoimost);
        EditText edit_price = findViewById(R.id.edit_price);
        EditText edit_price_za = findViewById(R.id.edit_price_za);


        TextView result_text = findViewById(R.id.result_text);

        Spinner spinner_two = findViewById(R.id.spinner_two);
        Spinner spinner_one = findViewById(R.id.spinner_one);
        Spinner spinner_money = findViewById(R.id.spinner_money);


        String text_stoimost = edit_stoimost.getText().toString();
        if (text_stoimost.equals("") || text_stoimost.equals("0") || text_stoimost.equals(".") ){
            Toast.makeText(getApplicationContext(),"Введите количество товара", Toast.LENGTH_SHORT).show();
        } else if (!text_stoimost.equals("")) {

            String text_price = edit_price.getText().toString();
            if (text_price.equals("") || text_price.equals("0")  || text_price.equals(".")) {
                Toast.makeText(getApplicationContext(), "Введите стоимость товара", Toast.LENGTH_SHORT).show();
            } else if(!text_price.equals("")) {


                String text_price_za = edit_price_za.getText().toString();
                if (text_price_za.equals("") || text_price_za.equals("0")  || text_price_za.equals(".")) {
                    Toast.makeText(getApplicationContext(), "Введите расчитываемый вес", Toast.LENGTH_SHORT).show();
                }






                else if (!text_price_za.equals("")) {

                        String spinner_znach_two = spinner_two.getSelectedItem().toString();
                        String spinner_znach_one = spinner_one.getSelectedItem().toString();
                        String spinner_znach_money = spinner_money.getSelectedItem().toString();

                    float number_1 = Float.parseFloat(text_price);
                    float number_2 = Float.parseFloat(text_stoimost);
                    float number_3 = Float.parseFloat(text_price_za);



                    if (spinner_znach_two.equals("кг") && spinner_znach_one.equals("кг") ) {
                        float number = number_1 / number_2 * number_3;
                        result_text.setText(String.format("%.2f",number)+ " " + spinner_znach_money + " " + "за"+ " " + number_3 + " " + spinner_znach_two );
                    } else if (spinner_znach_two.equals("г") && spinner_znach_one.equals("г") ) {
                        float number = number_1 / number_2 * number_3;
                        result_text.setText(String.format("%.2f",number)+ " " + spinner_znach_money + " " + "за" + " " + number_3 + " " + spinner_znach_two);
                    } else if  (spinner_znach_two.equals("г") && spinner_znach_one.equals("кг") ){
                        float number = number_1 / number_2 / (1000 / number_3);
                        result_text.setText(String.format("%.2f",number)+ " " + spinner_znach_money + " " + "за" + " " + number_3 + " " + spinner_znach_two);
                    } else if  (spinner_znach_two.equals("кг") && spinner_znach_one.equals("г") ){
                        float number = number_1 / number_2 * (1000 * number_3);
                        result_text.setText(String.format("%.2f",number)+ " " + spinner_znach_money + " " + "за" + " " + number_3 + " " + spinner_znach_two);
                    } else if (spinner_znach_two.equals("л") && spinner_znach_one.equals("л") ) {
                        float number = number_1 / number_2 * number_3;
                        result_text.setText(String.format("%.2f",number)+ " " + spinner_znach_money + " " + "за"+ " " + number_3 + " " + spinner_znach_two);
                    } else if (spinner_znach_two.equals("мл") && spinner_znach_one.equals("мл") ) {
                        float number = number_1 / number_2 * number_3;
                        result_text.setText(String.format("%.2f",number)+ " " + spinner_znach_money + " " + "за" + " " + number_3 + " " + spinner_znach_two);
                    } else if  (spinner_znach_two.equals("мл") && spinner_znach_one.equals("л") ){
                        float number = number_1 / number_2 / (1000 / number_3);
                        result_text.setText(String.format("%.2f",number)+ " " + spinner_znach_money + " " + "за" + " " + number_3 + " " + spinner_znach_two);
                    } else if  (spinner_znach_two.equals("л") && spinner_znach_one.equals("мл") ){
                        float number = number_1 / number_2 * (1000 * number_3);
                        result_text.setText(String.format("%.2f",number)+ " " + spinner_znach_money + " " + "за" + " " + number_3 + " " + spinner_znach_two);
                    }  else {
                        Toast.makeText(getApplicationContext(), "Неверные значения едениц", Toast.LENGTH_SHORT).show();

                    }





                }

            }
        }
    }










}