package com.blackstone.goldenquran.adapters;

import android.app.Dialog;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blackstone.goldenquran.R;
import com.blackstone.goldenquran.models.Mos7afModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Mos7afAdapter extends RecyclerView.Adapter<Mos7afViewHolder> {

    List<Mos7afModel> models;
    LayoutInflater layoutInflater;
    Context context;
    Dialog dialog;

    public Mos7afAdapter(Context context, List<Mos7afModel> models) {
        this.models = models;
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public Mos7afViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Mos7afViewHolder(layoutInflater.inflate(R.layout.mos7af_row_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(Mos7afViewHolder holder, final int position) {
        if (models.get(position).type.equalsIgnoreCase("warsh"))
            holder.mos7afImage.setImageResource(R.drawable.mos7af_warsh);
        else
            holder.mos7afImage.setImageResource(R.drawable.mos7af_medina);
        holder.mos7afName.setText(models.get(position).mos7afName);
        holder.mos7afRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = onCreateDialog(position);
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    private Dialog onCreateDialog(final int pos) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
        View v = layoutInflater.inflate(R.layout.mos7af_details_layout, null);

        FloatingActionButton addMoshafButton = (FloatingActionButton) v.findViewById(R.id.addMoshafButton);
        FloatingActionButton cancelButton = (FloatingActionButton) v.findViewById(R.id.cancelButton);
        final AppCompatSpinner spinner = (AppCompatSpinner) v.findViewById(R.id.mos7afType);
        final EditText mos7afNameDetails = (EditText) v.findViewById(R.id.mos7afNameDetails);
        TextView title = (TextView) v.findViewById(R.id.titleDialog);

        title.setText("Edit Mos7af");

        addMoshafButton.setImageResource(R.drawable.save);
        cancelButton.setImageResource(R.drawable.delete);

        spinner.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, new String[]{"Warsh", "medina"}));

        mos7afNameDetails.setText(models.get(pos).mos7afName);

        if (models.get(pos).type.equalsIgnoreCase("warsh"))
            spinner.setSelection(0);
        else
            spinner.setSelection(1);

        addMoshafButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                models.get(pos).mos7afName = mos7afNameDetails.getText().toString();
                if (spinner.getSelectedItem().toString().equalsIgnoreCase("warsh"))
                    models.get(pos).type = "warsh";
                else
                    models.get(pos).type = "medina";
                notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                models.remove(pos);
                notifyDataSetChanged();
                dialog.dismiss();
            }
        });


        alert.setView(v);

        return alert.create();
    }
}

class Mos7afViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.mos7afImage)
    ImageView mos7afImage;
    @BindView(R.id.mos7afName)
    TextView mos7afName;
    @BindView(R.id.mos7afRelative1)
    RelativeLayout mos7afRelative;

    Mos7afViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
