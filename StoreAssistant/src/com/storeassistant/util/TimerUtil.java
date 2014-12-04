package com.storeassistant.util;

import java.util.Timer;
import java.util.TimerTask;

public class TimerUtil {
	
	public static Timer getTimer(String name){
		Timer timer = new Timer(name);
		return timer;
	}
	
	/**
	 * @param timer
	 * @param task
	 * @param delay
	 */
	public static void schedule(Timer timer, TimerTask task, long delay) {
		if(timer == null || task == null){
			return;
		}
		timer.schedule(task, delay);
	}
	
	/**
	 * @param timer
	 * @param task
	 * @param delay
	 * @param period
	 */
	public static void schedule(Timer timer, TimerTask task, long delay, long period) {
		if(timer == null || task == null){
			return;
		}
		timer.schedule(task, delay, period);
	}
	
	/**
	 * @param task
	 * @param delay
	 * @param period
	 */
	public static void scheduleAtFixedRate(Timer timer, TimerTask task, long delay, long period) {
		if(timer == null || task == null){
			return;
		}
		timer.scheduleAtFixedRate(task, delay, period);;;
	}

}
