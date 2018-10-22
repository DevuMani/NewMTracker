package com.example.dream.mtracker;

import android.content.DialogInterface;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.maltaisn.icondialog.Icon;
import com.maltaisn.icondialog.IconDialog;
import com.maltaisn.icondialog.IconView;

public class AddCategory extends AppCompatActivity implements IconDialog.Callback {

    Button color;
    EditText test;
    ImageView icon_chooser;
    IconView icon_image;
    private int currentBackgroundColor = 0xffffffff;

    String data="";
    private int icon_color = 0;


    private Icon[] selectedIcons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        color=findViewById(R.id.color);
        color.setBackgroundColor(currentBackgroundColor);
        test=findViewById(R.id.category_name);
        icon_chooser=findViewById(R.id.icon_choose);
        //icon_image=findViewById(R.id.icon_image);

////        color.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
//
//                Toast.makeText(AddCategory.this, ""+test.getText().toString(), Toast.LENGTH_SHORT).show();
//
//                ColorPickerDialogBuilder
//                        .with(AddCategory.this)
//                        .setTitle("Choose color")
//                        .initialColor(currentBackgroundColor)
//                        .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
//                        .density(12)
//                        .setOnColorSelectedListener(new OnColorSelectedListener() {
//                            @Override
//                            public void onColorSelected(int selectedColor) {
//                                toast("onColorSelected: 0x" + Integer.toHexString(selectedColor));
//                            }
//                        })
//                        .setPositiveButton("ok", new ColorPickerClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
//                                changeBackgroundColor(selectedColor);
//                            }
//                        })
//                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                            }
//                        })
//                        .build()
//                        .show();
//
//
//
//            }
//        });

        icon_chooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(icon_color==0)
                {
                    Toast.makeText(AddCategory.this, "Choose a color", Toast.LENGTH_SHORT).show();
                }
                else {
                    IconDialog iconDialog = new IconDialog();

                    iconDialog.setTitle(IconDialog.VISIBILITY_ALWAYS, "Choose an icon");
                    iconDialog.setSelectedIcons(selectedIcons);
//                    iconDialog.show(getSupportFragmentManager(), "icon_dialog");

                }
            }
        });
    }

    @Override
    public void onIconDialogIconsSelected(Icon[] icons) {
        selectedIcons = icons;

        icon_image.setIcon(icons[0]);
//        icon_image.setColorFilter(icon_color);
        toast(icons.toString());
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
