package com.aqarmap.androidtask.Code.Threading;

/**
 * Created by Mohammad Desouky on 03/04/2018.
 */

public interface IThreadTask<S, U, V>
{
    void BeforeRun();

    V Action(S... Params);

    void AfterRun(V Result);

    void OnProgress(U[] Values);

}
