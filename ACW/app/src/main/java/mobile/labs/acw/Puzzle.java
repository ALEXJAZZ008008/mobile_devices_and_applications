package mobile.labs.acw;

import android.content.Context;

public class Puzzle
{
    private String state, title, score;

    public Puzzle(Context context)
    {
        SetState(context.getResources().getString(R.string.download));
        SetTitle(context.getResources().getString(R.string.title));
        SetScore(context.getResources().getString(R.string.initialScore));
    }

    public Puzzle(String inTitle, Context context)
    {
        SetState(context.getResources().getString(R.string.download));
        SetTitle(inTitle);
        SetScore(context.getResources().getString(R.string.initialScore));
    }

    public Puzzle(String inState, String inTitle, String inScore)
    {
        SetState(inState);
        SetTitle(inTitle);
        SetScore(inScore);
    }

    public void SetState(String newState)
    {
        state = newState;
    }

    public String GetState()
    {
        return state;
    }

    public void SetTitle(String newTitle)
    {
        title = newTitle;
    }

    public String GetTitle()
    {
        return title;
    }

    public void SetScore(String newScore)
    {
        score = newScore;
    }

    public String GetScore()
    {
        return score;
    }
}
