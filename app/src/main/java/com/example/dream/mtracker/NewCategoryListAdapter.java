package com.example.dream.mtracker;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;


/**
 * Created by DevuMani on 10/5/2018.
 */

public class NewCategoryListAdapter extends RecyclerView.Adapter<NewCategoryListAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<Category_Data> iconList;
    Boolean type;

    public NewCategoryListAdapter(Context context, ArrayList<Category_Data> iconList, Boolean type) {

        mContext = context;
        this.iconList = iconList;
        this.type=type;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(mContext).inflate(R.layout.new_category_cust_row, parent,false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

       Category_Data c_data= iconList.get(position);

        Resources resources = mContext.getResources();
        final int resourceId = resources.getIdentifier(c_data.category_name, "drawable",
                mContext.getPackageName());
        holder.iconView.setImageDrawable(resources.getDrawable(resourceId));

    }

    @Override
    public int getItemCount() {

//        Toast.makeText(mContext, "Size"+iconList.size(), Toast.LENGTH_SHORT).show();
        return iconList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView iconView;

        public MyViewHolder(View itemView) {
            super(itemView);

            iconView = itemView.findViewById(R.id.new_iconView);

            iconView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            if(view == iconView)
            {
                Category_Data c_data= iconList.get(getAdapterPosition());

                String iconName= String.valueOf(c_data.getCategory_name());
                int c_id=c_data.getCategory_id();

//                AddCategory a=new AddCategory();
//                a.setImage(iconName);

//                InterfaceCategory interfaceCategory=NewCategoryChooser.newCategoryChooser;
//                interfaceCategory.setter(iconName,c_id);

                NewCategoryChooser.new_iconName=iconName;
                NewCategoryChooser.new_cid=c_id;

//                Intent intent=new Intent(mContext,AddCategory.class);
//                intent.putExtra("c_id",c_id);
//                intent.putExtra("c_name",iconName);
//                mContext.startActivity(intent);

                Toast.makeText(mContext, ""+iconName, Toast.LENGTH_SHORT).show();
//                Toast.makeText(mContext, "Dataa:::"+String.valueOf(iconList.get(getItemCount())), Toast.LENGTH_SHORT).show();
            }
        }
    }

}
