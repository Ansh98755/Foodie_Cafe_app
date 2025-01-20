package com.example.foodiecafe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    private List<MenuItem> menuItems;
    private Context context;
    private String currentCategory = "All";

    public MenuAdapter(List<MenuItem> menuItems, Context context) {
        this.menuItems = menuItems;
        this.context = context;
    }

    public void filterByCategory(String category) {
        this.currentCategory = category;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MenuItem menuItem = getFilteredMenuItem(position);

        holder.imageView.setImageResource(menuItem.getImageResource());
        holder.nameTextView.setText(menuItem.getName());
        holder.descriptionTextView.setText(menuItem.getDescription());
        holder.priceTextView.setText(String.format("$%.2f", menuItem.getPrice()));
        holder.checkBox.setChecked(menuItem.isAddedToCart());

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            menuItem.setAddedToCart(isChecked);
        });
    }

    @Override
    public int getItemCount() {
        if (currentCategory.equals("All")) {
            return menuItems.size();
        } else {
            int count = 0;
            for (MenuItem item : menuItems) {
                if (item.getCategory().equals(currentCategory)) {
                    count++;
                }
            }
            return count;
        }
    }

    private MenuItem getFilteredMenuItem(int position) {
        if (currentCategory.equals("All")) {
            return menuItems.get(position);
        } else {
            int filteredPosition = 0;
            for (MenuItem item : menuItems) {
                if (item.getCategory().equals(currentCategory)) {
                    if (filteredPosition == position) {
                        return item;
                    }
                    filteredPosition++;
                }
            }
        }
        return null;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameTextView;
        TextView descriptionTextView;
        TextView priceTextView;
        CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.menu_item_image);
            nameTextView = itemView.findViewById(R.id.menu_item_name);
            descriptionTextView = itemView.findViewById(R.id.menu_item_description);
            priceTextView = itemView.findViewById(R.id.menu_item_price);
            checkBox = itemView.findViewById(R.id.menu_item_checkbox);
        }
    }
}