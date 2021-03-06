package mobile.labs.acw.utilities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import mobile.labs.acw.objects.puzzle_list_item_object;
import mobile.labs.acw.R;

public class custom_adapter extends ArrayAdapter<puzzle_list_item_object>
{
    private Context context;

    private Integer lastPosition = -1;

    public custom_adapter(Context context, ArrayList<puzzle_list_item_object> data)
    {
        super(context, R.layout.list_template, data);

        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        // Get the data item for this position
        puzzle_list_item_object puzzleListItem = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        view_holder viewHolder; // view lookup cache stored in tag

        final View result;

        Animation animation;

        if (convertView == null)
        {
            viewHolder = new view_holder();

            LayoutInflater inflater = LayoutInflater.from(getContext());

            convertView = inflater.inflate(R.layout.list_template, parent, false);

            viewHolder.SetStateTextView((TextView)convertView.findViewById(R.id.state));
            viewHolder.SetTitleTextView((TextView)convertView.findViewById(R.id.title));
            viewHolder.SetScoreTextView((TextView)convertView.findViewById(R.id.score));

            result=convertView;

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (view_holder) convertView.getTag();
            result=convertView;
        }

        if(position > lastPosition)
        {
            animation = AnimationUtils.loadAnimation(context, R.anim.up_from_bottom);
        }
        else
        {
            animation = AnimationUtils.loadAnimation(context, R.anim.down_from_top);
        }

        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.GetStateTextView().setText(puzzleListItem.GetState());
        viewHolder.GetTitleTextView().setText(puzzleListItem.GetTitle());
        viewHolder.GetScoreTextView().setText(puzzleListItem.GetScore());

        // Return the completed view to render on screen
        return convertView;
    }
}