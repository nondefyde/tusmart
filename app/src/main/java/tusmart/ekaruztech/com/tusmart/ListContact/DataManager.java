package tusmart.ekaruztech.com.tusmart.ListContact;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import tusmart.ekaruztech.com.tusmart.R;

/**
 * Created by greepon123 on 1/27/2016.
 */
public class DataManager extends RecyclerView.Adapter<DataManager.RecyclerViewHolder> {

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView mName, mPhone,mTrustee,mDistance;
        View mCircle;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.CONTACT_name);
            mPhone = (TextView) itemView.findViewById(R.id.CONTACT_phone);
            mCircle = itemView.findViewById(R.id.CONTACT_circle);
            mTrustee = (TextView) itemView.findViewById(R.id.CONTACT_trustee);
            mDistance = (TextView) itemView.findViewById(R.id.CONTACT_distance);
        }
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_list_detail, viewGroup, false);
        return new RecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder viewHolder, int i) {
        // get the single element from the main array
        final Contact contact = Contact.CONTACTS[i];
        // Set the values
        viewHolder.mName.setText(contact.get(Contact.Field.NAME));
        viewHolder.mPhone.setText(contact.get(Contact.Field.PHONE));
        viewHolder.mTrustee.setText(contact.get(Contact.Field.EMAIL));
        viewHolder.mDistance.setText(contact.get(Contact.Field.DISTANCE));

        // Set the color of the shape
        GradientDrawable bgShape = (GradientDrawable) viewHolder.mCircle.getBackground();
        bgShape.setColor(Color.parseColor(contact.get(Contact.Field.COLOR)));
//        viewHolder.mCircle.setBackgroundColor(Color.parseColor(contact.get(Contact.Field.COLOR)));
    }

    @Override
    public int getItemCount() {
        return Contact.CONTACTS.length;
    }
}