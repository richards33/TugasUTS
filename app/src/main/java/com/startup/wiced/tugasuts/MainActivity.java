package com.startup.wiced.tugasuts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    EditText txtnama;
    Button btnplus,btnmin,btnadd,btnreset,btndelete;
    RadioGroup rgtype;
    RadioButton rbtea,rbcoffe,rbsmoothies,radioButton;
    TextView tvQty,tvname,tvtotal;
    int qty=1;
    String jenis;
    int harga=0;
    String topping;
    private int index=-1;
    CheckBox cbpearl,cbpudding,cbred,cbcoco;
    ArrayList<Order> order;
    RecyclerView rview;
    OrderAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rgtype=findViewById(R.id.rgType);
        txtnama=findViewById(R.id.txtNama);
        btnmin=findViewById(R.id.btnMin);
        btnplus=findViewById(R.id.btnPlus);
        rbcoffe=findViewById(R.id.rbCoffe);
        rbtea=findViewById(R.id.rbTea);
        rbsmoothies=findViewById(R.id.rbSmoothies);
        tvQty=findViewById(R.id.tvQty);
        cbpearl=findViewById(R.id.cbPearl);
        cbred=findViewById(R.id.cbRed);
        cbpudding=findViewById(R.id.cbPudding);
        cbcoco=findViewById(R.id.cbCoco);
        btnreset=findViewById(R.id.btnReset);
        btnadd=findViewById(R.id.btnAdd);
        btndelete=findViewById(R.id.btnDel);
        tvname=findViewById(R.id.txtname);
        tvtotal=findViewById(R.id.txtTotal);
        rbtea.setChecked(true);
        rview = findViewById(R.id.rView);
        order = new ArrayList<Order>();

        adapter= new OrderAdapter(order, new RVClickListener() {
            @Override
            public void recyclerViewListClicked(View v, int posisi) {
                index=posisi;
                tvQty.setText(order.get(posisi).getQty()+"");
                if (order.get(posisi).getType().equals("Tea"))rbtea.setChecked(true);
                if (order.get(posisi).getType().equals("Coffee"))rbcoffe.setChecked(true);
                if (order.get(posisi).getType().equals("Smoothies"))rbsmoothies.setChecked(true);

                cbpearl.setChecked(false);
                cbcoco.setChecked(false);
                cbpudding.setChecked(false);
                cbred.setChecked(false);
                for (String s : order.get(posisi).getToppings()) {
                    if (s.equals("Pearl"))cbpearl.setChecked(true);
                    if (s.equals("Coconut Jelly"))cbcoco.setChecked(true);
                    if (s.equals("Pudding"))cbpudding.setChecked(true);
                    if (s.equals("Red Bean"))cbred.setChecked(true);
                }
            }
        });

        RecyclerView.LayoutManager lm = new LinearLayoutManager(MainActivity.this);
        rview.setLayoutManager(lm);
        rview.setAdapter(adapter);

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                int selectedId = rgtype.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(selectedId);
                String inputNama=txtnama.getText().toString();
                if (inputNama.equalsIgnoreCase("")){
                    Toast.makeText(MainActivity.this, "Field name cannot be empty", Toast.LENGTH_SHORT).show();
                }else {
                    tvname.setText(txtnama.getText().toString());
                    ArrayList<String> topping = new ArrayList<String>();
                    String type=radioButton.getText().toString();
                    int subtotal = 0;
                    if (type.equalsIgnoreCase("Tea")){
                        jenis="Tea";
                        harga=harga+23000;
                        subtotal = 23000;
                    } if (type.equalsIgnoreCase("Coffe")){
                        jenis="Coffe";
                        harga=harga+25000;
                        subtotal = 25000;
                    } if (type.equalsIgnoreCase("Smoothies")){
                        jenis="Smoothies";
                        harga=harga+30000;
                        subtotal = 30000;
                    }
                    if (cbpearl.isChecked()){
                        topping.add("Pearl");
                        harga=harga+3000;
                        subtotal += 3000;
                    }if (cbred.isChecked()){
                        topping.add("Red Bean");
                        harga=harga+4000;
                        subtotal += 4000;
                    }if (cbcoco.isChecked()){
                        topping.add("Coconut Jelly");
                        harga=harga+4000;
                        subtotal += 4000;
                    }if (cbpudding.isChecked()){
                        topping.add("Pudding");
                        harga=harga+3000;
                        subtotal += 3000;
                    }
                    //Toast.makeText(MainActivity.this, topping.get(0), Toast.LENGTH_SHORT).show();
                    order.add(new Order(jenis,topping,qty,subtotal*qty));
                    tvtotal.setText(String.valueOf(harga));
                    adapter.notifyDataSetChanged();
                }
            }
        });

        btnplus.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                qty+=1;
                String strI = String.valueOf(qty);
                tvQty.setText(strI);
            }
        });

        btnmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (qty>1){
                    qty-=1;
                    String strI = String.valueOf(qty);
                    tvQty.setText(strI);
                }
            }
        });
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                harga-=order.get(index).getSubtotal();
                tvtotal.setText(harga+"");
                order.remove(index);

                adapter.notifyDataSetChanged();
                txtnama.setText("");
                rbtea.setChecked(true);
                tvname.setText("Cust");
                tvtotal.setText("0");
                if (cbred.isChecked()==true){
                    cbred.setChecked(false);
                }
                if (cbcoco.isChecked()==true){
                    cbcoco.setChecked(false);
                }
                if (cbpudding.isChecked()==true){
                    cbpudding.setChecked(false);
                }
                if (cbpearl.isChecked()==true){
                    cbpearl.setChecked(false);
                }
            }
        });
        btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order.clear();
                txtnama.setText("");
                rbtea.setChecked(true);
                tvname.setText("Cust");
                tvtotal.setText("0");
                if (cbred.isChecked()==true){
                    cbred.setChecked(false);
                }
                if (cbcoco.isChecked()==true){
                    cbcoco.setChecked(false);
                }
                if (cbpudding.isChecked()==true){
                    cbpudding.setChecked(false);
                }
                if (cbpearl.isChecked()==true){
                    cbpearl.setChecked(false);
                }
            }
        });
    }



}