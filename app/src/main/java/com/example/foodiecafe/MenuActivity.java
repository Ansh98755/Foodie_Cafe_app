package com.example.foodiecafe;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MenuAdapter menuAdapter;
    private List<MenuItem> menuItems;
    private TextView totalPriceTextView;
    private Button placeOrderButton;
    private Spinner categorySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        recyclerView = findViewById(R.id.menu_recycler_view);
        totalPriceTextView = findViewById(R.id.total_price_text_view);
        placeOrderButton = findViewById(R.id.place_order_button);
        categorySpinner = findViewById(R.id.category_spinner);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        menuItems = new ArrayList<>();
        // Add sample menu items
        menuItems.add(new MenuItem("Pizza", "Delicious Margherita Pizza", 10.99, R.drawable.pizza, "Italian"));
        menuItems.add(new MenuItem("Burger", "Juicy Beef Burger", 8.99, R.drawable.burger, "Fast Food"));
        menuItems.add(new MenuItem("Dal Makhani", "Rich and creamy lentils", 12.99, R.drawable.dal_makhani, "Indian"));
        menuItems.add(new MenuItem("Shahi Paneer", "Paneer in a rich tomato-cream gravy", 14.99, R.drawable.shahi_panner, "Indian"));
        menuItems.add(new MenuItem("Butter Naan", "Soft and buttery flatbread", 2.99, R.drawable.butter_naan, "Indian"));
        menuItems.add(new MenuItem("Garlic Naan", "Naan with a touch of garlic", 3.49, R.drawable.garlic_naan, "Indian"));
        menuItems.add(new MenuItem("Pad Thai", "Stir-fried noodles with vegetables and tofu", 11.99, R.drawable.pad_thai, "Thai"));
        menuItems.add(new MenuItem("Tom Yum Soup", "Spicy and sour Thai soup", 7.99, R.drawable.tom_yum_soup, "Thai"));
        // Add more items with appropriate category

        menuAdapter = new MenuAdapter(menuItems, this);
        recyclerView.setAdapter(menuAdapter);

        // Set up category spinner
        String[] categories = {"All", "Indian", "Thai", "Fast Food"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = categories[position];
                menuAdapter.filterByCategory(selectedCategory);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Calculate total price
                double totalPrice = 0.0;
                for (MenuItem item : menuItems) {
                    if (item.isAddedToCart()) {
                        totalPrice += item.getPrice();
                    }
                }

                // Display total price
                totalPriceTextView.setText("Total Price: $" + String.format("%.2f", totalPrice));

                // Show order confirmation (replace with actual order placement logic)
                Toast.makeText(MenuActivity.this, "Order Placed!", Toast.LENGTH_SHORT).show();

                // Clear cart for next order
                for (MenuItem item : menuItems) {
                    item.setAddedToCart(false);
                }
                menuAdapter.notifyDataSetChanged();
            }
        });
    }
}