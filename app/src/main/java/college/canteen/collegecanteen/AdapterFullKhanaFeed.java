package college.canteen.collegecanteen;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class AdapterFullKhanaFeed extends FirebaseRecyclerAdapter<ModleFullKhanaFeed, AdapterFullKhanaFeed.ViewHolder>{
    Context context;
    String category;

    public AdapterFullKhanaFeed(@NonNull FirebaseRecyclerOptions<ModleFullKhanaFeed> options, Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, final int position, @NonNull final ModleFullKhanaFeed model) {
        holder.category.setText(model.getCategory());
        holder.course.setText(model.getCourse());
        holder.tarkari.setText(model.getTarkari());
        holder.price.setText(model.getCoursePrice());
        holder.courseProgressBar.setVisibility(View.VISIBLE);
        Picasso.get().load(model.ImageUri).networkPolicy(NetworkPolicy.OFFLINE).into(holder.foodImage, new Callback() {
            @Override
            public void onSuccess() {
                holder.courseProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(context, "Offline", Toast.LENGTH_SHORT).show();
                Picasso.get().load(model.ImageUri).into(holder.foodImage);
                holder.courseProgressBar.setVisibility(View.GONE);

            }
        });
        Log.e("Message",model.getCategory());
        switch (model.getCategory()){
            case "Lunch":
                holder.background.setBackground(ContextCompat.getDrawable(context ,R.color.khanaBackground));
                holder.personorcuporplate.setText("/Person");
                break;
            case "Khaja":
                holder.background.setBackground(ContextCompat.getDrawable(context ,R.color.khajaBackground));
                holder.personorcuporplate.setText("/Plate");
                break;
            case "Beverages":
                holder.background.setBackground(ContextCompat.getDrawable(context ,R.color.bevragesBackground));
                holder.personorcuporplate.setText("/Unit");
                break;
            case "Chiya":
                holder.background.setBackground(ContextCompat.getDrawable(context ,R.color.chiyaBackground));
                holder.personorcuporplate.setText("/Cup");
                break;

        }
        holder.hotelProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context,HotelProfile.class));
            }
        });
        holder.khanaClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fulldetails=new Intent(context,CanteenKhanaDetails.class);
                fulldetails.putExtra("Key",getRef(position).getKey());
                fulldetails.putExtra("Category",category);
                context.startActivity(fulldetails);

            }
        });

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.khana_feed_single_item,parent,false);
        return new ViewHolder(view);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView foodImage;
        TextView category,course,tarkari,personorcuporplate,price;
        LinearLayout background,hotelProfile,khanaClick;
        ProgressBar courseProgressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foodImage=itemView.findViewById(R.id.foodimage_of_feed);
            category=itemView.findViewById(R.id.category);
            course=itemView.findViewById(R.id.course);
            tarkari=itemView.findViewById(R.id.tarkari);
            personorcuporplate=itemView.findViewById(R.id.personOrplateOrcup);
            price=itemView.findViewById(R.id.price);

            khanaClick=itemView.findViewById(R.id.full_khana_portion);
            background=itemView.findViewById(R.id.backgroundd);
            hotelProfile=itemView.findViewById(R.id.hotel_profile);
            courseProgressBar =itemView.findViewById(R.id.course_picProgressBar);

        }
    }
}