package com.example.uvtesting;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private ListView lvItems;
    private List<Product> lstProducts;
    CountdownAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvItems = (ListView) findViewById(R.id.lvItems);
        lstProducts = new ArrayList<>();
        lvItems.setAdapter(adapter = new CountdownAdapter(MainActivity.this, lstProducts));
//        lvItems.setOnItemLongClickListener((parent, view, pos, id)-> {
//            Product selected = adapter.getItem(pos);
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle("Do you want to delete this Entry?")
//                    .setMessage("The selected mask is: "+lstProducts.get(pos).getId())
//                    .setPositiveButton("Confirm",(click,arg)->{
//                        lstProducts.remove(pos);adapter.notifyDataSetChanged();
//                        })
//                    .setNegativeButton("Cancel",(click,arg)->{adapter.notifyDataSetChanged();}).create().show();
//            //           showContact(pos);
//            return true;
//        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child("Uv");

         //Attach a listener to read the data at our posts reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String id = (String) dataSnapshot.child("id").getValue();
                lstProducts.add(new Product(id, System.currentTimeMillis() + 5000,false));
                adapter.notifyDataSetChanged();
                System.out.println(id);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    private class Product {
        String id;
        long expirationTime;
        boolean inIsolation;

        public Product(String id, long expirationTime, boolean isolation) {
            this.id = id;
            this.inIsolation=isolation;
            this.expirationTime = expirationTime;
        }

        public String getId() {
            return id;
        }
    }


    public class CountdownAdapter extends ArrayAdapter<Product> {

        private LayoutInflater lf;
        private List<ViewHolder> lstHolders;
        private Handler mHandler = new Handler();
        private Runnable updateRemainingTimeRunnable = new Runnable() {
            @Override
            public void run() {
                synchronized (lstHolders) {
                    long currentTime = System.currentTimeMillis();
                    for (ViewHolder holder : lstHolders) {
                        holder.updateTimeRemaining(currentTime);
                    }
                }
            }
        };

        public CountdownAdapter(Context context, List<Product> objects) {
            super(context, 0, objects);
            lf = LayoutInflater.from(context);
            lstHolders = new ArrayList<>();
            startUpdateTimer();
        }

        private void startUpdateTimer() {
            Timer tmr = new Timer();
            tmr.schedule(new TimerTask() {
                @Override
                public void run() {
                    mHandler.post(updateRemainingTimeRunnable);
                }
            }, 1000, 1000);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = lf.inflate(R.layout.list_item, parent, false);
                holder.tvProduct = (TextView) convertView.findViewById(R.id.maskIDValue);
                holder.tvTimeRemaining = (TextView) convertView.findViewById(R.id.tvTimeRemainingValue);
                convertView.setTag(holder);
                synchronized (lstHolders) {
                    lstHolders.add(holder);
                }
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.setData(getItem(position));

            return convertView;
        }
    }

    private class ViewHolder {
        TextView tvProduct;
        TextView tvTimeRemaining;
        Product mProduct;

        public void setData(Product item) {
            mProduct = item;
            tvProduct.setText(item.getId());
            updateTimeRemaining(System.currentTimeMillis());
        }

        public void updateTimeRemaining(long currentTime) {
            long timeDiff = mProduct.expirationTime - currentTime;
            if (timeDiff > 0) {
                int seconds = (int) (timeDiff / 1000) % 60;
                int minutes = (int) ((timeDiff / (1000 * 60)) % 60);
                int hours = (int) ((timeDiff / (1000 * 60 * 60)) % 24);
                int days = (int) ((timeDiff / (1000 * 60 * 60 * 24)) % 365);
                String under;
                if(mProduct.inIsolation)
                    under=" under Isolation";
                else
                    under=" under UV Radiation";
                tvTimeRemaining.setText(days + getString(R.string.days) + hours + getString(R.string.hrs) + minutes + getString(R.string.mins) + seconds + getString(R.string.sec)+under);
            } else {
                String message;
                lstProducts.remove(mProduct);
                if(mProduct.inIsolation)
                    message = getString(R.string.ready);
                else{
                    message = getString(R.string.swapMask);
                    lstProducts.add(new Product(tvProduct.getText().toString(), System.currentTimeMillis() + 259200000, true));
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Mask with ID: "+tvProduct.getText()+" is sanitized now")
                        .setMessage(message)
                        .setPositiveButton("Okay",(click,arg)->{
//                            lstProducts.add(new Product(tvProduct.getText().toString(), System.currentTimeMillis() + 259200000));
                        }).create().show();
                adapter.notifyDataSetChanged();
            }
        }
    }
}