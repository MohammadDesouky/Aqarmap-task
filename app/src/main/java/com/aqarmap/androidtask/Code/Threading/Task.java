package com.aqarmap.androidtask.Code.Threading;

import android.os.AsyncTask;

import java.util.ArrayList;

/**
 * Created by Mohammad Desouky on 03/04/2018.
 */

/**
 * A background task that can do a background task called MainAction and use any numbers of callbacks  (IThreadTask) with the MainAction result
 * Task will call the callbacks  onpreexecute and onpostexecute BUT NOT onprogress nor doInBackground
 * ONLY THE MainAction is called
 *
 * @param <S>        Generic type of parameters to be passed to the async task
 * @param <U>        generic type of the progress parameter to be sent the async task
 * @param <V>generic type of result
 */
public class Task<S, U, V> extends AsyncTask<S, U, V>
{


    ArrayList<IThreadTask<S, U, V>> CallBackActions;
    IThreadTask<S, U, V> MainAction;

    public Task(IThreadTask<S, U, V> Action)
    {
        super();
        CallBackActions = new ArrayList<>();
        CallBackActions.add(Action);

    }

    public Task()
    {
        super();
        CallBackActions = new ArrayList<>();
    }

    public void SetMainAction(IThreadTask<S, U, V> action)
    {

        MainAction = action;
    }

    public void AddAction(IThreadTask<S, U, V> action)
    {
        if (!CallBackActions.contains(action))
            CallBackActions.add(action);
    }

    @Override
    protected V doInBackground(S... ts)
    {
        return MainAction != null ? MainAction.Action(ts) : null;
    }

    @Override
    protected void onPostExecute(V v)
    {
        super.onPostExecute(v);
        if (MainAction != null)
            MainAction.AfterRun(v);
        for (IThreadTask<S, U, V> action : CallBackActions)
        {
            if (action != null)
                action.AfterRun(v);
        }
    }

    @Override
    protected void onProgressUpdate(U[] values)
    {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();

        if (MainAction != null)
            MainAction.BeforeRun();
        for (IThreadTask<S, U, V> action : CallBackActions)
        {
            if (action != null)
                action.BeforeRun();
        }
    }
}
