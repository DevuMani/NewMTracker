package com.example.dream.mtracker;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class Profile_Settings_MainListAdapter extends RecyclerView.Adapter<Profile_Settings_MainListAdapter.viewhold> {

    Context context;
    private List<Profile_Settings_ListItem> items; //instance var
    private LayoutInflater inflater;

    public Profile_Settings_MainListAdapter(Context c, ArrayList<Profile_Settings_ListItem> items) {

        context=c;
        this.items = items;
        inflater = LayoutInflater.from(c);  //nammade ob vachu inflater undaki
    }


    @Override
    public viewhold onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = inflater.inflate(R.layout.profile_cust_row, parent, false);
        return new viewhold(v);//  converting our xml to java
    }

    @Override
    public void onBindViewHolder(viewhold holder, int position) {

        Profile_Settings_ListItem item = items.get(position);
        holder.title.setText(item.getName());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewhold extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;

        View container;

        public viewhold(View itemView) {
            super(itemView); // goes to super class constructor; to connect each items
            title =  itemView.findViewById(R.id.profile_cust_text);// view ntw ullil kidakanondanu ingane koduthe
//            container = (View) itemView.findViewById(R.id.container_activity);
            itemView.setOnClickListener(this);// evide click cheithalum work akanam athondanu itemview vachu vilikkane

        }

        @Override
        public void onClick(View view) {

            int a=getPosition();
            Profile_Settings_ListItem list=items.get(a);
            String name=list.getName();

            if(name.equalsIgnoreCase("home"))
            {

            }
            else if(name.equalsIgnoreCase("recurrance"))
            {

            }
            else if(name.equalsIgnoreCase("new category"))
            {
                Intent intent=new Intent(context,AddCategory.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("type",true);
                context.startActivity(intent);
            }
            else if(name.equalsIgnoreCase("back up"))
            {

            }
            else if(name.equalsIgnoreCase("password"))
            {
                SharedPreferences sp=context.getSharedPreferences("User",Context.MODE_PRIVATE);
                Boolean pass=sp.getBoolean("Password",false);
                if(pass==true)
                {

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setCancelable(false);
                    builder.setTitle("Change Password");
                    builder.setMessage("Do you want to change your password?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            Intent intent=new Intent(context,PasswordSettingScreen.class);
                            intent.putExtra("from","profile_change");
                            context.startActivity(intent);

                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
//                        Toast.makeText(PasswordSettingScreen.this, "Don't Change password", Toast.LENGTH_SHORT).show();

                            dialogInterface.dismiss();

                        }
                    });

                    builder.create().show();



                }
                else
                {
                    Intent intent=new Intent(context,PasswordSettingScreen.class);
                    intent.putExtra("from","profile_set");
                    context.startActivity(intent);
                }

            }
            else if(name.equalsIgnoreCase("clear password"))
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(false);
                builder.setTitle("Clear Password");
                builder.setMessage("Do you want to clear your password?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent=new Intent(context,PasswordSettingScreen.class);
                        intent.putExtra("from","clear");
                        context.startActivity(intent);
                        Toast.makeText(context, "Clear password", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Toast.makeText(PasswordSettingScreen.this, "Don't clear password", Toast.LENGTH_SHORT).show();

                        dialogInterface.dismiss();

                    }
                });

                builder.create().show();



            }
            else if(name.equalsIgnoreCase("rate us"))
            {

            }


            Toast.makeText(context,name,Toast.LENGTH_SHORT).show();

        }
    }
}


