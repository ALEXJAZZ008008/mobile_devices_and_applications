package mobile.labs.acw;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;

public class Preferences
{
    public static final String PREFERENCES = "preferences";
    public static final Integer MODE = Context.MODE_PRIVATE;

    public static void WriteBoolean(Context context, String key, Boolean value)
    {
        GetEditor(context).putBoolean(key, value).commit();
    }

    public static Boolean ReadBoolean(Context context, String key, Boolean value)
    {
        return GetPreferences(context).getBoolean(key, value);
    }

    public static void WriteInteger(Context context, String key, Integer value)
    {
        GetEditor(context).putInt(key, value).commit();
    }

    public static Integer ReadInteger(Context context, String key, Integer value)
    {
        return GetPreferences(context).getInt(key, value);
    }

    public static void WriteString(Context context, String key, String value)
    {
        GetEditor(context).putString(key, value).commit();
    }

    public static String ReadString(Context context, String key, String value)
    {
        return GetPreferences(context).getString(key, value);
    }

    public static ArrayList<String> GetAllInstancesOfKey(Context context, String key)
    {
        String[] preferences = GetPreferences(context).getAll().keySet().toArray(new String[GetPreferences(context).getAll().keySet().size()]);
        ArrayList<String> preferencesKeyList = new ArrayList<>();

        for(String string : preferences)
        {
            if(string.contains(key))
            {
                preferencesKeyList.add(string);
            }
        }

        return preferencesKeyList;
    }

    public static void RemoveKey(Context context, String key)
    {
        GetEditor(context).remove(key).commit();
    }

    public static SharedPreferences.Editor GetEditor(Context context)
    {
        return GetPreferences(context).edit();
    }
    public static SharedPreferences GetPreferences(Context context)
    {
        return context.getSharedPreferences(PREFERENCES, MODE);
    }
}
