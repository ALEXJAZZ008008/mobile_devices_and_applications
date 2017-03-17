package mobile.labs.acw;

import android.content.Context;
import android.os.AsyncTask;
import java.net.URL;
import java.util.ArrayList;

public class MenuTasks extends AsyncTask<String, Void, String>
{
    private MenuActivity menuActivity;

    private ArrayList<ArrayList<String>> jsonArrays;
    private ArrayList<ImageObject> imageArray;

    private String parentURL, imagesURL, indexURL, pictureSetsURL, puzzlesURL, jsonExtension;

    public MenuTasks(Context context)
    {
        menuActivity = (MenuActivity)context;

        jsonArrays = new ArrayList<>();
        imageArray = new ArrayList<>();

        parentURL = "http://www.hull.ac.uk/php/349628/08027/acw/";
        imagesURL = "images/";
        jsonExtension = ".json";
        indexURL = "index" + jsonExtension;
        pictureSetsURL = "picturesets/";
        puzzlesURL = "puzzles/";
    }

    protected String doInBackground(String... args)
    {
        switch(args[0])
        {
            case "StartList":

                StartList();
                break;

            case "GetPuzzle":

                CheckGetPuzzle(args);
                break;

            case "GetPuzzleImages":

                GetPuzzleImages(args[1]);
                break;
        }

        return args[0];
    }

    private void StartList()
    {
        GoToJSON("null", new String[] { "PuzzleIndex" }, parentURL + indexURL);

        WaitForJSON();

        for(int i = 0; i < jsonArrays.get(0).size(); i++)
        {
            menuActivity.puzzleList.add(new PuzzleListItemObject(menuActivity, jsonArrays.get(0).get(i).split(jsonExtension)[0]));
        }

        jsonArrays.clear();
    }

    private void CheckGetPuzzle(String[] args)
    {
        if(!Boolean.valueOf(args[1]))
        {
            menuActivity.puzzleListBoolean = true;

            GetPuzzle(args[2]);
        }
    }

    private void GetPuzzle(String arg)
    {
        PuzzleListItemObject puzzleListItem = menuActivity.puzzleList.get(Integer.valueOf(arg));

        GoToJSON("Puzzle", new String[] { "Id", "PictureSet", "Rows", "Layout" }, parentURL + puzzlesURL + puzzleListItem.GetTitle() + jsonExtension);

        WaitForJSON();

        PuzzleObject puzzleObject = menuActivity.puzzleList.get(Integer.valueOf(arg)).GetPuzzle();

        ArrayList<String> layoutList = new ArrayList<>();

        puzzleObject.SetPictureSet(jsonArrays.get(1).get(0).split(jsonExtension)[0]);

        menuActivity.GoToTasks(new String[] { "GetPuzzleImages", arg });

        puzzleObject.SetId(jsonArrays.get(0).get(0));
        puzzleObject.SetRows(jsonArrays.get(2).get(0));

        for(int i = 0; i < jsonArrays.get(3).size(); i++)
        {
            layoutList.add(jsonArrays.get(3).get(i));
        }

        jsonArrays.clear();

        puzzleObject.SetLayout(layoutList);
    }

    private void GetPuzzleImages(String arg)
    {
        PuzzleListItemObject puzzleListItem = menuActivity.puzzleList.get(Integer.valueOf(arg));
        PuzzleObject puzzleObject = puzzleListItem.GetPuzzle();
        String pictureSet = puzzleObject.GetPictureSet();

        GoToJSON("null", new String[] { "PictureFiles" }, parentURL + pictureSetsURL + pictureSet + jsonExtension);

        WaitForJSON();

        int length = jsonArrays.get(0).size();

        for(int i = 0; i < length; i++)
        {
            String itemName = jsonArrays.get(0).get(i);

            imageArray.add(new ImageObject());

            GoToImages(i, pictureSet, itemName, parentURL + imagesURL + itemName);
        }

        WaitForImages(length);

        jsonArrays.clear();
        imageArray.clear();

        puzzleListItem.SetState(menuActivity.getResources().getString(R.string.play));
    }

    private void GoToJSON(String key, String[] arguments, String url)
    {
        try
        {
            new JSON(jsonArrays, key, arguments).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new URL(url));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private void WaitForJSON()
    {
        while (jsonArrays.size() == 0)
        {
            try
            {
                Thread.sleep(1000);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private void GoToImages(int i, String fileName, String itemName, String url)
    {
        try
        {
            new MenuImages(menuActivity, imageArray.get(i), fileName, itemName).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new URL(url));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private void WaitForImages(int length)
    {
        for(int i = 0; i < length; i++)
        {
            while (!imageArray.get(i).GetBitmapBoolean())
            {
                try
                {
                    Thread.sleep(100);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    protected void onPostExecute(String arg)
    {
        switch(arg)
        {
            case "StartList":

                menuActivity.customAdapter.notifyDataSetChanged();
                break;

            case "GetPuzzleImages":

                menuActivity.puzzleListBoolean = false;

                menuActivity.customAdapter.notifyDataSetChanged();
                break;

            default:

                break;
        }
    }
}