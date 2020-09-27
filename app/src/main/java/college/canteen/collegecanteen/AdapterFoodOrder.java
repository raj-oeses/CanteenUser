package college.canteen.collegecanteen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterFoodOrder extends RecyclerView.Adapter<AdapterFoodOrder.ViewHolder> {

    int []arr;
    public AdapterFoodOrder(int[] arr) {
        this.arr = arr;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout_profile_kolagi,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.foodImage.setImageResource(arr[position]);
        holder.foodcontent.setText("yesma "+position);
    }
    @Override
    public int getItemCount() {
        return arr.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView foodImage;
        TextView foodcontent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foodImage=itemView.findViewById(R.id.profilekhana);
            foodcontent=itemView.findViewById(R.id.k_khana);
        }
    }
}
