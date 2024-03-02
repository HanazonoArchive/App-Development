package yurine.hanazono.folder;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    //Baseline
    public static double balut_baseline;
    public static double penoy_baseline;

    //Rate
    public static double balut_rate_baseline;
    public static double penoy_rate_baseline;

    //Selling Rate
    public static double balut_selling_rate;
    public static double penoy_selling_rate;

    //Excess
    public static double balut_sobra;
    public static double penoy_sobra;

    //Capital
    public static double balut_halin;
    public static double penoy_halin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button calculateButton = findViewById(R.id.btnCalculate);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();

                EditText tfBalut = findViewById(R.id.tfBalut);
                EditText tfPenoy = findViewById(R.id.tfPenoy);
                EditText tfBalutBaseline = findViewById(R.id.tfBalutBaseline);
                EditText tfPenoyBaseline = findViewById(R.id.tfPenoyBaseline);
                EditText tfBalutRateBaseline = findViewById(R.id.tfBalutRateBaseline);
                EditText tfPenoyRateBaseline = findViewById(R.id.tfPenoyRateBaseline);
                EditText tfBalutSellingPrice = findViewById(R.id.tfBalutSellingPrice);
                EditText tfPenoySellingPrice = findViewById(R.id.tfPenoySellingPrice);

                try {
                    balut_sobra = Double.parseDouble(tfBalut.getText().toString());
                    penoy_sobra = Double.parseDouble(tfPenoy.getText().toString());
                    balut_baseline = Double.parseDouble(tfBalutBaseline.getText().toString());
                    penoy_baseline = Double.parseDouble(tfPenoyBaseline.getText().toString());
                    balut_rate_baseline = Double.parseDouble(tfBalutRateBaseline.getText().toString());
                    penoy_rate_baseline = Double.parseDouble(tfPenoyRateBaseline.getText().toString());
                    balut_selling_rate = Double.parseDouble(tfBalutSellingPrice.getText().toString());
                    penoy_selling_rate = Double.parseDouble(tfPenoySellingPrice.getText().toString());

                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Please enter valid numbers", Toast.LENGTH_SHORT).show();
                    return;
                }

                String result = "";


// Sold Pieces
                balut_halin = balut_baseline - balut_sobra;
                penoy_halin = penoy_baseline - penoy_sobra;
                double total_halin = balut_halin + penoy_halin;

                double balut_halin_income = balut_halin * balut_selling_rate;
                double penoy_halin_income = penoy_halin * penoy_selling_rate;
                double total_halin_income = balut_halin_income + penoy_halin_income;

                result += "Pila ka Balut nahalin: " + (int) balut_halin + " pc's" + "\n";
                result += "Pila ka Penoy nahalin: " + (int) penoy_halin + " pc's" + "\n";
                result += "Pila tanan nahalin: " + (int) total_halin + " pc's" + "\n";
                result += "Income nimo sa Balut lang: ₱" + balut_halin_income + "\n";
                result += "Income nimo sa Penoy lang: ₱" + penoy_halin_income + "\n";
                result += "Income nimo tanan: ₱" + total_halin_income + "\n\n";

// Order
                double balut_expenses_Capital = balut_baseline * balut_rate_baseline;
                double penoy_expenses_Capital = penoy_baseline * penoy_rate_baseline;
                double Total_quantity_baseline = balut_baseline + penoy_baseline;
                double Total_expenses_baseline = balut_expenses_Capital + penoy_expenses_Capital;

                result += "Pila ka Balut and Baseline: " + (int) balut_baseline + " pc's" + "\n";
                result += "Pila ka Penoy ang Baseline: " + (int) penoy_baseline + " pc's" + "\n";
                result += "Pila tanan ang Baseline: " + (int) Total_quantity_baseline + " pc's" + "\n";
                result += "Pila Capital sa Balut: ₱" + balut_expenses_Capital + "\n";
                result += "Pila Capital sa Penoy: ₱" + penoy_expenses_Capital + "\n";
                result += "Pila tanan Capital: ₱" + Total_expenses_baseline + "\n\n";

// Capital
                double balut_halin_Capital = balut_halin * balut_rate_baseline;
                double penoy_halin_Capital = penoy_halin * penoy_rate_baseline;
                double total_halin_Capital = balut_halin_Capital + penoy_halin_Capital;
                double profit = total_halin_income - total_halin_Capital;

                result += "Capital sa Balut na nahalin: ₱" + balut_halin_Capital + "\n";
                result += "Capital sa Penoy na nahalin: ₱" + penoy_halin_Capital + "\n";
                result += "Capital sa Tanan na nahalin: ₱" + total_halin_Capital + "\n\n";

                result += "Ginansya: ₱" + profit;

                TextView taResult = findViewById(R.id.taResult);
                taResult.setText("");
                taResult.append(result);

                Toast.makeText(MainActivity.this, "Success!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
}