package com.example.dream.mtracker;

import android.content.DialogInterface;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.maltaisn.icondialog.Icon;
import com.maltaisn.icondialog.IconDialog;
import com.maltaisn.icondialog.IconView;

public class AddCategory extends AppCompatActivity{

    Button color;
    EditText test;
    ImageView icon_chooser;
    IconView icon_image;
    private int currentBackgroundColor = 0xffffffff;

    FloatingActionButton save;

    TextView income,expense;
    Switch expenditure_type;

    String data="";
    private int icon_color = 0;


    private Icon[] selectedIcons;

    Boolean type=false;

    static int newColor=0;

    static String iconName="";
    static int c_id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        init();

        expenditure_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switchCheck();

            }
        });



        if(!iconName.equalsIgnoreCase("")) {

            Resources resources = getResources();
            final int resourceId = resources.getIdentifier(iconName, "drawable", getPackageName());
            icon_image.setImageDrawable(resources.getDrawable(resourceId));
        }

        color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(AddCategory.this, ""+test.getText().toString(), Toast.LENGTH_SHORT).show();

                ColorPickerDialogBuilder
                        .with(AddCategory.this)
                        .setTitle("Choose color")
                        .initialColor(currentBackgroundColor)
                        .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                        .density(12)
                        .setOnColorSelectedListener(new OnColorSelectedListener() {
                            @Override
                            public void onColorSelected(int selectedColor) {
                                toast("onColorSelected: 0x" + Integer.toHexString(selectedColor));
                            }
                        })
                        .setPositiveButton("ok", new ColorPickerClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                                changeBackgroundColor(selectedColor);
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .build()
                        .show();



            }
        });

        icon_chooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NewCategoryChooser newCategoryChooser=new NewCategoryChooser();
                Bundle bundle=new Bundle();
                bundle.putBoolean("type",type);
                newCategoryChooser.setArguments(bundle);
                newCategoryChooser.show(getSupportFragmentManager(),"New Category Chooser");


            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DBFunction dbFunction =new DBFunction(AddCategory.this);

                Boolean b=dbFunction.changeCategory(c_id);
                if(b==true)
                {
                    Toast.makeText(AddCategory.this, "Successfully added new category", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(AddCategory.this, "Couldn't add new category", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void init() {

        color=findViewById(R.id.color);
        color.setBackgroundColor(currentBackgroundColor);
        icon_chooser=findViewById(R.id.icon_choose);
        icon_image=findViewById(R.id.icon_image);

        save=findViewById(R.id.ac_fab);

        income=findViewById(R.id.add_income_text);
        expense=findViewById(R.id.add_expense_text);
        expenditure_type=findViewById(R.id.add_category_switch);


        type=getIntent().getBooleanExtra("type",false);

        expenditure_type.setChecked(type);

        switchCheck();
    }

    private void switchCheck() {

//        Boolean b = expenditure_type.isChecked();
        type=expenditure_type.isChecked();
        if (type == true) {
            expense.setTextColor(Color.RED);
            income.setTextColor(Color.BLACK);
//            data_switch.setBackgroundColor(getResources().getColor(R.color.red));

        } else {
            income.setTextColor(getResources().getColor(R.color.green));
//            data_switch.setBackgroundColor(getResources().getColor(R.color.green));
            expense.setTextColor(Color.BLACK);
        }

    }

    private void changeBackgroundColor(int selectedColor) {

            currentBackgroundColor = selectedColor;
            color.setBackgroundColor(selectedColor);
            icon_color=selectedColor;

    }

    private void toast(String text) {

        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();

    }
}
