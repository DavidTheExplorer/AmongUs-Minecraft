package dte.amongus.shiptasks.listeners;

import dte.amongus.shiptasks.ShipTask;

public interface TaskFinishListener<T extends ShipTask>
{
	void onFinish(T task);
}