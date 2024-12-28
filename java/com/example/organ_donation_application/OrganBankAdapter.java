package com.example.organ_donation_application;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

public class OrganBankAdapter extends RecyclerView.Adapter<OrganBankAdapter.ViewHolder> {
    private Context context;
    private List<OrganBank> organBanks;

    public OrganBankAdapter(Context context, List<OrganBank> organBanks) {
        this.context = context;
        this.organBanks = organBanks;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_blood_bank, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrganBank organBank = organBanks.get(position);
        holder.textViewName.setText("Name : "+organBank.getName());
        holder.textViewmobile.setText(organBank.getMobile_number());
        holder.textViewlat.setText(organBank.getLatitude());
        holder.textViewlng.setText(organBank.getLongitude());
        Picasso.get().load(organBank.getImageUrl()).into(holder.imageView);

        holder.buttonView.setOnClickListener(v -> {
            Intent intent = new Intent(context, BloodBankDetailsActivity.class);
            intent.putExtra("bloodBankId", organBank.getId());
            intent.putExtra("bloodBanklat", organBank.getLatitude());
            intent.putExtra("bloodBanklng", organBank.getLongitude());


            context.startActivity(intent);
        });

        holder.textViewmobile.setOnClickListener(v -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setCancelable(false);
            builder.setMessage("Select Action Mode?");

            builder.setPositiveButton("CALL", (dialog, which) -> {
                // If the user pressed "CALL", open the dialer with the phone number
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + holder.textViewmobile.getText().toString()));
                v.getContext().startActivity(callIntent);
            });

            builder.setNegativeButton("SMS", (dialog, which) -> {
                // If the user pressed "SMS", open the messaging app with the phone number
                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                smsIntent.setData(Uri.parse("smsto:" + holder.textViewmobile.getText().toString()));
                smsIntent.putExtra("sms_body", "Here is Emergency for Organ"); // Pre-fill SMS body if needed
                v.getContext().startActivity(smsIntent);
            });

            AlertDialog alert = builder.create();
            alert.show();

        });
    }

    @Override
    public int getItemCount() {
        return organBanks.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewName,textViewmobile,textViewlat,textViewlng;
        Button buttonView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewBloodBank);
            textViewName = itemView.findViewById(R.id.textViewBloodBankName);
            buttonView = itemView.findViewById(R.id.buttonViewBloodBank);


            textViewmobile = itemView.findViewById(R.id.textViewmobile);
            textViewlat = itemView.findViewById(R.id.textViewlatitude);
            textViewlng = itemView.findViewById(R.id.textViewlongitude);

        }
    }
}